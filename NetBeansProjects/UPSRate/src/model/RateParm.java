/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.a4.utils.ConnectAs400;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import upsrate.As400UPSAuth;
import upsrate.GetUpsRate;

/**
 *
 * @author Khatchik
 */
public class RateParm {
    private Connection con;
    private String shprNo;
    private String shprName;
    private String shprAddr1;
    private String shprAddr2;
    private String shprAddr3;
    private String shprCty;
    private String shprStt;
    private String shprZip;
    private String shprCntry;
    private String fromName;
    private String fromAddr1;
    private String fromAddr2;
    private String fromAddr3; 
    private String fromCty; 
    private String fromStt;
    private String fromZip;
    private String fromCntry;
    private String fromPhon;
    private String fromFax;
    private String toName;
    private String toAddr1;
    private String toAddr2;
    private String toAddr3;
    private String toCity;
    private String toStt;
    private String toZip;
    private String toCntry;
    private String toPhon;
    private String toFax;
    private String pickupType; // '01'
    private String svcCode;     //Service code
    private String svcCodeDesc;
    private String numPieces;   // number of packages
//    private String pkgTyp;     // package type;
//    private String pkgDes;      // package Description     
//    private String uom;         // Unit of measure LBL
//    private String weight;      // weight
//    private String length;
//    private String width;  
//    private String height;
    private List<Package> packageList = new ArrayList<Package>();
    private UPSParms upsParms;
    private As400UPSAuth as400UPSAuth;
    private final Logger logger;
//    void test() {
//        height.g
//    }

    public RateParm(Logger logger) {
        this.logger = logger;
    }

    public String getShprNo() {
        return shprNo;
    }

    public void setShprNo(String shprNo) {
        this.shprNo = shprNo;
    }

    public String getShprName() {
        return shprName;
    }

    public void setShprName(String shprName) {
        this.shprName = shprName;
    }

    public String getShprAddr1() {
        return shprAddr1;
    }

    public void setShprAddr1(String shprAddr1) {
        this.shprAddr1 = shprAddr1;
    }

    public String getShprAddr2() {
        return shprAddr2;
    }

    public void setShprAddr2(String shprAddr2) {
        this.shprAddr2 = shprAddr2;
    }
    public String getShprAddr3() {
        return shprAddr3;
    }

    public void setShprAddr3(String shprAddr3) {
        this.shprAddr3 = shprAddr3;
    }
    
    public String getShprCty() {
        return shprCty;
    }

    public void setShprCty(String shprCty) {
        this.shprCty = shprCty;
    }

    public String getShprStt() {
        return shprStt;
    }

    public void setShprStt(String shprStt) {
        this.shprStt = shprStt;
    }

    public String getShprZip() {
        return shprZip;
    }

    public void setShprZip(String shprZip) {
        this.shprZip = shprZip;
    }

    public String getShprCntry() {
        return shprCntry;
    }

