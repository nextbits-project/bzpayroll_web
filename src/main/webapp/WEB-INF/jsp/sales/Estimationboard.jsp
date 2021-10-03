<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/WEB-INF/jsp/include/headlogo.jsp"%>
<%@include file="/WEB-INF/jsp/include/header.jsp"%>
<%@include file="/WEB-INF/jsp/include/menu.jsp"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title><spring:message code="BzComposer.estimationboardtitle"/></title>
<style type="text/css">
.height250 {
height: 250px;

}
.fht-tbody{
height: 180px !important; /*  change table height*/
border-bottom: 1px solid rgb(207, 207, 207);
}
table.tabla-listados {
width: 100%;
border: 1px solid rgb(207, 207, 207);
margin: 0px 0px 0px 0px;
margin: 0px 0px 0px 0px;
}
table.tabla-listados tbody tr.odd td {
background: #e1e5e9;
}
</style>
<script type="text/javascript">
function updateInvoiceBoardDialog() {
	debugger;
    $("#updateInvoiceBoardDialog").dialog({
    	resizable: false,
        height: 200,
        width: 500,
        modal: true,
        buttons: {
            "<spring:message code='BzComposer.global.ok'/>": function () {
                $(this).dialog("close");
                //$('form').submit();
                debugger
                document.getElementById("modi").disabled=false;
        		document.forms['EstimationBoardForm'].action = "EstimationBoard?tabid=UpdateRecord";
        		document.forms['EstimationBoardForm'].submit();
            },
            <spring:message code='BzComposer.global.cancel'/>: function () {
                $(this).dialog("close");
                return false;
            }
        }
    });
    return false;
}
function showFirstNameLastNameDialog() {
	debugger;
    $("#showFirstNameLastNameDialog").dialog({
    	resizable: false,
        height: 200,
        width: 500,
        modal: true,
        buttons: {
            "<spring:message code='BzComposer.global.ok'/>": function () {
                $(this).dialog("close");
            }
        }
    });
    return false;
}
</script>
</head>
<body>
<!-- begin shared/header -->
<form:form name="EstimationBoardForm" id="EstimationBoardForm" action="EstimationBoard?tabid=ShowList" method="post" modelAttribute="estimationBoardDto">
<div id="">
<div id="ddcolortabsline">&nbsp;</div>
<div id="cos">
<div class="statusquo ok">
<div id="hoja">
<div id="blanquito">
<div id="padding">
	<div>
		<span style="font-size: 1.2em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px; border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
			<spring:message code="BzComposer.estimationboard.estimationboard" />
		</span>
	</div>
	<div>
		<div id="table-negotiations">
			<table cellspacing="0" align="center" class="section-border" style="width: 100%;">
				<tr>
					<td style="width:30%;padding:10px;">
						<table style="width: 100%;font-size:14px;border-right:2px solid #dddddd;" cellpadding="5">
							<tr>
								<th colspan="2">
									<spring:message code="BzComposer.estimationboard.filteroption" />
								</th>
							</tr>
							<tr>
								<td colspan="2"><spring:message code="BzComposer.salesorderboard.daterange" /></td>
							</tr>
							<tr>
								<td width="50%">
									<spring:message code="BzComposer.estimationboard.estimationdatefrom" /> :
								</td>
								<td width="50%">
								    <spring:message code="BzComposer.estimationboard.estimationdateto" /> :
								</td>
							</tr>
							<tr>
								<td>
									<form:input path="orderDate1" size="20" style="width: 120px;" />
                                    <img style="margin: 5;"src="${pageContext.request.contextPath}/images/cal.gif" onclick="displayCalendar(document.EstimationBoardForm.orderDate1,'mm-dd-yyyy',this);" />
								</td>
								<td>
								    <form:input path="orderDate2" size="20" style="width: 120px;" />
									<img style="margin: 5;" src="${pageContext.request.contextPath}/images/cal.gif" onclick="displayCalendar(document.EstimationBoardForm.orderDate2,'mm-dd-yyyy',this);">
								</td>
							</tr>
						</table>
					</td>
					<td style="width:30%;">
						<table style="width: 100%;font-size:14px;" cellpadding="5">
							<tr><th colspan="2">&nbsp;</th></tr>
							<tr>
								<td><spring:message code="BzComposer.estimationboard.column" /></td>
								<td>
									<form:select path="searchType" style="width: 150px;">
										<option value="1"><spring:message code="BzComposer.estimationboard.name" /></option>
										<option value="2"><spring:message code="BzComposer.estimationboard.order" /></option>
										<option value="3"><spring:message code="BzComposer.estimationboard.orderid" /></option>
										<option value="4"><spring:message code="BzComposer.estimationboard.address" /></option>
										<option value="5"><spring:message code="BzComposer.estimationboard.productname" /></option>
										<option value="6"><spring:message code="BzComposer.estimationboard.email" /></option>
									</form:select>
								</td>
							</tr>
							<tr>
								<td><spring:message code="BzComposer.estimationboard.text" /></td>
								<td><form:input type="text" path="searchTxt" style="width: 150px;" /></td>
							</tr>
							<tr><td colspan="2"><spring:message code="BzComposer.salesorderboard.dateformat" /> : (MM-DD-YYYY)</td></tr>
						</table>
					</td>
                    <td style="width:40%;">
                        <div><button type="button" style="width: 70px;" type="button" class="formbutton" onclick="SaleSearch(1);"><spring:message code='BzComposer.estimationboard.search'/></button></div>
                        <div><button type="button" style="width: 70px;margin-top:10px;" type="button" class="formbutton" onclick="SaleSearch(2);"><spring:message code='BzComposer.estimationboard.refresh'/></button></div>
                        <div><button type="button" style="width: 70px;margin-top:10px;" type="button" class="formbutton" onclick="SaleSearch(3);"><spring:message code='BzComposer.estimationboard.clear'/></button></div>
					</td>
				</tr>
			</table>
			<div>
				<table align="center">
					<tr>
						<td align="center">
							<c:if test="${not empty msg}">
								<font color="red"><b>${msg}</b></font>
							</c:if>
						</td>
					</tr>
				</table>
				<br/>
				<span style="font-size: 1.2em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px; border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
					<spring:message code="BzComposer.estimationboard.estimaionlist" />
				</span>
				<br/>
				<div>
     				<div class="grid_8 height250 tabla-listados" id="estimationList" >
      					<table  id="estiboardList" class="tabla-listados" cellpadding="0" cellspacing="0">
							<thead style="font-weight:bold;">
								<tr>
								<%--<th><spring:message code="BzComposer.sales.Market" /></th> --%>
									<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.estimationnumber" /></th>
								<%--<th><spring:message code="BzComposer.sales.OrderID" /></th> --%>
									<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.companyname" /></th>
									<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.name" /></th>
								<%--<th><spring:message code="BzComposer.sales.Address" /></th> --%>
									<%--<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.sortbyzipcode" /></th>--%>
								<%--<th><spring:message code="BzComposer.sales.ProductName" /></th> --%>	
									<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.emailid" /></th>
									<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.estimationdate" /></th>
									<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.saledate" /></th>
									<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.printed" /></th>
									<th style="font-size:14px;"><spring:message code="BzComposer.estimationboard.shipped" /></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty EstimationBoardDetails}">
                                    <input type="hidden" name="sListSize" id="lSize" value='${EstimationBoardDetails.size()}'>
                                    <c:forEach items="${EstimationBoardDetails}" var="objList" varStatus="loop">
                                        <tr id='${loop.index}$$' ondblclick="sendToEstimation()"
                                        onclick="setRowId(${objList.est_no},'${objList.email}','${loop.index}$$');">
                                        <%--<td align="center">${objList.marketPlaceName}</td> --%>
                                            <td style="font-size:14px;">
                                                ${objList.est_no}
                                            </td>
                                        <%--<td align="center">${objList.orderid}</td> --%>
                                            <td style="font-size:14px;">
                                                ${objList.companyName}
                                            </td>
                                            <td style="font-size:14px;">
                                                ${objList.lastName},${objList.firstName}
                                            </td>
                                        <%--<td align="center">${objList.address1},${objList..address2}</td> --%>
                                            <%--<td style="font-size:14px;">
                                                ${objList.zipCode}
                                            </td>--%>
                                        <%--<td align="center">${objList.itemName}</td> --%>
                                            <td style="font-size:14px;">
                                                ${objList.email}
                                            </td>
                                            <td style="font-size:14px;">
                                                ${objList.saleDate}
                                            </td>
                                            <td style="font-size:14px;">
                                                ${objList.dateAdded}
                                            </td>
                                            <td style="font-size:14px;">
                                                <c:if test="${objList.printed == true}">
                                                    <input type="checkbox" name="isPrintedCHK" id="isPrintedId" title="isPrinted" checked="true" disabled="disabled">
                                                </c:if>
                                                <c:if test="${objList.printed == false}">
                                                    <input type="checkbox" name="isPrintedCHK" id="isPrintedId" title="isPrinted" disabled="disabled">
                                                </c:if>
                                            </td>
                                            <td style="font-size:14px;">
                                                <c:if test="${objList.shipped == 1}">
                                                    <input type="checkbox" name="shipped${loop.index}" id="shippedId" title="shipped" checked="true" value="ON"
                                                        onclick="makeUpdate(${objList.est_no},this);">
                                                </c:if>
                                                <c:if test="${objList.shipped == 0}">
                                                    <input type="checkbox" name="shipped${loop.index}" id="shippedId" title="shipped" value="ON"
                                                        onclick="makeUpdate(${objList.est_no},this);">
                                                </c:if>
                                            </td>

