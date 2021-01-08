/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template raFileLock, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

import com.a4.utils.ConnectAs400;
import static com.a4.utils.ConnectAs400.setJtopen;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Khatchik
 */
public class PickTicketPrint extends Application {

    static final String dfltParm = "SHparms.xml";
    UpsShprParms parm = new UpsShprParms();
    ConnectAs400 connAs400;
    PtktMonMainController ptktMainCntl;
    private String parmFile;
    private Logger logger;
    RandomAccessFile raFileLock = null; // The raFileLock we'll lock
    FileLock filLock = null; // The lock object we hold

    @Override
    public void start(Stage primaryStage) {

        Map<String, String> namedParms;
        namedParms = getParameters().getNamed();
        parmFile = namedParms.getOrDefault("parm", dfltParm);
        AppParms.setParmFile(parmFile);

        XMLDecoder decoder;
        try {
            decoder = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(parmFile)));
            parm = (UpsShprParms) decoder.readObject();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            //parm.setShUrl("https://wwwcie.ups.com/rest/FreightShip");
            parm.setShUrl("https://wwwcie.ups.com/rest/Ship");
            parm.setVoidUrl("https://wwwcie.ups.com/rest/Void");
            parm.setShLicense("3D362A39E035F31D");
            //parm.setShLicense("4D08F7169B6154E6");
            // additional access key 3D362A39E035F31D
            parm.setShUserId("erindiebold2");
            parm.setShPassWord("Tanman96!");
            parm.setAs400SystemName("10.100.100.1");
            parm.setAs400UserId("ACSINC");
            parm.setAs400Password("ACSINC");
            parm.setAs400Lib("TSTDBASE");
            parm.setUpsLblLoc("C:\\Users\\Khatchik\\Dropbox\\Folder shared with khatchik\\UpsShipper\\Labels");
            parm.setCmpLogoLoc("c:\\users\\khatchik");
            parm.setPrinterName("CANON UPSTAIRS");
            parm.setJasperLoc("C:\\Users\\Khatchik\\JaspersoftWorkspace\\MyReports");
            parm.setUpsExpLib("TSTDBASE");
            parm.setPtktRtnDays(90);
            AppParms.writeParmFile(parm);
        }

      
        if (namedParms.containsKey("wrh")) {
            parm.setWhse(namedParms.get("wrh"));
        }

        if (namedParms.containsKey("pdfLoc")) {
            parm.setPdfLoc(namedParms.get("pdfLoc"));
        }

        if (namedParms.containsKey("printerName")) {
            parm.setPrinterName(namedParms.get("printerName"));
        }
        if (namedParms.containsKey("printerNameRprt")) {
            parm.setPrinterNameRptr(namedParms.get("printerNameRprt"));
        }

        logger = Logger.getLogger("PtktPrt_" + parm.getWhse());
        try {
            logger.addHandler(new FileHandler("PtktPrtLog_" + parm.getWhse() + ".xml", 50000, 1, false));
            AppParms.setLogger(logger);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(PickTicketPrint.class.getName()).log(Level.SEVERE, null, ex);
        }

        setJtopen();
        ConnectAs400.setConParms(parm.getAs400SystemName(), parm.getAs400UserId(), parm.getAs400Password(),
                parm.getAs400Lib());
        try {
            ConnectAs400.conAs400();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error Connecting to AS400\n" + ex.getLocalizedMessage());
            alert.setTitle("Error Connecting to AS400");
            alert.showAndWait();
            ex.printStackTrace();
            AppParms.getLogger().log(Level.SEVERE, null, ex);
            return;
        }

        ConnectAs400.As400JobInfo as400Info = new ConnectAs400.As400JobInfo();
        try {

            as400Info = ConnectAs400.getAs400JobInfo();
            //System.out.println("job="+ as400Info.getJobName());
        } catch (SQLException ex) {
            AppParms.getLogger().log(Level.SEVERE, "Error retreiving AS400 Job info " + ex.getLocalizedMessage());
        }
        if (lockFile() == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Another Instance Running for this whse\n");
            alert.setTitle("Another Instance running");
            alert.showAndWait();
            AppParms.getLogger().log(Level.SEVERE, null, "Another instance running for this whse");
            return;
        }
        AppParms.getLogger().log(Level.INFO, "Successfly signon to as400 \n JobName="
                + as400Info.getJobnNum().trim() + "/" + as400Info.getJobUser().trim() + "/"
                + as400Info.getJobName());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PtktMonMain.fxml"));

        Parent root;
        try {
            root = (Parent) fxmlLoader.load();
            //   } catch (IOException ex) {
            //       Logger.getLogger(PickTicketPrint.class.getName()).log(Level.SEVERE, null, ex);
            //   }
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Pick Ticket Print Monitor");
            ptktMainCntl = fxmlLoader.getController();
            ptktMainCntl.setIntParms(parm, as400Info.getJobName().trim(), as400Info.getJobUser().trim(),
                    as400Info.getJobnNum(), primaryStage);
            ptktMainCntl.deleteOldPtkt();
            primaryStage.show();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void stop() {
        
//        XMLEncoder encoder;
//
//        try {
//            System.out.println("saving parms    ");
//            encoder = new XMLEncoder(
//                    new BufferedOutputStream(
//                            new FileOutputStream(parmFile)));
//            encoder.writeObject(parm);
//            encoder.close();
//        } catch (Exception ex) {
//            AppParms.getLogger().log(Level.SEVERE, null, ex.getLocalizedMessage());
//        }

        if (filLock != null && filLock.isValid()) {
            try {
                filLock.release();
            } catch (IOException ex) {
                Logger.getLogger(PickTicketPrint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (raFileLock != null) {
            try {
                raFileLock.close();
            } catch (IOException ex) {
                Logger.getLogger(PickTicketPrint.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private boolean lockFile() {

        boolean rtn = true;
        String fileLoc = parm.getPdfLoc();
        //String tmpDir = System.getProperty("java.io.tmpdir");
        //System.out.println("temp dir =" + tmpDir);

        FileChannel filChnl = null; // The channel to the raFileLock

        File lockFile = new File(fileLoc, ".lock" + parm.getWhse().trim());

        try {
            raFileLock = new RandomAccessFile(lockFile, "rw");
            filChnl = raFileLock.getChannel();
            filLock = filChnl.tryLock();
            if (filLock == null) {
                rtn = false;
            } else {
                lockFile.deleteOnExit();
            }
        } catch (FileNotFoundException ex) {
            AppParms.getLogger().log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            AppParms.getLogger().log(Level.SEVERE, null, ex);
        } finally {

        }

        return rtn;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
