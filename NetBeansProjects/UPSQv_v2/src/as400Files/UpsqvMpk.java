/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as400Files;

import upsqv_v2.QVParms;
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
public class UpsqvMpk {
    private final Connection con;
    private final PreparedStatement ps;
    private final QVParms parms;
    private String file;            // manifest file name
    private String aDt;             // Activity Date
    private String aTm;             // Activity time.
    private String des;             // package Description
    private String dln;             // package dimension length
    private String dwd;             // package dimension widht 
    private String dht;             // package dimension height
    private String dwtUm;           // package dimensional weight unit of meas.
    private String uDes;            // uom description
    private String dWt;             // dimensional weight
    private String dpWt;            // dimensional package weight.
    private String lPkg;            // large package
    private String trk;             // tracking # 
    private List<Reference> ref;    // reference
    private String codCd;           // Package Service option cod code;
    private String codCc;           // Package Service option code curr. code.
    private String codVa;           // COD value
    private String insCC;           // insurance currency code 
    private String insVa;           // insurance value
    private String edvTm;           // earliest delivery time.
    private String hmcd;            // Hazardous material code
    private String crIn;            // care indicator
    
            
    public UpsqvMpk(Connection con, QVParms qvparms) throws SQLException {
        this.con = con;
        this.parms = qvparms;
        ref = new ArrayList<Reference>();
        clrFlds();
        ps = con.prepareStatement("Insert into " + parms.getAs400Lib()
                + ".UPSQVMPK "
                + "(QVWFILE,QVWPADT,QVWPATM,QVWPDES,QVWPDLN,QVWPDWD,QVWPDHT"
                + ",QVWPDWTUM,QVWPUDES,QVWPDWT,QVWPDPWT,QVWPLPKG,QVWPTRK"
                + ",QVWPRFTG1,QVWPRFCD1,QVWPRFVA1,QVWPRFTG2,QVWPRFCD2,QVWPRFVA2"
                + ",QVWPRFTG3,QVWPRFCD3,QVWPRFVA3,QVWPRFTG4,QVWPRFCD4,QVWPRFVA4"
                + ",QVWPRFTG5,QVWPRFCD5,QVWPRFVA5" 
                + ",QVWPSCODCD,QVWPSCODCC,QVWPSCODVA ,QVWPSINSCC"
                + ",QVWPSINSVA,QVWPEDVTM,QVWPHMCD,QVWPCRIN) "
                + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?)");
    }
    private void clrFlds() {
        setFile(" ");
        setaDt(" ");
        setaTm(" ");
        setDes(" ");
        setDln(" ");
        setDwd(" ");
        setDht(" ");
        setDwtUm(" ");
        setuDes(" ");
        setdWt(" ");
        setDpWt(" ");
        setlPkg(" ");
        setTrk(" ");
        ref.clear();
        setCodCd(" ");
        setCodCc(" ");
        setCodVa(" ");
        setInsCC(" ");
        setInsVa(" ");
        setEdvTm(" ");
        setHmcd(" ");
        setCrIn(" ");
        
    }
    public void write() throws SQLException {
        ps.setString(1, file.trim());
        ps.setString(2,aDt.trim());
        ps.setString(3,aTm.trim());
        ps.setString(4,des.trim());
        ps.setString(5,dln.trim());
        ps.setString(6,dwd.trim());
        ps.setString(7,dht.trim());
        ps.setString(8,dwtUm.trim());
        ps.setString(9,uDes.trim());
        ps.setString(10,dWt.trim());
        ps.setString(11,dpWt.trim());
        ps.setString(12,lPkg.trim());
        ps.setString(13,trk.trim());
        for (int x = 0; x < 5; x++) {
            if (x < ref.size()) {
                ps.setString((x * 3) + 14, ref.get(x).getNumber().trim());
                ps.setString((x * 3) + 15, ref.get(x).getCode().trim());
                ps.setString((x * 3) + 16, ref.get(x).getValue().trim());
            } else {
                ps.setString((x * 3) + 14, " ");
                ps.setString((x * 3) + 15, " ");
                ps.setString((x * 3) + 16, " ");
            }
        }
        ps.setString(29, codCd.trim());
        ps.setString(30, codCc.trim());
        ps.setString(31, codVa.trim());
        ps.setString(32,insCC.trim());
        ps.setString(33, insVa.trim());
        ps.setString(34, edvTm.trim());
        ps.setString(35, hmcd.trim());
        ps.setString(36, crIn.trim());
    
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
     * @param aDt the aDt to set
     */
    public void setaDt(String aDt) {
        this.aDt = aDt;
    }

    /**
     * @param aTm the aTm to set
     */
    public void setaTm(String aTm) {
        this.aTm = aTm;
    }

    /**
     * @param des the des to set
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     * @param dln the dln to set
     */
    public void setDln(String dln) {
        this.dln = dln;
    }

    /**
     * @param dwd the dwd to set
     */
    public void setDwd(String dwd) {
        this.dwd = dwd;
    }

    /**
     * @param dht the dht to set
     */
    public void setDht(String dht) {
        this.dht = dht;
    }

    /**
     * @param dwtUm the dwtUm to set
     */
    public void setDwtUm(String dwtUm) {
        this.dwtUm = dwtUm;
    }

    /**
     * @param uDes the uDes to set
     */
    public void setuDes(String uDes) {
        this.uDes = uDes;
    }

    /**
     * @param dWt the dWt to set
     */
    public void setdWt(String dWt) {
        this.dWt = dWt;
    }

    /**
     * @param dpWt the dpWt to set
     */
    public void setDpWt(String dpWt) {
        this.dpWt = dpWt;
    }

    /**
     * @param lPkg the lPkg to set
     */
    public void setlPkg(String lPkg) {
        this.lPkg = lPkg;
    }

    /**
     * @param trk the trk to set
     */
    public void setTrk(String trk) {
        this.trk = trk;
    }

    /**
     * @param ref the ref to set
     */
    public void setRef(List<Reference> ref) {
        this.ref = ref;
    }
    
    public void setRef(Reference reference ) {
        this.ref.add(reference);
    } 

    /**
     * @param codCd the codCd to set
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }

    /**
     * @param codCc the codCc to set
     */
    public void setCodCc(String codCc) {
        this.codCc = codCc;
    }

    /**
     * @param codVa the codVa to set
     */
    public void setCodVa(String codVa) {
        this.codVa = codVa;
    }

    /**
     * @param insCC the insCC to set
     */
    public void setInsCC(String insCC) {
        this.insCC = insCC;
    }

    /**
     * @param insVa the insVa to set
     */
    public void setInsVa(String insVa) {
        this.insVa = insVa;
    }

    /**
     * @param edvTm the edvTm to set
     */
    public void setEdvTm(String edvTm) {
        this.edvTm = edvTm;
    }

    /**
     * @param hmcd the hmcd to set
     */
    public void setHmcd(String hmcd) {
        this.hmcd = hmcd;
    }

    /**
     * @param crIn the crIn to set
     */
    public void setCrIn(String crIn) {
        this.crIn = crIn;
    }
    
    
}
