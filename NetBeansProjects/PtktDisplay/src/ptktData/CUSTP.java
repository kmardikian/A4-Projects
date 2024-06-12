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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Khatchik
 */
public class CUSTP {

    private Connection con;
    private final String dtaLib;

    public CUSTP(String dtaLib) {
        this.dtaLib = dtaLib;
    }

    public String getCust(String cusNo) throws CustomException {
        this.con = ConnectAs400.getConnection();
        String custName = null;

        Statement stmt;
        String qry = "Select a.cunam "
                + "from " + dtaLib + ".CUSTP a "
                + "Where a.cusol = '" + cusNo + "' ";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qry);
            while (rs.next()) {
                custName = rs.getString("CUNAM");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CUSTP.class.getName()).log(Level.SEVERE, null, ex);
            throw new CustomException("Error Getting Customer Data: " + ex.getLocalizedMessage());
        }

        return custName;
    }

}
