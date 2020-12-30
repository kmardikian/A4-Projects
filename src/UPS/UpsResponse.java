/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPS;

import java.math.BigDecimal;

/**
 *
 * @author Khatchik
 */
public class UpsResponse {

    private BigDecimal tpxChrg;     // transportation charges.
    private BigDecimal svcChrg;     // service charges
    private BigDecimal totChrg;     // total charges 
    private BigDecimal ngsChrg;     // negotiated charges
    private BigDecimal blgWgt;      // billing weight
    private String trkNo;           // tracking no
    private String trkTyp;           // tracking type
    //private byte[] lblImage;        // label image
    private String lblImage;
    private String lblLoc;
    private LblRspStat lblRspStat;

    public UpsResponse() {
        tpxChrg = BigDecimal.ZERO;
        svcChrg = BigDecimal.ZERO;
        totChrg = BigDecimal.ZERO;
        ngsChrg = BigDecimal.ZERO;
        blgWgt = BigDecimal.ZERO;
        trkNo = "";
        lblImage = " ";
        lblLoc = " ";
        lblRspStat = new LblRspStat();
    }

    public BigDecimal getTpxChrg() {
        return tpxChrg;
    }

    public void setTpxChrg(BigDecimal tpxChrg) {
        this.tpxChrg = tpxChrg;
    }

    public BigDecimal getSvcChrg() {
        return svcChrg;
    }

    public void setSvcChrg(BigDecimal svcChrg) {
        this.svcChrg = svcChrg;
    }

    public BigDecimal getTotChrg() {
        return totChrg;
    }

    public void setTotChrg(BigDecimal totChrg) {
        this.totChrg = totChrg;
    }

    public BigDecimal getNgsChrg() {
        return ngsChrg;
    }

    public void setNgsChrg(BigDecimal ngsChrg) {
        this.ngsChrg = ngsChrg;
    }

    public BigDecimal getBlgWgt() {
        return blgWgt;
    }

    public void setBlgWgt(BigDecimal blgWgt) {
        this.blgWgt = blgWgt;
    }

    public String getTrkNo() {
        return trkNo;
    }

    public String getTrkTyp() {
        return trkTyp;
    }

    public void setTrkTyp(String trkTyp) {
        this.trkTyp = trkTyp;
    }

    public void setTrkNo(String trkNo) {
        this.trkNo = trkNo;
    }

    public String getLblImage() {
        return lblImage;
    }

    public void setLblImage(String lblImage) {
        this.lblImage = lblImage;
    }

    public String getLblLoc() {
        return lblLoc;
    }

    public void setLblLoc(String lblLoc) {
        if (lblLoc == null) {
            this.lblLoc = " ";
        } else {
            this.lblLoc = lblLoc;
        }
    }

    public LblRspStat getLblRspStat() {
        return lblRspStat;
    }

    public void setLblRspStat(LblRspStat lblRspStat) {
        this.lblRspStat = lblRspStat;
    }

    public void print() {
        System.out.println("tpxChrg=" + tpxChrg
                + "\nSvcsChg= " + svcChrg
                + "\ntotChg= " + totChrg
                + "\nNgsChrg= " + ngsChrg
                + "\nblgWt = " + blgWgt
                + "\nTrkno= " + trkNo);
    }

}
