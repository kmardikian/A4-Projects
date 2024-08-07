/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package as400Files;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import upsqv_v2.QVParms;


/**
 *
 * @author Khatchik
 */
public final class UPSAuth {

    private final Connection con;
    private PreparedStatement ps;
    private final QVParms parms;
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
    private boolean foudAs400Rec = false;

    public UPSAuth(Connection con, QVParms parms) {
        this.con = con;
        this.parms = parms;
        this.getAs400Auth();
    }

    public void getAs400Auth() {
        String sql = "Select * "
                + "from " + parms.getAs400SecLib() + ".UPSAUTH "
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
            foudAs400Rec = true;
            }
        } catch (SQLException ex) {
            System.out.println("UPSAuth getAs400Auth SQL exception "
                    + ex.getMessage()
                    + "sql State=" + ex.getSQLState());
            ex.printStackTrace();
            foudAs400Rec = false;
        }
    }

    public void updateAs400() {
        System.out.println("token length is " + this.token.length());
        if (this.foudAs400Rec) {
            try {
                ps = con.prepareStatement("Update " + parms.getAs400SecLib() + ".UPSAUTH a "
                        + "set UPSISSAT = ?"
                        + ",UPSTOKTP =?"
                        + ",UPSCLNTID=?"
                        + ",UPSTOKEN=?"
                        + ",UPSEXPIN=?"
                        + ",UPSSTATUS=?"
                        + ",UPSISSDT=?"
                        + ",UPSISSTM=?"
                        + ",UPSEXPDT=?"
                        + ",UPSEXPTM=?"
                        + "where UPSSHPR = ? ");
                ps.setString(1, this.issAt);
                ps.setString(2, this.tokenTp);
                ps.setString(3, this.clientId);
                ps.setString(4,this.token);
                ps.setLong(5, expIn);
                ps.setString(6, this.status);
                ps.setBigDecimal(7, this.issDate);
                ps.setBigDecimal(8,this.issTime);
                ps.setBigDecimal(9, this.expDate);
                ps.setBigDecimal(10,this.expTime);
                ps.setString(11,this.shipper);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UPSAuth.class.getName()).log(Level.SEVERE, null, ex);
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
                        + ",UPSEXPTM) "
                        + "Values(?,?,?,?,?,?,?,?,?,?,?)" );
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
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(UPSAuth.class.getName()).log(Level.SEVERE, null, ex);
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

}
