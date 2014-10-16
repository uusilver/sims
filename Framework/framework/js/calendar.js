
/**
 * 删除字符串左右的空格和Tab字符
 */
function dtp_trimAll$(src)
{
  if(src == null)
    return "";

  src = "" + src;
  for(var i=0;i<src.length;i++)
  {
    if(src.charAt(i) != " " && src.charAt(i) != "\t")
      break;
  }

  for(var j=src.length - 1;j>=0;j--)
  {
    if(src.charAt(j) != " " && src.charAt(j) != "\t")
      break;
  }

  if(i > j)
    return "";

  return src.substring(i,j + 1);
}

/*
 *取得字符串src右边n个字符


 */
function dtp_right$(src,n)
{
  if(src == null || n <= 0)
    return "";
  else if(src.length < n)
    return src;
  else
    return src.substring(src.length-n,src.length);
}

/**
  * 类似于parseInt,但解决了数值前有前导了0的问题

  */
function dtp_parseInt$(text)
{
	var idx = -1;
	for(var i=0;i<text.length;i++)
	{
		if(text.charAt(i) != '0')
		{
			idx = i;
			break;
		}
	}
	if(idx == -1)
		return 0;
	else
		return parseInt(text.substring(idx));
}

/*
 *显示日期时间选择框
 */
function dtp_showpicker(obj,format,canempty,readonly,ubound,lbound,relubound,rellbound,relpos,countDateTargetIndex,countDateTarget,relativePath)
{
  var url = relativePath+"framework/jsp/calendar.jsp?";
  url += "format=" + format;
  url += "&value=" + obj.value;
  url += "&canempty=" + (canempty?"1":"0");
  url += "&readonly=" + (readonly?"1":"0");
  url += "&ubound=" + ubound;
  url += "&lbound=" + lbound;
  url += "&relubound=" + relubound;
  url += "&rellbound=" + rellbound;
  var left,top;
  if(relpos)
  {
    var e = obj;
    left = window.screenLeft;
    top = window.screenTop + e.clientHeight + 6;
    while(e != null)
    {
    left += e.offsetLeft;
    top += e.offsetTop;
    e = e.offsetParent;
    }
  }

  var height;
  if(format.indexOf('H') != -1 || format.indexOf('m') != -1 || format.indexOf('s') != -1)
    height = 270;
  else
    height = 230;
  var ret = window.showModalDialog(url,obj,"dialogWidth:290px;dialogHeight:" + height + "px;dialogLeft:" + left + "px;dialogTop:" + top + "px;status:no;help:yes;");
  if(ret == null)
    return;
  else if(ret == "blank")
    obj.value = "";
  else{
  	if(countDateTarget!=null && countDateTarget!="null" && countDateTarget!=""){
  		var selectedDate;
  		if((format.indexOf('-')!=-1 || format.indexOf('/')!=-1 || format.indexOf(' ')!=-1) && ret.length>=10){
  			selectedDate = new Date(ret.substring(0,4) + "/" + ret.substring(5,7) + "/" + ret.substring(8,10));
  		}else if(ret.length>=8){
  			selectedDate = new Date(ret.substring(0,4) + "/" + ret.substring(4,6) + "/" + ret.substring(6,8));
  		}
	    var curDate = new Date(new Date().getYear() + "/" + (new Date().getMonth()+1) + "/" + new Date().getDate());
	    var disDate = (selectedDate-curDate)/(1000*3600*24);
	    
	    //alert(obj.parentNode.parentNode.parentNode.rowIndex);
	    
	    //alert(document.getElementsByName(countDateTarget)[obj.parentNode.parentNode.parentNode.rowIndex-1]);
	    
	    if(document.getElementsByName(countDateTarget)!=null && document.getElementsByName(countDateTarget).length>0){
			if((countDateTargetIndex=="" || countDateTargetIndex==null || countDateTargetIndex=="null")
				&& document.getElementsByName(countDateTarget)[obj.parentNode.parentNode.parentNode.rowIndex-1]!=null){
				document.getElementsByName(countDateTarget)[obj.parentNode.parentNode.parentNode.rowIndex-1].value = disDate;
			}else{
				document.getElementsByName(countDateTarget)[countDateTargetIndex].value = disDate;
			}
		}
  	}
    obj.value = ret;
  }
}

