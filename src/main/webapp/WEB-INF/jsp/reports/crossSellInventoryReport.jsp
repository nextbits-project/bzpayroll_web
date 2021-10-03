<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/WEB-INF/jsp/include/header.jsp"%>
<title>${sessionScope.user} - <bean:message key="cross_Sell_Inventory_Report"/></title>
</head>
<body>

<form:form action="ReportCenterESales?tabid=CrossSellInventoryReport" method="post" modelAttribute="accountDto">
	<div class="report-form-headerpanel" id="headerPanel">
		<table>
		   <tr>
		   <%-- 	<td><input type="button" value='<bean:message key="BzComposer.Report.btn.ModifyReport"/>' class="formbutton mar"></td> --%>
		   <%-- 	<td><input type="button" value='<bean:message key="BzComposer.Report.btn.Print"/>' class="formbutton mar" onclick="printPage()"></td> --%>
		   	<td><input type="button" value='<bean:message key="BzComposer.reportcenter.allinvoicelist.emailbtn"/>' class="formbutton mar" onclick="sendMail()" id="email"></td>
		   	<td><input id="btnHeader1" type="button" value='<bean:message key="BzComposer.reportcenter.allinvoicelist.hideheaderbtn"/>' class="formbutton mar" onclick="hideShowHeader()"></td>
		   	<td><input type="button" value='<bean:message key="BzComposer.reportcenter.allinvoicelist.refreshbtn"/>' class="formbutton mar" onclick="search()"></td>
		   	
		   </tr>
		</table>
	</div>
	<div class="report-form-underheader">
	 <table>
	    	<tr>
	    		<td>
	    		  <label style="padding-right: 10px"><bean:message key="BzComposer.reportcenter.allinvoicelist.dates"/></label>
	    		</td>
	    		<td>
	    		  <form:select path="datesCombo">
	    		  	<form:option value="0" ><bean:message key="BzComposer.reportcenter.allinvoicelist.dates.all"/></form:option>
	    		  	<form:option value="1"><bean:message key="BzComposer.reportcenter.allinvoicelist.dates.today"/></form:option>
	    		  	<form:option value="2"><bean:message key="BzComposer.reportcenter.allinvoicelist.dates.thisweek"/></form:option>
	    		  	<form:option value="3"><bean:message key="BzComposer.reportcenter.allinvoicelist.dates.thisweektodate"/></form:option>
	    		  	<form:option value="4"><bean:message key="BzComposer.reportcenter.allinvoicelist.dates.thismonth"/></form:option>
	    		  	<form:option value="5"><bean:message key="BzComposer.reportcenter.allinvoicelist.dates.thismonthtodate"/></form:option>
	    		  	<form:option value="6"><bean:message key="BzComposer.reportcenter.allinvoicelist.dates.fiscalquarter"/></form:option>
	    		  	<form:option value="7"><bean:message key="BzComposer.reportcenter.allinvoicelist.dates.fiscalquartertodate"/></form:option>
	    		  	<form:option value="8">
	    		  		<bean:message key="BzComposer.reportcenter.allinvoicelist.dates.custom"/>
    		  		</form:option>
	    		  	<form:option value="9">
	    		  		<bean:message key="BzComposer.reportcenter.allinvoicelist.dates.last10days"/>
    		  		</form:option>
	    		  	<form:option value="10">
	    		  		<bean:message key="BzComposer.reportcenter.allinvoicelist.dates.last30days"/>
    		  		</form:option>
	    		  	<form:option value="11">
	    		  		<bean:message key="BizComposer.amazonBulkMailer.DateSelect.60Days"/>
    		  		</form:option>
	    		  </form:select>
	    		</td>
	    		<td><label style="padding-left: 15px"><bean:message key="BzComposer.reportcenter.allinvoicelist.from"/></label></td>
	    		<td><form:input path="fromDate" size="15"/></td>
	    		<td><img src="${pageContext.request.contextPath}/images/cal.gif" onclick="displayCalendar(document.AccountForm.fromDate,'mm-dd-yyyy',this);" style="padding-left: 5px"></td>
	    		<td><label style="padding-left: 15px"><bean:message key="BzComposer.reportcenter.allinvoicelist.to"/></label></td>
	    		<td><form:input path="toDate" size="15"/></td>
	    		<td><img src="${pageContext.request.contextPath}/images/cal.gif" onclick="displayCalendar(document.AccountForm.toDate,'mm-dd-yyyy',this);" style="padding-left: 5px"></td>
	    		<td><label style="padding-right: 10px;padding-left: 15px"><bean:message key="BzComposer.reportcenter.allinvoicelist.sortby"/></label></td>
	    		<td>
	    		 <form:select path="sortBy">
	    		  	<form:option value="0"><bean:message key="BzComposer.reportcenter.allinvoicelist.sortby.default"/></form:option>
	    		  	<form:option value="1"><bean:message key="BzComposer.reportcenter.allinvoicelist.sortby.type"/></form:option>
	    		  	<form:option value="2"><bean:message key="BzComposer.reportcenter.allinvoicelist.sortby.id"/></form:option>
	    		  	<form:option value="3"><bean:message key="BzComposer.reportcenter.allinvoicelist.sortby.clientorvendor"/></form:option>
	    		  	<form:option value="4"><bean:message key="BzComposer.reportcenter.allinvoicelist.sortby.memo"/></form:option>
	    		  	<form:option value="4"><bean:message key="BzComposer.reportcenter.allinvoicelist.sortby.amount"/></form:option>
	    		</form:select>
	    		</td>
	    	    <td><input type="button" value='<bean:message key="BzComposer.reportcenter.allinvoicelist.searchbtn"/>' class="formbutton mar" style="margin-left: 195px" onclick="search()"></td>
	    	</tr>
	 </table>
	</div>
