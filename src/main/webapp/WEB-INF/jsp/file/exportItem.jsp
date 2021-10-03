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
<title><spring:message code="BzComposer.exportitemtitle"/></title>
</head>
<body>
<form:form action="Item?tabid=ExportItem" method="post" id="uploadForm" modelAttribute="itemDto">
	<table>
		<tr>
			<td><input type="button" class="formbutton" value="<spring:message code='BzComposer.exportitem.downloadcustomerincsv'/>" onclick="downloadCustomerList('csv')"/></td>
			<td><input type="button" class="formbutton" value="<spring:message code='BzComposer.exportitem.downloadcustomerinxls'/>" onclick="downloadCustomerList('xls')"/></td>
		</tr>
	</table>
	<div>
		<c:if test="${not empty success}">
		    <span style="color: green">${success}</span>
		</c:if>
	</div>
</form:form>
<script type="text/javascript">
var progress;
function downloadCustomerList(type){
	debugger;
	document.forms[0].action = "Item?tabid=ExportItem&type="+type;
	document.forms[0].submit();
}
</script>
</body>
</html>