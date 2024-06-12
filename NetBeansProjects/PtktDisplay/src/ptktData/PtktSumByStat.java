/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

import java.math.BigDecimal;

/**
 *
 * @author Khatchik
 */
public class PtktSumByStat implements Comparable<PtktSumByStat>{

    private final String ptktStat;
    private final BigDecimal statusNum;
    private int ptktCnt;
    private BigDecimal totUnt;
    private BigDecimal totDlr;
    private Integer totCrtn;
    

    public PtktSumByStat(String ptktStat, BigDecimal statusNum ) {
        this.ptktStat = ptktStat;
        this.statusNum = statusNum;
        this.ptktCnt= 0;
        this.totUnt =BigDecimal.ZERO;
        this.totDlr= BigDecimal.ZERO;
        this.totCrtn=0;
        
    }

    /**
     * @return the ptktStat
     */
    public String getPtktStat() {
        return ptktStat;
    }

    /**
     * @param ptktStat the ptktStat to set
     */
    //public void setPtktStat(String ptktStat) {
    //    this.ptktStat = ptktStat;
   // }

    /**
     * @return the ptktCnt
     */
    public int getPtktCnt() {
        return ptktCnt;
    }

    /**
     * @param ptktCnt the ptktCnt to set
     */
    public void setPtktCnt(int ptktCnt) {
        this.ptktCnt = ptktCnt;
    }

    /**
     * @return the totUnt
     */
    public BigDecimal getTotUnt() {
        return totUnt;
    }

    /**
     * @param totUnt the totUnt to set
     */
    public void setTotUnt(BigDecimal totUnt) {
        this.totUnt = totUnt;
    }

    /**
     * @return the totDlr
     */
    public BigDecimal getTotDlr() {
        return totDlr;
    }

    /**
     * @param totDlr the totDlr to set
     */
    public void setTotDlr(BigDecimal totDlr) {
        this.totDlr = totDlr;
    }

    public Integer getTotCrtn() {
        return totCrtn;
    }

    public void setTotCrtn(Integer totCrtn) {
        this.totCrtn = totCrtn;
    }
    
    /**
     * @param units pick ticket units
     * @param dollars pick ticket Dollars 
     * @param totCrtn pick ticket carton count  
     */
    public void addStat(BigDecimal units, BigDecimal dollars,Integer totCrtn ) {
        incCnt();
        addUnits(units);
        addDollars(dollars);
        addCrtns(totCrtn);
    }

    public void incCnt() {
        this.ptktCnt = this.ptktCnt + 1;
    }

    public void addUnits(BigDecimal units) {
        this.totUnt=this.totUnt.add(units);

    }

    public void addDollars(BigDecimal dlrs) {
        this.totDlr =this.totDlr.add(dlrs);
    }
    public void addCrtns(Integer crtns ) {
        this.totCrtn += crtns; 
    }

    public BigDecimal getStatusNum() {
        return this.statusNum;
    }
    
    @Override
    public int compareTo(PtktSumByStat newStat) {
       
        return this.statusNum.compareTo(newStat.statusNum);
       
    }
}
