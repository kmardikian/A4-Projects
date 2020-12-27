/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prtPtkt;

import UPS.UpsResponse;
import com.a4.utils.ConnectAs400;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import pickticketprint.AppParms;
import pickticketprint.PtktData;
import pickticketprint.UpsShprParms;

/**
 *
 * @author Khatchik
 */
public class PrintPtkt {

    private final Connection con;
    private final String dtaLib;
    private final UpsShprParms shprParms;

    public PrintPtkt(UpsShprParms shprParms) {
        con = ConnectAs400.getConnection();
        dtaLib = shprParms.getAs400Lib();
        this.shprParms = shprParms;

    }

    public void print(PtktData ptktData, UpsResponse upsResponse, boolean rePrint) {

        String logoFileName;
        String ptktShprNo;
        Date wDate = new Date();
        Map ptktPrm = new HashMap();
        ArrayList<PkDtl> ptktDtl = new ArrayList<>();
        String printFileName = null;
        //String jasperTmpl = shprParms.getJasperLoc() + "pickTicket.jasper";
        String jasperTmpl = shprParms.getPdfLoc() + "pickTicket.jasper";
        logoFileName = shprParms.getCmpLogoLoc() + ptktData.getSoldTo() + ".png";

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(MediaSize.findMedia(8.5f, 14, Size2DSyntax.INCH));
        printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(shprParms.getPrinter(rePrint), null));

        File logoFile = new File(logoFileName);
        if (!logoFile.exists()) {
            logoFileName = shprParms.getCmpLogoLoc() + "A4_Small.png";
        }
        ptktPrm.put("cmpLogo", logoFileName);
        ptktPrm.put("soldTo", ptktData.getSoldTo());
        ptktPrm.put("shipToName", ptktData.getShpToNam());
        ptktPrm.put("shipToAdd1", ptktData.getShpToAdr().get(0));
        ptktPrm.put("shipToAdd2", ptktData.getShpToAdr().get(1));
        ptktPrm.put("shipToCity", ptktData.getShpToCty());
        ptktPrm.put("shipToStat", ptktData.getShpToSt());
        ptktPrm.put("shipToZip", ptktData.getShpToZip());
        ptktPrm.put("shipToPhone", ptktData.getShpToPh());
        ptktPrm.put("ptktNo", ptktData.getPtktNo());
        ptktPrm.put("orderNo", ptktData.getOrdNo());
        // ptktPrm.put("caseNo", ptktData.getCrtnId());
        ptktPrm.put("caseNo", ptktData.getCrtn128());
        ptktPrm.put("customerPO", ptktData.getOrPo());
        ptktPrm.put("shpFrNam", ptktData.getShpFrmNam());
        ptktPrm.put("shpFrAd1", ptktData.getShpFrmAdr().get(0));
        ptktPrm.put("shpFrStat", ptktData.getShpFrmSt());
        ptktPrm.put("shpFrCity", ptktData.getShpFrmCty());
        ptktPrm.put("shpFrZip", ptktData.getShpFrmZip());
        ptktPrm.put("terms", ptktData.getTerms());
        ptktPrm.put("shpCmp", ptktData.isShpCmp());
        ptktPrm.put("crtnCnt", ptktData.getCrtnCnt());
        ptktPrm.put("crtnSeq", ptktData.getCrtnSeqNo());
        if (ptktData.getBillOpt().equals("3")) {
            ptktShprNo = ptktData.getPyrShprNo();
        } else {
            ptktShprNo = ptktData.getShprShprNo();
        }
        ptktPrm.put("shprNo", ptktShprNo);

