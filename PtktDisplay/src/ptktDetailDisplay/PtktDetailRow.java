/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktDetailDisplay;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Khatchik
 */
public class PtktDetailRow {

    private final SimpleStringProperty ptktNo;
    private final SimpleStringProperty ordNo;
    private final SimpleStringProperty soldTo;
    private final SimpleStringProperty shipTo;
    private final SimpleStringProperty sVia;
    private final SimpleStringProperty whse;
    private final SimpleDoubleProperty units;
    private final SimpleDoubleProperty dollars;
    private final SimpleStringProperty status;
    private final SimpleStringProperty operator;
    private final SimpleStringProperty prtDate;
    private final SimpleStringProperty prtTime;
    private final SimpleStringProperty orStrDt;
    private final SimpleStringProperty orCmpDt;
    private final SimpleStringProperty cusName;
    private final SimpleStringProperty shpName;
    private final SimpleStringProperty ordType;
    private final SimpleStringProperty stgDate;
    private final SimpleStringProperty stgTime;
    private final SimpleStringProperty stgDatTm;
    private final SimpleIntegerProperty totCtn;

    public PtktDetailRow(BigDecimal ptktNo, BigDecimal ordNo, String soldTo,
            String shipTo, String svia, String whse, BigDecimal units, BigDecimal dollars, String status,
            String operator, Double prtDate, Double prtTime, BigDecimal orStrDt,
            BigDecimal orCmpDt, String cusName, String shpName, String ordType, 
            String stgDatTm, Integer totCtn) {

        DecimalFormat docFormater = new DecimalFormat("#######");
        DecimalFormat unitFormater = new DecimalFormat("#,###,##0");
        Locale enUSLocale
                = new Locale.Builder().setLanguage("en").setRegion("US").build();
        NumberFormat currencyFormatter
                = NumberFormat.getCurrencyInstance(enUSLocale);

        this.ptktNo = new SimpleStringProperty(docFormater.format(ptktNo.setScale(0)));
        this.ordNo = new SimpleStringProperty(docFormater.format(ordNo.setScale(0)));
        this.soldTo = new SimpleStringProperty(soldTo);
        this.shipTo = new SimpleStringProperty(shipTo);
        this.sVia = new SimpleStringProperty(svia);
        this.whse = new SimpleStringProperty(whse);
        //this.units = new SimpleStringProperty(unitFormater.format(units));
        this.units = new SimpleDoubleProperty(units.doubleValue());
        // this.dollars = new SimpleStringProperty(currencyFormatter.format(dollars));
        this.dollars = new SimpleDoubleProperty(dollars.doubleValue());
        this.status = new SimpleStringProperty(status);
        this.operator = new SimpleStringProperty(operator);
        DecimalFormat dateFmt = new DecimalFormat("00000000");
        String datStr = dateFmt.format(prtDate);
        this.prtDate = new SimpleStringProperty(datStr.substring(4, 6) + '/'
                + datStr.substring(6, 8) + '/'
                + datStr.substring(0, 4)
        );
        //DecimalFormat dateFmt =  new DecimalFormat("####/##/##");
        DecimalFormat tmFmt = new DecimalFormat("##:##:##");
        //this.prtDate =  new SimpleStringProperty(dateFmt.format(prtDate));
        //this.prtTime = new SimpleStringProperty(tmFmt.format(prtTime));
        DecimalFormat timFmt = new DecimalFormat("000000");
        String timStr = timFmt.format(prtTime);
        this.prtTime = new SimpleStringProperty(timStr.substring(0, 2) + ':'
                + timStr.substring(2, 4) + ':'
                + timStr.substring(4, 6));
        //datStr = dateFmt.format(orStrDt);
        //this.orStrDt = new SimpleStringProperty(datStr.substring(4, 6) + '/'
        //        + datStr.substring(6, 8) + '/'
        //       + datStr.substring(0, 4));
        this.stgDate = new SimpleStringProperty(stgDatTm.substring(0, 4) 
                + stgDatTm.substring(4, 6) 
                + stgDatTm.substring(6, 8));
        this.stgTime = new SimpleStringProperty(stgDatTm.substring(8,10) 
        + stgDatTm.substring(10,12)  
        + stgDatTm.substring(12,14));
        this.stgDatTm = new SimpleStringProperty(stgDatTm);

        this.orStrDt = bigDec2String(orStrDt);
        this.orCmpDt = bigDec2String(orCmpDt);
        this.cusName = new SimpleStringProperty(cusName);
        this.shpName = new SimpleStringProperty(shpName);
        this.ordType = new SimpleStringProperty(ordType);
        LocalDate curDt = LocalDate.now();
        this.totCtn = new SimpleIntegerProperty(totCtn);
       // LocatDate stgDt = LocalDate()
    }

