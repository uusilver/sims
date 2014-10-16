
var dyn_HideElementTemp = new Array();

function MM_hideElementAll(obj,HideElementTemp)
{
//  MM_HideElement("IMG",obj,HideElementTemp);
  MM_HideElement("SELECT",obj,HideElementTemp);
//  MM_HideElement("OBJECT",obj,HideElementTemp);
//  MM_HideElement("IFRAME",obj,HideElementTemp);
}

function MM_HideElement(strElementTagName,obj,HideElementTemp)
{
  //try
  {
    var showDivElement = obj;
    var calendarDiv = obj;
    var intDivLeft = MM_GetOffsetLeft(showDivElement);
    var intDivTop = MM_GetOffsetTop(showDivElement);

    for(i=0;i<window.document.all.tags(strElementTagName).length; i++)
    {
      var objTemp = window.document.all.tags(strElementTagName)[i];
      if(!objTemp||!objTemp.offsetParent)
        continue;
      var intObjLeft=MM_GetOffsetLeft(objTemp);
      var intObjTop=MM_GetOffsetTop(objTemp);
      if(((intObjLeft+objTemp.clientWidth)>intDivLeft)&&
        (intObjLeft<intDivLeft+calendarDiv.style.posWidth)&&
        (intObjTop+objTemp.clientHeight>intDivTop)&&
        (intObjTop<intDivTop+calendarDiv.style.posHeight)&&
        (objTemp.style.visibility!="hidden"))
      {
        HideElementTemp[HideElementTemp.length]=objTemp
        objTemp.style.visibility="hidden";
      }
    }
  }
  //catch(e)
  {
    //alert(e.message)
  }
}

function MM_ShowElement(HideElementTemp)
{
  var i;
  for(i=0;i<HideElementTemp.length; i++)
  {
    var objTemp = HideElementTemp[i]
    if(!objTemp||!objTemp.offsetParent)
      continue;
    objTemp.style.visibility='';
  }
  HideElementTemp=new Array();
}

function MM_GetOffsetLeft(src)
{
  var set=0;
  if(src)
  {
    if (src.offsetParent)
    {
           set+=src.offsetLeft+MM_GetOffsetLeft(src.offsetParent);
    }
    if(src.tagName.toUpperCase()!="BODY")
    {
      var x=parseInt(src.scrollLeft,10);
      if(!isNaN(x))
        set-=x;
    }
  }
    return set;
}

function MM_GetOffsetTop(src)
{
  var set=0;
  if(src)
  {
    if (src.offsetParent)
    {
      set+=src.offsetTop+MM_GetOffsetTop(src.offsetParent);
    }
    if(src.tagName.toUpperCase()!="BODY")
    {
      var y=parseInt(src.scrollTop,10);
      if(!isNaN(y))
        set-=y;
    }
  }
    return set;
}



function MM_openBrWindow(theURL,winName,features) { //v2.0
  var tempWindow = window.open(theURL,winName,features);
  tempWindow.focus();
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }

	if(args[2] == "show")
		MM_hideElementAll(document.getElementById(args[0]),dyn_HideElementTemp);
	else
		MM_ShowElement(dyn_HideElementTemp);
}

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
/*
 * 弹出对话框（showModalDialog）






 * v 1.0
 * 功能说明
 * @param1 : url连接
 * @param2 : 窗口宽度
 * @param3 : 窗口高度
 * @param4 : 窗口位置 0 -- 屏幕中央 ，其他 -- 事件附近
 */
function openDialog(WINurl,WINwidth,WINheight,xyPosition)
{
 if(xyPosition==0)//屏幕中央
   {
    showx = (window.screen.availWidth  - WINwidth)/2;
    showy = (window.screen.availHeight - WINheight)/2;
   }
 else//事件附近
   {
     showx = event.screenX - event.offsetX - 4 - WINwidth ; // + deltaX;
     showy = event.screenY - event.offsetY + 18; // + deltaY;
    }
  newWINwidth = WINwidth + 4 + 18;
  var features =
    'dialogWidth:'  + newWINwidth  + 'px;' +
    'dialogHeight:' + WINheight + 'px;' +
    'dialogLeft:'   + showx     + 'px;' +
    'dialogTop:'    + showy     + 'px;' +
    'directories:no; localtion:no; menubar:no; status=no; toolbar=no;scrollbars:yes;Resizeable=no'+
    '';
  var vDialog = window.showModalDialog(WINurl, " ", features);
  return vDialog;
}