<%-- 							<td><logic:equal name="objList" --%>
<!-- 								property="email" value="true"> -->
<!-- 								<input type="checkbox" name="isEmailValCHK" id="isEmailId" -->
<!-- 									title="isEmailed" checked="true" disabled="disabled"> -->
<%-- 							</logic:equal> <logic:equal name="objList" property="email" value="false"> --%>
<!-- 								<input type="checkbox" name="isEmailValCHK" id="isEmailId" -->
<!-- 									title="isEmailed" disabled="disabled"> -->
<%-- 							</logic:equal></td> --%>

						</tr>
					</c:forEach>
			</c:if>

		</tbody>
	</table>
	</div>
	</div></div>
	<table align="center">
		<tr align="center">
			<td>
				<input type="button" class="formbutton" name="smailbtn"
				id="smail" value='<spring:message code="BzComposer.estimationboard.lookup" /> '
				disabled="disabled" title="Please select a record" style="padding: 10px;"
				onclick="sendToEstimation();">
				&nbsp;&nbsp;
				<input type="button" class="formbutton" id="modi"
				onclick="makeUpdateInList();" title="Modify shipped" style="padding: 10px;"
				name="modifyBtn"
				value='<spring:message code="BzComposer.global.update" />'>
				&nbsp;&nbsp;
				<input type="button" class="formbutton" id="modi"
                    onclick="makeUpdateInList();" title="Modify shipped" style="padding: 10px;"
                    name="modifyBtn"
                    value='<spring:message code="BzComposer.global.InvoiceIt" />'>
                 &nbsp;&nbsp;
                <input type="button" class="formbutton" id="modi"
                    onclick="makeUpdateInList();" title="Modify shipped" style="padding: 10px;"
                    name="modifyBtn"
                    value='<spring:message code="BzComposer.global.cancel" />'>
                &nbsp;&nbsp;
                <input type="button" class="formbutton" id="modi"
                    onclick="makeUpdateInList();" title="Modify shipped" style="padding: 10px;"
                    name="modifyBtn"
                    value='<spring:message code="BzComposer.global.sendmail" />'>
				<%-- <input type="button" class="formbutton" id="modi"
				onclick="makeUpdateInList();" disabled=""disabled title="Modify shipped"
				name="modifyBtn"
				value='<spring:message code="BzComposer.sales.Update" />'> --%>
				<input
				type="hidden" name="ONum" id="ONumId"> <input type="hidden"
				name="sEmail" id="sEmailID"> <input type="hidden"
				name="rNum" id="rowONum"> <input type="hidden"
				name="senderEmail" id="EID"></td>
		</tr>
	</table>
	<input type="hidden" id="ordId" name="OrderValue" value=""> <input
		type="hidden" id="statusId" name="StatusValue" value=""> <input
		type="hidden" id="ordSize" name="Size" value=""> <!-- end Contents --></div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	<input type="hidden" id="ord_value" />
		<input type="hidden" id="est_value" />
