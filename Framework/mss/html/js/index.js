jQuery(document).ready(function($){
	//Hide all bg-line
	$(".net_a").hide();
	
    $( document ).tooltip();
    $('#carousel').carousel({width: 670,
        height: 350,
        itemWidth:220,
        horizontalRadius:260,
        verticalRadius:120,
        resize:false,
        mouseScroll:false,
        mouseDrag:true,
        scaleRatio:0.4,
        scrollbar:true,
        tooltip:true,
        mouseWheel:true,
        mouseWheelReverse:true});

      //Set server autoLoading message
      setInterval(loadServerMsg,3000);
      //set server status info
      loadServerStatus('南京市');
      //load gateway net work
      loadServerOrg('南京市');

      //Generate tooltip functions
      $( document ).tooltip({
         track: true
      });

      //click city image
      $(".carousel-item").live('click',function(){
          getCityName($(this).attr('src'));
          var cName =  getCityName($(this).attr('src'));
          loadServerStatus(cName);
          loadServerOrg(cName);
      });
});

function loadServerMsg(){
    //below codes are just for demo
    //Generate random number
    var times = parseInt(10*Math.random())
    var html = ''
    for(var index=0;index<times;index++){
        html += '服务器信息'+index+'<br/>';
    }
    $(".service_info_nav").html(html);
}

function showServerMsgWindow(){
    document.getElementById('service_info').style.display='none';
    document.getElementById('service_info_open').style.display='';
}

function  hideServerMsgWindow(){
    document.getElementById('service_info_open').style.display='none';
    document.getElementById('service_info').style.display='';
}

function loadServerStatus(cityName){
//     $(".service_a").html('<span>9</span>');
//     $(".service_b").html('<span>8</span>');
//     $(".service_c").html('<span>7</span>');
	
	$.post('/sims/mss/html/indexPageController.do?method=getServerInfoByCityName',{cityName:cityName},function(data){
	     
	     alert(data);
	     var value = data.split(",");
	     $(".service_a").html('<span>'+value[0]+'</span>');
	     $(".service_b").html('<span>'+value[1]+'</span>');
	     $(".service_c").html('<span>'+value[2]+'</span>');
 });

}

function loadServerOrg(cityName){
    
	$.post('/sims/mss/html/indexPageController.do?method=getServerOrgInfoByCityName',{cityName:cityName},function(data){
	     
	     alert(data);
	    
});
     

    

}

function getCityName(obj){
    var cityNames = ['南京市','徐州市','盐城市','镇江市','常州市','扬州市','南通市','连云港市','泰州市','淮安市','无锡市','苏州市','宿迁市','江苏省','省统筹'];
    var cityPYs = ['NANJING','XUZHOU','YANCHENG','ZHENJIANG','CHANGZHOU','YANGZHOU','NANTONG','LIANYUNGANG','TAIZHOU','HUAIAN','WUXI','SUZHOU','SUQIAN','JIANGSUSHENG','SHENGTONGCHOU'];

    var index = obj.replace("images/image",'').replace(".png",'');
    var cityName =  cityNames[parseInt(index)-1];
    var cityPY =  cityPYs[parseInt(index)-1];
    $(".top_text").html('<p class="a">'+cityName+'</p>'+'<p class="b">'+cityPY+'</p>');
    return cityName;
}