/*
 *用指定的格式格式化日期



 *参数原型1:year,month,day,format
 *参数原型2:date,format
 */
function dtp_formatDate$()
{
  var args = arguments;
  var format,year,month,day;
  //参数原型1:year,month,day,format
  if(args.length == 4)
  {
    format = args[3];
    year = args[0];
    month = args[1];
    day = args[2];
  }
  //参数原型2:date,format
  else if(args.length == 2)
  {
    format = args[1];
    year = args[0].getFullYear();
    month = args[0].getMonth() + 1;
    day = args[0].getDate();
  }
  //错误的参数



  else
    throw "formatDate$方法中接收到错误的参数个数";

  var text = format;
  text = text.replace("yyyy",year);
  text = text.replace("yy",year%100);
  text = text.replace("MM",dtp_right$("0" + month,2));
  text = text.replace("M",month);
  text = text.replace("dd",dtp_right$("0" + day,2));
  text = text.replace("d",day);
  return text;
}

/*
 *用指定的格式格式化时间



 *参数原型1:hour,minute,second,format
 *参数原型2:date,format
 */
function dtp_formatTime$()
{
  var args = arguments;
  var format,hour,minute,second;
  //参数原型1:hour,minute,second,format
  if(args.length == 4)
  {
    format = args[3];
    hour = args[0];
    minute = args[1];
    second = args[2];
  }
  //参数原型2:date,format
  else if(args.length == 2)
  {
    format = args[1];
    hour = args[0].getHours();
    minute = args[0].getMinutes();
    second = args[0].getSeconds();
  }
  //错误的参数



  else
    throw "formatTime$方法中接收到错误的参数个数";

  var text = format;
  text = text.replace("HH",dtp_right$("0" + hour,2));
  text = text.replace("H",hour);
  text = text.replace("mm",dtp_right$("0" + minute,2));
  text = text.replace("m",minute);
  text = text.replace("ss",dtp_right$("0" + second,2));
  text = text.replace("s",second);
  return text;
}

/*
 *用指定的格式格式化日期时间



 *参数原型1:year,month,day,hour,minute,second,format
 *参数原型2:date,format
 */
function dtp_formatDateTime$()
{
  var args = arguments;
  var format,year,month,day,hour,minute,second;
  //参数原型1:year,month,day,hour,minute,second,format
  if(args.length == 7)
  {
    format = args[6];
    year = args[0];
    month = args[1];
    day = args[2];
    hour = args[3];
    minute = args[4];
    second = args[5];
  }
  //参数原型2:date,format
  else if(args.length == 2)
  {
    format = args[1];
    year = args[0].getFullYear();
    month = args[0].getMonth() + 1;
    day = args[0].getDate();
    hour = args[0].getHours();
    minute = args[0].getMinutes();
    second = args[0].getSeconds();
  }
  //错误的参数



  else
    throw "formatDate$方法中接收到错误的参数个数";

  var text = format;
  text = dtp_formatDate$(year,month,day,format);
  text = dtp_formatTime$(hour,minute,second,text);
  
  return text;
}

/**
 * 检查用户输入的时间是否合法
 */
function checkUserInputTime(val)
{
  var re = /^(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
  var arr = re.exec(val);
  if(arr == null)
  {
    alert("时间\"" + val + "\"格式不合法,请确认为\"时:分:秒\"的格式");
    return false;
  }

  var hour = dtp_parseInt$(arr[1]);
  var minute = dtp_parseInt$(arr[2]);
  var second = dtp_parseInt$(arr[3]);
  if(hour > 23 || minute > 59 || second > 59)
  {
    alert("时间\"" + val + "\"无效,请确认时、分、秒值的范围在0-59之间");
    return false;
  }

  return true;
}
