/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktData;

import com.a4.utils.ConnectAs400;
import com.ibm.as400.access.ZonedDecimalFieldDescription;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Khatchik
 */
public class AppParms {

    private static ArrayList<PtktStat> pstat = new ArrayList<>();
    private static ArrayList<PtktPrd> ptktPrd = new ArrayList<>();
    private static CNTLP cntlp;
    private static String systemName;
    private static String user;
    //private static String userPassWord;
    private static String dataLib;
    private static String dftWrh;
    private static String curDate;
    public static final String ASG_STAT_NAME = "ASG";
    public static final String CUS_STAT_NAME = "CUS";
    public static final String CUS_STAT_NUM = "06";
    public static final String PRT_STAT_NAME = "PRT";
    public static final String DIS_STAT_NAME = "DIS";
    public static final String PCK_STAT_NAME = "PCK";
    public static final String PCM_STAT_NAME = "PCM";
    public static final String PAK_STAT_NAME = "PAK";
    public static final String QUA_STAT_NAME = "QUA";
    public static final String DIS_STAT_NUM = "05";
    public static final String CPU_STAT_NAME = "CPU";
    public static final String SHP_STAT_NAME = "SHP";
    public static final String WGH_STAT_NAME = "WGH";
    public static final String INV_STAT_NAME = "INV";
    public static final String CPU_STAT_NUM = "49";
    public static final String UNASIGNED = "Unassigned";
    public static final String ALL = "*ALL";
    private static final ZoneId PST = ZoneId.of("America/Los_Angeles");
    private final ZoneId dftZone = ZoneId.systemDefault();
       
      /**
     * Period 2 Label
     */
    public static final String PRD2LBL = "04:00 am";
    /**
     * Period 2 row
     */
    public static final int PRD2ROW = 2;
    /**
     * Period 3 Label
     */
    public static final String PRD3LBL = "05:00 am";
    /**
     * period 3 Row
     */
    public static final int PRD3ROW = 3;
    /**
     * period 4 Label
     */
    public static final String PRD4LBL = "06:00 am";
    /**
     * period 4 Row
     */
    public static final int PRD4ROW = 4;
    public static final String PRD5LBL = "07:00 am";
    public static final int PRD5ROW = 5;
    public static final String PRD6LBL = "08:00 am";
    public static final int PRD6ROW = 6;
    public static final String PRD7LBL = "09:00 am";
    public static final int PRD7ROW = 7;
    public static final String PRD8LBL = "10:00 am";
    public static final int PRD8ROW = 8;
    public static final String PRD9LBL = "11:00 am";
    public static final int PRD9ROW = 9;
    public static final String PRD10LBL = "12:00 pm";
    public static final int PRD10ROW = 10;
    public static final String PRD11LBL = "1:00 pm";
    public static final int PRD11ROW = 11;
    public static final String PRD12LBL = "2:00 pm";
    public static final int PRD12ROW = 12;
    public static final String PRD13LBL = "3:00 pm";
    public static final int PRD13ROW = 13;
    public static final String PRD14LBL = "> 3:00 pm";
    public static final int PRD14ROW = 14;