<div id="printContent">	
	<div id="headerBar">
	      <h5 style="text-align: center;color: blue;padding-top: 20px">${sessionScope.user}</h5>
	      <h6 style="text-align: center;color: blue;" id="headerBarValue"><bean:message key="cross_Sell_Inventory_Report"/></h6>
	</div>
	<div id="table-negotiations" style="overflow:auto;height:400; text-align: center;">
		<table class="tabla-customListOds" cellspacing="0" id="exportPd">
		<thead>
			<tr>
                <th><bean:message key="BzComposer.report.reservedinventorylist.inventorycode" /></th>
                <th><bean:message key="BzComposer.report.discountinuedinventorylist.sku" /></th>
                <th><bean:message key="crossSellDetails.OnHandQTY" /></th>
                <th><bean:message key="BzComposer.report.inventorylist.purchaseprice" /></th>
                <th><bean:message key="BzComposer.report.inventorylist.saleprice" /></th>
			</tr>
		</thead>
		<tbody>
		 	 <c:forEach items="${acList}" var="curObject">
				<tr>
                    <td>${curObject.inventoryCode}</td>
                    <td>${curObject.sku}</td>
                    <td>${curObject.qtyOnHand}</td>
                    <td>${curObject.purchasePrice}</td>
                    <td>${curObject.salePrice}</td>
				</tr>
			</c:forEach>
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
	$("#btnHeader1").replaceWith("<input id='btnHeader2' type='button' value='<bean:message key='BzComposer.reportcenter.allinvoicelist.showheaderbtn'/>' class='formbutton mar' onclick='ShowHeader()'>");
}
function ShowHeader()
{
	document.getElementById("headerBar").style.display = "block";
	$("#btnHeader2").replaceWith("<input id='btnHeader1' type='button' value='<bean:message key='BzComposer.reportcenter.allinvoicelist.hideheaderbtn'/>' class='formbutton mar' onclick='hideShowHeader()'>");
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
	document.forms[0].action = "ReportCenterESales?tabid=CrossSellInventoryReport";
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


