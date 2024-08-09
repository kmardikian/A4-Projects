/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upsrate;

import model.UPSParms;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Khatchik
 */
public final class As400UPSAuth {

    private final Connection con;
    private PreparedStatement ps;
    private final UPSParms parms;
    private String shipper;
    private String issAt;
    private String tokenTp;
    private String clientId;
    private String token;
    private Long   expIn;
    private String status;
    private BigDecimal issDate;
    private BigDecimal issTime;
    private BigDecimal expDate;
    private BigDecimal expTime;
    private String upsAutUrl;
    private String upsSecCd; 
    private final Logger logger;
            
    private boolean foudAs400Rec = false;

    public As400UPSAuth(Connection con, UPSParms parms,Logger logger) {
        this.con = con;
        this.parms = parms;
        this.logger = logger;
        this.getAs400Auth();
    }

    public void getAs400Auth() {
        String sql = "Select * "
                + "from " + parms.getAs400Lib() + ".UPSAUTH "
                + "where UPSSHPR ='" + parms.getUpsShprNo() + "'";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if  (rs.next()){
            this.shipper = rs.getString("UPSSHPR");
            this.issAt = rs.getString("UPSISSAT");
            this.tokenTp = rs.getString("UPSTOKTP");
            this.clientId = rs.getString("UPSCLNTID");
            this.token = rs.getString("UPSTOKEN");
            this.expIn = rs.getBigDecimal("UPSEXPIN").longValue();
            this.status = rs.getString("UPSSTATUS");
            this.issDate = rs.getBigDecimal("UPSISSDT");
            this.issTime = rs.getBigDecimal("UPSISSTM");
            this.expDate = rs.getBigDecimal("UPSEXPDT");
            this.expTime = rs.getBigDecimal("UPSEXPTM");
            this.upsAutUrl =rs.getString("UPSAUTURL");
            this.upsSecCd = rs.getString("UPSSECCD");
            
            foudAs400Rec = true;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "UPSAuth getAs400Auth SQL exception {0} SQL State = {1}"
                    , new Object[]{ex.getMessage(), ex.getSQLState()});
//            System.out.println("UPSAuth getAs400Auth SQL exception "
//                    + ex.getMessage()
//                    + "sql State=" + ex.getSQLState());
            ex.printStackTrace();
            foudAs400Rec = false;
        }
    }

    public void updateAs400() {
        //System.out.println("token length is " + this.token.length());
        if (this.foudAs400Rec) {
            try {
                ps = con.prepareStatement("Update " + parms.getAs400Lib() + ".UPSAUTH a "
                        + "set UPSISSAT = ?"
                        + ",UPSTOKTP =? "
                //        + ",UPSCLNTID=? "
                        + ",UPSTOKEN=? "
                        + ",UPSEXPIN=? "
                        + ",UPSSTATUS=? "
                        + ",UPSISSDT=? "
                        + ",UPSISSTM=? "
                        + ",UPSEXPDT=? "
                        + ",UPSEXPTM=? "
                  //      + ",UPSAUTURL=? " 
                       // + "UPSSECCD=? "
                        + "where UPSSHPR = ? ");
                ps.setString(1, this.issAt);
                ps.setString(2, this.tokenTp);
              //  ps.setString(3, this.clientId);
                ps.setString(3,this.token);
                ps.setLong(4, expIn);
                ps.setString(5, this.status);
                ps.setBigDecimal(6, this.issDate);
                ps.setBigDecimal(7,this.issTime);
                ps.setBigDecimal(8, this.expDate);
                ps.setBigDecimal(9,this.expTime);
                //ps.setString(10, this.upsAutUrl);
               // ps.setString(12, this.upsSecCd );
                ps.setString(10,this.shipper);
                ps.executeUpdate();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error AS400 UPSAUTH ", ex);
            }
        } else {
            try {
                ps = con.prepareStatement("Insert into " + parms.getAs400Lib() + ".UPSAUTH"
                        + "(UPSSHPR"
                        + ",UPSISSAT"
                        + ",UPSTOKTP"
                        + ",UPSCLNTID"
                        + ",UPSTOKEN"
                        + ",UPSEXPIN"
                        + ",UPSSTATUS"
                        + ",UPSISSDT"
                        + ",UPSISSTM"
                        + ",UPSEXPDT"
                        + ",UPSEXPTM"
                        + ",UPSAUTURL" 
                        + ",UPSSECCD ) "
                        + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?)" );
                ps.setString(1,this.shipper);
                ps.setString(2, this.issAt);
                ps.setString(3, this.tokenTp);
                ps.setString(4, this.clientId);
                ps.setString(5,this.token);
                ps.setLong(6, expIn);
                ps.setString(7, this.status);
                ps.setBigDecimal(8, this.issDate);
                ps.setBigDecimal(9,this.issTime);
                ps.setBigDecimal(10, this.expDate);
                ps.setBigDecimal(11,this.expTime);
                ps.setString(12, this.upsAutUrl);
                ps.setString(13, this.upsSecCd);
                ps.execute();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "error Inserting AS400 UPSAUTH", ex);
            }

        }
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getIssAt() {
        return issAt;
    }

    public void setIssAt(String issAt) {
        this.issAt = issAt;
    }

    public String getTokenTp() {
        return tokenTp;
    }

    public void setTokenTp(String tokenTp) {
        this.tokenTp = tokenTp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpIn() {
        return expIn;
    }

    public void setExpIn(Long expIn) {
        this.expIn = expIn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getIssDate() {
        return issDate;
    }

    public void setIssDate(BigDecimal issDate) {
        this.issDate = issDate;
    }

    public BigDecimal getIssTime() {
        return issTime;
    }

    public void setIssTime(BigDecimal issTime) {
        this.issTime = issTime;
    }

    public BigDecimal getExpDate() {
        return expDate;
    }

    public void setExpDate(BigDecimal expDate) {
        this.expDate = expDate;
    }

    public BigDecimal getExpTime() {
        return expTime;
    }

    public void setExpTime(BigDecimal expTime) {
        this.expTime = expTime;
    }

    public String getUpsAutUrl() {
        return upsAutUrl;
    }

    public void setUpsAutUrl(String upsAutUrl) {
        this.upsAutUrl = upsAutUrl;
    }

    public String getUpsSecCd() {
        return upsSecCd;
    }

    public void setUpsSecCd(String upsSecCd) {
        this.upsSecCd = upsSecCd;
    }
    
    

}