/*
 * 弹出对话框（window.open）






 * v 1.0
 * 功能说明
 * @param : url连接
 * @param : name
 * @param : 窗口宽度
 * @param : 窗口高度
 * @param : 窗口位置 0 -- 屏幕中央 ，其他 -- 事件附近
 */
function openWindow(WINurl,WINname,WINwidth,WINheight,xyPosition)
{
 if(xyPosition==0)//屏幕中央
   {
    showx = (window.screen.availWidth  - WINwidth)/2;
    showy = (window.screen.availHeight - WINheight)/2;
   }
 else//事件附近
   {
     showx = event.screenX - event.offsetX - 4 - WINwidth ; // + deltaX;
     showy = event.screenY - event.offsetY + 18; // + deltaY;
    }
  newWINwidth = WINwidth + 4 + 18;
  var features =
    'width='  + newWINwidth  + 'px; ' +
    'height=' + WINheight + 'px; ' +
    'left='   + showx     + 'px; ' +
    'top='    + showy     + 'px; ' +
    'scrollbars=1; resizable=1;'+
    '';
  //alert(features);
  var vDialog = window.open(WINurl, WINname, features);
  return vDialog;
}

/*
 * 检查字符串是否是整型





 * v 1.0
 * 功能说明
 * @param1 : 待检查字串





 * return  :
 */
function checkInteger(str)
{
  var i;
  var len = str.length;
  var chkStr = "1234567890";
  if(parseInt(str)==0)return false;
  if (len == 1)
      if (chkStr.indexOf(str.charAt(0)) < 0 || (str.charAt(0) == '0')) return false;
  else
      if ((chkStr.indexOf(str.charAt(0)) < 0))  return false;
  for (i = 1; i < len; i++)
      {
       if (chkStr.indexOf(str.charAt(i)) < 0) return false;
      }
  return true;
}
/*
 * 检查字符串是否是浮点数
 * v 1.0
 * 功能说明
 * @param1 : 待检查字串




 * return  :
 */
function checkFloat(str)
{
  var sNumSets="1234567890.";
  var len=0,cB=0,cE=0;
  var c,c1,c2;
  var i;
  //str=trim(""+str);
  len=str.length;
  if(len==0)return false;
  //开头和结尾不允许出现'.'
  c1=str.charAt(0);
  c2=str.charAt(len-1);
  if((c1=='.')||(c2=='.'))return false;
  //开头不允许出现0
  //if(c1 == '0'&&len==1)return false;
  //不允许出现两个'.'
  cB=str.indexOf('.');
  cE=str.lastIndexOf('.');
  if(cB!=cE)return false;
  //所有字符必须在sNumSets内




  for(i=0;i<len;i++)
     {
      c=str.charAt(i);
      if(sNumSets.indexOf(c)<0)	return false;
     }
  return true;
}


function onlyNum()
{
  if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)||event.keyCode==8||event.keyCode==13))
    event.returnValue=false;
}

/**
 * 设置form中的所有元素为disabled
 */
function setFormElementDisabled(frmObj,value)
{
 if(typeof(frmObj)!='object') {alert(frmObj + "不是对象!");return false;}
 for( i=0;i<frmObj.elements.length;i++)
    {
     if(value==null)
        frmObj.elements(i).disabled = 'true';
     else
        frmObj.elements(i).disabled = value;
    }
 //return s;
}


/**
 * 返回form中的所有元素信息


 */
function getFormElementString(frmObj)
{
 var s = '' ;
 if(typeof(frmObj)!='object') {alert(frmObj + "不是对象!");return false;}
 for( i=0;i<frmObj.elements.length;i++)
    {
     var name = frmObj.elements(i).name;
     var value = frmObj.elements(i).value;
     s += name+" = ["+value+"];\n";
    }
 return s;
}

/**
 * 返回checkbox中选中的项的值以","连接的字符串
 */
function getCheckboxCheckedValue(checkboxName)
{
  var vtmp = "";
  var obj = document.getElementsByName(checkboxName);
  if(obj!=null)
  {
    for (var i=0;i<obj.length;i++)
    {
      if(obj.item(i).checked == true)
      {
        vtmp += obj.item(i).value+",";
      }
    }
    if (vtmp.length>0 )
    {
     vtmp = vtmp.substring(0,vtmp.length-1);
     return vtmp;
    }
  }
}
