package com.bzpayroll.dashboard.sales.dao;


import com.bzpayroll.common.log.Loger;
import com.bzpayroll.dashboard.sales.forms.SalesBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class SalesOrderBoardDetails {

    @Autowired
    private SalesOrderBoardInfo SaleInfo;


    public void getSalesOrderBoardDetails(HttpServletRequest request, SalesBoardDto sform) {
        HttpSession sess = request.getSession();
        String compId = (String) sess.getAttribute("CID");
        // EmailInfo EmailInfo = new EmailInfo();

        ArrayList SaleOrderDetails = new ArrayList();
        // Loger.log("isEmailChk:"+isEmailChk);
        // if("on".equalsIgnoreCase(isEmailChk))
        // isEmailChk="1";
        // else
        // isEmailChk="0";

        String oDate1 = sform.getOrderDate1();
        String oDate2 = sform.getOrderDate2();
        String sDate1 = sform.getSaleDate1();
        String sDate2 = sform.getSaleDate2();

        String searchType = sform.getSearchType();
        String searchTxt = sform.getSearchTxt();
        String marketID = sform.getFilterMarket();
        String sOption1 = sform.getSortType1();
        String sOption2 = sform.getSortType2();

        Loger.log("oDate1:" + oDate1);
        Loger.log("oDate2:" + oDate2);
        Loger.log("SDate1:" + sDate1);
        Loger.log("SDate2:" + sDate2);

        Loger.log("SOption1:" + sOption1);
        Loger.log("SOption2:" + sOption2);
        Loger.log("searchType:" + searchType);
        Loger.log("searchTxt:" + searchTxt);
        Loger.log("sMarketId:" + marketID);

        SaleOrderDetails = SaleInfo.SalesRecordSearch(compId, oDate1, oDate2,
                sDate1, sDate2, marketID, sOption1, sOption2, searchType, searchTxt);

        request.setAttribute("SalesOrderBoardDetails", SaleOrderDetails);
        sform.setFilterMarket(marketID);
        request.setAttribute("Market", sform.getFilterMarket());
    }

    public void updateRecord(HttpServletRequest request) {
        SalesOrderBoardInfo salesInfo = new SalesOrderBoardInfo();
        boolean result = salesInfo.update(request);
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
