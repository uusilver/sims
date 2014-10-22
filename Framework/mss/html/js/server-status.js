/*
    @Author: Junying.Li
    @Date:   2014-10-16
    @@Desc:  For index.html
 */
$(function() {

    //***************************start to loading server list info*****************************************************************
	

    //***************************end of loading server list info*******************************************************************


    //Add dialog show server msg
    $( "#dialog" ).dialog({
        autoOpen: false,
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

    //dialog shows server status info
    $( "#pop" ).dialog({
        autoOpen: false,
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
    //Click
    $( "#dialog-msg" ).click(function( event ) {
        $( "#pop" ).dialog( "open" );
        event.preventDefault();
    });

});//end of function
//

