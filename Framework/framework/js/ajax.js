var Status=new function()
{
    this.statusDiv=null;
	this.init=function()
	{
	    if (this.statusDiv!=null)
	    {
	        return;
	    }
		var body = document.getElementsByTagName("body")[0];
		var div = document.createElement("div");
		div.style.position = "absolute";
		div.style.top = "50%";
		div.style.left = "50%";
		div.style.width = "280px";
		div.style.margin = "-50px 0 0 -100px";		
		div.style.padding = "15px";
		div.style.backgroundColor = "#353555";
		div.style.border = "1px solid #CFCFFF";
		div.style.color = "#CFCFFF";
		div.style.fontSize = "14px";
		div.style.textAlign = "center";
		div.id = "status";
		body.appendChild(div);
		div.style.display="none";
		this.statusDiv=document.getElementById("status");
	}
	this.showInfo=function(_message)
	{	  
	    if (this.statusDiv==null)
	    {
	        this.init();
	    }  
	    this.setStatusShow(true);
	    this.statusDiv.innerHTML = _message;	    
	}
	this.setStatusShow=function(_show)
	{	  
	    if (this.statusDiv==null)
	    {
	        this.init();
	    } 
	    if (_show)
	    {
	        this.statusDiv.style.display="";
	    }
	    else
	    {
	        this.statusDiv.innerHTML="";
	        this.statusDiv.style.display="none";
	    }
	}
}
function HttpRequestObject()
{
    this.chunnel=null;
    this.instance=null;
}
var Request=new function()
{
    this.showStatus=true;
    this.httpRequestCache=new Array();
    this.createInstance=function()
    {
        var instance=null;
        if (window.XMLHttpRequest)
        {
            instance=new XMLHttpRequest();
            if (instance.overrideMimeType)
            {
                instance.overrideMimeType="text/xml";
            }
        }
        else if (window.ActiveXObject)
        {
                instance = new ActiveXObject("Microsoft.XMLHTTP");
        }
        return instance;
    }
    this.getInstance=function(_chunnel)
    {
        var instance=null;
        var object=null;
        if (_chunnel==undefined)
        {
            _chunnel="default";
        }
        var getOne=false;
        for(var i=0; i<this.httpRequestCache; i++)
        {
            object=HttpRequestObject(this.httpRequestCache[i]);
            if (object.chunnel==_chunnel)
            {
                if (object.instance.readyState==0 || object.instance.readyState==4)
                {
                    instance=object.instance;
                }
                getOne=true;
                break;                    
            }
        }
        if (!getOne)
        {
            object=new HttpRequestObject();
            object.chunnel=_chunnel;
            object.instance=this.createInstance();
            this.httpRequestCache.push(object);
            instance=object.instance;
        }         
        return instance;
    }
    this.send=function(_url,_data,_processRequest,_chunnel,_asynchronous)
    {
        if (_url.length==0 || _url.indexOf("?")==0)
        {
            Status.showInfo("obj is null please check");
            window.setTimeout("Status.setStatusShow(false)",3000);
            return;
        }
        if (this.showStatus)
        {
            Status.showInfo("数据正在处理中，请稍候......");  
        }  
        if (_chunnel==undefined || _chunnel=="")
        {
            _chunnel="default";
        }
        if (_asynchronous==undefined)
        {
            _asynchronous=true;
        }
        try{
          var instance=this.getInstance(_chunnel);
        }catch(e){
           alert(e.message);
        }
        if (instance==null)
        {
            Status.showInfo("explorer is not support ajax");
            window.setTimeout("Status.setStatusShow(false)",3000);
            return;
        }        
        if (typeof(_processRequest)=="function")
        {
            instance.onreadystatechange=function()
            {
                if (instance.readyState == 4)
                {
                    if (instance.status == 200)
                    {                        
                        _processRequest(instance);
                        Status.setStatusShow(false);
                        Request.showStatus=true;                                    
                    }
                    else
                    {
                        Status.showInfo("your request page have exception please check");
                        window.setTimeout("Status.setStatusShow(false)",3000);
                    }                    
                }                                
            }            
        }
        if (_url.indexOf("?")!=-1)
        {
            _url+="&requestTime="+(new Date()).getTime();
        }
        else
        {
            _url+="?requestTime="+(new Date()).getTime();
        }
        if (_data.length==0)
        {
            instance.open("POST",_url,_asynchronous);
            instance.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            if (_data==null||_data.length==0)
              instance.send(null);
            else
              instance.send(_data);
            
        }
        else
        {
            instance.open("POST",_url,_asynchronous);
            instance.setRequestHeader("Content-Length",_data.length);
            instance.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            instance.send(_data);
        }
    }    
    this.intervalSend=function(_interval,_url,_processRequest,_chunnel)
    {
        var action=function()
        {
            if (_chunnel==undefined)
            {
                _chunnel="defaultInterval";
            }
            var instance=Request.getInstance(_chunnel);
            if (instance==null)
            {
                Status.showInfo("当前浏览器不支持ajax,请检查")
                window.setTimeout("Status.setStatusShow(false)",3000);
                return;
            }
            if (typeof(_processRequest)=="function")
            {
                instance.onreadystatechange=function()
                {
                    if (instance.readyState == 4)
                    {
                        if (instance.status == 200)
                        {
                            _processRequest(instance);
                        }
                        else
                        {
                            Status.showInfo("当前请求页面有异常，请检查。。。。。。。。。。");
                            window.setTimeout("Status.setStatusShow(false)",3000);
                        }
                    }
                }
            }
            if (_url.indexOf("?")!=-1)
            {
                _url+="&requestTime="+(new Date()).getTime();
            }
            else
            {
                _url+="?requestTime="+(new Date()).getTime();
            }
            instance.open("GET",_url,true);
            instance.send(null);
        }
        window.setInterval(action,_interval);        
    }
}