/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Khatchik
 */
public class PtkCarton implements Comparable<PtkCarton> {

    private final BigDecimal crtnNo;
    private final BigDecimal ptktNo;
    private final BigDecimal ordNo;
    private final String soldTo;
    private final String shipTo;
    private final String whse;
    private final BigDecimal totu;
    private final BigDecimal totd;
    private final String sVia;
    private final String status;
    private final String ctnStat;
    private final BigDecimal statusNum;
    private final BigDecimal orStrDt;
    private final BigDecimal orCmpDt;
    private final String operator;
    private final Double prtDate;
    private final Double prtTime;
    private String period;
    private final String cusName;
    private final String ordTyp;
    private final String pkrOpr;
    private final String shpToNam;
    private final Integer skuCnt;
    private final BigDecimal stgSDat;
    private final BigDecimal stgSTim;
    private final BigDecimal stgEDat;
    private final BigDecimal stgETim;
    private int row;

    public PtkCarton(BigDecimal crtnNo, BigDecimal ptktNo, BigDecimal ordNo, String soldTo,
            String shipTo, String whse, BigDecimal totu, BigDecimal totd,
            String sVia, String status, BigDecimal statusNum, String operator,
            Double prtDate, Double prtTime, BigDecimal orStrtDt, BigDecimal orCmpDt,
            String cusName, String ordTyp, String pkrOpr, String shpToNam,
            Integer skuCnt, String ctnStat, BigDecimal stgSDat, BigDecimal stgSTim,
            BigDecimal stgEDat, BigDecimal stgETim) {

        this.crtnNo = crtnNo;
        this.ptktNo = ptktNo;
        this.ordNo = ordNo;
        this.soldTo = soldTo;
        this.shipTo = shipTo;
        this.whse = whse;
        this.totu = totu;
        this.totd = totd;
        this.sVia = sVia;
        this.status = status;
        this.ctnStat = ctnStat;
        this.statusNum = statusNum;
        this.operator = operator;
        this.prtDate = prtDate;
        this.prtTime = prtTime;
        this.orStrDt = orStrtDt;
        this.orCmpDt = orCmpDt;
        this.cusName = cusName;
        this.ordTyp = ordTyp;
        this.pkrOpr = pkrOpr;
        this.shpToNam = shpToNam;
        this.skuCnt = skuCnt;
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //DecimalFormat formater8 = new DecimalFormat("00000000");
        //DecimalFormat formater6 = new DecimalFormat("000000");
        //String strDat = formater8.format(stgSDat);
        //String strTim = formater6.format(stgSTim);
        this.stgSDat = stgSDat;
        this.stgSTim = stgSTim;
        this.stgEDat = stgEDat;
        this.stgETim = stgETim;

    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * returns Carton number.
     */
    public BigDecimal getCrtnNo() {
        return crtnNo;
    }

    /**
     *
     * @return Pick Ticket number
     */
    public BigDecimal getPtktNo() {
        return ptktNo;
    }

    public BigDecimal getOrdNo() {
        return ordNo;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public String getShipTo() {
        return shipTo;
    }

    public String getWhse() {
        return whse;
    }

    public BigDecimal getTotu() {
        return totu;
    }

    public BigDecimal getTotd() {
        return totd;
    }

    public String getsVia() {
        return sVia;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getStatusNum() {
        return statusNum;
    }
/**
 *  
 * @return carton Status 
 */
    public String getCtnStat() {
        return ctnStat;
    }

    public String getOperator() {
        return operator;
    }

    public Double getPrtDate() {
        return prtDate;
    }

    public Double getPrtTime() {
        return prtTime;
    }

    public BigDecimal getOrStrtDt() {
        return orStrDt;
    }

    public BigDecimal getOrCmpDt() {
        return orCmpDt;
    }

    public String getCusName() {
        return cusName;
    }

    public String getOrdTyp() {
        return ordTyp;
    }

    public BigDecimal getOrStrDt() {
        return orStrDt;
    }

    public String getPkrOpr() {
        return pkrOpr;
    }

    public String getShpToNam() {
        return shpToNam;
    }

    public Integer getSkuCnt() {
        return skuCnt;
    }

    /**
     *
     * @return Stage Start Date
     */
    public BigDecimal getStgSDat() {
        return stgSDat;
    }

    /**
     *
     * @return Stage Start Time
     */
    public BigDecimal getStgSTim() {
        return stgSTim;
    }

    /**
     *
     * @return Stage End date
     */
    public BigDecimal getStgEDat() {
        return stgEDat;
    }

    /**
     *
     * @return stage End Time
     */
    public BigDecimal getStgETim() {
        return stgETim;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public int compareTo(PtkCarton o) {
        int result;
        if (o.prtDate < this.prtDate) {
            result = -1;
        } else if (o.prtDate > this.prtDate) {
            result = 1;
        } else if (o.prtTime < this.prtTime) {
            result = -1;
        } else if (o.prtTime > this.prtTime) {
            result = 1;
        } else {
            result = o.ptktNo.compareTo(this.ptktNo);
        }
        if (result == 0) {
            result = this.crtnNo.compareTo(o.crtnNo);
        }
        return result;
    }

}
