<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/WEB-INF/jsp/include/header.jsp"%>
<title>${sessionScope.user} - <spring:message code="BzComposer.Report.Profit&Budget.BalanceSheet"/></title>
</head>
<body>
<form:form action="Accounting?tabid=BalanceSheet" method="post" modelAttribute="accountDto">
	<div class="report-form-headerpanel" id="headerPanel">
		<table>
		   <tr>
		   <%-- 	<td><input type="button" value='<spring:message code="BzComposer.Report.btn.ModifyReport"/>' class="formbutton mar"></td> --%>
		   <%-- 	<td><input type="button" value='<spring:message code="BzComposer.Report.btn.Print"/>' class="formbutton mar" onclick="printPage()"></td> --%>
		   	<td><input type="button" value='<spring:message code="BzComposer.Report.btn.Email"/>' class="formbutton mar" onclick="sendMail()" id="email"></td>
		   	<td><input id="btnHeader1" type="button" value='<spring:message code="BzComposer.Report.btn.HideHeader"/>' class="formbutton mar" onclick="hideShowHeader()"></td>
		   	<td><input type="button" value='<spring:message code="BzComposer.Report.btn.Refresh"/>' class="formbutton mar" onclick="search()"></td>

		   </tr>
		</table>
	</div>
	<div class="report-form-underheader">
	 <table>
	    	<tr>
	    		<td>
	    		  <label style="padding-right: 10px"><spring:message code="BzComposer.Report.labelDates"/></label>
	    		</td>
	    		<td>
	    		  <form:select path="datesCombo">
	    		  	<form:option value="0" ><spring:message code="BizComposer.amazonBulkMailer.DateSelect.All"/></form:option>
	    		  	<form:option value="1"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.Today"/></form:option>
	    		  	<form:option value="2"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.ThisWeek"/></form:option>
	    		  	<form:option value="3"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.ThisWeekToDate"/></form:option>
	    		  	<form:option value="4"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.ThisMonth"/></form:option>
	    		  	<form:option value="5"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.ThisMonthToDate"/></form:option>
	    		  	<form:option value="6"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.FiscalQuarter"/></form:option>
	    		  	<form:option value="7"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.FiscalQuarterToDate"/></form:option>
	    		  	<form:option value="8"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.Custom"/></form:option>
	    		  	<form:option value="9"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.10Days"/></form:option>
	    		  	<form:option value="10"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.30Days"/></form:option>
	    		  	<form:option value="11"><spring:message code="BizComposer.amazonBulkMailer.DateSelect.60Days"/></form:option>
	    		  </form:select>
	    		</td>
	    		<td><label style="padding-left: 15px"><spring:message code="BzComposer.sales.FromDate"/></label></td>
	    		<td><form:input path="fromDate" size="15"/></td>
	    		<td><img src="/images/cal.gif" onclick="displayCalendar(document.AccountForm.fromDate,'mm-dd-yyyy',this);" style="padding-left: 5px"></td>
	    		<td><label style="padding-left: 15px"><spring:message code="BzComposer.sales.to"/></label></td>
	    		<td><form:input path="toDate" size="15"/></td>
	    		<td><img src="/images/cal.gif" onclick="displayCalendar(document.AccountForm.toDate,'mm-dd-yyyy',this);" style="padding-left: 5px"></td>
	    		<td><label style="padding-right: 10px;padding-left: 15px"><spring:message code="BzComposer.Report.labelSortBy"/></label></td>
	    		<td>
	    		 <form:select path="sortBy">
	    		  	<form:option value="0"><spring:message code="BzComposer.Report.labelSortBy.Default"/></form:option>
	    		  	<form:option value="1"><spring:message code="BzComposer.Report.labelSortBy.Type"/></form:option>
	    		  	<form:option value="2"><spring:message code="BzComposer.Report.labelSortBy.#ID"/></form:option>
	    		  	<form:option value="3"><spring:message code="BzComposer.Report.labelSortBy.client/vendor"/></form:option>
	    		  	<form:option value="4"><spring:message code="BzComposer.Report.labelSortBy.memo"/></form:option>
	    		  	<form:option value="4"><spring:message code="BzComposer.Report.labelSortBy.amount"/></form:option>
	    		</form:select>
	    		</td>
	    	    <td><input type="button" value='<spring:message code="BzComposer.sales.Search"/>' class="formbutton mar" style="margin-left: 195px" onclick="search()"></td>
	    	</tr>
	 </table>
	</div>
