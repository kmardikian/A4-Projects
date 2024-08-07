/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as400Files;


import as400Files.Reference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import upsqv_v2.QVParms;

/**
 *
 * @author khatchik
 */
public class UpsqvMf {
    private final Connection con;
    private final PreparedStatement ps;
    private final QVParms parms;
    private String file;            // manifest file name
    private String cmp;             // Shipper comnpany name
    private String nam;             // attention name
    private String tid;             // Tax id number
    private String phn;             // Phone #
    private String fax;             // Fax number
    private String shpr;            // shipper;
    private String eml;             // e-mail address
    private String ad1;             // shipper address 1
    private String ad2;             // shipper address 2 
    private String ad3;             // shipper address 3
    private String cty;             // shipper city 
    private String st;              // shipper state
    private String zip;             // shipper Zip
    private String cntr;            // shipper country
    private String rAdr;            // residential address indicator 
    private String shIdn;           // identification number specified by the shipper
    private String shCmp;           // Cosignee company name
    private String shNam;           // Ship to Attention name
    private String shPhn;           // ship to phone
    private String shTid;           // ship to Tax id
    private String shFax;           // ship to Fax
    private String shEml;           // ship to E-mail
    private String shCsNm;          // ship to cosignee company name
    private String shAd1;           // ship to Address 1
    private String shAd2;           // ship to Address 2
    private String shAd3;           // ship to address 3
    private String shCty;           // ship to Cty
    private String shSt;            // ship to state;
    private String shZip;           // ship to zip
    private String shCntr;          // ship to Country
    private String shLid;           // ship to location id
    private String shRAdn;          // ship to Receiving address name
    private String rnTg;            // reference number tag
    private String rnCd;            // reference number code
    private String rnVa;            // reference number value
    private List<Reference> ref;    // reference
    private String svCd;            // service code;
    private String svDs;            // service Description
    private String pDt;             // pickup date
    private String sDvDt;           // scheduled Delivery Date
    private String sDvTm;           // scheduled Delivery time
    private String docO;            // Document only 
    
    // manifest package.
    
    private String pAdt;            // package activity date
    private String pAtm;            // package activity time 
    private String pDes;            // package description
    private String pDLn;            // package Dim length
    private String pDwd;            // package dim width
    private String pDHt;            // package dim height
    private String pUCd;            // UOM Code
    private String pUDes;           // UOM Description
    private String pDWt;            // Dim Weight
    private String pDWtUm;          // Dim Weight UOM
    private String pLpkg;           // Large package 
    private String pTrk;             // Tracking #
            
            
            
