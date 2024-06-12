/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktDetailDisplay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
//import javafx.scene.control.Cell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.*;
import ptkDetailByCrtn.PickTicketDetailByCrtnController;
import ptktData.AppParms;
import ptktData.PtktByPtkt;
import ptktData.PtktSum;
import ptktdisplay.FXMLDocumentController;

/**
 * FXML Controller class
 *
 * @author Khatchik
 */
public class PickTicketDetailController implements Initializable {

    /**
     * Initializes the controller class.
     */
//    @FXML
//    private Accordion acPane;
    @FXML
    private Label lblTotU;

    @FXML
    private Label lblTotD;

    @FXML
    private Label lblPtkCnt;

    @FXML
    private Label lblCtnCnt;

    @FXML
    private TableView<PtktDetailRow> tblPtkDtl;

    @FXML
    private TableColumn<PtktDetailRow, String> tcPtkt;

    @FXML
    private TableColumn<PtktDetailRow, String> tcOrder;

    @FXML
    private TableColumn<PtktDetailRow, String> tcSoldTo;

    @FXML
    private TableColumn<PtktDetailRow, String> tcShipTo;
    @FXML
    private TableColumn<PtktDetailRow, String> tcCustNam;
    @FXML
    private TableColumn<PtktDetailRow, String> tcShpNam;
    @FXML
    private TableColumn<PtktDetailRow, String> tcPoNo;

    @FXML
    private TableColumn<PtktDetailRow, String> tcWebOrd;

    @FXML
    private TableColumn<PtktDetailRow, String> tcOrTyp;
    @FXML
    private TableColumn<PtktDetailRow, String> tcShipVia;

    @FXML
    private TableColumn<PtktDetailRow, String> tcWhse;
     @FXML
    private TableColumn<PtktDetailRow, Integer> tcOrPrl;

    @FXML
    private TableColumn<PtktDetailRow, Integer> tcCtn;

    @FXML
    private TableColumn<PtktDetailRow, Double> tcUnits;
    @FXML
    private TableColumn<PtktDetailRow, Double> tcDollars;

    @FXML
    private TableColumn<PtktDetailRow, String> tcStat;

    @FXML
    private TableColumn<PtktDetailRow, String> tcStDat;

    @FXML
    private TableColumn<PtktDetailRow, String> tcStTim;
    @FXML
    private TableColumn<PtktDetailRow, String> tcDur;
    @FXML
    private TableColumn<PtktDetailRow, String> tcOpr;
    @FXML
    private TableColumn<PtktDetailRow, String> tcStrDt;
    @FXML
    private TableColumn<PtktDetailRow, String> tcCmpDt;

    @FXML
    private TableColumn<PtktDetailRow, String> tcPrtDt;

    @FXML
    private TableColumn<PtktDetailRow, String> tcPrtTime;

    @FXML
    private Button btnExport;

    final ContextMenu contextMenu = new ContextMenu();
    final MenuItem mnuDspCrtDtl = new MenuItem("Display Carton Detail");

    private PtktSum ptktsum;
    private String test;
    private String fltrPrd;
    private String fltrStat;
    private String fltrPkr;
    private final String COL_PTKT_NO = "Pick Ticket";
    private final String COL_ORD_NO = "Order";
    private final String COL_SOLD_TO = "Sold To";
    private final String COL_SHIP_TO = "Ship to";
    private final String COL_SHIP_TO_NAM = "Ship to Name";
    private final String COL_SHIP_VIA = "Ship Via";
    private final String COL_WHSE = "Whse";
    private final String COL_ORPRL ="Price Level";
    private final String COL_CTN_CNT = "Total Cartons";
    private final String COL_UNITS = "Units";
    private final String COL_DOLLARS = "Dollars";
    private final String COL_STAT = "Status";
    private final String COL_STAT_ST_DATE = "Stat Start Date";
    private final String COL_STAT_ST_TIME = "Stat Start Time";
    private final String COL_STAT_DUR = "Stat Dur";
    private final String COL_OPR = "Operator";
    private final String COL_ORD_STRDT = "Order\nStart Date";
    private final String COL_ORD_CMPDT = "Order Comp. Date";
    private final String COL_PRT_DT = "Print Date";
    private final String COL_PRT_TM = "Print Time";
    private final String COL_CUS_NAM = "Customer Name";
    private final String COL_ORD_TYP = "Order Type";
    private final String COL_PO_NO = "PO #";
    private final String COL_WEB_ORD = "Web Ord/Remarks";
    // private final String TEST = "test";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fltrPrd = " ";
        fltrStat = " ";
        fltrPkr = AppParms.ALL;
        ImageView ivDetail = new ImageView(new Image(getClass().getResourceAsStream("/res/excel.png")));
        ivDetail.setFitHeight(15);
        ivDetail.setFitWidth(15);
        btnExport.setGraphic(ivDetail);

