<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/jsp/include/headlogo.jsp" />
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
<jsp:include page="/WEB-INF/jsp/include/menu.jsp" />
<title><spring:message code="BzComposer.generaltitle" /></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/dist/js/custom.js"></script>
<link href="${pageContext.request.contextPath}/tableStyle/tab/jquery-ui-tab.css" rel="stylesheet" media="screen" />
<script src="${pageContext.request.contextPath}/tableStyle/tab/jquery-ui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<script>
function toggleFunction() {
	debugger;
  var x = document.getElementById("divtoggle");
  var lftmenu = document.getElementById("leftMenu");
  if (x.style.display === "none") {
    x.style.display = "block";
    lftmenu.style.width = "180px";
    lftmenu.style.position = "relative";
    /* document.getElementById("togglebtn").value = "+"; */
  } else {
    x.style.display = "none";
    lftmenu.style.width = "0";
    lftmenu.style.position = "absolute";
    /* document.getElementById("togglebtn").value = "-"; */
  }
}
</script>
<script type="text/javascript">
function showLocale()
{
	var lang = document.getElementById("locale").value;
	var locale = "<%= request.getAttribute("selectedLocale")%>";

	if(lang == "" && locale != "")
	{
		$('select[id="locale"]').find('option[value="'+locale+'"]').attr("selected",true);
		return showLanguageDialog();
	}
	else
	{
		window.location="./changeLocale?lang="+lang;
	}
}
function showLanguageDialog()
{
	event.preventDefault();
	$("#showLanguageDialog").dialog({
    	resizable: false,
        height: 200,
        width: 400,
        modal: true,
        buttons: {
            "<spring:message code='BzComposer.global.ok'/>": function () {
                $(this).dialog("close");
            }
        }
    });
    return false;
}

$(function() {
    $("#tabs").tabs();
    $("#tabs1").tabs();
});
$(document).ready(function()
{
	debugger;
	var isSOBChecked = "<%= request.getAttribute("isSOBChecked")%>";
	var isISBChecked = "<%= request.getAttribute("isISBChecked")%>";
	var isIRBChecked = "<%= request.getAttribute("isIRBChecked")%>";
	var isPOBChecked = "<%= request.getAttribute("isPOBChecked")%>";
	var isSelectedWeightID = "<%= request.getAttribute("isSelectedWeightID")%>";

	$('select[id="weightID"]').find('option[value="'+isSelectedWeightID+'"]').attr("selected",true);

	$('#salesOrderBoard').change(function()
	{
		var isChecked = isSOBChecked;
		if($(this).prop("checked") == true)
		{
	        $("#salesOrderBoard").attr('checked', true);
	        isChecked = "on";
		}
	    else if($(this).prop("checked") == false)
	    {
	        $("#salesOrderBoard").attr('checked', false);
	        isChecked = "off";
		}
	    else
	    {
	        $("#salesOrderBoard").attr('checked', isChecked);
	    }
		document.configurationForm.salesOrderBoard.value = isChecked;
		$("#salesOrderBoard").val(isChecked);
	});
	$('#itemReceivedBoard').change(function()
	{

		var isChecked = isIRBChecked;
		if($(this).prop("checked") == true)
		{
	        $("#itemReceivedBoard").attr('checked', true);
	        isChecked = "on";
		}
	    else if($(this).prop("checked") == false)
	    {
	        $("#itemReceivedBoard").attr('checked', false);
	        isChecked = "off";
		}
	    else
	    {
	        $("#itemReceivedBoard").attr('checked', isChecked);
	    }
		document.configurationForm.itemReceivedBoard.value = isChecked;
		$("#itemReceivedBoard").val(isChecked);
	});
	$('#poboard').change(function()
	{
		var isChecked = isPOBChecked;
		if($(this).prop("checked") == true)
		{
	        $("#poboard").attr('checked', true);
	        isChecked = "on";
		}
	    else if($(this).prop("checked") == false)
	    {
	        $("#poboard").attr('checked', false);
	        isChecked = "off";
		}
	    else
	    {
	        $("#poboard").attr('checked', isChecked);
	    }
    	document.configurationForm.poboard.value = isChecked;
		$("#poboard").val(isChecked);
	});
	$('#itemShippedBoard').change(function()
	{
		var isChecked = isISBChecked;

		if($(this).prop("checked") == true)
		{
	        $("#itemShippedBoard").attr('checked', true);
	        isChecked = "on";
		}
	    else if($(this).prop("checked") == false)
	    {
	        $("#itemShippedBoard").attr('checked', false);
	        isChecked = "off";
		}
	    else
	    {
	        $("#itemShippedBoard").attr('checked', isChecked);
	    }
    	document.configurationForm.itemShippedBoard.value = isChecked;
		$("#itemShippedBoard").val(isChecked);
	});
});
function TestConnection()
{
    var authType = 'false';
	var host = document.configurationForm.mailServer.value;
	var userEmail = document.configurationForm.mailUserName.value;
	var password = document.configurationForm.mailPassword.value;
	if(document.configurationForm.mailAuth.checked){
        if(userEmail.length < 5 || !userEmail.includes('@')){
            alert("<spring:message code='BzComposer.configuration.invalidEmail' />");
            return;
        }
        else if(password.length < 3){
            alert("<spring:message code='BzComposer.configuration.invalidPassword' />");
            return;
        }
        authType = 'true';
    }
	oEmail = c(CheckEmailConnection);
	oGET(oEmail, 'include/testMailServerConnection.jsp?HostName='+host+'&authType='+authType+'&userEmail='+userEmail+'&password='+password);
}