</form:form>
<%@ include file="/WEB-INF/jsp/include/footer.jsp"%>

</body>
</html>

<script type="text/javascript">
function setIsEmail(){
	//alert("DFSD");
	document.getElementById("isEmailChk").value=document.getElementById("EmailId").value;
	document.forms[0].action = "Email?tabid=EOSOLO";
	document.forms[0].submit();
}
function checkName(){
	if(document.RMAForm.fnameTxt.value=="" && document.RMAForm.lnameTxt.value=="")	{
		//alert("You have to type one of fields in fst name and last name");
		return showFirstNameLastNameDialog();
	}
	else{
		document.forms[0].action = "RMA?tabid=R0S0C0" ;
		document.forms[0].submit();
	}	
}

function setRowId(rowId,email,rid){
	document.getElementById("est_value").value=rowId;
	
	//orderNo=document.getElementById("ord_value").value;
	size=document.getElementById("lSize").value;
	type=document.getElementById("searchType").value;
	typeVal=document.getElementById("searchTxt").value
// 	for(i=0;i<size;i++){
// 		var row1=document.getElementById(i+"$$");
// 		row1.className = "";
// 	}
// 	var rd=document.getElementById(rid);

rowValue= rid.replace("$$","");
rowValue++;
	for(i=0;i<size;i++){
		 document.getElementById(i+"$$").classList.remove('draft');		
		 if(i%2==0){	
			 if(size !=(i+1)){
			   document.getElementById((i+1)+"$$").classList.add('odd');
			 }
		 }
	}
	if(rowValue%2==0){ 	
		
		      document.getElementById((rowValue-1)+"$$").classList.remove('odd'); 
		
	}
	var rd = document.getElementById(rid).classList.add('draft');
	//rd.className = "draft";
	document.getElementById("smail").disabled=false;
	document.getElementById("sEmailID").value=email;
	document.getElementById("rowONum").value=rowId;
}