    public void setShprCntry(String shprCntry) {
        this.shprCntry = shprCntry;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromAddr1() {
        return fromAddr1;
    }

    public void setFromAddr1(String fromAddr1) {
        this.fromAddr1 = fromAddr1;
    }

    public String getFromAddr2() {
        return fromAddr2;
    }

    public void setFromAddr2(String fromAddr2) {
        this.fromAddr2 = fromAddr2;
    }
    public String getFromAddr3() {
        return fromAddr3;
    }

    public void setFromAddr3(String fromAddr3) {
        this.fromAddr3 = fromAddr3;
    }

    public String getFromCty() {
        return fromCty;
    }

    public void setFromCty(String fromCty) {
        this.fromCty = fromCty;
    }

    public String getFromStt() {
        return fromStt;
    }

    public void setFromStt(String fromStt) {
        this.fromStt = fromStt;
    }

    public String getFromZip() {
        return fromZip;
    }

    public void setFromZip(String fromZip) {
        this.fromZip = fromZip;
    }

    public String getFromCntry() {
        return fromCntry;
    }

    public void setFromCntry(String fromCntry) {
        this.fromCntry = fromCntry;
    }

    public String getFromPhon() {
        return fromPhon;
    }

    public void setFromPhon(String fromPhon) {
        this.fromPhon = fromPhon;
    }

    public String getFromFax() {
        return fromFax;
    }

    public void setFromFax(String fromFax) {
        this.fromFax = fromFax;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToAddr1() {
        return toAddr1;
    }

    public void setToAddr1(String toAddr1) {
        this.toAddr1 = toAddr1;
    }

    public String getToAddr2() {
        return toAddr2;
    }

    public void setToAddr2(String toAddr2) {
        this.toAddr2 = toAddr2;
    }
    
    public String getToAddr3() {
        return toAddr3;
    }

    public void setToAddr3(String toAddr3) {
        this.toAddr3 = toAddr3;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getToStt() {
        return toStt;
    }

    public void setToStt(String toStt) {
        this.toStt = toStt;
    }

    public String getToZip() {
        return toZip;
    }

    public void setToZip(String toZip) {
        this.toZip = toZip;
    }

    public String getToCntry() {
        return toCntry;
    }

    public void setToCntry(String toCntry) {
        this.toCntry = toCntry;
    }

    public String getToPhon() {
        return toPhon;
    }

    public void setToPhon(String toPhon) {
        this.toPhon = toPhon;
    }

    public String getToFax() {
        return toFax;
    }

    public void setToFax(String toFax) {
        this.toFax = toFax;
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }

    public String getSvcCode() {
        return svcCode;
    }

    public void setSvcCode(String svcCode) {
        this.svcCode = svcCode;
    }

    public String getSvcCodeDesc() {
        return svcCodeDesc;
    }

    public void setSvcCodeDesc(String svcCodeDesc) {
        this.svcCodeDesc = svcCodeDesc;
    }
    
    public String getNumPieces() {
        return numPieces;
    }

    public void setNumPieces(String numPieces) {
        this.numPieces = numPieces;
    }

    public List<Package> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }
    
    public void newPackage(String pkgTyp,
            String pkgDesc,
            String pkgDimUom,
            String pkgDimUomDes,
            String len,
            String width, 
            String height,
            String weightUom, 
            String weightUomDes,
            String weight) {
        
        packageList.add(new Package(pkgTyp,pkgDesc,pkgDimUom,pkgDimUomDes,len,width,height,weightUom,weightUomDes,weight));
        
    }
    public void crtUpsParm(String upsUrl, 
            String As400Name,
            String AS400User, 
            String As400Pwd,
            String as400Lib,
            String upsShipr) {
        upsParms = new UPSParms();
        upsParms.setAs400SystemName(As400Name);
        upsParms.setAs400UserId(AS400User);
        upsParms.setAs400Password(As400Pwd);
        upsParms.setAs400Lib(as400Lib);
        upsParms.setUpsShprNo(upsShipr);
        this.shprNo = upsShipr;
        
        connectAs400();
        as400UPSAuth = new As400UPSAuth(con,upsParms,logger);
        
    }
    
    private boolean connectAs400() {
         boolean conOk = false;
        ConnectAs400.setJtopen();
        ConnectAs400.setConParms(upsParms.getAs400SystemName(), upsParms.getAs400UserId(),
                upsParms.getAs400Password(), upsParms.getAs400Lib());
        try {
            ConnectAs400.conAs400();
            ConnectAs400.As400JobInfo as400Info;
            as400Info = ConnectAs400.getAs400JobInfo();
            System.out.println("AS400Job started:" + as400Info.getJobnNum() 
                + "/" +as400Info.getJobUser()
                + "/" + as400Info.getJobName() + "/" );
            con = ConnectAs400.getConnection();
            conOk = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            System.out.println("Error Connecting to AS400");
            conOk=false;
        }
        
        return conOk;
        
    }
//    public void rtvUPSRate() {
//        GetUpsRate upsRate = new GetUpsRate(this );
//        upsRate.rtvUPSRate();
//    }
    
    
}