        ptktPrm.put("shprOpt", ptktData.getBillOpt());
        try {
            wDate = new SimpleDateFormat("yyyyMMdd").parse(ptktData.getOrDat().toString());
            ptktPrm.put("orDate", wDate);
            wDate = new SimpleDateFormat("yyyyMMdd").parse(ptktData.getOrStDat().toString());
            ptktPrm.put("startDate", wDate);
            wDate = new SimpleDateFormat("yyyyMMdd").parse(ptktData.getOrEnDat().toString());
            ptktPrm.put("compDate", wDate);

        } catch (ParseException ex) {
            AppParms.getLogger().log(Level.SEVERE, null, "Error parsing date for ptkt "
                    + ptktData.getPtktNo() + " " + ex);
        }
        //ptktPrm.put("prDat", ptktData.getPtktDat());
        ptktPrm.put("prDat", new Date());
        ptktPrm.put("webOrder", ptktData.getWebOrd());
        ptktPrm.put("shipVia", ptktData.getOrShVia());
        ptktPrm.put("upsLabel", upsResponse.getLblLoc());
        // sold to address 
        ptktPrm.put("soName", ptktData.getSoNam());
        ptktPrm.put("soAdr1", ptktData.getSoAd1());
        ptktPrm.put("soAdr2", ptktData.getSoAd2());
        ptktPrm.put("soAdr3", ptktData.getSoAd3());
        ptktPrm.put("soCity", ptktData.getSoCty());
        ptktPrm.put("soStat", ptktData.getSoSt());
        ptktPrm.put("soZip", ptktData.getSoZip());
        ptktPrm.put("soPhone", ptktData.getSoPh());
        if (upsResponse.getLblRspStat().getErrStat().equals("1")) {
            ptktPrm.put("upsErr", " ");
        } else {
            ptktPrm.put("upsErr", upsResponse.getLblRspStat().getErrMsg());
        }

        ArrayList<String> cmtList = getCmtList(ptktData.getOrdNo(), ptktData.getOrOty());

        for (int i = 0; i < 8; i++) {
            String key = "cmt" + String.valueOf(i + 1);
            ptktPrm.put(key, cmtList.get(i));
        }

