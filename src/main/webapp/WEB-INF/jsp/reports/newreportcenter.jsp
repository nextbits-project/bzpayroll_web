<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/WEB-INF/jsp/include/sessionExpired.jsp"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<%@include file="/WEB-INF/jsp/include/headlogo.jsp"%>
<%@include file="/WEB-INF/jsp/include/header.jsp"%>
<%@include file="/WEB-INF/jsp/include/menu.jsp"%>
<title><spring:message code="BzComposer.reportcentertitle" /></title>

<style type="text/css">
.selected{
background-color: rgba(5, 169, 197, 0.63);
color: white ;
padding: 4px;
cursor: pointer;
}
.nonselected{
color: #666;
padding: 4px;
cursor: pointer;
}
#reports a{
text-decoration: none !important;
color: #05A9C5 !important;
cursor: pointer;

}
#reports a:HOVER{
text-decoration:underline !important;
color: #666 !important;
cursor: pointer;
}
#reports {
width:200px;
}

div#pie { /* 	color:#05A9C5;; */
	padding: 10px 0px 20px 0px;
}

table.tabla-listados {
	width: 100%;
	border: 1px solid rgb(207, 207, 207);
	margin: 20px 0px 20px 0px;
}

table.tabla-listados thead tr th {
	font-size: .7em;
	text-align: left;
	padding: 5px 10px;
	/* 	background: rgba(5, 169, 197, 0.11); */
	border-bottom: 1px solid rgba(5, 169, 197, 0.2);
	/* 	color: #333; */
	text-shadow: #999 0px 1px 1px;
	white-space: nowrap;
}

table.tabla-listados tbody tr td {
	font-size: .8em;
	/* 	color: #666; */
	padding: 5px 0px 5px 14px;
	/* 	border-bottom: 1px solid rgb(207, 207, 207); */
	background: #fff;
	vertical-align: top;
}
</style>
</head>
<body onload="init();">
<!-- begin shared/header -->
<form action="Configuration.do?" enctype="MULTIPART/FORM-DATA" method="post">

<div id="ddcolortabsline">&nbsp;</div>

<div id="cos">

<div class="statusquo ok">

<div id="hoja">
<div id="blanquito">
<div id="padding">

<div>
	<span style="font-size: 1.1em; font-weight: normal; color: #838383; margin: 30px 0px 15px 0px; 
	border-bottom: 1px dotted #333; padding: 0 0 .3em 0;">
		<spring:message code="BzComposer.reportcenter.reportcentertitle"/>
	</span>
