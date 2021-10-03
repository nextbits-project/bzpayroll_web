<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="/WEB-INF/jsp/include/header.jsp"%>
<title>${sessionScope.user}-<spring:message code="BzComposer.allvendorlisttitle"/></title>
</head>
<body>
<form:form action="PurchaseBoard?tabid=AllVendorList" method="post">
	<div class="report-form-headerpanel" id="headerPanel">
		<table>
			<tr>
			<%--<td><input type="button" value='<spring:message code="BzComposer.Report.btn.ModifyReport"/>' class="formbutton mar"></td> --%>
		   	<%--<td><input type="button" value='<spring:message code="BzComposer.Report.btn.Print"/>' class="formbutton mar" onclick="printPage()"></td> --%>
		   		<td>
		   			<input type="button" value='<spring:message code="BzComposer.reportcenter.allinvoicelist.emailbtn"/>' 
		   			class="formbutton mar" onclick="sendMail()" id="email">
	   			</td>
		   		<td>
		   			<input id="btnHeader1" type="button" value='<spring:message code="BzComposer.reportcenter.allinvoicelist.hideheaderbtn"/>' 
		   			class="formbutton mar" onclick="hideShowHeader()">
	   			</td>
		   		<td>
		   			<input type="button" value='<spring:message code="BzComposer.reportcenter.allinvoicelist.refreshbtn"/>' 
		   			class="formbutton mar" onclick="search()">
	   			</td>
		   	</tr>
		</table>
	</div>
	<div id="ddcolortabsline">&nbsp;</div>
	<div id="table-negotiations">
		<table align="center">
			<tr>
				<td align="center">
					<c:if test="${not empty msg}">
						<font color="red"><b>${msg}</b></font>
					</c:if>
				</td>
			</tr>
		</table>
		<div id="printContent">
			<div id="headerBar">
				<h5 style="text-align: center; color: blue; padding-top: 20px">${sessionScope.user}</h5>
				<h6 style="text-align: center; color: blue;" id="headerBarValue">
					<spring:message code="BzComposer.report.vendorpurchaselist.vendorlist" />
				</h6>
			</div>
			<div id="table-negotiations" style="overflow: auto; height: 400; text-align: center;">
				<table class="tabla-customListOds" id="exportPd" border="1">
					<thead>
						<tr>
							<th><spring:message code="BzComposer.report.vendorpurchaselist.companyname" /></th>
							<th><spring:message code="BzComposer.global.firstname" /></th> 
							<th><spring:message code="BzComposer.global.lastname" /></th> 	
							<th><spring:message code="BzComposer.global.phone" /></th>		
							<th><spring:message code="BzComposer.global.fax" /></th>
							<th><spring:message code="BzComposer.global.email" /></th>
							<th><spring:message code="BzComposer.global.address1" /></th>															
							<th><spring:message code="BzComposer.global.address2" /></th>
							<th><spring:message code="BzComposer.global.city" /></th>
							<th><spring:message code="BzComposer.global.state" /></th>
							<th><spring:message code="BzComposer.global.province" /></th>
							<th><spring:message code="BzComposer.global.country" /></th>
							<th><spring:message code="BzComposer.global.zipcode" /></th>
							<th><spring:message code="BzComposer.report.vendorpurchaselist.detail"/></th>				
						</tr>
					</thead>
					<tbody>
					    <c:if test="${not empty VendorDetails}">
                            <input type="hidden" name="sListSize" id="lSize" value='${VendorDetails.size()}'>
                            <c:forEach items="${VendorDetails}" var="curObject">
                                <tr>
                                    <td>${curObject.cname}</td>
                                    <td>${curObject.firstName}</td>
                                    <td>${curObject.lastName}</td>
                                    <td>${curObject.phone}</td>
                                    <td>${curObject.fax}</td>
                                    <td>${curObject.email}</td>
                                    <td>${curObject.address1}</td>
                                    <td>${curObject.address2}</td>
                                    <td>${curObject.city}</td>
                                    <td>${curObject.stateName}</td>
                                    <td>${curObject.province}</td>
                                    <td>${curObject.country}</td>
                                    <td>${curObject.zipCode}</td>
                                    <td>${curObject.memo}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form:form>
<%@ include file="/WEB-INF/jsp/include/emailModal.jsp"%>
<%@ include file="/WEB-INF/jsp/include/footer.jsp"%>
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
	document.forms[0].action = "PurchaseBoard?tabid=AllVendorList";
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