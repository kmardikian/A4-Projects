/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktdisplay;

import java.math.BigDecimal;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import ptktData.AppParms;
import ptktData.PtktSumByPkr;
import ptktData.PtktSumByStat;

/**
 *
 * @author Khatchik
 */
public class PtktByPkrTblRow {

    private final SimpleStringProperty nam;
    final private SimpleIntegerProperty prt;
    final private SimpleIntegerProperty dis;
    final private SimpleIntegerProperty cus;
    //private SimpleIntegerProperty asg;
    final private SimpleIntegerProperty pck;
    final private SimpleIntegerProperty pcm;
    final private SimpleIntegerProperty qua;
    final private SimpleIntegerProperty cpu;
    final private SimpleIntegerProperty wgh;
    final private SimpleIntegerProperty inv;
    final private SimpleIntegerProperty shp;
    final private SimpleIntegerProperty tot;
    final private SimpleIntegerProperty totCtn;
    private BigDecimal prtUnt;
    private BigDecimal prtDlr;
    private Integer prtCtn;
    private BigDecimal disUnt;
    private BigDecimal disDlr;
    private Integer disCtn;
    private BigDecimal cusUnt;
    private BigDecimal cusDlr;
    private Integer cusCtn;
    private BigDecimal pckUnt;
    private BigDecimal pckDlr;
    private Integer pckCtn;
    private BigDecimal pcmUnt;
    private BigDecimal pcmDlr;
    private Integer    pcmCtn;  
    private BigDecimal quaUnt;
    private BigDecimal quaDlr;
    private Integer quaCtn;
    private BigDecimal cpuUnt;
    private BigDecimal cpuDlr;
    private Integer cpuCtn;
    private BigDecimal wghUnt;
    private BigDecimal wghDlr;
    private Integer wghCtn;
    private BigDecimal invUnt;
    private BigDecimal invDlr;
    private Integer invCtn;
    private BigDecimal shpUnt;
    private BigDecimal shpDlr;
    private Integer shpCtn;
    private BigDecimal totUnt;
    private BigDecimal totDlr;