        String qry = " select CDORLN "
                + ",CDSEAC "
                + ",CDSTYL"
                + ",CDCOLR"
                + ",CDSIZE"
                + ",CDPKUN"
                + ",STDES"
                + ",ifnull(STPISL,' ') as STPISL"
                + ",ifnull(STPBAY,' ') as STPBAY"
                + ",ifnull(STPLVL,' ') as STPLVL"
                + ",ifnull(STPPOS,' ') as STPPOS"
                + ",ifnull(d.cndes,' ') as COLOR_DES "
                + " from " + dtaLib + ".CCARPD a"
                + " join " + dtaLib + ".STYLPH b"
                + " on a.cdSeac = b.stSea"
                + " and a.cdStyl = b.stSty"
                + " left join " + dtaLib + ".STPKLOCP c "
                + " on STPWRH = '" + shprParms.getWhse() + "'"
                + " and STPSTY = cdStyl"
                + " and STPCLR = cdColr"
                + " and STPDIM = cdDime"
                + " and STPSIZ = cdSize"
                + " left join " + dtaLib + ".cntlp d "
                + " on a.CDCOLR = d.cnelk "
                + " and d.cnelt = 'CO' "
                + " where CDPCKP = " + ptktData.getPtktNo()
                + " and CDCAR# = " + ptktData.getCrtnId()
                + " order by "
                + "STPISL"
                + ",STPBAY"
                + ",STPLVL"
                + ",STPPOS";
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(qry)) {
            while (rs.next()) {
                ptktDtl.add(new PkDtl(rs.getBigDecimal("CDORLN").intValueExact(),
                        rs.getString("CDSEAC"),
                        rs.getString("CDSTYL"),
                        rs.getString("CDCOLR"),
                        rs.getString("COLOR_DES"),
                        rs.getString("CDSIZE"),
                        rs.getString("STDES"),
                        rs.getString("STPISL").trim() + "."
                        + rs.getString("STPBAY").trim() + "."
                        + rs.getString("STPLVL").trim() + "."
                        + rs.getString("STPPOS").trim() + ".",
                        rs.getBigDecimal("CDPKUN"),
                        ptktData.getPtktNo()
                ));

            }
            JRBeanCollectionDataSource beanDataCollection
                    = new JRBeanCollectionDataSource(ptktDtl);
            try {
                printFileName
                        = JasperFillManager.fillReportToFile(jasperTmpl, ptktPrm, beanDataCollection);
                if (printFileName != null) {
                    // Method 1 
                    exporter.setExporterInput(new SimpleExporterInput(printFileName));
                    configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
                    configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
                    configuration.setDisplayPageDialog(false);
                    configuration.setDisplayPrintDialog(false);
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();

                    //method 2
                    //JasperPrintManager.printReport(printFileName, true);
                    // export to pdf 
                    JasperExportManager.exportReportToPdfFile(printFileName,
                            shprParms.getPdfLoc() + ptktData.getPtktNo() + "_"
                            + ptktData.getCrtnId() + ".pdf");
                    if (!upsResponse.getLblImage().trim().isEmpty()) {
                        File upsLbl = new File(upsResponse.getLblLoc());
                        upsLbl.delete();
                    }
                }

            } catch (JRException ex) {
                AppParms.getLogger().log(Level.SEVERE,
                        "JRException Printing ptkt " + ptktData.getPtktNo() + " cart =" + ptktData.getCrtnId()
                        + " Err=" + ex.getLocalizedMessage());
                ex.printStackTrace();
                // Alert alert = new Alert(Alert.AlertType.ERROR, "Jasper report error filling report\n" + ex.getLocalizedMessage());
                // alert.setTitle("Pick ticket print Error ");
                // alert.showAndWait();
            }

        } catch (SQLException ex) {
            AppParms.getLogger().log(Level.SEVERE,
                    "SQLException  Printing ptkt " + ptktData.getPtktNo() + " cart =" + ptktData.getCrtnId()
                    + " Err=" + ex);
            ex.printStackTrace();
            // Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving Pick Ticked data\n" + ex.getLocalizedMessage());
            // alert.setTitle("Error retrieving Pick Ticked detail");
            // alert.showAndWait();
        } catch (Exception ex) {
            AppParms.getLogger().log(Level.SEVERE,
                    "Error Printing ptkt " + ptktData.getPtktNo() + " cart =" + ptktData.getCrtnId()
                    + " Err=" + ex.getLocalizedMessage());
            ex.printStackTrace();
        }

    }

    private ArrayList<String> getCmtList(String ordNo, String orOty) {
        ArrayList<String> orCmtList = new ArrayList<>();
        if (orOty.equals("H")) {
            orCmtList.add("Replacement order - must be checked by management");
        }
        String qry = "Select orfld "
                + "from " + dtaLib + ".ordcop "
                + "where orord = " + ordNo
                + " and orcmc like'%P%'";

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(qry)) {
            while (rs.next()) {
                if (!rs.getString("ORFLD").trim().isEmpty()) {
                    orCmtList.add(rs.getString("ORFLD"));
                }
            }
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving Pick Ticked comments\n" + ex.getLocalizedMessage());
            alert.setTitle("Error retrieving Pick Ticked comments");
            alert.showAndWait();
        }
        int i = 8 - orCmtList.size();
        if (i > 0) {
            for (int j = 1; j <= i; j++) {
                orCmtList.add(" ");
            }
        }
        return orCmtList;
    }

    public class PkDtl {

        Integer line;
        String sea;
        String style;
        String color;
        String colorDes;
        String size;
        String description;
        String pickLoc;
        Long pickUnits;
        String pickTktNo;

        public PkDtl(Integer line, String sea, String style, String color,
                String colorDes, String size, String description, String pickLoc,
                BigDecimal pickUnits, String pickTktNo) {
            this.line = line;
            this.sea = sea;
            this.style = style;
            this.color = color;
            this.colorDes = colorDes;
            this.size = size;
            this.description = description;
            this.pickLoc = pickLoc;
            this.pickUnits = pickUnits.longValue();
            this.pickTktNo = pickTktNo;
        }

        public Integer getLine() {
            return line;
        }

        public void setLine(Integer line) {
            this.line = line;
        }

        public String getSea() {
            return sea;
        }

        public void setSea(String sea) {
            this.sea = sea;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPickLoc() {
            return pickLoc;
        }

        public void setPickLoc(String pickLoc) {
            this.pickLoc = pickLoc;
        }

        public Long getPickUnits() {
            return pickUnits;
        }

        public void setPickUnits(BigDecimal pickUnits) {
            this.pickUnits = pickUnits.longValue();
        }

        public String getPickTktNo() {
            return pickTktNo;
        }

        public void setPickTktNo(String pickTktNo) {
            this.pickTktNo = pickTktNo;
        }

        public String getColorDes() {
            return colorDes;
        }

        public void setColorDes(String colorDes) {
            this.colorDes = colorDes;
        }

    }

}
