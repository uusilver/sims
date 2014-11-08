jQuery(document).ready(function($){
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
      loadServerStatus();
      //load gateway net work
      loadServerOrg();

      //Generate tooltip functions
      $( document ).tooltip({
         track: true
      });

      //click city image
      $(".carousel-item").live('click',function(){
          getCityName($(this).attr('src'));
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

function loadServerStatus(){
     $(".service_a").html('<span>9</span>');
     $(".service_b").html('<span>8</span>');
     $(".service_c").html('<span>7</span>');

}

function loadServerOrg(){
    var html = '';
        html+='<div class="net_a" id="id1" style="top:-35px; left:-35px;" title="用户1,<br/>用户2"></div> ';
        html+='<div class="net_a" id="id2" style="top:-35px; left:115px;"></div> ';
        html+='<div class="net_a" id="id3" style="top:-35px; left:275px;"></div> ';
        html+='<div class="net_a" id="id4" style="top:85px; left:-35px;"></div>   ';
        html+='<div class="net_a" id="id5" style="top:85px; left:115px;"></div>  ';
        html+='<div class="net_a" id="id6" style="top:85px; left:275px;"></div>  ';
        html+='<div class="net_b" id="id7" style="top:210px; left:-35px;"></div>   ';
        html+='<div class="net_b" id="id8" style="top:210px; left:115px;"></div>  ';
        html+='<div class="net_b" id="id9" style="top:210px; left:275px;"></div> ';
     $(".line_bg").html(html);

//    $("#id1").hide();
//    $("#id2").hide();
//    $("#id3").hide();
////    $("#id4").hide();
////    $("#id6").hide();
//    $("#id7").hide();
//    $("#id8").hide();
//    $("#id9").hide();

}

function getCityName(obj){
    var cityNames = ['南京','徐州','盐城','镇江','常州','扬州','南通','连云港','泰州','淮安','无锡','苏州','宿迁','江苏省','省统筹'];
    var cityPY = ['NANJING','XUZHOU','YANCHENG','ZHENJIANG','CHANGZHOU','YANGZHOU','南通','连云港','泰州','淮安','无锡','苏州','宿迁','江苏省','省统筹'];

    var index = obj.replace("images/image",'').replace(".png",'');
    var cityName =  cityNames[parseInt(index)-1];
}