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
public class FXParms  implements Serializable{
//    private String as400SystemName;
//    private String as400UserId;
//    private String as400Password;
//    private String as400Lib; 
    private String fxServer;
    private String fxAutPath;
    private String fxRatePath; 
    private String addrValPath; 
    private String fxShprNo;
    private String fxClientId;
    private String fxSecCd;
    private String logLocation; 
    private String environment;
    private boolean debug;
   
    static final long serialVersionUID = -3126998878902358585L;

    public String getFxServer() {
        return fxServer;
    }

    public void setFxServer(String fxServer) {
        this.fxServer = fxServer;
    }

    public String getFxAutPath() {
        return fxAutPath;
    }

    public void setFxAutPath(String fxAutPath) {
        this.fxAutPath = fxAutPath;
    }

    public String getFxRatePath() {
        return fxRatePath;
    }

    public void setFxRatePath(String fxRatePath) {
        this.fxRatePath = fxRatePath;
    }

    public String getAddrValPath() {
        return addrValPath;
    }

    public void setAddrValPath(String addrValPath) {
        this.addrValPath = addrValPath;
    }
    

    public String getFxShprNo() {
        return fxShprNo;
    }

    public void setFxShprNo(String fxShprNo) {
        this.fxShprNo = fxShprNo;
    }

    public String getFxClientId() {
        return fxClientId;
    }

    public void setFxClientId(String fxClientId) {
        this.fxClientId = fxClientId;
    }

    public String getFxSecCd() {
        return fxSecCd;
    }

    public void setFxSecCd(String fxSecCd) {
        this.fxSecCd = fxSecCd;
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