var funsequence = 0;
var _1 = navigator.userAgent.toLowerCase();
var ___ = (_1.indexOf("msie") != -1);
var ___5 = (_1.indexOf("msie 5") != -1);
var _io = (_1.indexOf("opera") != -1);
var _im = (_1.indexOf("mac") != -1);
var ____gi = (_1.indexOf("gecko") != -1);
var i____s = (_1.indexOf("safari") != -1);
var o = null;
var o22 = null;
var o33 = null;
var oEmail = null;
var oT = null;
var nm="";
var r = null;

function c(r) {

  if (___) {
	var t = (___5) ? "Microsoft.XMLHTTP" : "Msxml2.XMLHTTP";
    try {
	  o = new ActiveXObject(t);
      o.onreadystatechange = r;
	} catch (ex) {
      alert("You need to enable active scripting and activeX ts.." + ex );
	}
  } else {
	o = new XMLHttpRequest();
    o.onload = r;
	o.onerror = r;
  }
  return o;
}
function oGET(oo, url) {
  try {
    pleaseWaitDialog();
	oo.open("GET", url, true);
    oo.send(null);
  }
  catch (ex) {
  }
}
function CheckEmailConnection()
{
    $('#pleaseWaitDialog').dialog("close");
	if (oEmail.readyState != 4 || oEmail.status != 200) {
	  	return;
	}
	response = parseInt(trim(oEmail.responseText));
	if(response == 1)
	{
		return serverConnectedSeccessDialog();
		document.configurationForm.mailAuth.disabled=false;
		EnableDisableFields2();
	}
	else
	{
		return serverConnectedErrorDialog();
		document.configurationForm.mailAuth.disabled=true;
		EnableDisableFields2();
	}
}