<div id="printContent">
	<div id="headerBar">
	      <h5 style="text-align: center;color: blue;padding-top: 20px">${sessionScope.user}</h5>
	      <h6 style="text-align: center;color: blue;" id="headerBarValue"><spring:message code="BzComposer.Report.Profit&Budget.BalanceSheet"/></h6>
	</div>
	<div id="table-negotiations"
		style="overflow:auto;height:400; text-align: center;">

		<table class="tabla-customListOds" cellspacing="0" id="exportPd">

		<thead>
			<tr>
					<th><spring:message code="BzComposer.Report.vendor.BalanceSummary.Account" /></th>
					<th><spring:message code="BzComposer.Report.Amount" /></th>
			</tr>
		</thead>

		<tbody>
		 <tr>
			<td style="padding-left: 5px;color: blue;"><b><spring:message code="BzComposer.Report.Profit&Budget.Assests"/></b></td>
			<td></td>
		</tr>
		<tr>
			<td style="padding-top: 15px"><b><spring:message code="BzComposer.Report.Profit&Budget.CurrentAssests"/></b></td>
			<td></td>
		</tr>
		<tr>
			<td style="padding-top: 15px">&nbsp;&nbsp;&nbsp;&nbsp;<b><spring:message code="BzComposer.Report.Profit&Budget.CheckingSaveing"/></b></td>
			<td></td>
		</tr>
		<%-- <tr>
			<td style="padding-top: 15px;padding-top: 15px"><b><spring:message code="BzComposer.Report.Profit&Budget.TotalCheckingSaveing"/></b></td>
			<td style="padding-top: 15px"><b><u>${BalanceSheetCurrentAssets_TotalCheckingSavings}</u></b></td>
		</tr> --%>
	<%--	<tr>
			<td >&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="BzComposer.Report.CostOfGoodsSold"/></td>
			<td></td>
		</tr>
		<tr>
			<td style="padding-left: 5px;color: blue;padding-top: 15px"><spring:message code="BzComposer.Report.CostOfGoodsSold"/></td>
			<td></td>
		</tr>
		<tr>
			<td><spring:message code="BzComposer.Report.BegginingInv"/></td>
			<td><b>${Cost_of_Goods_Sold}</b></td>
		</tr>
		<tr>
			<td><spring:message code="BzComposer.Purchase"/></td>
			<td><b>${Cost_of_Goods_Sold.Purchases}</b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px"><spring:message code="BizComposer.Invoice.EndingIv"/></td>
			<td style="padding-top: 15px"><b>${Cost_of_Goods_Sold.Ending_Inventory}</b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px">&nbsp;&nbsp;&nbsp;&nbsp;<b><spring:message code="BzComposer.Report.CostOfGoodsSold"/></b></td>
			<td><b><u>${IncomeStatement.Cost_of_Goods_Sold}</u></b></td>
		</tr>
		<tr>
			<td style="color: blue;padding-top: 15px">&nbsp;&nbsp;&nbsp;&nbsp;<b><spring:message code="BzComposer.Report.GrossProfit"/></b></td>
			<td style="color: blue;padding-top: 15px"><b><u>${IncomeStatement.Cost_of_Goods_Sold.Gross_Profit}</u></b></td>
		</tr>
		<tr>
			<td style="color: blue;padding-top: 15px;padding-left: 5px"><b><spring:message code="BzComposer.Report.OperatingExpenses"/></b></td>
			<td></td>
		</tr> --%>
		<c:if test="${not empty acList}">
