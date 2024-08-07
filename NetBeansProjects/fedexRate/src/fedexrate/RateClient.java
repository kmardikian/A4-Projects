/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fedexrate;

//import com.a4.utils.ConnectAs400;
//import static com.a4.utils.ConnectAs400.setJtopen;
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
import model.FXParms;
import fedexshipping.FxAppParms;
import fedexshipping.GetAuth;
import java.math.BigDecimal;
import model.RateInfo;
import models.FedexShipmentRequest;
import models.PickupTypes;
import models.WeightUnits;
//import model.UPSParms;

/**
 *
 * @author Khatchik
 */
public class RateClient {

    private FXParms fxParms = new FXParms();
    private final Logger logger;
    private FedexShipmentRequest rateParm = new FedexShipmentRequest();
    // private ConnectAs400.As400JobInfo as400Info;
    private RateInfo rateInfo = new RateInfo();
    //private RateInfo GetUpsRate;
    private final FxAppParms shprAuthParms = new FxAppParms();

    public RateClient(String parmFile) {
        XMLDecoder decoder;
        try {
            decoder = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(parmFile)));
            fxParms = (FXParms) decoder.readObject();
        } catch (Exception ex) {
            Logger.getLogger(RateClient.class.getName()).log(Level.SEVERE, null, ex);
//            fxParms.setAs400Lib("ACSDBASE");
//            fxParms.setAs400Password("ACSINC");
//            fxParms.setAs400UserId("ACSINC");
//            fxParms.setAs400SystemName("AS400.A4.COM");
            fxParms.setLogLocation("c:\\users\\khatchik");

            fxParms.setFxServer("https://apis.fedex.com");
            fxParms.setFxAutPath("/oauth/token");
            fxParms.setFxClientId("l7e231abde67a444d9b209ea334eb13f40");
            fxParms.setFxSecCd("73e166edc3af40de8d21518cf47aa902");
            fxParms.setFxRatePath("/rate/v1/rates/quotes");
            fxParms.setAddrValPath("/address/v1/addresses/resolve");
            fxParms.setEnvironment("Windows");
            fxParms.setFxShprNo("203549279");
            fxParms.setDebug(true);
            saveParmFile(parmFile, fxParms);

        }
        //  logger = Logger.getLogger("UPSRATE_LOG");
        String loggerFile = fxParms.getEnvironment().equals("AS400") ? fxParms.getLogLocation() + "/upsRate_log.xml"
                : fxParms.getLogLocation() + "\\upsRate_log.xml";

        //System.out.println("logFile = " + loggerFile);
        logger = Logger.getLogger(loggerFile);
        try {
            logger.addHandler(new FileHandler(loggerFile, 50000, 10, false));

        } catch (IOException | SecurityException ex) {
            Logger.getLogger(RateClient.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try {
//            setJtopen();
//            ConnectAs400.setConParms(fxParms.getAs400SystemName(), fxParms.getAs400UserId(), fxParms.getAs400Password(),
//                    fxParms.getAs400Lib());
//
//            try {
//                ConnectAs400.conAs400();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                logger.log(Level.SEVERE, "Error Creating AS400 Connection {0} ",
//                        new Object[]{ex.getMessage()});
//                return;
//            }
//
//            as400Info = ConnectAs400.getAs400JobInfo();
//            //System.out.println("AS400 Job = " + as400Info.getJobnNum().trim()
//            //        + "/" + as400Info.getJobUser().trim() + "/" + as400Info.getJobName());
//            if (fxParms.isDebug()) {
//                logger.log(Level.INFO, "AS400 JOB = " + as400Info.getJobnNum().trim()
//                        + "/" + as400Info.getJobUser().trim() + "/" + as400Info.getJobName()
//                );
//            }
//            //System.out.println("job="+ as400Info.getJobName());
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, "Error retreiving AS400 Job info " + ex.getLocalizedMessage());
//        }
//
//        //As400UPSAuth as400Ups = new As400UPSAuth(as400Info., fxParms);
//            this.rateParm = new RateParm(logger); 
    }

    public void setRateData(
            //String shprNo,
            String fromName,
            String fromAddr1,
            String fromAddr2,
            String fromAddr3,
            String fromCty,
            String fromStt,
            String fromZip,
            String fromCntry,
            String toName,
            String toAddr1,
            String toAddr2,
            String toAddr3,
            String toCity,
            String toStt,
            String toZip,
            String toCntry,
            String pickupType,
             String svcCode //Service code
            ,String weight,
             String weightUom
    //String numPieces
    ) {
        //rateParm.setShprNo(fxParms.getFxShprNo());
        //
        if (fxParms.isDebug()) {
            logger.log(Level.INFO, "from name ={0} fromAddr1 ={1} fromCty ={2} "
                    + "fromstt ={3} fromZip= {4} fromCntry={5} toName={6} "
                    + "toAddr1 ={7} toCity ={8} toStt ={9} toZip= {10} toCntry={11} "
                    + "svcCode={12} weight={13} weightUom={14}",
                     new Object[]{fromName, fromAddr1, fromCty, fromStt ,fromZip
                     ,fromCntry,toName, toAddr1, toCity, toStt, toZip, toCntry
                             , svcCode, weight, weightUom});
        }
        //System.out.println("weight = " + weight.trim());
        rateParm.setShipperName(fromName.trim());
        rateParm.getShipperAdr().add(fromAddr1.trim());
        rateParm.getShipperAdr().add(fromAddr2.trim());
        rateParm.getShipperAdr().add(fromAddr3.trim());
        rateParm.setShipperCity(fromCty.trim());
        rateParm.setShipperStat(fromStt.trim());
        rateParm.setShipperZip(fromZip.trim());
        rateParm.setShipperCountry(fromCntry.trim());

        rateParm.setRecName(toName.trim());
        rateParm.getRecAdr().add(toAddr1.trim());
        rateParm.getRecAdr().add(toAddr2.trim());
        rateParm.getRecAdr().add(toAddr3.trim());
        rateParm.setRecCity(toCity.trim());
        rateParm.setRecStat(toStt.trim());
        rateParm.setRecZip(toZip.trim());
        rateParm.setRecCountry(toCntry.trim());
        rateParm.setPickupType(PickupTypes.valueOf(pickupType.trim()));
        rateParm.setSrvTyp(svcCode.trim());
        BigDecimal bdWeight = new BigDecimal(weight.trim());
        rateParm.setPkgWeight(bdWeight, WeightUnits.valueOf(weightUom.trim()));

        //rateParm.setNumPieces(numPieces);
    }

//    public void setPkgData(String pkgTyp,
//            String pkgDesc,
//            String pkgDimUom,
//            String pkgDimUomDes,
//            String len,
//            String width,
//            String height,
//            String weightUom,
//            String weightUomDes,
//            String weight) {
//
//        //rateParm.newPackage(pkgTyp, pkgDesc, "", "", "", "", "", weightUom, weightUomDes, weight);
//        rateParm.newPackage(pkgTyp, pkgDesc, pkgDimUom, pkgDimUomDes, len, width, height, weightUom, weightUomDes, weight);
//
//    }
    public String callRate() {
//        As400UPSAuth as400Auth = new As400UPSAuth(ConnectAs400.getConnection(), fxParms,logger);
//        String expDate = as400Auth.getExpDate().toString();
//        String expTime = as400Auth.getExpTime().toString();
//        if (expTime.length() < 6) {
//            expTime = "0" + expTime;
//        }
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        int year = Integer.parseInt(expDate.substring(0, 4));
//        int month = Integer.parseInt(expDate.substring(4, 6));
//        int day = Integer.parseInt(expDate.substring(6, 8));
//        int hh = Integer.parseInt(expTime.substring(0, 2));
//        int mm = Integer.parseInt(expTime.substring(2, 4));
//        int ss = Integer.parseInt(expTime.substring(4, 6));
//
//        LocalDateTime expDatTime = LocalDateTime.of(year,
//                month,
//                day,
//                hh,
//                mm,
//                ss
//        );
//        if (currentDateTime.isAfter(expDatTime)) {
        //      }

        shprAuthParms.setServer(fxParms.getFxServer().trim());
        shprAuthParms.setAuthPath(fxParms.getFxAutPath().trim());
        shprAuthParms.setClientId(fxParms.getFxClientId().trim());
        shprAuthParms.setClientSecret(fxParms.getFxSecCd().trim());
        shprAuthParms.setAddrValPath(fxParms.getAddrValPath().trim());
        shprAuthParms.setDebug(fxParms.isDebug());
        GetAuth fxAuth = new GetAuth(shprAuthParms, logger);
        fxAuth.clientHttpCall();

        FedexRate fedexRate = new FedexRate(fxAuth, shprAuthParms, rateParm, fxParms, logger);

        rateInfo = fedexRate.rtvRate();
       // logger.log(Level.INFO,"returned from fedexRate");
       // System.out.println("rate info =" + rateInfo);
        return rateInfo.toString();

    }

    public String getRate() {
        return rateInfo.getTotchg();
    }

    private void saveParmFile(String parmFile, FXParms parm) {
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