</div>
		<div>
		<br/>
			<div id="table-negotiations">
				<table cellspacing="0"  style="width: 100%;" class="section-border">
					<tr height="450">
						<!-- left side start -->
						<td valign="top"  style="width:1%;border-right: 1px solid rgb(207, 207, 207);">
							<div id="table-negotiations" style="width:170;">
								<table class="tabla-listados" cellspacing="0" style="margin: 0px 0px 0px 0px;">

			        				<tr>
										<td style="padding-left: 1px;">
											<div id="tr4" class="leftreportmenu selected"><spring:message code="BzComposer.reportcenter.employee" /></div>
                                        </td>
									</tr>
                                    <tr>
										<td style="padding-left: 1px;">
											<div id="tr5" class="leftreportmenu unselected"><spring:message code="BzComposer.reportcenter.payroll" /></div>
                                        </td>
									</tr>

					             </table>
					</div>
			</td>
	
	        <!-- right side start -->
	
			<td valign="top" style="padding-top: 2%;padding-right: 4%;padding-left: 2%;">
			<!-- General(sales) panel -->
             <div id="employeedetails" class="rightreportmenu">

                <a><span><spring:message code="BzComposer.reportcenter.employeelist" /></span></a> <br/>

             		 	<table class="tabla-listados" cellspacing="0">
                         <tr>
             					<td width="45">
             					<img src="/images/invoice.png" />
             					</td>
             					<td id="reports"><a onclick="showEmployeeReport('Hired');" ><spring:message code="BzComposer.reportcenter.employee.hired" /></a></td>
             					<td><spring:message code="BzComposer.reportcenter.sales.salesreportbycustomer.description" /></td>
             			</tr>
             			<tr>
             					<td width="45">
             					<img src="/images/invoice.png" />
             					</td>
             					<td id="reports"><a onclick="showEmployeeReport('Terminated');"><spring:message code="BzComposer.reportcenter.employee.terminated" /></a></td>
             					<td><spring:message code="BzComposer.reportcenter.sales.salesbyitem.description" /></td>
             			</tr>
             			 </table>


			 </div>

			 <div id="payrolldetails" class="rightreportmenu" style="display:none">

                             <a><span><spring:message code="BzComposer.reportcenter.payroll" /></span></a> <br/>

                          		 	<table class="tabla-listados" cellspacing="0">
                                      <tr>
                          					<td width="45">
                          					<img src="/images/invoice.png" />
                          					</td>
                          					<td id="reports"><a onclick="showEmployeeReport('Weekly');" ><spring:message code="BzComposer.reportcenter.payroll.weekly" /></a></td>
                          					<td><spring:message code="BzComposer.reportcenter.sales.salesreportbycustomer.description" /></td>
                          			</tr>
                          			<tr>
                          					<td width="45">
                          					<img src="/images/invoice.png" />
                          					</td>
                          					<td id="reports"><a onclick="showEmployeeReport('Monthly');"><spring:message code="BzComposer.reportcenter.payroll.monthly" /></a></td>
                          					<td><spring:message code="BzComposer.reportcenter.sales.salesbyitem.description" /></td>
                          			</tr>
                                    <tr>
                          					<td width="45">
                          					<img src="/images/invoice.png" />
                          					</td>
                          					<td id="reports"><a onclick="showEmployeeReport('Quarterly');"><spring:message code="BzComposer.reportcenter.payroll.quarterly" /></a></td>
                          					<td><spring:message code="BzComposer.reportcenter.sales.salesbyitem.description" /></td>
                          			</tr>
                                    <tr>
                          					<td width="45">
                          					<img src="/images/invoice.png" />
                          					</td>
                          					<td id="reports"><a onclick="showEmployeeReport('Yearly');"><spring:message code="BzComposer.reportcenter.payroll.yearly" /></a></td>
                          					<td><spring:message code="BzComposer.reportcenter.sales.salesbyitem.description" /></td>
                          			</tr>
                          			 </table>


             			 </div>
			</td>
		</tr>
	</table>
	</div>
	</div>
	</div></div></div></div></div>
</form>
<%@ include file="/WEB-INF/jsp/include/footer.jsp"%>

</body>
</html>

<script type="text/javascript">


    $(document).ready(function(){


        // leftreportmenu
        $( ".leftreportmenu" ).click(function() {

            $(".leftreportmenu").removeClass('selected');
            $(".leftreportmenu").removeClass('unselected');
            $(".leftreportmenu").addClass('unselected');
            $(this).addClass('selected');


            $(".rightreportmenu").hide();



            if('tr4' == this.id){
                $("#employeedetails").show();

            }else if('tr5' == this.id){
                $("#payrolldetails").show();
            }



        });

    });


	function SetRow(rid){
 		setTableVisible(rid);
	}	
	function setTableVisible(rid){

	}
	
    function init()
    {
         SetRow('tr0');
    }


    function showEmployeeReport(action)
	{
		if(action == 'Hired')
		{
			window.open("/dashboard/Reports?tabid="+action,null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
		else if(action == 'Terminated')
		{
			window.open("/dashboard/Reports?tabid="+action,null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
		else if(action == 'Monthly')
		{
			window.open("/dashboard/Reports?tabid="+action,null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
		else if(action == 'Weekly')
		{
			window.open("/dashboard/Reports?tabid="+action,null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
		else if(action == 'Quarterly')
		{
			window.open("/dashboard/Reports?tabid="+action,null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
		else if(action == 'Yearly')
		{
			window.open("/dashboard/Reports?tabid="+action,null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}

	}
</script>