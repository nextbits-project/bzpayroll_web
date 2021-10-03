package com.bzpayroll.dashboard.file.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bzpayroll.common.TblStore;
import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.common.utility.Path;
import com.bzpayroll.dashboard.company.dao.AddNewCompanyDAO;
import com.bzpayroll.dashboard.file.dao.CompanyDetails;
import com.bzpayroll.dashboard.file.dao.CompanyInfo;
import com.bzpayroll.dashboard.file.dao.FileMenuDao;
import com.bzpayroll.dashboard.file.forms.CompanyInfoDto;
import com.bzpayroll.dashboard.login.forms.LoginFormDto;
import com.bzpayroll.dashboard.login.forms.MultiUserFormDto;
import com.bzpayroll.dashboard.sales.forms.InvoiceDto;
import com.bzpayroll.dashboard.sales.forms.ItemDto;
import com.bzpayroll.login.dao.LoginDAO;
import com.bzpayroll.login.dao.LoginDAOImpl;
import com.bzpayroll.sales.forms.CustomerDto;


/**
 * @author sarfrazmalik
 */
@Controller
public class FileController {
	
	@Autowired
	private LoginDAO loginDAO;
	
	@Autowired
	private  AddNewCompanyDAO dao;

	@Autowired
	private  CompanyInfo customer;

	@Autowired
	private  CompanyDetails cdetails;
	
	@Autowired
	private  CountryState countryState;

    @Autowired
    private  FileMenuDao fileMenuDao;


