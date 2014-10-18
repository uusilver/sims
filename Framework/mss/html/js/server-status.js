/*
    @Author: Junying.Li
    @Date:   2014-10-16
    @@Desc:  For index.html
 */
var i=1;
var _interval;
var html='';
$(function() {
    //***************************start to loading server list info*****************************************************************
    var serverListIno='';
    serverListIno+='<div class =  "buttons"><img title="美国服务器10" src="images/server-green.gif" width="99" height="99"/></div>';
    $("#main").append(serverListIno);
    //***************************end of loading server list info*******************************************************************

    $(".buttons").tooltip();
    //Add tooltip
    $( "#dialog" ).dialog({
        autoOpen: true,
        width: 1100,
        buttons: [
            {
                text: "关闭",
                click: function() {
                    $( this ).dialog( "close" );
                }
            }
        ]
    });

    // Link to open the dialog
    $( "#dialog-link" ).click(function( event ) {
        $( "#dialog" ).dialog( "open" );
        event.preventDefault();
    });

    //auto msg box
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