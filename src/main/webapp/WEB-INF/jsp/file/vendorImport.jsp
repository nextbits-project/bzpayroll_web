<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/styles/form.css" media="screen" rel="Stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/tableStyle/js/jquery.min.js"></script>
<title><spring:message code="BzComposer.importvendortitle"/></title>
</head>
<body>
<form:form action="FileUpload?tabid=UploadVendorFile" method="post" enctype="MULTIPART/FORM-DATA" id="uploadForm" modelAttribute="companyInfoDto">
	<div>
		<!-- <input type="button" class="formbutton" value="DownLoad Sample CSV for customer" onclick= "csvOption()" id="csv"/> -->
		<a href="${pageContext.request.contextPath}/samplefile/BCA vendor data template.csv" download class="formbutton">
 			<spring:message code="BzComposer.vendorimport.csvfiledownload"/>
		</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/samplefile/BCA vendor data template.xls" download class="formbutton">
 			<spring:message code="BzComposer.vendorimport.excelfiledownload"/>
		</a>
		<!-- <input type="button" class="formbutton" value="DownLoad Sample xls for customer" onclick= "xlsOption()" id="xls"/> -->
	</div>
	<div style="margin-top: 40px">
	 <table>
	 	<tr>
	 		<td><spring:message code="BzComposer.vendorimport.csvorexcelfile"/></td>
	 		<td><input type="file" name="attachFile" /></td>
	 	</tr>
	 	<tr>
	 		<td><input type="submit" class="formbutton" value="<spring:message code='BzComposer.global.upload'/>" onclick="upload()"/></td>
	 	</tr>
	 </table>
	</div>
	<div>
		<logic:present name="successMessage1">
		  <span style="color: green">
		  		<spring:message code="BzComposer.FileUpload"/>
		  </span>
		</logic:present>
	</div>
</form:form>
</body>
</html>