/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPS;

/**
 *
 * @author Khatchik
 */
public class LblRspStat {
     
        private String errStat;
        private String errCode;
        private String errMsg;
        private String lblLoc;
        private String severity;
        
        public LblRspStat() {
            errStat = "";
            errCode = "";
            errMsg = " ";
            lblLoc = " ";
            severity=" ";
        }

    public String getErrStat() {
        return errStat;
    }

    public void setErrStat(String errStat) {
        this.errStat = errStat;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getLblLoc() {
        return lblLoc;
    }

    public void setLblLoc(String lblLoc) {
        this.lblLoc = lblLoc;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
        
        

    }

    
