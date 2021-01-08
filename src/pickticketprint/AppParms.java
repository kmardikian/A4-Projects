/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatchik
 */
public class AppParms {

    private static Logger logger;
    private static String parmFile; 

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger inLogger) {
        logger = inLogger;
    }

    public static String getParmFile() {
        return parmFile;
    }

    public static void setParmFile(String parmFile) {
        AppParms.parmFile = parmFile;
    }
    public static void writeParmFile(UpsShprParms params) {
        XMLEncoder encoder;

        try {
            System.out.println("saving parms    ");
            encoder = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(parmFile)));
            encoder.writeObject(params);
            encoder.close();
        } catch (Exception ex) {
            AppParms.getLogger().log(Level.SEVERE, null, ex.getLocalizedMessage());
        }
    }
}
