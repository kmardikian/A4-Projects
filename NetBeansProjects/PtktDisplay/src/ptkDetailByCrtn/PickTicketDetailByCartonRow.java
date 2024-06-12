/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptkDetailByCrtn;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import ptktData.PtkCarton;
import ptktDetailDisplay.PtktDetailRow;

/**
 *
 * @author khatchik
 */
public class PickTicketDetailByCartonRow {

    private final SimpleDoubleProperty crtnNo;      // carton #
    private final SimpleStringProperty ptktNo;      // pick ticket
    private final SimpleStringProperty ordNo;       // order#
    private final SimpleStringProperty soldTo;      // sold-to
    private final SimpleStringProperty shipTo;      // ship to
    private final SimpleStringProperty sVia;        // ship via
    private final SimpleStringProperty whse;        // warehouse
    private final SimpleDoubleProperty units;       // units in carton
    private final SimpleDoubleProperty dollars;     // dollars in carton
    private final SimpleStringProperty status;      // carton status 
    private final SimpleStringProperty ctnStat;
    private final SimpleStringProperty operator;
    private final SimpleDoubleProperty prtDate;
    private final SimpleDoubleProperty prtTime;
    private final SimpleDoubleProperty orStrDt;
    private final SimpleDoubleProperty orCmpDt;
    private final SimpleStringProperty cusName;
    private final SimpleStringProperty shpName;
    private final SimpleStringProperty ordType;
    private final SimpleDoubleProperty stgSDate;
    private final SimpleDoubleProperty stgSTime;
    private final SimpleStringProperty stgDatTm;
    private final SimpleDoubleProperty stgCmpDat;
    private final SimpleDoubleProperty stgCmpTim;
    private final SimpleIntegerProperty totSku;
    private final SimpleLongProperty dur;
    private final SimpleStringProperty poNo;
    private final SimpleStringProperty webOrd;
    private final String sDur; 
    

    public PickTicketDetailByCartonRow(PtkCarton ptkCtn) {
        DecimalFormat dat8 = new DecimalFormat("00000000");
        DecimalFormat tim6 = new DecimalFormat("000000");
        this.crtnNo = new SimpleDoubleProperty(ptkCtn.getCrtnNo().doubleValue());
        this.ptktNo = new SimpleStringProperty(ptkCtn.getPtktNo().toString());
        this.ordNo = new SimpleStringProperty(ptkCtn.getOrdNo().toString());
        this.soldTo = new SimpleStringProperty(ptkCtn.getSoldTo());
        this.shipTo = new SimpleStringProperty(ptkCtn.getShipTo());
        this.sVia = new SimpleStringProperty(ptkCtn.getsVia());
        this.whse = new SimpleStringProperty(ptkCtn.getWhse());
        this.units = new SimpleDoubleProperty(ptkCtn.getTotu().doubleValue());
        this.dollars = new SimpleDoubleProperty(ptkCtn.getTotd().doubleValue());
        this.status = new SimpleStringProperty(ptkCtn.getStatus());
        this.ctnStat = new SimpleStringProperty(ptkCtn.getCtnStat());
        this.operator = new SimpleStringProperty(ptkCtn.getCrtOpr());
        this.prtDate = new SimpleDoubleProperty(ptkCtn.getPrtDate());
        this.prtTime = new SimpleDoubleProperty(ptkCtn.getPrtTime());
        this.orStrDt = new SimpleDoubleProperty(ptkCtn.getOrStrDt().doubleValue());
        this.orCmpDt = new SimpleDoubleProperty(ptkCtn.getOrCmpDt().doubleValue());
        this.cusName = new SimpleStringProperty(ptkCtn.getCusName());
        this.shpName = new SimpleStringProperty(ptkCtn.getShpToNam());
        this.ordType = new SimpleStringProperty(ptkCtn.getOrdTyp());
        this.stgSDate = new SimpleDoubleProperty(ptkCtn.getStgSDat().doubleValue());    // stage start date
        this.stgSTime = new SimpleDoubleProperty(ptkCtn.getStgSTim().doubleValue());    // stage start time
        this.stgCmpDat = new SimpleDoubleProperty(ptkCtn.getStgEDat().doubleValue());
        this.stgCmpTim = new SimpleDoubleProperty(ptkCtn.getStgETim().doubleValue());
        String sStgDat = dat8.format(ptkCtn.getStgSDat());
        String sStgTim = tim6.format(ptkCtn.getStgSTim());
        this.stgDatTm = new SimpleStringProperty(sStgDat + sStgTim);
        this.poNo = new SimpleStringProperty(ptkCtn.getPoNo());
        this.webOrd = new SimpleStringProperty(ptkCtn.getWebOrd());
        
        //this.stgDatTm = new SimpleStringProperty(dat8.format(ptkCtn.getStgSDat()
        //        + tim6.format(ptkCtn.getStgSTim())));
        this.totSku = new SimpleIntegerProperty(ptkCtn.getSkuCnt());
        this.dur = new SimpleLongProperty(getTimDif());  // duration in minutes
        Long  days = this.dur.getValue() / 1440;  // (60 * 24)
        int minRmn = (int) (this.dur.getValue() % 1440 ); 
        int hrs = minRmn / 60;
        int min = minRmn % 60;
        
       // long hrs = (long) dur.getValue() / 3600;
        //            int min = (int) (dur.getValue() % 3600) / 60;
        if (days > 0) {
            this.sDur= String.format("%d days %d Hrs %d Min",days, hrs, min);
        } else {
        this.sDur= String.format("%d Hrs %d Min", hrs, min);
        }
    
    }

