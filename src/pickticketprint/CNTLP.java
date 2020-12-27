/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

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
    private final Connection con;
    private final UpsShprParms parms;
    public CNTLP(UpsShprParms parms) {
        this.parms = parms;
        this.con= ConnectAs400.getConnection();
    }
    
    public ArrayList<String> getWhList() throws CustomException{
        ArrayList<String> whList = new ArrayList<>();
        String sql = "Select cnelk "
                + ",substring(cnDes,1,15) CNDES "
                + "from " + parms.getAs400Lib() + ".CNTLP "
                + "where cnelt ='WQ' "
                + "and substring(cnelk,1,1) <> '*'"; 
        
        try( Statement stmt = con.createStatement();
                ResultSet rs= stmt.executeQuery(sql) ) {
            while(rs.next()) {
                whList.add(rs.getString("CNELK") + " " + rs.getString("CNDES"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CNTLP.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException(ex.getMessage());
        }
        
        return whList;
        
    }
    
}