    public PtktByPkrTblRow(PtktSumByPkr sumByPkr) {

        ArrayList<PtktSumByStat> sumByStatList = new ArrayList<>();
        this.nam = new SimpleStringProperty(sumByPkr.getPicker());
        sumByStatList = sumByPkr.getSumByStat();
        int wTot = 0;
        int wPrt = 0;
        int wDis = 0;
        int wCus = 0;
        int wAsg = 0;
        int wPck = 0;
        int wPcm = 0;
        int wQua = 0;
        int wCpu = 0;
        int wWgh = 0;
        int wShp = 0;
        int wInv = 0;
        prtUnt = BigDecimal.ZERO;
        prtDlr = BigDecimal.ZERO;
        disUnt = BigDecimal.ZERO;
        disDlr = BigDecimal.ZERO;
        cusUnt = BigDecimal.ZERO;
        cusDlr = BigDecimal.ZERO;
        
        pckUnt = BigDecimal.ZERO;
        pckDlr = BigDecimal.ZERO;
        pcmUnt = BigDecimal.ZERO;
        pcmDlr = BigDecimal.ZERO;
        quaUnt = BigDecimal.ZERO;
        quaDlr = BigDecimal.ZERO;
        cpuUnt = BigDecimal.ZERO;
        cpuDlr = BigDecimal.ZERO;
        wghUnt = BigDecimal.ZERO;
        wghDlr = BigDecimal.ZERO;
        invUnt = BigDecimal.ZERO;
        invDlr = BigDecimal.ZERO;
        shpUnt = BigDecimal.ZERO;
        shpDlr = BigDecimal.ZERO;
        totUnt = BigDecimal.ZERO;
        totDlr = BigDecimal.ZERO;
        
        prtCtn = 0;
        disCtn = 0;
        cusCtn = 0;
        pckCtn = 0;
        pcmCtn = 0;
        quaCtn = 0; 
        cpuCtn = 0;
        wghCtn = 0;
        shpCtn = 0;
        invCtn = 0;

        for (PtktSumByStat statSum : sumByStatList) {
            wTot = wTot + statSum.getPtktCnt();
            totUnt = totUnt.add(statSum.getTotUnt());
            totDlr = totDlr.add(statSum.getTotDlr());
            switch (statSum.getPtktStat().trim()) {
                case AppParms.PRT_STAT_NAME:
                    wPrt = statSum.getPtktCnt();
                    prtUnt= statSum.getTotUnt();
                    prtDlr= statSum.getTotDlr();
                    prtCtn= statSum.getTotCrtn();
                    break;
                case AppParms.DIS_STAT_NAME:
                    wDis = statSum.getPtktCnt();
                    disUnt= statSum.getTotUnt();
                    disDlr= statSum.getTotDlr();
                    disCtn= statSum.getTotCrtn();
                    break;
                case AppParms.CUS_STAT_NAME:
                    wCus = statSum.getPtktCnt();
                    cusUnt = statSum.getTotUnt();
                    cusDlr = statSum.getTotDlr();
                    cusCtn = statSum.getTotCrtn();
                    break;
//                case AppParms.ASG_STAT_NAME:      4/19/18 removed ASGN stat 
//                    wAsg = statSum.getPtktCnt();
//                    break;
                case AppParms.PCK_STAT_NAME:
                    wPck = statSum.getPtktCnt();
                    pckUnt= statSum.getTotUnt();
                    pckDlr= statSum.getTotDlr();
                    pckCtn= statSum.getTotCrtn();
                    break;
                case AppParms.PCM_STAT_NAME:
                    wPcm = statSum.getPtktCnt();
                    pcmUnt= statSum.getTotUnt();
                    pcmDlr= statSum.getTotDlr();
                    pcmCtn= statSum.getTotCrtn();
                    break;
                case AppParms.QUA_STAT_NAME: 
                    wQua = statSum.getPtktCnt();
                    quaUnt= statSum.getTotUnt();
                    quaDlr= statSum.getTotDlr();
                    quaCtn= statSum.getTotCrtn();
                    break;
                case AppParms.PAK_STAT_NAME: 
                    wQua = statSum.getPtktCnt();
                    quaUnt= statSum.getTotUnt();
                    quaDlr= statSum.getTotDlr();
                    quaCtn= statSum.getTotCrtn();
                    break;
                case AppParms.CPU_STAT_NAME:
                    wCpu = statSum.getPtktCnt();
                    cpuUnt= statSum.getTotUnt();
                    cpuDlr= statSum.getTotDlr();
                    cpuCtn= statSum.getTotCrtn();
                    break;
                case AppParms.WGH_STAT_NAME:
                    wWgh = statSum.getPtktCnt();
                    wghUnt= statSum.getTotUnt();
                    wghDlr= statSum.getTotDlr();
                    wghCtn= statSum.getTotCrtn();
                    break;
                case AppParms.SHP_STAT_NAME:       
                    wShp = statSum.getPtktCnt();
                    shpUnt= statSum.getTotUnt();
                    shpDlr= statSum.getTotDlr();
                    shpCtn= statSum.getTotCrtn();
                    break;
                case AppParms.INV_STAT_NAME:
                    wInv = statSum.getPtktCnt();
                    invUnt= statSum.getTotUnt();
                    invDlr= statSum.getTotDlr();
                    invCtn= statSum.getTotCrtn();
                    break;
            }
        }
        
        this.prt = new SimpleIntegerProperty(wPrt);
        this.dis = new SimpleIntegerProperty(wDis);
        this.cus = new SimpleIntegerProperty(wCus);
//        this.asg = new SimpleIntegerProperty(wAsg);
        this.pck = new SimpleIntegerProperty(wPck);
        this.pcm = new SimpleIntegerProperty(wPcm);
        this.qua = new SimpleIntegerProperty(wQua);
        this.cpu = new SimpleIntegerProperty(wCpu);
        this.wgh = new SimpleIntegerProperty(wWgh);
        this.shp = new SimpleIntegerProperty(wShp);
        this.inv = new SimpleIntegerProperty(wInv);
        this.tot = new SimpleIntegerProperty(wTot);
        this.totCtn = new SimpleIntegerProperty(
                prtCtn + disCtn + cusCtn + pckCtn + pcmCtn + quaCtn + cpuCtn + wghCtn + shpCtn + invCtn
        );
        

    }
    public PtktByPkrTblRow(String wNam,  int wPrt, int wDis, int wCus, int wPck
    ,int wPcm ,int wQua,int wCpu, int wWgh, int wShp, int wInv,
    BigDecimal wPrtU, BigDecimal wPrtD, BigDecimal wDisU, 
    BigDecimal wDisD, BigDecimal wCusU, BigDecimal wCusD,
    BigDecimal wPckU, BigDecimal wPckD, BigDecimal wPcmU, BigDecimal wPcmD, 
    BigDecimal wQuaU, BigDecimal wQuaD,
    BigDecimal wCpuU, BigDecimal wCpuD, BigDecimal wWghU, BigDecimal wWghD,
    BigDecimal wShpU, BigDecimal wShpD, BigDecimal wInvU, BigDecimal wInvD,
    Integer wPrtCtn, Integer wDisCtn, Integer wCusCtn, Integer wPckCtn,Integer wPcmCtn, 
    Integer wQuaCtn, Integer wCpuCtn, Integer wWghCtn, Integer wShpCtn, Integer wInvCtn ) {
        
        int wTot = wPrt + wDis+ wCus + wPck+ wPcm+ wQua + wCpu + wWgh + wShp + wInv; 
        this.prtCtn = wPrtCtn;
        this.disCtn = wDisCtn;
        this.cusCtn = wCusCtn;
        this.pckCtn = wPckCtn;
        this.pcmCtn = wPcmCtn;
        this.quaCtn = wQuaCtn; 
        this.cpuCtn = wCpuCtn;
        this.wghCtn = wWghCtn;
        this.shpCtn = wShpCtn;
        this.invCtn = wInvCtn;
        
        this.prt = new SimpleIntegerProperty(wPrt);
        this.dis = new SimpleIntegerProperty(wDis);
        this.cus = new SimpleIntegerProperty(wCus);
        //this.asg = new SimpleIntegerProperty(wAsg);
        this.pck = new SimpleIntegerProperty(wPck);
        this.pcm = new SimpleIntegerProperty(wPcm);
        this.qua = new SimpleIntegerProperty(wQua);
        this.cpu = new SimpleIntegerProperty(wCpu);
        this.wgh = new SimpleIntegerProperty(wWgh);
        this.shp = new SimpleIntegerProperty(wShp);
        this.inv = new SimpleIntegerProperty(wInv);
        this.tot = new SimpleIntegerProperty(wTot);
        this.nam = new SimpleStringProperty(wNam);
        
        this.prtUnt = wPrtU;
        this.prtDlr = wPrtD;
        this.disUnt = wDisU;
        this.disDlr = wDisD;
        this.cusUnt = wCusU;
        this.cusDlr = wCusD;
        this.pckUnt = wPckU;
        this.pckDlr = wPckD;
        this.pcmUnt = wPcmU;
        this.pcmDlr = wPcmD;
        this.quaUnt = wQuaU;
        this.quaDlr = wQuaD;
        this.cpuUnt = wCpuU;
        this.cpuDlr = wCpuD;
        this.wghUnt = wWghU;
        this.wghDlr = wWghD;
        this.shpUnt = wShpU;
        this.shpDlr = wShpD;
        this.invUnt = wInvU;
        this.invDlr = wInvD;
        this.totUnt = wPrtU
                .add(wDisU)
                .add(wCusU) 
                .add(wPckU)
                .add(wPcmU)
                .add(wQuaU)
                .add(wCpuU)
                .add(wWghU)
                .add(wShpU)
                .add(wInvU);
        
        this.totDlr = wPrtD
                .add(wDisD)
                .add(wCusD)
                .add(wPckD)
                .add(wPcmD)
                .add(wQuaD)
                .add(wCpuD)
                .add(wWghD)
                .add(wShpD)
                .add(wInvD);
        
        this.totCtn = new SimpleIntegerProperty(
                this.prtCtn + this.disCtn + this.cusCtn + this.pckCtn + this.quaCtn 
                        + this.cpuCtn + this.wghCtn + this.shpCtn + this.invCtn);
        
    }

