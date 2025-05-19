/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static ptktData.AppParms.TORD;
import ptktdisplay.KpiTblRow;
import static ptktData.AppParms.B4_5PM_ORD;

/**
 *
 * @author Khatchik
 */
public class PtktSum {

    private final ArrayList<PtktByPtkt> ptktList = new ArrayList<>();
    private ArrayList<PtkCarton> ptkCartonList = new ArrayList<>();
   // private ArrayList<PtktPrd> ptktPrdList = new ArrayList<>();
    private final ArrayList<PtktPrd> ptktPrdList;
    private final ArrayList<KpiTblRow> kpiTbl = new ArrayList();
    private final Map<BigDecimal, PtktByPtkt> ptktByPtk = new HashMap<>();
    private final Map<String, PtktSumByStat> ptktStatMap = new HashMap<>();
    private final Map<String, PtktSumByPrd> ptktPrdMap = new HashMap<>();
    private final Map<String, PtktSumByPkr> ptktPkrMap = new HashMap<>();
    private final Map<String, KpiTblRow> kpiTblMap = new HashMap<>();
    private final Double curDtNum;
    private final String curDt;

    public PtktSum() {
        SimpleDateFormat datFmt = new SimpleDateFormat("yyyyMMdd");
        this.curDt = datFmt.format(new Date());
        this.curDtNum = Double.valueOf(curDt);
        this.ptktPrdList = AppParms.getPtktPrdList();
        crtInitSumByStat();
        crtInitSumByPrd();
    }

    public PtktSum(String curDt) {
        this.curDt = curDt;
        this.curDtNum = Double.valueOf(curDt);
        this.ptktPrdList = AppParms.getPtktPrdList();
        crtInitSumByStat();
        crtInitSumByPrd();
    }

    private void crtInitSumByStat() {
        ArrayList<PtktStat> pstat;
        pstat = AppParms.getPstat();
        for (PtktStat ps : pstat) {
            // if (ps.getStat().equals(AppParms.DIS_STAT_NAME)) {
            // System.out.println("init sum by stat " + ps.getStat() + " " 
            // + ps.getStatNum());
            // }
            ptktStatMap.put(ps.getStat(), new PtktSumByStat(ps.getStat().trim(), ps.getStatNum()));
        }
        // init the sum by stat to ensure we have entry for all types to make charts have similar colors

    }

    private void crtInitSumByPrd() {

        ptktPrdList.forEach(x -> {
            ptktPrdMap.put(x.getPrdLbl(), new PtktSumByPrd(x.getPrdLbl(), x.getPrdRow()));
        });

    }

//    private void add(PtkCarton ptkCrt) {
//
//        ptkCartonList.add(ptkCrt);
//        ptkCrt.setPeriod(updPrd(ptkCrt));
//        updPtktMap(ptkCrt);
//
//    }
    public void add(ArrayList<PtkCarton> ptkCrtList) {

        this.ptkCartonList = ptkCrtList;
        this.ptkCartonList.forEach((ptkCart) -> {
            // ptkCart.setPeriod(updPrd(ptkCart));
            updCrtPrd(ptkCart);
            updPtktMap(ptkCart);
        });
        ptktList.addAll(ptktByPtk.values());
        Collections.sort(ptktList);
        ptktByPtk.clear();

        ptktList.forEach((PtktByPtkt ptk) -> {
            updSum(ptk);
        }
        );
        // after pick ticket List is created update other summary levels
    }

//    private String updPrd(PtkCarton ptkCrt) {
//        Double ptktTime = ptkCrt.getPrtTime();
//        int row = 0;
//        String prd = "";
//        if (ptkCrt.getPrtDate() < curDtNum) {
//            prd = "<" + curDt;
//            row = 1;
//        } else {
//            for (PtktPrd ptkPrd : ptktPrdList) {
//                if (ptktTime < ptkPrd.getPrdTim()) {
//                    prd = ptkPrd.getPrdLbl();
//                    row = ptkPrd.getPrdRow();
//                    break;
//                }
//            }
//        }
//        return prd;
//    }
    private void updPtktMap(PtkCarton ptkCrt) {

        if (ptktByPtk.get(ptkCrt.getPtktNo()) == null) {
            ptktByPtk.put(ptkCrt.getPtktNo(), new PtktByPtkt(ptkCrt));
        } else {
            ptktByPtk.get(ptkCrt.getPtktNo()).updPtkt(ptkCrt);
        }
    }

    private void updCrtPrd(PtkCarton ptkCrt) {

        Double ptktTime = ptkCrt.getPrtTime();
        int row = 0;
        String prd = "";
        if (ptkCrt.getPrtDate() < curDtNum) {
            prd = "<" + curDt;
            row = 1;
        } else {
            for (PtktPrd ptkPrd : ptktPrdList) {
                if (ptktTime < ptkPrd.getPrdTim()) {
                    prd = ptkPrd.getPrdLbl();
                    row = ptkPrd.getPrdRow();
                    break;
                }
            }
        }

        ptkCrt.setPeriod(prd);
        ptkCrt.setRow(row);

    }