    public SimpleDoubleProperty getCrtnNo() {
        return crtnNo;
    }

    public SimpleStringProperty getPtktNo() {
        return ptktNo;
    }

    public SimpleStringProperty getOrdNo() {
        return ordNo;
    }

    public SimpleStringProperty getSoldTo() {
        return soldTo;
    }

    public SimpleStringProperty getShipTo() {
        return shipTo;
    }

    public SimpleStringProperty getsVia() {
        return sVia;
    }

    public SimpleStringProperty getWhse() {
        return whse;
    }

    public SimpleDoubleProperty getUnits() {
        return units;
    }

    public SimpleDoubleProperty getDollars() {
        return dollars;
    }

    public SimpleStringProperty getStatus() {
        return status;
    }

    public SimpleStringProperty getOperator() {
        return operator;
    }

    public SimpleDoubleProperty getPrtDate() {
        return prtDate;
    }

    public SimpleDoubleProperty getPrtTime() {
        return prtTime;
    }

    public SimpleDoubleProperty getOrStrDt() {
        return orStrDt;
    }

    public SimpleDoubleProperty getOrCmpDt() {
        return orCmpDt;
    }

    public SimpleStringProperty getCusName() {
        return cusName;
    }

    public SimpleStringProperty getShpName() {
        return shpName;
    }

    public SimpleStringProperty getOrdType() {
        return ordType;
    }

    public SimpleDoubleProperty getStgSDate() {
        return stgSDate;
    }

    public SimpleDoubleProperty getStgSTime() {
        return stgSTime;
    }

    public SimpleStringProperty getStgDatTm() {
        return stgDatTm;
    }

    public SimpleIntegerProperty getTotSku() {
        return totSku;
    }
/**
 * 
 * @return duration in seconds  
 */
    public SimpleLongProperty getDur() {
        return dur;
    }
/**
 * 
 * @return Carton Status 
 */
    public SimpleStringProperty getCtnStat() {
        return ctnStat;
    }
/**
 * 
 * @return carton Complete Date 
 */
    public SimpleDoubleProperty getStgCmpDat() {
        return stgCmpDat;
    }
    /** 
     * 
     * @return Carton Complete Time 
     */
    public SimpleDoubleProperty getStgCmpTim() {
        return stgCmpTim;
    }

    public SimpleStringProperty getPoNo() {
        return poNo;
    }

    
    public SimpleStringProperty getWebOrd() {
        return webOrd;
    }
    

    public String getsDur() {
        return sDur;
    }
    
    
    
    public Long getTimDif() {
        SimpleStringProperty datTimDif;
        //SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
        DateTimeFormatter fmtDatTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime curDat = LocalDateTime.now();
        LocalDateTime stgStr = LocalDateTime.parse(this.stgDatTm.getValue() ,fmtDatTime);
        long minutes = stgStr.until( curDat, ChronoUnit.MINUTES);
        
//        Date strtDat;
//        Date curDat = new Date();
//        try {
//            strtDat = sdf.parse(stgDatTm.getValue());
//        } catch (ParseException ex) {
//            Logger.getLogger(PtktDetailRow.class.getName()).log(Level.SEVERE, null, ex);
//            strtDat = curDat;
//        }
//        long timDif = curDat.getTime() - strtDat.getTime();
//        long seconds = (long) timDif / 1000;
        
                
//        return seconds; 
        return minutes;
    }
    public String getTimDifStr() {
        return this.sDur;
    }


}