    public SimpleStringProperty getNamProperty() {
        return nam;
    }

    public SimpleIntegerProperty getPrt() {
        return prt;
    }

    public SimpleIntegerProperty getDisProperty() {
        return dis;
    }

    public SimpleIntegerProperty getCusProperty() {
        return cus;
    }
    
    

//    public SimpleIntegerProperty getAsgProperty() {
//        return asg;
//    }

    public SimpleIntegerProperty getPckProperty() {
        return pck;
    }
    
    public SimpleIntegerProperty getPcmProperty() {
        return pcm;
    }

    public SimpleIntegerProperty getQuaProperty() {
        return qua;
    }

    public SimpleIntegerProperty getCpuProperty() {
        return cpu;
    }

    public SimpleIntegerProperty getWghProperty() {
        return wgh;
    }
    public SimpleIntegerProperty getShpProperty() {
        return shp;
    }

    public SimpleIntegerProperty getInvProperty() {
        return inv;
    }

    public SimpleIntegerProperty getTotProperty() {
        return tot;
    }

    public BigDecimal getPrtUnt() {
        return prtUnt;
    }

    public BigDecimal getPrtDlr() {
        return prtDlr;
    }

    public BigDecimal getDisUnt() {
        return disUnt;
    }

    public BigDecimal getDisDlr() {
        return disDlr;
    }

