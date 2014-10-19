/**
 * Created with JetBrains WebStorm.
 * User: vali
 * Date: 14-10-19
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
function demo_create() {
    var ref = $('#jstree_demo').jstree(true),
        sel = ref.get_selected();
    if(!sel.length) { return false; }
    sel = sel[0];
    sel = ref.create_node(sel, {"type":"file"});
    if(sel) {
        ref.edit(sel);
    }
};
function demo_rename() {
    var ref = $('#jstree_demo').jstree(true),
        sel = ref.get_selected();
    if(!sel.length) { return false; }
    sel = sel[0];
    ref.edit(sel);
};
function demo_delete() {
    var ref = $('#jstree_demo').jstree(true),
        sel = ref.get_selected();
    if(!sel.length) { return false; }
    ref.delete_node(sel);
};
$(function () {
    var to = false;
    $('#demo_q').keyup(function () {
        if(to) { clearTimeout(to); }
        to = setTimeout(function () {
            var v = $('#demo_q').val();
            $('#jstree_demo').jstree(true).search(v);
        }, 250);
    });
    //Loading information from server
    $.get('/sims/mss/html/locationController.do?method=queryLocationInfo',function(data){
        alert(data);
    });
    $('#jstree_demo')
        .jstree({
            "core" : {
                "animation" : 0,
                "check_callback" : true,
                "themes" : { "stripes" : true },
                'data' : [
                    {
                        'id': '0-0',
                        'text': '地址管理',
                        'children': [
                            {
                                id: '1-1',
                                'text': '中国',
                                'children': [
                                    {
                                        'id': '2-1',
                                        'text': "江苏",
                                        'children':[{'id':'3-1','text':'南京'},{'id':'3-2','text':'无锡'},{'id':'3-3','text':'镇江'}]
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            "types" : {
                "#" : { "max_children" : 1, "max_depth" : 4, "valid_children" : ["root"] },
                "root" : { "icon" : "/static/3.0.6/assets/images/tree_icon.png", "valid_children" : ["default"] },
                "default" : { "valid_children" : ["default","file"] },
                "file" : { "icon" : "glyphicon glyphicon-file", "valid_children" : [] }
            },
            "plugins" : [ "contextmenu", "dnd", "search", "state", "types", "wholerow" ]
        });

    //Click save button
    $("#saveInfo").click(function(){
       var data =  $('#jstree_demo').jstree('get_json');
       alert(JSON.stringify(data));
    });
});
