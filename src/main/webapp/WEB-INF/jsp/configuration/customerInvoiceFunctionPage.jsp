<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$(function() {
    $( "#tabs" ).tabs();
    $( "#tabs2" ).tabs();
  });
<%-- $(document).ready(function()
{
	$("#refundReasonSel option").each(function() 
	{
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
	$('select[id="customerTerm"]').find('option[value="'+termId+'"]').attr("selected",true);
	$('select[id="customerSalesRep"]').find('option[value="'+salesRepId+'"]').attr("selected",true);
	$('select[id="customerPaymentMethod"]').find('option[value="'+payMethodId+'"]').attr("selected",true);
	$('select[id="packingSlipTemplateId"]').find('option[value="'+packingSlipStyleId+'"]').attr("selected",true);
	
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
	Commented on 05-05-2019 due to this error:
	The code of method _jspService(HttpServletRequest, HttpServletResponse) is exceeding the 65535 bytes limit
	
	 $("#isRefundAllowed").change(function () 
	{
      	
      	var isChecked = '<%= request.getAttribute("isRefundAllowed")%>';
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
	$('select[id="selectedBankAccountId"]').find('option[value="'+accountID+'"]').attr("selected",true);
	$("#reason").val("");
	$("#parentReasonId option").prop("selected",false);
	$("#availableReasons option").prop("selected",false);
	
});  
 --%>
function disable() 
{
	var value = document.configurationForm.custDefaultCountryID.value;
	//alert("Selected Country:"+value);
	if(value == "2")
	{
		document.configurationForm.selectedStateId.disabled=false;
		$('#customerState').prop('disabled', false);
	}
	else
	{
		document.configurationForm.selectedStateId.disabled=true;
		$('#customerState').prop('disabled', true);
	}
}

function showSetupID()
{
	var val = document.getElementById("setupID").value;
		//alert("Selected SetUpId is:"+val);
		
		if(val == "Location")
		{
			document.getElementById("location").style.display = 'block';
			document.getElementById("message").style.display = 'none';
			document.getElementById("rep").style.display = 'none';
			document.getElementById("terms").style.display = 'none';
			document.getElementById("days").style.display = 'none';
			document.getElementById("salesTax").style.display = 'none';
			document.getElementById("tax").style.display = 'none';
			document.getElementById("creditTerm").style.display = 'none';
			document.getElementById("days1").style.display = 'none';
		}
		else if(val == "Message")
		{
			document.getElementById("location").style.display = 'none';
			document.getElementById("message").style.display = 'block';
			document.getElementById("rep").style.display = 'none';
			document.getElementById("terms").style.display = 'none';
			document.getElementById("days").style.display = 'none';
			document.getElementById("salesTax").style.display = 'none';
			document.getElementById("tax").style.display = 'none';
			document.getElementById("creditTerm").style.display = 'none';
			document.getElementById("days1").style.display = 'none';
		}
		else if(val == "REP")
		{
			document.getElementById("location").style.display = 'none';
			document.getElementById("message").style.display = 'none';
			document.getElementById("rep").style.display = 'block';
			document.getElementById("terms").style.display = 'none';
			document.getElementById("days").style.display = 'none';
			document.getElementById("salesTax").style.display = 'none';
			document.getElementById("tax").style.display = 'none';
			document.getElementById("creditTerm").style.display = 'none';
			document.getElementById("days1").style.display = 'none';
		}
		else if(val == "Terms")
		{
			document.getElementById("location").style.display = 'none';
			document.getElementById("message").style.display = 'none';
			document.getElementById("rep").style.display = 'none';
			document.getElementById("terms").style.display = 'block';
			document.getElementById("days").style.display = 'block';
			document.getElementById("salesTax").style.display = 'none';
			document.getElementById("tax").style.display = 'none';
			document.getElementById("creditTerm").style.display = 'none';
			document.getElementById("days1").style.display = 'none';
		}
		else if(val == "SalesTax")
		{
			document.getElementById("location").style.display = 'none';
			document.getElementById("message").style.display = 'none';
			document.getElementById("rep").style.display = 'none';
			document.getElementById("terms").style.display = 'none';
			document.getElementById("days").style.display = 'none';
			document.getElementById("salesTax").style.display = 'block';
			document.getElementById("tax").style.display = 'block';
			document.getElementById("creditTerm").style.display = 'none';
			document.getElementById("days1").style.display = 'none';
		}
		else if(val == "creditTerm")
		{
			document.getElementById("location").style.display = 'none';
			document.getElementById("message").style.display = 'none';
			document.getElementById("rep").style.display = 'none';
			document.getElementById("terms").style.display = 'none';
			document.getElementById("days").style.display = 'none';
			document.getElementById("salesTax").style.display = 'none';
			document.getElementById("tax").style.display = 'none';
			document.getElementById("creditTerm").style.display = 'block';
			document.getElementById("days1").style.display = 'block';
		}
	}
	
	function setDefaultReason()
	{
		var reason = $('#refundReasonSel :selected').text();
		var reasonId = $("#refundReasonSel option:selected").attr('id');
		if(reason == "")
		{
			alert("<spring:message code='BzComposer.configuration.customerinvoice.selectreasonfromlist'/>")
		}
		else
		{
			//alert("Selected Reason is:"+reason);
        	$('#refundReasonSel option:selected').remove();
        	document.getElementById('locationID').value = reasonId;
        	document.getElementById('tabid').value="makeDefaultReason";
			document.forms[0].action = "Configuration";
			document.forms[0].submit();
		}
	}
	
	function setReason()
	{
		var oldReason = $.trim($('#refundReasonSel option:selected').text());
		//$('#refundReasonSel option:selected').remove();
    	document.getElementById("refundReason").value = oldReason;	
	}
	
	function setCategory()
	{
		var oldCategory = $('#jobCategory option:selected').text();
		var isChecked = $("#jobCategory option:selected").attr("id");
		//$('#jobCategory option:selected').remove();
    	document.getElementById("txtJobCategory").value = oldCategory;
    	if(isChecked == "on")
    	{
    		$("#recurringServiceBill").prop('checked',true);
    	}
    	else
    	{
    		$("#recurringServiceBill").prop('checked',false);
    	}
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
		var strUser = $('#terms :selected').val();
		document.getElementById("txtTerms").value = strUser;
	}
	
	function setDescription4()
	{
		var text = $('#salesTax :selected').text();
		//var e = document.getElementById("salesTax");
		var strUser = $('#salesTax :selected').val();
		document.getElementById("description").value = text;
		document.getElementById("txtTax").value = strUser;
	}
	
	function setDescription5()
	{
		var text = $('#creditTerm :selected').text();
		var isChecked = $("#creditTerm option:selected").attr("name");
		document.getElementById("description").value = text;
		
		var e = document.getElementById("creditTerm");
		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("txtDays").value = strUser;
		if(isChecked == "on")
		{
			$("#isDefaultCreditTerm").prop("checked",true);
		}
		else
		{
			$("#isDefaultCreditTerm").prop("checked",false);
		}
	}
	
	/* function AddDescription1()
	{
		//alert("Inside AddDescription");
		var text = $('#description').val();
		var setup = $("#setupID option:selected").val();
		if(text == "" || text == " ")
		{
			alert("<spring:message code='BzComposer.configuration.customerinvoice.emptydata'/>");
		}
		else
		{
			if(setup == "Location")
			{
				document.getElementById('Description').value = text;
				/* document.getElementById('tabid').value="addDescription";
				document.forms[0].action = "Configuration";
				document.forms[0].submit(); */
				/* var formData = $('form').serialize();
        		$.ajax({
        			type : "POST",
        			url : "Configuration?tabid=addDescription",
        			data : formData,
        			success : function(data) {
        				debugger; */
        				/* $("#showsaverecorddialog").dialog("close"); */
        			/* 	$("#showsuccessdialog").dialog({
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
        			}
        		});
			}
			else if(setup == "Message")
			{
				document.getElementById('Description').value = text;
				document.getElementById('tabid').value="addNewMessage";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			}
			else if(setup == "REP")
			{
				document.getElementById('Description').value = text;
				document.getElementById('tabid').value="addNewSalesRep";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			}
			else if(setup == "Terms")
			{
				var days = $("#txtTerms").val();
				document.getElementById('Description').value = text;
				document.getElementById('locationID').value = days;
				document.getElementById('tabid').value="addNewTerms";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			} 
			else if(setup == "SalesTax")
			{
				var tax = $("#txtTax").val();
				document.getElementById('Description').value = text;
				document.getElementById('locationID').value = tax;
				document.getElementById('tabid').value="addNewSalesTax";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			}
			else
			{
				var days = $("#txtDays").val();
				document.configurationForm.isDefaultCreditTerm.value = document.configurationForm.isDefaultCreditTerm.value;
				document.getElementById('Description').value = text;
				document.getElementById('locationID').value = days;
				document.getElementById('isDefault').value = $("#isDefaultCreditTerm").val();
				document.getElementById('tabid').value="addNewCreditTerm";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			}
		} 
	} */
	
	
	/* function AddDescription()
	{
		debugger
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
				debugger */
				/*alert(text);
				 $.ajax({
					type: "POST",
	   				url:"Configuration?tabid=addDescription&Description="+text,
	               	data: { location : location } 
	           		}).done(function(data){
	           		debugger
	               	//$("#state").html(data);
	           		$(document).find('div#locationDiv table').replaceWith($(data).find('div#locationDiv').html());
	           		//$('select[id="phonecode"]').find('option[id="'+selectedCountry+'"]').attr("selected",true);
	           	}); */
				
				/* document.getElementById('Description').value = text;
				document.getElementById('tabid').value="addDescription";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
				debugger
			}	
			else if(setup == "Message")
			{
				debugger
				document.getElementById('Description').value = text;
				document.getElementById('tabid').value="addNewMessage";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
				debugger
			} */
			/* else if(setup == "REP")
			{
				document.getElementById('Description').value = text;
				document.getElementById('tabid').value="addNewSalesRep";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			} */
			/* else if(setup == "Terms")
			{
				//vat days = $("#txtTerms").val();
				document.getElementById('Description').value = text;
				document.getElementById('locationID').value = days;
				document.getElementById('tabid').value="addNewTerms";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			} */
			/* else if(setup == "Sales Tax")
			{
				var tax = $("#txtTax").val();
				document.getElementById('Description').value = text;
				document.getElementById('locationId').value = tax;
				document.getElementById('tabid').value="addNewSalesTax";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			} */
			/* else
			{
				//document.configurationForm.isDefaultCreditTerm.value = documetn.configurationForm.isDefaultCreditTerm.value;
				document.getElementById('Description').value = text;
				document.getElementById('locationId').value = tax;
				document.getElementById('isDefault').value = $("#isDefaultCreditTerm").val();
				document.getElementById('tabid').value="addNewCreditTerm";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
			}  */
		/*}
	}*/
	
	function deleteDescription()
	{
		var t =  $('#description').val();
		var setup = $("#setupID option:selected").val();
		$("#parentReasonId").val(0);
		if(t == "" || t == " ")
		{
			alert("<spring:message code='BzComposer.configuration.customerinvoice.selectitemtodelete'/>");
		}
		else
		{
			if(setup == "Location")
			{
				var deleteItem = confirm("<spring:message code='BzComposer.configuration.customerinvoice.deleteselectedrecord'/>");
				if(deleteItem)
				{
				    var text = $("#location option:selected").val();
					document.getElementById('locationID').value = text;
					document.getElementById('tabid').value="deleteLocation";
					document.forms['form'].action = "Configuration";
					document.forms['form'].submit();
				}
			}
			else if(setup == "Message")
			{
				var deleteItem = confirm("<spring:message code='BzComposer.configuration.customerinvoice.deleteselectedrecord'/>");
				if(deleteItem)
				{
					var text = $("#message option:selected").val();
					document.getElementById('locationID').value = text;
					document.getElementById('tabid').value="deleteMessage";
					document.forms['form'].action = "Configuration";
                    document.forms['form'].submit();
				}
			}
			else if(setup == "REP")
			{
				var deleteItem = confirm("<spring:message code='BzComposer.configuration.customerinvoice.deleteselectedrecord'/>");
				if(deleteItem)
				{
					var text = $("#rep option:selected").val();
					document.getElementById('locationID').value = text;
					document.getElementById('tabid').value="deleteSalesRep";
					document.forms['form'].action = "Configuration";
					document.forms['form'].submit();
				}
			}
			else if(setup == "Terms")
			{
				var deleteItem = confirm("<spring:message code='BzComposer.configuration.customerinvoice.deleteselectedrecord'/>");
				if(deleteItem)
				{
					var text = $("#terms option:selected").attr('id');
					document.getElementById('locationID').value = text;
					document.getElementById('tabid').value="deleteTerms";
					document.forms['form'].action = "Configuration";
					document.forms['form'].submit();
				}
			}
			else if(setup == "SalesTax")
			{
				var deleteItem = confirm("<spring:message code='BzComposer.configuration.customerinvoice.deleteselectedrecord'/>");
				if(deleteItem)
				{
					var text = $("#salesTax option:selected").attr('id');
					document.getElementById('locationID').value = text;
					document.getElementById('tabid').value="deleteSalesTax";
					document.forms['form'].action = "Configuration";
					document.forms['form'].submit();
				}
			}
			else
			{
				var deleteItem = confirm("<spring:message code='BzComposer.configuration.customerinvoice.deleteselectedrecord'/>");
				if(deleteItem)
				{
					var text = $("#creditTerm option:selected").attr('id');
					document.getElementById('locationID').value = text;
					document.getElementById('tabid').value="deleteCreditTerm";
					document.forms['form'].action = "Configuration";
					document.forms['form'].submit();
				}
			}
		}
	}
	
	function updateDescription()
	{
		var t =  $('#description').val();
		var setup = $("#setupID option:selected").val();
		$("#parentReasonId").val(0);
		if(t == "" || t == " ")
		{
			alert("<spring:message code='BzComposer.configuration.customerinvoice.emptydata'/>");
		}
		else
		{
			//alert("new Data is:"+t);
			if(setup == "Location")
			{
				
				var locationId = $("#location option:selected").val();
				document.getElementById('Description').value = t;
				document.getElementById('locationID').value = locationId;
				document.getElementById('tabid').value="updateDescription";
				document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
			}	
			else if(setup == "Message")
			{
				var messageId = $("#message option:selected").val();
				document.getElementById('Description').value = t;
				document.getElementById('locationID').value = messageId;
				document.getElementById('tabid').value="updateMessage";
				document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
			}
			else if(setup == "REP")
			{
				var repId = $("#rep option:selected").val();
				document.getElementById('Description').value = t;
				document.getElementById('locationID').value = repId;
				document.getElementById('tabid').value="updateSalesRep";
				document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
			}
			else if(setup == "Terms")
			{
				var termId = $("#terms option:selected").attr('id');
				var days = $("#txtTerms").val();
				document.getElementById('Description').value = t;
				document.getElementById('locationID').value = termId;
				document.getElementById('isDefault').value = days;
				document.getElementById('tabid').value="updateTerms";
				document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
			}
			else if(setup == "SalesTax")
			{
				var salesTaxId = $("#salesTax option:selected").attr('id');
				var tax = $("#txtTax").val();
				document.getElementById('Description').value = t;
				document.getElementById('locationID').value = salesTaxId;
				document.getElementById('isDefault').value = tax;
				document.getElementById('tabid').value="updateSalesTax";
				document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
			}
			else
			{
				var creditTermId = $("#creditTerm option:selected").attr('id');
				var days = $("#txtDays").val();
				document.getElementById('Description').value = t;
				document.getElementById('locationID').value = creditTermId;
				document.getElementById('creditTermDays').value = days;
				document.getElementById('isDefault').value = $("#isDefaultCreditTerm").val();
				document.getElementById('tabid').value="updateCreditTerm";
				document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
			}
		}
	}
	 
	function addNewJobCategory()
    {
		var itemExists = false;
    	if ($("#txtJobCategory").val() == '')
        {
            alert("<spring:message code='BzComposer.configuration.customerinvoice.enterjobcategory'/>");
        }
        else if($("#txtJobCategory").val() == ' ')
        {
        	alert("<spring:message code='BzComposer.configuration.customerinvoice.whitespaceisnotallowed'/>")
        }
        else
        {
            $("#jobCategory option").each(function() 
			{
                if ($(this).text() == $.trim($("#txtJobCategory").val())) 
                {
                    itemExists= true;
                    alert('<spring:message code="BzComposer.configuration.customerinvoice.jobcategoryexists"/>');
                    event.preventDefault();
                    $("#txtJobCategory").val('');
                }
            });

			if (!itemExists) 
			{
			    $("#parentReasonId").val(0);
				document.getElementById('Description').value= $("#txtJobCategory").val();
				document.getElementById('locationID').value = document.configurationForm.recurringServiceBill.value;
	          	document.getElementById('tabid').value="addJobCategory";
				document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
          	}
        }
    }

	function updateExistingJobCategory()
	{
	    var oldCategory = $('#jobCategory option:selected').text();
		var jCategoryId = $('#jobCategory option:selected').val();
		var newCategory = $('#txtJobCategory').val();
	    var itemExists = false;
		if (oldCategory == "" || jCategoryId == "")
		{
	    	alert("<spring:message code='BzComposer.configuration.customerinvoice.selectjobfromlist'/>");
		}
	    else if(oldCategory == ' ')
	    {
			alert("<spring:message code='BzComposer.configuration.customerinvoice.whitespaceisnotallowed'/>");
		}
	    else
	    {
			$("#jobCategory option").each(function() 
			{
                if ($(this).text() == $.trim($("#txtJobCategory").val())) 
                {
                    itemExists= true;
                    alert('<spring:message code="BzComposer.configuration.customerinvoice.samejobcantupdatetolist"/>');
                    event.preventDefault();
                    $("#txtJobCategory").val(oldCategory);
                }
            });

			if (!itemExists) 
			{
			    $("#parentReasonId").val(0);
				$('#jobCategory option:selected').remove();
				document.getElementById('Description').value= newCategory;
				document.getElementById('locationID').value = jCategoryId
				document.getElementById('isDefault').value = document.configurationForm.recurringServiceBill.value;
				document.getElementById('tabid').value="updateJobCategory";
	          	document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
          	}
		}
	}
	
	function deleteSelectedJobCategory()
	{
		var jCategoryId = $('#jobCategory option:selected').val();
        if ($("#jobCategory option").text() == "" || jCategoryId == "" || jCategoryId == null)
        {
			alert("<spring:message code='BzComposer.configuration.customerinvoice.selectjobfromlist'/>");
        }
        else
        {
        	var con = confirm("<spring:message code='BzComposer.configuration.customerinvoice.removeselectedjob'/>");
        	if(con)
        	{
        	    $("#parentReasonId").val(0);
        		//$('#jobCategory option:selected').remove();
            	$("#txtJobCategory").val('');
            	document.getElementById('locationID').value = jCategoryId;
				document.getElementById('tabid').value="deleteJobCategory";
	          	document.forms['form'].action = "Configuration";
				document.forms['form'].submit();
            }
        	
        }
	}
	
	function EditServiceBillInfo()
	{
		//alert("inside EditServiceInfo function")
		var billName = $('#serviceBillName').val();
		if(billName == "" || billName == " ")
		{
			alert("<spring:message code='BzComposer.configuration.customerinvoice.enterrecurringservicecategory'/>");
		}
		else
		{
			$("#parentReasonId").val(0);
			document.getElementById('Description').value = billName;
			document.getElementById('isDefault').value =  document.configurationForm.recurringServiceBill.value;

			document.getElementById('tabid').value="EditServiceBillInfo";
          	document.forms['form'].action = "Configuration";
			document.forms['form'].submit();
		}
	}
	/*$(function () {
        $("#updateRefundReason").click(function () 
        {
        	debugger
        	var oldReason = $('#refundReasonSel option:selected').text();
        	var newReason = $('#refundReason').text();
            if (oldReason == '' || oldReason == ' ') {
                alert("Please select a refund reason from the list");
            }
            else if(oldReason == newReason)
            {
            	alert("Same Reason can't update to the list")	
            }
            else
            {
            	debugger
            	$('#refundReasonSel option:selected').remove();
            	$('#refundReasonSel').append($("<option value="+$('#refundReason').val()+" onclick=setReason()>" + $('#refundReason').val() + "</option>"));
            	/*$('#refundReasonSel').append($("<option>" + newReason + "</option>"));
            }
        });
    });*/
	
	/* $(function () {
        $("#deleteRefundReason").click(function () {
        	debugger
        	var reason = $('#refundReasonSel selected').val();
            if (reason == '') {
                alert("Please select a refund reason from the list");
            }
            else if(reason == ' ')
            {
            	alert("Whitespace is not allowed")
            }
            else
            {
            	debugger
            	var con = confirm("Are you sure to remove this reason?");
            	//alert("Inside else condition")
            	if(con)
            	//$('#refundReasonSel option:selected').remove();
            	$("#refundReason").val('');
            		
            }
        });
    }); */
	
	/*$(function () {
        $("#addJobCategory").click(function (e) {
        	debugger
        	var cat = $("#txtJobCategory").val();
        	//var items = $("#jobCategory option").val();
            if (cat == '') {
                alert("Please enter a job category.");
            }
            else if(cat == ' ')
            {
            	alert("Whitespace is not allowed.")
            }
            else
            {
            	//$('#jobCategory').append($("<option value="+cat+">" + cat + "</option>"));
            	var itemExists = false;
                //var txt = $("#refundReason").val();
                e.preventDefault();
                $("#jobCategory option").each(function() {
                    if ($(this).text() == $.trim(cat)) {
                        itemExists = true;
                        alert('Job Category already exists');
                        $("#txtJobCategory").val('');
                    }
                });

              if (!itemExists) {
              $("#jobCategory").append("<option value="+cat+" onclick=setCategory()>" + cat + "</option>");
              $("#txtJobCategory").val('');
              }
            }
        });
    });*/
    
   /*$(function () {
        $("#updateJobCategory").click(function () {
        	var oldCategory = $('#jobCategory option:selected').text();
        	var newCategory = $('#txtJobCategory').val();
        	debugger
            if (oldCategory == "") {
                alert("Please Select a Job from the list.");
            }
            else if(oldCategory == ' ')
            {
            	alert("Whitespace is not allowed.")
            }
            else
            {
            	debugger
            	$('#jobCategory option:selected').remove();
            	$('#jobCategory').append($("<option value="+newCategory+" onclick=setCategory()>" + newCategory + "</option>"));
            }
        });
    });*/
	
	/*$(function () {
        $("#deleteJobCategory").click(function () {
        	debugger
            if ($("#jobCategory option").text() == "") {
                alert("Please Select a Job from the List");
            }
            else
            {
            	debugger
            	var con = confirm("Are You Sure to remove this job?");
            	if(con)
            	//$('#jobCategory option:selected').remove();
            	$("#txtJobCategory").val('');
            }
        });
    });
	*/
	
	function clearDescription()
	{
		//document.getElementById("description").value = "";
		$("#description").val("");
		
	}
	
	/*function addRefundReason()
	{
		debugger
		alert("Inside addRefundReason function")
    	var reason = $("#refundReason").val();
        if (reason == '') {
            alert("Please enter refund reason");
        }
        else if(reason == ' ')
        {
        	alert("Whitespace is not allowed")
        }
        else
        {
        	var itemExists = false;*/
            //e.preventDefault();
            /*$("#refundReasonSel option").each(function() {
                if ($(this).text() == $.trim(reason)) {
                    itemExists = true;
                    alert('Reason already exists');
                    $("#refundReason").val('');
                }
            });

          	if (!itemExists) 
          	{
          	
          		debugger
          		document.getElementById('Description').value = reason;
          		debugger*/
          		//document.getElementById('isDefault').value = $("#isRefundAllowed").val();
				/*debugger
				document.getElementById('tabid').value="addRefundReason";
				document.forms[0].action = "Configuration";
				document.forms[0].submit();
				debugger
          	}
		}
	}*/
	
	function addNewRefundReason()
	{
		
		//alert("Inside addNewRefundReason method");
		$("#parentReasonId").val(0);
		var reason = $("#refundReason").val();
        if (reason == '') {
            alert("<spring:message code='BzComposer.configuration.customerinvoice.enterrefundreason'/>");
        }
        else if(reason == ' '){
        	alert("<spring:message code='BzComposer.configuration.customerinvoice.whitespaceisnotallowed'/>")
        }
        else{
        	var itemExists = false;
            //e.preventDefault();
            //$(this).find('option').attr("name");
            $("#refundReasonSel option").each(function() {
                if ($(this).attr("name") == $.trim(reason)) {
                    itemExists = true;
                    alert('<spring:message code="BzComposer.configuration.customerinvoice.reasonalreadyexists"/>');
                    $("#refundReason").val('');
                    event.preventDefault();
                }
            });

          	if (!itemExists) 
          	{
          		document.getElementById('Description').value = reason;
          		//document.getElementById('isDefault').value = $("#isRefundAllowed").val();
				//debugger
				document.getElementById('tabid').value="addRefundReason";
				document.forms['form'].action = "Configuration?tabid=config6&&tab=tr6";
				document.forms['form'].submit();
				
          	}
		}
	}
	
	function updateExistingRefundReason()
	{
		$("#parentReasonId").val(0);
    	var oldReason = $('#refundReasonSel option:selected').text();
		var reasonId = $('#refundReasonSel option:selected').attr('id');
    	var newReason = $('#refundReason').val();
        if (oldReason == '' || oldReason == ' ') {
            alert("<spring:message code='BzComposer.configuration.customerinvoice.selectreasonfromlist'/>");
        }
        else if(oldReason == newReason)
        {
        	alert("<spring:message code='BzComposer.configuration.customerinvoice.samereasoncantupdate'/>");	
        }
        else
        {
        	$('#refundReasonSel option:selected').remove();
        	document.getElementById('Description').value = newReason;
        	document.getElementById('locationID').value = reasonId;
        	
        	document.getElementById('tabid').value="updateRefundReason";
			document.forms['form'].action = "Configuration?tabid=config6&&tab=tr6";
			document.forms['form'].submit();
			
        }
	}
	
	function deleteSelectedRefundReason()
	{
		$("#parentReasonId").val(0);
    	var reason = $('#refundReasonSel option:selected').text();
		var reasonId = $("#refundReasonSel option:selected").attr('id');
        if (reason == '') {
            alert("<spring:message code='BzComposer.configuration.customerinvoice.selectreasonfromlist'/>");
        }
       /*  else if(reason == ' ')
        {
        	alert("Whitespace is not allowed")
        } */
        else
        {
        	var con = confirm("<spring:message code='BzComposer.configuration.customerinvoice.removereason'/>");
        	if(con)
        	{
            	document.getElementById('locationID').value = reasonId;
            	document.getElementById('tabid').value="deleteRefundReason";
    			document.forms['form'].action = "Configuration?tabid=config6&&tab=tr6";
    			document.forms['form'].submit();
        	}
        }
	}
	
	/* Added on 04-05-2020 */
	function selectReason()
	{
		$('select[id="availableReasons"]').find('option').attr("selected",false);
		debugger
		var selectedReason = $.trim($("#parentReasonId option:selected").text());
		if(selectedReason ==""){
			//alert("<spring:message code='BzComposer.configuration.rma.enterreason'/>");
			return rmareasondialog();
		}
		else{
			//alert("SelectedReason:"+selectedReason);
			debugger
			var reasonSelected = $('select[id="parentReasonId"]').find('option[id="'+selectedReason+'"]').val();
			debugger	
			$('select[id="availableReasons"]').find('option[value="'+reasonSelected+'"]').attr("selected",true);
			$("#reason").val(selectedReason);
		}
	}

	function addNewReason()
	{	
		debugger
		var newReason = $("#reason").val();
		debugger
		var camelized = newReason.toLowerCase().replace(/\b[a-z]/g, function(letter) {
	    	return letter.toUpperCase();
		});
		debugger
    	var selectedReason = $('select[id="parentReasonId"]').find('option[id="'+camelized+'"]').attr("selected",true);
		debugger
		var isAvailable = $.trim($("#parentReasonId option:selected").text());
		debugger
		if(newReason == "")
		{
			//alert("<spring:message code='BzComposer.configuration.rma.enterreason'/>");
			return rmareasondialog();
			$("#parentReasonId option").prop("selected",false);
		}
		else
		{
			if((newReason.match("^no") || newReason.match("^No"))&&(newReason.match("reason$") || newReason.match("Reason$"))) 
			{
				//alert("<spring:message code='BzComposer.configuration.rma.entervalidreason'/>");
				return entervalidreasondialog();
				$("#parentReasonId option").prop("selected",false);
			}
			else if(camelized == isAvailable || newReason == isAvailable)
			{
				debugger
				//alert("<spring:message code='BzComposer.configuration.customerinvoice.reasonalreadyexists'/>");
				return reasonnotexistdialog();
				$("#parentReasonId option").prop("selected",false);
			}
			/* if(newReason.match("reason$") || newReason.match("Reason$")) 
			{
				alert("Please enter valid reason")
			} */
			else
			{
				var parentReasonId = $("#availableReasons option:selected").val();
				var parentReason = $("#availableReasons option:selected").text();
				alert("New Reason:"+newReason+"\nParent Reason Id:"+parentReasonId+"\nParentReason:"+parentReason);
				document.getElementById('tabid').value="addNewRMAReason";
				document.getElementById('reason').value= newReason;
				debugger
				document.getElementById('parentReasonId').value= parentReasonId;
				debugger
				document.forms[0].action = "Configuration?tabid=addNewRMAReason";
				document.forms[0].submit();
			}
		}
		/* $("#parentReasonId option").prop("selected",false); */
		/* document.configurationForm.reason.value = document.configurationForm.reason.value;
		document.configurationForm. */
	}

	function updateRMAReason()
	{
		//alert("inside updateRMAReason method");
		debugger
		var reason = $("#reason").val();
		debugger
		/* var camelized = reason.toLowerCase().replace(/\b[a-z]/g, function(letter) {
	    return letter.toUpperCase();
		}); */
		var isAvailable = $.trim($("#parentReasonId option:selected").text());
		debugger
		var parentReasonId = $("#availableReasons option:selected").val();
		debugger
		var reasonId = $("#parentReasonId option:selected").val();
		debugger
		if(reason == "")
		{
			debugger
			//alert("<spring:message code='BzComposer.configuration.rma.enterreason'/>");
			return rmareasondialog();
			debugger
			$("#parentReasonId option").prop("selected",false);
		}
		/* else if(reason == camelized)
		{
			alert("Reason already exist")
		} */
		else if(isAvailable =="")
		{
			//alert("<spring:message code='BzComposer.configuration.rma.selectreasontoupdate'/>");
			return selectreasontoupdatedialog();
		}
		else{
			alert("ReasonID: "+reasonId+"\nReason: "+reason+"\nParentReasonID: "+parentReasonId);
			document.getElementById('tabid').value="updateRMAReason";
			document.getElementById('reason').value= reason;
			debugger
			document.getElementById('resonId').value = reasonId;
			debugger
			document.getElementById('parentReasonId').value= parentReasonId;
			debugger
			document.forms[0].action = "Configuration?tabid=updateRMAReason";
			document.forms[0].submit();
		}
	}

	function deleteReason()
	{
		var reason = $("#reason").val();
		var parentReasonId = $("#availableReasons option:selected").val();
		var parentReason = $("#availableReasons option:selected").text();
		//alert("Reason:"+reason+"\nParent Reason Id:"+parentReasonId+"\nParentReason:"+parentReason);
		if(confirm("<spring:message code='BzComposer.configuration.rma.deleteselectedrecord'/>"))
		{
			//alert("Redirectd to delete page code");
			document.getElementById('tabid').value = "deleteRMAReason";
			document.getElementById('reason').value = reason;
			document.getElementById('parentReasonId').value= parentReasonId;
			debugger
			document.forms[0].action = "Configuration?tabid=deleteRMAReason";
			document.forms[0].submit();
		}
		else{
			//alert("<spring:message code='BzComposer.configuration.rma.recordwillnotdeleted'/>");
			return recordnotdeleteddialog();
		}
	} 
	function clearValues()
	{
		debugger
		$("#reason").val("");
		$("#parentReasonId option").prop("selected",false);
		$("#availableReasons option").prop("selected",false);
		//$('select[id="parentReasonId"]').find('option').prop("selected",false);
		debugger
	}
</script>