    public BigDecimal getCusUnt() {
        return cusUnt;
    }

    public BigDecimal getCusDlr() {
        return cusDlr;
    }
    
    
    

    public BigDecimal getPckUnt() {
        return pckUnt;
    }

    public BigDecimal getPckDlr() {
        return pckDlr;
    }

    public BigDecimal getQuaUnt() {
        return quaUnt;
    }

    public BigDecimal getQuaDlr() {
        return quaDlr;
    }

    public BigDecimal getCpuUnt() {
        return cpuUnt;
    }

    public BigDecimal getCpuDlr() {
        return cpuDlr;
    }

    public BigDecimal getWghUnt() {
        return wghUnt;
    }

    public BigDecimal getWghDlr() {
        return wghDlr;
    }

    public BigDecimal getInvUnt() {
        return invUnt;
    }

    public BigDecimal getInvDlr() {
        return invDlr;
    }

    public BigDecimal getShpUnt() {
        return shpUnt;
    }

    public BigDecimal getShpDlr() {
        return shpDlr;
    }

    public BigDecimal getTotUnt() {
        return totUnt;
    }

    public BigDecimal getTotDlr() {
        return totDlr;
    }

    public SimpleIntegerProperty getTotCtnProperty() {
        return totCtn;
    }
    public Integer getTotCtn() {
        return totCtn.getValue();
    }

    public Integer getPrtCtn() {
        return prtCtn;
    }

    public Integer getDisCtn() {
        return disCtn;
    }

    public Integer getCusCtn() {
        return cusCtn;
    }

    public Integer getPckCtn() {
        return pckCtn;
    }

    public Integer getQuaCtn() {
        return quaCtn;
    }

    public Integer getCpuCtn() {
        return cpuCtn;
    }

    public Integer getWghCtn() {
        return wghCtn;
    }

    public Integer getInvCtn() {
        return invCtn;
    }

    public Integer getShpCtn() {
        return shpCtn;
    }

    public BigDecimal getPcmUnt() {
        return pcmUnt;
    }

    public void setPcmUnt(BigDecimal pcmUnt) {
        this.pcmUnt = pcmUnt;
    }

    public BigDecimal getPcmDlr() {
        return pcmDlr;
    }

    public void setPcmDlr(BigDecimal pcmDlr) {
        this.pcmDlr = pcmDlr;
    }

    public Integer getPcmCtn() {
        return pcmCtn;
    }

    public void setPcmCtn(Integer pcmCtn) {
        this.pcmCtn = pcmCtn;
    }
    
    

    

}