    public UpsqvMf(Connection con, QVParms qvparms) throws SQLException {
        this.con = con;
        this.parms = qvparms;
        ref = new ArrayList<Reference>();
        clrFlds();
        ps = con.prepareStatement("Insert into " + parms.getAs400Lib()
                + ".UPSQVMF "
                + "(QVWFILE,QVWCMP,QVWNAM,QVWTID,QVWPHN,QVWFAX,QVWSHPR,QVWEML,QVWAD1 "
                + ",QVWAD2,QVWAD3,QVWCTY,QVWST,QVWZIP,QVWCNTR,QVWRADR,QVWSHIDN,QVWSHCMP"
                + ",QVWSHNAM,QVWSHPHN,QVWSHTID,QVWSHFAX,QVWSHEML,QVWSHCSNM,QVWSHAD1"
                + ",QVWSHAD2,QVWSHAD3,QVWSHCTY,QVWSHST,QVWSHZIP,QVWSHCNTR,QVWSHLID"
                + ",QVQSHRADN,QVWRNTG1,QVWRNCD1,QVWRNVA1"
                + ",QVWRNTG2,QVWRNCD2,QVWRNVA2"
                + ",QVWRNTG3,QVWRNCD3,QVWRNVA3"
                + ",QVWSVCD,QVWSVDS,QVWPDT,QVWSDVDT"
                + ",QVWSDVTM,QVWDOCO)"
                + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    }
    private void clrFlds() {
        setFile(" ");
        setCmp(" ");
        setNam(" ");
        setTid(" ");
        setPhn(" ");
        setFax(" ");
        setShpr(" ");
        setEml(" ");
        setAd1(" ");
        setAd2(" ");
        setAd3(" ");
        setCty(" ");
        setSt(" ");
        setZip(" ");
        setCntr(" ");
       // setShLid(" ");
        setRAdr(" ");
        setShIdn(" ");
        setShCmp(" ");
        setShNam(" ");
        setShPhn(" ");
        setShTid(" ");
        setShFax(" ");
        setShEml(" ");
        setShCsNm(" ");
        setShAd1(" ");
        setShAd2(" ");
        setShAd3(" ");
        setShCty(" ");
        setShSt(" ");
        setShZip(" ");
        setShCntr(" ");
        setShLid(" ");
        setShRAdn(" ");
        setRAdr(" ");
        setRnTg(" ");
        setRnCd(" ");
        setRnVa(" ");
        setSvCd(" ");
        setSvCd(" ");
        setSvDs(" ");
        setPDt(" ");
        setSDvDt(" ");
        setSDvTm(" ");
        setDocO(" ");
        ref.clear();
    }
    public void write() throws SQLException {
        ps.setString(1, file.trim());
        ps.setString(2,cmp.trim());
        ps.setString(3,nam.trim());
        ps.setString(4,tid.trim());
        ps.setString(5,phn.trim());
        ps.setString(6,fax.trim());
        ps.setString(7,shpr.trim());
        ps.setString(8,eml.trim());
        ps.setString(9, ad1.trim());
        ps.setString(10,ad2.trim());
        ps.setString(11,ad3.trim() );
        ps.setString(12,cty.trim());
        ps.setString(13,st.trim() );
        ps.setString(14,zip.trim());
        ps.setString(15,cntr.trim());
        ps.setString(16,rAdr.trim());
        ps.setString(17,shIdn.trim());
        ps.setString(18,shCmp.trim());
        ps.setString(19,shNam.trim());
        ps.setString(20,shPhn.trim());
        ps.setString(21,shTid.trim());
        ps.setString(22,shFax.trim());
        ps.setString(23,shEml.trim());
        ps.setString(24,shCsNm.trim());
        ps.setString(25, shAd1.trim());
        ps.setString(26, shAd2.trim());
        ps.setString(27, shAd3.trim());
        ps.setString(28, shCty.trim());
        ps.setString(29,shSt.trim());
        ps.setString(30,shZip.trim());
        ps.setString(31,shCntr.trim());
        ps.setString(32,shLid.trim());
        ps.setString(33,shRAdn.trim());
        for (int x = 0; x <3; x++ ) {
            if (ref.size() < x +1) {
                ps.setString((x* 3) + 34 , " ");
                ps.setString((x* 3) + 35 , " ");
                ps.setString((x* 3) + 36 , " ");
            } else {
                ps.setString((x* 3) + 34 , ref.get(x).getNumber().trim());
                ps.setString((x* 3) + 35 , ref.get(x).getCode().trim());
                ps.setString((x* 3) + 36 , ref.get(x).getValue().trim());
            }
        }
       // ps.setString(34,rnTg);
       // ps.setString(35,rnCd);
       // ps.setString(36, rnVa);
        ps.setString(43,svCd.trim());
        ps.setString(44,svDs.trim());
        ps.setString(45,pDt.trim());
        ps.setString(46,sDvDt.trim());
        ps.setString(47,sDvTm.trim());
        ps.setString(48,docO.trim());
        ps.execute();
        clrFlds();

    }
    
    /**
     * @param cmp the cmp to set
     */
    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    /**
     * @param nam the nam to set
     */
    public void setNam(String nam) {
        this.nam = nam;
    }

    /**
     * @param tid the tid to set
     */
    public void setTid(String tid) {
        this.tid = tid;
    }

    /**
     * @param phn the phn to set
     */
    public void setPhn(String phn) {
        this.phn = phn;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @param shpr the shpr to set
     */
    public void setShpr(String shpr) {
        this.shpr = shpr;
    }

    /**
     * @param eml the eml to set
     */
    public void setEml(String eml) {
        this.eml = eml;
    }

    /**
     * @param ad1 the ad1 to set
     */
    public void setAd1(String ad1) {
        this.ad1 = ad1;
    }

    /**
     * @param ad2 the ad2 to set
     */
    public void setAd2(String ad2) {
        this.ad2 = ad2;
    }

    /**
     * @param ad3 the ad3 to set
     */
    public void setAd3(String ad3) {
        this.ad3 = ad3;
    }

    /**
     * @param cty the cty to set
     */
    public void setCty(String cty) {
        this.cty = cty;
    }

    /**
     * @param st the st to set
     */
    public void setSt(String st) {
        this.st = st;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @param cntr the cntr to set
     */
    public void setCntr(String cntr) {
        this.cntr = cntr;
    }

    /**
     * @param shIdn the shIdn to set
     */
    public void setShIdn(String shIdn) {
        this.shIdn = shIdn;
    }

    /**
     * @param shCmp the shCmp to set
     */
    public void setShCmp(String shCmp) {
        this.shCmp = shCmp;
    }

    /**
     * @param shNam the shNam to set
     */
    public void setShNam(String shNam) {
        this.shNam = shNam;
    }

    /**
     * @param shPhn the shPhn to set
     */
    public void setShPhn(String shPhn) {
        this.shPhn = shPhn;
    }

    /**
     * @param shTid the shTid to set
     */
    public void setShTid(String shTid) {
        this.shTid = shTid;
    }

    /**
     * @param shFax the shFax to set
     */
    public void setShFax(String shFax) {
        this.shFax = shFax;
    }

    /**
     * @param shEml the shEml to set
     */
    public void setShEml(String shEml) {
        this.shEml = shEml;
    }

    /**
     * @param shCsNm the shCsNm to set
     */
    public void setShCsNm(String shCsNm) {
        this.shCsNm = shCsNm;
    }

    /**
     * @param shAd1 the shAd1 to set
     */
    public void setShAd1(String shAd1) {
        this.shAd1 = shAd1;
    }

    /**
     * @param shAd2 the shAd2 to set
     */
    public void setShAd2(String shAd2) {
        this.shAd2 = shAd2;
    }

    /**
     * @param shAd3 the shAd3 to set
     */
    public void setShAd3(String shAd3) {
        this.shAd3 = shAd3;
    }

    /**
     * @param shCty the shCty to set
     */
    public void setShCty(String shCty) {
        this.shCty = shCty;
    }

    /**
     * @param shSt the shSt to set
     */
    public void setShSt(String shSt) {
        this.shSt = shSt;
    }

    /**
     * @param shZip the shZip to set
     */
    public void setShZip(String shZip) {
        this.shZip = shZip;
    }

    /**
     * @param shCntr the shCntr to set
     */
    public void setShCntr(String shCntr) {
        this.shCntr = shCntr;
    }

    /**
     * @param shLid the shLid to set
     */
    public void setShLid(String shLid) {
        this.shLid = shLid;
    }

    /**
     * @param shRAdn the shRAdn to set
     */
    public void setShRAdn(String shRAdn) {
        this.shRAdn = shRAdn;
    }

    /**
     * @param rnTg the rnTg to set
     */
    public void setRnTg(String rnTg) {
        this.rnTg = rnTg;
    }

    /**
     * @param rnCd the rnCd to set
     */
    public void setRnCd(String rnCd) {
        this.rnCd = rnCd;
    }

    /**
     * @param rnVa the rnVa to set
     */
    public void setRnVa(String rnVa) {
        this.rnVa = rnVa;
    }

    /**
     * @param svCd the svCd to set
     */
    public void setSvCd(String svCd) {
        this.svCd = svCd;
    }

    /**
     * @param svDs the svDs to set
     */
    public void setSvDs(String svDs) {
        this.svDs = svDs;
    }

    /**
     * @param pDt the pDt to set
     */
    public void setPDt(String pDt) {
        this.pDt = pDt;
    }

    /**
     * @param sDvDt the sDvDt to set
     */
    public void setSDvDt(String sDvDt) {
        this.sDvDt = sDvDt;
    }

    /**
     * @param sDvTm the sDvTm to set
     */
    public void setSDvTm(String sDvTm) {
        this.sDvTm = sDvTm;
    }

    /**
     * @param docO the docO to set
     */
    public void setDocO(String docO) {
        this.docO = docO;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @param rAdr the rAdr to set
     */
    public void setRAdr(String rAdr) {
        this.rAdr = rAdr;
    }

    /**
     * @return the ref
     */
    public List<Reference> getRef() {
        return ref;
    }

    /**
     * @param ref the ref to set
     */
    public void setRef(List<Reference> ref) {
        this.ref = ref;
    }
    public void setRef(Reference reference) {
        this.ref.add(reference);
    }
    
}
