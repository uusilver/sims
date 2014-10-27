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
    refreshIFrame('server-org-chats.html?nj');
    loadServerInfo();
    //Bind click function to each images on the roll table
    $("img").live('click',function(){
        var cityCode = $(this).attr('src').split('/')[1].split('.')[0];
        //refresh iframe
        refreshIFrame('server-org-chats.html?'+cityCode);
        //add server info
        loadServerInfo();
    });

});

function loadServerInfo(){
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
}

function refreshIFrame(url){
    $("#orgChart").attr('src',url);
}