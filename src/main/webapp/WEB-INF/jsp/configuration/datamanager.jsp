<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/WEB-INF/jsp/include/sessionExpired.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<jsp:include page="/WEB-INF/jsp/include/headlogo.jsp" />
    <jsp:include page="/WEB-INF/jsp/include/header.jsp" />
    <jsp:include page="/WEB-INF/jsp/include/menu.jsp" />
	<title><spring:message code="BzComposer.datamanagertitle" /></title>
	<link href="${pageContext.request.contextPath}/tableStyle/tab/jquery-ui-tab.css" rel="stylesheet" media="screen" />
	<script src="${pageContext.request.contextPath}/tableStyle/tab/jquery-ui.js"></script>
	<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"> -->
	<%-- <script src="${pageContext.request.contextPath}/dist/js/jquery.min.js"></script>  --%>
	<%-- <script src="${pageContext.request.contextPath}/dist/js/bootstrap.min.js"></script> --%>
</head>
<head>
	<jsp:include page="customerInvoiceFunctionPage.jsp"></jsp:include>
	<%-- <%@include file ="customerInvoiceFunctionPage.jsp" %> --%>
<%--	<style>
		table.table-notifications tr td {
			padding: .7em 2em;
		}

		table.table-notifications th {
			padding-left: 27px;
			text-align: left;
		}
	</style>--%>
	<script type="text/javascript">
		function selectItemDialog() {
			event.preventDefault();
			$("#selectItemDialog").dialog({
				resizable : false,
				height : 200,
				width : 300,
				modal : true,
				buttons : {
					"<spring:message code='BzComposer.global.ok'/>" : function() {
						$(this).dialog("close");
					},
			"<spring:message code='BzComposer.global.cancel'/>" : function() {
				$(this).dialog("close");
				return false;
			}
		}
		});
			return false;
		}

		function selectTaxRateDialog() {
			event.preventDefault();
			$("#selectTaxRateDialog").dialog({
				resizable : false,
				height : 200,
				width : 300,
				modal : true,
				buttons : {
					"<spring:message code='BzComposer.global.ok'/>" : function() {
						$(this).dialog("close");
					},
			"<spring:message code='BzComposer.global.cancel'/>" : function() {
				$(this).dialog("close");
				return false;
			}
		}
		});
			return false;
		}

		function showBlankDiscriptionDialog() {
			event.preventDefault();
			$("#showBlankDiscriptionDialog").dialog({
				resizable : false,
				height : 200,
				width : 400,
				modal : true,
				buttons : {
					"<spring:message code='BzComposer.global.ok'/>" : function() {
						$(this).dialog("close");
					},
			"<spring:message code='BzComposer.global.cancel'/>" : function() {
				$(this).dialog("close");
				return false;
			}
		}
		});
			return false;
		}

		function saveSelectedItemDialog() {
			event.preventDefault();
			$("#saveSelectedItemDialog").dialog({
				resizable : false,
				height : 200,
				width : 400,
				modal : true,
				buttons : {
					"<spring:message code='BzComposer.global.ok'/>" : function() {
						$(this).dialog("close");
						document.forms['dmForm'].action = "Configuration?tabid=DM_Save";
						document.forms['dmForm'].submit();

						//window.location = "Configuration?tabid=DM_Save";

					},
			"<spring:message code='BzComposer.global.cancel'/>" : function() {
				$(this).dialog("close");
				return false;
			}
		}
		});
			return false;
		}

		function deleteSelectedItemDialog() {
			event.preventDefault();
			$("#deleteSelectedItemDialog").dialog({
				resizable : false,
				height : 200,
				width : 400,
				modal : true,
				buttons : {
					"<spring:message code='BzComposer.global.ok'/>" : function() {
						$(this).dialog("close");
						/*  document.forms[0].action = "Configuration?tabid=DM_Delete";
                        document.forms[0].submit(); */

						window.location ="Configuration?tabid=DM_Delete&sTitleval="+sType+"&sNewvalID="+sNew;


					},
			<spring:message code='BzComposer.global.cancel'/> : function() {
				$(this).dialog("close");
				return false;
			}
		}
		});
			return false;
		}
	</script>
