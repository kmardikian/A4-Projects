/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author khatchik
 */
public class UpsShprParms implements Serializable {

    private String shUrl;
    private String voidUrl;
    private String shLicense;
    private String shUserId;
    private String shPassWord;
    private String fxUrl;
    private String fxKey;
    private String fxPassWord;
    private String fxAcctNo;
    private String fxMeterNo;
    private String fxLblLoc;
    private String as400SystemName;
    private String as400UserId;
    private String as400Password;
    private String as400Lib;
    private String upsLblLoc;
    private String cmpLogoLoc;
    private String jasperLoc;
    private String upsExpLib;
    private String pdfLoc;
    private String printerName;
    private String printerNameRptr;   // printer for re-printing
    private Integer ptktRtnDays;         // Pick ticket retention days.
    private transient String whse;
    static final long serialVersionUID = -3126998878902358585L;

    /**
     * @return the shUrl
     */
    public String getShUrl() {
        return shUrl;
    }

    /**
     * @param shUrl the shUrl to set
     */
    public void setShUrl(String shUrl) {
        this.shUrl = shUrl;
    }

    public String getVoidUrl() {
        return voidUrl;
    }

    public void setVoidUrl(String voidUrl) {
        this.voidUrl = voidUrl;
    }

    /**
     * @return the shLicense
     */
    public String getShLicense() {
        return shLicense;
    }

    /**
     * @param shLicense the shLicense to set
     */
    public void setShLicense(String shLicense) {
        this.shLicense = shLicense;
    }

    /**
     * @return the shUserId
     */
    public String getShUserId() {
        return shUserId;
    }

    /**
     * @param shUserId the shUserId to set
     */
    public void setShUserId(String shUserId) {
        this.shUserId = shUserId;
    }

    /**
     * @return the shPassWord
     */
    public String getShPassWord() {
        return shPassWord;
    }

    /**
     * @param shPassWord the shPassWord to set
     */
    public void setShPassWord(String shPassWord) {
        this.shPassWord = shPassWord;
    }

    public String getFxUrl() {
        if (fxUrl == null) {
            return "";
        }
            
        return fxUrl;
    }

    public void setFxUrl(String fxUrl) {
        this.fxUrl = fxUrl;
    }

    public String getFxKey() {
        if (fxKey == null) {
            return "";
        }
        return fxKey;
    }

    public void setFxKey(String fxKey) {
        this.fxKey = fxKey;
    }

    public String getFxPassWord() {
        if (fxPassWord == null) {
            return "";
        }
        return fxPassWord;
    }

    public void setFxPassWord(String fxPassWord) {
        
        this.fxPassWord = fxPassWord;
    }
    

    public String getFxAcctNo() {
        if (fxPassWord == null) {
            return "";
        }
        return fxAcctNo;
    }

    public void setFxAcctNo(String fxAcctNo) {
        this.fxAcctNo = fxAcctNo;
    }

    public String getFxMeterNo() {
        if (fxMeterNo == null) {
            return "";
        }
        return fxMeterNo;
    }

    public void setFxMeterNo(String fxMeterNo) {
        this.fxMeterNo = fxMeterNo;
    }

    public String getFxLblLoc() {
        if (fxLblLoc == null) {
            return "";
        }
        return fxLblLoc;
    }

    public void setFxLblLoc(String fxLblLoc) {
        this.fxLblLoc = fxLblLoc;
    }
    
    

    /**
     * @return the as400SystemName
     */
    public String getAs400SystemName() {
        return as400SystemName;
    }

    /**
     * @param as400SystemName the as400SystemName to set
     */
    public void setAs400SystemName(String as400SystemName) {
        this.as400SystemName = as400SystemName;
    }

    /**
     * @return the as400UserId
     */
    public String getAs400UserId() {
        return as400UserId;
    }

    /**
     * @param as400UserId the as400UserId to set
     */
    public void setAs400UserId(String as400UserId) {
        this.as400UserId = as400UserId;
    }

    /**
     * @return the as400Password
     */
    public String getAs400Password() {
        return as400Password;
    }

    /**
     * @param as400Password the as400Password to set
     */
    public void setAs400Password(String as400Password) {
        this.as400Password = as400Password;
    }

    /**
     * @return the as400Lib
     */
    public String getAs400Lib() {
        return as400Lib;
    }

    /**
     * @param as400Lib the as400Lib to set
     */
    public void setAs400Lib(String as400Lib) {
        this.as400Lib = as400Lib;
    }

    public String getUpsLblLoc() {
        return upsLblLoc;
    }

    public void setUpsLblLoc(String upsLblLoc) {
        if (upsLblLoc.endsWith("\\")) {
            this.upsLblLoc = upsLblLoc;
        } else {
            this.upsLblLoc = upsLblLoc + "\\";
        }
    }

    public String getCmpLogoLoc() {
        return cmpLogoLoc;
    }

    public void setCmpLogoLoc(String cmpLogoLoc) {
        if (cmpLogoLoc.endsWith("\\")) {
            this.cmpLogoLoc = cmpLogoLoc;
        } else {
            this.cmpLogoLoc = cmpLogoLoc + "\\";
        }
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterNameRptr() {
        if (this.printerNameRptr == null ||
                this.printerNameRptr.trim().isEmpty()) {
            return this.printerName;
        } else {
            return printerNameRptr;
        }
    }

    /**
     * Set printer to re-print pick tickets
     *
     * @param printerNameRptr Printer to re-print pick tickets
     */
    public void setPrinterNameRptr(String printerNameRptr) {
        this.printerNameRptr = printerNameRptr;
    }

    /**
     * get Printer returns original pick ticket printer or re-print printer
     * based on reprint boolean value
     *
     * @param reprint true returns re-print printer false returns original
     * printer
     */
    public String getPrinter(boolean reprint) {
        if (reprint) {
            return getPrinterNameRptr();
        } else {
            return getPrinterName();
        }

    }

    public String getJasperLoc() {
        return jasperLoc;
    }

    public void setJasperLoc(String jasperLoc) {
        if (jasperLoc.endsWith("\\")) {
            this.jasperLoc = jasperLoc;
        } else {
            this.jasperLoc = jasperLoc + "\\";
        }
    }

    public String getUpsExpLib() {
        return upsExpLib;
    }

    public void setUpsExpLib(String upsExpLib) {
        this.upsExpLib = upsExpLib;
    }

    public String getWhse() {
        return whse;
    }

    public void setWhse(String whse) {
        this.whse = whse;
    }

    public String getPdfLoc() {
        return pdfLoc;
    }

    public void setPdfLoc(String pdfLoc) {
        if (!pdfLoc.endsWith("\\")) {
            this.pdfLoc = pdfLoc + "\\";
        } else {
            this.pdfLoc = pdfLoc;
        }
    }

    public Integer getPtktRtnDays() {
        if  (this.ptktRtnDays == null) {
            return 0;
        }
        return ptktRtnDays;
    }

    public void setPtktRtnDays(Integer ptktRtnDays) {
        this.ptktRtnDays = ptktRtnDays;
    }
    

}
