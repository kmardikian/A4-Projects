/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

import com.a4.utils.ConnectAs400;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khatchik
 */
public class CNTLP {
    private Connection con;
    private final String dtaLib;

    public CNTLP(String dtaLib) {
        //this.con = con;
        this.dtaLib = dtaLib;
    }
    public ArrayList<WrhData> getWrhList() throws CustomException  {
        this.con = ConnectAs400.getConnection();
        ArrayList<WrhData> wrhList = new ArrayList<>();
        
        Statement stmt;
        String qry = "Select a.cnelk "
                +",a.cndes "
                +"from " + dtaLib + ".CNTLP a "
                +"Where a.cnelt = 'WQ' "
                +"and substring(cnelk,1,1) <> '*' " 
                ;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qry);
            while (rs.next() ) { 
                wrhList.add(new WrhData(rs.getString("CNELK").trim(),
                rs.getString("CNDES").substring(0, 15)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CNTLP.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException("Error Getting Warehouse list: " + ex.getLocalizedMessage());
        }
                
        return wrhList;
    }
    public String getShipViaDes(String sViaCd ) throws CustomException {
        this.con = ConnectAs400.getConnection();
        String sViaName = null;
        Statement stmt;
        
        String qry = "Select a.cnelk "
                +",a.cndes "
                +"from " + dtaLib + ".CNTLP a "
                +"Where a.cnelt = 'SV' "
                +"and cnelk = '" + sViaCd + "'" 
                ;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qry);
            if (rs.next()) {
                sViaName = rs.getString("CNDES");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CNTLP.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException("Error retrieving Ship via data " + ex.getLocalizedMessage() );
            
        }
        
        return sViaName;
    }
    public ArrayList<PtktStat> getPtktStatList() throws CustomException {
         this.con = ConnectAs400.getConnection();
          ArrayList<PtktStat> ptktStatList = new ArrayList<>();
          Statement stmt;
          String Qry = "Select CNELK "
                  + ",cnva2 "
                  + "from " + dtaLib + ".CNTLP a "
                  + "Where cnelt = '90' "
                  + "and substring(cnva1,8,1) <>'I' "
                  + "and (cnva2 < 85 or cnva2 = 90) "
                  //+ " and CNELK not in('ASG','CCT')"
                  ;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(Qry);
            while(rs.next()) {
                ptktStatList.add(new PtktStat(
                rs.getString("CNELK").trim()
                ,rs.getBigDecimal("CNVA2")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CNTLP.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException("error getting PTKT StatList " + ex.getLocalizedMessage());
        }
          
        return ptktStatList;
        
    }
    
}
