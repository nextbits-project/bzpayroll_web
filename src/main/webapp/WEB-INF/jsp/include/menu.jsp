<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div id="menubar2" style="width: 100%;display:none" class="header-section">
	<div class="bzclogo">
		<ul class="sf-menu" id="example"
			style="text-align: center; width: max-content;">
			<li>
				<a title="File" style="cursor: pointer;">
					<spring:message code="BzComposer.File" />
				</a>
				<ul>
					<li>
						<a href="/dashboard?tabid=Dashboard" style="cursor: pointer;"> <span>Dashboard</span></a>
					</li>
					<li>
						<a href="/dashboard/file?tabid=CompanyInfo" style="cursor: pointer;">
                  <span>
                     <spring:message code="BzComposer.Companyinformation" />
                  </span>
						</a>
					</li>
					<%-- <li>
						<a href="#" onclick="SetUpprintForms();">
                     <span>
                        <spring:message code="menu.file.SetUpprintForms" />
                     </span>
						</a>
					</li>  --%>
					<%--  <li>
						<a href="#" onclick="MultiPrintInvoice();" style="cursor: pointer;">
                     <span>
                        <spring:message code="menu.file.MultiPrintInvoice" />
                     </span>
						</a>
					</li>  --%>
					<%-- <li>
						<a href="/dashboard/SalesBord?tabid=ShowList" style="cursor: pointer;">
                  <span>
                     <spring:message code="menu.file.PrintInvoice" />
                  </span>
						</a>
					</li> --%>
					<li>
						<a href="#" style="cursor: pointer;">
                     <span>
                        <spring:message code="menu.file.Import" />
                     </span>
						</a>
						<ul>
							<%--<li><a href="<%= session.getAttribute("path")%>/file?tabid=ImportCustomer" style="cursor: pointer;">
							<spring:message code="BzComposer.customer.Customer" /></a></li> --%>
							<li>
								<a href="/dashboard/file?tabid=ImportCustomer" style="cursor: pointer;">
                        <span>
                           <spring:message code="BzComposer.customer.Customer" />
                        </span>
								</a>
							</li>
							<%-- <li><a href="#" onclick="customerImport()" style="cursor: pointer;">
							    <spring:message code="BzComposer.customer.Customer" />
                               </a></li> --%>
							<%-- <li><a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
							    <spring:message code="BzComposer.vendor.vendors" /></a></li> --%>
							<li>
								<a href="/dashboard/file?tabid=ImportVendor" style="cursor: pointer;">
                        <span>
                           <spring:message code="BzComposer.vendor.vendors" />
                        </span>
								</a>
							</li>
							<%-- <li><a href="#" onclick="vendorImport()" style="cursor: pointer;">
							<spring:message code="BzComposer.vendor.vendors" />
                               </a></li> --%>
							<%-- <li><a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
							    <spring:message code="NavigationTree.Items" /></a></li> --%>
							<li>
								<a href="/dashboard/Item?tabid=UploadItem" style="cursor: pointer;">
                        <span>
                           <spring:message code="NavigationTree.Items" />
                        </span>
								</a>
							</li>
							<%-- <li><a href="#" onclick="uploadItem()" style="cursor: pointer;">
							<spring:message code="NavigationTree.Items" />
                               </a></li> --%>
						</ul>
					</li>
					<li>
						<a href="#" style="cursor: pointer;">
                     <span>
                        <spring:message code="menu.file.ExportTo" />
                     </span>
						</a>
						<ul>
							<%-- <li><a href="/dashboard/file?tabid=CompanyInfo" style="cursor: pointer;">
							    <spring:message code="BzComposer.customer.Customer" /></a></li> --%>
							<li>
								<a href="#" onclick="exportCustomer()"
								   style="cursor: pointer;">
									<spring:message code="BzComposer.customer.Customer" />
								</a>
							</li>
							<%--<li><a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
							<spring:message code="BzComposer.vendor.vendors" /></a></li> --%>
							<li>
								<a href="#" onclick="exportVendor()"
								   style="cursor: pointer;">
									<spring:message code="BzComposer.vendor.vendors" />
								</a>
							</li>
							<%-- <li><a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
							<spring:message code="NavigationTree.Items" /></a></li> --%>
							<li>
								<a href="#" onclick="exportItem()"
								   style="cursor: pointer;">
									<spring:message code="NavigationTree.Items" />
								</a>
							</li>
						</ul>
					</li>
					<li>
						<%-- <a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
                           <span><spring:message code="menu.file.QBImport" /></span></a> --%>
					<li>
						<a href="#" onclick="quickBookImport()" style="cursor: pointer;">
							<spring:message code="menu.file.QBImport" />
						</a>
					</li>
					</li>
					<%-- <li>
                       <a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
                           <span><spring:message code="menu.file.Order_Import" /></span></a>
                       </li> --%>
					<%--    <li>
						<a href="#" onclick="orderImport()" style="cursor: pointer;">
							<spring:message code="menu.file.Order_Import" />
						</a>
					</li>   --%>
					</li>
					<li>
						<a href="#" style="cursor: pointer;">
                     <span>
                        <spring:message code="menu.file.Utilities" />
                     </span>
						</a>
						<ul>
							<li>
								<a href="#ex1" rel="modal:open">
                           <span>
                              <spring:message code="menu.file.Calculator" />
                           </span>
								</a>
							</li>
							<%-- <li>
								<a href="#" onClick="openCouponDesign()">
                           <span>
                              <spring:message code="menu.file.CouponDesign" />
                           </span>
								</a>
							</li>  --%>
						</ul>
					</li>
					<%-- <li>
                       <a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
                       <span><spring:message code="menu.file.Module_Upload" /></span></a>
                       </li> --%>
					<li>
						<a href="#" onclick="moduleImport()" style="cursor: pointer;">
							<spring:message code="menu.file.Module_Upload" />
						</a>
					</li>
					<li>
						<a href="#" style="cursor: pointer;">
                     <span>
                        <spring:message code="menu.file.Module_Download" />
                     </span>
						</a>
						<ul>
							<li>
								<a href="#" style="cursor: pointer;">
                           <span>
                              <spring:message code="menu.file.Module_Download_Shipping" />
                           </span>
								</a>
								<ul>
									<%--	<li>
                                       <a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
                                       <span>
                                           <spring:message code="menu.file.Module_Download_UPS" />
                                       </span>
                                       </a>
                                       </li>
                                       <li> --%>
									<li>
										<a href="http://www.nextbits.com?modulename=UPS" style="cursor: pointer;">
                                 <span>
                                    <spring:message code="menu.file.Module_Download_UPS" />
                                 </span>
										</a>
									</li>
									<%-- <a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
                                       <span>
                                           <spring:message code="menu.file.Module_Download_USPS" />
                                       </span>
                                       </a>
                                       </li> --%>
									<li>
										<a href="http://www.nextbits.com?modulename=USPS" style="cursor: pointer;">
                                 <span>
                                    <spring:message code="menu.file.Module_Download_USPS" />
                                 </span>
										</a>
									</li>
									<%-- <li>
                                       <a href="<%= session.getAttribute("path")%>/file?tabid=CompanyInfo" style="cursor: pointer;">
                                       <span>
                                           <spring:message code="menu.file.Module_Download_FEDEX" />
                                       </span>
                                       </a>
                                       </li> --%>
									<li>
										<a href="http://www.nextbits.com?modulename=FEDEX" style="cursor: pointer;">
                                 <span>
                                    <spring:message code="menu.file.Module_Download_FEDEX" />
                                 </span>
										</a>
									</li>
								</ul>
							</li>
						</ul>
					</li>
					<li>
						<a href="./Logout" style="cursor: pointer;">
                            <span><spring:message code="menu.file.Exit" /></span>
						</a>
					</li>
				</ul>
			</li>
			 
			 
			<li>
				<a href="/dashboard/employeelist" title="Employee">
					<spring:message code="BzComposer.EmployeeList" />
				</a>
			</li>
			
			<li>
				<a href="/dashboard/manageemployee?act=timesheet" title="Time Sheet">
							<spring:message code="BzComposer.Employee.TimeSheet" />
				 </a>
			</li>
			 
			<li>
				<a href="/dashboard/Payroll?tabid=Payroll" title="Create Payroll">
					<spring:message code="BzComposer.payroll.createPayroll" />
				</a>
			</li>
			<li>
				<a href="/dashboard/Payroll?tabid=payrollList" title="Payroll List">
					<spring:message code="BzComposer.payroll.payrollList" />
				</a>
			</li>	
			
