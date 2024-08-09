/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.Transient;
import java.io.Serializable;

/**
 *
 * @author khatchik
 */
public class UPSParms  implements Serializable{
    private String as400SystemName;
    private String as400UserId;
    private String as400Password;
    private String as400Lib; 
    private String upsServer;
    private String upsAutPath;
    private String upsRatePath; 
    private String upsShprNo;
    private String upsClientId;
    private String upsSecCd;
    private String logLocation; 
    private String environment;
    private boolean debug;
   
    static final long serialVersionUID = -3126998878902358585L;

   
    public String getAs400SystemName() {
        return as400SystemName;
    }

    public void setAs400SystemName(String as400SystemName) {
        this.as400SystemName = as400SystemName;
    }

    public String getAs400UserId() {
        return as400UserId;
    }

    public void setAs400UserId(String as400UserId) {
        this.as400UserId = as400UserId;
    }

    public String getAs400Password() {
        return as400Password;
    }

    public void setAs400Password(String as400Password) {
        this.as400Password = as400Password;
    }

    public String getAs400Lib() {
        return as400Lib;
    }

    public void setAs400Lib(String as400Lib) {
        this.as400Lib = as400Lib;
    }

//    public String getUpsAutUrl() {
//        return upsAutUrl;
//    }
//
//    public void setUpsAutUrl(String upsAutUrl) {
//        this.upsAutUrl = upsAutUrl;
//    }

    public String getUpsShprNo() {
        return upsShprNo;
    }

    public void setUpsShprNo(String upsShprNo) {
        this.upsShprNo = upsShprNo;
    }
    

//    public String getUpsSecCd() {
//        return upsSecCd;
//    }
//
//    public void setUpsSecCd(String upsSecCd) {
//        this.upsSecCd = upsSecCd;
//    }

    public String getUpsServer() {
        return upsServer;
    }

    public void setUpsServer(String upsServer) {
        this.upsServer = upsServer;
    }

    public String getUpsAutPath() {
        return upsAutPath;
    }

    public void setUpsAutPath(String upsAutPath) {
        this.upsAutPath = upsAutPath;
    }

    public String getUpsRatePath() {
        return upsRatePath;
    }

    public void setUpsRatePath(String upsRatePath) {
        this.upsRatePath = upsRatePath;
    }

    public String getUpsClientId() {
        return upsClientId;
    }

    public void setUpsClientId(String upsClientId) {
        this.upsClientId = upsClientId;
    }

    public String getUpsSecCd() {
        return upsSecCd;
    }

    public void setUpsSecCd(String upsSecCd) {
        this.upsSecCd = upsSecCd;
    }

    public String getLogLocation() {
        return logLocation;
    }

    public void setLogLocation(String logLocation) {
        this.logLocation = logLocation;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    
}
