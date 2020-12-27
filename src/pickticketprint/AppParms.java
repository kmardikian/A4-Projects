/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

import java.util.logging.Logger;

/**
 *
 * @author khatchik
 */
public class AppParms {

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger inLogger) {
        logger = inLogger;
    }

}
