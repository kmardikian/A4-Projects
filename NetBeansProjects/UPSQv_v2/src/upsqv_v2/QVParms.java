/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upsqv_v2;

import java.beans.Transient;
import java.io.Serializable;

/**
 *
 * @author khatchik
 */
public class QVParms  implements Serializable{
  //  private String qvUrl;
    private String qvLicense;
    private String qvUserId;
    private String qvPassWord;
    private String as400SystemName;
    private String as400UserId;
    private String as400Password;
    private String as400Lib; 
    private String upsAutPath;
    private String upsShprNo;
    private String upsSecCd;
    private String upsClientId;
    private String upsServer;
    private String qvPath;
    private String environment;
    private String logLocation;
    private String as400SecLib;
    
    static final long serialVersionUID = -3126998878902358585L;
     
    private transient String startDt;
    private transient String endDt;

    /**
     * @return the qvUrl
     */
//    public String getQvUrl() {
//        return qvUrl;
//    }
//
//    /**
//     * @param qvUrl the qvUrl to set
//     */
//    public void setQvUrl(String qvUrl) {
//        this.qvUrl = qvUrl;
//    }

    /**
     * @return the qvLicense
     */
    public String getQvLicense() {
        return qvLicense;
    }

    /**
     * @param qvLicense the qvLicense to set
     */
    public void setQvLicense(String qvLicense) {
        this.qvLicense = qvLicense;
    }

    /**
     * @return the qvUserId
     */
    public String getQvUserId() {
        return qvUserId;
    }

    /**
     * @param qvUserId the qvUserId to set
     */
    public void setQvUserId(String qvUserId) {
        this.qvUserId = qvUserId;
    }

    /**
     * @return the qvPassWord
     */
    public String getQvPassWord() {
        return qvPassWord;
    }

    /**
     * @param qvPassWord the qvPassWord to set
     */
    public void setQvPassWord(String qvPassWord) {
        this.qvPassWord = qvPassWord;
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

    /**
     * @return the startDt
     */
    public String getStartDt() {
        return startDt;
    }

    /**
     * @param startDt the startDt to set
     */
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    /**
     * @return the endDt
     */
    public String getEndDt() {
        return endDt;
    }

    /**
     * @param endDt the endDt to set
     */
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getUpsAutPath() {
        return upsAutPath;
    }

    public void setUpsAutPath(String upsAutUrl) {
        this.upsAutPath = upsAutUrl;
    }

    public String getUpsShprNo() {
        return upsShprNo;
    }

    public void setUpsShprNo(String upsShprNo) {
        this.upsShprNo = upsShprNo;
    }

    public String getUpsSecCd() {
        return upsSecCd;
    }

    public void setUpsSecCd(String upsSecCd) {
        this.upsSecCd = upsSecCd;
    }

    public String getUpsClientId() {
        return upsClientId;
    }

    public void setUpsClientId(String upsClientId) {
        this.upsClientId = upsClientId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getUpsServer() {
        return upsServer;
    }

    public void setUpsServer(String server) {
        this.upsServer = server;
    }

    public String getQvPath() {
        return qvPath;
    }

    public void setQvPath(String path) {
        this.qvPath = path;
    }

    public String getLogLocation() {
        return logLocation;
    }

    public void setLogLocation(String logLocation) {
        this.logLocation = logLocation;
    }

    public String getAs400SecLib() {
        return as400SecLib;
    }

    public void setAs400SecLib(String as400SecLib) {
        this.as400SecLib = as400SecLib;
    }
    
    
       
    
}