    private SimpleStringProperty bigDec2String(BigDecimal bdDate) {
        SimpleStringProperty ret;
        String wrk;
        DecimalFormat dateFmt = new DecimalFormat("00000000");
        wrk = dateFmt.format(bdDate);
        ret = new SimpleStringProperty(wrk.substring(4, 6) + '/'
                + wrk.substring(6, 8) + '/'
                + wrk.substring(0, 4));

        return ret;
    }

    /**
     * @return the ptktNo
     */
    public SimpleStringProperty getPtktNo() {
        return ptktNo;
    }

    /**
     * @return the ordNo
     */
    public SimpleStringProperty getOrdNo() {
        return ordNo;
    }

    /**
     * @return the soldTo
     */
    public SimpleStringProperty getSoldTo() {
        return soldTo;
    }

    /**
     * @return the shipTo
     */
    public SimpleStringProperty getShipTo() {
        return shipTo;
    }

    /**
     * @return the whse
     */
    public SimpleStringProperty getsVia() {
        return sVia;
    }

    public SimpleStringProperty getWhse() {
        return whse;
    }

    /**
     * @return the units
     */
    public SimpleDoubleProperty getUnits() {
        return units;
    }

    public SimpleDoubleProperty getDollars() {
        return dollars;
    }

    /**
     * @return the status
     */
    public SimpleStringProperty getStatus() {
        return status;
    }

    /**
     * @return the operator
     */
    public SimpleStringProperty getOperator() {
        return operator;
    }

    /**
     * @return the prtDate
     */
    public SimpleStringProperty getPrtDate() {
        return prtDate;
    }

    /**
     * @return the prtTime
     */
    public SimpleStringProperty getPrtTime() {
        return prtTime;
    }

    public SimpleStringProperty getOrStrDt() {
        return orStrDt;
    }

    public SimpleStringProperty getOrCmpDt() {
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

    public SimpleStringProperty getStgDate() {
        return stgDate;
    }

    public SimpleStringProperty getStgTime() {
        return stgTime;
    }

    public SimpleStringProperty getStgDatTm() {
        return stgDatTm;
    }

    public SimpleIntegerProperty getTotCtn() {
        return totCtn;
    }
        
    public SimpleStringProperty getTimDif() {
        SimpleStringProperty datTimDif;
        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss"); 
        Date strtDat;
        Date curDat = new Date();
        try {
            strtDat = sdf.parse(stgDatTm.getValue());
        } catch (ParseException ex) {
            Logger.getLogger(PtktDetailRow.class.getName()).log(Level.SEVERE, null, ex);
            strtDat = curDat;
        }
        long timDif = curDat.getTime() - strtDat.getTime();
        long seconds = (long) timDif / 1000;
        
        int hrs = (int) seconds /3600; 
        int min = (int) (seconds % 3600) / 60;
        
        datTimDif = new SimpleStringProperty(String.format("%d Hrs %d Min", hrs,min));
        
        return datTimDif; 
    }

}