        // TODO
    }

    public void setPtktsum(PtktSum ptktsum) {
        this.ptktsum = ptktsum;
        this.test = "init";
//        loadDtlDsp();
    }

    public void setFltrPrd(String fltrPrd) {
        this.fltrPrd = fltrPrd;
    }

    public void setFltrStat(String fltrStat) {
        this.fltrStat = fltrStat;
    }

    public void setFltrPkr(String fltrPkr) {
        this.fltrPkr = fltrPkr;
    }

    public void loadDtlDsp() {
        ArrayList<PtktByPtkt> ptktList;
        ObservableList<PtktDetailRow> ptktDetailRow = FXCollections.observableArrayList();
        ptktList = ptktsum.getPtktByPtkt();
        BigDecimal totU = BigDecimal.ZERO;
        BigDecimal totD = BigDecimal.ZERO;
        int totCtn = 0;
        int totPck = 0;
        Locale enUSLocale
                = new Locale.Builder().setLanguage("en").setRegion("US").build();
        NumberFormat currencyFormatter
                = NumberFormat.getCurrencyInstance(enUSLocale);
        DecimalFormat numFormatter = new DecimalFormat("#,###");

        // ObservableList<TitledPane> tps;
        for (PtktByPtkt ptktRec : ptktList) {
            if (fltrPtkt(ptktRec)) {
                ptktDetailRow.add(new PtktDetailRow(ptktRec.getPtktNo(),
                        ptktRec.getOrdNo(),
                        ptktRec.getSoldTo(),
                        ptktRec.getShipTo(),
                        ptktRec.getsVia(),
                        ptktRec.getWhse(),
                        ptktRec.getTotu(),
                        ptktRec.getTotd(),
                        ptktRec.getStatus().trim(),
                        ptktRec.getOperator().trim(),
                        ptktRec.getPrtDate(),
                        ptktRec.getPrtTime(),
                        ptktRec.getOrStrtDt(),
                        ptktRec.getOrCmpDt(),
                        ptktRec.getCusName(),
                        ptktRec.getShpToNam(),
                        ptktRec.getOrdTyp(),
                        ptktRec.getStgDatTm(),
                        ptktRec.getCrtnCnt(),
                        ptktRec.getPoNo(),
                        ptktRec.getWebOrd()
                        ,ptktRec.getOrPrl()
                ));
                totU = totU.add(ptktRec.getTotu());
                totD = totD.add(ptktRec.getTotd());
                totCtn += ptktRec.getCrtnCnt();
                totPck += 1;
            }
        }
        lblTotU.setText(numFormatter.format(totU));
        lblTotD.setText(currencyFormatter.format(totD));
        lblPtkCnt.setText(numFormatter.format(totPck));
        lblCtnCnt.setText(numFormatter.format(totCtn));
        if (!ptktDetailRow.isEmpty()) {
            btnExport.setDisable(false);
        }
        tcPtkt.setCellValueFactory(cellData -> cellData.getValue().getPtktNo());
        tcOrder.setCellValueFactory(cellData -> cellData.getValue().getOrdNo());
        tcSoldTo.setCellValueFactory(cellData -> cellData.getValue().getSoldTo());
        tcShipTo.setCellValueFactory(cellData -> cellData.getValue().getShipTo());
        tcPoNo.setCellValueFactory(cellData -> cellData.getValue().getPoNo());
        tcWebOrd.setCellValueFactory(cellData -> cellData.getValue().getWebOrd());
        tcCustNam.setCellValueFactory(cellData -> cellData.getValue().getCusName());
        tcShpNam.setCellValueFactory(cellData -> cellData.getValue().getShpName());
        tcOrTyp.setCellValueFactory(cellData -> cellData.getValue().getOrdType());
        tcShipVia.setCellValueFactory(cellData -> cellData.getValue().getsVia());
        tcWhse.setCellValueFactory(cellData -> cellData.getValue().getWhse());
        tcOrPrl.setCellValueFactory(cellData -> cellData.getValue().getOrPrl().asObject());
        tcCtn.setCellValueFactory(cellData -> cellData.getValue().getTotCtn().asObject());
        tcUnits.setCellValueFactory(cellData -> cellData.getValue().getUnits().asObject());
        tcUnits.setCellFactory(col -> new TableCell<PtktDetailRow, Double>() {
            @Override
            public void updateItem(Double units, boolean empty) {
                super.updateItem(units, empty);
                DecimalFormat numFormatter = new DecimalFormat("###,###,###");
                if (empty) {
                    setText(null);
                } else {
                    setText(numFormatter.format(units));
                }
            }
        });

        tcDollars.setCellValueFactory(cellData -> cellData.getValue().getDollars().asObject());
        tcDollars.setCellFactory(col -> new TableCell<PtktDetailRow, Double>() {
            @Override
            public void updateItem(Double dlrs, boolean empty) {
                DecimalFormat numFormatter = new DecimalFormat("$#,###.00");
                super.updateItem(dlrs, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(numFormatter.format(dlrs));
                }
            }
        });
        tcStat.setCellValueFactory(cellData -> cellData.getValue().getStatus());
        tcStDat.setCellValueFactory(cellData -> cellData.getValue().getStgDatTm());
        tcStDat.setCellFactory(col -> new TableCell<PtktDetailRow, String>() {
            @Override
            public void updateItem(String stDat, boolean empty) {
                super.updateItem(stDat, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(stDat.substring(4, 6) + "/"
                            + stDat.substring(6, 8) + "/"
                            + stDat.substring(0, 4));
                }
            }
        });
        tcStTim.setCellValueFactory(cellData -> cellData.getValue().getStgTime());
        tcStTim.setCellFactory(cell -> new TableCell<PtktDetailRow, String>() {
            @Override
            public void updateItem(String stTim, boolean empty) {
                super.updateItem(stTim, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(stTim.substring(0, 2) + ":"
                            + stTim.substring(2, 4) + ":"
                            + stTim.substring(4, 6));
                }
            }
        });
        tcDur.setCellValueFactory(cellData -> cellData.getValue().getTimDif());
        tcOpr.setCellValueFactory(cellData -> cellData.getValue().getOperator());
        tcStrDt.setCellValueFactory(cellData -> cellData.getValue().getOrStrDt());
        tcCmpDt.setCellValueFactory(cellData -> cellData.getValue().getOrCmpDt());
        tcPrtDt.setCellValueFactory(cellData -> cellData.getValue().getPrtDate());
        tcPrtTime.setCellValueFactory(cellData -> cellData.getValue().getPrtTime());

        tblPtkDtl.setItems(ptktDetailRow);

        mnuDspCrtDtl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final PtktDetailRow selDtlRow = tblPtkDtl.getSelectionModel().getSelectedItem();
                dspCrtDtl(selDtlRow.getPtktNo().getValue());
            }
        });
        contextMenu.getItems().add(mnuDspCrtDtl);

        tblPtkDtl.setRowFactory(e -> {
            TableRow<PtktDetailRow> row = new TableRow<>();
            row.setContextMenu(contextMenu);
            return row;
        });