    public AppParms(String systemName, String user, String userPassWord,
            String dataLib, String dftWrh) throws CustomException {
        cntlp = new CNTLP( ConnectAs400.getLib());
        pstat = cntlp.getPtktStatList();
        pstat.add(new PtktStat(DIS_STAT_NAME, new BigDecimal(DIS_STAT_NUM)));
        pstat.add(new PtktStat(CPU_STAT_NAME, new BigDecimal(CPU_STAT_NUM)));
        pstat.add(new PtktStat(CUS_STAT_NAME, new BigDecimal(CUS_STAT_NUM)));
        ptktPrd.add(new PtktPrd(PRD2LBL, PRD2ROW, 050000.0));
        ptktPrd.add(new PtktPrd(PRD3LBL, PRD3ROW, 060000.0));
        ptktPrd.add(new PtktPrd(PRD4LBL, PRD4ROW, 070000.0));
        ptktPrd.add(new PtktPrd(PRD5LBL, PRD5ROW, 080000.0));
        ptktPrd.add(new PtktPrd(PRD6LBL, PRD6ROW, 090000.0));
        ptktPrd.add(new PtktPrd(PRD7LBL, PRD7ROW, 100000.0));
        ptktPrd.add(new PtktPrd(PRD8LBL, PRD8ROW, 110000.0));
        ptktPrd.add(new PtktPrd(PRD9LBL, PRD9ROW, 120000.0));
        ptktPrd.add(new PtktPrd(PRD10LBL, PRD10ROW, 130000.0));
        ptktPrd.add(new PtktPrd(PRD11LBL, PRD11ROW, 140000.0));
        ptktPrd.add(new PtktPrd(PRD12LBL, PRD12ROW, 150000.0));
        ptktPrd.add(new PtktPrd(PRD13LBL, PRD13ROW, 160000.0));
        ptktPrd.add(new PtktPrd(PRD14LBL, PRD14ROW, 990000.0));
        this.systemName = systemName;
        this.user = user;
//        this.userPassWord = userPassWord;
        this.dataLib = dataLib;
        this.dftWrh = dftWrh;
        SimpleDateFormat datFmt = new SimpleDateFormat("yyyyMMdd");
        curDate = datFmt.format(new Date());
        
        if (!dftZone.equals(PST)) {
            adjPtkPrd();
        }

    }

    public static ArrayList<PtktStat> getPstat() {
        return pstat;
    }

    public static String getSystemName() {
        return systemName;
    }

    public static String getUser() {
        return user;
    }

    public static String getDataLib() {
        return dataLib;
    }

    public static String getDftWrh() {
        return dftWrh;
    }

    public static String getCurDate() {
        return curDate;
    }

    public static ArrayList<PtktPrd> getPtktPrdList() {
        return ptktPrd;
    }
    public static ZonedDateTime dec2ZoneDt(BigDecimal dateCnv, BigDecimal timCnv)
    throws Exception {
        LocalDateTime ldt;
        ldt = LocalDateTime.now();

        String dateFormat="yyyyMMdd HHmmss";
        DecimalFormat decFmt8 = new DecimalFormat("00000000");
        DecimalFormat decFmt6 = new DecimalFormat("000000");
        String strDat = decFmt8.format(dateCnv);
        String strTim = decFmt6.format(timCnv);
        String strDatTime;
        
        strDatTime = strDat
                + " "
                + strTim
                ;
        try {
        ldt = LocalDateTime.parse(strDatTime, DateTimeFormatter.ofPattern(dateFormat));
        } catch(Exception ex) {
            System.out.println("Invalid start date" + strDatTime );
            throw new Exception("Invalid Date");

        }
        
        ZonedDateTime pstTime = ldt.atZone(PST);
        ZonedDateTime rtnTime = pstTime.withZoneSameInstant(ZoneId.systemDefault());
        return rtnTime;
    }
    
    private void adjPtkPrd() {
        BigDecimal bCurDt= new BigDecimal(curDate);
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter dtFormatterLbl = DateTimeFormatter.ofPattern("hh:mm a");
        Double adjTime;  
        String adjTimeLbl ="";
        ZonedDateTime datTime = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), 
                        ZoneId.systemDefault());
        
        for(PtktPrd prd : ptktPrd) {
            if (prd.getPrdTim()== 990000.0 ) {
                prd.setPrdLbl("> " + adjTimeLbl);
                
            } else {
                try {
                 datTime = dec2ZoneDt(bCurDt,new BigDecimal(prd.getPrdTim()) );
                } catch(Exception ex ) {
                    
                }
                adjTime = new Double(datTime.format(dtFormatter));
                adjTimeLbl= datTime.minusHours(1)
                        .format(dtFormatterLbl);
               prd.setPrdTim(adjTime);
               prd.setPrdLbl(adjTimeLbl);
                                    
            }
            
        }
    }

}
