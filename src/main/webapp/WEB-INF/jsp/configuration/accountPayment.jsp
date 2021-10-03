<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/WEB-INF/jsp/include/sessionExpired.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/jsp/include/headlogo.jsp" />
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
<jsp:include page="/WEB-INF/jsp/include/menu.jsp" />
<title><spring:message code="BzComposer.accountingandpaymenttitle" /></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/custom.js"></script>
<link href="${pageContext.request.contextPath}/tableStyle/tab/jquery-ui-tab.css" rel="stylesheet" media="screen" />
<script src="${pageContext.request.contextPath}/tableStyle/tab/jquery-ui.js"></script>
<!-- Remember to include jQuery :) -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>  -->

<!-- jQuery Modal -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" /> -->

 <style type="text/css">
 /* * {
  padding: 0;
  margin: 5px;
  text-align: center;
} */
.modal {
  display: none; /* Hidden by default */
}
.fonts
{
	font: 12px;
}
.fontsTitle
{
	font:12px; padding: 5px;
}
</style>

<script type="text/javascript">
function toggleFunction() {
	debugger;
  var x = document.getElementById("divtoggle");
  var lftmenu = document.getElementById("leftMenu");
  if (x.style.display === "none") {
    x.style.display = "block";
    lftmenu.style.width = "180px";
    lftmenu.style.position = "relative";
  } else {
    x.style.display = "none";
    lftmenu.style.width = "0";
    lftmenu.style.position = "absolute";
  }
} 
$(function() {
    $( "#tabs1" ).tabs();
  });
 $(document).ready(function()
{
	var id = '<%= request.getAttribute("selectedId")%>';
	var pId = '<%=request.getAttribute("paymentId")%>';
	var cateID = '<%= request.getAttribute("cateId")%>';
	
	var arCatID = '<%= request.getAttribute("arCatId")%>';
	var poCatID = '<%= request.getAttribute("poCatId")%>';
	var bpCatID = '<%= request.getAttribute("bpCatId")%>';
	
	var arReceive = '<%= request.getAttribute("arReceiveType")%>';
	var poReceive = '<%= request.getAttribute("poReceiveType")%>';
	var bpReceive = '<%= request.getAttribute("bpReceiveType")%>';
	
	var arDeposit = '<%= request.getAttribute("arDepositTo")%>';
	var poDeposit = '<%= request.getAttribute("poDepositTo")%>';
	var bpDeposit = '<%= request.getAttribute("bpDepositTo")%>';
	
	$('select[id="defaultDepositToId"]').find('option[value="'+id+'"]').attr("selected",true);
	$('select[id="defaultPaymentMethodId"]').find('option[value="'+pId+'"]').attr("selected",true);
	$('select[id="defaultCategoryId"]').find('option[value="'+cateID+'"]').attr("selected",true);
	
	$('select[id="arCategory"]').find('option[value="'+arCatID+'"]').attr("selected",true);
	$('select[id="poCategory"]').find('option[value="'+poCatID+'"]').attr("selected",true);
	$('select[id="bpCategory"]').find('option[value="'+bpCatID+'"]').attr("selected",true);
	
	$('select[id="arReceivedType"]').find('option[value="'+arReceive+'"]').attr("selected",true);
	$('select[id="poReceivedType"]').find('option[value="'+poReceive+'"]').attr("selected",true);
	$('select[id="bpReceivedType"]').find('option[value="'+bpReceive+'"]').attr("selected",true);
	
	$('select[id="arDepositTo"]').find('option[value="'+arDeposit+'"]').attr("selected",true);
	$('select[id="poDepositTo"]').find('option[value="'+poDeposit+'"]').attr("selected",true);
	$('select[id="bpDepositTo"]').find('option[value="'+bpDeposit+'"]').attr("selected",true);
	
	if(document.configurationForm.scheduleDays.value == 0)
	{
		$("#noNeedToSetup").prop("checked",true);
		$("#needSetup").prop("checked",false);
	}
	else
	{
		$("#needSetup").prop("checked",true);
		$("#noNeedToSetup").prop("checked",false);
	}
	
	if(document.configurationForm.reimbursementSettings.value == 2)
	{
		$("#askWhatToDo").prop("checked",true);
		$("#dontAddAny").prop("checked",false);	
		$("#timeForPrompt").prop("checked",false);
	}
	else if(document.configurationForm.reimbursementSettings.value == 1)
	{
		$("#askWhatToDo").prop("checked",false);
		$("#dontAddAny").prop("checked",true);
		$("#timeForPrompt").prop("checked",false);
	}
	else
	{
		$("#askWhatToDo").prop("checked",false);
		$("#dontAddAny").prop("checked",false);
		$("#timeForPrompt").prop("checked",true);
	}
	
	if(document.configurationForm.scheduleDays.value >0)
	{
		$("#scheduleDays").prop("checked",true);
		var scheduleDays = $("#scheduleDays").val();
		document.configurationForm.scheduleDays.value = scheduleDays;
	}
	else
	{
		$("#noNeedToSetup").prop("checked",true);
		document.configurationForm.scheduleDays.value = 0;
	}
	
	/* if($("#noNeedToSetup").prop("checked",true))
	{
		alert("NoNeedToSetup is checked");
		document.configurationForm.scheduleDays.value = 0;
	}
	else
	{
		alert("NeedSetup is checked");
		var scheduleDays = $("#scheduleDays").val();
		document.configurationForm.scheduleDays.value = scheduleDays;
	} */
	
	if(document.configurationForm.reimbursementSettings.value == 2)
	{
		$("#askWhatToDo").prop("checked",true);
		document.configurationForm.reimbursementSettings.value = 2;
	}
	
	else if(document.configurationForm.reimbursementSettings.value == 1)
	{
		$("#dontAddAny").prop("checked",true);
		document.configurationForm.reimbursementSettings.value = 1;
	}
	
	else
	{
		$("#timeForPrompt").prop("checked",true);
		document.configurationForm.reimbursementSettings.value = 0;
	}
	
	/* if($("#askWhatToDo").prop("checked",true))
	{
		alert("askWhatToDo is checked");
		document.configurationForm.reimbursementSettings.value = 2;
	}
	else if($("#dontAddAny").prop("checked",true))
	{
		alert("dontNeedAny is checked");
		document.configurationForm.reimbursementSettings.value = 1;
	}
	else
	{
		alert("timeForPrompt is checked");
		document.configurationForm.reimbursementSettings.value = 0;
	} */
	
	$('#assessFinanceCharge').change(function()
	{
		var isChecked = "<%= request.getAttribute("isChecked")%>";
		if($(this).prop("checked") == true)
		{
			//alert("assessFinanceCharge is checked.");
	        $("#assessFinanceCharge").attr('checked', true);
	        isChecked = "on"; 
		}
	    else if($(this).prop("checked") == false)
	    {
			//alert("assessFinanceCharge is unchecked.");
	        $("#assessFinanceCharge").attr('checked', false);
	        isChecked = "off";
		}	
	    else
	    {
	    	//alert("assessFinanceCharge is unchecked.");
	        $("#assessFinanceCharge").attr('checked', true);
	    	document.configurationForm.assessFinanceCharge.value = isChecked;
	    }	
		$("#assessFinanceCharge").val(isChecked);
	});
	
	document.configurationForm.showCombinedBilling.value = '<%= request.getAttribute("showCmbValue")%>';
	var template = '<%= request.getAttribute("billingTemplateID")%>';
	 
	$('select[id="showBillingStatStyle"]').find('option[value="'+template+'"]').attr("selected",true);
});
 
 function numbersonly(e,val)
 {
 	var temp=val.indexOf(".");
 	var unicode=e.charCode? e.charCode : e.keyCode;
 	if (unicode!=8)
 	{
  		//if the key isn't the backspace key (which we should allow)
 		if(unicode==46 && temp==-1)
 		{
  			return true;
 		} 
 		else 
 		if (unicode<48||unicode>57) //if not a number
 			return false; //disable key press
 	}
 }
 
 /* Added on 04-05-2020 */
 function setDiv()
	{
		var value = $("#stores option:selected").val();
		//alert("selected Value:"+value);
		if(value == 39)
		{
			document.getElementById("forOnlineOption").style.display = "block";
			document.getElementById("forCdromusaOption").style.display = "none";
		}
		else
		{
			document.getElementById("forOnlineOption").style.display = "none";
			document.getElementById("forCdromusaOption").style.display = "block";
		}
	}
	function seteSalesStores()
	{
		$('select[id="eSalesChannels"]').find('option').attr("selected",false);
		var eSalesStore = $.trim($("#eSalesStoreId option:selected").text());	//selected select option value
		var selectedeSalesStore = $.trim($("#eSalesStoreId option:selected").val()); //selected select option id
		var ab = $.trim($('select[id="eSalesStoreId"]').find('option[id="'+selectedeSalesStore+'"]').val());	//abbreviation of selected option 
		
		var storeChannel = $.trim($('select[id="eSalesStoreId"]').find('option[id="'+eSalesStore+'"]').val());
		
		//alert("Selected eSales Store Value:"+eSalesStore+"\nId is:"+selectedeSalesStore+"\nAbbreviation is:"+ab+"\nStoreType Id is:"+storeChannel);
		
		$('select[id="eSalesStoreId"]').find('option[id="'+selectedeSalesStore+'"]').attr("selected",true);
		$('select[id="eSalesStoreId"]').find('option[id="'+eSalesStore+'"]').attr("selected",true);
		$('select[id="eSalesChannels"]').find('option[id="'+storeChannel+'"]').attr("selected",true);
		
		$("#storeTypeName").val(eSalesStore);
		$("#eSalesAbbreviation").val(ab);
		
	}
	
	function clearFields()
	{
		$("#storeTypeName").val("");
		$("#eSalesAbbreviation").val("");
		$("#btnAddeBayCategory").attr('disabled',false);
	}
