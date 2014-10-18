/*
    @Author: Junying.Li
    @Date:   2014-10-16
    @@Desc:  For index.html
 */
var i=1;
var _interval;
var html='';
jQuery(document).ready(function() {
//    jQuery('#vmap').vectorMap({
//        map: 'world_en',
//        backgroundColor: '#333333',
//        color: '#ffffff',
//        hoverOpacity: 0.7,
//        selectedColor: '#666666',
//        enableZoom: true,
//        showTooltip: true,
//        values: sample_data,
//        scaleColors: ['#C8EEFF', '#006491'],
//        normalizeFunction: 'polynomial',
//        onRegionOver: function(element, code, region){
//            //alert(region);
//        },
//        onRegionClick:function(element,code,region){
//            jAlert('当前选择国家:'+region+" <br/>服务器数目为:3台<br/>可用:2台<br/>繁忙:1台<br/>不可用:1台");
//
//        }
//    }); //end of loading world map
    //fusion charts loading
//   

    showTime();
    _interval = setInterval("showTime()", 1000);

    //define close button
    $("#popClose").click(function(){
        closeMe($('#pop'));
    })

});//end of function
//
function showTime()
{
    if(html){
        html += "服务器"+(i-1)+"在被攻击<br/>";
    }else{
        html =  "<br/>";
    }
    $("#popIntro").html(html);
    $("#pop").show("slow");
    i++;
    if (i>10)
    {
        clearInterval(_interval);
        closeMe($('#pop'));
    }

}
//close window
function closeMe(obj){
    obj.hide();
}