function EnableDisableFields2(){
	if(document.configurationForm.mailAuth.checked==true){
		document.configurationForm.mailUserName.disabled=false;
		document.configurationForm.mailPassword.disabled=false;
	}
	else{
		document.configurationForm.mailUserName.disabled=true;
		document.configurationForm.mailPassword.disabled=true;
	}
}
function trim(inputString) {
   // Removes the spaces  return from the passed string.
   var retValue = inputString;
   var ch = retValue.substring(0, 1);
   while (ch == "\n" || ch == "\r" || ch==" " || ch=="\t" ) {
  retValue = retValue.substring(1, retValue.length);
      ch = retValue.substring(0, 1);
   }
   return retValue;
}
function serverConnectedErrorDialog(){
	event.preventDefault();
	$("#serverConnectedErrorDialog").dialog({
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
function serverConnectedSeccessDialog(){
	event.preventDefault();
	$("#serverConnectedSeccessDialog").dialog({
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
function pleaseWaitDialog(){
	event.preventDefault();
	$("#pleaseWaitDialog").dialog({
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
</script>
<body onload="init();">
<form:form name="configurationForm" enctype="MULTIPART/FORM-DATA" method="post" modelAttribute="configDto">
<div id="ddcolortabsline">&nbsp;</div>
<div id="cos">
<div class="statusquo ok">
<div id="hoja">
<div id="blanquito">
<div id="padding">
<div>
	<span style="font-size: 1.1em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px;
	border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
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
		<table cellspacing="0" style="border: 0; padding: 0; width: 100%;overflow-y:scroll;" class="section-border">
			<!-- <tr>
				<td>
					<span style="font-size:30px;cursor:pointer; margin-left: 30px;" onclick="toggleFunction()">&#9776;</span>
				</td>
				<td></td>
			</tr> -->
			<tr>
			<span style="font-size:30px;cursor:pointer; margin-left: 20px;" onclick="toggleFunction()">&#9776;</span>
				<td id="leftMenu" style="position: relative; width: 180px;">
					<table>
						<tr>
							<td>
								<jsp:include page="menuPage.jsp" />
							</td>
						</tr>
					</table>
				</td>
				<td valign="top">
					<!-- General -->
					<div></div>
					<!-- general page content starts -->
					<div id="general" style="display:none;padding: 0; position: relative; left: 0;">
						<div id="tabs" style="height: auto;">
  							<ul>
    							<li style="font-size: 12px;">
    								<a href="#GeneralSetting">
    									<spring:message code="BzComposer.configuration.tab.generalsettings" />
   									</a>
								</li>
    							<li style="font-size: 12px;">
    								<a href="#features">
    									<spring:message code="BzComposer.configuration.tab.features" />
   									</a>
								</li>
    							<li style="font-size: 12px;">
    								<a href="#orderTemplate">
    									<spring:message code="BzComposer.configuration.tab.orderTemplate" />
   									</a>
								</li>
								<li style="font-size: 12px;">
    								<a href="#reminder">
    									<spring:message code="BizComposer.Configuration.Reminders"/>
   									</a>
								</li>
								<li style="font-size: 12px;">
    								<a href="#emailSetup">
    									<spring:message code="BzComposer.tab.eMailSetup"/>
   									</a>
								</li>
  							</ul>
  							<div id="GeneralSetting">
								<div id="content1" class="tabPage">
									<table class="table-notifications" width="80%">
										<tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.generaltitle" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.currency" />
											</td>
											<td style="font-size: 12px;">
												<form:select path="currencyID" id="currencyID">
													<form:option value="0">
														<spring:message code="BzComposer.configuration.select"/>
													</form:option>
													<form:option value="1">
														<spring:message code="BzComposer.configuration.currency.baht"/>
													</form:option>
													<form:option value="2">
														<spring:message code="BzComposer.configuration.currency.bolivar"/>
													</form:option>
													<form:option value="3">
														<spring:message code="BzComposer.configuration.currency.boliviano"/>
													</form:option>
													<form:option value="4">
														<spring:message code="BzComposer.configuration.currency.cedi"/>
													</form:option>
													<form:option value="5">
														<spring:message code="BzComposer.configuration.currency.dirham"/>
													</form:option>
													<form:option value="6">
														<spring:message code="BzComposer.configuration.currency.dinar"/>
													</form:option>
													<form:option value="7" selected="selected">
														<spring:message code="BzComposer.configuration.currency.dollar"/>
													</form:option>
													<form:option value="8">
														<spring:message code="BzComposer.configuration.currency.dong"/>
													</form:option>
													<form:option value="9">
														<spring:message code="BzComposer.configuration.currency.euro"/>
													</form:option>
													<form:option value="10">
														<spring:message code="BzComposer.configuration.currency.forint"/>
													</form:option>
													<form:option value="11">
														<spring:message code="BzComposer.configuration.currency.franc"/>
													</form:option>
													<form:option value="12">
														<spring:message code="BzComposer.configuration.currency.koruna"/>
													</form:option>
													<form:option value="13">
														<spring:message code="BzComposer.configuration.currency.krona"/>
													</form:option>
													<form:option value="14">
														<spring:message code="BzComposer.configuration.currency.krone"/>
													</form:option>
													<form:option value="15">
														<spring:message code="BzComposer.configuration.currency.newshekel"/>
													</form:option>
													<form:option value="16">
														<spring:message code="BzComposer.configuration.currency.nuevosol"/>
													</form:option>
													<form:option value="17">
														<spring:message code="BzComposer.configuration.currency.peso"/>
													</form:option>
													<form:option value="18">
														<spring:message code="BzComposer.configuration.currency.pound"/>
													</form:option>
													<form:option value="19">
														<spring:message code="BzComposer.configuration.currency.pula"/>
													</form:option>
													<form:option value="20">
														<spring:message code="BzComposer.configuration.currency.quetzal"/>
													</form:option>
													<form:option value="21">
														<spring:message code="BzComposer.configuration.currency.rand"/>
													</form:option>
													<form:option value="22">
														<spring:message code="BzComposer.configuration.currency.real"/>
													</form:option>
													<form:option value="23">
														<spring:message code="BzComposer.configuration.currency.ringgit"/>
													</form:option>
													<form:option value="24">
														<spring:message code="BzComposer.configuration.currency.riyal"/>
													</form:option>
													<form:option value="25">
														<spring:message code="BzComposer.configuration.currency.riyali"/>
													</form:option>
													<form:option value="26">
														<spring:message code="BzComposer.configuration.currency.rouble"/>
													</form:option>
													<form:option value="27">
														<spring:message code="BzComposer.configuration.currency.rupee"/>
													</form:option>
													<form:option value="28">
														<spring:message code="BzComposer.configuration.currency.rupiah"/>
													</form:option>
													<form:option value="29">
														<spring:message code="BzComposer.configuration.currency.schilling"/>
													</form:option>
													<form:option value="30">
														<spring:message code="BzComposer.configuration.currency.sucre"/>
													</form:option>
													<form:option value="31">
														<spring:message code="BzComposer.configuration.currency.won"/>
													</form:option>
													<form:option value="32">
														<spring:message code="BzComposer.configuration.currency.yen"/>
													</form:option>
													<form:option value="33">
														<spring:message code="BzComposer.configuration.currency.yuan"/>
													</form:option>
												</form:select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.weight" />
											</td>
											<td style="font-size: 12px;">
												<form:select path="weightID" style="font-size: 12px;" id="weightID">
													<c:if test="${not empty configDto.listOfExistingWeights}">
														<c:forEach items="${configDto.listOfExistingWeights}" var="objList1">
															<option value="${objList1.weightID}">${objList1.weightName}</option>
														</c:forEach>
													</c:if>
												</form:select>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.language" />
											</td>
											<td>
												<select name="locale" id="locale" onchange="showLocale();">
													<option value=""><spring:message code="BzComposer.selectlanguage"/></option>
													<option value="en" ${sessionScope.currentLocale=='en'?'selected':''}><spring:message code="BzComposer.selectlanguage.english"/></option>
													<option value="zh" ${sessionScope.currentLocale=='zh'?'selected':''}><spring:message code="BzComposer.selectlanguage.chinese"/></option>
													<option value="es" ${sessionScope.currentLocale=='es'?'selected':''}><spring:message code="BzComposer.selectlanguage.spanish"/></option>
												</select>
											</td>
										</tr>
										<tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.addresslabel" />
											</th>
										</tr>
										<tr colspan="2">
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.defaultlabel" />
											</td>
											<td style="font-size: 12px;">
												<c:if test="${not empty Labels}">
													<form:select path="defaultLabelID" onchange="SetLabelName(this.value);" style="width:200" id="defaultLabelID">
														<form:options items="${Labels}" itemValue="value" itemLabel="label" />
													</form:select>
												</c:if>
											</td>
										</tr>
										<tr>
											<td colspan="2" align="center" style="font-size: 14px;">
												<button type="button" class="formButton" title='<spring:message code="BzComposer.configuration.setuplabeltooltip"/>'>
												    <spring:message code="BzComposer.configuration.setuplabelbtn"/>
												</button>
											</td>
										</tr>
										<!-- <tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.defaultfilteroption" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.defaultfilteroption" />:
											</td>
											<td style="font-size: 12px;">
												<form:select path="filterOption" id="filterOption">
													<form:option value="All"><spring:message code="BzComposer.reportcenter.allinvoicelist.dates.all"/></form:option>
													<form:option value="Custom"><spring:message code="BzComposer.reportcenter.allinvoicelist.dates.custom"/></form:option>
													<form:option value="Today"><spring:message code="BzComposer.reportcenter.allinvoicelist.dates.today"/></form:option>
													<form:option value="This Month"><spring:message code="BzComposer.reportcenter.allinvoicelist.dates.thismonth"/></form:option>
													<form:option value="This Quarter"><spring:message code="BzComposer.configuration.filteroption.thisquarter"/></form:option>
													<form:option value="This Year"><spring:message code="BzComposer.configuration.filteroption.thisyear"/></form:option>
													<form:option value="1 Year"><spring:message code="BzComposer.configuration.filteroption.oneyear"/></form:option>
													<form:option value="2 Year"><spring:message code="BzComposer.configuration.filteroption.twoyear"/></form:option>
													<form:option value="3 Year"><spring:message code="BzComposer.configuration.filteroption.threeyear"/></form:option>
													<form:option value="This Month-to-Date"><spring:message code="BzComposer.reportcenter.allinvoicelist.dates.thismonthtodate"/></form:option>
													<form:option value="This Quarter-to-Date"><spring:message code="BzComposer.configuration.filteroption.thisquartertodate"/></form:option>
													<form:option value="This Year-to-Date"><spring:message code="BzComposer.configuration.filteroption.thisyeartodate"/></form:option>
													<form:option value="Last 10 days"><spring:message code="BzComposer.reportcenter.allinvoicelist.dates.last10days"/></form:option>
													<form:option value="Last 30 days"><spring:message code="BzComposer.reportcenter.allinvoicelist.dates.last30days"/></form:option>
													<form:option value="Last 60 days"><spring:message code="BzComposer.reportcenter.allinvoicelist.dates.last60days"/></form:option>
													<form:option value="1 Week"><spring:message code="BzComposer.configuration.filteroption.oneweek"/></form:option>
												</form:select>
											</td>
										</tr> -->
										<!-- <tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BizCompozer.StartingPageSetting" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.defaultstartingpage" />:
											</td>
											<td style="font-size: 12px;">
												<form:select path="moduleID" id="moduleID">
													<form:option value="3"><spring:message code="BzComposer.configuration.defaultstartingpage.customer"/></form:option>
													<form:option value="5"><spring:message code="BzComposer.configuration.defaultstartingpage.inventory"/></form:option>
													<form:option value="1"><spring:message code="BzComposer.configuration.defaultstartingpage.invoice"/></form:option>
													<form:option value="6"><spring:message code="BzComposer.configuration.defaultstartingpage.navigationpage"/></form:option>
													<form:option value="2"><spring:message code="BzComposer.configuration.defaultstartingpage.po"/></form:option>
													<form:option value="4"><spring:message code="BzComposer.configuration.defaultstartingpage.vendor"/></form:option>
												</form:select>
											</td>
										</tr> -->
										<tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.defaultdashboardsetting" />
												<form:hidden path="moduleID" />
												<form:hidden path="filterOption" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.defaultcustomdashboard" />:
											</td>
											<td align="left" style="font-size: 12px;">
												<input type="checkbox" name="salesOrderBoard" id="salesOrderBoard" value="${configDto.salesOrderBoard}" ${configDto.salesOrderBoard=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.defaultdashboard.opensalesorders" /></label>&nbsp;&nbsp;&nbsp;
												<input type="checkbox" name="itemReceivedBoard" id="itemReceivedBoard" value="${configDto.itemReceivedBoard}" ${configDto.itemReceivedBoard=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.defaultdashboard.itemreceived" /></label>
												<br/>
												<input type="checkbox" name="poboard" id="poboard" value='${configDto.poboard}' ${configDto.poboard=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.defaultdashboard.poboard" /></label>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="checkbox" name="itemShippedBoard" id="itemShippedBoard" value='${configDto.itemShippedBoard}' ${configDto.itemShippedBoard=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.defaultdashboard.itemshiped" /></label>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<div id="features">
   								<div id="content2" class="tabPage">
			   					 	<table class="table-notifications" width="80%">
										<tr>
											<th colspan="3" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.feturesheader" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<b>
													<spring:message code="BzComposer.configuration.features.availablemodules" />
												</b>
											</td>
											<td>&nbsp;&nbsp;</td>
											<td style="font-size: 12px;">
												<b>
													<spring:message code="BzComposer.configuration.features.selectedmodules" />
												</b>
											</td>
										</tr>
										<tr>
											<td width="25%" style="font-size: 12px;">
												<form:select path="selectedModuleId" id="selectedModuleId" style="width: 200px; height: 200px;font-size: 1em;" styleClass="featureName1" multiple="true">
													<c:if test="${not empty configDto.listOfExistingModules}">
														<c:forEach items="${configDto.listOfExistingModules}" var="objList1">
															<option value="${objList1.selectedModuleId}">${objList1.featureName}</option>
														</c:forEach>
													</c:if>
												</form:select>
											</td>
											<td style="font-size: 12px;">
												<br><br>
												<a class="addfeature" style="cursor: pointer; border: 1px solid #000; padding: 5px; background-color: #fff ">
													<spring:message code="BzComposer.configuration.lefttorightbtn"/>
												</a>
												<br/><br/>
												<a class="removefeature" style="cursor: pointer;border: 1px solid #000; padding: 5px; background-color: #fff ">
													<spring:message code="BzComposer.configuration.righttoleftbtn"/>
												</a>
											</td>
											<td>
												<form:select path="selectedModules" id="selectedModules" style="width: 200px; height: 200px;font-size: 1em;" styleClass="featureName2" multiple="true">
													<c:if test="${not empty configDto.listOfExistingselectedModules}">
														<c:forEach items="${configDto.listOfExistingselectedModules}" var="objList1">
															<option value="${objList1.selectedModuleId}">${objList1.featureName}</option>
														</c:forEach>
													</c:if>
												</form:select>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<div id="orderTemplate" style="font-size: 12px;">
			   					<div id="content3" class="tabPage">
									<table class="table-notifications" width="100%">
										<tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
											<spring:message code="BzComposer.configuration.importantordertemplate" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.importantordertemplate.templatename" />
											</td>
											<td style="font-size: 12px;">
												<form:input path="templateName" />
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<spring:message code="BzComposer.configuration.choosefilebtn"/>
											</td>
											<td style="font-size: 12px;">
												<input type="file" name="exelFile" accept=".xls" />
											</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td colspan="2" style="font-size: 12px;">
												<spring:message code="Bzcomposer.configuration.choosefilebtn.onlyexcelfiles"/>
											</td>
										</tr>
										<tr>
											<td style="font-size: 12px;">
												<input type="checkbox" name="def" id="def" value="${configDto.def}" ${configDto.def=='on'?'checked':''} />
												<label><spring:message code="BzComposer.configuration.defaultchkbox" /></label>
											</td>
										</tr>
										<tr>
											<th colspan="2" align="left" style="font-size: 12px; padding: 5px;">
												<spring:message code="BzComposer.configuration.fieldsmapping" />
											</th>
										</tr>
										<tr>
											<td style="font-size: 12px;"><b><spring:message code="BzComposer.configuration.databasefieldsname"/> </b></td>
											<td style="font-size: 12px;"><b><spring:message code="BzComposer.configuration.mappingfieldsname"/></b></td>
										</tr>
										<tr>
											<td style="font-size: 12px;"><spring:message code= "BzComposer.configuration.itemcode"/>*</td>
											<td style="font-size: 12px;"><form:input path="itemCode" /></td>
										</tr>
										<tr>
											<td style="font-size: 12px;"><spring:message code = "BzComposer.configuration.itemname"/></td>
											<td style="font-size: 12px;"><form:input path="itemName" /></td>
										</tr>
										<tr>
											<td style="font-size: 12px;"><spring:message code="BzComposer.configuration.qty"/>*</td>
											<td style="font-size: 12px;"><form:input path="qty" /></td>
										</tr>
										<tr>
											<td style="font-size: 12px;"><spring:message code="BzComposer.configuration.unitprice"/>* ($)</td>
											<td style="font-size: 12px;"><form:input path="unitPrice" /></td>
										</tr>
										<tr>
											<td style="font-size: 12px;"><spring:message code="BzComposer.configuration.unitweight"/></td>
											<td style="font-size: 12px;"><form:input path="weight" /></td>
										</tr>
										<tr>
											<td style="font-size: 12px;"><spring:message code="BzComposer.configuration.taxable"/></td>
											<td style="font-size: 12px;"><form:input path="taxable" value="" /></td>
										</tr>
										<tr>
											<td colspan="2" align="center" style="font-size: 12px; padding: 5px;">
												<input type="button" name="New" value="<spring:message code='BzComposer.global.new'/>"/>&nbsp;&nbsp;
												<input type="button" name="Save" value="<spring:message code='BzComposer.global.save'/>"/>&nbsp;&nbsp;
												<input type="button" name="Delete" value="<spring:message code='BzComposer.global.delete'/>"/>
											</td>
										</tr>
									</table>
									<div id="orderTemplateList">
										<table class="table-notifications" width="80%">
											<tr>
												<th colspan="2" align="left" style="font-size: 12px;  padding: 5px;">
													<spring:message code="BzComposer.configuration.ordertemplatelist" />
												</th>
											</tr>
											<tr>
												<td></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<!--  Reminders Starts -->
							<div id="reminder">
								<div id="content4" class="tabPage">
									<jsp:include page="ReminderNew.jsp"/>
								</div>
							</div>
							<div id="emailSetup">
								<div id="tabs1" style="height:600px;">
  									<ul>
    									<li style="font-size: 12px;">
    										<a href="#smtpServerSettings">
    											<spring:message code="BzComposer.tab.smtpServerSettings"/>
    										</a>
										</li>
    									<li style="font-size: 12px;">
    										<a href="#emailTemplate">
    											<spring:message code="BzComposer.configuration.emailtemplate"/>
   											</a>
										</li>
									</ul>
									<div id="smtpServerSettings">
										<div id="content" class="tabPage">
											<%-- <jsp:include page="/public_html/configuration/smtpSetup.jsp"/> --%>
											<!-- SMTP Setup Starts -->
											<table class="table-notifications" width="100%">
												<tr>
													<td colspan="2" style="font-size: 12px;">
														<b><spring:message code="BzComposer.configuration.setuploginnote" /></b>
													</td>
												</tr>
												<tr>
													<th align="left" colspan="2" style="font-size: 12px; padding: 5px;">
														<spring:message code="BzComposer.configuration.serverinformation" />
													</th>
												</tr>
												<tr>
													<td style="font-size: 12px;">
														<spring:message code="BzComposer.configuration.senderemailaddress" />
													</td>
													<td style="font-size: 12px;">
														&nbsp;&nbsp;&nbsp;
														<form:input path="senderEmail" size="30" maxlength="45" />
													</td>
												</tr>
												<tr>
													<td style="font-size: 12px;">
														<spring:message code="BzComposer.configuration.smtpserver" />
													</td>
													<td style="font-size: 12px;">
														&nbsp;&nbsp;&nbsp;
														<form:input path="mailServer" size="30" maxlength="45" />
													</td>
												</tr>
												<tr>
													<td colspan="2" style="font-size: 12px;">
														<input type="button" name="testMailConnection" class="formButton" size="25" onclick="TestConnection()"
														value='<spring:message code="BzComposer.configuration.testconnectiontomailserverbtn" />' />
													</td>
												</tr>
												<tr>
													<td colspan="2" style="font-size: 12px;">
														<spring:message code="BzComposer.configuration.mailserverauthentication" />
													</td>
												</tr>
												<tr>
													<td colspan="2" style="font-size: 12px;">
														<input type="checkbox" name="mailAuth" id="mailAuth" onclick="EnableDisableFields2();" value='${configDto.mailAuth}' ${configDto.mailAuth=='on'?'checked':''} />
														<label><spring:message code="BzComposer.configuration.serverrequeiresauthentication" /></label>
													</td>
												</tr>
												<tr>
													<th align="left" colspan="2" style="font-size: 12px; padding: 5px;">
														<spring:message code="BzComposer.configuration.userinformation" />
													</th>
												</tr>
												<tr>
													<td style="font-size: 12px;">
														<spring:message code="BzComposer.configuration.username" />
													</td>
													<td style="font-size: 12px;">
														&nbsp;&nbsp;&nbsp;
														<form:input type="email" path="mailUserName" size="30" maxlength="45" />
													</td>
												</tr>
												<tr>
													<td style="font-size: 12px;">
														<spring:message code="BzComposer.configuration.password" />
													</td>
													<td style="font-size: 12px;">
														&nbsp;&nbsp;&nbsp;
														<form:input path="mailPassword" size="30" maxlength="35" />
													</td>
												</tr>
											</table>
											<!-- SMTP Setup Ends -->
										</div>
									</div>
									<div id="emailTemplate">
										<div id="content2" class="tabPage">
										<jsp:include page="emailSetupNew.jsp"/>
									</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- general page content ends -->
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
		<input type="hidden" name="salesOrderBoard" value=""/>
		<input type="hidden" name="itemReceivedBoard" value=""/>
		<input type="hidden" name="itemShippedBoard" value=""/>
		<input type="hidden" name="poboard" value=""/>
	</div>
	<div align="center" id="generalButtons" style="display: block;">
		<input type="submit" name="Save" id="Save" onclick="SaveValues()" style="font-size:14px;"
		value="<spring:message code='BzComposer.global.save'/>">
		<input type="reset" id="Cancel" name="Cancel" onclick="RevokeValues()" style="font-size:14px;"
		value="<spring:message code='BzComposer.global.cancel'/>">
	</div>
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
EnableDisableFields2();
function SaveValues()
{
	event.preventDefault();
	$("#showsaverecorddialog").dialog({
	    	resizable: false,
	        height: 200,
	        width: 500,
	        modal: true,
	        buttons: {
	        	"<spring:message code='BzComposer.global.ok'/>": function () {

	            	/* document.configurationForm.currencyID.value = parseInt(document.configurationForm.currencyID.value);
	        		var currencyValue = $.trim($("#currencyID option:selected").text());

	        		document.configurationForm.weightID.value = parseInt(document.configurationForm.weightID.value);
	        		var weightName =$.trim($("#weightID option:selected").text());

	        		document.configurationForm.defaultLabelID.value = document.configurationForm.defaultLabelID.value;
	        		var labelValue = $.trim($("#defaultLabelID option:selected").text());

	        		document.configurationForm.filterOption.value = document.configurationForm.filterOption.value;
	        		var filterOption = $.trim($("#filterOption option:selected").text());

	            	document.configurationForm.moduleID.value = document.configurationForm.moduleID.value;
	        		var moduleName = $.trim($("#moduleID option:selected").text());
	        		//Added by tulsi


	        		document.configurationForm.salesOrderBoard.value = document.configurationForm.salesOrderBoard.value;
	        		document.configurationForm.itemReceivedBoard.value = document.configurationForm.itemReceivedBoard.value;
	        		document.configurationForm.poboard.value = document.configurationForm.poboard.value;
	        		document.configurationForm.itemShippedBoard.value = document.configurationForm.itemShippedBoard.value; */

	        		/*first checkbox for showReminder*/
	        		//document.configurationForm.showReminder.value = $("#showReminder").val();

	        		/*Radio Button values- Either 0 or 1*/
	        		/* document.configurationForm.invoiceMemo.value = document.configurationForm.invoiceMemo.value;
	        		document.configurationForm.overdueInvoice.value = document.configurationForm.overdueInvoice.value;
	        		document.configurationForm.inventoryOrder.value = document.configurationForm.inventoryOrder.value;
	        		document.configurationForm.billsToPay.value = document.configurationForm.billsToPay.value;
	        		document.configurationForm.memorizeEstimation.value = document.configurationForm.memorizeEstimation.value;
	        		document.configurationForm.serviceBilling.value = document.configurationForm.serviceBilling.value;
	        		document.configurationForm.memorizeBill.value = document.configurationForm.memorizeBill.value;
	        		document.configurationForm.memorizePurchaseOrder.value = document.configurationForm.memorizePurchaseOrder.value; */

	        		/*All RadioButton Days value*/
	        		/* document.configurationForm.invoiceMemoDays.value = document.configurationForm.invoiceMemoDays.value;
	        		document.configurationForm.overdueInvoiceDays.value = document.configurationForm.overdueInvoiceDays.value;
	        		document.configurationForm.inventoryOrderDays.value = document.configurationForm.inventoryOrderDays.value;
	        		document.configurationForm.billsToPayDays.value = document.configurationForm.billsToPayDays.value;
	        		document.configurationForm.memorizeEstimationDays.value = document.configurationForm.memorizeEstimationDays.value;
	        		document.configurationForm.memorizePurchaseOrderDays.value = document.configurationForm.memorizePurchaseOrderDays.value;
	        		document.configurationForm.serviceBillingDays.value = document.configurationForm.serviceBillingDays.value;
	        		document.configurationForm.memorizeBillDays.value = document.configurationForm.memorizeBillDays.value;
	        		document.getElementById('showReminderStatus').value = $("#showReminder").val();*/
	        		var currencyID=document.getElementById("currencyID").value;
	        		var weightID=document.getElementById("weightID").value;
	        		var defaultLabelID=document.getElementById("defaultLabelID").value;
	        		var filterOption=document.getElementById("filterOption").value;
	        		var moduleID=document.getElementById("moduleID").value;


	        		var salesOrderBoard = $("#salesOrderBoard").val();
	        		var itemReceivedBoard = $("#itemReceivedBoard").val();
	        		var poboard = $("#poboard").val();
	        		var itemShippedBoard = $("#itemShippedBoard").val();
	        		document.getElementById('salesOrderBoard').value = salesOrderBoard;
	        		document.getElementById('itemReceivedBoard').value = itemReceivedBoard;
	        		document.getElementById('itemShippedBoard').value = itemShippedBoard;
	        		document.getElementById('poboard').value = poboard;
	        		var x = document.getElementById("selectedModules");
	        	    var modules = "";
	        	    var i;
	        	    var moduleslist = [];
	        	    for (i = 0; i < x.length; i++)
	        	    {
	        	    	modules =x.options[i].text;
	        	    	moduleslist.push(modules);
	        	    }
	        	    /* document.getElementById('tabid').value="SaveConfigurationGeneral";
	        		document.forms[0].action = "Configuration";
	        		document.forms[0].submit(); */
	        		window.location.href= "${pageContext.request.contextPath}/Configuration?tabid=SaveConfigurationGeneral&salesOrderBoard="+salesOrderBoard+"&itemReceivedBoard="
	        				+itemReceivedBoard+"&itemShippedBoard="+itemShippedBoard+"&poboard="+poboard+"&currencyID="+currencyID+"&weightID="+weightID+
	        				"&defaultLabelID="+defaultLabelID+"&filterOption="+filterOption+"&moduleID="+moduleID+"&moduleslist="+moduleslist;
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
function SaveValuesFeatures()
{
	if(confirm('<spring:message code="BzComposer.configuration.saveconfirm"/>'))
	{
		event.preventDefault();
		$("#showsaverecorddialog").dialog({
		    	resizable: false,
		        height: 200,
		        width: 500,
		        modal: true,
		        buttons: {
		        	"<spring:message code='BzComposer.global.ok'/>": function () {
		            	document.configurationForm.selectedModules.value = document.configurationForm.selectedModules.value;
		        		document.configurationForm.selectedModuleId.value = document.configurationForm.selectedModuleId.value;

		        		var x = document.getElementById("selectedModules");
		        	    var txt = "";
		        	    var i;
		        	    for (i = 0; i < x.length; i++)
		        	    {
		        	        txt = txt + "\n" + x.options[i].text;
		        	    }
		        	    document.configurationForm.selectedModules.value = txt;

		        	    var x = document.getElementById("selectedModuleId");
		        	    var txt1 = "";
		        	    var i;
		        	    for (i = 0; i < x.length; i++)
		        	    {
		        	        txt1 = txt1 + "\n" + x.options[i].text;
		        	    }

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
}
function RevokeValues(){
	document.getElementById('tid').value="config";
	document.forms[0].action = "Configuration";
	document.forms[0].submit();
}

function SetLabelName(lblid){
	size = document.getElementById('lblsize').value;
	for(cnt=0;cnt<size;cnt++){
		lid = document.getElementById(cnt+'lid').value;
		if(lblid == lid){
			document.configurationForm.labelName.value =  document.getElementById(cnt+'lname').value;
			break;
		}
	}
}
</script>
</html>
<!-- Dialog box used in this page -->
<div id="showsaverecorddialog" style="display:none;">
	<p><spring:message code="BzComposer.configuration.saveconfirm"/></p>
</div>
<div id="serverConnectedErrorDialog" style="display:none;">
	<p><spring:message code="BzComposer.configuration.manageservicetype.serverconnectederror"/></p>
</div>
<div id="serverConnectedSeccessDialog" style="display:none;">
	<p><spring:message code="BzComposer.configuration.manageservicetype.serverconnectedsuccess"/></p>
</div>
<div id="pleaseWaitDialog" style="display:none;">
	<p><spring:message code="BzComposer.configuration.pleaseWait"/></p>
</div>