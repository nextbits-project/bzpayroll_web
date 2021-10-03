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
<title><spring:message code="BzComposer.customerinvoicetitle" /></title>
<link href="${pageContext.request.contextPath}/tableStyle/tab/jquery-ui-tab.css" rel="stylesheet" media="screen" />
<script src="${pageContext.request.contextPath}/tableStyle/tab/jquery-ui.js"></script>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"> -->
<%-- <script src="${pageContext.request.contextPath}/dist/js/jquery.min.js"></script>  --%>
<%-- <script src="${pageContext.request.contextPath}/dist/js/bootstrap.min.js"></script> --%>
</head>
<head>
<jsp:include page="customerInvoiceFunctionPage.jsp"></jsp:include>
<%-- <%@include file ="customerInvoiceFunctionPage.jsp" %> --%>

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
$(document).ready(function()
{
    $("#refundReasonSel option").each(function() {
        if ($(this).val() == 1)
        {
            $(this).css("color", 'Blue');
        }
        else
        {
            //alert('normal Reason');
        }
    });

    var groupId = '<%= request.getAttribute("groupId")%>';
    var countryId = '<%= request.getAttribute("countryId")%>';
    var stateId = '<%= request.getAttribute("stateId")%>';
    var shippingFeeMethodId = '<%= request.getAttribute("shippingMethodId")%>';
    var termId = '<%= request.getAttribute("termId")%>';
    var salesRepId = '<%= request.getAttribute("salesRepId")%>';
    var payMethodId = '<%= request.getAttribute("payMethodId")%>';

    var InvoiceFootnoteID =  '<%= request.getAttribute("InvoiceFootnoteID")%>';
    debugger;
    var selectedInvoiceStyleId =<%= request.getAttribute("selectedInvoiceStyleId")%>;
    $('select[id="selectedInvoiceStyleId"]').find('option[value="'+selectedInvoiceStyleId+'"]').attr("selected",true);
    var DefaultPaymentMethodId = '<%= request.getAttribute("DefaultPaymentMethodId")%>';
    //Added on 01-05-2020
    debugger
    var sortId = '<%= request.getAttribute("sortById")%>';
    debugger
    $('select[id="sortBy"]').find('option[value="'+sortId+'"]').attr("selected",true);
    debugger
    //$("#description").val("");

    var packingSlipStyleId = '<%= request.getAttribute("packingSlipStyleId")%>';

    var isChecked = '<%= request.getAttribute("isRefundAllowed")%>';
    /*alert("RefundAllowed Checkecbox is"+isChecked);*/
    if(isChecked == "on")
    {

        //alert("isRefundAllowed checked");
        $("#isRefundAllowed").attr('checked', true);

        $("#refundReason").prop('readonly', false);
        $("#refundReasonSel").prop('readonly',false);
        $("#addRefundReason").prop('disabled',false);
        $("#updateRefundReason").prop('disabled',false);
        $("#deleteRefundReason").prop('disabled',false);
        $("#defaultReason").prop('disabled',false);
    }
    else if(isChecked == "off")
    {
        //alert("isRefundAllowed unchecked");
        $("#refundReason").prop('readonly', true);
        $("#refundReasonSel").prop('readonly',true);
        $("#addRefundReason").prop('disabled',true);
        $("#updateRefundReason").prop('disabled',true);
        $("#deleteRefundReason").prop('disabled',true);
        $("#defaultReason").prop('disabled',true);
    }
    else
    {
        $("#refundReason").prop('readonly', isChecked);
        $("#refundReasonSel").prop('readonly',isChecked);
        $("#addRefundReason").prop('disabled',isChecked);
        $("#updateRefundReason").prop('disabled',isChecked);
        $("#deleteRefundReason").prop('disabled',isChecked);
        $("#defaultReason").prop('disabled',isChecked);
    }

    if(countryId==2)
    {
        $('#customerState').prop('disabled', false);
    }
    else
    {
        $('#customerState').prop('disabled', true);
    }
    $('select[id="customerGroup"]').find('option[value="'+groupId+'"]').attr("selected",true);
    $('select[id="customerCountry"]').find('option[value="'+countryId+'"]').attr("selected",true);
    $('select[id="customerState"]').find('option[value="'+stateId+'"]').attr("selected",true);

    $('select[id="customerShippingId"]').find('option[value="'+shippingFeeMethodId+'"]').attr("selected",true);
    $('select[id="selectedTermId"]').find('option[value="'+termId+'"]').attr("selected",true);
    $('select[id="customerSalesRep"]').find('option[value="'+salesRepId+'"]').attr("selected",true);
    $('select[id="selectedPaymentId"]').find('option[value="'+payMethodId+'"]').attr("selected",true);

    $('select[id="packingSlipTemplateId"]').find('option[value="'+packingSlipStyleId+'"]').attr("selected",true);

    $('select[id="selectedMessageId"]').find('option[value="'+InvoiceFootnoteID+'"]').attr("selected",true);
    $('select[id="selectedBankAccountId"]').find('option[value="'+DefaultPaymentMethodId+'"]').attr("selected",true);

    $("#isDefaultCreditTerm").change(function()
    {
        var isChecked = '<%= request.getAttribute("isDefault")%>';
        //var isChecked = "on";
        if($(this).prop("checked") == true){
            //alert("CustomerTaxale is checked.");
            $("#isDefaultCreditTerm").attr('checked', true);

            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            //alert("CustomerTaxable is unchecked.");
            $("#isDefaultCreditTerm").attr('checked', false);

             isChecked = "off";
        }
        else
        {
            //alert("CustomerTaxable is checked.");
            $("#isDefaultCreditTerm").attr('checked', isChecked);


        }
        document.configurationForm.isDefaultCreditTerm.value = isChecked;
        $("#isDefaultCreditTerm").val(isChecked);
    });

    $("#custTaxable").change(function()
    {
        var isChecked = '<%= request.getAttribute("custTaxableStatus")%>';
        if($(this).prop("checked") == true){
            //alert("CustomerTaxale is checked.");
            $("#custTaxable").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            //alert("CustomerTaxable is unchecked.");
            $("#custTaxable").attr('checked', false);
             isChecked = "off";
        }
        else
        {
            //alert("CustomerTaxable is checked.");
            $("#custTaxable").attr('checked', isChecked);
            document.configurationForm.custTaxable.value = isChecked;
        }
        $("#custTaxable").val(isChecked);
    });

    $("#addressSettings").change(function()
    {
        var isChecked = '<%= request.getAttribute("addressStatus")%>';
        if($(this).prop("checked") == true)
        {
            $("#addressSettings").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false)
        {
            $("#addressSettings").attr('checked', false);
            isChecked = "off";
        }
        else
        {
            alert("CustomerTaxable is checked.");
            $("#custTaxable").attr('checked', isChecked);
            document.configurationForm.addressSettings.value = isChecked;
        }
        $("#addressSettings").val(isChecked);
    });
    $("#isSalesOrder").change(function()
    {
        var isChecked = '<%= request.getAttribute("salesOrderStatus")%>';
        if($(this).prop("checked") == true){
            $("#isSalesOrder").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            $("#isSalesOrder").attr('checked', false);
            isChecked = "off";
        }
        else
        {
            $("#isSalesOrder").attr('checked', isChecked);
            document.configurationForm.isSalesOrder.value = isChecked;
        }
        $("#isSalesOrder").val(isChecked);
    });


    /*for Sales&Invocie panel*/
    $("#saleShowCountry").change(function()
    {
        var isChecked = '<%=request.getAttribute("showCountry")%>';
        if($(this).prop("checked") == true){
            $("#saleShowCountry").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            $("#saleShowCountry").attr('checked', false);
            isChecked = "off";
        }
        else
        {
            $("#isSalesOrder").attr('checked', isChecked);
            document.configurationForm.saleShowCountry.value = isChecked;
        }
        $("#saleShowCountry").val(isChecked);
    });

    $("#ratePriceChangable").change(function()
    {
        var isChecked = '<%=request.getAttribute("ratePrice")%>';
        if($(this).prop("checked") == true){
            $("#ratePriceChangable").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            $("#ratePriceChangable").attr('checked', false);
             isChecked = "off";
        }
        else
        {
            $("#ratePriceChangable").attr('checked', isChecked);
            document.configurationForm.ratePriceChangable.value = isChecked;
        }
        $("#ratePriceChangable").val(isChecked);
    });

    $("#saleShowTelephone").change(function()
    {
        var isChecked = '<%= request.getAttribute("salesShowTelephone")%>';
        if($(this).prop("checked") == true){
            $("#saleShowTelephone").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            $("#saleShowTelephone").attr('checked', false);
            isChecked = "off";
        }
        else
        {
            $("#saleShowTelephone").attr('checked', isChecked);
            document.configurationForm.saleShowTelephone.value = isChecked;
        }
        $("#saleShowTelephone").val(isChecked);
    });

    $("#isSalePrefix").change(function()
    {
        var isChecked = '<%= request.getAttribute("isSalePrefix")%>';
        if($(this).prop("checked") == true){
            $("#isSalePrefix").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            $("#isSalePrefix").attr('checked', false);
            isChecked = "off";
        }
        else
        {
            $("#isSalePrefix").attr('checked', isChecked);
            document.configurationForm.isSalePrefix.value = isChecked;
        }
        $("#isSalePrefix").val(isChecked);
    });

    $("#extraChargeApplicable").change(function()
    {
        var isChecked = '<%= request.getAttribute("extraCharge")%>';
        if($(this).prop("checked") == true){
            $("#extraChargeApplicable").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            $("#extraChargeApplicable").attr('checked', false);
             isChecked = "off";
        }
        else
        {
            $("#extraChargeApplicable").attr('checked', isChecked);
            document.configurationForm.extraChargeApplicable.value = isChecked;
        }
        $("#extraChargeApplicable").val(isChecked);
    });

    $("#recurringServiceBill").change(function()
    {
        var isChecked = "on";
        if($(this).prop("checked") == true){
            $("#recurringServiceBill").attr('checked', true);
            isChecked = "on";
        }
        else if($(this).prop("checked") == false){
            $("#recurringServiceBill").attr('checked', false);
             isChecked = "off";
        }
        else
        {
            $("#recurringServiceBill").attr('checked', isChecked);
            document.configurationForm.recurringServiceBill.value = isChecked;
        }
        $("#recurringServiceBill").val(isChecked);
    });

    $("#invoiceLocation").change(function(){
        var filePath = $("#invoiceLocation").val();
        //alert("Selected File:"+filePath);
        $("invoiceLocation").val(filePath);
        //document.configurationForm.invoiceLocation.value = filePath;
        //debugger
    });

    $("#saveImage").change(function(){
        var filePath = $("#saveImage").val();
        //alert("Selected File:"+filePath);
        $("saveImage").val(filePath);
        //document.configurationForm.invoiceLocation.value = filePath;
        //debugger
    });

    /* $("#form").submit(function(){
        debugger
        var form = $('form')[0]; // You need to use standard javascript object here
        debugger
        var formData = new FormData(form);
        formData.append('image', $('input[type=file]')[0].files[0]);
        $.ajax({
            url: 'Configuration?tabid=SaveCustomerInvocieSettings',
            data: formData,
            type: 'POST',
            contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
            processData: false, // NEEDED, DON'T OMIT THIS
            // ... Other options like success and etc
        });
    }); */
    <%-- Commented on 05-05-2019 due to this error:
    The code of method _jspService(HttpServletRequest, HttpServletResponse) is exceeding the 65535 bytes limit --%>

     $("#isRefundAllowed").change(function ()
    {

        <%-- var isChecked = '<%= request.getAttribute("isRefundAllowed")%>'; --%>
        var checked = "on";
        if($(this).prop("checked") == true)
        {
            //alert("isRefundAllowed checked");
            $("#isRefundAllowed").attr('checked', true);

            $("#refundReason").prop('readonly', false);
            $("#refundReasonSel").prop('readonly',false);
            $("#addRefundReason").prop('disabled',false);
            $("#updateRefundReason").prop('disabled',false);
            $("#deleteRefundReason").prop('disabled',false);
            $("#defaultReason").prop('disabled',false);

            document.configurationForm.isRefundAllowed.value = "on";
        }
        else if($(this).prop("checked") == false)
        {
            //alert("isRefundAllowed unchecked");
            $("#refundReason").prop('readonly', true);
            $("#refundReasonSel").prop('readonly',true);
            $("#addRefundReason").prop('disabled',true);
            $("#updateRefundReason").prop('disabled',true);
            $("#deleteRefundReason").prop('disabled',true);
            $("#defaultReason").prop('disabled',true);

            document.configurationForm.isRefundAllowed.value = "off";
        }
      });
    /* $("#form").submit(function( event )
    {
        var text = $('#description').val();
        var setup = $("#setupID option:selected").val();
        if(text == "" || text == " ")
        {
            alert("Necessary data is empty");
        }
        else
        {
            if(setup == "Location")
            {
                debugger
                alert(text);
                $.ajax({
                    type: "POST",
                    url:"Configuration?tabid=addDescription&Description="+text,
                    data:  { location : text }
                    }).done(function(data){
                    debugger
                    //$("#state").html(data);
                    $(document).find('div#locationDiv table').replaceWith($(data).find('div#locationDiv').html());
                    //$('select[id="phonecode"]').find('option[id="'+selectedCountry+'"]').attr("selected",true);
                });

                /* document.getElementById('Description').value = text;
                document.getElementById('tabid').value="addDescription";
                document.forms[0].action = "Configuration";
                document.forms[0].submit();
                debugger */
            /*}
        }
    }); */

    /* Added on 04-05-2020 */
    var accountID = '<%= request.getAttribute("accountId")%>';
    /* $('select[id="selectedBankAccountId"]').find('option[value="'+accountID+'"]').attr("selected",true); */
    $("#reason").val("");
    $("#parentReasonId option").prop("selected",false);
    $("#availableReasons option").prop("selected",false);

});

