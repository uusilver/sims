/**
 * Created with JetBrains WebStorm.
 * User: vali
 * Date: 14-11-4
 * Time: 下午9:50
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function () {
	 $.get('/sims/mss/html/locationController.do?method=queryAllRegion',function(data){
		 var dataObj = $.parseJSON(data);
		 var html = '';
		 for(var index=0;index<dataObj.length;index++){
			 html+='<tr><td class="idseq qinggoudan_table_td1">'+dataObj[index].regionid+'</td><td class="change qinggoudan_table_td1">'+dataObj[index].regionname+'</td><td class="qinggoudan_table_td1"><button class="btn btn-primary">保存</button><button class="btn btn-danger">删除</button></td></tr>';
		 }
		 $("tbody").html(html);
	 });

	 
    //Create new
    $("#create").click(function(){
        var  html ='<tr><td class="idseq qinggoudan_table_td1"></td><td class="change qinggoudan_table_td1">请输入</td><td class="qinggoudan_table_td1"><button class="btn btn-primary">保存</button><button class="btn btn-danger">删除</button></td></tr>';
        $("tbody").append(html);
    });

    //update
     $(".btn.btn-primary").live('click',function(){
    	 var id = $(this).parent().parent().find(".idseq").text();
         var obj = $(this).parent().parent().find(".change").text();
         if(!obj){
        	 alert("请按回车保存");
        	 return false;
         }
         saveObj(obj,id);
     });
    //delete
     $(".btn.btn-danger").live('click',function(){
    	 var obj = $(this).parent().parent().find(".change").text();
         deleteObj(obj);
         $(this).parent().parent().remove();
     });

    //绑定change事件
    $("tbody td.change").live('click',function(){
        //找到当前鼠标点击的td,this对应的就是响应了click的那个td
        var tdObj = $(this);
        if (tdObj.children("input").length > 0) {
            //当前td中input，不执行click处理
            return false;
        }
        var text = tdObj.html();
        //清空td中的内容
        tdObj.html("");

        var inputObj = $("<input type='text'>").css("border-width","0")
            .css("font-size","16px")
            .css("background-color",tdObj.css("background-color"))
            .val(text).appendTo(tdObj);
        //是文本框插入之后就被选中
        inputObj.trigger("focus").trigger("select");
        inputObj.click(function() {
            return false;
        });
        //处理文本框上回车和esc按键的操作
        inputObj.keyup(function(event){
            //获取当前按下键盘的键值
            var keycode = event.which;
            //处理回车的情况
            if (keycode == 13) {
                //获取当当前文本框中的内容
                var inputtext = $(this).val();
                //将td的内容修改成文本框中的内容
                tdObj.html(inputtext);
            }
            //处理esc的情况
            if (keycode == 27) {
                //将td中的内容还原成text
                tdObj.html(text);
            }
        });
    }) ;





});

function saveObj(obj, id){
	if(id)
	{
		var obj = JSON.stringify(id+":"+obj);
		$.post('/sims/mss/html/locationController.do?method=updateRegion',{obj:obj},function(data){
		    if(data=='success'){
		    	 alert("操作成功");
	        }else{
	        	alert("操作失败!");
	        }
	    });
	}else{
		var obj = JSON.stringify(obj);
		$.post('/sims/mss/html/locationController.do?method=saveRegion',{obj:obj},function(data){
		    if(data=='success'){
		    	 alert("操作成功");
	        }else{
	        	alert("操作失败!");
	        }
	    });
		
	}
	
}

function deleteObj(obj){
	
	var obj = JSON.stringify(obj);
	$.post('/sims/mss/html/locationController.do?method=deleteRegion',{obj:obj},function(data){
	    if(data=='success'){
	    	 alert("操作成功");
        }else{
        	alert("操作失败!");
        }
 });
}

