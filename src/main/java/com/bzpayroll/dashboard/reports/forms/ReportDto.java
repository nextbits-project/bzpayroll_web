package com.bzpayroll.dashboard.reports.forms;

public class ReportDto {

    private static final long serialVersionUID = 1L;
    private String fromDate, month, year;
    private String toDate;
    private String sortBy;
    private String datesCombo;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDatesCombo() {
        return datesCombo;
    }

    public void setDatesCombo(String datesCombo) {
        this.datesCombo = datesCombo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


}