// this code for table odd/even row color
setRowId(null, null, "0$$");

function sendToEstimation(){
	debugger;
	est_no=document.getElementById("est_value").value;
	/* document.forms[0].action = "Estimation?tabid=SBLU&est_no="+est_no;
	document.forms[0].submit(); */
	window.location = "Estimation?tabid=SBLU&est_no="+est_no;
}
var ordarr = new Array();
var statusarr = new Array();

var cnt=0;
var ord="";
var status="";
function makeUpdate(orderno,obj)
{
	var flag=0;
	document.getElementById("modi").disabled=false;
	val=obj.checked;
	for(j=0;j<=cnt;j++){
		if(orderno==ordarr[j]){
			flag=1;
			statusarr[j]=val;
		}
	}
	if(flag==0){
		ordarr[cnt]=orderno;
		statusarr[cnt]=val;
		cnt++;
	}
}

function makeUpdateInList()
{
	for(j=0;j<cnt;j++){
		ord+=ordarr[j]+";";
		status+=statusarr[j]+";";
	}
	document.getElementById('ordSize').value=cnt;
	document.getElementById('ordId').value=ord;
	document.getElementById("statusId").value=status;
	debugger;
	return updateInvoiceBoardDialog();
	/* response = window.confirm("Do you want to update record?");
	if(response){
		document.getElementById("modi").disabled=false;
		document.forms[0].action = "EstimationBoard?tabid=UpdateRecord";
		document.forms[0].submit();
	} */
}

function SaleSearch(filterType){
    if(filterType > 1){
        location.reload();
    }
    let searchType = $("#searchType").val();
    let searchTxt = $("#searchTxt").val();
    let orderDate1 = $("#orderDate1").val();
    let orderDate2 = $("#orderDate2").val();
    $.ajax({
        type : "POST",
        url : "EstimationBoard?tabid=ShowList",
        data:"searchType=" + searchType + "&searchTxt=" +searchTxt+ "&orderDate1=" +orderDate1+ "&orderDate2=" +orderDate2,
        success : function(data){
            $(document).find('div#estimationList table').replaceWith($(data).find('div#estimationList').html());
        },
         error : function(data) {
             alert("<spring:message code='BzComposer.billingboard.someerroroccurred'/>");
        }
    });
}
</script>
<!-- Dialog box used in estimation board page -->
<div id="updateInvoiceBoardDialog" style="display:none;">
	<p><spring:message code="BzComposer.estimationboard.updaterecordmessage"/></p>
</div>
<div id="showFirstNameLastNameDialog" style="display:none;">
	<p><spring:message code="BzComposer.estimationboard.enterfirstorlastnamemessage"/></p>
</div>