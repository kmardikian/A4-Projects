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
public class UpsqvGn {
    private final Connection con;
    private final PreparedStatement ps;
    private final QVParms parms;
    private String file;            // file name
    private String atp;             // Activity type
    private String trk;             // Tracking number 
    private String shpr;            // Shipper
    private List<Reference> shRef; 
    // Ship Reference
    private List<Reference> pkRef;  // Pkg Reference 
    private String svCd;            // Service Code
    private String paDt;            // Package Activity Date
    private String paTm;            // Package Activity Time 
    private String btOpt;           // Bill to Option
    private String btAct;           // Bill to Account
    private String shLid;           // Ship to Location Id
    private String shRadn;          // ship to Recv addr name
    private String bkmrk;           // Book Mark
    private String rsDt;            // reShedule Date
    private String fEml;            // fail e-mail address 
    private String fNcd;            // Fail notification Code
    
    
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
            
            
            
    public UpsqvGn(Connection con, QVParms qvparms) throws SQLException {
        this.shRef = new ArrayList<Reference>();
        this.pkRef = new ArrayList<Reference>();
        this.con = con;
        this.parms = qvparms;
        clrFlds();
        ps = con.prepareStatement("Insert into " + parms.getAs400Lib()
                + ".UPSQVGN "
                + "(QVWFILE,QVWATP,QVWTRK ,QVWSHPR"
                + ",QVWSHNMTG1,QVWSHRFCD1,QVWSHRFVA1"
                + ",QVWSHNMTG2,QVWSHRFCD2,QVWSHRFVA2"
                + ",QVWSHNMTG3,QVWSHRFCD3,QVWSHRFVA3"
                + ",QVWSHNMTG4,QVWSHRFCD4,QVWSHRFVA4"
                + ",QVWSHNMTG5,QVWSHRFCD5,QVWSHRFVA5"
                + ",QVWPKNMTG1,QVWPKRFCD1,QVWPKRFVA1"
                + ",QVWPKNMTG2,QVWPKRFCD2,QVWPKRFVA2"
                + ",QVWPKNMTG3,QVWPKRFCD3,QVWPKRFVA3"
                + ",QVWPKNMTG4,QVWPKRFCD4,QVWPKRFVA4"
                + ",QVWPKNMTG5,QVWPKRFCD5,QVWPKRFVA5"
                + ",QVWSVCD ,QVWPADT ,QVWPATM ,QVWDBTOPT,QVWDBTACT"
                + ",QVWSHLID ,QVWSHRADN ,QVWBKMRK,QVWRSDT,QVWFEML,QVWFNCD)"
                + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    }
    private void clrFlds() {
        setFile(" ");
        setAtp(" ");
        setTrk(" ");
        setShpr(" ");
        shRef.clear();
        pkRef.clear();
        setSvCd(" ");
        setPaDt(" ");
        setPaTm(" ");
        setBtOpt(" ");
        setBtAct(" ");
        setShLid(" ");
        setShRadn(" ");
        setBkmrk(" ");
        setRsDt(" ");
        setfEml(" ");
        setfNcd(" ");
        
    }
    public void write() throws SQLException {
        ps.setString(1, file.trim());
        ps.setString(2, atp.trim());
        ps.setString(3, trk.trim());
        ps.setString(4, shpr.trim()); 
        
        for (int x = 0; x< 5; x++ ) {
            if (shRef.size() >= x+1 ) {
                ps.setString(5 +(x*3), shRef.get(x).getNumber().trim());
                ps.setString(6 + (x*3), shRef.get(x).getCode().trim());
                ps.setString(7 + (x*3), shRef.get(x).getValue().trim());
            } else {
                ps.setString(5 +(x* 3), " ");
                ps.setString(6 + (x*3), " ");
                ps.setString(7 + (x*3), " ");
            }
        }
        
        for (int x = 0; x< 5; x++ ) {
            if (pkRef.size() >= x+1 ) {
                ps.setString(20 + (x*3), pkRef.get(x).getNumber().trim());
                ps.setString(21 + (x*3), pkRef.get(x).getCode().trim());
                ps.setString(22 + (x*3), pkRef.get(x).getValue().trim());
            } else {
                ps.setString(20 + (x*3), " ");
                ps.setString(21 + (x*3), " ");
                ps.setString(22 + (x*3), " ");
            }
        }
        
        ps.setString(35, svCd.trim());
        ps.setString(36, paDt.trim());
        ps.setString(37, paTm.trim());
        ps.setString(38, btOpt.trim());
        ps.setString(39, btAct.trim());
        ps.setString(40, shLid.trim());
        ps.setString(41,shRadn.trim());
        ps.setString(42, bkmrk.trim());
        ps.setString(43, rsDt.trim());
        ps.setString(44, fEml.trim());
        ps.setString(45, fNcd.trim());
        
        ps.execute();
        
               
        clrFlds();

    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @param atp the atp to set
     */
    public void setAtp(String atp) {
        this.atp = atp;
    }

    /**
     * @param trk the trk to set
     */
    public void setTrk(String trk) {
        this.trk = trk;
    }

    /**
     * @param shpr the shpr to set
     */
    public void setShpr(String shpr) {
        this.shpr = shpr;
    }

    /**
     * @param shRef the shRef to set
     */
    public void setShRef(List<Reference> shRef) {
        this.shRef = shRef;
    }
    public void setShRef( Reference shRef ) {
        this.shRef.add(shRef);
    }

    /**
     * @param pkRef the pkRef to set
     */
    public void setPkRef(List<Reference> pkRef) {
        this.pkRef = pkRef;
    }
    public void setPkRef( Reference pkRef) {
        this.pkRef.add(pkRef);
    }

    /**
     * @param svCd the svCd to set
     */
    public void setSvCd(String svCd) {
        this.svCd = svCd;
    }

    /**
     * @param paDt the paDt to set
     */
    public void setPaDt(String paDt) {
        this.paDt = paDt;
    }

    /**
     * @param paTm the paTm to set
     */
    public void setPaTm(String paTm) {
        this.paTm = paTm;
    }

    /**
     * @param btOpt the btOpt to set
     */
    public void setBtOpt(String btOpt) {
        this.btOpt = btOpt;
    }

    /**
     * @param btAct the btAct to set
     */
    public void setBtAct(String btAct) {
        this.btAct = btAct;
    }

    /**
     * @param shLid the shLid to set
     */
    public void setShLid(String shLid) {
        this.shLid = shLid;
    }

    /**
     * @param shRadn the shRadn to set
     */
    public void setShRadn(String shRadn) {
        this.shRadn = shRadn;
    }

    /**
     * @param bkmrk the bkmrk to set
     */
    public void setBkmrk(String bkmrk) {
        this.bkmrk = bkmrk;
    }

    /**
     * @param rsDt the rsDt to set
     */
    public void setRsDt(String rsDt) {
        this.rsDt = rsDt;
    }

    /**
     * @param fEml the fEml to set
     */
    public void setfEml(String fEml) {
        this.fEml = fEml;
    }

    /**
     * @param fNcd the fNcd to set
     */
    public void setfNcd(String fNcd) {
        this.fNcd = fNcd;
    }

    /**
     * @param pAdt the pAdt to set
     */
    public void setpAdt(String pAdt) {
        this.pAdt = pAdt;
    }

    /**
     * @param pAtm the pAtm to set
     */
    public void setpAtm(String pAtm) {
        this.pAtm = pAtm;
    }

    /**
     * @param pDes the pDes to set
     */
    public void setpDes(String pDes) {
        this.pDes = pDes;
    }

    /**
     * @param pDLn the pDLn to set
     */
    public void setpDLn(String pDLn) {
        this.pDLn = pDLn;
    }

    /**
     * @param pDwd the pDwd to set
     */
    public void setpDwd(String pDwd) {
        this.pDwd = pDwd;
    }

    /**
     * @param pDHt the pDHt to set
     */
    public void setpDHt(String pDHt) {
        this.pDHt = pDHt;
    }

    /**
     * @param pUCd the pUCd to set
     */
    public void setpUCd(String pUCd) {
        this.pUCd = pUCd;
    }

    /**
     * @param pUDes the pUDes to set
     */
    public void setpUDes(String pUDes) {
        this.pUDes = pUDes;
    }

    /**
     * @param pDWt the pDWt to set
     */
    public void setpDWt(String pDWt) {
        this.pDWt = pDWt;
    }

    /**
     * @param pDWtUm the pDWtUm to set
     */
    public void setpDWtUm(String pDWtUm) {
        this.pDWtUm = pDWtUm;
    }

    /**
     * @param pLpkg the pLpkg to set
     */
    public void setpLpkg(String pLpkg) {
        this.pLpkg = pLpkg;
    }

    /**
     * @param pTrk the pTrk to set
     */
    public void setpTrk(String pTrk) {
        this.pTrk = pTrk;
    }
    
   
}