<li>
				<a href="/dashboard/Reports?tabid=ReportsCenter"
				   title="Report Center">
               <span>
                  <spring:message code="BzComposer.Report.ReportTitle" />
               </span>
				</a>
				<ul>
					<li>
						<a
								href="/dashboard/Reports?tabid=ReportsCenter"
								title="Report Center">
                  <span>
                     <spring:message code="BzComposer.Report.ReportCenter" />
                  </span>
						</a>
					</li>

				</ul>
			</li>			
								
			<li>
				<a href="/dashboard/Configuration?tabid=config12&&tab=tr12" title="Confuguration">
					<spring:message code="BzComposer.Confuguration" />
				</a>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#menubar2").show();
	});
	var screenHeight = $(window).height()/2;
	var screenWidth = $(window).width()/2;
	var top = $(window).height() / 4;
	var left = $(window).width() / 4;
	function companyinfo()
	{
		//window.open("/dashboard/file?tabid=CompanyInfo",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no," );
	}
	function openCouponDesign()	//added on 08-01-2019
	{
		window.open("/dashboard/file?tabid=CouponDesign",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function showInvoiceList(action)
	{
		if(action == 'AllInvoice')
		{
			window.open("/dashboard/SalesBord?tabid=AllInvoiceList&ilist=1",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
		else if (action == 'PaidInvoice')
		{
			window.open("/dashboard/SalesBord?tabid=PaidInvoiceList&ilist=2",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
		else if (action == 'UnPaidInvoice')
		{
			window.open("/dashboard/SalesBord?tabid=UnPaidInvoiceList&ilist=3",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
	}
	function showRefundInvoiceReport()
	{
		window.open("/dashboard/SalesBord?tabid=refundInvoiceReport",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function showBudgetVsActual()
	{
		//window.open("SalesBord?tabid=refundInvoiceReport",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
		window.open("/dashboard/Customer?tabid=BudgetVsActual",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function showBudgetOverview()
	{
		window.open("/dashboard/Customer?tabid=BudgetOverview",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowIncomeCustomerDetail()
	{
		window.open("/dashboard/Customer?tabid=IncomeCustomerSummary",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowProfitLossByJob()
	{
		alert("Not yet supported.");
	}
	function showSalesReport(action)
	{
		if(action == 'SalesRBC')
		{
			window.open("/dashboard/SalesBord?tabid=SalesRBC&ilist=1",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
		}
		else if(action == 'SalesRID')
		{
			window.open("/dashboard/SalesBord?tabid=SalesRID&ilist=3",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
		else if(action == 'SalesRBI')
		{
			window.open("/dashboard/SalesBord?tabid=SalesRBI&ilist=2",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		}
	}
	function showEstimationList()
	{
		window.open("/dashboard/EstimationBoard?tabid=AllEstimationList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowvendorList()
	{
		window.open("/dashboard/PurchaseBoard?tabid=AllVendorList",null,"scrollbars=yes,height=500,width=1250,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowvendorPhoneList()
	{
		window.open("/dashboard/PurchaseBoard?tabid=VendorPhoneList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowVendorContactList()
	{
		window.open("/dashboard/PurchaseBoard?tabid=VendorContactList",null,"scrollbars=yes,height=500,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowsvendorBalanceDetails()
	{
		window.open("/dashboard/PurchaseBoard?tabid=VendorBalancedetails",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowsvendorBalanceSymmary()
	{
		window.open("/dashboard/PurchaseBoard?tabid=VendorBalancesymmary",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowsAllPurchaseorders()
	{
		window.open("/dashboard/PurchaseBoard?tabid=AllPurchaseOrderList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowAllPurchaseBills()
	{
		window.open("/dashboard/PurchaseBoard?tabid=AllPurchaseBillList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowPaidPurchaseBills()
	{
		window.open("/dashboard/PurchaseBoard?tabid=PaidPurchaseBillList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowUnPaidPurchaseBills()
	{
		window.open("PurchaseBoard?tabid=ShowUnPaidPurchaseBills",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showInventoryList()
	{
		window.open("/dashboard/Item?tabid=InventoryList",null,"scrollbars=yes,height=600,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowCancelledPurchaseRefBill()
	{
		window.open("/dashboard/PurchaseBoard?tabid=CancelledPurREfBill",null,"scrollbars=yes,height=600,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowVendor1099List()
	{
		window.open("/dashboard/PurchaseBoard?tabid=Vendor1099List",null,"scrollbars=yes,height=600,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}

	function Vendor1099TransactionSummary()
	{
		window.open("/dashboard/PurchaseBoard?tabid=vendor1099TransactionSummary",null,"scrollbars=yes,height=600,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function vendor1099TransactionDetail()
	{
		window.open("/dashboard/PurchaseBoard?tabid=vendor1099TransactionDetail",null,"scrollbars=yes,height=600,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showReservedInventoryList()
	{
		window.open("/dashboard/SalesOrderBoard?tabid=ReservedInventoryList",null,"scrollbars=yes,height=600,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowCustomerList()
	{
		window.open("/dashboard/Customer?tabid=CustomerList",null,"scrollbars=yes,height=600,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowCustomerPhoneList()
	{
		window.open("Customer?tabid=CustomerPhoneList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowCustomerContactList()
	{
		window.open("Customer?tabid=CustomerContactList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowTrancsactionbylistCustomer()
	{
		window.open("/dashboard/Customer?tabid=CustomerTransactionList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowCustomerBalSummary()
	{
		window.open("/dashboard/Customer?tabid=CustomerBalanceSummary",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowCustomerBalDetail()
	{
		window.open("/dashboard/Customer?tabid=CustomerBalDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowSalesByCustomerSummary()
	{
		window.open("/dashboard/Customer?tabid=SalesByCustomerSummary",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowIncomeCustomerSummary()
	{
		window.open("/dashboard/Customer?tabid=IncomeCustomerSummary",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function showItemPriceList()
	{
		window.open("/dashboard/Item?tabid=ItemPriceList",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showDiscontinuedInventoryList()
	{
		window.open("/dashboard/Item?tabid=DiscontinuedList",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showInvValSummary()
	{
		window.open("/dashboard/Item?tabid=InventoryValSummary",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showInvValDetail()
	{
		window.open("/dashboard/Item?tabid=InvValDetail",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showInvOrderReport()
	{
		window.open("/dashboard/Item?tabid=InvOrderReport",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showInvStatistic()
	{
		window.open("/dashboard/Item?tabid=InvStatistic",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function ShowSalesTaxSummary()
	{
		//window.open("/dashboard/Item?tabid=ShowSalesTaxSummary",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
		alert("Not Yet Supported");
	}
	function ShowReportTaxDetail()
	{
		//window.open("/dashboard/Item?tabid=ShowSalesTaxSummary",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
		alert("Not Yet Supported");
	}
	function showDamagedInventoryList()
	{
		window.open("/dashboard/Item?tabid=DamagedInvList",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showDamageInventoryList()
	{
		window.open("/dashboard/Item?tabid=showDamageInventoryList",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showUnknownInventoryList()
	{
		window.open("/dashboard/Item?tabid=showUnknownInventoryList",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showReturnedInventoryList()
	{
		window.open("/dashboard/Item?tabid=showReturnedInventoryList",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function showDailyItemSummary()
	{
		window.open("/dashboard/Item?tabid=showDailyItemSummary",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no");
	}
	function showDailySalesSummary()
	{
		window.open("/dashboard/Item?tabid=showDailySalesSummary",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no");
	}
	function MissingInventoryList()
	{
		window.open("Item?tabid=MissingInventoryList",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ReturnInventoryList()
	{
		window.open("/dashboard/Item?tabid=ReturnInventoryList",null,"scrollbars=yes,height=600,width=850,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowEmployeeSalesByRep()
	{
		window.open("/dashboard/SalesBord?tabid=SalesByRepDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowEmployeeSalesReportByRep()
	{
		window.open("/dashboard/SalesBord?tabid=SalesReportByRep",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function banking()
	{
		window.open("/dashboard/Accounting?tabid=Banking",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowCheckDetail()
	{
		window.open("/dashboard/BankingAccounting?tabid=CheckDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowDepositDetail()
	{
		window.open("/dashboard/BankingAccounting?tabid=DepositDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowBillDetail()
	{
		window.open("/dashboard/BankingAccounting?tabid=BillDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function TransactionDeatail()
	{
		//window.open("BankingAccounting?tabid=BillDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		alert("Not Yet Supprted");
	}
	function ShowAccountReceivableGraph()
	{
		window.open("/dashboard/BankingAccounting?tabid=ARGraph",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowAccountReceivable()
	{
		window.open("/dashboard/AccountReceivableAR?tabid=AccontReceivableReport",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowAccountPayable()
	{
		window.open("/dashboard/Customer?tabid=AccountPayable",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function AccountPayableGraph()
	{
		window.open("/dashboard/Customer?tabid=AccountPayableGraph",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowProfitLoss()
	{
		window.open("/dashboard/Category?tabid=ProfitLoss",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowProfitLossDetail()
	{
		window.open("/dashboard/Customer?tabid=ProfitLossDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function showProfitLossByItem()
	{
		window.open("/dashboard/Item?tabid=ProfitLossByItem",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function showIncomeStatement()
	{
		window.open("/dashboard/Category?tabid=IncomeStatement",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowBalSheet()
	{
		window.open("/dashboard/Accounting?tabid=BalanceSheet",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowCashFlow()
	{
		window.open("/dashboard/ShowCashFlow?tabid=CashFlowStatement",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShowCashFlowForeCast()
	{
		//window.open("ShowCashFlowForeCast?tabid=ShowCashFlowForeCast",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		alert("Not Yet Supprted");
	}
	function IncomeExpenseGraph()
	{
		//window.open("ShowCashFlowForeCast?tabid=ShowCashFlowForeCast",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		window.open("/dashboard/BankingAccounting?tabid=IncomeExpenseGraph",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
		//alert("Income Expence graph");
	}
	function Networth()
	{
		//window.open("ShowCashFlowForeCast?tabid=ShowCashFlowForeCast",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		window.open("/dashboard/BankingAccounting?tabid=NetworthGraph",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
		//alert("Income Expence graph");
	}
	function BudgetvsActualGraph()
	{
		//window.open("ShowCashFlowForeCast?tabid=ShowCashFlowForeCast",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
		window.open("/dashboard/BankingAccounting?tabid=BudgetvsActualGraph",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
		//alert("Income Expence graph");
	}
	function ChartsofCategories()
	{
		window.open("/dashboard/ReportCenterLists?tabid=ChartsofCategories",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function TermList()
	{
		window.open("/dashboard/ReportCenterLists?tabid=TermList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function SaleRepList()
	{
		window.open("/dashboard/ReportCenterLists?tabid=SaleRepList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function PaymentMethodList()
	{
		window.open("/dashboard/ReportCenterLists?tabid=PaymentMethodList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ShipViaList()
	{
		window.open("/dashboard/ReportCenterLists?tabid=ShipViaList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function TaxTypeList()
	{
		window.open("/dashboard/ReportCenterLists?tabid=TaxTypeList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function FootnoteList()
	{
		window.open("/dashboard/ReportCenterLists?tabid=FootnoteList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function MessageList()
	{
		window.open("/dashboard/ReportCenterLists?tabid=MessageList",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ESales_Invoice_Detail()
	{
		window.open("/dashboard/ReportCenterESales?tabid=ESalesInvoiceDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ESales_Refund_Detail()
	{
		window.open("/dashboard/ReportCenterESales?tabid=ESalesRefundDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ESales_sale_Detail()
	{
		window.open("/dashboard/ReportCenterESales?tabid=ESalessaleDetail",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ESales_Inventory_Sale_Statistics()
	{
		window.open("/dashboard/ReportCenterESales?tabid=ESalesInventorySaleStatistics",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function Cross_Sell_Inventory_Report()
	{
		window.open("/dashboard/ReportCenterESales?tabid=CrossSellInventoryReport",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function ESale_Sales_Graph()
	{
		window.open("/dashboard/ReportCenterESales?tabid=ESaleSalesGraph",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	/*file menu*/
	function SetUpprintForms()
	{
		window.open("/dashboard/file?tabid=SetUpprintForms",null,"scrollbars=no,height=300,width=800,status=yes,toolbar=no,menubar=no,location=center");
	}
	function MultiPrintInvoice()
	{
		window.open("/dashboard/file?tabid=MultiPrintInvoice",null,"scrollbars=no,height=300,width=800,status=yes,toolbar=no,menubar=no,location=no");
	}
	function customerImport()
	{
		window.location("/dashboard/file?tabid=ImportCustomer",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	function vendorImport()
	{
		window.open("/dashboard/file?tabid=ImportVendor",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	function uploadItem()
	{
		window.open("/dashboard/Item?tabid=UploadItem",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	function exportCustomer()
	{
		window.open("/dashboard/file?tabid=ExportCustomer",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	function exportVendor()
	{
		window.open("/dashboard/file?tabid=ExportVendor",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	function exportItem()
	{
		window.open("/dashboard/Item?tabid=ExportItem",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	function quickBookImport()
	{
		window.open("/dashboard/file?tabid=QuickBookImport",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	function orderImport()
	{
		window.open("/dashboard/file?tabid=OrderImport",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	function moduleImport()
	{
		window.open("/dashboard/Module?tabid=ImportModule",null,"scrollbars=no,height="+screenHeight+",width ="+screenWidth+",left = "+left+",top = "+top+",status=yes,toolbar=no,menubar=no,location=no");
	}
	/*esales board*/
	/* function eSalesSalesBoard()
    {
        window.open("/dashboard/eSalesBoard?tabid=eSalesSalesBoard",null,"scrollbars=yes,height=500,width=800,status=yes,toolbar=no,menubar=no,location=no" );
    } */

	/**/
</script>
<!-- Remember to include jQuery :) -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script> -->
<!-- jQuery Modal -->
<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet"
	  href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
<style type="text/css">
	/* * {
    padding: 0;
    margin: 5px;
    text-align: center;
    } */
	.modal {
		display: none; /* Hidden by default */
	}
	body {
		background-color: white;
		overflow: auto;
	}
	.calculator {
		width: 350px;
		height: 320px;
		box-shadow: 0px 0px 0px 10px #666;
		border: 1px solid;
		border-radius: 2px;
		text-align: center
	}
	#display {
		width: 320px;
		height: 40px;
		text-align: right;
		border: 1px solid black;
		font-size: 20px;
		left: 2px;
		top: 2px;
		color: black;
	}
	.btnTop {
		color: white;
		background-color: black;
		font-size: 14px;
		margin: auto;
		width: 50px;
		height: 25px;
	}
	.btnNum {
		color: black;
		font-size: 20px;
		margin: auto;
		width: 50px;
		height: 25px;
	}
	.btnMath {
		color: black;
		font-size: 20px;
		margin: auto;
		width: 50px;
		height: 25px;
	}
	.btnOpps {
		color: black;
		font-size: 20px;
		margin: auto;
		width: 50px;
		height: 25px;
	}
	.modal1 {
		overflow: visible;
		height: auto;
		vertical-align: top;
	}
</style>
<div id="ex1" class="modal modal1">
	<form name="sci-calc">
		<center>
			<table class="calculator" cellspacing="0" cellpadding="1">
				<tr>
					<td colspan="5"><input id="display" name="display" value="0"
										   size="28" maxlength="25"></td>
				</tr>
				<tr>
					<td><input type="button" class="btnTop" name="btnTop"
							   value="C" onclick="this.form.display.value=  0 "></td>
					<td><input type="button" class="btnTop" name="btnTop"
							   value="<--" onclick="deleteChar(this.form.display)"></td>
					<td><input type="button" class="btnTop" name="btnTop"
							   value="="
							   onclick="if(checkNum(this.form.display.value)) { compute(this.form) }"></td>
					<td><input type="button" class="btnOpps" name="btnOpps"
							   value="&#960;"
							   onclick="addChar(this.form.display,'3.14159265359')"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="%" onclick=" percent(this.form.display)"></td>
				</tr>
				<tr>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="7" onclick="addChar(this.form.display, '7')"></td>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="8" onclick="addChar(this.form.display, '8')"></td>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="9" onclick="addChar(this.form.display, '9')"></td>
					<td><input type="button" class="btnOpps" name="btnOpps"
							   value="x&#94;"
							   onclick="if(checkNum(this.form.display.value)) { exp(this.form) }"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="/" onclick="addChar(this.form.display, '/')"></td>
				<tr>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="4" onclick="addChar(this.form.display, '4')"></td>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="5" onclick="addChar(this.form.display, '5')"></td>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="6" onclick="addChar(this.form.display, '6')"></td>
					<td><input type="button" class="btnOpps" name="btnOpps"
							   value="ln"
							   onclick="if(checkNum(this.form.display.value)) { ln(this.form) }"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="*" onclick="addChar(this.form.display, '*')"></td>
				</tr>
				<tr>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="1" onclick="addChar(this.form.display, '1')"></td>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="2" onclick="addChar(this.form.display, '2')"></td>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="3" onclick="addChar(this.form.display, '3')"></td>
					<td><input type="button" class="btnOpps" name="btnOpps"
							   value="&radic;"
							   onclick="if(checkNum(this.form.display.value)) { sqrt(this.form) }"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="-" onclick="addChar(this.form.display, '-')"></td>
				</tr>
				<tr>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="&#177" onclick="changeSign(this.form.display)"></td>
					<td><input type="button" class="btnNum" name="btnNum"
							   value="0" onclick="addChar(this.form.display, '0')"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="&#46;" onclick="addChar(this.form.display, '&#46;')"></td>
					<td><input type="button" class="btnOpps" name="btnOpps"
							   value="x&#50;"
							   onclick="if(checkNum(this.form.display.value)) { square(this.form) }"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="+" onclick="addChar(this.form.display, '+')"></td>
				</tr>
				<tr>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="(" onclick="addChar(this.form.display, '(')"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value=")" onclick="addChar(this.form.display,')')"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="cos"
							   onclick="if(checkNum(this.form.display.value)) { cos(this.form) }"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="sin"
							   onclick="if(checkNum(this.form.display.value)) { sin(this.form) }"></td>
					<td><input type="button" class="btnMath" name="btnMath"
							   value="tan"
							   onclick="if(checkNum(this.form.display.value)) { tan(this.form) }"></td>
				</tr>
			</table>
	</form>
	<br> <a href="#" rel="modal:close"><spring:message code="BzComposer.global.close" /></a>
	</center>
</div>
<script type="text/javascript">
	function ShowCustomerList()
	{
		window.open("Customer?tabid=CustomerList",null,"scrollbars=yes,height=600,width=1000,status=yes,toolbar=no,menubar=no,location=no" );
	}
	function addChar(input, character)
	{
		if(input.value == null || input.value == "0")
			input.value = character
		else
			input.value += character
	}
	function cos(form)
	{
		form.display.value = Math.cos(form.display.value);
	}
	function sin(form)
	{
		form.display.value = Math.sin(form.display.value);
	}
	function tan(form)
	{
		form.display.value = Math.tan(form.display.value);
	}
	function sqrt(form)
	{
		form.display.value = Math.sqrt(form.display.value);
	}
	function ln(form)
	{
		form.display.value = Math.log(form.display.value);
	}
	function exp(form)
	{
		form.display.value = Math.exp(form.display.value);
	}
	function deleteChar(input)
	{
		input.value = input.value.substring(0, input.value.length - 1)
	}
	var val = 0.0;
	function percent(input)
	{
		val = input.value;
		input.value = input.value + "%";
	}
	function changeSign(input)
	{
		if(input.value.substring(0, 1) == "-")
			input.value = input.value.substring(1, input.value.length)
		else
			input.value = "-" + input.value
	}
	function compute(form)
	{
		//if (val !== 0.0) {
		// var percent = form.display.value;
		// percent = pcent.substring(percent.indexOf("%")+1);
		// form.display.value = parseFloat(percent)/100 * val;
		//val = 0.0;
		// } else
		form.display.value = eval(form.display.value);
	}
	function square(form)
	{
		form.display.value = eval(form.display.value) * eval(form.display.value)
	}
	function checkNum(str)
	{
		for (var i = 0; i < str.length; i++)
		{
			var ch = str.charAt(i);
			if (ch < "0" || ch > "9")
			{
				if (ch != "/" && ch != "*" && ch != "+" && ch != "-" && ch != "." && ch != "(" && ch!= ")" && ch != "%")
				{
					alert("invalid entry!")
					return false
				}
			}
		}
		return true
	}
</script>