/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author Khatchik
 */
public class PtktByPtkt  implements Comparable<PtktByPtkt>{
    private final BigDecimal ptktNo;
    private final BigDecimal ordNo;
    private final String soldTo;
    private final String shipTo;
    private final String whse;
    private BigDecimal totu;
    private BigDecimal totd; 
    private final String sVia;
    private final String status;
    private final BigDecimal statusNum;
    private final BigDecimal orStrDt;
    private final BigDecimal orCmpDt;
    private final String operator;
    private final Double prtDate;
    private final Double prtTime; 
    private String period;
    private final String cusName;
    private final String ordTyp;
    private String stgDatTm;
    private final String pkrOpr;
    private final String shpToNam;
    private Integer crtnCnt; 
    private String poNo;
    private String webOrd;
    private BigDecimal stgSDt;
    private BigDecimal stgSTm;
    private BigDecimal stgEDt;
    private BigDecimal stgETm;
    private BigDecimal orPrl; 
    private int row;
   

    public PtktByPtkt(BigDecimal ptktNo, BigDecimal ordNo, String soldTo, 
            String shipTo, String whse, BigDecimal totu, BigDecimal totd,  
            String sVia, String status, BigDecimal statusNum, String operator, 
            Double prtDate, Double prtTime, BigDecimal orStrtDt, BigDecimal orCmpDt
    ,String cusName, String ordTyp, String stgDatTm, String pkrOpr,String shpToNam
    , Integer crtnCnt, String poNo, String webOrd, BigDecimal orPrl) {
        this.ptktNo = ptktNo;
        this.ordNo = ordNo;
        this.soldTo = soldTo;
        this.shipTo = shipTo;
        this.whse = whse;
        this.totu = totu;
        this.totd = totd;
        this.sVia = sVia;
        this.status = status;
        this.statusNum = statusNum;
        this.operator = operator;
        this.prtDate = prtDate;
        this.prtTime = prtTime;
        this.orStrDt = orStrtDt;
        this.orCmpDt=orCmpDt;
        this.cusName= cusName;
        this.ordTyp = ordTyp;
        this.stgDatTm= stgDatTm; 
        this.pkrOpr = pkrOpr;
        this.shpToNam=shpToNam;
        this.crtnCnt= crtnCnt; 
        this.stgSDt = BigDecimal.ZERO;
        this.stgSTm = BigDecimal.ZERO;
        this.stgEDt = BigDecimal.ZERO;
        this.stgETm = BigDecimal.ZERO;
        this.poNo = poNo;
        this.webOrd = webOrd;
        this.orPrl = orPrl;
    }
    public PtktByPtkt(PtkCarton ptkCrt) {
        DecimalFormat dec8 = new DecimalFormat("00000000");
        DecimalFormat dec6 = new DecimalFormat("000000");
        this.ptktNo = ptkCrt.getPtktNo();
        this.ordNo = ptkCrt.getOrdNo();
        this.soldTo = ptkCrt.getSoldTo();
        this.shipTo = ptkCrt.getShipTo();
        this.poNo = ptkCrt.getPoNo();
        this.webOrd = ptkCrt.getWebOrd();
        this.whse = ptkCrt.getWhse();
        this.totu = ptkCrt.getTotu();
        this.totd = ptkCrt.getTotd(); 
        this.sVia = ptkCrt.getsVia();
        this.status = ptkCrt.getStatus();
        this.statusNum = ptkCrt.getStatusNum();
        this.operator = ptkCrt.getOperator();
        this.prtDate = ptkCrt.getPrtDate();
        this.prtTime = ptkCrt.getPrtTime();
        this.orStrDt = ptkCrt.getOrStrtDt();
        this.orCmpDt= ptkCrt.getOrCmpDt();
        this.cusName= ptkCrt.getCusName();
        this.ordTyp = ptkCrt.getOrdTyp();
        this.stgDatTm= dec8.format(ptkCrt.getStgSDat()) + 
                dec6.format(ptkCrt.getStgSTim());
        this.pkrOpr = ptkCrt.getPkrOpr();
        this.shpToNam=ptkCrt.getShpToNam();
        this.crtnCnt = 1; 
        this.stgSDt = ptkCrt.getStgSDat();
        this.stgSTm = ptkCrt.getStgSTim();
        this.stgEDt = ptkCrt.getStgEDat();
        this.stgETm = ptkCrt.getStgETim();
        this.period = ptkCrt.getPeriod();
        this.orPrl = ptkCrt.getOrPrl();
        this.row = ptkCrt.getRow();
    }
    public void updPtkt(PtkCarton ptkCrt) {
        this.totu = this.totu.add(ptkCrt.getTotu());
        this.totd = this.totd.add(ptkCrt.getTotd());
        this.crtnCnt +=1;
        // update stage start date and time with the highest date & time from cartons
        DecimalFormat dec8 = new DecimalFormat("00000000");
        DecimalFormat dec6 = new DecimalFormat("000000");
        String crtDatTim = dec8.format(ptkCrt.getStgSDat()) +
                dec6.format(ptkCrt.getStgSTim());
        if (crtDatTim.compareTo(this.stgDatTm) >0)  {
            this.stgDatTm = crtDatTim;
            this.stgSDt = ptkCrt.getStgSDat();
            this.stgEDt = ptkCrt.getStgSTim();
        }
        
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
    

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

    public String getStgDatTm() {
        return stgDatTm;
    }

    public String getPkrOpr() {
        return pkrOpr;
    }

    public String getShpToNam() {
        return shpToNam;
    }

    public Integer getCrtnCnt() {
        return crtnCnt;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getWebOrd() {
        return webOrd;
    }

    public void setWebOrd(String webOrd) {
        this.webOrd = webOrd;
    }

    public BigDecimal getOrPrl() {
        return orPrl;
    }

    public void setOrPrl(BigDecimal orPrl) {
        this.orPrl = orPrl;
    }
    
    
    

    @Override
    public int compareTo(PtktByPtkt o) {
        int result;
        if (o.prtDate < this.prtDate) {
            result = -1;
        } else if (o.prtDate > this.prtDate) {
            result = 1;
        } else if (o.prtTime < this.prtTime) {
            result = -1;
        } else if (o.prtTime > this.prtTime) {
            result = 1;
        } else  {
            result =o.ptktNo.compareTo(this.ptktNo);
        }
        return result;
    }
    
}

