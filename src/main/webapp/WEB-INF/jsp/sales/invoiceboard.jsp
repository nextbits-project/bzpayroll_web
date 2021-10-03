<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/WEB-INF/jsp/include/headlogo.jsp"%>
<%@include file="/WEB-INF/jsp/include/header.jsp"%>
<%@include file="/WEB-INF/jsp/include/menu.jsp"%>
<title><spring:message code="BzComposer.invoiceboardtitle"/></title>
<style type="text/css">
.height250 
{
height: 250px;
}
.fht-tbody
{
height: 180px !important; /*  change table height*/
border-bottom: 1px solid rgb(207, 207, 207);
}
table.tabla-listados 
{
width: 100%;
border: 1px solid rgb(207, 207, 207);
margin: 0px 0px 0px 0px;
margin: 0px 0px 0px 0px;
}
table.tabla-listados tbody tr.odd td 
{
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
        		document.forms['invoiceForm'].action = "SalesBord?tabid=UpdateRecord";
        		document.forms['invoiceForm'].submit();
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
<form:form id="invoiceForm" name="salesboardForm" action="SalesBord?tabid=ShowList" method="post" modelAttribute="salesBoardDto">
	<div id=""></div>
	<div id="ddcolortabsline">&nbsp;</div>
	<div id="cos">
	<div class="statusquo ok">
	<div id="hoja">
	<div id="blanquito">
	<div id="padding">
	<div>
		<span style="font-size: 1.2em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px; border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
			<spring:message code="BzComposer.invoiceboard.invoiceboardtitle" />
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
                        <td><spring:message code="BzComposer.salesorderboard.orderdate" /> <spring:message code="BzComposer.sales.FromDate" /> :</td>
                        <td><spring:message code="BzComposer.salesorderboard.orderdate" /> <spring:message code="BzComposer.salesorderboard.to" /> :</td>
                    </tr>
                    <tr>
                        <td style="width: 50%;">
                            <form:input path="orderDate1" size="20" style="width: 120px;" />
                            <img style="margin: 5;"src="${pageContext.request.contextPath}/images/cal.gif" onclick="displayCalendar(document.salesboardForm.orderDate1,'mm-dd-yyyy',this);" />
                        </td>
                        <td>
                            <form:input path="orderDate2" size="20" style="width: 120px;" />
                            <img style="margin: 5;" src="${pageContext.request.contextPath}/images/cal.gif" onclick="displayCalendar(document.salesboardForm.orderDate2,'mm-dd-yyyy',this);">
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
			    </c:if></td>
		</tr>
		</table>
		<br/>
		<span style="font-size: 1.2em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px; border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
			<spring:message code="BzComposer.invoiceboard.invoicelist" />
		</span>
		<br/>
		<div>
     		<div class="grid_8 height250 tabla-listados" id="invoiceOrderList" >
				<table  id="poboardList" class="tabla-listados" cellpadding="0" cellspacing="0">
					<thead style="font-weight: bold;">
						<tr>
							<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.order" /></th>
							<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.orderdate" /></th>
							<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.companyname" /></th>
							<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.name" /></th>
			    			<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.salesamount" /></th>
							<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.emailid" /></th>	
							<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.printed" /></th>
			    			<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.shipped" /></th>
			   				<th style="font-size: 14px;"><spring:message code="BzComposer.invoiceboard.isemailed" /></th>
						</tr>
					</thead>
					<tbody>
					<c:if test="${not empty SalesBoardDetails}">
                        <input type="hidden" name="sListSize" id="lSize" value='${SalesBoardDetails.size()}'>
                        <c:forEach items="${SalesBoardDetails}" var="objList" varStatus="loop">
                            <tr id='${loop.index}$$' ondblclick="sendToInvoice()" onclick="setRowId(${objList.orderNum},'${objList.email}','${loop.index}$$');">
                                <td style="font-size: 14px;">${objList.orderNum}</td>
                                <td style="font-size: 14px;">${objList.dateAdded}</td>
                                <td style="font-size:14px;">${objList.companyName}</td>
                                <td style="font-size:14px;">${objList.lastName},${objList.firstName}</td>
                                <%--<td style="font-size:14px;">${objList.zipCode}</td>--%>
                                <td style="font-size:14px;">${objList.total}</td>
                                <td style="font-size:14px;">${objList.email}</td>
                                <td style="font-size:14px;">
                                    <c:if test="${objList.printed == true}">
                                        <input type="checkbox" name="isPrintedCHK" id="isPrintedId" title="isPrinted" checked="true" disabled="disabled">
                                    </c:if>
                                    <c:if test="${objList.printed == false}">
                                        <input type="checkbox" name="isPrintedCHK" id="isPrintedId" title="isPrinted" disabled="disabled">
                                    </c:if>
                                </td>
                                <td style="font-size:14px;">
                                    <c:if test="${objList.shipped == '1'}">
                                        <input type="checkbox" name="shipped${loop.index}"
                                        id="shippedId" title="shipped" checked="true" value="ON"
                                        onclick="makeUpdate(${objList.orderNum},this);">
                                    </c:if>
                                    <c:if test="${objList.shipped == '0'}">
                                        <input type="checkbox" name="shippedshipped${loop.index}"
                                        id="shippedId" title="shipped" value="ON"
                                        onclick="makeUpdate(${objList.orderNum},this);">
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${objList.emailed == '1'}">
                                        <input type="checkbox" value="ON" name="isEmailValCHK" id="isEmailId"
                                        title="isEmailed" checked="true" disabled="disabled"/>
                                    </c:if>
                                    <c:if test="${objList.emailed == '0'}">
                                        <input type="checkbox" value="ON" name="isEmailValCHK" id="isEmailId"
                                        title="isEmailed" disabled="disabled"/>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
					</c:if>
					</tbody>
				</table>
			</div>
			</div>
			<table align="center">
				<tr align="center">
					<td>
						<input type="button" style="padding: 10px;" class="formbutton" name="smailbtn" id="smail" value='<spring:message code="BzComposer.invoiceboard.lookup" /> '
						disabled="disabled" title="Please select a record" onclick="sendToInvoice();"> 
						&nbsp;&nbsp;
						<input type="button" style="padding: 10px;" class="formbutton" disabled="disabled" id="modi" onclick="makeUpdateInList();" title="Modify shipped"
						name="modifyBtn" value='<spring:message code="BzComposer.global.update" />'>
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
						<input type="hidden" name="ONum" id="ONumId"> 
						<input type="hidden" name="sEmail" id="sEmailID"> 
						<input type="hidden" name="rNum" id="rowONum"> 
						<input type="hidden" name="senderEmail" id="EID">
					</td>
				</tr>
			</table>
			<input type="hidden" id="ordId" name="OrderValue" value=""> 
			<input type="hidden" id="statusId" name="StatusValue" value=""> 
			<input type="hidden" id="ordSize" name="Size" value=""> 
			<!-- end Contents -->
		</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	<input type="hidden" id="ord_value" />
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
var prev;
function setRowId(rowId,email,rid){
	
	document.getElementById("ord_value").value=rowId;
	
	//orderNo=document.getElementById("ord_value").value;
	size=document.getElementById("lSize").value;
	type=document.getElementById("searchType").value;
	typeVal=document.getElementById("searchTxt").value
	
	rowValue= rid.replace("$$","");
	for(i=0;i<size;i++){
// 		 var row1=document.getElementById(i+"$$");
// 		 row1.className = "";   
		 document.getElementById(i+"$$").classList.remove('draft');		
		 if(i%2==0){	
			 if(size !=(i+1)){
			 document.getElementById((i+1)+"$$").classList.add('odd');
			 }
		 }
	}
 	if((rowValue-1)%2==0){ 	
 		//alert(rowValue+"IN");
 		document.getElementById(rowValue+"$$").classList.remove('odd'); 		
 	}
 	var rd = document.getElementById(rid).classList.add('draft');
 	//	rd.className = "draft";
	
	document.getElementById("smail").disabled = false;
	document.getElementById("sEmailID").value = email;
	document.getElementById("rowONum").value = rowId;
}

setRowId(null, null, "0$$");

function sendToInvoice()
{
	debugger
	order_no=document.getElementById("ord_value").value;
	/* document.forms[0].action = "Invoice?tabid=SBLU&order_no="+order_no;
	document.forms[0].submit(); */
	//Added by tulsi
	window.location = "Invoice?tabid=SBLU&order_no="+order_no;
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
	if(flag==0)
	{
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
		document.forms[0].action = "SalesBord?tabid=UpdateRecord";
		document.forms[0].submit();
	} */
}

function SaleSearch(filterType)
{
	if(filterType > 1){
        location.reload();
    }
    let searchType = $("#searchType").val();
    let searchTxt = $("#searchTxt").val();
    let orderDate1 = $("#orderDate1").val();
    let orderDate2 = $("#orderDate2").val();
    $.ajax({
        type : "POST",
        url : "SalesBord?tabid=ShowList",
        data:"searchType=" + searchType + "&searchTxt=" +searchTxt+ "&orderDate1=" +orderDate1+ "&orderDate2=" +orderDate2,
        success : function(data){
            $(document).find('div#invoiceOrderList table').replaceWith($(data).find('div#invoiceOrderList').html());
        },
         error : function(data) {
             alert("<spring:message code='BzComposer.billingboard.someerroroccurred'/>");
        }
    });
}
</script>
<!-- Dialog box used in invoiceboard page -->
<div id="updateInvoiceBoardDialog" style="display:none;">
	<p><spring:message code="BzComposer.invoiceboard.updaterecordmessage"/></p>
</div>
<div id="showFirstNameLastNameDialog" style="display:none;">
	<p><spring:message code="BzComposer.invoiceboard.enterfirstorlastnamemessage"/></p>
</div>