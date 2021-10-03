package com.bzpayroll.dashboard.sales.dao;


import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.sales.forms.EstimationBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class EstimationBoardDetails {


    @Autowired
    private SQLExecutor db;

    @Autowired
    private EstimationBoardInfo EstimationInfo;


    public void getEstimationBoardDetails(HttpServletRequest request, EstimationBoardDto eform) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");
        // EmailInfo EmailInfo = new EmailInfo();

        ArrayList EstimationDetails = new ArrayList();
        // Loger.log("isEmailChk:"+isEmailChk);
        // if("on".equalsIgnoreCase(isEmailChk))
        // isEmailChk="1";
        // else
        // isEmailChk="0";

        String oDate1 = eform.getOrderDate1();
        String oDate2 = eform.getOrderDate2();
        String sDate1 = eform.getSaleDate1();
        String sDate2 = eform.getSaleDate2();

        String searchType = eform.getSearchType();
        String searchTxt = eform.getSearchTxt();
        String marketID = eform.getFilterMarket();
        String sOption1 = eform.getSortType1();
        String sOption2 = eform.getSortType2();

        Loger.log("oDate1:" + oDate1);
        Loger.log("oDate2:" + oDate2);
        Loger.log("SDate1:" + sDate1);
        Loger.log("SDate2:" + sDate2);

        Loger.log("SOption1:" + sOption1);
        Loger.log("SOption2:" + sOption2);
        Loger.log("searchType:" + searchType);
        Loger.log("searchTxt:" + searchTxt);
        Loger.log("sMarketId:" + marketID);


        EstimationDetails = EstimationInfo.EstimationRecordSearch(compId, oDate1, oDate2,
                sDate1, sDate2, marketID, sOption1, sOption2, searchType, searchTxt);

        request.setAttribute("EstimationBoardDetails", EstimationDetails);
        eform.setFilterMarket(marketID);
        request.setAttribute("Market", eform.getFilterMarket());
    }

    public void updateRecord(HttpServletRequest request) {
        //	SalesBoardInfo salesInfo = new SalesBoardInfo();
        EstimationBoardInfo EstimationInfo = new EstimationBoardInfo();
        boolean result = EstimationInfo.update(request);
        String msg = "";
        if (result) {
            msg = "**Update is sucessfully completed";
            Loger.log("Updated " + msg);
        } else {
            msg = "**Record is not updated";
        }
        request.setAttribute("IsUpdated", msg);
    }
}
