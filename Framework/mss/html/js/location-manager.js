/**
 * Created with JetBrains WebStorm.
 * User: vali
 * Date: 14-10-19
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
//function demo_create() {
//    window.showModalDialog("region-manager.html");
//};
function demo_rename() {
    var ref = $('#jstree_demo').jstree(true),
        sel = ref.get_selected();
    if(!sel.length) { alert("未选中!"); return false; }
    sel = sel[0];
    ref.edit(sel);
};
function demo_delete() {
    var ref = $('#jstree_demo').jstree(true),
        sel = ref.get_selected();
    if(!sel.length) { alert("未选中!"); return false; }
    
    var param = JSON.stringify(sel);
    $.post('/sims/mss/html/locationController.do?method=deleteLocationInfo',{param:param},function(data){
	    if(data=='success'){
	    	  ref.delete_node(sel);
        }else{
        	alert("删除失败!");
        }
 });
    
};
$(function () {
    //Open model dialog
    // make instance
    api = $.fn.MultiDialog();
    api.options.dialog.enabled = true;
    api.options.dialog.width="400";
    api.options.dialog.height="300";
    $("#open_region").click(function(){
        api.options.dialog.title = "域信息管理";
        api.openLink({
            href: "region-manager.html",
            type: "iframe"
        });
    });

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
        //alert(data);
        $('#jstree_demo')
        .jstree({
            "core" : {
                "animation" : 0,
                "check_callback" : true,
                "themes" : { "stripes" : true },
                'data' : eval('(' + data + ')')
            },
            "types" : {
                "#" : { "max_children" : 1, "max_depth" : 4, "valid_children" : ["root"] },
                "root" : { "icon" : "/static/3.0.6/assets/images/tree_icon.png", "valid_children" : ["default"] },
                "default" : { "valid_children" : ["default","file"] },
                "file" : { "icon" : "glyphicon glyphicon-file", "valid_children" : [] }
            },
            "plugins" : [ "contextmenu", "dnd", "search", "state", "types", "wholerow" ]
        });
    });
   

    //Click save button
    $("#saveInfo").click(function(){
       var data =  $('#jstree_demo').jstree(true).get_json('#', { 'flat': false,'no_state':true,'no_id':true,'no_data':true });
       var flag = false;
       for(var i=0;i<data.length;i++){
          //first level is the root
          var countryObj = data[i].children;
          flag = findDulInJSONObj(countryObj);
          if(flag){
	          for(var j=0;j<countryObj.length;j++){
	             var provinceObj = countryObj[j].children;
	             flag = findDulInJSONObj(provinceObj);
	             if(flag){
	            	 for(var k=0;k<provinceObj.length;k++){
	            		 var cityObj = provinceObj[k].children;
	            		 flag = findDulInJSONObj(cityObj);
	            		 if(!flag){
	            			 return false;
	            		 }
	            	 }
	             }else{
	            	 return false;
	             }
	          }
          }else{
        	  return false;
          }
       }
       if(flag){
	       var result = JSON.stringify(data);
	       //alert(result);
	       
	       $.post('/sims/mss/html/locationController.do?method=saveLocationInfo',{result:result},function(data){
	    	      if(data=='success'){
	                  alert('保存成功!');
	                  window.location.reload();
	              }else{
	            	  alert('保存失败');
	              }
	       });
       }
    });
});

function findDulInJSONObj(obj){
      for(var i=0;i<obj.length;i++){
              var pStr = obj[i].text;
              var counter = 0;
             for(var j=0;j<obj.length;j++){
                 if (pStr==obj[j].text){
                     counter+=1;
                 }
                 if(counter>1){
                     alert(pStr+"重复，请检查");
                     return false;
                 }
             }
      }
      return true;
}