    private void updSum(PtktByPtkt ptk) {

//        Double ptktTime = ptk.getPrtTime();
//        int row = 0;
//        String prd = "";
//        if (ptk.getPrtDate() < curDtNum) {
//            prd = "<" + curDt;
//            row = 1;
//        } else {
//            for (PtktPrd ptkPrd : ptktPrdList) {
//                if (ptktTime < ptkPrd.getPrdTim()) {
//                    prd = ptkPrd.getPrdLbl();
//                    row = ptkPrd.getPrdRow();
//                    break;
//                }
//            }
//        }
//        
//        ptk.setPeriod(prd);
        if (ptktPrdMap.get(ptk.getPeriod()) == null) {
            ptktPrdMap.put(ptk.getPeriod(), new PtktSumByPrd(ptk.getPeriod(), ptk.getRow()));
        }
        ptktPrdMap.get(ptk.getPeriod()).addStat(ptk.getStatus().trim(),
                ptk.getTotu(), ptk.getTotd(), ptk.getCrtnCnt());
//        String tst = ptkCrt.getStatus().trim();
//        switch (ptkCrt.getStatus().trim()) {
//            case "PRT":
//                ptktPrdMap.get(prd).incPrt();
//                break;
//            case AppParms.DIS_STAT_NAME:
//                ptktPrdMap.get(prd).incDis();
//                break;
////            case "ASG":
////                ptktPrdMap.get(prd).incAsg();
////                break;
//            case "PCK":
//                ptktPrdMap.get(prd).incPck();
//                break;
//            case "PAK":
//                ptktPrdMap.get(prd).incPak();
//                break;
//            case AppParms.CPU_STAT_NAME:
//                ptktPrdMap.get(prd).incCpk();
//                break;
//            case "WGH":
//                ptktPrdMap.get(prd).incWgh();
//                break;
//            case AppParms.SHP_STAT_NAME:
//                ptktPrdMap.get(prd).incShp();
//                break;
//            case "INV":
//                ptktPrdMap.get(prd).incInv();
//                break;
//        }
//
        // update sum by stat
        String wStat;
        wStat = ptk.getStatus().trim();
        if (wStat.equals(AppParms.PAK_STAT_NAME)) {
            wStat = AppParms.QUA_STAT_NAME;
        }

        if (ptktStatMap.get(wStat) == null) {
//            System.out.println("not foud:" + ptkCrt.getStatus());
            ptktStatMap.put(wStat, new PtktSumByStat(wStat,
                    ptk.getStatusNum()));
        }
        ptktStatMap.get(wStat).addStat(ptk.getTotu(), ptk.getTotd(), ptk.getCrtnCnt());

        if (!ptktPkrMap.containsKey(ptk.getPkrOpr())) {
            ptktPkrMap.put(ptk.getPkrOpr(), new PtktSumByPkr(ptk.getPkrOpr()));
        }

        ptktPkrMap.get(ptk.getPkrOpr()).addStat(ptk.getStatus(),
                ptk.getTotu(), ptk.getTotd(), ptk.getCrtnCnt());

        //return prd;
    }

