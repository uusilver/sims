/*
    @Author: Junying.Li
    @Date:   2014-10-16
    @@Desc:  For index.html
 */
$(function() {

    //***************************start to loading server list info*****************************************************************
	

    //***************************end of loading server list info*******************************************************************




    //dialog shows server status info
    $( "#pop" ).dialog({
        autoOpen: false,
        width: 1100,
        height:600

        /*buttons: [
            {
                text: "关闭",
                click: function() {
                    $( this ).dialog( "close" );
                }
            }
        ]*/
    });


    //goes into sever detail info page

    $("div[name='citybox']").click(function(){

        //clean pop box content
        $("#server_green").html();
        $("#server_warning").html();
        $("#server_gray").html();

        //Get City Code fromid
        var cityCode= $(this).attr("id");
        $( "#pop" ).dialog( "open" );
        event.preventDefault();
        var greenHtml = '';
        greenHtml += "<div class='main'>"
        greenHtml += "<div class='buttons'><img title='美国服务器1' src='image/server-green.gif' width='70' height='70'/></div>";
        greenHtml += "<div class='buttons'><img title='美国服务器1' src='image/server-green.gif' width='70' height='70'/></div>";
        greenHtml += "</div>"
        var warningHtml ='';
        warningHtml += "<div class='main'>"
        warningHtml += "<div class='buttons'><img title='美国服务器1' src='image/server-warning.gif' width='70' height='70'/></div>";
        warningHtml += "</div>"

        var grayHtml ='';
        grayHtml += "<div class='main'>"
        grayHtml += "<div class='buttons'><img title='美国服务器1' src='image/server-gray.gif' width='70' height='70'/></div>";
        grayHtml += "</div>"

        //append img on
        $("#server_green").html(greenHtml);
        $("#server_warning").html(warningHtml);
        $("#server_gray").html(grayHtml);

        //Dynamic value for iframe
        $("#orgChart").attr("src","server-org-chats.html?"+cityCode);
    }).tooltip();
});//end of function
//