    @GetMapping("/dashboard/changeLocale")
    public String changeLocale(HttpServletRequest request) throws Exception {
        String url = "redirect:/Configuration?tabid=config";;
        request.getSession().setAttribute("currentLocale", request.getParameter("lang"));
        if(request.getSession().getAttribute("prevLocalePath") != null){
            Path p = (Path)request.getSession().getAttribute("prevLocalePath");
            if(p.getPathvalue().equals("File")){
                url = "redirect:/dasboard/file?tabid=CompanyInfo";
            }
            else if(p.getPathvalue().equals("HomePage")){
                url = "redirect:/";
            }
        }
        return url;
    }

    
    @RequestMapping(value = {"/dashboard", "/dashboard/file","/dashboard/File"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String execute1(CompanyInfoDto companyInfoDto, HttpServletRequest request, Model model) throws IOException, ServletException, ParseException {
        model.addAttribute("companyInfoDto", companyInfoDto);
        String forward = "/include/dashboard";
        String action = request.getParameter("tabid");

        if(action.equalsIgnoreCase("AdminDashboard")){
            ArrayList<LoginFormDto> list = LoginDAOImpl.getAllCompany(request);
            request.setAttribute("cList", list);
            forward = "/admin/admindashboard";
        }
        else if(action.equalsIgnoreCase("ComapanyLists")){
            ArrayList<LoginFormDto> list = LoginDAOImpl.getAllCompany(request);
            request.setAttribute("cList", list);
            forward = "success1";
        }
        else if(action.equalsIgnoreCase("ComapanyContacts")){
            ArrayList<CustomerDto> Customerlist = LoginDAOImpl.getAllCustomerlist(request);
            request.setAttribute("CustomerList", Customerlist);
            forward = "success2";
        }
        else if(action.equalsIgnoreCase("ApplicationUser")){
            ArrayList<MultiUserFormDto> Userlist = loginDAO.getAllUserlist(request);
            request.setAttribute("Userlist", Userlist);
            forward = "success3";
        }
        else if(action.equalsIgnoreCase("Income")){
            forward = "success4";
        }
        else if(action.equalsIgnoreCase("Visitor")){
            forward = "success5";
        }
        else if(action.equalsIgnoreCase("Dashboard")){
            HttpSession sess = request.getSession();
            String compId = (String) sess.getAttribute("CID");
            ArrayList<InvoiceDto> purchaseDetails = customer.selectPurchaseOrders(compId);
            ArrayList<InvoiceDto> salesOrderDetails = customer.selectSalesOrders(compId);
            ArrayList<InvoiceDto> invoiceDetails = customer.selectInvoiceDetails(compId);
            ArrayList<InvoiceDto> estimateDetails = customer.selectEstimateDetails(compId);
            ArrayList<ItemDto> itemListDetails = customer.getItemListDetails(compId);

            request.setAttribute("purchaseDetails", purchaseDetails);
            request.setAttribute("salesOrderDetails", salesOrderDetails);
            request.setAttribute("invoiceDetails", invoiceDetails);
            request.setAttribute("estimateDetails", estimateDetails);
            request.setAttribute("itemListDetails", itemListDetails);
            System.out.println("estimateDetails size:"+estimateDetails.size());
			forward = "/include/dashboard";
        }
        else if(action.equalsIgnoreCase("CompanyInfo")){
            HttpSession sess = request.getSession();
            String compId = (String) sess.getAttribute("CID");
            int userID=(Integer) sess.getAttribute("userID");
            

            dao.getBusinessType(compId,request,companyInfoDto);
            ArrayList<CompanyInfoDto> companydetails = new ArrayList<CompanyInfoDto>();
            

            int businessTypeId = companyInfoDto.getBusinessTypeId();
            request.setAttribute("businessTypeId1", businessTypeId);
            companydetails = customer.SearchCompany(compId, userID, companyInfoDto, request);

            System.out.println("City:"+companyInfoDto.getCity());

            request.setAttribute("cList", countryState.getCountryNew());
            request.setAttribute("sList", countryState.getStatesNew(companyInfoDto.getState()));
            request.setAttribute("city", companyInfoDto.getCity());
            int stateId = countryState.getStatesId(companyInfoDto.getState());
            request.setAttribute("state", stateId);
     		/*String country = cs.getCountryName(cust.getCity());
     		cust.setCountry(country);*/
            System.out.println("stateId:"+stateId);
            forward = "/include/updateCompanyinfo";

            Path p = new Path();
            p.setPathvalue("File");
            request.getSession().setAttribute("prevLocalePath", p);
        }
		/*else if(action.equalsIgnoreCase("states")){
			HttpSession sess = request.getSession();
			String state = request.getParameter("stateName");
			System.out.println("Enter state name:"+state);
			CountryState cs = new CountryState();
			request.setAttribute("cList", cs.getCountryNew());
			request.setAttribute("sList", cs.getStatesNew(state));
			//request.setAttribute("cList", cs.getCountry());

			//CompanyDetailsDao cdetails = new CompanyDetailsDao();
       		//cdetails.getAllList(request);

			forward = "Success";
		}*/

        else if(action.equalsIgnoreCase("zipcode")){
            HttpSession sess = request.getSession();
            String zipcode = request.getParameter("zipcode");
            System.out.println("Entered zipcode:"+zipcode);
             
            String[] data =  countryState.getCityState(zipcode);
            System.out.println(data[0]+" "+data[1]);

            companyInfoDto.setCity(data[0]);
            companyInfoDto.setStateName(data[1]);
            companyInfoDto.setZip(zipcode);

            int stateId = countryState.getStatesId(companyInfoDto.getStateName());
            System.out.println("stateId:"+stateId);
            //request.setAttribute("state", stateId);
            request.setAttribute("state", stateId);
            request.setAttribute("cList", countryState.getCountryNew());
            request.setAttribute("sList", countryState.getStatesNew(zipcode));
            //request.setAttribute("sList", cs.getStatesNew(zipcode));
            forward = "/include/updateCompanyinfo";
        }

        else if(action.equalsIgnoreCase("CompanyInformation"))
        {
            HttpSession sess = request.getSession();
            String compId = (String) sess.getAttribute("CID");
            int userID=(Integer) sess.getAttribute("userID");
            
            ArrayList<CompanyInfoDto> Comanydetails = new ArrayList<CompanyInfoDto>();
            
            Comanydetails=customer.SearchCompany(compId,userID,companyInfoDto,request);
            cdetails.getAllList(request);
            forward = "Success1";
        }

        /*else if (action.equalsIgnoreCase("edit")) {

			HttpSession sess = request.getSession();
			String compId = (String) sess.getAttribute("CID");
			int userID=(Integer) sess.getAttribute("userID");
        	CompanyInfoDao customer = new CompanyInfoDao();
       		//ArrayList<CompanyInfoDto> Comanydetails = new ArrayList<CompanyInfoDto>();

       		//Comanydetails=customer.SearchCompany(compId,userID, form,request);
       		String city = companyInfoDto.getCity();
       		System.out.println("City entered:"+city);
       		//Comanydetails=customer.SearchCompany(compId,userID,companyInfoDto,request);
       		CompanyDetailsDao cdetails = new CompanyDetailsDao();
       		customer.updateComapanyinfo(companyInfoDto,userID,compId);
  			//cdetails.updateConpanydetails(request, form);
       		//cdetails.updateConpanydetails(request, companyInfoDto);
			cdetails.getAllList(request);
			System.out.println("City:"+companyInfoDto.getCity());
     		request.setAttribute("city", companyInfoDto.getCity());
			forward = "success3";
		}*/

        else if (action.equalsIgnoreCase("SetUpprintForms")) {
            HttpSession sess = request.getSession();
            String compId = (String) sess.getAttribute("CID");
            int userID=(Integer) sess.getAttribute("userID");
            
            forward = "/file/setupprintForm";
        }
        else if (action.equalsIgnoreCase("MultiPrintInvoice")) {
            HttpSession sess = request.getSession();
            String compId = (String) sess.getAttribute("CID");
            int userID=(Integer) sess.getAttribute("userID");
             boolean result = fileMenuDao.validation(request, companyInfoDto);
            forward = "/file/multi-printInvoice";
        }
        else if(action.equalsIgnoreCase("CouponDesign")) {
            forward = "/file/couponDesign";
        }
        else if(action.equalsIgnoreCase("ImportCustomer")) {
            forward = "/file/customerImport";
        }
        else if(action.equalsIgnoreCase("ImportVendor")) {
            forward = "/file/vendorImport";
        }
        else if(action.equalsIgnoreCase("ExportCustomer")) {
            String type = request.getParameter("type");
            if( type != null && (type.equalsIgnoreCase("csv") || type.equalsIgnoreCase("xls"))) {
                 boolean b = fileMenuDao.exportCustomerList(request, type);
                if(b==true) {
                    if(type.equals("csv")) {
//						request.setAttribute("success", "BzComposer.exportcustomer.customerlistincsvdownloaded");
                        request.setAttribute("success", "Customerlist.csv file downloaded successfully at /Downloads");
                    }
                    else {
//						request.setAttribute("success", "BzComposer.exportcustomer.customerlistinxlsdownloaded");
                        request.setAttribute("success", "Customerlist.xls file downloaded successfully at /Downloads");
                    }
                }
            }
            forward = "/file/exportCustomer";
        }
        else if(action.equalsIgnoreCase("ExportVendor")) {
            String type = request.getParameter("type");
            if( type != null && (type.equalsIgnoreCase("csv") || type.equalsIgnoreCase("xls"))) {
                 boolean b = fileMenuDao.exportVendorList(request, type);
                if(b==true) {
                    if(type.equals("csv")) {
                        request.setAttribute("success", "BzComposer.exportvendor.vendorlistincsvdownloaded");
                        /*request.setAttribute("success", "Vendorlist.csv file downloaded successfully at /Downloads");*/
                    }
                    else {
                        request.setAttribute("success", "BzComposer.exportvendor.vendorlistinxlsdownloaded");
                        /*request.setAttribute("success", "Vendorlist.xls file downloaded successfully at /Downloads");*/
                    }
                }
            }
            forward = "/file/exportVendor";
        }
        else if(action.equalsIgnoreCase("QuickBookImport")) {
            quickBookImportTest(companyInfoDto, request);
            forward = "/file/quickBookImport";
        }
        else if(action.equalsIgnoreCase("OrderImport")) {
            fileMenuDao.getcmbLoadTemplate(request);
            forward = "/file/orderImport";
        }
        return forward;
    }

    @ResponseBody
    @PostMapping("/dashboard/updateEditedCompanyinfo")
    public String updateCompanyInfo(CompanyInfoDto companyInfoDto, HttpServletRequest request, Model model) {
        String status = "Success";
        String action = request.getParameter("tabid");
        System.out.println("--------------updateEditedCompanyinfo--------------" + request.getMethod());
        System.out.println("tabid: " + action);
        if (action.equalsIgnoreCase("edit")) {
            HttpSession sess = request.getSession();
            String compId = (String) sess.getAttribute("CID");
            int userID=(Integer) sess.getAttribute("userID");

            String company = request.getParameter("companyName");
            String nickName = request.getParameter("nickName");
            String fName = request.getParameter("fName");
            String lName = request.getParameter("lName");
            String add1 = request.getParameter("add1");
            String add2 = request.getParameter("add2");
            String province = request.getParameter("province");
            String cellphone = request.getParameter("cellphone");
            String phone = request.getParameter("phone");
            String fax = request.getParameter("fax");
            String email = request.getParameter("email");
            String stateName = request.getParameter("stateName");
            //String password = request.getParameter("password");
            String selectbusinessTypeId1 = request.getParameter("Typeid");
            int selectbusinessTypeId = Integer. valueOf(selectbusinessTypeId1);
            companyInfoDto.setCompanyName(company);
            companyInfoDto.setNickName(nickName);
            companyInfoDto.setFirstName(fName);
            companyInfoDto.setLastName(lName);
            companyInfoDto.setAddress1(add1);
            companyInfoDto.setAddress2(add2);
            companyInfoDto.setProvince(province);
            companyInfoDto.setCellPhone(cellphone);
            companyInfoDto.setPhone(phone);
            companyInfoDto.setFax(fax);
            companyInfoDto.setEmail(email);
            companyInfoDto.setiState(stateName);
            //companyInfoDto.setPassword(password);
            companyInfoDto.setBusinessTypeId(selectbusinessTypeId);
            
            System.out.println("Company Name:"+companyInfoDto.getCompanyName()+
                    "\nCity entered:"+companyInfoDto.getCity()+
                    "\nState:"+companyInfoDto.getState()+
                    "\nCountry:"+companyInfoDto.getCountry()+
                    "\nPhone:"+companyInfoDto.getPhone());
            customer.updateComapanyinfo(companyInfoDto,userID,compId);
            cdetails.getAllList(request);
             
            System.out.println("City:"+companyInfoDto.getCity());
            request.setAttribute("cList", countryState.getCountryNew());
            request.setAttribute("city", companyInfoDto.getCity());
            request.setAttribute("state",countryState.getStatesId(companyInfoDto.getState()));
            status = "Success";
        }
        return status;
    }

    @PostMapping("/dashboard/FileUpload")
    public String FileUpload(CompanyInfoDto companyInfoDto, HttpServletRequest request, HttpServletResponse response,
            @RequestParam("attachFile") MultipartFile attachFile) throws IOException, ServletException, ParseException {
        String forward = "/include/dashboard";
        String action = request.getParameter("tabid");

        System.out.println("--------------FileController-------FileUpload-------" + request.getMethod());
        System.out.println("tabid: " + action);
        if(action.equalsIgnoreCase("UploadCustomerFile")) {
             String type = "customer";
            if(!attachFile.isEmpty()) {
                boolean b = fileMenuDao.uploadCustomerFile(attachFile, request, type);
                System.out.println("file upload---------:" + b);
                if (b == true) {
                    request.setAttribute("successMessage", "success");
                }
            }
            forward = "redirect:File?tabid=ImportCustomer";
        }
        else if(action.equalsIgnoreCase("UploadVendorFile")) {
             String type = "vendor";
            if(!attachFile.isEmpty()) {
                boolean b = fileMenuDao.uploadVendorFile(attachFile, request, type);
                System.out.println("file upload---------:" + b);
                if (b == true) {
                    request.setAttribute("successMessage1", "success");
                }
            }
            forward = "redirect:File?tabid=ImportVendor";
        }
        else if(action.equalsIgnoreCase("QuickBookImport")) {
            companyInfoDto.setQuickBookFile(attachFile);
            quickBookImportTest(companyInfoDto, request);
            forward = "redirect:File?tabid=QuickBookImport";
        }
        return forward;
    }

    private void quickBookImportTest(CompanyInfoDto companyInfoDto, HttpServletRequest request){
        String type = request.getParameter("type");
        if(type != null) {
            fileMenuDao.quickBookImport(companyInfoDto.getQuickBookFile(),request);
        }
        ArrayList<TblStore> store = fileMenuDao.getQuickBookStores(fileMenuDao.getQBStoreTypeID());
        if(store == null || store.size() == 0) {
            store.add(new TblStore());
            request.setAttribute("LastImportDate", "<Not Available>");
            request.setAttribute("cmbImportFrom", store);
        } else{
            request.setAttribute("LastImportDate", "<Not Available>");
            request.setAttribute("cmbImportFrom", store);
        }
    }
}
