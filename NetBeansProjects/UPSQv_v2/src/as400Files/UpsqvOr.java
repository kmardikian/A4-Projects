/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as400Files;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import upsqv_v2.QVParms;

/**
 *
 * @author khatchik
 */
public class UpsqvOr {

    private final Connection con;
    private final PreparedStatement ps;
    private final QVParms parms;
    private String file;        // Subscription File name    
    private String pkNmTg1;      // origin Package reference number (tag)
    private String pkRfCd1;      // origin Package reference code    
    private String pkRfVa1;      // origin package reference value
 
    private String pkNmTg2;      // origin Package reference number (tag)
    private String pkRfCd2;      // origin Package reference code    
    private String pkRfVa2;      // origin package reference value

    private String pkNmTg3;      // origin Package reference number (tag)
    private String pkRfCd3;      // origin Package reference code    
    private String pkRfVa3;      // origin package reference value

    private String pkNmTg4;      // origin Package reference number (tag)
    private String pkRfCd4;      // origin Package reference code    
    private String pkRfVa4;      // origin package reference value

    private String pkNmTg5;      // origin Package reference number (tag)
    private String pkRfCd5;      // origin Package reference code    
    private String pkRfVa5;      // origin package reference value
    
    
    private String shNmTg1;      // origin shipment reference number (tag)
    private String shRfCd1;      // origin shipment reference code 
    private String shRfVa1;      // origin shipment reference value 
    private String shNmTg2;      // origin shipment reference number (tag)
    private String shRfCd2;      // origin shipment reference code 
    private String shRfVa2;      // origin shipment reference value 
    private String shNmTg3;      // origin shipment reference number (tag)
    private String shRfCd3;      // origin shipment reference code 
    private String shRfVa3;      // origin shipment reference value
    private String shNmTg4;      // origin shipment reference number (tag)
    private String shRfCd4;      // origin shipment reference code 
    private String shRfVa4;      // origin shipment reference value
    private String shNmTg5;      // origin shipment reference number (tag)
    private String shRfCd5;      // origin shipment reference code 
    private String shRfVa5;      // origin shipment reference value
    private String shprNo;      // origin shipper number 
    private String trk;         // Origin Tracking number 
    private String dat;         // Origin Date.  Date package picked up.
    private String tim;         // Origin Time.  Time package picked up.
    private String aCty;        // Origin Activity City
    private String aSt;         // origin Activity State
    private String cntr;        // origin Activity Country
    private String bTAOp;       // Origin Bill to Account option.
    private String bTANm;       // Origin Bill to Account number.
    private String sDlDt;       // Origin Schedule Delivery Date;
    private String sDlTm;       // Origin Schedule Delivery Time. 

    public UpsqvOr(Connection con, QVParms qvparms) throws SQLException {
        this.con = con;
        this.parms = qvparms;
        clrFlds();
        ps = con.prepareStatement("Insert into " + parms.getAs400Lib()
                + ".UPSQVOR "
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
                + ",QVWSHPRNO,QVWTRK,QVWDAT,QVWTIM,QVWACTY,QVWAST"
                + ",QVWCNTR,QVWBTAOP,QVWBTANM,QVWSDLDT,QVWSDLTM) "
                + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    }
    public void write() throws SQLException {
        ps.setString(1, file.trim());
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
        ps.setString(36, aCty.trim());
        ps.setString(37, aSt.trim());
        ps.setString(38, cntr.trim());
        ps.setString(39, bTAOp.trim());
        ps.setString(40, bTANm.trim());
        ps.setString(41, sDlDt.trim());
        ps.setString(42, sDlTm.trim());
        
        ps.execute();
        clrFlds();
    }
    public void clrFlds() {
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
        setACty(" ");
        setASt(" ");
        setCntr(" ");
        setBTAOp(" ");
        setBTANm(" ");
        setSDlDt(" ");
        setSDlTm(" ");
        setFile(" "); 
    }

    /**
     * @param PkNmTg1 the PkNmTg1 to set
     */
    public void setPkNmTg1(String pkNmTg1) {
        this.pkNmTg1 = pkNmTg1;
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
     * @param oShRfCd the shRfCd1 to set
     */
    public void setShRfCd1(String oShRfCd) {
        this.shRfCd1 = oShRfCd;
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
     * @param trk the trk to set
     */
    public void setTrk(String trk) {
        this.trk = trk;
    }

    /**
     * @param dat the dat to set
     */
    public void setDat(String dat) {
        this.dat = dat;
    }

    /**
     * @param tim the tim to set
     */
    public void setTim(String tim) {
        this.tim = tim;
    }

    /**
     * @param aCty the aCty to set
     */
    public void setACty(String aCty) {
        this.aCty = aCty;
    }

    /**
     * @param aSt the aSt to set
     */
    public void setASt(String aSt) {
        this.aSt = aSt;
    }

    /**
     * @param cntr the cntr to set
     */
    public void setCntr(String cntr) {
        this.cntr = cntr;
    }

    /**
     * @param bTAOp the bTAOp to set
     */
    public void setBTAOp(String bTAOp) {
        this.bTAOp = bTAOp;
    }

    /**
     * @param bTANm the bTANm to set
     */
    public void setBTANm(String bTANm) {
        this.bTANm = bTANm;
    }

    /**
     * @param sDlDt the sDlDt to set
     */
    public void setSDlDt(String sDlDt) {
        this.sDlDt = sDlDt;
    }

    /**
     * @param sDlTm the sDlTm to set
     */
    public void setSDlTm(String sDlTm) {
        this.sDlTm = sDlTm;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
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
     * @param pkNmTg5 the pkNmTg5 to set
     */
    public void setPkNmTg5(String pkNmTg5) {
        this.pkNmTg5 = pkNmTg5;
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
    

}
