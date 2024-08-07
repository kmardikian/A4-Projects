/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;

/**
 *
 * @author Khatchik
 */
public class FedexResponse {
    
    private String trkNo;
    private String trkTyp;
    private BigDecimal tpxChrg;     // transportation charges.
    private BigDecimal svcChrg;     // service charges
    private BigDecimal totChrg;     // total charges 
    private BigDecimal ngsChrg;     // negotiated charges
    private BigDecimal blgWgt;      // billing weight
   
    //private byte[] lblImage;        // label image
    //private String lblImage;
    private String lblLoc;
    private String pdfLoc;
    private String message;
    private String msgSeverity;
    private int responceStatus;
    private boolean residential;
    private String addrValAlertCode;
    private String addrValAlertMsg;
    private String addrValAlertType;
    private String addrValClassification;
    
    public String getTrkNo() {
        return trkNo;
    }

    public void setTrkNo(String trkNo) {
        this.trkNo = trkNo;
    }

    public String getTrkTyp() {
        return trkTyp;
    }

    public void setTrkTyp(String trkTyp) {
        this.trkTyp = trkTyp;
    }

    public BigDecimal getTpxChrg() {
        return tpxChrg;
    }

    public void setTpxChrg(BigDecimal tpxChrg) {
        this.tpxChrg = tpxChrg;
    }

    public BigDecimal getSvcChrg() {
        return svcChrg;
    }

    public void setSvcChrg(BigDecimal svcChrg) {
        this.svcChrg = svcChrg;
    }

    public BigDecimal getTotChrg() {
        return totChrg;
    }

    public void setTotChrg(BigDecimal totChrg) {
        this.totChrg = totChrg;
    }

    public BigDecimal getNgsChrg() {
        return ngsChrg;
    }

    public void setNgsChrg(BigDecimal ngsChrg) {
        this.ngsChrg = ngsChrg;
    }

    public BigDecimal getBlgWgt() {
        return blgWgt;
    }

    public void setBlgWgt(BigDecimal blgWgt) {
        this.blgWgt = blgWgt;
    }

       public String getLblLoc() {
        return lblLoc;
    }

    public void setLblLoc(String lblLoc) {
        this.lblLoc = lblLoc;
    }

    public String getPdfLoc() {
        return pdfLoc;
    }

    public void setPdfLoc(String pdfLoc) {
        this.pdfLoc = pdfLoc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgSeverity() {
        return msgSeverity;
    }

    public void setMsgSeverity(String msgSeverity) {
        this.msgSeverity = msgSeverity;
    }

    public boolean isResidential() {
        return residential;
    }

    public void setResidential(boolean residential) {
        this.residential = residential;
    }

    public String getAddrValAlertCode() {
        return addrValAlertCode;
    }

    public void setAddrValAlertCode(String addrValAlertCode) {
        this.addrValAlertCode = addrValAlertCode;
    }

    public String getAddrValAlertMsg() {
        return addrValAlertMsg;
    }

    public void setAddrValAlertMsg(String addrValAlertMsg) {
        this.addrValAlertMsg = addrValAlertMsg;
    }

    public String getAddrValAlertType() {
        return addrValAlertType;
    }

    public void setAddrValAlertType(String addrValAlertType) {
        this.addrValAlertType = addrValAlertType;
    }

    public void setAddrValClassification(String addrValClassification) {
        this.addrValClassification = addrValClassification;
        
        if (addrValClassification.equals("RESIDENTIAL")) {
            setResidential(true);
        } else {
            setResidential(false);
        }
    }

    public int getResponceStatus() {
        return responceStatus;
    }

    public void setResponceStatus(int respondStatus) {
        this.responceStatus = respondStatus;
    }

    
}
    

