//radio按钮事件
function radioCheck(radioValue){
	var businsRadio = document.getElementsByName("radioValue")[0];
	var departRadio = document.getElementsByName("radioValue")[1];
	if(radioValue=='busins'){
		businsRadio.checked=true;
		departRadio.checked=false;
		document.getElementsByName("qarea")[0].disabled=false;
		document.getElementsByName("qbus")[0].disabled=true;
		document.getElementsByName("qbusFlag")[0].disabled=false;
		if(document.getElementsByName("qbusFlag")[0].checked){
			document.getElementsByName("qbus")[0].disabled=false;
		}else{
			document.getElementsByName("qbus")[0].disabled=true;
		}
		document.getElementsByName("qbusType")[0].disabled=false;
		document.getElementsByName("departName")[0].disabled=true;
	}
	else if(radioValue=='depart'){
		businsRadio.checked=false;
		departRadio.checked=true;
		document.getElementsByName("qarea")[0].value="";
		document.getElementsByName("qbus")[0].value="";
		var busOptions = document.getElementsByName("qbus")[0].options;
		if(busOptions.length>1){
			for(var i=1;i<busOptions.length;i++){
				busOptions.remove(i);
				i--;
			}
		}
		document.getElementsByName("qbusType")[0].value="";
		document.getElementsByName("qbusType")[0].disabled=true;
		document.getElementsByName("qarea")[0].disabled=true;
		document.getElementsByName("qbus")[0].disabled=true;
		document.getElementsByName("qbusFlag")[0].checked = false;
		document.getElementsByName("qbusFlag")[0].disabled=true;
		document.getElementsByName("departName")[0].disabled=false;
	}
}

//查询
function query(){
	var qstartDate = document.orderStaticForm.qstartDate.value;
	var qendDate = document.orderStaticForm.qendDate.value;
	var passed = "0";
	if(qstartDate!="" && qendDate!="" && parseInt(qstartDate) > parseInt(qendDate)){
		alert("起始日期不能大于截至日期，请修改！");
		passed = "1";
		return;
	}
	if(passed == "0"){ 
		/**if(document.getElementsByName("qarea")[0].value=="" && document.getElementsByName("qbus")[0].value==""){
			document.getElementsByName("qbusFlag")[0].checked = false;
		}**/
		document.orderStaticForm.action="/mpms/mpms/jsp/orderInfo/mpmsBusOrderController.do?method=staticOrder";
		document.orderStaticForm.submit();
	}
}

//选择营业厅
function selectBusinessRoom(){
	if(document.getElementsByName("qbusFlag")[0].checked == true){
		/**if(document.getElementsByName("qarea")[0].value == ""){
			alert("请选择区域/县公司！");
			document.getElementsByName("qbusFlag")[0].checked = false;
			return;
		}else {**/
			document.getElementsByName("qbus")[0].disabled = false;
			document.getElementsByName("qbus")[0].value = "";
		//}
	}else {
		document.getElementsByName("qbus")[0].disabled = true;
		document.getElementsByName("qbus")[0].value = "";
	}
}

//选择物料类别
function selectMateType(){
	if(document.getElementsByName("qmateTypeFlag")[0].checked == true){
		document.getElementsByName("qmateType")[0].disabled = false;
		document.getElementsByName("qmateType")[0].value = "";
	}else {
		document.getElementsByName("qmateType")[0].disabled = true;
		document.getElementsByName("qmateType")[0].value = "";
	}
}

//选择物料
function selectMateId(){
	if(document.getElementsByName("qmateIdFlag")[0].checked == true){
		document.getElementsByName("qmateId")[0].disabled = false;
		document.getElementsByName("qmateId")[0].value = "";
	}else {
		document.getElementsByName("qmateId")[0].disabled = true;
		document.getElementsByName("qmateId")[0].value = "";
	}
}

//计算预算
function count(){
	var use = document.getElementsByName("useTotal");
	var budget = document.getElementsByName("budgetTotal");
	var budgfetTime = document.getElementsByName("budgfetTime");
	var lastBudgfetTime = "";
	var useTotal = 0;
	var budgetTotal = 0;
	for(var i=0; i<use.length; i++){
		if(i==0 && budgfetTime!=null && budgfetTime.length>0){
			lastBudgfetTime = budgfetTime[0].value;
		}
		useTotal = forRound((parseFloat(useTotal) + parseFloat(use[i].value)),"2");
		if(budget!=null && budget.length>0){
			if(budgfetTime!=null && budgfetTime.length>0 && lastBudgfetTime.substring(0,6)!=budgfetTime[i].value.substring(0,6)){
				budgetTotal = forRound((parseFloat(budgetTotal) + parseFloat(budget[i].value)),"2");
				lastBudgfetTime = budgfetTime[i].value;
				if(i==(use.length-1)){
					budgetTotal = forRound((parseFloat(budgetTotal) + parseFloat(budget[i].value)),"2");
				}
			}
		}
	}		
	document.getElementById("usetotalspan").innerHTML = forRound(useTotal,"2");
	document.getElementsByName("exTotal")[0].value = forRound(useTotal,"2");
	if(document.getElementById("budgettotalspan")!=null){
		document.getElementById("budgettotalspan").innerHTML = forRound(budgetTotal,"2");
		document.getElementsByName("exTotal2")[0].value = forRound(budgetTotal,"2");
	}
}

//计算物料
function countMate(){
	var mateCount = document.getElementsByName("mateCount");
	var totalPrice = document.getElementsByName("totalPrice");
	var countTotal = 0;
	var priceTotal = 0.00;
	for(var i=0; i<mateCount.length; i++){
		countTotal = parseInt(countTotal) + parseInt(mateCount[i].value);
		priceTotal = forRound((parseFloat(priceTotal) + parseFloat(totalPrice[i].value)),"2");
	}		
	document.getElementById("countTotalspan").innerHTML = countTotal;
	document.getElementsByName("exTotal")[0].value = countTotal;
	document.getElementById("priceTotalspan").innerHTML = forRound(priceTotal,"2");
	document.getElementsByName("exTotal2")[0].value = forRound(priceTotal,"2");
}

//导出到excel
function exportToExcel(){
	if(document.getElementsByName("exValue")!=null && document.getElementsByName("exValue").length>0){
		document.orderStaticForm.action="/mpms/mpms/jsp/orderInfo/mpmsBusOrderController.do?method=exportToExcel";
		document.orderStaticForm.submit();
	}else{
		alert("没有数据可以导出！");
	}
}

//查看订单信息
function viewOrder(mateType,mate,areaId,orgId){
	var url = "/mpms/mpms/jsp/orderInfo/mpmsBusOrderController.do?method=viewStaticOrder" 
		+ constructParams("qbusFlag,qbusName,qbusType,radioValue,departName,qfactor,,qstartDate,qendDate,qmateType,qmateId,qmateTypeFlag,qmateIdFlag,currentPage");
	if(mateType==null || mateType==''){
		url += constructParams("qmateType");
	}else{
		url += "&qmateType=" + mateType;
	}
	if(mate==null || mate==''){
		url += constructParams("qmateId");
	}else{
		url += "&qmateId=" + mate;
	}
	if(areaId==null || areaId==''){
		url += constructParams("qarea");
	}else{
		url += "&qarea=" + areaId;
	}
	if(orgId==null || orgId==''){
		url += constructParams("qbus");
	}else{
		url += "&qbus=" + orgId;
	}
	window.open(url,'','toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=600,top=100,left=100');
}