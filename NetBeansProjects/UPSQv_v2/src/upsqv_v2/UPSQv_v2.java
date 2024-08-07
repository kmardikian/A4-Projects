/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package upsqv_v2;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khatchik
 */
public class UPSQv_v2 {
    
    static final String dft_parm = "QVparms2.xml";
    static String parmFile;
    private static QVParms parm;
     

    public static void main(String[] args)  {
        
        Logger logger;
        if (args.length > 0 ) {
            parmFile = args[0];
        } else {
            parmFile = dft_parm;
        }
        parm = new QVParms();
        System.out.println("num args = " + args.length + " parmFile =" + parmFile);

        XMLDecoder decoder;
        try {
            decoder = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(parmFile)));
            parm = (QVParms) decoder.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UPSQv_v2.class.getName()).log(Level.SEVERE, null, ex);
            //parm.setQvUrl("https://wwwcie.ups.com/api/quantumview/V2/events");
            parm.setQvLicense("4D08F7169B6154E6");
            parm.setQvUserId("erindiebold3");
            parm.setQvPassWord("A4MoshayPa$$");
            parm.setAs400SystemName("AS400.A4.COM");
            parm.setAs400UserId("KHATCHIK");
            parm.setAs400Password("FEBY2K3");
            parm.setAs400Lib("UPS");
            parm.setUpsAutPath("/security/v1/oauth/token");
            parm.setUpsSecCd("vG9gFehHKtxlHogGa7sDS7XLprwgBAYaaVTXTcqjXoGZacZNRCJYchJK1WjBzGkg");
            parm.setUpsClientId("KQf9GTDU0S83qGFqgpmgdINvy34kU8dIvWVbmVPdbb9MmQnd");
            parm.setUpsShprNo("928946");
            parm.setEnvironment("Windows");
            parm.setUpsServer("https://onlinetools.ups.com");
            parm.setQvPath("/api/quantumview/V2/events");
            parm.setLogLocation("c:\\users\\khatchik");
            parm.setAs400SecLib("ACSDBASE");
        }
        if (args.length > 1) {
            parm.setStartDt(args[1]);
        }else {
            parm.setStartDt(" ");
        }
        if  (args.length > 2 ) {
            parm.setEndDt(args[2]);
        } else {
            parm.setEndDt(" ");
        }
        if (parm.getEnvironment() == null ) {
            parm.setEndDt("Windows");
        }
        
       // parm.setUpsClientId("YK9VsUYvRAlFecREGzXnkx4H1GTbYF4ryh4b4Q4fE5uJ7qXA");
       String loggerFile = parm.getEnvironment().equals("AS400") ? parm.getLogLocation() + "/UPSQv_V2_log.xml"
                : parm.getLogLocation() + "\\UPSQv_V2_log.xml";
       
       logger = Logger.getLogger(loggerFile);
       try {
            logger.addHandler(new FileHandler(loggerFile + ".xml", 50000, 10, false));

        } catch (IOException | SecurityException ex) {
            Logger.getLogger(UPSQv_v2.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       QVGetData qvGetData = new QVGetData(parm,logger);
        qvGetData.qvPrcQv();
        
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