</script>
</head>
<!-- <body onload="init1();"> -->
<body onload="init();">
<!-- begin shared/header -->
<%-- <form:form action="Configuration?" enctype="multipart/form-data" method="post" id="form"> --%>
<form:form name="configurationForm" action="Configuration" method="post" id="form" modelAttribute="configDto">
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
		<div>
			<c:if test="${not empty Labels}">
                <input type="hidden" name="lsize" id="lblsize" value='${Labels.size()}' />
                <c:forEach items="${Labels}" var="lbl" varStatus="loop">
                    <input type="hidden" id='${loop.index}lid' name='${loop.index}lidname' value='${lbl.value}' />
                    <input type="hidden" id='${loop.index}lname' name='${loop.index}lnm' value='${lbl.label}' />
                </c:forEach>
			</c:if>
		</div>
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
						<div id="customerInvoice"  style="display:none;padding: 0; position: relative; left: 0;" >
							<div id="tabs" style="height:auto;">
  								<ul>
    								<li style="font-size:12px;"><a href="#Customer"><spring:message code="BzComposer.configuration.tab.customer" /></a></li>
    								<li style="font-size:12px;"><a href="#estimation"><spring:message code="BzComposer.sales.Estimation"/></a></li>
    								<li style="font-size:12px;"><a href="#Sales&Invoice"><spring:message code="BzComposer.configuration.tab.salesinvoice" /></a></li>
    								<li style="font-size:12px;"><a href="#SalesOption"><spring:message code="BzComposer.configuration.tab.salesoption" /></a></li>
    								<li style="font-size:12px;"><a href="#RefundSettings"><spring:message code="BzComposer.configuration.tab.refundsetting" /></a></li>
    								<li style="font-size:12px;"><a href="#Customer&Job"><spring:message code="BzComposer.configuration.tab.customerjob" /></a></li>
    								<li style="font-size:12px;"><a href="#Rma"><spring:message code="BzComposer.RMA"/></a></li>
  								</ul>
  								<div id="Customer">
									<div id="content1" class="tabPage">
   						 				<table class="table-notifications" width="100%">
											<tr>
												<th colspan="5" align="left" style="font-size:12px; padding: 5px;">
													<spring:message code="BzComposer.configuration.tab.customer" />
												</th>
											</tr>
											<tr>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.customerlistsortby" />:
												</td>
												<td style="font-size:12px;">
													<form:select path="sortBy" id="sortBy">
														<form:option value="0"><spring:message code="BzComposer.configuration.companyname"/></form:option>
														<form:option value="1"><spring:message code="BzComposer.global.lastname"/></form:option>
														<%-- <form:option value="0"><spring:message code="BzComposer.configuration.select"/></form:option> --%>
													</form:select>
												</td>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.customerGroup"/>:
												</td>
												<td style="font-size:12px;">
													<form:select path="customerGroup" id="customerGroup">
														<c:if test="${not empty configDto.listOfExistingCustomerGroup}">
															<c:forEach items="${configDto.listOfExistingCustomerGroup}" var="objList1">
																<option value="${objList1.customerGroup}">${objList1.groupName}</option>
															</c:forEach>
														</c:if>
													</form:select>
												</td>
												<td style="font-size:12px;">
													<%-- <form:file path="saveImage" id="saveImage" accept="/image/*"></form:file> --%>
												</td>
											</tr>
											<tr>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.customerCountry"/>
												</td>
												<td id="country" style="font-size:12px;">
													<form:select path="custDefaultCountryID" id="customerCountry" onchange="disable()">
														<c:if test="${not empty configDto.listOfExistingCountry}">
															<c:forEach items="${configDto.listOfExistingCountry}" var="objList1">
																<option value="${objList1.countryId}">${objList1.countryName}</option>
															</c:forEach>
														</c:if>
													</form:select>
												</td>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.customerState"/>:
												</td>
												<td id="state" style="font-size:12px;">
													<form:select path="selectedStateId" id="customerState">
													    <c:if test="${not empty configDto.listOfExistingState}">
                                                            <c:forEach items="${configDto.listOfExistingState}" var="objList1">
                                                                <option value="${objList1.selectedStateId}">${objList1.stateName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
											</tr>
											<tr>
												<td colspan="2" style="font-size:12px;">
													<%-- <form:checkbox path="taxable"> --%>
													<input type="checkbox" name="custTaxable" id="custTaxable" value="${configDto.custTaxable}" ${configDto.custTaxable=='on'?'checked':''} />
													<spring:message code="BzComposer.configuration.allCustomerAreTaxable"/>
												</td>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.province"/>:
												</td>
												<td style="font-size:12px;">
													<form:input path="customerProvince" />
												</td>
											</tr>
											<tr>
												<th colspan="5" align="left" style="font-size:12px; padding: 5px;">
													<spring:message code="BzComposer.configuration.customerprefrence"/>
												</th>
											</tr>
											<tr>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.shipping"/> :
												</td>
												<td style="font-size:12px;">
													<form:select path="customerShippingId" id="customerShippingId">
														<!-- <option value="0"></option> -->
														<c:if test="${not empty configDto.listOfExistingShipping}">
                                                            <c:forEach items="${configDto.listOfExistingShipping}" var="objList1">
                                                                <option value="${objList1.selectedShippingId}">${objList1.shippingName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.term"/> :
												</td>
												<td style="font-size:12px;">
									  				<form:select path="selectedTermId" id="selectedTermId">
														<!-- <option value="0"></option>	 -->
														<c:if test="${not empty configDto.listOfExistingTerm}">
                                                            <c:forEach items="${configDto.listOfExistingTerm}" var="objList1">
                                                                <option value="${objList1.selectedTermId}">${objList1.termName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
											</tr>
											<tr>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.rep"/> :
												</td>
												<td style="font-size:12px;">
													<form:select path="selectedSalesRepId" id="selectedSalesRepId">
														<!-- <option value="0"></option> -->
														<c:if test="${not empty configDto.listOfExistingSalesRep}">
                                                            <c:forEach items="${configDto.listOfExistingSalesRep}" var="objList1">
                                                                <option value="${objList1.selectedSalesRepId}">${objList1.salesRepName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.paymethod"/>:
												</td>
												<td style="font-size:12px;">
													<form:select path="selectedPaymentId" id="selectedPaymentId">
													    <c:if test="${not empty configDto.listOfExistingPayment}">
                                                            <c:forEach items="${configDto.listOfExistingPayment}" var="objList1">
                                                                <option value="${objList1.paymentId}">${objList1.paymentName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
											</tr>
											<tr>
												<th colspan="5" align="left" style="font-size:12px; padding: 5px;">
													<spring:message code="BzComposer.configuration.addresssettings"/>
												</th>
											</tr>
											<tr>
												<td colspan="5" style="font-size:12px;">
													<input type="checkbox" id="addressSettings" name="addressSettings" value="${configDto.addressSettings}" ${configDto.addressSettings=='on'?'checked':''} />
													<label><spring:message code="BzComposer.configuration.addresssettingstext"/></label>
												</td>
											</tr>
											<!--<tr>
												<th colspan="5" align="left" style="font-size:12px; padding: 5px;">
													<spring:message code="BzComposer.configuration.salesorderpreference"/>
												</th>
											</tr>
											<tr>
												<td colspan="5" style="font-size:12px;">
													<form:checkbox path="isSalesOrder" id="isSalesOrder" value="${configDto.isSalesOrder}" />
													<spring:message code="BzComposer.configuration.usesalesorder"/>
												</td>
											</tr>-->
										</table>
 									</div>
								</div>

								<!-- estimation Starts -->
								<div id="estimation">
									<table class="table-notifications">
										<tr>
											<th align="left" colspan="2" style="font-size:12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.estimationpreference"/>
											</th>
										</tr>
										<tr>
											<td style="font-size:12px;">
												<spring:message code="BzComposer.configuration.startingestimationnumber"/>:
											</td>
											<td style="font-size:12px;">
												<form:input path="startingEstimationNumber" />
											</td>
										</tr>
									</table>
								</div>
								<!-- estimation Ends -->
								<div id="Sales&Invoice">
									<div id="content1" class="tabPage">
   										<table class="table-notifications" width="100%">
											<tr>
												<th colspan="4" align="left" style="font-size:12px; padding: 5px;">
													<spring:message code="BzComposer.configuration.invoicesalespreference" />
												</th>
											</tr>
											<tr>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.startinvoicenumber"/> :
												</td>
												<td style="font-size:12px;">
													<form:input path="startInvoiceNo" readonly="true" id="startInvoiceNo" value="1000" />
												</td>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.defaultpackingslipstyle"/> :
												</td>
												<td style="font-size:12px;">
													<form:select path="packingSlipTemplateId" id="packingSlipTemplateId">
													    <c:if test="${not empty configDto.listOfExistingPackingSlipTemplate}">
                                                            <c:forEach items="${configDto.listOfExistingPackingSlipTemplate}" var="objList1">
                                                                <option value="${objList1.packingSlipTemplateId}">${objList1.packingSlipTemplateName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
											</tr>
											<tr>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.invoicestyle"/> :
												</td>
												<td style="font-size:12px;">
													<form:select path="selectedInvoiceStyleId" id="selectedInvoiceStyleId">
													    <c:if test="${not empty configDto.listOfExistingInvoiceStyle}">
                                                            <c:forEach items="${configDto.listOfExistingInvoiceStyle}" var="objList1">
                                                                <option value="${objList1.selectedInvoiceStyleId}">${objList1.invoiceStyle}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
												<!--<td style="font-size:12px;">
													<spring:message code="Bizcomposer.PoNumPrefix"/>
												</td>-->
												<td style="font-size:12px;">
													<form:hidden path="poNumPrefix" id="poNumPrefix" />
												</td>
											</tr>
											<tr>
												<td style="font-size:12px;">
													<spring:message code="BzComposer.configuration.defaultfootnote"/> :
												</td>
												<td style="font-size:12px;">
													<form:select path="selectedMessageId" id="selectedMessageId">
													    <c:if test="${not empty configDto.messages}">
                                                            <c:forEach items="${configDto.messages}" var="objList1">
                                                                <option value="${objList1.selectedMessageId}">${objList1.messageName}</option>
                                                            </c:forEach>
                                                        </c:if>
													</form:select>
												</td>
											</tr>
											<tr>
											<td style="font-size:12px;">
												<input type="checkbox" name="saleShowCountry" id="saleShowCountry" value="${configDto.saleShowCountry}" ${configDto.saleShowCountry=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.showcountrynameoninvoice"/></label>
											</td>
											<td style="font-size:12px;">
												<input type="checkbox" name="ratePriceChangable" id="ratePriceChangable" value="${configDto.ratePriceChangable}" ${configDto.ratePriceChangable=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.ratepricechangable"/></label>
											</td>
										</tr>
										<tr>
											<td style="font-size:12px;">
												<input type="checkbox" name="saleShowTelephone" id="saleShowTelephone" value="${configDto.saleShowTelephone}" ${configDto.saleShowTelephone=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.showtelephonefaxoninvoice"/></label>
											</td>
											<td style="font-size:12px;">
												<input type="checkbox" name="isSalePrefix" id="isSalePrefix" value="${configDto.isSalePrefix}" ${configDto.isSalePrefix=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.useprefix"/></label>
											</td>
										</tr>
										<tr>
											<td style="font-size:12px;">
												<spring:message code="BzComposer.configuration.taxcode"/> :
											</td>
											<td style="font-size:12px;">
												<form:input path="salesTaxCode" id="salesTaxCode" />
											</td>
										</tr>
										<tr>
											<td style="font-size:12px;">
												<spring:message code="BzComposer.configuration.taxrate"/>
											</td>
											<td style="font-size:12px;">
												<form:input path="saleTaxRate" id="saleTaxRate" />
											</td>
										</tr>
							<tr>
								<td style="font-size:12px;">
									<spring:message code="BzComposer.configuration.howoftenyoupaysalestax"/> :
								</td>
								<td style="font-size:12px;">
									<form:radiobutton path="howOftenSalestax" value="1" id="monthlySalesTax" />
									<label><spring:message code="BzComposer.configuration.monthly"/></label>
									&nbsp;
									<form:radiobutton path="howOftenSalestax" value="2" id="quartely" />
									<label><spring:message code="BzComposer.configuration.quarterly"/></label>
									&nbsp;
									<form:radiobutton path="howOftenSalestax" value="3" id="annually" />
									<label><spring:message code="BzComposer.configuration.annually"/></label>
								</td>
							</tr>
							<tr>
								<th colspan="4" align="left" style="font-size:12px; padding: 5px;"><spring:message code="BzComposer.configuration.dropshippingcharge" /></th>
							</tr>
							<tr>
								<td style="font-size:12px;">
									<spring:message code="BzComposer.configuration.dropshippingcharge"/> ($) :
								</td>
								<td style="font-size:12px;">
									<form:input path="dropShipCharge" id="dropShipCharge" />
								</td>
							</tr>
							<tr>
								<td style="font-size:12px;">
									<form:radiobutton path="isShowDropShipItems" value="0" />
									<label><spring:message code="BzComposer.configuration.showallitems"/></label>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="left" style="font-size:12px;">
									<form:radiobutton path="isShowDropShipItems" value="1" />
									<label><spring:message code="BzComposer.configuration.showdropshipitemsonly"/></label>
								</td>
							</tr>
							<tr>
								<th colspan="4" align="left" style="font-size:12px; padding: 5px;"><spring:message code="BzComposer.configuration.extrachargeforinvoice" /></th>
							</tr>
							<tr>
								<td style="font-size:12px;">
									<input type="checkbox" name="extraChargeApplicable" value="${configDto.extraChargeApplicable}" ${configDto.extraChargeApplicable=='on'?'checked':''} />
									<label><spring:message code="BzComposer.configuration.isextrachargeppplicable"/></label>
								</td>
							</tr>
							<tr>
								<td style="font-size:12px;">
									<spring:message code="BzComposer.configuration.chargeextra"/> ($) :
								</td>
								<td style="font-size:12px;">
									<form:input path="chargeAmount" />
								</td>
								<td style="font-size:12px;">
									<spring:message code="BzComposer.configuration.iftotalorderislessthan"/> :
								</td>
								<td style="font-size:12px;">
									<form:input path="orderAmount" />
								</td>
							</tr>
							<tr>
								<td style="font-size:12px;">
									<input type="checkbox" name="backOrderNeeded" value="0" />
									<spring:message code="BzComposer.configuration.backorderconfirmationneeded"/>
								</td>
							</tr>

						</table>
 					</div>
				</div>
								<div id="SalesOption">
					<div id="content1" class="tabPage">
   						 <table class="table-notifications" width="100%">
							<tr>
								<th colspan="4" align="left" style="font-size:12px; padding: 5px;"><spring:message code="BzComposer.configuration.tab.salesoption" /></th>
							</tr>
							<tr>
								<td style="width:100px;font-size: 12px;">
									<b><spring:message code="BzComposer.configuration.setup"/></b>
								</td>
								<td style="font-size:12px;">
									<select id="setupID" onchange="showSetupID()">
										<option value="Location"><spring:message code="BzComposer.configuration.location"/></option>
										<option value="Message"><spring:message code="BzComposer.configuration.message"/></option>
										<option value="REP"><spring:message code="BzComposer.configuration.rep"/></option>
										<option value="Terms"><spring:message code="BzComposer.configuration.terms"/></option>
										<option value="SalesTax"><spring:message code="BzComposer.configuration.salestax"/></option>
										<option value="creditTerm"><spring:message code="BzComposer.configuration.lineofcreditterm"/></option>
									</select>
								</td>
								<td rowspan="2" align="center">
									<button type="button" class="formButton" id="AddDecription" name="AddDescription" onclick="AddDescription1()" style="width:60px;font-size: 1em;">
										<spring:message code="BzComposer.global.add"/>
									</button>
									<br><br>
									<button type="button" class="formButton" id="Delete" name="Delete" onclick="deleteDescription()" style="width:60px;font-size: 1em;">
										<spring:message code="BzComposer.global.delete"/>
									</button>
									<br><br>
									<button type="button" class="formButton" id="Update" name="Update" onclick="updateDescription()" style="width:60px;font-size: 1em;">
										<spring:message code="BzComposer.global.update"/>
									</button>
									<br><br>
									<button type="button" class="formButton" id="Clear" name="Clear" onclick="clearDescription()" style="width:60px;font-size: 1em;">
										<spring:message code="BzComposer.global.clear"/>
									</button>
								</td>
								<td style="height:2px;">
									<b><spring:message code="BzComposer.configuration.description"/> :</b>
									<br>
									<form:input path="description" id="description" style="font-size:1em;" />
									<!-- <input type="text" id="description" name="description" style="font-size:1em;"> -->
								</td>
							</tr>
							<tr>
								<td></td>
								<td style="width:100px;font-size:12px;">
									<div id="locationDiv">
									<table><tr><td>
									<select id="location" name="location" style="display:block; width: 200px; height: 200px;font-size:12px;" multiple="multiple">
									    <c:if test="${not empty configDto.listOfExistingLocation}">
                                            <c:forEach items="${configDto.listOfExistingLocation}" var="objList1">
                                                <option value="${objList1.selectedLocationId}" onclick="setDescription()">${objList1.locationName}</option>
                                            </c:forEach>
                                        </c:if>
									</select>
									</td></tr>
									</table>
									</div>
									<select id="message" name="message" style="display:none; width: 200px; height: 200px;" multiple="multiple">
									    <c:if test="${not empty configDto.messages}">
                                            <c:forEach items="${configDto.messages}" var="objList1">
                                                <option value="${objList1.selectedMessageId}" onclick="setDescription1()">${objList1.messageName}</option>
                                            </c:forEach>
                                        </c:if>
									</select>
									<select id="rep" name="rep" style="display:none; width: 200px; height: 200px;" multiple="multiple">
									    <c:if test="${not empty configDto.listOfExistingSalesRep}">
                                            <c:forEach items="${configDto.listOfExistingSalesRep}" var="objList1">
                                                <option value="${objList1.selectedSalesRepId}" onclick="setDescription2()">${objList1.salesRepName}</option>
                                            </c:forEach>
                                        </c:if>
									</select>
									<select id="terms" name="terms" style="display:none; width: 200px; height: 200px;" multiple="multiple">
									<c:if test="${not empty configDto.listOfExistingTerm}">
                                        <c:forEach items="${configDto.listOfExistingTerm}" var="objList1">
                                            <option id="${objList1.selectedTermId}" onclick="setDescription3()" value="${objList1.days}">${objList1.termName}</option>
                                        </c:forEach>
                                    </c:if>
									</select>
									<select id="salesTax" name="salesTax" style="display:none; width: 200px; height: 200px;" multiple="multiple">
									<c:if test="${not empty configDto.listOfExistingSalesTax}">
                                        <c:forEach items="${configDto.listOfExistingSalesTax}" var="objList1">
                                            <option id="${objList1.selectedSalesTaxId}" onclick="setDescription4()" value="${objList1.salesTaxRate}">${objList1.salesTaxName}</option>
                                        </c:forEach>
                                    </c:if>
									</select>
									<select id="creditTerm" name="creditTerm" style="display:none; width: 200px; height: 200px;" multiple="multiple">
									<c:if test="${not empty configDto.listOfExistingCreditTerm}">
                                        <c:forEach items="${configDto.listOfExistingCreditTerm}" var="objList1">
                                            <option name="${objList1.isDefault}" id="${objList1.selectedCreditTermId}" onclick="setDescription5()" value="${objList1.days}">${objList1.creditTermName}</option>
                                        </c:forEach>
                                    </c:if>
									</select>
								</td>
								<td>
									<div id="days" style="display:none;font-size: 12px;">
										<b><spring:message code="BzComposer.configuration.days"/><br></b>
										<input type="text" id="txtTerms" name="txtTerms">
									</div>
									<div id="tax" style="display:none;font-size: 12px;">
										<b><spring:message code="BzComposer.configuration.tax"/><br></b>
										<input type="text" id="txtTax" name="txtTax">
									</div>
									<div id="days1" style="display:none;font-size: 12px;">
										<b><spring:message code="BzComposer.configuration.days"/><br></b>
										<input type="text" id="txtDays" name="txtDays"><br><br>
										<form:checkbox path="isDefaultCreditTerm" id="isDefaultCreditTerm" value="${configDto.isDefaultCreditTerm}" />
										<label><spring:message code="BzComposer.configuration.default"/></label>
									</div>
								</td>
							</tr>
						</table>
 					</div>
				</div>
				<div id="RefundSettings">
					<div id="content1" class="tabPage">
						<input type="checkbox" name="isRefundAllowed" id="isRefundAllowed" value="${configDto.isRefundAllowed}" ${configDto.isRefundAllowed=='on'?'checked':''} />
						<label><spring:message code="BzComposer.configuration.allowrefundforsaleorder"/></label>
						<div id="refundDiv" style="display:block;">
   						<table class="table-notifications" width="100%">
							<tr>
								<th colspan="4" align="left" style="font-size:12px; padding: 5px;">
									<spring:message code="BzComposer.configuration.refundsetting" /></th>
							</tr>
							<tr>
								<td style="font-size:12px;">
									<b><spring:message code="BzComposer.configuration.reason" /></b><br>
									<input type="text" id="refundReason" name="refundReason"><br><br>
									<!-- <input type="button" id="addRefundReason" name="addRefundReason" value="Add">&nbsp;&nbsp; -->
									<!-- <button type="submit" id="addRefundReason" name="addRefundReason" class="formbutton"
									style="width: 60px; font-size: 1em;" onclick="addRefundReason()">Add
									</button> -->
									<button type="submit" class="formButton" id="AddRefundReason" name="AddRefundReason" onclick="addNewRefundReason()"
									style="width:60px;font-size: 14px;">
										<spring:message code="BzComposer.global.add"/>
									</button>
									&nbsp;&nbsp;
									<button type="submit" id="updateRefundReason" name="updateRefundReason" class="formButton"
									style="width: 80px; font-size: 14px;" onclick="updateExistingRefundReason()">
										<spring:message code="BzComposer.global.update"/>
									</button>&nbsp;&nbsp;
									<button type="submit" id="deleteRefundReason" name="deleteRefundReason" class="formbutton"
									style="width: 60px; font-size: 14px;" onclick="deleteSelectedRefundReason()">
										<spring:message code="BzComposer.global.delete"/>
									</button>
									<!-- <input type="button" id="updateRefundReason" name="updateRefundReason" value="Update">&nbsp;&nbsp; -->
									<!-- <input type="button" id="deleteRefundReason" name="deleteRefundReason" value="Delete"> -->
								</td>
								<td rowspan="2" align="justify" style="font-size:12px;">
									<select id="refundReasonSel" name="refundReasonSel" style="display:block; width: 200px; height: 200px;" multiple="multiple">
									<option value="Default Reason" onclick="setReason()">
										<spring:message code="BzComposer.configuration.defaultreason"/>
									</option>
									<c:if test="${not empty configDto.listOfExistingRefundReason}">
                                        <c:forEach items="${configDto.listOfExistingRefundReason}" var="objList1">
                                            <option name="${objList1.refundReason}" id="${objList1.selectedRefundReasonId}" onclick="setReason()" value="${objList1.isDefaultRefundReason}">${objList1.refundReason}</option>
                                        </c:forEach>
                                    </c:if>
									</select>
									<br>
									<button type="submit" id="defaultReason" name="defaultReason" onclick="setDefaultReason()" class="formButton" style="font-size: 14px;">
										<spring:message code="BzComposer.configuration.setasdefaultreasonbtn"/>
									</button>
									<br><br>
									<spring:message code="BzComposer.configuration.defaultreasonnote"/>
								</td>
							</tr>
						</table>
						</div>
 					</div>
				</div>

				<div id="Customer&Job">
					<div id="content1" class="tabPage">
   						 <div id="refundDiv" style="display:block;">
   						<table class="table-notifications" width="100%">
							<tr>
								<th colspan="4" align="left" style="font-size:12px; padding: 5px;">
									<spring:message code="BzComposer.configuration.tab.customerjob" />
								</th>
							</tr>
							<tr>
								<td style="font-size:14px;">
									<b><spring:message code="BzComposer.configuration.jobcategory" /></b><br>
									<input type="text" id="txtJobCategory" name="txtJobCategory"><br><br>
									<button type="button" id="addJobCategory" name="addJobCategory" onclick="addNewJobCategory()" class="formButton">
										<spring:message code="BzComposer.global.add"/>
									</button>&nbsp;&nbsp;
									<button type="button" id="updateJobCategory" name="updateJobCategory" onclick="updateExistingJobCategory()" class="formButton">
										<spring:message code="BzComposer.global.update"/>
									</button>&nbsp;&nbsp;
									<button type="button" id="deleteJobCategory" name="deleteJobCategory" onclick="deleteSelectedJobCategory()" class="formButton">
										<spring:message code="BzComposer.global.delete"/>
									</button>
									<!-- <input type="button" id="addJobCategory" name="addJobCategory" value="Add">&nbsp;&nbsp;
									<input type="button" id="updateJobCategory" name="updateJobCategory" value="Update">&nbsp;&nbsp;
									<input type="button" id="deleteJobCategory" name="deleteJobCategory" value="Delete">-->

								</td>
								<td rowspan="2" align="justify">
									<select id="jobCategory" name="jobCategory" style="display:block; width: 200px; height: 200px;font-size:12px;" multiple="multiple">
									<option value="" onclick="setCategory()">
										<spring:message code="BzComposer.configuration.defaultcategory"/>
									</option>
									<c:if test="${not empty configDto.listOfExistingJobCategory}">
                                        <c:forEach items="${configDto.listOfExistingJobCategory}" var="objList1">
                                            <option id="${objList1.recurringServiceBill}" onclick="setCategory()" value="${objList1.jobCategoryId}">${objList1.jobCategory}</option>
                                        </c:forEach>
                                    </c:if>
									</select>
								</td>
							</tr>
							<tr>
								<td>

								</td>
							</tr>
							<tr>
								<th colspan="4" align="left" style="font-size:12px; padding: 5px;">
									<spring:message code="BzComposer.configuration.recurringservicebilling" />
								</th>
							</tr>
							<tr>
								<td style="font-size:12px;">
									<%-- <input type="checkbox" id="recurringServiceBill" checked="checked">
										<spring:message code="Bizcomposer.useRecurringServiceBill"/> --%>
									<input type="checkbox" name="recurringServiceBill" id="recurringServiceBill" value="${configDto.recurringServiceBill}" ${configDto.recurringServiceBill=='on'?'checked':''} />
									<label><spring:message code="BzComposer.configuration.userecurringservicebill"/></label>
								</td>
							</tr>
							<tr>
								<td style="font-size:12px;">
									<spring:message code="BzComposer.configuration.recurringservicebillingname"/> :
								</td>
								<td style="font-size:12px;">
									<input type="text" id="serviceBillName" name="serviceBillName" value="Recurring Service Billing" style="width:200px;">
								</td>
							</tr>
							<tr>
								<td colspan="2" style="font-size:12px;">
									<spring:message code="BzComposer.configuration.recurringservicebillingnote"/>
								</td>
							</tr>
							<tr>
								<td></td>
								<td style="font-size:14px;">
									<!-- <input type="button" id="EditInfo" name="EditInfo" value="EditInfo"> -->
									<button type="submit" id="EditInfo" name="EditInfo" onclick="EditServiceBillInfo()" class="formButton">
										<spring:message code="BzComposer.configuration.editinfobtn"/>
									</button>
								</td>
							</tr>
						</table> 
						</div>   
 					</div>
				</div>
								<div id="Rma">
									<div id="content1" class="tabPage">
 										<!-- RMA Starts -->
										<div id="tabs2" style="height:450px;">
							  					<ul>
							    					<li style="font-size: 12px; padding: 5px;"><a href="#addModifyReason">
							    						<spring:message code="BzComposer.configuration.addmodifyreason" /></a>
							   						</li>
							    					<li style="font-size: 12px; padding: 5px;"><a href="#chargeForRMAItem" style="display:none;">
							    						<spring:message code="BzComposer.configuration.chargeforrma" /></a>
							   						</li>
							  					</ul>
							  					<div id="addModifyReason">
													<table class="table-notifications" width="100%">
														<tr>
															<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
																<b><spring:message code="BzComposer.configuration.addmodifyreason" /></b>
															</th>
														</tr>
														<tr>
															<td style="font-size: 12px;">
																<spring:message code="BzComposer.configuration.addReason"/> :
															</td>
															<td style="font-size: 12px;">
																<form:input path="reason" id="reason" />
															</td>
														</tr>
														<tr>
															<td style="font-size: 12px;">
																<spring:message code="BzComposer.configuration.type"/> :
															</td>
															<td style="font-size: 12px;">
																<form:select path="reasonTypeId" id="availableReasons">
																    <c:if test="${not empty configDto.listOfExistingReasonType}">
                                                                        <c:forEach items="${configDto.listOfExistingReasonType}" var="objList1">
                                                                            <option value="${objList1.reasonTypeId}">${objList1.reasonType}</option>
                                                                        </c:forEach>
                                                                    </c:if>
																</form:select>
															</td>
														</tr>
														<tr>
															<td>
																<form:select path="parentReasonId"  id="parentReasonId" multiple="multiple" style="height:100px;font-size: 12px;" onchange="selectReason()">
																    <c:if test="${not empty configDto.listOfExistingMasterReasonType}">
                                                                        <c:forEach items="${configDto.listOfExistingMasterReasonType}" var="objList1">
                                                                            <option name="${objList1.reason}" id="${objList1.parentReasonId}" value="${objList1.reasonId}">${objList1.reason}</option>
                                                                            <%-- <option style="display: none;" id="${objList1.reason}" value="${objList1.parentReasonId}">${objList1.reasonType}</option> --%>
                                                                        </c:forEach>
                                                                    </c:if>
																</form:select>
															</td>
															<td style="font-size: 14px;padding-top: 20px;">
																<button type="Submit" class="formButton" id="Save" name="Save" style="width:70px;" onclick="addNewReason()">
																    <spring:message code='BzComposer.global.save'/>
																</button>
																<button type="Submit" class="formButton" id="Update" name="Update" style="width:70px;" onclick="updateRMAReason()">
																    <spring:message code='BzComposer.global.update'/>
																</button>
																<button type="Submit" class="formButton" id="Delete" name="Delete" style="width:70px;" onclick="deleteReason()">
																    <spring:message code='BzComposer.global.delete'/>
																</button>
																<button type="button" class="formButton" id="Clear" name="Clear" style="width:70px;" onclick="clearValues()">
																    <spring:message code='BzComposer.global.clear'/>
																</button>
															</td>
														</tr>
													</table>
													<table class="table-notifications" width="100%">
														<tr>
															<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
																<b><spring:message code="BzComposer.configuration.defaultbankforaction" /></b>
															</th>
														</tr>
														<tr>
															<td style="font-size: 12px;">
																<spring:message code="BzComposer.configuration.defaultbank"/>:
															</td>
															<td style="font-size: 12px;">
																<%-- <form:select path="selectedBankAccountId" id="selectedBankAccountId" onchange="showSelectedAccount()">> --%>
																<form:select path="selectedBankAccountId" id="selectedBankAccountId">
																    <c:if test="${not empty configDto.listOfExistingBankAccount}">
                                                                        <c:forEach items="${configDto.listOfExistingBankAccount}" var="objList1">
                                                                            <option value="${objList1.selectedBankAccountId}">${objList1.selectedAccountName}</option>
                                                                        </c:forEach>
                                                                    </c:if>
																</form:select>
															</td>
														</tr>
													</table>
												</div>
												<div id="chargeForRMAItem" style="display:none;">
													<table class="table-notifications" width="100%">
														<tr>
															<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
																<b><spring:message code="BzComposer.configuration.chargeforrma" /></b>
															</th>
														</tr>
														<tr>
															<td style="font-size: 12px;">
																<spring:message code="BzComposer.configuration.chargeforrmaitem"/> :
															</td>
															<td>
																<%-- <form:input path="" /> --%>
															</td>
														</tr>
													</table>
												</div>
											</div>
										<!-- RMA Ends -->
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
		
		<input type="hidden" name="selectedBankAccountId" id="selectedBankAccountId" value="" />
		<input type="hidden" name="selectedTermId" id="selectedTermId" value="" />
		<input type="hidden" name="selectedSalesRepId" id="selectedSalesRepId" value="" />
		<input type="hidden" name="selectedPaymentId" id="selectedPaymentId" value="" />
		
		<input type="hidden" name="selectedInvoiceStyleId" id="selectedInvoiceStyleId" value=""/>
		<input type="hidden" name="sortBy" id="sortBy" value=""/>
		<input type="hidden" name="custTaxable" id="custTaxable" value=""/>
		<input type="hidden" name="addressSettings" id="addressSettngs" value=""/>
		<input type="hidden" name="isSalesOrder" id="isSalesOrder" value=""/>
		
		<input type="hidden" name="Description" id="Description" value=""/>
		<input type="hidden" name="locationID" id="locationID" value=""/>
		<input type="hidden" name="isDefault" id="isDefault" value=""/>
		<input type="hidden" name="creditTermDays" id="creditTermDays" value=""/>
		
		<input type="hidden" name="saleShowCountry" id="saleShowCountry" value=""/>
		<input type="hidden" name="ratePriceChangable" id="ratePriceChangable" value=""/>
		<input type="hidden" name="saleShowTelephone" id="saleShowTelephone" value=""/>
		<input type="hidden" name="isSalePrefix" id="isSalePrefix" value=""/>
		<input type="hidden" name="extraChargeApplicable" id="extraChargeApplicable" value=""/>
		<input type="hidden" name="resonId" id="resonId" />
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
function AddDescription1()
{
	//alert("Inside AddDescription");
	var text = $('#description').val();
	var setup = $("#setupID option:selected").val();
	$("#parentReasonId").val(0);
	if(text == "" || text == " ")
	{
		alert("<spring:message code='BzComposer.configuration.customerinvoice.emptydata'/>");
	}
	else
	{
		if(setup == "Location")
		{
			document.getElementById('Description').value = text;
			document.getElementById('tabid').value="addDescription";
			document.forms['form'].action = "Configuration";
			document.forms['form'].submit();
			var formData = $('form').serialize();
		}
		else if(setup == "Message")
		{
			document.getElementById('Description').value = text;
			document.getElementById('tabid').value="addNewMessage";
			document.forms['form'].action = "Configuration";
			document.forms['form'].submit();
		}
		else if(setup == "REP")
		{
			document.getElementById('Description').value = text;
			document.getElementById('tabid').value="addNewSalesRep";
			document.forms['form'].action = "Configuration";
			document.forms['form'].submit();
		}
		else if(setup == "Terms")
		{
			var days = $("#txtTerms").val();
			document.getElementById('Description').value = text;
			document.getElementById('locationID').value = days;
			document.getElementById('tabid').value="addNewTerms";
			document.forms['form'].action = "Configuration";
			document.forms['form'].submit();
		} 
		else if(setup == "SalesTax")
		{
			var tax = $("#txtTax").val();
			document.getElementById('Description').value = text;
			document.getElementById('locationID').value = tax;
			document.getElementById('tabid').value="addNewSalesTax";
			document.forms['form'].action = "Configuration";
			document.forms['form'].submit();
		}
		else
		{
			var days = $("#txtDays").val();
			document.configurationForm.isDefaultCreditTerm.value = document.configurationForm.isDefaultCreditTerm.value;
			document.getElementById('Description').value = text;
			document.getElementById('locationID').value = days;
			document.getElementById('isDefault').value = $("#isDefaultCreditTerm").val();
			document.getElementById('tabid').value="addNewCreditTerm";
			document.forms['form'].action = "Configuration";
			document.forms['form'].submit();
		}
	}
}
function SaveValues()
{
	/* if(confirm('<spring:message code="BzComposer.configuration.saveconfirm"/>'))
	{
		//Customer Panel
		debugger
		document.configurationForm.sortBy.value = document.configurationForm.sortBy.value;
		document.configurationForm.customerGroup.value = document.configurationForm.customerGroup.value;
		document.configurationForm.custDefaultCountryID.value = document.configurationForm.custDefaultCountryID.value;
		document.configurationForm.selectedStateId.value = document.configurationForm.selectedStateId.value;
		document.configurationForm.custTaxable.value = $("#custTaxable").val();
		document.configurationForm.customerProvince.value = document.configurationForm.customerProvince.value;
		document.configurationForm.customerShippingId.value = document.configurationForm.customerShippingId.value;
		document.configurationForm.selectedTermId.value = document.configurationForm.selectedTermId.value;
		document.configurationForm.selectedSalesRepId.value = document.configurationForm.selectedSalesRepId.value;
		document.configurationForm.selectedPaymentId.value = document.configurationForm.selectedPaymentId.value;
		document.configurationForm.addressSettings.value = $("#addressSettings").val();
		document.configurationForm.isSalesOrder.value = $("#isSalesOrder").val();
		debugger
		
		document.getElementById('custTaxable').value = $("#custTaxable").val();
		document.getElementById('addressSettings').value = $("#addressSettings").val();
		document.getElementById('isSalesOrder').value = $("#isSalesOrder").val();
		
		//Sales&Invoice Panel
		document.configurationForm.startInvoiceNo.value = document.configurationForm.startInvoiceNo.value;
		document.configurationForm.packingSlipTemplateId.value = document.configurationForm.packingSlipTemplateId.value;
		document.configurationForm.selectedInvoiceStyleId.value = document.configurationForm.selectedInvoiceStyleId.value;
		document.configurationForm.poNumPrefix.value = document.configurationForm.poNumPrefix.value;
		document.configurationForm.selectedMessageId.value = document.configurationForm.selectedMessageId.value;
		document.configurationForm.saleShowCountry.value = $("#saleShowCountry").val();
		document.configurationForm.ratePriceChangable.value = $("#ratePriceChangable").val();
		document.configurationForm.saleShowTelephone.value = $("#saleShowTelephone").val();
		document.configurationForm.isSalePrefix.value = $("#isSalePrefix").val();
		document.configurationForm.salesTaxCode.value = document.configurationForm.salesTaxCode.value;
		document.configurationForm.saleTaxRate.value = document.configurationForm.saleTaxRate.value;
		document.configurationForm.howOftenSalestax.value = document.configurationForm.howOftenSalestax.value;
		document.configurationForm.dropShipCharge.value = document.configurationForm.dropShipCharge.value;
		document.configurationForm.isShowDropShipItems.value = document.configurationForm.isShowDropShipItems.value;
		document.configurationForm.extraChargeApplicable.value = $("#extraChargeApplicable").val();
		document.configurationForm.chargeAmount.value = document.configurationForm.chargeAmount.value;
		document.configurationForm.orderAmount.value = document.configurationForm.orderAmount.value;
		
		//for Refund Settings panel
		document.configurationForm.isRefundAllowed.value =  $("#isRefundAllowed").val(); 
		
		//for Invoice Save Option
		debugger
		//document.configurationForm.invoiceLocation.value = document.configurationForm.invoiceLocation.value ; 
		
		document.getElementById('saleShowCountry').value = $("#saleShowCountry").val();
		document.getElementById('ratePriceChangable').value = $("#ratePriceChangable").val();
		document.getElementById('saleShowTelephone').value = $("#saleShowTelephone").val();
		document.getElementById('isSalePrefix').value = $("#isSalePrefix").val();
		document.getElementById('extraChargeApplicable').value = $("#extraChargeApplicable").val();
		document.getElementById('creditTermDays').value = $("#isRefundAllowed").val(); 
		
		document.getElementById('tabid').value="SaveCustomerInvocieSettings";
		document.forms[0].action = "Configuration";
		document.forms[0].submit();
	} */
	
	event.preventDefault();
	$("#showsaverecorddialog").dialog({
	    	resizable: false,
	        height: 200,
	        width: 500,
	        modal: true,
	        buttons: {
	        	"<spring:message code='BzComposer.global.ok'/>": function () {
	            	debugger;
	            	/*Customer Panel*/
	        		debugger
	        		document.configurationForm.sortBy.value = document.configurationForm.sortBy.value;
	        		document.configurationForm.customerGroup.value = document.configurationForm.customerGroup.value;
	        		document.configurationForm.custDefaultCountryID.value = document.configurationForm.custDefaultCountryID.value;
	        		document.configurationForm.selectedStateId.value = document.configurationForm.selectedStateId.value;
	        		debugger
	        		document.configurationForm.custTaxable.value = $("#custTaxable").val();
	        		debugger
	        		document.configurationForm.customerProvince.value = document.configurationForm.customerProvince.value;
	        		document.configurationForm.customerShippingId.value = document.configurationForm.customerShippingId.value;
	        		document.configurationForm.selectedTermId.value = document.configurationForm.selectedTermId.value;
	        		document.configurationForm.selectedSalesRepId.value = document.configurationForm.selectedSalesRepId.value;
	        		document.configurationForm.selectedPaymentId.value = document.configurationForm.selectedPaymentId.value;
	        		
	        		debugger
	        		document.getElementById('custTaxable').value = $("#custTaxable").val();
	        		debugger
	        		document.getElementById('addressSettings').value = $("#addressSettings").val();
	        		document.getElementById('isSalesOrder').value = $("#isSalesOrder").val();
	        		
	        		/*Sales&Invoice Panel*/
	        		document.configurationForm.startInvoiceNo.value = document.configurationForm.startInvoiceNo.value;
	        		document.configurationForm.packingSlipTemplateId.value = document.configurationForm.packingSlipTemplateId.value;
	        		document.configurationForm.selectedInvoiceStyleId.value = document.configurationForm.selectedInvoiceStyleId.value;
	        		document.configurationForm.poNumPrefix.value = document.configurationForm.poNumPrefix.value;
	        		document.configurationForm.selectedMessageId.value = document.configurationForm.selectedMessageId.value;
	        		
	        		document.configurationForm.saleShowCountry.value = $("#saleShowCountry").val();
	        		document.configurationForm.ratePriceChangable.value = $("#ratePriceChangable").val();
	        		document.configurationForm.saleShowTelephone.value = $("#saleShowTelephone").val();
	        		document.configurationForm.isSalePrefix.value = $("#isSalePrefix").val();
	        		
	        		document.configurationForm.salesTaxCode.value = document.configurationForm.salesTaxCode.value;
	        		document.configurationForm.saleTaxRate.value = document.configurationForm.saleTaxRate.value;
	        		document.configurationForm.howOftenSalestax.value = document.configurationForm.howOftenSalestax.value;
	        		document.configurationForm.dropShipCharge.value = document.configurationForm.dropShipCharge.value;
	        		document.configurationForm.isShowDropShipItems.value = document.configurationForm.isShowDropShipItems.value;
	        		document.configurationForm.extraChargeApplicable.value = $("#extraChargeApplicable").val();
	        		document.configurationForm.chargeAmount.value = document.configurationForm.chargeAmount.value;
	        		document.configurationForm.orderAmount.value = document.configurationForm.orderAmount.value;
	        		
	        		/*for Refund Settings panel*/
	        		document.configurationForm.isRefundAllowed.value =  $("#isRefundAllowed").val();
	        		document.configurationForm.recurringServiceBill.value =  $("#recurringServiceBill").val();
	        		
	        		/*for Invoice Save Option*/
	        		debugger
	        		//document.configurationForm.invoiceLocation.value = document.configurationForm.invoiceLocation.value ; 
	        		
	        		document.getElementById('saleShowCountry').value = $("#saleShowCountry").val();
	        		document.getElementById('ratePriceChangable').value = $("#ratePriceChangable").val();
	        		document.getElementById('saleShowTelephone').value = $("#saleShowTelephone").val();
	        		document.getElementById('isSalePrefix').value = $("#isSalePrefix").val();
	        		document.getElementById('extraChargeApplicable').value = $("#extraChargeApplicable").val();
	        		document.getElementById('creditTermDays').value = $("#isRefundAllowed").val();
	        		if($("#parentReasonId").val() == null){
	        		    $("#parentReasonId").val(0);
	        		}

	        		//Estimation value
	        		document.configurationForm.startingEstimationNumber.value = document.configurationForm.startingEstimationNumber.value;
	        		
	        		/* document.getElementById('tabid').value="SaveCustomerInvoiceSettings";
	        		document.forms["form"].action = "Configuration";
	        		document.forms["form"].submit(); */

	        		var formData = $('form').serialize();
	        		$.ajax({
	        			type : "POST",
	        			url : "ConfigurationAjax/SaveConfiguration?tabid=SaveCustomerInvoiceSettings",
	        			data : formData,
	        			success : function(data) {
	        				debugger
	        				$("#showsaverecorddialog").dialog("close");
	        				$("#showsuccessdialog").dialog({
	        						resizable: false,
	        				        height: 200,
	        				        width: 500,
	        				        modal: true,
	        				        buttons: {
	        				        	"<spring:message code='BzComposer.global.ok'/>": function () {
	        				        		$(this).dialog("close");
	        				                return false;
	        				        	},
	        				            "<spring:message code='BzComposer.global.cancel'/>": function () {
	        				                $(this).dialog("close");
	        				                return false;
	        				            }
        				        	}
	        				});
	        			},
	        			error : function(data) {
	        				alert("Some error occured.");
	        				alert(JSON.stringify(data));
	        			}
	        		});
	        		
	        		//$('form').submit();
	            },
	            "<spring:message code='BzComposer.global.cancel'/>": function () {
	                $(this).dialog("close");
	                return false;
	            }
	        }
	    });
	    return false;
}
function setDescription()
{
	var text = $('#location :selected').text();
	document.getElementById("description").value = text;
}

function setDescription1()
{
	var text = $('#message :selected').text();
	document.getElementById("description").value = text;
}

function setDescription2()
{
	var text = $('#rep :selected').text();
	document.getElementById("description").value = text;
}

function setDescription3()
{
	var text = $('#terms :selected').text();
	document.getElementById("description").value = text;
}

function setDescription4()
{
	var text = $('#salesTax :selected').text();
	var e = document.getElementById("salesTax");
	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("description").value = text;
	document.getElementById("txtTerms1").value = strUser;
}

function setDescription5()
{
	
	var text = $('#creditTerm :selected').text();
	document.getElementById("description").value = text;
	
	var e = document.getElementById("creditTerm");
	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("txtDays").value = strUser;
}
</script>
</html>
<!-- Dialog box used in this page -->
<div id="showsaverecorddialog" style="display:none;">
	<p><spring:message code="BzComposer.configuration.saveconfirm"/></p>
</div>
<!-- Dialog box used in this page -->
<div id="showsuccessdialog" style="display:none;">
	<p>Record updated</p>
</div>
<div id="deleteSelectedRecordDialog" style="display:none;">
	<p>Record deleted</p>
</div>