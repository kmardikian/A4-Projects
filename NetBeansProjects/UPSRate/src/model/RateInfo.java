/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Khatchik
 */
public class RateInfo {

    private String httpStatus = "";
    private String trnChg = "";    // transportation charges 
    private String trnCurr = "";
    private String svcOptChg = "";  // service option charges
    private String svcOptCurr;
    private String totChg = "";     // total Charges 
    private String totCurr = "";
    private String basChg = "";
    private String basCurr = "";
    private String message = "";

    public RateInfo() {
        //this.httpStatus = "";
        //this.totChg = "";
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getTotchg() {
        return totChg;
    }

    public void setTotRate(String totChg) {
        this.totChg = totChg;
    }

    public String getTrnChg() {
        return trnChg;
    }

    public void setTrnChg(String trnChg) {
        this.trnChg = trnChg;
    }

    public String getTrnCurr() {
        return trnCurr;
    }

    public void setTrnCurr(String trnCurr) {
        this.trnCurr = trnCurr;
    }

    public String getSvcOptChg() {
        return svcOptChg;
    }

    public void setSvcOptChg(String svcOptChg) {
        this.svcOptChg = svcOptChg;
    }

    public String getSvcOptCurr() {
        return svcOptCurr;
    }

    public void setSvcOptCurr(String svcoptCurr) {
        this.svcOptCurr = svcoptCurr;
    }

    public String getTotChg() {
        return totChg;
    }

    public void setTotChg(String totChg) {
        this.totChg = totChg;
    }

    public String getTotCurr() {
        return totCurr;
    }

    public void setTotCurr(String totCurr) {
        this.totCurr = totCurr;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBasChg() {
        return basChg;
    }

    public void setBasChg(String basChg) {
        this.basChg = basChg;
    }

    public String getBasCurr() {
        return basCurr;
    }

    public void setBasCurr(String basCurr) {
        this.basCurr = basCurr;
    }

    @Override
    public String toString() {
        return String.format("%-3s %-10s %-3s %-10s %-3s %-10s %-3s %-10s %-3s %-100s ",
                 this.httpStatus.trim(),
                 this.trnChg.trim(),
                 this.trnCurr.trim(),
                 this.svcOptChg.trim(),
                 this.svcOptCurr.trim(),
                 this.totChg.trim(),
                 this.totCurr.trim(),
                 this.basChg.trim(),
                 this.basCurr.trim(),
                 this.message.trim()
        );
        // return this.httpStatus + " " + this.totalChg;
    }

}
