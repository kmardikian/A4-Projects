/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.math.BigDecimal;
import models.Dimensions;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khatchik
 */
// shipper data
public class FedexShipmentRequest {
    private String shipperName;
    private String shipperComp;
    private String shipperPhone;
    private List<String> shipperAdr = new ArrayList<>();
    private String shipperCity;
    private String shipperStat;
    private String shipperZip;
    private String shipperCountry;
    // receipient data
    private String recName;
    private String recComp;
    private String recPhone;
    private boolean recRes;         // residential ?
    private List<String> recAdr = new ArrayList<>();
    private String recCity;
    private String recStat;
    private String recZip;
    private String recCountry;
    //pkg data 
    //private DropoffType dropOffTyp;         // drop off type  1 = Business_Service_Center, 2 = drop_box, 3 = regular_pickup,  4 = request Courier, 5 = station
    private PickupTypes pickupType;
    private String srvTyp;          // service type
    private String pkgTyp;          // package type.
    // shipping charges 
    private PaymentType paymentType;
    private String payorAcctNo;
    private String payorCountry;
    private Weight pkgWeight;
    private Dimensions pkgDim;
    private String ptkt;
    private String crtnId;
    private String trkIdType;
    private String trkId;               // tracking number for deleting 
    private List<CustomerReference> customerReference = new ArrayList<>();
    private String originContactName;
    private String originCompany;
    private String originPhone;
    private List<String> orgAdr = new ArrayList<>();
    private String orgCity;
    private String orgState;
    private String orgZip;
    private String orgCountry;

    public String getShipperName() { 
        return shipperName;
    } 

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperComp() {
        return shipperComp;
    }

    public void setShipperComp(String shipperComp) {
        this.shipperComp = shipperComp;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public List<String> getShipperAdr() {
        return shipperAdr;
    }
  
    public String getShipperCity() {
        return shipperCity;
    }

    public void setShipperCity(String shipperCity) {
        this.shipperCity = shipperCity;
    }

    public String getShipperStat() {
        return shipperStat;
    }

    public void setShipperStat(String shipperStat) {
        this.shipperStat = shipperStat;
    }

    public String getShipperZip() {
        return shipperZip;
    }

    public void setShipperZip(String shipperZip) {
        this.shipperZip = shipperZip;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecComp() {
        return recComp;
    }

    public void setRecComp(String recComp) {
        this.recComp = recComp;
    }

    public String getRecPhone() {
        return recPhone;
    }

    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
    }

    public boolean isRecRes() {
        return recRes;
    }

    public void setRecRes(boolean recRes) {
        this.recRes = recRes;
    }

    public List<String> getRecAdr() {
        return recAdr;
    }

    public String getRecCity() {
        return recCity;
    }

    public void setRecCity(String recCity) {
        this.recCity = recCity;
    }

    public String getRecStat() {
        return recStat;
    }

    public void setRecStat(String recStat) {
        this.recStat = recStat;
    }

    public String getRecZip() {
        return recZip;
    }

    public void setRecZip(String recZip) {
        this.recZip = recZip;
    }

//    public DropoffType getDropOffTyp() {
//
//
//
//        return this.dropOffTyp;
//    }

//    public void setDropOffTyp(DropoffType dropOffTyp) {
//        this.dropOffTyp = dropOffTyp;
//    }

    public String getSrvTyp() {
        return srvTyp;
    }

    public void setSrvTyp(String srvTyp) {
        this.srvTyp = srvTyp;
    }

    public String getPkgTyp() {
        return pkgTyp;
    }

    public void setPkgTyp(String pkgTyp) {
        this.pkgTyp = pkgTyp;
    }

    public String getShipperCountry() {
        return shipperCountry;
    }

    public void setShipperCountry(String shipperCountry) {
        this.shipperCountry = shipperCountry;
    }

    public String getRecCountry() {
        return recCountry;
    }

    public void setRecCountry(String recCountry) {
        this.recCountry = recCountry;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayorAcctNo() {
        return payorAcctNo;
    }

    public void setPayorAcctNo(String payorAcctNo) {
        this.payorAcctNo = payorAcctNo;
    }

    public String getPayorCountry() {
        return payorCountry;
    }

    public void setPayorCountry(String payorCountry) {
        this.payorCountry = payorCountry;
    }

    public Weight getPkgWeight() {
        return pkgWeight;
    }

    public void setPkgWeight(Weight pkgWeight) {
        this.pkgWeight = pkgWeight;
    }

    

    public Dimensions getPkgDim() {
        return pkgDim;
    }

    public void setPkgDim(Dimensions pkgDim) {
        this.pkgDim = pkgDim;
    }

    public List<CustomerReference> getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(List<CustomerReference> customerReference) {
        this.customerReference = customerReference;
    }

    

    public String getPtkt() {
        return ptkt;
    }

    public void setPtkt(String ptkt) {
        this.ptkt = ptkt;
    }

    public String getCrtnId() {
        return crtnId;
    }

    public void setCrtnId(String crtnId) {
        this.crtnId = crtnId;
    }

    public String getTrkIdType() {
        return trkIdType;
    }

    public void setTrkIdType(String trkIdType) {
        this.trkIdType = trkIdType;
    }
    

    public String getTrkId() {
        return trkId;
    }

    public void setTrkId(String trkId) {
        this.trkId = trkId;
    }

    public String getOriginContactName() {
        return originContactName;
    }

    public void setOriginContactName(String originContactName) {
        this.originContactName = originContactName;
    }

    public String getOriginCompany() {
        return originCompany;
    }

    public void setOriginCompany(String originCompany) {
        this.originCompany = originCompany;
    }

    public String getOriginPhone() {
        return originPhone;
    }

    public void setOriginPhone(String originPhone) {
        this.originPhone = originPhone;
    }

    public List<String> getOrgAdr() {
        return orgAdr;
    }

    public void setOrgAdr(List<String> orgAdr) {
        this.orgAdr = orgAdr;
    }

    public String getOrgCity() {
        return orgCity;
    }

    public void setOrgCity(String orgCity) {
        this.orgCity = orgCity;
    }

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
    }

    public String getOrgZip() {
        return orgZip;
    }

    public void setOrgZip(String orgZip) {
        this.orgZip = orgZip;
    }

    public String getOrgCountry() {
        return orgCountry;
    }

    public void setOrgCountry(String orgCountry) {
        this.orgCountry = orgCountry;
    }

    public PickupTypes getPickupType() {
        return pickupType;
    }

    public void setPickupType(PickupTypes pickupType) {
        this.pickupType = pickupType;
    }
    public void setPkgWeight(BigDecimal weight, WeightUnits unit ) {
        setPkgWeight(new Weight(unit, weight) ) ;
    }
    public void setPkgDim(int length, int width, int height, LinearUnits units) {
        setPkgDim(new Dimensions(length, width, height, units));
        
    }
    public void setPkgDim(String length, String width, String height, LinearUnits units) {
        setPkgDim(new Dimensions(Integer.parseInt(length)
                , Integer.parseInt(width)
                , Integer.parseInt(height), units));
        
    }
    
}
