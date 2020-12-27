/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

/**
 *
 * @author khatchik
 */
public class PurgeOldPtkt {

    Worker<String> worker;
    private UpsShprParms upsShprParms;
    private String ptktFolder;
    private int ptkRtnDays;

    public PurgeOldPtkt() {

        worker = new Task<String>() {
            @Override
            protected String call() throws Exception {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                Calendar curDate = Calendar.getInstance();
                Calendar priorDate;
                priorDate = curDate;
                priorDate.add(Calendar.DATE, (0 - upsShprParms.getPtktRtnDays()));

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.US);
//                Date dPrDat = priorDate.getTime();
//
                long ms = priorDate.getTimeInMillis();
//                System.out.println("prior date is :" + format.format(dPrDat) + "\n MS =" + ms);

                File ptktFldr = new File(upsShprParms.getPdfLoc());
                File[] pdfFiles = ptktFldr.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

                Path path;
                BasicFileAttributes atr;

                for (File fl : pdfFiles) {
                    path = Paths.get(fl.getPath());
                    atr = Files.readAttributes(path, BasicFileAttributes.class);
                    long fMs = atr.creationTime().toMillis();
//                    System.out.println("file " + fl.getName() + "crt date " + 
//                                atr.creationTime().toString() + format.format(atr.creationTime().toMillis()) + "Fms ="+ fMs  );  
                    
                    if (atr.creationTime().toMillis() < ms) {
//                        long fMs = atr.creationTime().toMillis();
//                        System.out.println("file " + fl.getName() + "crt date " + 
//                                atr.creationTime().toString() + format.format(atr.creationTime().toMillis()) + "Fms ="+ fMs  );     
                        fl.delete();
                    }
                }

                return " ";
            }
        };
    }

    public void setPtktFolder(String ptktFolder) {
        this.ptktFolder = ptktFolder;
    }

    public void setPtkRtnDays(int ptkRtnDays) {
        this.ptkRtnDays = ptkRtnDays;
    }

    public Worker<String> getWorker() {
        return this.worker;
    }

    public void setWorker(Worker<String> worker) {
        this.worker = worker;
    }

    public void setUpsShprParms(UpsShprParms upsShprParms) {
        this.upsShprParms = upsShprParms;
    }

}
