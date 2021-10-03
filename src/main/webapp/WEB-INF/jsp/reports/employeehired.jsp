<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/WEB-INF/jsp/include/header.jsp"%>
<title>${sessionScope.user} - <spring:message code="BzComposer.SalesPanel.SalesReportByItem"/></title>
</head>
<body>

<form:form action="/dashboard/Reports?tabid=Hired" method="post" id="salesboardForm" name="salesboardForm"  modelAttribute="salesBoardDto" >
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
	    		<td><img src="/images/cal.gif" onclick="displayCalendar(document.salesboardForm.fromDate,'mm-dd-yyyy',this);" style="padding-left: 5px"></td>
	    		<td><label style="padding-left: 15px"><spring:message code="BzComposer.sales.to"/></label></td>
	    		<td><form:input path="toDate" size="15"/></td>
	    		<td><img src="/images/cal.gif" onclick="displayCalendar(document.salesboardForm.toDate,'mm-dd-yyyy',this);" style="padding-left: 5px"></td>
	    		<td><label style="padding-right: 10px;padding-left: 15px"><spring:message code="BzComposer.Report.labelSortBy"/></label></td>
	    		<td>
	    		 <form:select path="sortBy">
 	    		  	<form:option value="1"><spring:message code="BzComposer.global.firstname"/></form:option>
	    		  	<form:option value="2"><spring:message code="BzComposer.global.lastname"/></form:option>
	    		</form:select>
	    		</td>
	    	    <td><input type="button" value='<spring:message code="BzComposer.sales.Search"/>' class="formbutton mar" style="margin-left: 195px" onclick="search()"></td>
	    	</tr>
	 </table>
	</div>
<div id="printContent">	
	<div id="headerBar">
	      <h5 style="text-align: center;color: blue;padding-top: 20px">${sessionScope.user}</h5>
	      <h6 style="text-align: center;color: blue;" id="headerBarValue"><spring:message code="BzComposer.employee.hiredemployeelist"/></h6>
	</div>
	<div id="table-negotiations"
		style="overflow:auto;height:400; text-align: center;">
		
		<table class="tabla-customListOds" cellspacing="0" id="exportPd" border="1">

		<thead>
			<tr>
				<th><spring:message code="BzComposer.editemployee.title" /></th>
				<th><spring:message code="BzComposer.Employee.Name" /></th>
				<th><spring:message code="BzComposer.Employee.SSN" /></th>
				<th><spring:message code="BzComposer.editemployee.dateofbirth" /></th>
				<th><spring:message code="BzComposer.editemployee.employeetype" /></th>
                <th><spring:message code="BzComposer.editemployee.jobtitle" /></th>
				<th><spring:message code="BzComposer.Employee.Email" /></th>
				<th><spring:message code="BzComposer.global.address1" /></th>
				<th><spring:message code="BzComposer.global.address2" /></th>
				<th><spring:message code="BzComposer.Employee.City" /></th>
				<th><spring:message code="BzComposer.Employee.State" /></th>
			</tr>
		</thead>

		<tbody>
			<c:if test="${not empty CustomerDetails}">
     		<c:forEach items="${CustomerDetails}" var="sale">
                <tr>
                <td>${sale.title}</td>
                <td>${sale.fname} ${sale.mname} ${sale.lname}</td>
                <td>${sale.ssn}</td>
                <td>${sale.doa}</td>
                    <td><c:choose>
                        <c:when test="${sale.emptype=='1'}">
                            Full Time
                        </c:when>
                        <c:when test="${sale.emptype=='2'}">
                           Part Time
                        </c:when>
                        <c:when test="${sale.emptype=='3'}">
                            Contractor
                        </c:when>
                    </c:choose>
                </td>
                <td><c:choose>
                        <c:when test="${sale.jtitle=='1'}">
                            Manager
                        </c:when>
                        <c:when test="${sale.jtitle=='2'}">
                          Sales Rap
                        </c:when>
                        <c:when test="${sale.jtitle=='3'}">
                            Technician
                        </c:when>
                        <c:when test="${sale.jtitle=='14'}">
                            Junior Technicians
                        </c:when>
                        <c:when test="${sale.jtitle=='15'}">
                            Junior Manager
                        </c:when>
                        <c:when test="${sale.jtitle=='18'}">
                            Tester
                        </c:when>
                    </c:choose>
                </td>
                <td>${sale.email}</td>
                <td>${sale.address1}</td>
                <td>${sale.address2}</td>
                <td>${sale.city}</td>
                <td>${sale.state}</td>
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
	document.forms[0].action = "/dashboard/Reports?tabid=Hired";
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