    void sumKpi() {
//        int b41pm_open = 0;
//        int b41pm_shipped = 0;
        int todayOpen = 0;
        int todayShipped = 0;
        int b4_4pm_open = 0;
        int b4_4pm_shipped = 0;

        WhseCutoffData cutoff;

        for (PtktByPtkt ptktEnt : ptktList) {

            if (AppParms.getWhseCutoffData().containsKey(ptktEnt.getWhse())) {
                cutoff = AppParms.getWhseCutoffData().get(ptktEnt.getWhse());
                if (AppParms.kpiIsPckSel(AppParms.TORD, ptktEnt ) ) {
                    if (ptktEnt.getStatus().trim().equals(AppParms.INV_STAT_NAME)) {
                        todayShipped++;
                    } else {
                        todayOpen++;
                    }
                }
                }  
            if (AppParms.kpiIsPckSel(AppParms.B4_5PM_ORD, ptktEnt ) )  {
                    if (ptktEnt.getStatus().trim().equals(AppParms.INV_STAT_NAME)) {
                        //System.out.println("shipped " + ptktEnt.getPtktNo() + " " + ptktEnt.getPrtDtTm());
                        b4_4pm_shipped++;
                    } else {
                        b4_4pm_open++;
                        //System.out.println("Open " + ptktEnt.getPtktNo() + " " + ptktEnt.getPrtDtTm());
                    }
                }
             

//                if (ptktEnt.getPrtDate().compareTo(getCurDtNum()) == 0
//                        && !ptktEnt.getPkhDisF().equals("Y")) {
                    //2 && !ptktEnt.getStatus().equals(AppParms.DIS_STAT_NAME)) {

// 2                   if (ptktEnt.getOrPrtTime().compareTo(cutoff.getCutOffTime()) <= 0) {
//2
// 2                       if (ptktEnt.getStatus().trim().equals(AppParms.INV_STAT_NAME)) {
//  2                          b41pm_shipped++;
//   2                     } else {
//    2                        b41pm_open++;
//     2                   }
//      2              }
//                    if (ptktEnt.getStatus().trim().equals(AppParms.INV_STAT_NAME)) {
//                        todayShipped++;
//                    } else {
//                        todayOpen++;
//                    }

  //              }
//                if (ptktEnt.getPrtDtTm().isAfter(cutoff.getlDtm_DayB4())
//                        && ptktEnt.getPrtDtTm().isBefore(cutoff.lDtm_DayCur)
//                        && !ptktEnt.getPkhDisF().equals("Y")) {
//                    if (ptktEnt.getStatus().trim().equals(AppParms.INV_STAT_NAME)) {
//                        //System.out.println("shipped " + ptktEnt.getPtktNo() + " " + ptktEnt.getPrtDtTm());
//                        b4_4pm_shipped++;
//                    } else {
//                        b4_4pm_open++;
//                        //System.out.println("Open " + ptktEnt.getPtktNo() + " " + ptktEnt.getPrtDtTm());
//                    }
//                }
// 2               if (ptktEnt.getPtktNo().equals(BigDecimal(0))) {
// 2                   System.out.println(ptktEnt.getPtktNo() + " " + ptktEnt.getPrtDate().toString());
// 2                   
// 2               }

 //           }
        }

        KpiTblRow kpiRow;

//        kpiRow = new KpiTblRow(B4_1ORD, b41pm_open, b41pm_shipped);
//        kpiTblMap.put(B4_1ORD, kpiRow);
//        this.kpiTbl.add(kpiRow);

        kpiRow = new KpiTblRow(TORD, todayOpen, todayShipped);
        kpiTblMap.put(TORD, kpiRow);
        this.kpiTbl.add(kpiRow);

        kpiRow = new KpiTblRow(B4_5PM_ORD, b4_4pm_open, b4_4pm_shipped);
        this.kpiTbl.add(kpiRow);
        kpiTblMap.put(B4_5PM_ORD, kpiRow);

    }

    public ArrayList<PtktSumByStat> getPtktSumStat() {
        ArrayList<PtktSumByStat> ptktSumByStat = new ArrayList<>();
        ptktSumByStat.addAll(ptktStatMap.values());
        Collections.sort(ptktSumByStat);

        return ptktSumByStat;
    }

    public PtktSumByStat getPtktSumStat(String stat) {
        PtktSumByStat rtnSumByStat = null;
        if (ptktStatMap.containsKey(stat)) {
            rtnSumByStat = ptktStatMap.get(stat);
        }
        return rtnSumByStat;
    }

    public ArrayList<PtktSumByPrd> getPtktSumPrd() {
        ArrayList<PtktSumByPrd> sumByPrd = new ArrayList<>();
        sumByPrd.addAll(ptktPrdMap.values());
        Collections.sort(sumByPrd);
        return sumByPrd;
    }

    public PtktSumByPrd getPtktSumPrd(String prd) {
        PtktSumByPrd rtnSumByPrd = null;
        if (ptktPrdMap.containsKey(prd)) {
            rtnSumByPrd = ptktPrdMap.get(prd);
        }
        return rtnSumByPrd;
    }

    public ArrayList<PtkCarton> getPtkCartonList() {
        return ptkCartonList;
    }

    public ArrayList<PtktByPtkt> getPtktByPtkt() {
        Collections.sort(ptktList);
        return ptktList;
    }

    public ArrayList<PtktSumByPkr> getPtkByPkr() {
        ArrayList<PtktSumByPkr> sumByPkr = new ArrayList<>();
        sumByPkr.addAll(ptktPkrMap.values());
        Collections.sort(sumByPkr);
        return sumByPkr;
    }

    public PtktSumByPkr getPtktByPkr(String pkr) {
        PtktSumByPkr rtnByPkr = null;
        if (ptktPkrMap.containsKey(pkr)) {
            rtnByPkr = ptktPkrMap.get(pkr);
        }
        return rtnByPkr;
    }

    public ArrayList<KpiTblRow> getKpiTbl() {
        return kpiTbl;
    }

    public Map<String, KpiTblRow> getKpiTblMap() {
        return kpiTblMap;
    }

    public Double getCurDtNum() {
        return curDtNum;
    }

    public String getCurDt() {
        return curDt;
    }

}