</head>
<!-- <body onload="init1();"> -->
<body onload="init();">
<!-- begin shared/header -->
<%-- <form:form action="Configuration?" enctype="multipart/form-data" method="post" id="form"> --%>
<form:form name="configurationForm" method="post" id="dmForm" modelAttribute="configDto">
	<div id="ddcolortabsline">&nbsp;</div>
	<div id="cos">
		<div class="statusquo ok">
			<div id="hoja">
				<div id="blanquito">
					<div id="padding">
						<div>
		<span style="font-size: 1.1em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px; border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
			<spring:message code="BzComposer.configuration.configurationtitle"/>
		</span>
						</div>
						<div>
							<div id="table-negotiations" style="padding: 0; border: 1px solid #ccc;">
								<table cellspacing="0"  style="border: 0;width: 100%;overflow-y:scroll;" class="section-border">
									<span style="font-size:30px;cursor:pointer; margin-left: 20px;" onclick="toggleFunction()">&#9776;</span>
									<tr>
										<td  id="leftMenu" style="position: relative; width: 180px;">
											<table>
												<tr>
													<td>
														<jsp:include page="menuPage.jsp" />
													</td>
												</tr>
													<%-- <tr align="center">
                                                        <td>
                                                            <input type="button" name="Revoke" class="formButton" onclick="RevokeValues();" value='<spring:message code="BizComposer.Configuration.RevokeButton"/>' />
                                                            <input type="button" name="Save" class="formButton" onclick="SaveValues();" value='<spring:message code="BizComposer.Configuration.SaveButton"/>' />
                                                        </td>
                                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                    </tr> --%>
											</table>
										</td>
										<td valign="top">
											<!-- customerInvoice Starts -->
											<div id="datamanager"  style="display:none;padding: 0; position: relative; left: 0;" >
												<div id="cos">
													<div class="statusquo ok">
														<div id="hoja">
															<div id="blanquito">
																<div id="padding">
																	<!-- begin Contents -->

																	<div>
								<span
										style="font-size: 1.6em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px; border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
										<spring:message code="BzComposer.datamanager.datamanagerheading" /></span>
																	</div>
																	<div>
																		<div id="table-negotiations" align="center"
																			 class="section-border" style="width: 100%;">
																			<div id="Hidden">
																				<div id="CustTitle">
																					<c:if test="${not empty customerTitle}">
																						<input type="hidden" id="ctSize" value='${customerTitle.size()}'>
																						<c:forEach items="${customerTitle}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.titleID}' id='${loop.index}ctitleID' />
																							<input type="hidden" value='${curObject.title}' id='${loop.index}ctitleNm' />
																						</c:forEach>
																					</c:if>
																				</div>
																				<div id="Rep">
																					<c:if test="${not empty SalesRep}">
																						<input type="hidden" id="rSize" value='${SalesRep.size()}'>
																						<c:forEach items="${SalesRep}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.salesRepID}' id='${loop.index}repID' />
																							<input type="hidden" value='${curObject.salesRepName}' id='${loop.index}repNm' />
																						</c:forEach>
																					</c:if>
																				</div>
																				<div id="ShipCarrier">
																					<c:if test="${not empty Via}">
																						<input type="hidden" id="shSize" value='${Via.size()}'>
																						<c:forEach items="${Via}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.shipCarrierID}' id='${loop.index}shipID' />
																							<input type="hidden" value='${curObject.shipCarrierName}' id='${loop.index}shipNm' />
																						</c:forEach>
																					</c:if>
																				</div>
																				<div id="Term">
																					<c:if test="${not empty salesTerms}">
																						<input type="hidden" id="tSize" value='${salesTerms.size()}'>
																						<c:forEach items="${salesTerms}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.termId}' id='${loop.index}termID' />
																							<input type="hidden" value='${curObject.termName}' id='${loop.index}termNm' />
																						</c:forEach>
																					</c:if>
																				</div>

																				<div id="Type">
																					<c:if test="${not empty SalesCatType}">
																						<input type="hidden" id="catSize" value='${SalesCatType.size()}'>
																						<c:forEach items="${SalesCatType}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.cvCategoryID}' id='${loop.index}typeID' />
																							<input type="hidden" value='${curObject.cvCategoryName}' id='${loop.index}typeNm' />
																						</c:forEach>
																					</c:if>
																				</div>
																				<div id="Location">
																					<c:if test="${not empty SalesLocation}">
																						<input type="hidden" id="lSize" value='${SalesLocation.size()}'>
																						<c:forEach items="${SalesLocation}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.locationId}' id='${loop.index}locID' />
																							<input type="hidden" value='${curObject.locationName}' id='${loop.index}locNm' />
																						</c:forEach>
																					</c:if>
																				</div>
																				<div id="PaymentMethod">
																					<c:if test="${not empty SalesPaymentMethod}">
																						<input type="hidden" id="pSize" value='${SalesPaymentMethod.size()}'>
																						<c:forEach items="${SalesPaymentMethod}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.paymentTypeId}' id='${loop.index}pmtID' />
																							<input type="hidden" value='${curObject.paymentTypeName}' id='${loop.index}pmtNm' />
																						</c:forEach>
																					</c:if>
																				</div>
																				<div id="CreditCard">
																					<c:if test="${not empty CreditCardType}">
																						<input type="hidden" id="ccSize" value='${CreditCardType.size()}'>
																						<c:forEach items="${CreditCardType}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.ccTypeID}' id='${loop.index}ccTypeID' />
																							<input type="hidden" value='${curObject.ccTypeName}' id='${loop.index}ccTypeNm' />
																						</c:forEach>
																					</c:if>
																				</div>
																				<div id="STax">
																					<c:if test="${not empty SalesTax}">
																						<input type="hidden" id="txSize" value='${SalesTax.size()}'>
																						<c:forEach items="${SalesTax}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.salesTaxID}' id='${loop.index}txID' />
																							<input type="hidden" value='${curObject.state}' id='${loop.index}txNm' />
																							<input type="hidden" value='${curObject.salesRate}' id='${loop.index}txRate' />
																						</c:forEach>
																					</c:if>
																				</div>

																				<div id="MSG">
																					<c:if test="${not empty SalesMessage}">
																						<input type="hidden" id="mSize" value='${SalesMessage.size()}'>
																						<c:forEach items="${SalesMessage}" var="curObject" varStatus="loop">
																							<input type="hidden" value='${curObject.messageID}' id='${loop.index}msgID' />
																							<input type="hidden" value='${curObject.messageName}' id='${loop.index}msgNm' />
																						</c:forEach>
																					</c:if>
																				</div>

																			</div>

																			<table cellspacing="0" style="width: 100%;">
																				<tr>
																					<td align="left">
																						<table style="width: 100%;">
																							<tr>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.customertitle" /></th>
																										</tr>
																										<tr>
																											<td valign="top" style="font-size: 12px;"><select
																													name="custTitle" size="10" style="width: 140px" id="1$$"
																													onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.customertitle" />');"
																													onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.customertitle" />');"
																													onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.customertitle" />');">
																												<c:if test="${not empty customerTitle}">
																													<c:forEach items="${customerTitle}" var="curObject" varStatus="loop">
																														<option value='${curObject.titleID}'>${curObject.title}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" class="formbutton"
																													onclick="buttonClick('1$$','<spring:message code="BzComposer.datamanager.customertitle" />');"
																													name="s_title"
																													value='<spring:message code="BzComposer.datamanager.title" />'></TD>
																										</tr>
																									</table>
																								</td>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.shippingvia" /></th>
																										</tr>
																										<tr>
																											<td valign="top" style="font-size: 12px;"><select
																													name="shippingvia" size="10" style="width: 120px"
																													id="2$$"
																													onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.shippingvia" />');"
																													onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.shippingvia" />');"
																													onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.shippingvia" />');">
																												<c:if test="${not empty Via}">
																													<c:forEach items="${Via}" var="curObject" varStatus="loop">
																														<option value='${curObject.shipCarrierID}'>${curObject.shipCarrierName}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" class="formbutton"
																													onclick="buttonClick('2$$','<spring:message code="BzComposer.datamanager.shippingvia" />');"
																													name="s_shippVia"
																													value='<spring:message code="BzComposer.datamanager.shippingvia" />'>
																											</TD>
																										</tr>
																									</table>
																								</td>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.representative" /></th>
																										</tr>
																										<tr>
																											<td style="font-size: 12px;"><select name="sRep"
																																				 size="10" style="width: 120px" id="3$$"
																																				 onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.representative" />');"
																																				 onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.representative" />');"
																																				 onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.representative" />');">
																												<c:if test="${not empty SalesRep}">
																													<c:forEach items="${SalesRep}" var="curObject" varStatus="loop">
																														<option value='${curObject.salesRepID}'>${curObject.salesRepName}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" name="s_rep" class="formbutton"
																													onclick="buttonClick('3$$','<spring:message code="BzComposer.datamanager.representative" />');"
																													value='<spring:message code="BzComposer.datamanager.representative" />'></TD>
																										</tr>
																									</table>
																								</td>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.terms" /></th>
																										</tr>
																										<tr>
																											<td style="font-size: 12px;"><select name="sTerms"
																																				 size="10" style="width: 120px" id="4$$"
																																				 onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.terms" />');"
																																				 onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.terms" />');"
																																				 onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.terms" />');">
																												<c:if test="${not empty salesTerms}">
																													<c:forEach items="${salesTerms}" var="curObject" varStatus="loop">
																														<option value='${curObject.termId}'>${curObject.termName}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" name="s_terms" class="formbutton"
																													onclick="buttonClick('4$$','<spring:message code="BzComposer.datamanager.terms" />');"
																													value='<spring:message code="BzComposer.datamanager.terms" />'></TD>
																										</tr>
																									</table>
																								</td>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.type" /></th>
																										</tr>
																										<tr>
																											<td style="font-size: 12px;"><select name="sType"
																																				 size="10" style="width: 120px" id="5$$"
																																				 onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.type" />');"
																																				 onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.type" />');"
																																				 onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.type" />');">
																												<c:if test="${not empty SalesCatType}">
																													<c:forEach items="${SalesCatType}" var="curObject" varStatus="loop">
																														<option value='${curObject.cvCategoryID}'>${curObject.cvCategoryName}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" name="s_type" class="formbutton"
																													onclick="buttonClick('5$$','<spring:message code="BzComposer.datamanager.type" />');"
																													value='<spring:message code="BzComposer.datamanager.type" />'></TD>
																										</tr>
																									</table>
																								</td>
																								<td>
																									<table width="150" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.paymentmethod" /></th>
																										</tr>
																										<tr>
																											<td style="font-size: 12px;"><select
																													name="sPayType" size="10" style="width: 140px" id="7$$"
																													onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.paymentmethod" />');"
																													onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.paymentmethod" />');"
																													onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.paymentmethod" />');">
																												<c:if test="${not empty SalesPaymentMethod}">
																													<c:forEach items="${SalesPaymentMethod}" var="curObject" varStatus="loop">
																														<option value='${curObject.paymentTypeId}'>${curObject.paymentTypeName}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" class="formbutton"
																													onclick="buttonClick('7$$','<spring:message code="BzComposer.datamanager.paymentmethod" />');"
																													name="s_payType"
																													value='<spring:message code="BzComposer.datamanager.paymentmethod" />'>
																											</TD>
																										</tr>
																									</table>
																								</td>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.creditcard" /></th>
																										</tr>
																										<tr>
																											<td style="font-size: 12px;"><select
																													name="sCreditCard" size="10" style="width:120px"
																													id="8$$"
																													onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.creditcard" />');"
																													onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.creditcard" />');"
																													onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.creditcard" />');">
																												<c:if test="${not empty CreditCardType}">
																													<c:forEach items="${CreditCardType}" var="curObject" varStatus="loop">
																														<option value='${curObject.ccTypeID}'>${curObject.ccTypeName}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" class="formbutton"
																													onclick="buttonClick('8$$','<spring:message code="BzComposer.datamanager.creditcard" />');"
																													name="s_creditcard"
																													value='<spring:message code="BzComposer.datamanager.creditcard" />'>
																											</TD>
																										</tr>
																									</table>
																								</td>
																							</tr>

																							<tr>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.message" /></th>
																										</tr>
																										<tr>
																											<td style="font-size: 12px;"><select
																													name="sMessage" size="10"
																													style="overflow: auto; width: 120px" id="9$$"
																													onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.message" />');"
																													onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.message" />');"
																													onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.message" />');">
																												<c:if test="${not empty SalesMessage}">
																													<c:forEach items="${SalesMessage}" var="curObject" varStatus="loop">
																														<option value='${curObject.messageID}'>${curObject.messageName}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" class="formbutton"
																													onclick="buttonClick('9$$','<spring:message code="BzComposer.datamanager.message" />');"
																													name="s_msg"
																													value='<spring:message code="BzComposer.datamanager.message" />'></TD>
																										</tr>
																									</table>
																								</td>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.tax" /></th>
																										</tr>
																										<tr>
																											<td style="font-size: 12px;"><select
																													name="sMessage" size="10" style="width: 120px" id="10$$"
																													onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.tax" />');"
																													onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.tax" />');"
																													onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.tax" />');">
																												<c:if test="${not empty SalesTax}">
																													<c:forEach items="${SalesTax}" var="curObject" varStatus="loop">
																														<option value='${curObject.salesTaxID}'>${curObject.state}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" class="formbutton"
																													onclick="buttonClick('10$$','<spring:message code="BzComposer.datamanager.tax" />');"
																													name="s_tax"
																													value='<spring:message code="BzComposer.datamanager.tax" />'></TD>
																										</tr>
																									</table>
																								</td>
																								<td>
																									<table width="130" height="180"
																										   class="table-notifications">
																										<tr>
																											<th style="font-size: 14px;"><spring:message code="BzComposer.datamanager.location" /></th>
																										</tr>
																										<tr>
																											<td style="font-size: 12px;"><select
																													name="sLocation" size="10" style="width: 120px" id="6$$"
																													onkeydown="callClick(this.id,'<spring:message code="BzComposer.datamanager.location" />');"
																													onkeyup="callClick(this.id,'<spring:message code="BzComposer.datamanager.location" />');"
																													onclick="callClick(this.id,'<spring:message code="BzComposer.datamanager.location" />');">
																												<c:if test="${not empty SalesLocation}">
																													<c:forEach items="${SalesLocation}" var="curObject" varStatus="loop">
																														<option value='${curObject.locationId}'>${curObject.locationName}</option>
																													</c:forEach>
																												</c:if>
																											</select></td>
																										</tr>
																										<tr>
																											<TD align="center" style="font-size: 14px;"><input
																													type="button" class="formbutton"
																													onclick="buttonClick('6$$','<spring:message code="BzComposer.datamanager.location" />');"
																													name="s_location"
																													value='<spring:message code="BzComposer.datamanager.location" />'>
																											</TD>
																										</tr>
																									</table>
																								</td>
																								<td colspan="4" align="center"  style="background-color: rgb(247, 247, 247);">
																									<table class="table-notifications">
																										<tr>
																											<td colspan="2"  align="center" style="font-size: 25px;">
																												<h4 id="datamanager12"><spring:message code="BzComposer.datamanager.setvalues" /></h4>
																											</td>
																										</tr>
																										<tr>
																											<td>
																												<span id="selectedTitle" style="font-size: 14px; font-weight: normal; color: #000DDD;"></span>
																											</td>
																											<td  id="taxRate" style="visibility: hidden">
                                                                                                                <spring:message code="BzComposer.datamanager.taxrate" />
                                                                                                                <input type="text" id="tax_rate" name="taxRateVal" onkeydown="return numbersonly(event,this.value)" tabindex="2" />
                                                                                                                <input type="hidden" name="sTitleval" id="sTitleId" value="" />
                                                                                                                <input type="hidden" name="sOldval" id="sOldId" value="" />
                                                                                                                <input type="hidden" name="sNewval" id="sNewId" value="" />
                                                                                                                <input type="hidden" name="sNewvalID" id="newIDD" value="" />
                                                                                                            </td>
																										</tr>
																										<tr>
																											<td colspan="2"  align="center" style="font-size: 14px; width: 70%; padding-left: 10px;">
																												<spring:message code="BzComposer.datamanager.description" />
																												<input type="text" name="des" id="descriptionId" tabindex="1">
																											</td>
																										</tr>
																										<tr>
																											<td colspan="2" align="center" style="font-size: 14px; ">
																												<a class="formbutton" name="save" onclick="callSave();" tabindex="3" style="font-size: 14px;"><spring:message code="BzComposer.global.add" /></a>
																												<a class="formbutton" name="save" onclick="callUpdate();" tabindex="3" style="font-size: 14px;"><spring:message code="BzComposer.global.update" /></a>
																												<a name="Delete" class="formbutton" onclick="callDelete();" tabindex="4" style="font-size: 14px;"><spring:message code="BzComposer.global.delete" /></a>
																												<a name="Cancel" class="formbutton" onclick="callClear();" tabindex="5" style="font-size: 14px;"><spring:message code="BzComposer.global.clear" /></a>
																											</td>
																										</tr>

																										<!-- 						<tr> -->
																										<!-- 							<td colspan="4"><input type="button" class="formbutton" name="save" -->
																										<!-- 								onclick="callSave();" -->
																											<%-- 								value='<spring:message code="BzComposer.datamanager.Save" />' --%>
																										<!-- 								tabindex="3"> -->
																										<!-- 							<input type="button" name="Delete" class="formbutton" -->
																										<!-- 								onclick="callDelete();" -->
																											<%-- 								value='<spring:message code="BzComposer.datamanager.Delete" />' --%>
																										<!-- 								tabindex="4"> -->
																										<!-- 							<input type="button" name="Cancel" class="formbutton" -->
																										<!-- 								onclick="callCancel();" -->
																											<%-- 								value='<spring:message code="BzComposer.datamanager.Cancel" />' --%>
																										<!-- 								tabindex="5"></td> -->


																										<!-- 								</tr> -->
																									</table>
																								</td>
																							</tr>
																						</table>
																					</td>
																				</tr>
																			</table>
																		</div>
																		<table>
																			<tr>
																				<td align="center" colspan="2">
																		</table>
																		<!-- end Contents -->
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
											<!-- customerInvoice Ends -->
										</td>
									</tr>
								</table>
								<div>
									<form:hidden path="empStateID" />
									<form:hidden path="labelName" />
									<form:hidden path="fileName" />
								</div>
								<div>
									<input type="hidden" name="tabid" id="tabid" value="" />
								</div>
							</div>
							<div align="center">
								<input type="submit" name="Submit" style="font-size:1em;" onclick="SaveValues()"
									   value="<spring:message code="BzComposer.global.save"/>"/>
								<input type="reset" name="Cancel" onclick="RevokeValues()" style="font-size:1em;"
									   value="<spring:message code="BzComposer.global.cancel"/>"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
