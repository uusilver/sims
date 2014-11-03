/**
 * Created with JetBrains WebStorm.
 * User: vali
 * Date: 14-10-27
 * Time: 下午8:42
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function($){

    $('#carousel').carousel({
        width: 870,
        height: 350,
        itemWidth:120,
        horizontalRadius:270,
        verticalRadius:85,
        resize:false,
        mouseScroll:false,
        mouseDrag:true,
        scaleRatio:0.4,
        scrollbar:true,
        tooltip:true,
        mouseWheel:true,
        mouseWheelReverse:true
    });
    //init show nj
    loadOrgChart('nj');
    loadServerInfo();
    //Bind click function to each images on the roll table
    $(".carousel-item").live('click',function(){
            var cityCode = $(this).attr('src').split('/')[1].split('.')[0];
            //refresh iframe
            //alert(cityCode);
            //Clear div content
            $(".jOrgChart").remove();
            loadOrgChart(cityCode);
            //add server info
            loadServerInfo();
    });

});

function loadServerInfo(){
	//$.get('/sims/mss/html/mainPageInfoController.do?method=queryServerInfo',function(data){
		//alert(data);
	    var greenHtml = '';
	    greenHtml += ""
	    greenHtml += "<img title='美国服务器1' src='image/server-green.gif' width='35' height='35'/>";
	    greenHtml += "<img title='美国服务器1' src='image/server-green.gif' width='35' height='35'/>";
	    greenHtml += ""
	    var warningHtml ='';
	    warningHtml += ""
	    warningHtml += "<img title='美国服务器1' src='image/server-warning.gif' width='35' height='35'/>";
	    warningHtml += ""
	
	    var grayHtml ='';
	    grayHtml += ""
	    grayHtml += "<img title='美国服务器1' src='image/server-gray.gif' width='35' height='35'/>";
	    grayHtml += ""
	
	    //append img on
	    $("#server_green").html(greenHtml);
	    $("#server_warning").html(warningHtml);
	    $("#server_gray").html(grayHtml);


        //Load organization chart

	//});
}

function loadOrgChart(cityCode){
    //Init orgnization chars
    //$.get('/sims/mss/html/mainPageInfoController.do?method=queryTopologyInfo',function(data){
    //alert(data);
    var html = '';
    html+="<li>"+transCityCode(cityCode)+"<ul><li>组1<ul><li><img src='../html/images/user.jpg' /></li><li><img src='../html/images/user.jpg' /></li></ul></li><li>组2<ul><li><img src='../html/images/user-group.jpg' /></li></ul></li></ul><li>";
    $("#org").html(html);
    $("#org").jOrgChart({
        chartElement : '#chart',
        dragAndDrop  : true
    });
    //});
}

function transCityCode(cityCode){
    if(cityCode=='nj') return "南京";
    if(cityCode=='sz') return "苏州";
    if(cityCode=='xz') return "徐州";
    if(cityCode=='wx') return "无锡";
    if(cityCode=='cz') return "常州";
    if(cityCode=='sq') return "宿迁";
    if(cityCode=='ha') return "淮安";
    if(cityCode=='lyg') return "连云港";
    if(cityCode=='nt') return "南通";
    if(cityCode=='yc') return "盐城";
    if(cityCode=='tz') return "泰州";
    if(cityCode=='yz') return "扬州";
    if(cityCode=='st') return "省厅";
    if(cityCode=='stc') return "省统筹";
}