//        tps = acPane.getPanes();
//        for (int i = 0; i < tps.size(); i++) {
//            if (tps.get(i).getText().equals("Pick ticket Detail")) {
//                acPane.setExpandedPane(tps.get(i));
//            }
//        }
        // String  x = tps.get(0).getText();
        //  x= tps.get(1).getText();
    }

    private void dspCrtDtl(String ptkNo) {
        PickTicketDetailByCrtnController ptktDtlCrtnCtl;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ptkDetailByCrtn/PickTicketDetailByCrtn.fxml"));
            //   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ptktDetailDisplay/PickTicketDetail.fxml"));

            Parent dtlRoot = (Parent) fxmlLoader.load();
            ptktDtlCrtnCtl = (PickTicketDetailByCrtnController) fxmlLoader.getController();
            ptktDtlCrtnCtl.setFltrPrd(" ");
            ptktDtlCrtnCtl.setFltrStat(" ");
            ptktDtlCrtnCtl.setFltrPkr(AppParms.ALL);
            ptktDtlCrtnCtl.setFltrPtk(ptkNo);
            ptktDtlCrtnCtl.setPtktsum(ptktsum);

            ptktDtlCrtnCtl.bldCrtnDspl();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Pick Ticket Detail by Carton");
            stage.setScene(new Scene(dtlRoot));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/cartons.png")));

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean fltrPtkt(PtktByPtkt ptktRec) {
        boolean result = true;
        if (!" ".equals(fltrPrd)) {
            if (!ptktRec.getPeriod().equals(fltrPrd)) {
                result = false;
            }
        }
        if (!" ".equals(fltrStat)) {
            if (fltrStat.equals(AppParms.QUA_STAT_NAME)) {
                if (!ptktRec.getStatus().trim().equals(fltrStat)
                        && !ptktRec.getStatus().trim().equals(AppParms.PAK_STAT_NAME)) {
                    result = false;
                }
            } else {
                if (!ptktRec.getStatus().trim().equals(fltrStat)) {
                    result = false;
                }
            }
        }

        if (!AppParms.ALL.equals(fltrPkr)) {
            if (!ptktRec.getPkrOpr().equals(fltrPkr)) {
                result = false;
            }
        }

        return result;
    }

    public void btnExportClicked(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Excel Files", "*.XLS"));

        fc.setTitle("Choose Export File");

        File selFile = fc.showSaveDialog(null);
        //if (!selFile.getAbsolutePath().toUpperCase().endsWith(".XLS")) {
        //    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid file type " + selFile.getAbsolutePath());
        //    alert.showAndWait();
        //    return;
        // }

        if (selFile != null) {
            System.out.println("file name is " + selFile.getAbsolutePath());
            exportPtktData(selFile);
        }

    }

    private void exportPtktData(File selFile) {
        ObservableList<TableColumn<PtktDetailRow, ?>> tblCol;
        ObservableList<PtktDetailRow> tblRows;
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Pick ticket List");
        // Hdr Font and Style
        Font hdrFont = wb.createFont();
        hdrFont.setFontHeight((short) 12);
        hdrFont.setFontName("Arial");
        CellStyle hdrCellStyl = wb.createCellStyle();
        hdrCellStyl.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        hdrCellStyl.setBorderBottom(BorderStyle.THICK);
        hdrCellStyl.setFillPattern(FillPatternType.NO_FILL);
        //hdrCellStyl.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        //hdrCellStyl.setFont(hdrFont);
        // Detail Font and style.
        Font dtlFont = wb.createFont();
        dtlFont.setFontHeight((short) 10);
        dtlFont.setFontName("Arial");
        CellStyle dtlCellStyl = wb.createCellStyle();
        dtlCellStyl.setBorderBottom(BorderStyle.THICK);
        dtlCellStyl.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        dtlCellStyl.setFont(dtlFont);

        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle datCellStyle = wb.createCellStyle();
        //datCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        datCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yy"));

        tblCol = tblPtkDtl.getColumns();
        Calendar wDate = Calendar.getInstance();
        //  Calendar wStatDt = Calendar.getInstance();
        String stTime;
        String sDate;
        String sTime;
        //String sUnits;
        //String sDollars;
        Double dUnits;
        Double dDollars;
        int wYear;
        int wMon;
        int wDa;
        int wHr;
        int wMin;
        int wSec;

        short colIx = 0;
        int rowIx = 0;
        Row row = sheet.createRow(rowIx++);
        for (TableColumn<PtktDetailRow, ?> col : tblCol) {
            //System.out.println("getting col hdr " + col.getText());
            Cell cell = row.createCell(colIx);
            cell.setCellValue(col.getText());
            cell.setCellStyle(hdrCellStyl);

            switch (col.getText()) {
                case COL_PTKT_NO:
                    setColWidth(sheet, colIx, 10);
                    break;
                case COL_ORD_NO:
                    setColWidth(sheet, colIx, 10);
                    break;
                case COL_SOLD_TO:
                    setColWidth(sheet, colIx, 10);
                    break;
                case COL_SHIP_TO:
                    setColWidth(sheet, colIx, 10);
                    break;
                case COL_CUS_NAM:
                    setColWidth(sheet, colIx, 30);
                    break;
                case COL_SHIP_TO_NAM:
                    setColWidth(sheet, colIx, 30);
                    break;
                case COL_ORD_TYP:
                    setColWidth(sheet, colIx, 10);
                    break;
                case COL_PO_NO:
                    setColWidth(sheet, colIx, 15);
                    break;
                case COL_WEB_ORD:
                    setColWidth(sheet, colIx, 20);
                    break;
                case COL_STAT_ST_DATE:
                    setColWidth(sheet, colIx, 20);
                    break;
                case COL_STAT_ST_TIME:
                    setColWidth(sheet, colIx, 10);
                    break;
                case COL_STAT_DUR:
                    setColWidth(sheet, colIx, 25);
                    break;
                case COL_PRT_DT:
                    setColWidth(sheet, colIx, 20);
                    break;
                case COL_ORD_STRDT:
                    setColWidth(sheet, colIx, 20);
                    break;
                case COL_ORD_CMPDT:
                    setColWidth(sheet, colIx, 20);
                    break;
                case COL_OPR:
                    setColWidth(sheet, colIx, 30);
                    break;
            }
            colIx++;

        }

        tblRows = tblPtkDtl.getItems();
        for (PtktDetailRow tr : tblRows) {
            row = sheet.createRow(rowIx++);
//            sDate = tr.getPrtDate().getValue();
//            sTime = tr.getPrtTime().getValue();
//            wMon = Integer.valueOf(sDate.substring(0, 2)) - 1;
//            wDa = Integer.valueOf(sDate.substring(3, 5));
//            wYear = Integer.valueOf(sDate.substring(6, 10));
//            wHr = Integer.valueOf(sTime.substring(0, 2));
//            wMin = Integer.valueOf(sTime.substring(3, 5));
//            wSec = Integer.valueOf(sTime.substring(6, 8));
//            wDate.set(wYear, wMon, wDa, wHr, wMin, wSec);
//            sDate = tr.getStgDatTm().getValue();
//            wYear = Integer.valueOf(sDate.substring(0, 4));
//            wMon = Integer.valueOf(sDate.substring(4, 6)) - 1;
//            wDa = Integer.valueOf(sDate.substring(6, 8));
//            wHr = Integer.valueOf(sDate.substring(8, 10));
//            wMin = Integer.valueOf(sDate.substring(10, 12));
//            wSec = Integer.valueOf(sDate.substring(12, 14));
//            wStatDt.set(wYear, wMon, wDa, wHr, wMin, wSecww
            dDollars = tr.getDollars().getValue();
            dUnits = tr.getUnits().getValue();
            stTime = tr.getStgTime().getValue();
            // sUnits = sUnits.replace(",", "");
            //dUnits = Double.valueOf(sUnits);
            // sDollars = sDollars.replace("$", "");
            //sDollars = sDollars.replace(",", "");
            // dDollars = Double.valueOf(sDollars);
            for (colIx = 0; colIx < tblCol.size(); colIx++) {
                TableColumn<PtktDetailRow, ?> col = tblCol.get(colIx);
                String tst = col.getText();
                Cell cell = row.createCell(colIx);

                switch (col.getText()) {
                    case COL_PTKT_NO:
                        cell.setCellValue(tr.getPtktNo().getValue());
                        break;
                    case COL_ORD_NO:
                        cell.setCellValue(tr.getOrdNo().getValue());
                        break;
                    case COL_SOLD_TO:
                        cell.setCellValue(tr.getSoldTo().getValue());
                        break;
                    case COL_SHIP_TO:
                        cell.setCellValue(tr.getShipTo().getValue());
                        break;
                    case COL_CUS_NAM:
                        cell.setCellValue(tr.getCusName().getValue());
                        break;
                    case COL_SHIP_TO_NAM:
                        cell.setCellValue(tr.getShpName().getValue());
                        break;
                    case COL_ORD_TYP:
                        // System.out.println("order type " + tr.getOrdType().getValue());
                        cell.setCellValue(tr.getOrdType().getValue());
                        break;
                    case COL_PO_NO:
                        cell.setCellValue(tr.getPoNo().getValue());
                        break;
                    case COL_WEB_ORD:
                        cell.setCellValue(tr.getWebOrd().getValue());
                        break;
                    case COL_SHIP_VIA:
                        cell.setCellValue(tr.getsVia().getValue());
                        break;
                    case COL_WHSE:
                        cell.setCellValue(tr.getWhse().getValue());
                        break;
                    case COL_ORPRL:
                        cell.setCellValue(tr.getOrPrl().getValue());
                        break;
                    case COL_CTN_CNT:
                        cell.setCellValue(tr.getTotCtn().getValue());
                        break;
                    case COL_UNITS:
                        cell.setCellValue(dUnits);
                        break;
                    case COL_DOLLARS:
                        cell.setCellValue(dDollars);
                        break;
                    case COL_STAT:
                        cell.setCellValue(tr.getStatus().getValue());
                        break;
                    case COL_STAT_ST_DATE:
                        // cell.setCellValue(wStatDt);
                        cell.setCellValue(fmtDate(tr.getStgDate().getValue()));
//                        cell.setCellValue(tr.getStgDatTm().getValue());
//                        System.out.println("wStatDt=" + wStatDt);
                        cell.setCellStyle(datCellStyle);
                        break;
                    case COL_STAT_ST_TIME:
                        cell.setCellValue(stTime.substring(0, 2) + ":"
                                + stTime.substring(2, 4) + ":"
                                + stTime.substring(4, 6));
                        break;
                    case COL_STAT_DUR:
                        cell.setCellValue(tr.getTimDif().getValue());
                        break;
                    case COL_OPR:
                        cell.setCellValue(tr.getOperator().getValue());
                        break;
                    case COL_PRT_DT:
                        //cell.setCellValue(wDate);
                        cell.setCellValue(double2String(tr.getPrtDate_d().getValue()));
                        //cell.setCellValue(tr.getPrtDate_d().getValue());
                        cell.setCellStyle(datCellStyle);
                        //  System.out.println("prtdate=" + double2String(tr.getPrtDate_d().getValue()));
                        break;
                    case COL_PRT_TM:
                        cell.setCellValue(tr.getPrtTime().getValue());
                        cell.setCellStyle(datCellStyle);
                        break;
                    case COL_ORD_STRDT:
                        //cell.setCellValue(tr.getOrStrDt().getValue());
                        cell.setCellValue(double2String(tr.getOrStrDtD().getValue()));
                        cell.setCellStyle(datCellStyle);
                        break;
                    case COL_ORD_CMPDT:
                        //cell.setCellValue( tr.getOrCmpDt().getValue());
                        cell.setCellValue(double2String(tr.getOrCmpDtD().getValue()));
                        cell.setCellStyle(datCellStyle);
                        break;
                }
            }
        }
        String sFileName = selFile.getAbsolutePath();
        if (!sFileName.toUpperCase().endsWith(".XLS")) {
            selFile = new File(sFileName.trim() + ".XLS");
        }
        boolean done;

        do {
            done = true;
            try {
                FileOutputStream outStream = new FileOutputStream(selFile);
                wb.write(outStream);
                wb.close();
                outStream.close();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error writing to sFilename.  Click OK to retry /n" + ex.getMessage(), ButtonType.CANCEL, ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    done = false;
                } else {
                    done = true;
                }
                Logger.getLogger(PickTicketDetailController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (!done);

    }

    private void setColWidth(Sheet sheet, int col, int charLen) {
        int width = ((int) (charLen * 1.14388)) * 256;
        sheet.setColumnWidth(col, width);

    }

    private Calendar str2Cal(String sDate) {
        Calendar res = Calendar.getInstance();
        int wYear;
        int wMon;
        int wDa;

        wMon = Integer.valueOf(sDate.substring(0, 2));
        wDa = Integer.valueOf(sDate.substring(3, 5));
        wYear = Integer.valueOf(sDate.substring(6, 10));
        res.set(wYear, wMon - 1, wDa);
        return res;
    }

    private String double2String(Double dDate) {
        DecimalFormat fmtDat8 = new DecimalFormat("00000000");
        String sDat8 = fmtDat8.format(dDate);

        return fmtDate(sDat8);
    }

    private String fmtDate(String sDat8) {
        String rtnDat = sDat8.substring(0, 4)
                + "/" + sDat8.substring(4, 6)
                + "/" + sDat8.substring(6, 8);

        return rtnDat;
    }
}
