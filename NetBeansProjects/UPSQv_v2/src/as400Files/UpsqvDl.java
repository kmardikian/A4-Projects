/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as400Files;


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
public class UpsqvDl {

    private final Connection con;
    private final PreparedStatement ps;
    private final QVParms parms;
    private String dFile;                   // File Name 
    private String pkNmTg1;                 // Delivery Pkg Ref Number tag
    private String pkRfCd1;                 // Delivery Pkg ref code  
    private String pkRfVa1;
    private String pkNmTg2;                 // Delivery Pkg Ref Number tag
    private String pkRfCd2;                 // Delivery Pkg ref code  
    private String pkRfVa2;
    private String pkNmTg3;                 // Delivery Pkg Ref Number tag
    private String pkRfCd3;                 // Delivery Pkg ref code  
    private String pkRfVa3;
    private String pkNmTg4;                 // Delivery Pkg Ref Number tag
    private String pkRfCd4;                 // Delivery Pkg ref code  
    private String pkRfVa4;
    private String pkNmTg5;                 // Delivery Pkg Ref Number tag
    private String pkRfCd5;                 // Delivery Pkg ref code  
    private String pkRfVa5;
    private String shNmTg1;
    private String shRfCd1;
    private String shRfVa1;
    private String shNmTg2;
    private String shRfCd2;
    private String shRfVa2;
    private String shNmTg3;
    private String shRfCd3;
    private String shRfVa3;
    private String shNmTg4;
    private String shRfCd4;
    private String shRfVa4;
    private String shNmTg5;
    private String shRfCd5;
    private String shRfVa5;
    private String shprNo;
    private String trk;
    private String dat;
    private String tim;
    private String drls;
    private String aCty;                   // Activity City 
    private String aSt;                    // Activity State
    private String cntr;                   // Activity Country
    private String csNm;                   // ConsigneeName 
    private String strNo;                  // Street number
    private String strPfx;                 // street prefix
    private String strNm;                  // Street name
    private String strTp;                  // Street type
    private String strSfx;                 // Street sufix
    private String bldNm;                  // Building name;
    private String exAdTp;                 // extended address type
    private String exALow;                 // extended address low
    private String exAHi;                  // extended address high
    private List<ExtendedAddress> extAddr; // extended address 
    private String plDv3;                  // Political Div3 
    private String dCty;                   // Delivery City
    private String dSt;                    // Delivery state
    private String dCntr;                  // Delivery Country 
    private String postP;                  // Post code Primary
    private String postPE;                 // Post Code primary extended
    private String lCd;                 // location Code
    private String lDes;                   // location destination 
    private String sgnNm;                  // signed by name
    private String codCC;                  // COD Currency code
    private String codAmt;                 // COD Amount.
    private String bTopt;                  // Bill to option
    private String bTAct;                  // Bill to Account
    private String pDt;                    // Pickup date;
    private String aPLc;                   // Access poing location
    
   
    public UpsqvDl(Connection con, QVParms qvparms) throws SQLException {
        this.con = con;
        this.parms = qvparms;
        extAddr = new ArrayList<ExtendedAddress>();
        clrFlds();
        ps = con.prepareStatement("Insert into " + parms.getAs400Lib()
                + ".UPSQVDL "
                + "(QVWFILE,QVWPKNMTG1,QVWPKRFCD1,QVWPKRFVA1"
                + ",QVWPKNMTG2,QVWPKRFCD2,QVWPKRFVA2"
                + ",QVWPKNMTG3,QVWPKRFCD3,QVWPKRFVA3"
                + ",QVWPKNMTG4,QVWPKRFCD4,QVWPKRFVA4"
                + ",QVWPKNMTG5,QVWPKRFCD5,QVWPKRFVA5"
                + ",QVWSHNMTG1,QVWSHRFCD1,QVWSHRFVA1"
                + ",QVWSHNMTG2,QVWSHRFCD2,QVWSHRFVA2"
                + ",QVWSHNMTG3,QVWSHRFCD3,QVWSHRFVA3"
                + ",QVWSHNMTG4,QVWSHRFCD4,QVWSHRFVA4"
                + ",QVWSHNMTG5,QVWSHRFCD5,QVWSHRFVA5"
                + ",QVWSHPRNO,QVWTRK,QVWDAT,QVWTIM,QVWDRLS,QVWACTY"
                + ",QVWAST,QVWCNTR,QVWCSNM,QVWSTRNO,QVWSTRPFX,QVWSTRNM,QVWSTRTP"
                + ",QVWSRTSFX,QVWBLDNM,QVWEXADTP1,QVWEXALOW1,QVWEXAHI1"
                + ",QVWEXADTP2,QVWEXALOW2,QVWEXAHI2"
                + ",QVWEXADTP3,QVWEXALOW3,QVWEXAHI3"
                + ",QVWPLDV3,QVWDCTY,QVWDST,QVWDCNTR,QVWPOSTP"
                + ",QVWPOSTPE,QVWLCD,QVWLDES,QVWSGNNM,QVWCODCC,QVWCODAMT"
                + ",QVWBTOPT,QVWBTACT,QVWPDT,QVWAPLC) "
                + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    }                                           
    public void write() throws SQLException {
        ps.setString(1, dFile.trim());
        ps.setString(2, pkNmTg1.trim());
        ps.setString(3, pkRfCd1.trim());
        ps.setString(4, pkRfVa1.trim());
        ps.setString(5, pkNmTg2.trim());
        ps.setString(6, pkRfCd2.trim());
        ps.setString(7, pkRfVa2.trim());
        ps.setString(8, pkNmTg3.trim());
        ps.setString(9, pkRfCd3.trim());
        ps.setString(10, pkRfVa3.trim());
        ps.setString(11, pkNmTg4.trim());
        ps.setString(12, pkRfCd4.trim());
        ps.setString(13, pkRfVa4.trim());
        ps.setString(14, pkNmTg5.trim());
        ps.setString(15, pkRfCd5.trim());
        ps.setString(16, pkRfVa5.trim());
        ps.setString(17, shNmTg1.trim());
        ps.setString(18, shRfCd1.trim());
        ps.setString(19, shRfVa1.trim());
        ps.setString(20, shNmTg2.trim());
        ps.setString(21, shRfCd2.trim());
        ps.setString(22, shRfVa2.trim());
        ps.setString(23, shNmTg3.trim());
        ps.setString(24, shRfCd3.trim());
        ps.setString(25, shRfVa3.trim());
        ps.setString(26, shNmTg4.trim());
        ps.setString(27, shRfCd4.trim());
        ps.setString(28, shRfVa4.trim());
        ps.setString(29, shNmTg5.trim());
        ps.setString(30, shRfCd5.trim());
        ps.setString(31, shRfVa5.trim());
        ps.setString(32, shprNo.trim());
        ps.setString(33, trk.trim());
        ps.setString(34,dat.trim());
        ps.setString(35,tim.trim());
        ps.setString(36, drls.trim());
        ps.setString(37, aCty.trim());
        ps.setString(38, aSt.trim());
        ps.setString(39, cntr.trim());
        ps.setString(40, csNm.trim());
        ps.setString(41, strNo.trim());
        ps.setString(42, strPfx.trim());
        ps.setString(43, strNm.trim());
        ps.setString(44, strTp.trim());
        ps.setString(45, strSfx.trim());
        ps.setString(46, bldNm.trim());
       // ps.setString(47,exAdTp);
       // ps.setString(48, exALow);
       // ps.setString(49, exAHi);
        for (int x = 0; x< 3; x++) {
            if (extAddr.size()< x +1) {
                ps.setString((x * 3) + 47, " ");
                ps.setString((x * 3) + 48, " ");
                ps.setString((x * 3) + 49, " ");
            } else {
                ps.setString((x * 3) + 47, extAddr.get(x).getType().trim());
                ps.setString((x * 3) + 48, extAddr.get(x).getLow().trim());
                ps.setString((x * 3) + 49, extAddr.get(x).getHigh().trim());
                
            }
            
        }
        ps.setString(56, plDv3.trim());
        ps.setString(57, dCty.trim());
        ps.setString(58, dSt.trim());
        ps.setString(59, dCntr.trim());
        ps.setString(60, postP.trim());
        ps.setString(61, postPE.trim());
        ps.setString(62, lCd.trim());
        ps.setString(63, lDes.trim());
        ps.setString(64,sgnNm.trim());
        ps.setString(65,codCC.trim());
        ps.setString(66, codAmt.trim());
        ps.setString(67, bTopt.trim());
        ps.setString(68, bTAct.trim());
        ps.setString(69, pDt.trim());
        ps.setString(70, aPLc.trim());
        
        ps.execute();
        clrFlds();
    }
    public void clrFlds() {
        setdFile(" ");
        setPkNmTg1(" ");
        setPkRfCd1(" ");
        setPkRfVa1(" ");
        setPkNmTg2(" ");
        setPkRfCd2(" ");
        setPkRfVa2(" ");
        setPkNmTg3(" ");
        setPkRfCd3(" ");
        setPkRfVa3(" ");
        setPkNmTg4(" ");
        setPkRfCd4(" ");
        setPkRfVa4(" ");
        setPkNmTg5(" ");
        setPkRfCd5(" ");
        setPkRfVa5(" ");
        setShNmTg1(" ");
        setShRfCd1(" ");
        setShRfVa1(" ");
        setShNmTg2(" ");
        setShRfCd2(" ");
        setShRfVa2(" ");
        setShNmTg3(" ");
        setShRfCd3(" ");
        setShRfVa3(" ");
        setShNmTg4(" ");
        setShRfCd4(" ");
        setShRfVa4(" ");
        setShNmTg5(" ");
        setShRfCd5(" ");
        setShRfVa5(" ");
        setShprNo(" ");
        setTrk(" ");
        setDat(" ");
        setTim(" ");
        setDrls(" ");
        setaCty(" ");
        setdASt(" ");
        setCntr(" ");
        setCsNm(" ");
        setStrNo(" ");
        setStrPfx(" ");
        setStrNm(" ");
        setStrTp(" ");
        setStrSfx(" ");
        setBldNm(" ");
        setExAdTp(" ");
        setExALow(" ");
        setExAHi(" ");
        setPlDv3(" ");
        setdCty(" ");
        setdSt(" ");
        setdCntr(" ");
        setPostP(" ");
        setPostPE(" ");
        setlCd(" ");
        setdlDes(" ");
        setSgnNm(" ");
        setCodCC(" ");
        setCodAmt(" ");
        setbTopt(" ");
        setbTAct(" ");
        setpDt(" ");
        setaPLc(" ");
        extAddr.clear();
    }

    /**
     * @param dFile the dFile to set
     */
    public void setdFile(String dFile) {
        this.dFile = dFile;
    }

    /**
     * @param PkNmTg1 the pkNmTg1 to set
     */
    public void setPkNmTg1(String PkNmTg1) {
        this.pkNmTg1 = PkNmTg1;
    }

    /**
     * @param pkRfCd1 the pkRfCd1 to set
     */
    public void setPkRfCd1(String pkRfCd1) {
        this.pkRfCd1 = pkRfCd1;
    }

    /**
     * @param pkRfVa1 the pkRfVa1 to set
     */
    public void setPkRfVa1(String pkRfVa1) {
        this.pkRfVa1 = pkRfVa1;
    }

    /**
     * @param shNmTg1 the shNmTg1 to set
     */
    public void setShNmTg1(String shNmTg1) {
        this.shNmTg1 = shNmTg1;
    }

    /**
     * @param shRfCd1 the shRfCd1 to set
     */
    public void setShRfCd1(String shRfCd1) {
        this.shRfCd1 = shRfCd1;
    }

    /**
     * @param shRfVa1 the shRfVa1 to set
     */
    public void setShRfVa1(String shRfVa1) {
        this.shRfVa1 = shRfVa1;
    }

    /**
     * @param shprNo the shprNo to set
     */
    public void setShprNo(String shprNo) {
        this.shprNo = shprNo;
    }

    /**
     * @param dTrk the trk to set
     */
    public void setTrk(String dTrk) {
        this.trk = dTrk;
    }

    /**
     * @param dat the dat to set
     */
    public void setDat(String dat) {
        this.dat = dat;
    }

    /**
     * @param dTim the tim to set
     */
    public void setTim(String tim) {
        this.tim = tim;
    }

    /**
     * @param drls the drls to set
     */
    public void setDrls(String drls) {
        this.drls = drls;
    }

    /**
     * @param aCty the aCty to set
     */
    public void setaCty(String aCty) {
        this.aCty = aCty;
    }

    /**
     * @param dASt the aSt to set
     */
    public void setdASt(String dASt) {
        this.aSt = dASt;
    }

    /**
     * @param cntr the cntr to set
     */
    public void setCntr(String cntr) {
        this.cntr = cntr;
    }

    /**
     * @param csNm the csNm to set
     */
    public void setCsNm(String csNm) {
        this.csNm = csNm;
    }

    /**
     * @param strNo the strNo to set
     */
    public void setStrNo(String strNo) {
        this.strNo = strNo;
    }

    /**
     * @param strPfx the strPfx to set
     */
    public void setStrPfx(String strPfx) {
        this.strPfx = strPfx;
    }

    /**
     * @param strNm the strNm to set
     */
    public void setStrNm(String strNm) {
        this.strNm = strNm;
    }

    /**
     * @param strTp the strTp to set
     */
    public void setStrTp(String strTp) {
        this.strTp = strTp;
    }

    /**
     * @param bldNm the bldNm to set
     */
    public void setBldNm(String bldNm) {
        this.bldNm = bldNm;
    }

    /**
     * @param exAdTp the exAdTp to set
     */
    public void setExAdTp(String exAdTp) {
        this.exAdTp = exAdTp;
    }

    /**
     * @param exALow the exALow to set
     */
    public void setExALow(String exALow) {
        this.exALow = exALow;
    }

    /**
     * @param exAHi the exAHi to set
     */
    public void setExAHi(String exAHi) {
        this.exAHi = exAHi;
    }

    /**
     * @param plDv3 the plDv3 to set
     */
    public void setPlDv3(String plDv3) {
        this.plDv3 = plDv3;
    }

    /**
     * @param postP the postP to set
     */
    public void setPostP(String postP) {
        this.postP = postP;
    }

    /**
     * @param postPE the postPE to set
     */
    public void setPostPE(String postPE) {
        this.postPE = postPE;
    }

    /**
     * @param lCd the lCd to set
     */
    public void setlCd(String lCd) {
        this.lCd = lCd;
    }

    /**
     * @param dlDes the dlDes to set
     */
    public void setdlDes(String dlDes) {
        this.lDes = dlDes;
    }

    /**
     * @param sgnNm the sgnNm to set
     */
    public void setSgnNm(String sgnNm) {
        this.sgnNm = sgnNm;
    }

    /**
     * @param codCC the codCC to set
     */
    public void setCodCC(String codCC) {
        this.codCC = codCC;
    }

    /**
     * @param codAmt the codAmt to set
     */
    public void setCodAmt(String codAmt) {
        this.codAmt = codAmt;
    }

    /**
     * @param bTopt the bTopt to set
     */
    public void setbTopt(String bTopt) {
        this.bTopt = bTopt;
    }

    /**
     * @param bTAct the bTAct to set
     */
    public void setbTAct(String bTAct) {
        this.bTAct = bTAct;
    }

    /**
     * @param pDt the pDt to set
     */
    public void setpDt(String pDt) {
        this.pDt = pDt;
    }

    /**
     * @param aPLc the aPLc to set
     */
    public void setaPLc(String aPLc) {
        this.aPLc = aPLc;
    }

    /**
     * @param strSfx the strSfx to set
     */
    public void setStrSfx(String strSfx) {
        this.strSfx = strSfx;
    }

    /**
     * @param dCty the dCty to set
     */
    public void setdCty(String dCty) {
        this.dCty = dCty;
    }

    /**
     * @param dSt the dSt to set
     */
    public void setdSt(String dSt) {
        this.dSt = dSt;
    }

    /**
     * @param dCntr the dCntr to set
     */
    public void setdCntr(String dCntr) {
        this.dCntr = dCntr;
    }

    /**
     * @param pkNmTg2 the pkNmTg2 to set
     */
    public void setPkNmTg2(String pkNmTg2) {
        this.pkNmTg2 = pkNmTg2;
    }

    /**
     * @param pkRfCd2 the pkRfCd2 to set
     */
    public void setPkRfCd2(String pkRfCd2) {
        this.pkRfCd2 = pkRfCd2;
    }

    /**
     * @param pkRfVa2 the pkRfVa2 to set
     */
    public void setPkRfVa2(String pkRfVa2) {
        this.pkRfVa2 = pkRfVa2;
    }

    /**
     * @param pkNmTg3 the pkNmTg3 to set
     */
    public void setPkNmTg3(String pkNmTg3) {
        this.pkNmTg3 = pkNmTg3;
    }

    /**
     * @param pkRfCd3 the pkRfCd3 to set
     */
    public void setPkRfCd3(String pkRfCd3) {
        this.pkRfCd3 = pkRfCd3;
    }

    /**
     * @param pkRfVa3 the pkRfVa3 to set
     */
    public void setPkRfVa3(String pkRfVa3) {
        this.pkRfVa3 = pkRfVa3;
    }

    /**
     * @param pkNmTg4 the pkNmTg4 to set
     */
    public void setPkNmTg4(String pkNmTg4) {
        this.pkNmTg4 = pkNmTg4;
    }

    /**
     * @param pkRfCd4 the pkRfCd4 to set
     */
    public void setPkRfCd4(String pkRfCd4) {
        this.pkRfCd4 = pkRfCd4;
    }

    /**
     * @param pkRfVa4 the pkRfVa4 to set
     */
    public void setPkRfVa4(String pkRfVa4) {
        this.pkRfVa4 = pkRfVa4;
    }

    /**
     * @param shNmTg5 the shNmTg5 to set
     */
    public void setShNmTg5(String shNmTg5) {
        this.shNmTg5 = shNmTg5;
    }

    /**
     * @param shRfCd5 the shRfCd5 to set
     */
    public void setShRfCd5(String shRfCd5) {
        this.shRfCd5 = shRfCd5;
    }

    /**
     * @param shRfVa5 the shRfVa5 to set
     */
    public void setShRfVa5(String shRfVa5) {
        this.shRfVa5 = shRfVa5;
    }

    /**
     * @param shNmTg2 the shNmTg2 to set
     */
    public void setShNmTg2(String shNmTg2) {
        this.shNmTg2 = shNmTg2;
    }

    /**
     * @param shRfCd2 the shRfCd2 to set
     */
    public void setShRfCd2(String shRfCd2) {
        this.shRfCd2 = shRfCd2;
    }

    /**
     * @param shRfVa2 the shRfVa2 to set
     */
    public void setShRfVa2(String shRfVa2) {
        this.shRfVa2 = shRfVa2;
    }

    /**
     * @param shNmTg3 the shNmTg3 to set
     */
    public void setShNmTg3(String shNmTg3) {
        this.shNmTg3 = shNmTg3;
    }

    /**
     * @param shRfCd3 the shRfCd3 to set
     */
    public void setShRfCd3(String shRfCd3) {
        this.shRfCd3 = shRfCd3;
    }

    /**
     * @param shRfVa3 the shRfVa3 to set
     */
    public void setShRfVa3(String shRfVa3) {
        this.shRfVa3 = shRfVa3;
    }

    /**
     * @param shNmTg4 the shNmTg4 to set
     */
    public void setShNmTg4(String shNmTg4) {
        this.shNmTg4 = shNmTg4;
    }

    /**
     * @param shRfCd4 the shRfCd4 to set
     */
    public void setShRfCd4(String shRfCd4) {
        this.shRfCd4 = shRfCd4;
    }

    /**
     * @param shRfVa4 the shRfVa4 to set
     */
    public void setShRfVa4(String shRfVa4) {
        this.shRfVa4 = shRfVa4;
    }

    /**
     * @param pkNmTg5 the pkNmTg5 to set
     */
    public void setPkNmTg5(String pkNmTg5) {
        this.pkNmTg5 = pkNmTg5;
    }

    /**
     * @return the pkRfCd5
     */
    public String getPkRfCd5() {
        return pkRfCd5;
    }

    /**
     * @param pkRfCd5 the pkRfCd5 to set
     */
    public void setPkRfCd5(String pkRfCd5) {
        this.pkRfCd5 = pkRfCd5;
    }

    /**
     * @param pkRfVa5 the pkRfVa5 to set
     */
    public void setPkRfVa5(String pkRfVa5) {
        this.pkRfVa5 = pkRfVa5;
    }

    /**
     * @param extAddr the extAddr to set
     */
    public void setExtAddr(List<ExtendedAddress> extAddr) {
        this.extAddr = extAddr;
    }
    
    public void setExtAddr(ExtendedAddress extendedAddress) {
        this.extAddr.add(extendedAddress);
        
    }
    public String toString() {
        String rtn;
               
        rtn ="File=" +dFile + " pkNmTg1 =" + pkNmTg1+
                " pDt=" +pDt + " aPlc=" + aPLc;
                
        return rtn;
    }

    

 

}