</script>
</head>
<body onload="init();">
<!-- begin shared/header -->
<form:form name="configurationForm" enctype="MULTIPART/FORM-DATA" method="post" id="accountPaymentfrm" modelAttribute="configDto">
<div id="ddcolortabsline">&nbsp;</div>
<div id="cos">
<div class="statusquo ok">
<div id="hoja">
<div id="blanquito">
<div id="padding">
<div class="fontsTitle">
	<span style="font-size: 1.1em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px; border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
		<spring:message code="BzComposer.configuration.configurationtitle"/>
	</span>
</div>
<div>
	<div>
		<c:if test="${not empty Labels}">
            <input type="hidden" name="lsize" id="lblsize" value='${Labels}' />
            <c:forEach items="${Labels}" var="lbl" varStatus="loop">
                <input type="hidden" id='${loop.index}lid' name='${loop.index}lidname' value='${lbl.value}' />
                <input type="hidden" id='${loop.index}lname' name='${loop.index}lnm' value='${lbl.label}' />
            </c:forEach>
		</c:if>
	</div>
	<div id="table-negotiations" style="padding: 0; border: 1px solid #ccc;">
		<span style="font-size:30px;cursor:pointer; margin-left: 20px;" onclick="toggleFunction()">&#9776;</span>
		<table cellspacing="0"  style="border: 0;width: 100%;overflow-y:scroll;" class="section-border">
			<tr>
				<td id="leftMenu" style="position: relative; width: 180px;">
				<table>
					<tr>
						<td>
							<jsp:include page="menuPage.jsp" />
						</td>
					</tr>
					<%-- <tr align="center">
						<td><input type="button" name="Revoke" class="formButton"
							onclick="RevokeValues();"
							value='<spring:message code="BizComposer.Configuration.RevokeButton"/>' />
							<input type="button" name="Save" class="formButton"
							onclick="SaveValues();"
							value='<spring:message code="BizComposer.Configuration.SaveButton"/>' />
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr> --%>
				</table>
				</td>
				<td valign="top" >
					<!-- Account&Payment Starts -->
					<div id="accountPayment"  style="display:none;padding: 0; position: relative; left: 0;" >
						<div id="tabs1" style="height:auto;">
  							<ul>
    							<li style="font-size: 12px;"><a href="#AccountSetting">
    								<spring:message code="BzComposer.configuration.tab.accountsetting" /></a>
   								</li>
    							<li style="font-size: 12px;"><a href="#AccountReceivable">
    								<spring:message code="BzComposer.configuration.tab.accountrecivable" /></a>
   								</li>
    							<li style="font-size: 12px;"><a href="#POPayable">
    								<spring:message code="BzComposer.configuration.tab.popayable" /></a>
   								</li>
    							<li style="font-size: 12px;"><a href="#billing">
    								Billing</a>
   								</li>
    							<li style="font-size: 12px;"><a href="#PaymentType">
    								Payment Type</a>
   								</li>
   								<li style="font-size: 12px;"><a href="#ReceivedType">
    								Received Type</a>
   								</li>
  							</ul>
							<div id="AccountSetting">
								<div id="content1" class="tabPage">
   									<!-- add here the content of first tab -->
										<table class="table-notifications" width="100%">
											<tr>
												<th colspan="4" align="left" style="font-size: 12px; padding: 5px;">
													<spring:message code="BzComposer.configuration.defaultaccountsetting" />
												</th>
											</tr>
											<tr>
												<td style="font-size: 12px;">
													<spring:message code="BzComposer.configuration.defaultpaymentmethod" />:
												</td>
												<td style="font-size: 12px;">
													<form:select path="defaultPaymentMethodId" id="defaultPaymentMethodId">
														<c:if test="${not empty configDto.listOfExistingAccounts}">
														    <c:forEach items="${configDto.listOfExistingAccounts}" var="objList1">
																<option value="${objList1.accountNumber}">${objList1.accountName}</option>
															</c:forEach>
														</c:if>
													</form:select>
												</td>
												<td style="font-size: 12px;">
													<spring:message code="BzComposer.configuration.defaultreceivetype"/>:
												</td>
												<td style="font-size: 12px;">
													<form:select path="selectedPaymentId">
													    <c:if test="${not empty configDto.listOfExistingPayment}">
                                                            <c:forEach items="${configDto.listOfExistingPayment}" var="objList1">
                                                                <option value="${objList1.paymentId}">${objList1.paymentName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
											</tr>
											<tr>
												<td style="font-size: 12px;">
													<spring:message code="BzComposer.configuration.defaultcategory" />:
												</td>
												<td style="font-size: 12px;">
													<form:select path="defaultCategoryId" id="defaultCategoryId">
														<c:if test="${not empty configDto.listOfExistingCategory}">
                                                            <c:forEach items="${configDto.listOfExistingCategory}" var="objList1">
                                                                <option value="${objList1.selectedCategoryId}">${objList1.categoryName}&nbsp;${objList1.categoryNumber}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
												<td style="font-size: 12px;">
													<spring:message code="BzComposer.configuration.depositto"/>:
												</td>
												<td style="font-size: 12px;">
													<form:select path="defaultDepositToId" id="defaultDepositToId">
													    <c:if test="${not empty configDto.listOfExistingAccounts}">
                                                            <c:forEach items="${configDto.listOfExistingAccounts}" var="objList1">
                                                                <option value="${objList1.defaultDepositToId}">${objList1.accountName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
											</tr>
											<tr>
												<th colspan="4" align="left" style="font-size: 12px;  padding: 5px;"><spring:message code="BzComposer.configuration.paymentschdulesetting" /></th>
											</tr>
											<tr colspan="2">
												<td style="font-size: 12px;">
													<form:radiobutton path="needSetUp" id="noNeedToSetup" value="0" />
													&nbsp;<spring:message code="BzComposer.configuration.noneedtosetup"/>
												</td>
											</tr>
											<tr colspan="2">
												<td style="font-size: 12px;">
													<form:radiobutton path="needSetUp" id="needSetup" value="0" />&nbsp;
													<form:input path="scheduleDays" id="scheduleDays" />
													&nbsp;<spring:message code="BzComposer.configuration.daysbeforeduedate"/>
												</td>
											</tr>
										<tr>
											<th colspan="4" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.reimbursementsettings" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<form:radiobutton path="reimbursementSettings" value="0" id="timeForPrompt" />
												&nbsp;<spring:message code="BzComposer.configuration.reimbursementsettings.timeforprompt"/>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<form:radiobutton path="reimbursementSettings" value="1" id="dontAddAny" />
												&nbsp;<spring:message code="BzComposer.configuration.reimbursementsettings.dontaddany"/>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<form:radiobutton path="reimbursementSettings" value="2" id="askWhatToDo" />
												&nbsp;<spring:message code="BzComposer.configuration.reimbursementsettings.askwhattodo"/>
											</td>
										</tr>
										<tr>
											<th colspan="4" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.applyingcharges" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<%-- <input type="checkbox" name="financeCharge"/> <spring:message code="Bizcomposer.financeCharge"/> --%>
												<input type="checkbox" name="assessFinanceCharge" id="assessFinanceCharge" value="${configDto.assessFinanceCharge}" ${configDto.assessFinanceCharge=='on'?'checked':''} />&nbsp;
												<spring:message code="BzComposer.configuration.applyingcharges.assessfinancecharge" />
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<b><spring:message code="BzComposer.configuration.chargerate" /></b>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.annualinterestrate"/>:
											</td>
											<td style="font-size: 12px;">
												<%-- <form:input path="annualRate"></form:input> --%>
												<form:input path="annualInterestRate" id="annualInterestRate" onkeypress="return numbersonly(event,this.value);" />
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.minimumfinancerate"/>:
											</td>
											<td style="font-size: 12px;">
												<%-- <form:input path="financeCharge" value=""></form:input> --%>
												<form:input path="minCharge" id="minCharge" onkeypress="return numbersonly(event,this.value);" />
											</td>
										</tr>
										<tr>
											<td class=".fonts">
												<spring:message code="BzComposer.configuration.graceperiodindays"/>:
											</td>
											<td>
												<%-- <form:input path="gracePeriodDays" value=""></form:input> --%>
												<form:input path="gracePeriod" id="gracePeriod" onkeypress="return numbersonly(event,this.value);" />
											</td>
										</tr>
										<tr>
											<th colspan="4" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.setubudgetyear" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.startmonth"/>:
											</td>
											<td style="font-size: 12px;">
												<form:select path="startMonth" id="startMonth">
													<form:option value="0"><spring:message code="BzComposer.configuration.month.january"/></form:option>
													<form:option value="1"><spring:message code="BzComposer.configuration.month.february"/></form:option>
													<form:option value="2"><spring:message code="BzComposer.configuration.month.march"/></form:option>
													<form:option value="3"><spring:message code="BzComposer.configuration.month.april"/></form:option>
													<form:option value="4"><spring:message code="BzComposer.configuration.month.may"/></form:option>
													<form:option value="5"><spring:message code="BzComposer.configuration.month.june"/></form:option>
													<form:option value="6"><spring:message code="BzComposer.configuration.month.july"/></form:option>
													<form:option value="7"><spring:message code="BzComposer.configuration.month.august"/></form:option>
													<form:option value="8"><spring:message code="BzComposer.configuration.month.september"/></form:option>
													<form:option value="9"><spring:message code="BzComposer.configuration.month.october"/></form:option>
													<form:option value="10"><spring:message code="BzComposer.configuration.month.november"/></form:option>
													<form:option value="11"><spring:message code="BzComposer.configuration.month.december"/></form:option>
												</form:select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.endmonth"/>:
											</td>
											<td style="font-size: 12px;">
												<form:select path="endMonth" id="endMonth">
													<form:option value="0"><spring:message code="BzComposer.configuration.month.january"/></form:option>
													<form:option value="1"><spring:message code="BzComposer.configuration.month.february"/></form:option>
													<form:option value="2"><spring:message code="BzComposer.configuration.month.march"/></form:option>
													<form:option value="3"><spring:message code="BzComposer.configuration.month.april"/></form:option>
													<form:option value="4"><spring:message code="BzComposer.configuration.month.may"/></form:option>
													<form:option value="5"><spring:message code="BzComposer.configuration.month.june"/></form:option>
													<form:option value="6"><spring:message code="BzComposer.configuration.month.july"/></form:option>
													<form:option value="7"><spring:message code="BzComposer.configuration.month.august"/></form:option>
													<form:option value="8"><spring:message code="BzComposer.configuration.month.september"/></form:option>
													<form:option value="9"><spring:message code="BzComposer.configuration.month.october"/></form:option>
													<form:option value="10"><spring:message code="BzComposer.configuration.month.november"/></form:option>
													<form:option value="11"><spring:message code="BzComposer.configuration.month.december"/></form:option>
												</form:select>
											</td>
										</tr>
									</table>	
								</div>  
							</div>
		
					  		<div id="AccountReceivable">
					   			<div id="content2" class="tabPage">
					   				<!-- add here the content of second tab -->		
					   				<table class="table-notifications" width="100%">
										<tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.defaultaccountsetting" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.category" />:
											</td>
											<td style="font-size: 12px;">
												<form:select path="arCategory" id="arCategory">
												    <c:if test="${not empty configDto.listOfExistingCategory}">
                                                        <c:forEach items="${configDto.listOfExistingCategory}" var="objList1">
                                                            <option value="${objList1.arCategory}">${objList1.categoryName}&nbsp;${objList1.categoryNumber}</option>
                                                        </c:forEach>
                                                    </c:if>
												</form:select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.defaultreceivetype"/>:
											</td>
											<td style="font-size: 12px;">
												<form:select path="arReceivedType" id="arReceivedType">
												    <c:if test="${not empty configDto.listOfExistingPayment}">
                                                        <c:forEach items="${configDto.listOfExistingPayment}" var="objList1">
                                                            <option value="${objList1.arReceivedType}">${objList1.paymentName}</option>
                                                        </c:forEach>
                                                    </c:if>
												</form:select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.depositto" />:
											</td>
											<td style="font-size: 12px;">
												<form:select path="arDepositTo" id="arDepositTo">
												    <c:if test="${not empty configDto.listOfExistingAccounts}">
                                                        <c:forEach items="${configDto.listOfExistingAccounts}" var="objList1">
                                                            <option value="${objList1.arDepositTo}">${objList1.accountName}</option>
                                                        </c:forEach>
                                                    </c:if>
												</form:select>
											</td>
										</tr>
									</table>
								</div>  
							</div>
							<div id="POPayable">
								<div id="content3" class="tabPage">
								<!-- add here the content of first tab -->
									<table class="table-notifications" width="100%">
										<tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.defaultaccountsetting" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.category" />:
											</td>
											<td style="font-size: 12px;">
												<form:select path="poCategory" id="poCategory">
												    <c:if test="${not empty configDto.listOfExistingCategory}">
                                                        <c:forEach items="${configDto.listOfExistingCategory}" var="objList1">
                                                            <option value="${objList1.poCategory}">${objList1.categoryName}&nbsp;${objList1.categoryNumber}</option>
                                                        </c:forEach>
                                                    </c:if>
												</form:select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.defaultreceivetype"/>:
											</td>
											<td style="font-size: 12px;">
												<form:select path="poReceivedType" id="poReceivedType">
												    <c:if test="${not empty configDto.listOfExistingPaymentGeneralAccount}">
                                                        <c:forEach items="${configDto.listOfExistingPaymentGeneralAccount}" var="objList1">
                                                            <option value="${objList1.poReceivedType}">${objList1.paymentName}</option>
                                                        </c:forEach>
                                                    </c:if>
												</form:select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.depositto" />:
											</td>
											<td style="font-size: 12px;">
												<form:select path="poDepositTo" id="poDepositTo">
												    <c:if test="${not empty configDto.listOfExistingAccounts}">
                                                        <c:forEach items="${configDto.listOfExistingAccounts}" var="objList1">
                                                            <option value="${objList1.poDepositTo}">${objList1.accountName}</option>
                                                        </c:forEach>
                                                    </c:if>
												</form:select>
											</td>
										</tr>
									</table>			
								</div>  
							</div>
							<div id="billing">
								<table class="table-notifications" width="80%">
									<tr>
										<th colspan="2" align="left" style="font-size:12px; padding: 5px;"><spring:message code="BzComposer.configuration.invoiceprefrence" /></th>
									</tr>
									<tr>
										<td style="font-size:12px;">
											<spring:message code="BzComposer.configuration.startbillinginvoicenumber"/>:
										</td>
										<td colspan="2" align="center" style="font-size:12px;">
											<form:input path="startingBillNumber" />
										</td>
									</tr>
									<tr>
										<th colspan="2" align="left" style="font-size:12px; padding: 5px;"><spring:message code="BzComposer.configuration.billingstatementpreference" /></th>
									</tr>
									<tr>
										<td colspan="2" style="font-size:12px;">
											<input type="checkbox" name="showCombinedBilling" id="showCombinedBilling" value="${configDto.showCombinedBilling}" ${configDto.showCombinedBilling=='on'?'checked':''} />
											<spring:message code="BzComposer.configuration.showbillingstatement"/>
										</td>
									</tr>
									<tr>
										<td style="font-size:12px;">
											<spring:message code="BzComposer.configuration.defaultbillingtemplateforprint"/>
										</td>
										<td colspan="2" align="center" style="font-size:12px;">
											<form:select path="showBillingStatStyle" id="showBillingStatStyle">
											    <c:if test="${not empty configDto.listOfExistingBillingType}">
                                                    <c:forEach items="${configDto.listOfExistingBillingType}" var="objList1">
                                                        <option value="${objList1.showBillingStatStyle}">${objList1.billingTypeName}</option>
                                                    </c:forEach>
                                                </c:if>
											</form:select>
										</td>
									</tr>
									<tr>
										<td style="font-size:12px;">
											<%-- <input type="checkbox" id="mailToCustomer" name="mailToCustomer">
											<spring:message code="Bizcomposer.emailToCustomers"/>&nbsp;&nbsp; --%>
											<input type="checkbox" name="mailToCustomer" id="mailToCustomer" value="${configDto.mailToCustomer}" ${configDto.mailToCustomer=='on'?'checked':''} />
											<spring:message code="BzComposer.configuration.emailtocustomers"/>
											&nbsp;&nbsp;
											<%-- <input type="checkbox" id="printBills" name="printBills">
											<spring:message code="Bizcomposer.printBills"/> --%>
											<input type="checkbox" name="printBills" id="printBills" value="${configDto.printBills}" ${configDto.printBills=='on'?'checked':''} />
											<spring:message code="BzComposer.configuration.printbills"/>
										</td>
									</tr>
									<tr>
                                        <th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
                                            <spring:message code="BzComposer.configuration.defaultaccountsetting" /> (<spring:message code="BzComposer.configuration.tab.billingpayable" />)
                                        </th>
                                    </tr>
                                    <tr>
                                        <td style="font-size: 12px;">
                                            <spring:message code="BzComposer.configuration.category" />:
                                        </td>
                                        <td style="font-size: 12px;">
                                            <form:select path="bpCategory" id="bpCategory">
                                                <c:if test="${not empty configDto.listOfExistingCategory}">
                                                    <c:forEach items="${configDto.listOfExistingCategory}" var="objList1">
                                                        <option value="${objList1.bpCategory}">${objList1.categoryName}&nbsp;${objList1.categoryNumber}</option>
                                                    </c:forEach>
                                                </c:if>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="font-size: 12px;">
                                            <spring:message code="BzComposer.configuration.defaultreceivetype"/>:
                                        </td>
                                        <td style="font-size: 12px;">
                                            <form:select path="bpReceivedType" id="bpReceivedType" styleClass="width:50%" >
                                                <c:if test="${not empty configDto.listOfExistingPaymentGeneralAccount}">
                                                    <c:forEach items="${configDto.listOfExistingPaymentGeneralAccount}" var="objList1">
                                                        <option value="${objList1.bpReceivedType}">${objList1.paymentName}</option>
                                                    </c:forEach>
                                                </c:if>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="font-size: 12px;">
                                            <spring:message code="BzComposer.configuration.depositto" />:
                                        </td>
                                        <td style="font-size: 12px;">
                                            <form:select path="bpDepositTo" id="bpDepositTo">
                                                <c:if test="${not empty configDto.listOfExistingAccounts}">
                                                    <c:forEach items="${configDto.listOfExistingAccounts}" var="objList1">
                                                        <option value="${objList1.bpDepositTo}">${objList1.accountName}</option>
                                                    </c:forEach>
                                                </c:if>
                                            </form:select>
                                        </td>
                                    </tr>
								</table>
							</div>
							
							<div id="PaymentType">
								<div id="content6" class="tabPage">
									<table class="table-notifications">
										<tr>
											<th colspan="4" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.creditcardtype" />
											</th>
										</tr>
										<tr>
											<td rowspan="3">
												<table border="2" width="300px;">
													<tr>
														<td align="center" style="font-size: 12px;">
															<b><spring:message code="BzComposer.configuration.creditcardtype" /></b>
														</td>
														<td align="center" style="font-size: 12px;">
															<b><spring:message code="BzComposer.configuration.active" /></b>
														</td>
													</tr>
													<c:if test="${not empty configDto.listOfExistingCreditCard}">
                                                        <c:forEach items="${configDto.listOfExistingCreditCard}" var="objList1" varStatus="loop">
                                                        <tr>
                                                            <td id="${objList1.creditCardTypeId}" onclick="showType(this.val);" align="center" style="font-size: 1em;">
                                                                <label id="cType" name="cType" value="${objList1.creditCardName}">
                                                                    ${objList1.creditCardName}
                                                                </label>
                                                            </td>
                                                            <td align="center"><input type="checkbox" name="isActive" id="isActive${loop.index}" value="true" /></td>
                                                        </tr>
														</c:forEach>
													</c:if>
												</table>
											</td>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.creditcardtype" /> :
											</td>
											<td>
												<input type="text" id="creditCardName" style="font-size: 12px;">
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.active" />
											</td>
											<td>
												<input type="checkbox" id="active" checked>
											</td>
										</tr>
										<tr>
											<td style="font-size: 14px;">
												<button type="button" class="formbutton" id="Save" name="Save" style="width:60px;">
													<spring:message code="BzComposer.global.save"/>
												</button>
												&nbsp;&nbsp;
												<button type="button" class="formbutton" id="Add" name="Add" style="width:80px;">
													<spring:message code="BzComposer.configuration.addnewbtn"/>
												</button>
												&nbsp;&nbsp;
												<button type="button" class="formbutton" id="Delete" name="Delete" style="width:60px;">
													<spring:message code="BzComposer.global.delete"/>
												</button>
											</td>
										</tr>
										<tr>
											<th colspan="4" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.tab.paymenttype" />
											</th>
										</tr>
										<tr>
											<td rowspan="4" style="font-size: 12px;">
												<table border="2" style='height: 100px; width: 300px;'>
													<tr>
														<td align="center">
															<b><spring:message code="BzComposer.configuration.paymenttypename" /></b>
														</td>
														<td align="center">
															<b><spring:message code="BzComposer.configuration.tab.paymenttype" /></b>
														</td>
													</tr>
													<c:if test="${not empty configDto.listOfExistingPaymentType}">
                                                        <c:forEach items="${configDto.listOfExistingPaymentType}" var="objList1">
														<tr>
                                                            <td id="paymentTypeId" value="${objList1.paymentTypeId}" onclick="showType();" align="center" style="font-size: 1em;">
                                                                <label id="pName" name="pName" value="${objList1.paymentTypeId}">
                                                                    ${objList1.paymentName}
                                                                </label>
                                                            </td>
                                                            <td align="center" style="font-size: 12px;">${objList1.paymentType}</td>
														</tr>
														</c:forEach>
													</c:if>
												</table>
											</td>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.tab.paymenttype" /> :
											</td>
											<td style="font-size: 12px;">
												<input type="text" id="paymentType1">
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.type" /> :
											</td>
											<td style="font-size: 12px;">
												<form:select path="listOfExistingCreditCardType">
												    <c:if test="${not empty configDto.listOfExistingCreditCardType}">
                                                        <c:forEach items="${configDto.listOfExistingCreditCardType}" var="objList1">
															<option value="${objList1.creditCardName}">${objList1.creditCardName}</option>
														</c:forEach>
													</c:if>
												</form:select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.accountcategory" /> :
											</td>
											<td style="font-size: 12px;">
												<select>
													<option></option>
													<option>1</option>
													<option>2</option>
												</select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 14px;">
												<button type="button" id="Add" name="Add" class="formbutton" style="width: 60px;" readonly>
												    <spring:message code='BzComposer.global.add'/>
												</button>
												&nbsp;&nbsp;
												<button type="button" id="Update" name="Update" class="formbutton" style="width: 60px;">
												    <spring:message code='BzComposer.global.update'/>
												</button>
												&nbsp;&nbsp; 
												<button type="button" id="Delete" name="Delete" class="formbutton" style="width: 60px;">
												    <spring:message code='BzComposer.global.delete'/>
												</button>
												&nbsp;&nbsp; 
												<button type="button" id="Clear" name="Clear" class="formbutton" style="width: 60px;">
												    <spring:message code='BzComposer.global.clear'/>
												</button>
											</td>
										</tr>
										<tr>
											<td colspan="4" align="center" style="font-size: 12px;">
												<h1>
													<a href="/bzcomposerweb2/Banking?tabid=Banking">
														<spring:message code="BzComposer.configuration.gotobank" />
													</a>
												</h1>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<div id="ReceivedType">
								<div id="content7" class="tabPage">
									<table class="table-notifications">
										<tr>
											<th colspan="4" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.tab.receivedtype" />
											</th>
										</tr>
										<tr>
											<td rowspan="3" style="font-size: 12px;">
												<table border="2" width="300px;">
													<tr>
														<td align="center" style="font-size: 12px;">
															<b><spring:message code="BzComposer.configuration.creditcardtype" /></b>
														</td>
														<td align="center" style="font-size: 12px;">
															<b><spring:message code="BzComposer.configuration.active" /></b>
														</td>
													</tr>
													<c:if test="${not empty configDto.listOfExistingCreditCard}">
                                                        <c:forEach items="${configDto.listOfExistingCreditCard}" var="objList1" varStatus="loop">
														<tr>
                                                            <td id="${objList1.creditCardTypeId}" onclick="showType();" align="center">
                                                                <label id="cType" name="cTyoe" value="${objList1.creditCardTypeId}">
                                                                    ${objList1.creditCardName}
                                                                </label>
                                                            </td>
                                                            <td align="center"><input type="checkbox" name="isActive" id="isActive${loop.index}" value="0"/></td>
														</tr>
														</c:forEach>
													</c:if>
												</table>
											</td>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.creditcardtype" /> :
											</td>
											<td style="font-size: 12px;">
												<input type="text" id="creditCardName">
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.active" /> :
											</td>
											<td style="font-size: 12px;">
												<input type="checkbox" id="active" value="true" />
											</td>
										</tr>
										<tr>
											<td style="font-size: 14px;">
												<button type="button" class="formbutton" id="Save" name="Save" readonly style="width:60px;">
													<spring:message code="BzComposer.global.save"/>
												</button>
												&nbsp;&nbsp;
												<button type="button" class="formbutton" id="Add" name="Add" style="width:80px;">
													<spring:message code="BzComposer.configuration.addnewbtn"/>
												</button>
												&nbsp;&nbsp;
												<button type="button" class="formbutton" id="Delete" name="Delete" style="width:60px;">
													<spring:message code="BzComposer.global.delete"/>
												</button>
											</td>
										</tr>
										<tr>
											<th colspan="4" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.tab.paymenttype" />
											</th>
										</tr>
										<tr>
											<td rowspan="4" style="font-size: 12px;">
												<table border="2" width="300px;">
													<tr>
														<td align="center" style="font-size: 12px;">
															<b><spring:message code="BzComposer.configuration.receivetypename" /></b>
														</td>
														<td align="center" style="font-size: 12px;">
															<b><spring:message code="BzComposer.configuration.tab.receivedtype" /></b>
														</td>
													</tr>
													<c:if test="${not empty configDto.listOfExistingPaymentType}">
                                                        <c:forEach items="${configDto.listOfExistingPaymentType}" var="objList1">
														<tr>
                                                            <td id="paymentTypeId" value="${objList1.paymentTypeId}" onclick="showType();" align="center">
                                                                <label id="pName" name="pName" value="${objList1.paymentTypeId}" style="font-size: 1em;">
                                                                    ${objList1.paymentName}
                                                                </label>
                                                            </td>
                                                            <td align="center">${objList1.paymentType}</td>
														</tr>
														</c:forEach>
													</c:if>
												</table>
											</td>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.paymenttypename" /> :
											</td>
											<td style="font-size: 12px;">
												<input type="text" id="paymentType1">
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.type" /> :
											</td>
											<td style="font-size: 12px;">
												<form:select path="listOfExistingCreditCardType">
												    <c:if test="${not empty configDto.listOfExistingCreditCardType}">
                                                        <c:forEach items="${configDto.listOfExistingCreditCardType}" var="objList1">
															<option value="${objList1.creditCardName}">${objList1.creditCardName}</option>
														</c:forEach>
													</c:if>
												</form:select>
											</td>
											<tr>
												<td style="font-size: 12px;">
													<spring:message code="BzComposer.configuration.accountcategory" /> :
												</td>
												<td style="font-size: 12px;">
													<select>
														<option></option>
														<option>1</option>
														<option>2</option>
													</select>
												</td>
											</tr>
											<tr>
												<td style="font-size: 14px;">
													<button type="button" id="Add" name="Add" class="formbutton" readonly style="width: 60px;">
													    <spring:message code='BzComposer.global.add'/>
													</button>
													&nbsp;&nbsp;
													<button type="button" id="Update" name="Update" class="formbutton" style="width: 60px;">
													    <spring:message code='BzComposer.global.update'/>
													</button>
													&nbsp;&nbsp;
													<button type="button" id="Delete" name="Delete" class="formbutton" style="width: 60px;">
													    <spring:message code='BzComposer.global.delete'/>
													</button>
													&nbsp;&nbsp; 
													<button type="button" id="Clear" name="Clear" class="formbutton" style="width: 60px;">
													    <spring:message code='BzComposer.global.clear'/>
													</button>
												</td>
											</tr>
											<tr>
												<td colspan="4" align="center" style="font-size: 12px;">
													<h1>
														<a href="/bzcomposerweb2/Banking?tabid=Banking">
															<spring:message code="BzComposer.configuration.gotobank" />
														</a>
													</h1>
												</td>
											</tr>
										</table>
								</div>
							</div>
						</div>	
					</div>
					<!-- Account&Payment Ends -->
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
			<%-- <form:button path="Submit" onclick="SaveValues()" style="font-size:1em;">Save</form:button>
			<form:cancel path="Cancel" onclick="RevokeValues()" style="font-size:1em;">Cancel</form:cancel> --%>
			<input type="Submit" name="Submit" onclick="SaveValues()" style="font-size:1em;" value="<spring:message code='BzComposer.global.save'/>"/>
		<input type="reset" name="Cancel" onclick="RevokeValues()" style="font-size:1em;" value="<spring:message code='BzComposer.global.cancel'/>"/>
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
/* function SaveValues(){
	if(confirm('<spring:message code="BizComposer.Configuration.SaveConfirm"/>')){
		document.configurationForm.annualInterestRate.value = wxToFixed(document.configurationForm.annualInterestRate.value,2);
		document.configurationForm.minCharge.value = wxToFixed(document.configurationForm.minCharge.value,2);
		
		document.configurationForm.startInvoiceNo.value = parseInt(document.configurationForm.startInvoiceNo.value);	
		document.configurationForm.startPONum.value = parseInt(document.configurationForm.startPONum.value);
		document.configurationForm.startRINum.value = parseInt(document.configurationForm.startRINum.value);
		document.configurationForm.timeSheet.value = parseInt(document.configurationForm.timeSheet.value);
		
		document.configurationForm.invoiceMemoDays.value = parseInt(document.configurationForm.invoiceMemoDays.value);
		document.configurationForm.overdueInvoiceDays.value = parseInt(document.configurationForm.overdueInvoiceDays.value);
		document.configurationForm.inventoryOrderDays.value = parseInt(document.configurationForm.inventoryOrderDays.value);
		document.configurationForm.billsToPayDays.value = parseInt(document.configurationForm.billsToPayDays.value);
		document.configurationForm.gracePeriod.value = parseInt(document.configurationForm.gracePeriod.value);
		
		performance_value = document.configurationForm.userDefinePerform.value;
		if(document.configurationForm.timeSheet.value <2){
			document.configurationForm.timeSheet.value = 2;
		}
		if(performance_value == "" || parseInt(performance_value) <= 10000 || (!IsNumeric(performance_value))){
			document.configurationForm.userDefinePerform.value = 10001;
			
		}
		document.getElementById('tid').value="SaveConfiguration";
		document.forms[0].action = "Configuration";
		document.forms[0].submit();
	}
}

function RevokeValues(){
	document.getElementById('tid').value="config";
	document.forms[0].action = "Configuration";
	document.forms[0].submit();
} */

function SaveValues()
{
	//if(confirm('<spring:message code="BzComposer.configuration.saveconfirm"/>'))
	//{
		/* debugger
		//General Account Setting panel options
		document.configurationForm.defaultPaymentMethodId.value = document.configurationForm.defaultPaymentMethodId.value;
		document.configurationForm.defaultCategoryId.value = document.configurationForm.defaultCategoryId.value;
		debugger
		
		document.configurationForm.scheduleDays.value = document.configurationForm.scheduleDays.value;
		
		if($("#askWhatToDo").prop("checked",true))
		{
			//alert("askWhatToDo is checked");
			document.configurationForm.reimbursementSettings.value = 2;
		}
		else if($("#dontAddAny").prop("checked",true))
		{
			//alert("dontNeedAny is checked");
			document.configurationForm.reimbursementSettings.value = 1;
		}
		else
		{
			//alert("timeForPrompt is checked");
			document.configurationForm.reimbursementSettings.value = 0;
		} 
		
		document.configurationForm.reimbursementSettings.value = document.configurationForm.reimbursementSettings.value;
		
		debugger
		
		document.configurationForm.annualInterestRate.value = document.configurationForm.annualInterestRate.value;
		document.configurationForm.minCharge.value = document.configurationForm.minCharge.value;
		document.configurationForm.gracePeriod.value = document.configurationForm.gracePeriod.value; 
		document.configurationForm.assessFinanceCharge.value = $("#assessFinanceCharge").val();
		
		debugger
		
		document.configurationForm.startMonth.value = document.configurationForm.startMonth.value;
		document.configurationForm.endMonth.value = document.configurationForm.endMonth.value;
		
		debugger
		//Account Receivable panel options 
		document.configurationForm.arCategory.value = document.configurationForm.arCategory.value;
		document.configurationForm.arReceivedType.value = document.configurationForm.arReceivedType.value;
		document.configurationForm.arDepositTo.value = document.configurationForm.arDepositTo.value;
		
		debugger 
		
		//Po Payable panel options
		document.configurationForm.poCategory.value = document.configurationForm.poCategory.value;
		document.configurationForm.poReceivedType.value = document.configurationForm.poReceivedType.value;
		document.configurationForm.poDepositTo.value = document.configurationForm.poDepositTo.value;
		
		debugger
		
		//Billing Payable panel options
		document.configurationForm.bpCategory.value = document.configurationForm.bpCategory.value;
		document.configurationForm.bpReceivedType.value = document.configurationForm.bpReceivedType.value;
		document.configurationForm.bpDepositTo.value = document.configurationForm.bpDepositTo.value;
		
		debugger
		
		document.getElementById('tabid').value="SaveConfigurationAccountPayment";
		document.forms[0].action = "Configuration";
		document.forms[0].submit(); */
		
		debugger;
		event.preventDefault();
		$("#showsaverecorddialog").dialog({
		    	resizable: false,
		        height: 200,
		        width: 500,
		        modal: true,
		        buttons: {
		        	"<spring:message code='BzComposer.global.ok'/>": function () {
		            	debugger;
		            	//General Account Setting panel options
		        		document.configurationForm.defaultPaymentMethodId.value = document.configurationForm.defaultPaymentMethodId.value;
		        		document.configurationForm.defaultCategoryId.value = document.configurationForm.defaultCategoryId.value;
		        		document.configurationForm.scheduleDays.value = document.configurationForm.scheduleDays.value;

		        		if($("#askWhatToDo").prop("checked",true)){
		        			document.configurationForm.reimbursementSettings.value = 2;
		        		}
		        		else if($("#dontAddAny").prop("checked",true)){
		        			document.configurationForm.reimbursementSettings.value = 1;
		        		}
		        		else{
		        			document.configurationForm.reimbursementSettings.value = 0;
		        		} 
		        		
		        		document.configurationForm.reimbursementSettings.value = document.configurationForm.reimbursementSettings.value;
		        		document.configurationForm.annualInterestRate.value = document.configurationForm.annualInterestRate.value;
		        		document.configurationForm.minCharge.value = document.configurationForm.minCharge.value;
		        		document.configurationForm.gracePeriod.value = document.configurationForm.gracePeriod.value; 
		        		document.configurationForm.assessFinanceCharge.value = $("#assessFinanceCharge").val();
		        		
		        		document.configurationForm.startMonth.value = document.configurationForm.startMonth.value;
		        		document.configurationForm.endMonth.value = document.configurationForm.endMonth.value;
		        		
		        		//Account Receivable panel options 
		        		document.configurationForm.arCategory.value = document.configurationForm.arCategory.value;
		        		document.configurationForm.arReceivedType.value = document.configurationForm.arReceivedType.value;
		        		document.configurationForm.arDepositTo.value = document.configurationForm.arDepositTo.value;
		        		
		        		//Po Payable panel options
		        		document.configurationForm.poCategory.value = document.configurationForm.poCategory.value;
		        		document.configurationForm.poReceivedType.value = document.configurationForm.poReceivedType.value;
		        		document.configurationForm.poDepositTo.value = document.configurationForm.poDepositTo.value;
		        		
		        		//Billing Payable panel options
		        		document.configurationForm.bpCategory.value = document.configurationForm.bpCategory.value;
		        		document.configurationForm.bpReceivedType.value = document.configurationForm.bpReceivedType.value;
		        		document.configurationForm.bpDepositTo.value = document.configurationForm.bpDepositTo.value;
		        		
		        		//Billing options
		        		var selectedBillingType = $.trim($("#selectedBillingTypeId option:selected").text());
	        			//var printBill = $("#printBills").val();
	        			//var mailCustomer = $("#mailToCustomer").val();
	        			//var combinedBilling = $("#showCombinedBilling").val();
	            	
	            		document.configurationForm.startingBillNumber.value = document.configurationForm.startingBillNumber.value;
	        			document.configurationForm.showCombinedBilling.value = document.configurationForm.showCombinedBilling.value;
	        			document.configurationForm.showBillingStatStyle.value =  document.configurationForm.showBillingStatStyle.value;
	        		
	        			document.configurationForm.mailToCustomer.value = document.configurationForm.mailToCustomer.value;
	        			document.configurationForm.printBills.value = document.configurationForm.printBills.value;
	        		
	        			//document.getElementById('printBillsValue').value= printBill;
	        			//document.getElementById("mailToCust").value = mailCustomer;
	        			//document.getElementById("showCmbBilling").value = combinedBilling;
		        		document.getElementById('tabid').value="SaveConfigurationAccountPayment";

		        		document.forms['accountPaymentfrm'].action = "Configuration?tabid=SaveConfigurationAccountPayment";
		        		document.forms['accountPaymentfrm'].submit();
						$('form').submit();
		            },
		            "<spring:message code='BzComposer.global.cancel'/>": function () {
		                $(this).dialog("close");
		                return false;
		            }
		        }
		    });
		    return false;
	//}
}
</script>
</html>
<!-- Dialog box used in this page -->
<div id="showsaverecorddialog" style="display:none;">
	<p><spring:message code="BzComposer.configuration.saveconfirm"/></p>
</div>