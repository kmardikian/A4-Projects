/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexshipping;

import java.io.Serializable;

/**
 *
 * @author Khatchik
 */
public class FxAppParms implements Serializable {
    static final long serialVersionUID = -3126998878902358585L;
    //private String key;
    //private String passWord;
    private String accountNumber;
    //private String meterNumber;
   // private String endPoint;
    private String clientId; 
    private String clientSecret;
    //private String authServer;
    private String authPath;
    private String server;
    private String shipPath;
    private String addrValPath; 
    private String cxlShpPath;
    private String fedexLblLoc;
    private boolean debug;

        
    
//    public String getEndPoint() {
//        return endPoint;
//    }
//
//    public void setEndPoint(String endPoint) {
//        this.endPoint = endPoint;
//    }

    public String getFedexLblLoc() {
        return fedexLblLoc;
    }

    public void setFedexLblLoc(String fedexLblLoc) {
        this.fedexLblLoc = fedexLblLoc;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

//    public String getAuthServer() {
//        return authServer;
//    }

//    public void setAuthServer(String server) {
//        this.authServer = server;
//    }

    public String getAuthPath() {
        return authPath;
    }

    public void setAuthPath(String authPath) {
        this.authPath = authPath;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String shipServer) {
        this.server = shipServer;
    }

    public String getShipPath() {
        return shipPath;
    }

    public void setShipPath(String shipPath) {
        this.shipPath = shipPath;
    }
    

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    

//    public String getAuthpath() {
//        return authPath;
//    }
//
//    public void setAuthpath(String authpath) {
//        this.authPath = authpath;
//    }

    public String getAddrValPath() {
        return addrValPath;
    }

    public void setAddrValPath(String addrValPath) {
        this.addrValPath = addrValPath;
    }

    public String getCxlShpPath() {
        return cxlShpPath;
    }

    public void setCxlShpPath(String cxlShpPath) {
        this.cxlShpPath = cxlShpPath;
    }
    
    
    
                    
}