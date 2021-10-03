<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/WEB-INF/jsp/include/header.jsp"%>
<title>${sessionScope.user} - <spring:message code="BzComposer.inventorylisttitle"/></title>
</head>
<body>
<form:form action="Item" method="post">
	<div class="report-form-headerpanel" id="headerPanel">
		<table>
			<tr>
		   		<%--<td>
		   				<input type="button" value='<spring:message code="BzComposer.Report.btn.ModifyReport"/>' 
		   				class="formbutton mar">
	   				</td> --%>
		   		<%--<td>
		   				<input type="button" value='<spring:message code="BzComposer.Report.btn.Print"/>' 
		   				class="formbutton mar" onclick="printPage()">
	   				</td> --%>
		   			<td>
		   				<input type="button" value='<spring:message code="BzComposer.reportcenter.allinvoicelist.emailbtn"/>' 
		   				class="formbutton mar" onclick="sendMail()" id="email">
	   				</td>
		   			<td>
		   				<input id="btnHeader1" type="button" 
		   				value='<spring:message code="BzComposer.reportcenter.allinvoicelist.hideheaderbtn"/>' class="formbutton mar" 
		   				onclick="hideShowHeader()">
	   				</td>
		   			<td>
		   				<input type="button" value='<spring:message code="BzComposer.reportcenter.allinvoicelist.refreshbtn"/>' 
		   				class="formbutton mar" onclick="callRefresh()">
	   				</td>
		   		</tr>
			</table>
		</div>
		<div id="printContent">
			<div id="headerBar">
	      		<h5 style="text-align: center;color: blue;padding-top: 20px">${sessionScope.user}</h5>
	      		<h6 style="text-align: center;color: blue;" id="headerBarValue">
	      			<spring:message code="BzComposer.report.inventorylist.inventorylisttitle"/>
      			</h6>
			</div>
			<div id="table-negotiations" style="overflow: auto; height: 400; text-align: center;">
				<table class="tabla-customListOds" id="exportPd" border="1">		
					<thead>
						<tr>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.itemname" /></th>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.title" /></th>
							<th class="emblem">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</th>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.quantity" /></th>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.purchaseprice" /></th>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.saleprice" /></th>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.serialnumber" /></th>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.weight" /></th>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.location" /></th>
							<th class="emblem"><spring:message code="BzComposer.report.inventorylist.taxable" /></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty ItemDetails}">
                            <input type="hidden" name="listSize" id="lSize" value='${ItemDetails.size()}'>
                            <c:forEach items="${ItemDetails}" var="item">
                                <tr>
                                    <td>
                                        <c:if test="${item.putcharacter == '***'}">
                                        <font size="2">
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            ${item.itemCode}
                                        </font>
                                        </c:if>
                                        <c:if test="${item.putcharacter == '**'}">
                                        <font size="3">
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*
                                            ${item.itemCode}
                                        </font>
                                        </c:if>
                                        <c:if test="${item.putcharacter == '*'}">
                                        <font size="3">
                                            ${item.putcharacter}${item.itemCode}
                                        </font>
                                        </c:if>
                                    </td>
                                    <td>${item.itemName}</td>
                                    <td></td>
                                    <td>&nbsp;${item.qty}</td>
                                    <td>&nbsp;${item.purchasePrice}</td>
                                    <td>&nbsp;${item.salePrice}</td>
                                    <td>&nbsp;${item.serialNum}</td>
                                    <td>&nbsp;${item.weight}</td>
                                    <td>&nbsp;${item.location}</td>
                                    <td>&nbsp;${item.taxable}</td>
                                </tr>
                            </c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>	
</form:form>
<%@ include file="/WEB-INF/jsp/include/footer.jsp"%>
<%@ include file="/WEB-INF/jsp/include/emailModal.jsp"%>
<!-- Javascript begins here -->
<script type="text/javascript">
var modal = document.getElementById('myModal');
function hideShowHeader()
{
	debugger;
	document.getElementById("headerBar").style.display = "none";
	/* $("#btnHeader1").hide(); */
	document.getElementById("headerBar").style.display = "none";
	$("#btnHeader1").replaceWith("<input id='btnHeader2' type='button' value='<spring:message code='BzComposer.reportcenter.allinvoicelist.showheaderbtn'/>' class='formbutton mar' onclick='ShowHeader()'>");
}
function ShowHeader()
{
	document.getElementById("headerBar").style.display = "block";
	$("#btnHeader2").replaceWith("<input id='btnHeader1' type='button' value='<spring:message code='BzComposer.reportcenter.allinvoicelist.hideheaderbtn'/>' class='formbutton mar' onclick='hideShowHeader()'>");
}
function printPage()
{
	/*   debugger;
	  var doc = new jsPDF("1", "pt","a2");  
	  var source = $("#printContent")[0]; 
	  doc.fromHTML(source); 
	  doc.save($("#headerBarValue").html()+".pdf");  */
	  
	  //for creating pdf 
	   var divToPrint=document.getElementById("exportPd");
	   var header = document.getElementById("headerBar");
	   newWin= window.open("");
	   newWin.document.write(header.outerHTML+divToPrint.outerHTML);
	   newWin.print();
	   newWin.close(); 
	 
	   //for creating excel
	   debugger;
	   str="";

  var myTableHead = document.getElementById('ProfitLossItem');
  var rowCount = myTableHead.rows.length;
  var colCount = myTableHead.getElementsByTagName("tr")[0].getElementsByTagName("th").length; 

var ExcelApp = new ActiveXObject("Excel.Application");
var ExcelSheet = new ActiveXObject("Excel.Sheet");
ExcelSheet.Application.Visible = true;

for(var i=0; i<rowCount; i++) 
{   
    for(var j=0; j<colCount; j++) 
    {           
        str= myTableHead.getElementsByTagName("tr")[i].getElementsByTagName("th")[j].innerHTML;
        ExcelSheet.ActiveSheet.Cells(i+1,j+1).Value = str;
    }
}
	/* window.open('data:application/vnd.ms-excel,' + $('#ProfitLossItem').html()); */
}
function search()
{
	document.forms[0].action = "Item?tabid=ProfitLossByItem";
	document.forms[0].submit();
}
function callRefresh(){
	document.forms[0].action = "Item?tabid=InventoryList";
	document.forms[0].submit();
}
function sendMail() {
	modal.style.display = "block";
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
}
function closeModal()
{
	modal.style.display = "none";
}
</script>
<!-- Javascript end here -->
</body>
</html>