<%-- 		<input type="hidden" name="sListSize" id="lSize" value='${catList}'> --%>
 			<c:forEach items="${acList}" var="curObject">
                <tr>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${curObject.accountName}</td>
                <td>${curObject.amount}</td>
                </tr>
            </c:forEach>
        </c:if>
		<tr>
			<td style="padding-top: 15px;padding-top: 15px">&nbsp;&nbsp;&nbsp;&nbsp;<b><spring:message code="BzComposer.Report.Profit&Budget.TotalCheckingSaveing"/></b></td>
			<td style="padding-top: 15px"><b><u>${BalanceSheetCurrentAssets_TotalCheckingSavings}</u></b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px"><b><spring:message code="BzComposer.accounting.AccountReceivable"/></b></td>
			<td></td>
		</tr>
		<tr>
			<td style="padding-top: 15px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="BzComposer.accounting.Accounts"/></td>
			<td><b>${BalanceSheetCurrentAssets_AccountsReceivable}</b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="BzComposer.accounting.TotalAccounts"/></td>
			<td style="padding-top: 15px"><b>${BalanceSheetCurrentAssets_AccountsReceivable}</b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px"><b><spring:message code="BzComposer.Report.Profit&Budget.TotalCurrentAssets"/></b></td>
			<td><b>${BalanceSheetCurrentAssets_TotalCurrentAssets}</b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px"><b><spring:message code="BzComposer.Report.Profit&Budget.OtherAssets"/></b></td>
			<td></td>
		</tr>
		<tr>
			<td style="padding-top: 15px"><b><spring:message code="BzComposer.Item.Inventory"/></b></td>
			<td style="padding-top: 15px"><b>${BalanceSheetOtherAssets_Inventory}</b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px"><b><spring:message code="BzComposer.Report.Profit&Budget.TotalOtherAssets"/></b></td>
			<td style="padding-top: 15px"><b><u>${BalanceSheetOtherAssets_TotalOtherAssets}</u></b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px"><b><spring:message code="BzComposer.Report.Profit&Budget.TotalAssets"/></b></td>
			<td style="padding-top: 15px"><b><u>${BalanceSheetTotalAssets}</u></b></td>
		</tr>
		 <tr>
			<td style="padding-top: 15px;color: blue;"><b><spring:message code="BzComposer.Report.Profit&Budget.Liabilities"/></b></td>
			<td></td>
		</tr>
		<tr>
			<td style="padding-top: 15px;"><b><spring:message code="BzComposer.Report.Profit&Budget.CurrentLiabilities"/></b></td>
			<td></td>
		</tr>
		<%-- <tr>
			<td style="padding-top: 15px;"><b><spring:message code="BzComposer.Report.Profit&Budget.CurrentLiabilities"/></b></td>
			<td></td>
		</tr>	 --%>
		<tr>
			<td style="padding-top: 15px;"><b><spring:message code="BzComposer.Report.Profit&Budget.TotalLiabilities"/></b></td>
			<td style="padding-top: 15px;"><b><u>${BalanceSheetTotalLiabilities}</u></b></td>
		</tr>
			<tr>
			<td style="padding-top: 15px;color: blue;"><b><spring:message code="BzComposer.Report.Profit&Budget.Equity"/></b></td>
			<td></td>
		</tr>
		<tr>
			<td style="padding-top: 15px;">&nbsp;&nbsp;&nbsp;<b><spring:message code="BzComposer.Report.Profit&Budget.TotalStockHolderEquity"/></b></td>
			<td style="padding-top: 15px;"><b><u>${BalanceSheetTotalStockholdersEquity}</u></b></td>
		</tr>
		<tr>
			<td style="padding-top: 15px;color: blue;">&nbsp;&nbsp;&nbsp;<b><spring:message code="BzComposer.Item.AdjustAmount"/></b></td>
			<td style="padding-top: 15px;color: blue;"><b><u>${BalanceSheetAdjustAmount}</u></b></td>
		</tr>
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
	$("#btnHeader1").replaceWith("<input id='btnHeader2' type='button' value='<spring:message code='BzComposer.Report.btn.ShowHeader'/>' class='formbutton mar' onclick='ShowHeader()'>");
}
function ShowHeader()
{
	document.getElementById("headerBar").style.display = "block";
	$("#btnHeader2").replaceWith("<input id='btnHeader1' type='button' value='<spring:message code='BzComposer.Report.btn.HideHeader'/>' class='formbutton mar' onclick='hideShowHeader()'>");
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
	document.forms[0].action = "Accounting?tabid=BalanceSheet";
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