<jsp:include page="/WEB-INF/jsp/include/footer.jsp" />
</body>
<script type="text/javascript">
	sOldVal = "";

	function callClick(idVal, ttl) {
		sOldVal = "";
		debugger;
		document.getElementById("datamanager12").innerHTML = ttl;
		document.getElementById("selectedTitle").innerHTML = ttl;
		document.getElementById("descriptionId").value = "";
		document.getElementById("tax_rate").value = "";
		if (idVal == "10$$")
			document.getElementById("taxRate").style.visibility = "visible";
		else
			document.getElementById("taxRate").style.visibility = "hidden";

		for (i = 1; i <= 10; i++) {
			document.getElementById(i + "$$").style.background = '#ffffff'
			if (idVal != i + "$$")
				document.getElementById(i + "$$").selectedIndex = -1;
		}
		document.getElementById("" + idVal).style.background = '#AAAAAA';
		id = document.getElementById("" + idVal).value;
		var i = 0;
		var sval;
		if (ttl == "CUSTOMER TITLE") {
			size = document.getElementById('ctSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "ctitleID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "ctitleNm").value;
					sOldVal = document.getElementById(i + "ctitleNm").value;
					document.getElementById("newIDD").value = id;
					break;
				}
			}
		} else if (ttl == "REP") {
			size = document.getElementById('rSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "repID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "repNm").value;
					sOldVal = document.getElementById(i + "repNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		} else if (ttl == "TERMS") {
			size = document.getElementById('tSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "termID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "termNm").value;
					sOldVal = document.getElementById(i + "termNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		} else if (ttl == "MESSAGE") {
			size = document.getElementById('mSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "msgID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "msgNm").value;
					sOldVal = document.getElementById(i + "msgNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		} else if (ttl == "TYPE") {
			size = document.getElementById('catSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "typeID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "typeNm").value;
					sOldVal = document.getElementById(i + "typeNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		} else if (ttl == "LOCATION") {
			size = document.getElementById('lSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "locID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document.getElementById(i + "locNm").value;
					sOldVal = document.getElementById(i + "locNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		} else if (ttl == "PAYMENT METHOD") {
			size = document.getElementById('pSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "pmtID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "pmtNm").value;
					sOldVal = document.getElementById(i + "pmtNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		} else if (ttl == "CREDIT CARD") {
			size = document.getElementById('ccSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "ccTypeID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "ccTypeNm").value;
					sOldVal = document.getElementById(i + "ccTypeNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		} else if (ttl == "SHIPPING VIA") {
			size = document.getElementById('shSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "shipID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "shipNm").value;
					sOldVal = document.getElementById(i + "shipNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		} else if (ttl == "TAX") {
			size = document.getElementById('txSize').value;
			for (i = 0; i < size; i++) {
				idd = document.getElementById(i + "txID").value;
				if (id == idd) {
					document.getElementById("descriptionId").value = document
							.getElementById(i + "txNm").value;
					document.getElementById("tax_rate").value = document
							.getElementById(i + "txRate").value;
					sOldVal = document.getElementById(i + "txNm").value;
					document.getElementById("newIDD").value = idd;
					break;
				}
			}
		}

	}

	function buttonClick(idVal, ttl) {
		sOldVal = "";
		//alert("HH");
		document.getElementById("datamanager12").innerHTML = ttl;
		document.getElementById("selectedTitle").innerHTML = ttl;
		document.getElementById("descriptionId").value = "";
		document.getElementById("tax_rate").value = "";
		if (idVal == "10$$")
			document.getElementById("taxRate").style.visibility = "visible";
		else
			document.getElementById("taxRate").style.visibility = "hidden";

		for (i = 1; i <= 10; i++) {
			document.getElementById(i + "$$").style.background = '#ffffff'
			//if(idVal!=i+"$$")
			document.getElementById(i + "$$").selectedIndex = -1;
		}
		document.getElementById("" + idVal).style.background = '#AAAAAA';
	}

	function callSave() {
		//debugger;
		sType = document.getElementById("selectedTitle").innerHTML
		sOld = sOldVal;
		sNew = document.getElementById("descriptionId").value;
		newID = document.getElementById("newIDD").value;
		taxrate = document.getElementById("tax_rate").value;
		document.getElementById("sTitleId").value = sType;
		document.getElementById("sOldId").value = sOld;
		document.getElementById("sNewId").value = sNew;
		document.getElementById("newIDD").value = newID;
		if (sOld != "") {
			showemptynewDescriptionDialog();
			document.getElementById("descriptionId").focus();
			return false;
		}
		if (sType == "") {
			alert("Please choose item");
			/* return selectItemDialog(); */
			//window.location = "Configuration?tabid=DM_Save";
		} else {
			if (taxrate == "" && sType == "TAX") {
				//alert("Please enter tax rate");
				return selectTaxRateDialog();
				document.getElementById("tax_rate").focus();
			}
			if (sNew == "") {
				//alert("Description field should not be blank");
				return showBlankDiscriptionDialog();
				document.getElementById("descriptionId").focus();
			} else {
				event.preventDefault();
				$("#saveSelectedItemDialog").dialog({
					resizable: false,
					height: 200,
					width: 400,
					modal: true,
					buttons: {
						"<spring:message code='BzComposer.global.ok'/>": function () {
							$(this).dialog("close");
							document.getElementById("tabid").value = "DM_Save";
							document.forms['dmForm'].action = "Configuration?tabid=DM_Save";
							document.forms['dmForm'].submit();
						},
				<spring:message code='BzComposer.global.cancel'/> :

				function () {
					$(this).dialog("close");
					return false;
				}
			}
			})
				;
			}
		}
	}

	function callUpdate() {
		//debugger;
		sType = document.getElementById("selectedTitle").innerHTML
		sOld = sOldVal;
		sNew = document.getElementById("descriptionId").value;
		newID = document.getElementById("newIDD").value;
		taxrate = document.getElementById("tax_rate").value;
		document.getElementById("sTitleId").value = sType;
		document.getElementById("sOldId").value = sOld;
		document.getElementById("sNewId").value = sNew;
		document.getElementById("newIDD").value = newID;
		if (sOld == "") {
			showemptyupdateDescriptionDialog();
			return false;
		}
		if (sType == "") {
			alert("Please choose item");
			/* return selectItemDialog(); */
			//window.location = "Configuration?tabid=DM_Save";
		} else {
			if (taxrate == "" && sType == "TAX") {
				//alert("Please enter tax rate");
				return selectTaxRateDialog();
				document.getElementById("tax_rate").focus();
			}
			if (sNew == "") {
				//alert("Description field should not be blank");
				return showBlankDiscriptionDialog();
				document.getElementById("descriptionId").focus();
			} else {
				event.preventDefault();
				$("#saveSelectedItemDialog").dialog({
					resizable: false,
					height: 200,
					width: 400,
					modal: true,
					buttons: {
						"<spring:message code='BzComposer.global.ok'/>": function () {
							$(this).dialog("close");
							document.getElementById("tabid").value = "DM_Update";
							document.forms['dmForm'].action = "Configuration?tabid=DM_Update";
							document.forms['dmForm'].submit();
						},
				"<spring:message code='BzComposer.global.cancel'/>" : function () {
					$(this).dialog("close");
					return false;
				}
			}
			})
				;
			}
		}
	}

	function callDelete() {
		sType = document.getElementById("selectedTitle").innerHTML
		sOld = sOldVal;
		newID = document.getElementById("newIDD").value;
		sNew = document.getElementById("descriptionId").value;
		document.getElementById("sTitleId").value = sType;
		document.getElementById("sOldId").value = sOld;
		document.getElementById("sNewId").value = sNew;
		document.getElementById("newIDD").value = newID;
		if (sNew == "") {
			//alert("Description field should not be blank");
			return showBlankDiscriptionDialog();
			document.getElementById("descriptionId").focus();
		} else {
			return deleteSelectedItemDialog();
			/* var x=window.confirm("Do you want to delete "+sNew+"?")
            if (x){
                document.forms[0].action = "Configuration?tabid=DM_Delete";
                document.forms[0].submit();
             } */
		}
	}

	function callClear() {
		document.getElementById("selectedTitle").innerHTML = "";
		document.getElementById("descriptionId").value = "";
		document.getElementById("tax_rate").value = "";
		document.getElementById("newIDD").value = "";
		sOldVal = "";
		for (i = 1; i <= 10; i++) {
			document.getElementById(i + "$$").style.background = '#ffffff'
			document.getElementById(i + "$$").selectedIndex = -1;
		}
	}

	function showemptynewDescriptionDialog() {
		event.preventDefault();
		$("#showemptynewDescriptionDialog").dialog({
			resizable: false,
			height: 200,
			width: 350,
			modal: true,
			buttons: {
				"<spring:message code='BzComposer.global.ok'/>": function () {
					$(this).dialog("close");
				}
			}
		});
		return false;
	}

	function showemptyupdateDescriptionDialog() {
		event.preventDefault();
		$("#showemptyupdateDescriptionDialog").dialog({
			resizable: false,
			height: 200,
			width: 350,
			modal: true,
			buttons: {
				"<spring:message code='BzComposer.global.ok'/>": function () {
					$(this).dialog("close");
				}
			}
		});
		return false;
	}

	function numbersonly(e, val) {
		var temp = val.indexOf(".");
		var key = e.charCode ? e.charCode : e.keyCode;
		if (window.event) {
			if (window.event.ctrlKey)
				isCtrl = true;
			else
				isCtrl = false;
		} else {
			if (e.ctrlKey)
				isCtrl = true;
			else
				isCtrl = false;
		}
		if (isCtrl) {
			if ("v" == String.fromCharCode(key).toLowerCase()) {
				return false;
			}
			if ("x" == String.fromCharCode(key).toLowerCase()) {
				return false;
			}
		} else if (key != 8) {
			var str = String(val);
			var temp = val.indexOf(".");
			index = 0;
			for (i = 0; i < str.length; i++) {
				if (str.charAt(i) == '.') {
					index = 1;
					break;
				}
			}
			if (key == 46 && temp == -1) {
				return true;
			} else if (key == 37 || key == 39) {
				return true;
			} else if (key == 110 && index == 0) {
				return true;
			} else if (key == 190 && index == 0) {
				return true;
			} else if (key >= 96 && key <= 105) {
				return true;
			} else if (key < 48 || key > 57) //if not a number
				return false; //disable key press
		}
	}
</script>
</html>
<div id="selectItemDialog" style="display: none;">
	<p>
		<spring:message code="BzComposer.datamanager.selectitemvalidation"/>
	</p>
</div>
<div id="selectTaxRateDialog" style="display: none;">
	<p>
		<spring:message code="BzComposer.datamanager.entertaxratevalidation"/>
	</p>
</div>
<div id="showBlankDiscriptionDialog" style="display: none;">
	<p>
		<spring:message code="BzComposer.datamanager.enterdescriptionvalidation"/>
	</p>
</div>
<div id="saveSelectedItemDialog" style="display: none;">
	<p>
		<spring:message code="BzComposer.datamanager.saveselecteditem"/>
	</p>
</div>
<div id="deleteSelectedItemDialog" style="display: none;">
	<p>
		<spring:message code="BzComposer.datamanager.deleteselecteditem"/>
	</p>
</div>
<!-- Dialog box used in this page -->
<div id="showemptynewDescriptionDialog" style="display:none;">
	<p>Please Enter Description Value First</p>
</div>
<div id="showemptyupdateDescriptionDialog" style="display:none;">
	<p>Please Select Update Description Vlaue First</p>
</div>