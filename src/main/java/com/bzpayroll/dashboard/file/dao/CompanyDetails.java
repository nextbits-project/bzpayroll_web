package com.bzpayroll.dashboard.file.dao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.utility.CountryState;
import com.bzpayroll.dashboard.file.forms.CompanyInfoDto;

@Service
public class CompanyDetails {

	@Autowired
	private CompanyInfo comapanyDao;
	
    public void updateConpanydetails(HttpServletRequest request, CompanyInfoDto companyInfoDto) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");
        int userID=(Integer) sess.getAttribute("userID");
       
        comapanyDao.updateComapanyinfo(companyInfoDto,userID,compId);
        //	comapany.updateInsertComapany(compfrm, compId);
    }

    public void getAllList(HttpServletRequest request) {
        HttpSession sess = request.getSession();
        // country List
        CountryState cs = new CountryState();
        request.setAttribute("cList", cs.getCountry());
    }
}
