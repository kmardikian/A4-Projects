/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktFilter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Khatchik
 */
public class PtktFilter {
    private String ptktFr;
    private String ptktTo;
    private String ordFr;
    private String ordTo;
    private ArrayList<String> soldToList = new ArrayList<>();
    private ArrayList<String> shipViaList = new ArrayList<>();
    private ArrayList<String> wrhList = new ArrayList<>();
    private String prtDatFr;
    private String prtDatTo;
    private String strDatFr;
    private String strDatTo;
    private boolean prtSel;
    private boolean disSel;
    private boolean asgSel;
    private boolean pckSel;
    private boolean pakSel;
    private boolean cpuSel;
    private boolean wghSel;
    private boolean invSel;

    public PtktFilter() {
        ptktFr = " ";
        ptktTo = " ";
        ordFr = " ";
        ordTo = " ";
        soldToList.clear();
        shipViaList.clear();
        wrhList.clear();
        prtDatFr=" ";
        prtDatTo=" ";
        strDatFr=" ";
        strDatTo=" ";
        prtSel = true;
        disSel = true;
        asgSel = true;
        pckSel = true;
        pakSel = true;
        cpuSel = true;
        wghSel = true;
        invSel = true;
    }

    public String getPtktFr() {
        return ptktFr;
    }

    public void setPtktFr(String ptktFr) {
        this.ptktFr = ptktFr;
    }

    public String getPtktTo() {
        return ptktTo;
    }

    public void setPtktTo(String ptktTo) {
        this.ptktTo = ptktTo;
    }

    public String getOrdFr() {
        return ordFr;
    }

    public void setOrdFr(String ordFr) {
        this.ordFr = ordFr;
    }

    public String getOrdTo() {
        return ordTo;
    }

    public void setOrdTo(String ordTo) {
        this.ordTo = ordTo;
    }

    public ArrayList<String> getSoldToList() {
        return soldToList;
    }

    public void setSoldToList(ArrayList<String> soldToList) {
        this.soldToList = soldToList;
    }

    public ArrayList<String> getShipViaList() {
        return shipViaList;
    }

    public void setShipViaList(ArrayList<String> shipViaList) {
        this.shipViaList = shipViaList;
    }

    public boolean isPrtSel() {
        return prtSel;
    }

    public void setPrtSel(boolean prtSel) {
        this.prtSel = prtSel;
    }

    public boolean isDisSel() {
        return disSel;
    }

    public void setDisSel(boolean disSel) {
        this.disSel = disSel;
    }

    public boolean isAsgSel() {
        return asgSel;
    }

    public void setAsgSel(boolean asgSel) {
        this.asgSel = asgSel;
    }

    public boolean isPckSel() {
        return pckSel;
    }

    public void setPckSel(boolean pckSel) {
        this.pckSel = pckSel;
    }

    public boolean isPakSel() {
        return pakSel;
    }

    public void setPakSel(boolean pakSel) {
        this.pakSel = pakSel;
    }

    public boolean isCpuSel() {
        return cpuSel;
    }

    public void setCpuSel(boolean cpuSel) {
        this.cpuSel = cpuSel;
    }

    public boolean isWghSel() {
        return wghSel;
    }

    public void setWghSel(boolean wghSel) {
        this.wghSel = wghSel;
    }

    public boolean isInvSel() {
        return invSel;
    }

    public void setInvSel(boolean invSel) {
        this.invSel = invSel;
    }

    public String getPrtDatFr() {
        return prtDatFr;
    }

    public void setPrtDatFr(String prtDatFr) {
        this.prtDatFr = prtDatFr;
    }

    public String getPrtDatTo() {
        return prtDatTo;
    }

    public void setPrtDatTo(String prtDatTo) {
        this.prtDatTo = prtDatTo;
    }

    public ArrayList<String> getWrhList() {
        return wrhList;
    }

    public void setWrhList(ArrayList<String> wrhList) {
        this.wrhList = wrhList;
    }

    public String getStrDatFr() {
        return strDatFr;
    }

    public void setStrDatFr(String strDatFr) {
        this.strDatFr = strDatFr;
    }

    public String getStrDatTo() {
        return strDatTo;
    }

    public void setStrDatTo(String strDatTo) {
        this.strDatTo = strDatTo;
    }
    
    
    
}
