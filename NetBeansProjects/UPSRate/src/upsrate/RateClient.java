/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package upsrate;

import com.a4.utils.ConnectAs400;
import static com.a4.utils.ConnectAs400.setJtopen;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RateInfo;
import model.RateParm;
import model.UPSParms;

/**
 *
 * @author Khatchik
 */
public class RateClient {

    private UPSParms upsParms = new UPSParms();
    private final Logger logger;
    private RateParm rateParm; 
    private ConnectAs400.As400JobInfo as400Info;
    private RateInfo rateInfo = new RateInfo();
    private RateInfo GetUpsRate;

    public RateClient(String parmFile) {
        XMLDecoder decoder;
        try {
            decoder = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(parmFile)));
            upsParms = (UPSParms) decoder.readObject();
        } catch (Exception ex) {
            Logger.getLogger(RateClient.class.getName()).log(Level.SEVERE, null, ex);
            upsParms.setAs400Lib("ACSDBASE");
            upsParms.setAs400Password("ACSINC");
            upsParms.setAs400UserId("ACSINC");
            upsParms.setAs400SystemName("AS400.A4.COM");
            upsParms.setLogLocation("c:\\users\\khatchik");
            upsParms.setUpsServer("https://onlinetools.ups.com");
            upsParms.setUpsAutPath("/security/v1/oauth/token");
            upsParms.setUpsClientId("KQf9GTDU0S83qGFqgpmgdINvy34kU8dIvWVbmVPdbb9MmQnd");
            upsParms.setUpsSecCd("upsSecCd");
            upsParms.setUpsRatePath("/api/rating/v2403/Rate");
            upsParms.setEnvironment("Windows");
            upsParms.setUpsShprNo("928946");
            upsParms.setDebug(true);
            saveParmFile(parmFile, upsParms);

        }
        //  logger = Logger.getLogger("UPSRATE_LOG");
        String loggerFile = upsParms.getEnvironment().equals("AS400") ? upsParms.getLogLocation() + "/upsRate_log.xml"
                : upsParms.getLogLocation() + "\\upsRate_log.xml";

        logger = Logger.getLogger(loggerFile);
        try {
            logger.addHandler(new FileHandler(loggerFile, 50000, 10, false));

        } catch (IOException | SecurityException ex) {
            Logger.getLogger(RateClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            setJtopen();
            ConnectAs400.setConParms(upsParms.getAs400SystemName(), upsParms.getAs400UserId(), upsParms.getAs400Password(),
                    upsParms.getAs400Lib());

            try {
                ConnectAs400.conAs400();
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.log(Level.SEVERE, "Error Creating AS400 Connection {0} ",
                        new Object[]{ex.getMessage()});
                return;
            }

            as400Info = ConnectAs400.getAs400JobInfo();
            //System.out.println("AS400 Job = " + as400Info.getJobnNum().trim()
            //        + "/" + as400Info.getJobUser().trim() + "/" + as400Info.getJobName());
            if (upsParms.isDebug()) {
                logger.log(Level.INFO, "AS400 JOB = " + as400Info.getJobnNum().trim()
                        + "/" + as400Info.getJobUser().trim() + "/" + as400Info.getJobName()
                );
            }
            //System.out.println("job="+ as400Info.getJobName());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error retreiving AS400 Job info " + ex.getLocalizedMessage());
        }

        //As400UPSAuth as400Ups = new As400UPSAuth(as400Info., upsParms);
            this.rateParm = new RateParm(logger); 
    }


    public void setRateData(
            //String shprNo,
            String shprName,
            String shprAddr1,
            String shprAddr2,
            String shprAddr3,
            String shprCty,
            String shprStt,
            String shprZip,
            String shprCntry,
            String fromName,
            String fromAddr1,
            String fromAddr2,
            String fromAddr3,
            String fromCty,
            String fromStt,
            String fromZip,
            String fromCntry,
            String fromPhon,
            String fromFax,
            String toName,
            String toAddr1,
            String toAddr2,
            String toAddr3,
            String toCity,
            String toStt,
            String toZip,
            String toCntry,
            String toPhon,
            String toFax,
            String pickupType // '01'
            ,
             String svcCode //Service code
            ,
             String svcCodeDesc
    //String numPieces
    ) {
        rateParm.setShprNo(upsParms.getUpsShprNo());
        rateParm.setShprName(shprName);
        rateParm.setShprAddr1(shprAddr1);
        rateParm.setShprAddr2(shprAddr2);
        rateParm.setShprAddr3(shprAddr3);
        rateParm.setShprCty(shprCty);
        rateParm.setShprStt(shprStt);
        rateParm.setShprZip(shprZip);
        rateParm.setShprCntry(shprCntry);
        rateParm.setFromName(fromName);
        rateParm.setFromAddr1(fromAddr1);
        rateParm.setFromAddr2(fromAddr2);
        rateParm.setFromAddr3(fromAddr3);
        rateParm.setFromCty(fromCty);
        rateParm.setFromStt(fromStt);
        rateParm.setFromZip(fromZip);
        rateParm.setFromCntry(fromCntry);
        rateParm.setFromPhon(fromPhon);
        rateParm.setFromFax(fromFax);
        rateParm.setToName(toName);
        rateParm.setToAddr1(toAddr1);
        rateParm.setToAddr2(toAddr2);
        rateParm.setToAddr3(toAddr3);
        rateParm.setToCity(toCity);
        rateParm.setToStt(toStt);
        rateParm.setToZip(toZip);
        rateParm.setToCntry(toCntry);
        rateParm.setToPhon(toPhon);
        rateParm.setToFax(toFax);
        rateParm.setPickupType(pickupType);
        rateParm.setSvcCode(svcCode);
        rateParm.setSvcCodeDesc(svcCodeDesc);
        //rateParm.setNumPieces(numPieces);

    }

    public void setPkgData(String pkgTyp,
            String pkgDesc,
            String pkgDimUom,
            String pkgDimUomDes,
            String len,
            String width,
            String height,
            String weightUom,
            String weightUomDes,
            String weight) {

        //rateParm.newPackage(pkgTyp, pkgDesc, "", "", "", "", "", weightUom, weightUomDes, weight);
        rateParm.newPackage(pkgTyp, pkgDesc, pkgDimUom, pkgDimUomDes, len, width, height, weightUom, weightUomDes, weight);

    }

    public String callUpsRate() {
        As400UPSAuth as400Auth = new As400UPSAuth(ConnectAs400.getConnection(), upsParms,logger);
        String expDate = as400Auth.getExpDate().toString();
        String expTime = as400Auth.getExpTime().toString();
        if (expTime.length() < 6) {
            expTime = "0" + expTime;
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        int year = Integer.parseInt(expDate.substring(0, 4));
        int month = Integer.parseInt(expDate.substring(4, 6));
        int day = Integer.parseInt(expDate.substring(6, 8));
        int hh = Integer.parseInt(expTime.substring(0, 2));
        int mm = Integer.parseInt(expTime.substring(2, 4));
        int ss = Integer.parseInt(expTime.substring(4, 6));

        LocalDateTime expDatTime = LocalDateTime.of(year,
                month,
                day,
                hh,
                mm,
                ss
        );
        if (currentDateTime.isAfter(expDatTime)) {
            GetAuth auth = new GetAuth(upsParms, as400Auth, logger);
            auth.getAuth();
        }

        GetUpsRate getUpsRate = new GetUpsRate(upsParms, as400Auth, rateParm, logger);
        rateInfo = getUpsRate.rtvUPSRate();
        //System.out.println("rate info =" + rateInfo);
        return rateInfo.toString();

    }

    public String getUpsRate() {
        return rateInfo.getTotchg();
    }

    private void saveParmFile(String parmFile, UPSParms parm) {
        XMLEncoder encoder;
        try {
            encoder = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(parmFile)));
            encoder.writeObject(parm);
            encoder.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
