package com.bzpayroll.common.utility;

import java.text.DateFormatSymbols;

public class Utility {

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
