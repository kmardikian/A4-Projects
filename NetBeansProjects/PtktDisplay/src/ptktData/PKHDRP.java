/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

import com.a4.utils.ConnectAs400;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ptktFilter.PtktFilter;

/**
 *
 * @author Khatchik
 */
public class PKHDRP {

    private  Connection con;
    private final String dtaLib;
    private final PtktFilter ptktFilter;
    boolean firstSel = false;
    //private final PreparedStatement ps;

    public PKHDRP( String dtaLib, PtktFilter ptktFilter) {
       // this.con = con;
        this.dtaLib = dtaLib;
        this.ptktFilter = ptktFilter;
    }

    public synchronized PtktSum getOpnPtktByStat() throws CustomException {
        this.con = ConnectAs400.getConnection();

       // ArrayList<PtktByPtkt> ptktList = new ArrayList<>();
       ArrayList<PtkCarton> ptkCarton = new ArrayList<>();
        SimpleDateFormat datFmt = new SimpleDateFormat("yyyyMMdd");
        String today = datFmt.format(new Date());

        
        String qry = "Select PKHPCK "
                + ",cdCar#"
                + ",PKHORD "
                + ",PKHSOL "
                + ",PKHSHP "
                + ",PKHWRH "
                + ",totu" // from inner sql 
                + ",totd" // frp, inner sql 
                + ",PKHVIA"
                + ",skuCnt"
                + ",case when PKHVIA = 'CPU' and c.cnelk = 'QUA' then "
                + "'" + AppParms.CPU_STAT_NAME + "' "
                + "when oroty = 'D' then '" + AppParms.CUS_STAT_NAME + "'"
                + "when PKHDISF = 'Y' "
                + "and c.cnelk <> '" + AppParms.INV_STAT_NAME + "' then " 
                + " '" + AppParms.DIS_STAT_NAME + "' "
                + "else c.cnelk end as stat "   // pick ticket status 
                                
                + ",case when  PKHDISF = 'Y' then "
                + AppParms.DIS_STAT_NUM + " "
                + "when  PKHVIA = 'CPU' and c.cnelk = 'PAK' then "
                + AppParms.CPU_STAT_NUM + " "
                + "else pkhsta end as pkhsta " // pick ticket status #
                
                + ",ifNull(d.cndes,pkhopr) OPR "
                + ",PKHPDT "
                + ",PKHPTM "
                + ",PKHSDT "
                + ",PKHTDT "
                + ",CUNAM "
                + ",OROTY "
                + ",ifnull(i.chSdt,0) chsdt"
                + ",ifnull(i.chstm,0) chstm"
                + ",ifnull(i.chedt,0) chedt"
                + ",ifnull(i.chEtm,0) chetm"
                + ",ifnull(i.chOpcd,' ') chOpcd"
                + ",ifnull(i.chOpr,' ') chopr"
                + ",ifnull(i.oprnam,' ') ORPNAM "
                + ",ifnull(f.ornam, cunam) SHPNAM"
                + ",ORPO#"
                + ",ORREM"
                + ",ORPRL"
               // + ",k.CRTNCNT"
                // + ",ifNull(g.PICKER,' ') PICKER "
                + ",ifNull(h.cndes,' ') PICKER "
           // get total units, total $, and sku count from carton detail subquery      
                + "From (select cdpckp "
                + ",cdCar#"
                + ",dec(sum(cdpkun),9,0) totu"
                + ",dec(sum(ord$),15,2) totD"
                + ",dec(count(*),5,0) skuCnt"
                // inner sql within inner sql
                + " from(select cdpckp"
                + ",cdcar#"
                + ",cdorln"
                + ",cdpkun"
                + ",dec((cdpkun * orpri),9,2) ord$ "
                + "from " + dtaLib + ".ccarpd "
                + "join " + dtaLib + ".ordrpd "
                + "on orpck = cdpckp "
                + "and orlne = cdOrln "
                + "where cdpkun<>0 "
                + "and (ordst =' ' or (ordst = 'S' and orltd= " + today + "))"
                + ") a "
                + "group by cdpckp,cdcar#) l "
                + "join " + dtaLib + ".PKHDRP a "
                + "on a.pkhpck = l.cdpckp "
                + "join " + dtaLib + ".ORDRPH b "
                + "on b.orord = a.pkhord "
                + "join " + dtaLib + ".CNTLP c "
                + "on c.cnelt = '90' "
                + "And c.cnva2 = pkhsta "
                + "left join " + dtaLib + ".CNTLP d "
                + "on d.cnelt = '91' "
                + " and d.cnelk = pkhopr "
                + "join " + dtaLib + ".custp e "
                + "on e.cusol = pkhsol "
                + "and e.cushp = PKHSHP "
                + " left join " + dtaLib + ".ordshp f "
                + " on f.orOrd = pkhord "
                + " left join " + dtaLib + ".cntlp h "
                + " on h.cnelt = '91'"
                + " and h.cnelk = pkhAsgn "
                + " join "
                + " (select chpck#"
                + ",chcar#"
                + ",chopcd"
                + ",chopr"
                + ",cndes oprnam"
                + ",chsdt"
                + ",chstm"
                + ",chedt"
                + ",chetm "
                + "from " + dtaLib + ".ccarph "                           
                + "left join " + dtaLib + ".cntlp "                       
                + "on cnelt = '91' "                                
                + "and cnelk = chopr " 
                + " ) i "
                + "on a.pkhpck = i.chpck# "
                + "and l.cdcar# = chcar#"
//                + "(select pksPck"
//                + ",pksCar#"
//                + ",pksOpcd"
//                + ",pkssta"
//                + ",pksOpr"
//                + ",pksSdt"
//                + ",pksStm"
//                + ",pksEdt"
//                + ",pksETm"
//                + ",dec(row_number() "
//                + "over (PARTITION BY PKSPCK,PKSCAR# "
//                + "order by pkssdt, pksstm desc ),5,0) rownum "
//                + "from " + dtaLib + ".pkhstp) i "
//                + "ON a.PKHPCK = i.PKSPCK "
//                + "and l.cdcar# = i.pksCAR# " 
//                + "and i.rownum=1"
                ;
        firstSel = false;

        if (!ptktFilter.getPrtDatFr().trim().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhpdt >= " + ptktFilter.getPrtDatFr();
        }
        if (!ptktFilter.getPrtDatTo().trim().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhpdt <= " + ptktFilter.getPrtDatTo();
        }
        if (!ptktFilter.getStrDatFr().trim().isEmpty()) {
            qry = qry + detWhereAnd() + "PKHSDT >= " + ptktFilter.getStrDatFr();
        }
        if (!ptktFilter.getStrDatTo().trim().isEmpty()) {
            qry = qry + detWhereAnd() + "PKHSDT <= " + ptktFilter.getStrDatTo();
        }

        if (!ptktFilter.getWrhList().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhwrh in(";
            boolean firstWhSel = true;
            ArrayList<String> wrhList = ptktFilter.getWrhList();

            for (String wrh : wrhList) {
                if (!wrh.isEmpty()) {
                    if (firstWhSel) {
                        firstWhSel = false;
                        qry = qry + "'" + wrh + "'";
                    } else {
                        qry = qry + ",'" + wrh + "'";
                    }
                }
            }
            qry = qry + ")";
        }
        // Ship via selection 

        if (!ptktFilter.getShipViaList().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhVia in(";
            boolean firstWhSel = true;
            ArrayList<String> sViaList = ptktFilter.getShipViaList();

            for (String sVia : sViaList) {
                if (!sVia.isEmpty()) {
                    if (firstWhSel) {
                        firstWhSel = false;
                        qry = qry + "'" + sVia.toUpperCase() + "'";
                    } else {
                        qry = qry + ",'" + sVia.toUpperCase() + "'";
                    }
                }
            }
            qry = qry + ")";
        }

        //Sold to Selection 
        if (!ptktFilter.getSoldToList().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhSol in(";
            boolean firstWhSel = true;
            ArrayList<String> soldToList = ptktFilter.getSoldToList();

            for (String soldTo : soldToList) {
                if (!soldTo.isEmpty()) {
                    if (firstWhSel) {
                        firstWhSel = false;
                        qry = qry + "'" + soldTo + "'";
                    } else {
                        qry = qry + ",'" + soldTo + "'";
                    }
                }
            }
            qry = qry + ")";
        }

        // Order range selection 
        if (!ptktFilter.getOrdFr().trim().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhOrd >= " + ptktFilter.getOrdFr();
        }
        if (!ptktFilter.getOrdTo().trim().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhOrd <= " + ptktFilter.getOrdTo();
        }

        // Pick ticket selection 
        // Order range selection 
        if (!ptktFilter.getPtktFr().trim().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhPck >= " + ptktFilter.getPtktFr();
        }
        if (!ptktFilter.getPtktTo().trim().isEmpty()) {
            qry = qry + detWhereAnd() + "pkhPck <= " + ptktFilter.getPtktTo();
        }

        // + "Where PKHPDT=" + today
        // + " and PKHWRH='VN'";
        //                   + "WHERE PKHSTA < 85 "
        //                   + ") c " 
        //                   + "GROUP BY stat,pkhsta "
        //                   + "order by pkhsta ";
//        System.out.println("sql:" + qry);
        try (Statement stmt = con.createStatement()) {
//             System.out.println("SQL =" + qry);
//            stmt = con.createStatement();
            PtktSum ptktSum;
            try (ResultSet rs = stmt.executeQuery(qry)) {
                // String pkStat;
                while (rs.next()) {
                   ptkCarton.add(new PtkCarton(
                           rs.getBigDecimal("CDCAR#"),
                           rs.getBigDecimal("PKHPCK"),
                            rs.getBigDecimal("PKHORD"),
                            rs.getString("PKHSOL"),
                            rs.getString("PKHSHP"),
                            rs.getString("PKHWRH"),
                            rs.getBigDecimal("TOTU"),
                            rs.getBigDecimal("TOTD"),
                            rs.getString("PKHVIA"),
                            rs.getString("STAT"),
                            rs.getBigDecimal("PKHSTA"),
                            rs.getString("OPR").trim(),
                            rs.getString("ORPNAM").trim(),
                           rs.getBigDecimal("PKHPDT").doubleValue(),
                            rs.getBigDecimal("PKHPTM").doubleValue(),
                            rs.getBigDecimal("PKHSDT"),
                            rs.getBigDecimal("PKHTDT"),
                            rs.getString("CUNAM").trim(),
                            rs.getString("OROTY"),
                        //    rs.getString("STGDATTM"),
                            rs.getString("PICKER"),
                            rs.getString("SHPNAM"),
                            rs.getInt("SKUCNT")
                           ,rs.getString("CHOPCD")
                           ,rs.getBigDecimal("CHSDT")
                           ,rs.getBigDecimal("CHSTM")
                           ,rs.getBigDecimal("CHEDT")
                           ,rs.getBigDecimal("CHETM")
                           ,rs.getString("ORPO#")
                           ,rs.getString("ORREM")
                           ,rs.getBigDecimal("ORPRL")
                    ));
                }
                Collections.sort(ptkCarton);
                ptktSum = new PtktSum(today);
                ptktSum.add(ptkCarton);
            }
            //stmt.close();
            return ptktSum;
        } catch (SQLException ex) {
            System.out.println("SQL stmt = " + qry);
            ex.printStackTrace();
            Logger.getLogger(PKHDRP.class.getName()).log(Level.SEVERE, null, ex);
            
            throw new CustomException("Error Getting open pick tickets"
                    + "\nErr =" + ex.getLocalizedMessage()
                    + "\nSQL=" + qry);

        }
    }

    private String detWhereAnd() {
        String result;
        if (!firstSel) {
            firstSel = true;
            result = " Where ";
        } else {
            result = " And ";
        }

        return result;
    }

    public ResultSet getPtktDtlRs(String inStat) {
        String selStat;
        Statement stmt;
        ResultSet ptktRs = null;
        if (inStat.equals("PRT")) {
            selStat = " ";
        } else {
            selStat = inStat;
        }
        String qry = "Select "
                + "PKHPCK"
                + ",PKHORD"
                + ",PKHSOL"
                + ",PKHSHP"
                + ",PKHWRH"
                + ",PKHUNT"
                + ",PKHSTA"
                + ",PKHOPCD"
                + ",PKHOPR"
                + ",PKHPDT"
                + ",PKHPTM "
                + "FROM " + dtaLib + ".PKHDRP "
                + "join (select orpck "
                + ",int(sum(orotq)) as totu "
                + ",dec(sum(orotq*orpri),13,2) as totd  "
                + "from " + dtaLib + ".ORDRPD "
                + "where orpck <> 0 and ordst = ' ' "
                + "group by orpck"
                + ") b "
                + "ON ORPCK = PKHPCK "
                + "Where PKHOPCD= '" + selStat + "'"
                + "order by pkhPdt desc"
                + ",pkhPck desc ";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(qry);
        } catch (Exception ex) {
            return null;
        }

        return ptktRs;
    }

}
