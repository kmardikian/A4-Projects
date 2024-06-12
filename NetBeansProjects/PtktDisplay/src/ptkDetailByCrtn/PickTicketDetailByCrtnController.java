/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptkDetailByCrtn;

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
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ptktData.AppParms;
import ptktData.PtkCarton;
import ptktData.PtktByPtkt;
import ptktData.PtktSum;
import ptktDetailDisplay.PtktDetailRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.BorderStyle;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.*;

/**
 * FXML Controller class
 *
 * @author khatchik
 */
public class PickTicketDetailByCrtnController implements Initializable {
    
    @FXML
    private Button btnExport;
    
    @FXML
    private Label lblPtkCnt;
    
    @FXML
    private Label lblCtnCnt;
    
    @FXML
    private Label lblTotU;
    
    @FXML
    private Label lblTotD;
    
    @FXML
    private TableView<PickTicketDetailByCartonRow> tblPtkDtl;
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcCrtn;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcPtkt;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcOrder;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcSoldTo;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcShipTo;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcCustNam;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcShpNam;
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcPoNo;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcWebOrd;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcOrTyp;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcShipVia;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcWhse;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Integer> tcSku;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcUnits;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcDollars;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcStat;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcCtnStat;
    @FXML
    
    private TableColumn<PickTicketDetailByCartonRow, Double> tcStDat;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcStTim;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcCtnCmpDat;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcCtnCmpTim;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Long> tcDur;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, String> tcOpr;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcStrDt;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcCmpDt;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcPrtDt;
    
    @FXML
    private TableColumn<PickTicketDetailByCartonRow, Double> tcPrtTime;
    
    private PtktSum ptktsum;
    private String test;
    private String fltrPrd;
    private String fltrStat;
    private String fltrPkr;
    private String fltrPtk;
    private final String COL_CRTN_NO = "Carton";
    private final String COL_PTKT_NO = "Pick Ticket";
    private final String COL_ORD_NO = "Order";
    private final String COL_SOLD_TO = "Sold To";
    private final String COL_SHIP_TO = "Ship to";
    private final String COL_SHIP_TO_NAM = "Ship to Name";
    private final String COL_SHIP_VIA = "Ship Via";
    private final String COL_WHSE = "Whse";
    private final String COL_SKU_CNT = "Total SKU's";
    private final String COL_UNITS = "Units";
    private final String COL_DOLLARS = "Dollars";
    private final String COL_STAT = "Pick ticket Status";
    private final String COL_CRTN_STAT = "Carton Status";
    private final String COL_STAT_ST_DATE = "Stat Start Date";
    private final String COL_STAT_ST_TIME = "Stat Start Time";
    private final String COL_STAT_CMP_DATE = "Stat Complete Date";
    private final String COL_STAT_CMP_TIME = "Stat Complete Time";
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
    private final String TEST = "test";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fltrPrd = " ";
        fltrStat = " ";
        fltrPkr = AppParms.ALL;
        fltrPtk = AppParms.ALL;
        ImageView ivDetail = new ImageView(new Image(getClass().getResourceAsStream("/res/excel.png")));
        ivDetail.setFitHeight(15);
        ivDetail.setFitWidth(15);
        btnExport.setGraphic(ivDetail);
    }

    /**
     * Set Pick ticket Summary
     *
     * @param ptktsum
     */
    public void setPtktsum(PtktSum ptktsum) {
        this.ptktsum = ptktsum;
        this.test = "init";
//        loadDtlDsp();
    }

    /**
     * Set Period to Filter
     *
     * @param fltrPrd Period to filter
     */
    public void setFltrPrd(String fltrPrd) {
        this.fltrPrd = fltrPrd;
    }

    /**
     * Set Status to Filter
     *
     * @param fltrStat
     */
    public void setFltrStat(String fltrStat) {
        this.fltrStat = fltrStat;
    }

    /**
     * set Picker to Filter by
     *
     * @param fltrPkr
     */
    public void setFltrPkr(String fltrPkr) {
        this.fltrPkr = fltrPkr;
    }

    /**
     * set Pick Ticket to filter by..
     *
     * @param fltrPtk
     */
    public void setFltrPtk(String fltrPtk) {
        this.fltrPtk = fltrPtk;
    }

    /**
     * display Carton Detail Display
     */
    public void bldCrtnDspl() {
        ArrayList<PtkCarton> ptkCrtnList;
        ObservableList<PickTicketDetailByCartonRow> ptktCrtnDetailRow = FXCollections.observableArrayList();
        ptkCrtnList = ptktsum.getPtkCartonList();
        BigDecimal totU = BigDecimal.ZERO;
        BigDecimal totD = BigDecimal.ZERO;
        BigDecimal ptkSv = BigDecimal.ZERO;
        int totCtn = 0;
        int totPck = 0;
        Locale enUSLocale
                = new Locale.Builder().setLanguage("en").setRegion("US").build();
        NumberFormat currencyFormatter
                = NumberFormat.getCurrencyInstance(enUSLocale);
        DecimalFormat numFormatter = new DecimalFormat("#,###");
        
        for (PtkCarton ptkCrt : ptkCrtnList) {
            if (fltrPtktCrt(ptkCrt)) {
                ptktCrtnDetailRow.add(new PickTicketDetailByCartonRow(ptkCrt));
                if (ptkSv.compareTo(ptkCrt.getPtktNo()) != 0) {
                    totPck++;
                    ptkSv = ptkCrt.getPtktNo();
                }
                totU = totU.add(ptkCrt.getTotu());
                totD = totD.add(ptkCrt.getTotd());
                totCtn++;
            }
        }
        lblPtkCnt.setText(numFormatter.format(totPck));
        lblTotU.setText(numFormatter.format(totU));
        lblTotD.setText(currencyFormatter.format(totD));
        lblCtnCnt.setText(numFormatter.format(totCtn));
        if (!ptktCrtnDetailRow.isEmpty()) {
            btnExport.setDisable(false);
        }
        
        tcPtkt.setCellValueFactory(cellData -> cellData.getValue().getPtktNo());
        tcCrtn.setCellValueFactory(cellData -> cellData.getValue().getCrtnNo().asObject());
        tcCrtn.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double crtn, boolean empty) {
                super.updateItem(crtn, empty);
                DecimalFormat numFormatter = new DecimalFormat("#########");
                if (empty) {
                    setText(null);
                } else {
                    setText(numFormatter.format(crtn));
                }
            }
        });
        tcOrder.setCellValueFactory(cellData -> cellData.getValue().getOrdNo());
        tcSoldTo.setCellValueFactory(cellData -> cellData.getValue().getSoldTo());
        tcShipTo.setCellValueFactory(cellData -> cellData.getValue().getShipTo());
        tcCustNam.setCellValueFactory(cellData -> cellData.getValue().getCusName());
        tcShpNam.setCellValueFactory(cellData -> cellData.getValue().getShpName());
        tcPoNo.setCellValueFactory(cellData -> cellData.getValue().getPoNo());
        tcWebOrd.setCellValueFactory(cellData -> cellData.getValue().getWebOrd());
        tcOrTyp.setCellValueFactory(cellData -> cellData.getValue().getOrdType());
        tcShipVia.setCellValueFactory(cellData -> cellData.getValue().getsVia());
        tcWhse.setCellValueFactory(cellData -> cellData.getValue().getWhse());
        tcSku.setCellValueFactory(cellData -> cellData.getValue().getTotSku().asObject());
        tcUnits.setCellValueFactory(cellData -> cellData.getValue().getUnits().asObject());
        tcUnits.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
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
        tcDollars.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
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
        tcCtnStat.setCellValueFactory(ctData -> ctData.getValue().getCtnStat());
        tcStDat.setCellValueFactory(value -> value.getValue().getStgSDate().asObject());
        tcStDat.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double stDat, boolean empty) {
                super.updateItem(stDat, empty);
                DecimalFormat numFormatter = new DecimalFormat("00000000");
                if (empty) {
                    setText(null);
                } else {
                    String sDat = numFormatter.format(stDat);
                    String sYr = sDat.substring(0, 4);
                    String sMo = sDat.substring(4, 6);
                    String sDa = sDat.substring(6, 8);
                    setText(sMo + "/" + sDa + "/" + sYr);
                }
            }
        });
        tcStTim.setCellValueFactory(cellData -> cellData.getValue().getStgSTime().asObject());
        tcStTim.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double stTim, boolean empty) {
                super.updateItem(stTim, empty);
                DecimalFormat numFormatter = new DecimalFormat("000000");
                if (empty) {
                    setText(null);
                } else {
                    String sTim = numFormatter.format(stTim);
                    String sHr = sTim.substring(0, 2);
                    String sMn = sTim.substring(2, 4);
                    String sSec = sTim.substring(4, 6);
                    setText(sHr + ':' + sMn + ':' + sSec);
                }
            }
        });
        tcCtnCmpDat.setCellValueFactory(ctData -> ctData.getValue().getStgCmpDat().asObject());
        tcCtnCmpDat.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double cmpDat, boolean empty) {
                super.updateItem(cmpDat, empty);
                DecimalFormat numFormatter = new DecimalFormat("00000000");
                if (empty) {
                    setText(null);
                } else {
                    if (cmpDat == 0) {
                        setText("");
                    } else {
                        String sDat = numFormatter.format(cmpDat);
                        String sYr = sDat.substring(0, 4);
                        String sMo = sDat.substring(4, 6);
                        String sDa = sDat.substring(6, 8);
                        setText(sMo + "/" + sDa + "/" + sYr);
                    }
                }
            }
        });
        tcCtnCmpTim.setCellValueFactory(cData -> cData.getValue().getStgCmpTim().asObject());
        tcCtnCmpTim.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double cmpTim, boolean empty) {
                super.updateItem(cmpTim, empty);
                DecimalFormat numFormatter = new DecimalFormat("000000");
                if (empty || cmpTim == 0) {
                    setText(null);
                } else {
                    String sTim = numFormatter.format(cmpTim);
                    String sHr = sTim.substring(0, 2);
                    String sMn = sTim.substring(2, 4);
                    String sSec = sTim.substring(4, 6);
                    setText(sHr + ':' + sMn + ':' + sSec);
                }
            }
        });
        tcDur.setCellValueFactory(cellData -> cellData.getValue().getDur().asObject());
        tcDur.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Long>() {
            @Override
            public void updateItem(Long dur, boolean empty) {
                super.updateItem(dur, empty);
                if (empty) {
                    setText(null);
                } else {
                    long days = dur / 1440;
                    int wrk = (int) (dur % 1440);
                    int hrs = wrk / 60;
                    int min = wrk % 60;
                    //long hrs = (long) dur / 3600;
                    //int min = (int) (dur % 3600) / 60;
                    if (days > 0) {
                        setText(String.format("%d days %d Hrs %d Min", days, hrs, min));
                    } else {
                        setText(String.format("%d Hrs %d Min", hrs, min));
                    }
                }
            }
        });
        tcOpr.setCellValueFactory(cellData -> cellData.getValue().getOperator());
        tcStrDt.setCellValueFactory(cellData -> cellData.getValue().getOrStrDt().asObject());
        tcStrDt.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double stDat, boolean empty) {
                super.updateItem(stDat, empty);
                DecimalFormat numFormatter = new DecimalFormat("00000000");
                if (empty) {
                    setText(null);
                } else {
                    String sDat = numFormatter.format(stDat);
                    String sYr = sDat.substring(0, 4);
                    String sMo = sDat.substring(4, 6);
                    String sDa = sDat.substring(6, 8);
                    setText(sMo + "/" + sDa + "/" + sYr);
                }
            }
        });
        tcCmpDt.setCellValueFactory(cellData -> cellData.getValue().getOrCmpDt().asObject());
        tcCmpDt.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double cmpDt, boolean empty) {
                super.updateItem(cmpDt, empty);
                DecimalFormat numFormatter = new DecimalFormat("00000000");
                if (empty) {
                    setText(null);
                } else {
                    String sDat = numFormatter.format(cmpDt);
                    String sYr = sDat.substring(0, 4);
                    String sMo = sDat.substring(4, 6);
                    String sDa = sDat.substring(6, 8);
                    setText(sMo + "/" + sDa + "/" + sYr);
                }
            }
        });
        tcPrtDt.setCellValueFactory(cellData -> cellData.getValue().getPrtDate().asObject());
        tcPrtDt.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double prtDat, boolean empty) {
                super.updateItem(prtDat, empty);
                DecimalFormat numFormatter = new DecimalFormat("00000000");
                if (empty) {
                    setText(null);
                } else {
                    String sDat = numFormatter.format(prtDat);
                    String sYr = sDat.substring(0, 4);
                    String sMo = sDat.substring(4, 6);
                    String sDa = sDat.substring(6, 8);
                    setText(sMo + "/" + sDa + "/" + sYr);
                }
            }
        });
        tcPrtTime.setCellValueFactory(cellData -> cellData.getValue().getPrtTime().asObject());
        tcPrtTime.setCellFactory(col -> new TableCell<PickTicketDetailByCartonRow, Double>() {
            @Override
            public void updateItem(Double prtTime, boolean empty) {
                super.updateItem(prtTime, empty);
                DecimalFormat numFormatter = new DecimalFormat("000000");
                if (empty) {
                    setText(null);
                } else {
                    String sTim = numFormatter.format(prtTime);
                    String sHr = sTim.substring(0, 2);
                    String sMn = sTim.substring(2, 4);
                    String sSec = sTim.substring(4, 6);
                    setText(sHr + ':' + sMn + ':' + sSec);
                }
            }
        });
        
        tblPtkDtl.setItems(ptktCrtnDetailRow);
        
    }
    
    private boolean fltrPtktCrt(PtkCarton ptkCrt) {
        
        boolean result = true;
        DecimalFormat fmtPtk = new DecimalFormat("0000000");
        
        if (!" ".equals(fltrPtk) && !AppParms.ALL.equals(fltrPtk)) {
            if (!fmtPtk.format(ptkCrt.getPtktNo()).equals(fltrPtk)) {
                result = false;
            }
        }
        if (!" ".equals(fltrPrd)) {
            if (!ptkCrt.getPeriod().equals(fltrPrd)) {
                result = false;
            }
        }
        
        if (!" ".equals(fltrStat)) {
            if (fltrStat.equals(AppParms.QUA_STAT_NAME)) {
                if (!ptkCrt.getStatus().trim().equals(fltrStat)
                        && !ptkCrt.getStatus().trim().equals(AppParms.PAK_STAT_NAME)) {
                    result = false;
                }
            } else {
                if (!ptkCrt.getStatus().trim().equals(fltrStat)) {
                    result = false;
                }
            }
        }
        
        if (!AppParms.ALL.equals(fltrPkr)) {
            if (!ptkCrt.getPkrOpr().equals(fltrPkr)) {
                result = false;
            }
        }
        
        return result;
    }
    
    @FXML
    void btnExportClicked(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Excel Files", "*.XLS"));
        
        fc.setTitle("Choose Export File");
        
        File selFile = fc.showSaveDialog(null);
        
        if (selFile != null) {
            System.out.println("file name is " + selFile.getAbsolutePath());
            exportPtktData(selFile);
        }
        
    }
    
    private void exportPtktData(File selFile) {
        ObservableList<TableColumn<PickTicketDetailByCartonRow, ?>> tblCol;
        ObservableList<PickTicketDetailByCartonRow> tblRows;
        DecimalFormat dfDat8 = new DecimalFormat("00000000");
        DecimalFormat dfTim6 = new DecimalFormat("000000");
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Pick ticket list by carton");
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
        datCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yy"));
        
        tblCol = tblPtkDtl.getColumns();
        Calendar wDate = Calendar.getInstance();
        Calendar wStatDt = Calendar.getInstance();
        Calendar wCmpDt = Calendar.getInstance();
        String stTime;
        String enTime;
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
        for (TableColumn<PickTicketDetailByCartonRow, ?> col : tblCol) {
            //System.out.println("getting col hdr " + col.getText());
            Cell cell = row.createCell(colIx);
            cell.setCellValue(col.getText());
            cell.setCellStyle(hdrCellStyl);
            
            switch (col.getText()) {
                case COL_CRTN_NO:
                    setColWidth(sheet, colIx, 15);
                    break;
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
                case COL_STAT_CMP_DATE:
                    setColWidth(sheet, colIx, 20);
                    break;
                case COL_STAT_CMP_TIME:
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
        for (PickTicketDetailByCartonRow tr : tblRows) {
            row = sheet.createRow(rowIx++);

//            sDate = dfDat8.format(tr.getPrtDate().getValue());
//            sTime = dfTim6.format(tr.getPrtTime().getValue());
//            wMon = Integer.valueOf(sDate.substring(4, 6));
//            wDa = Integer.valueOf(sDate.substring(6, 8));
//            wYear = Integer.valueOf(sDate.substring(0, 4));
//            wHr = Integer.valueOf(sTime.substring(0, 2));
//            wMin = Integer.valueOf(sTime.substring(2, 4));
//            wSec = Integer.valueOf(sTime.substring(4, 6));
//            wDate.set(wYear, wMon - 1, wDa, wHr, wMin, wSec);
//            sDate = dfDat8.format(tr.getStgSDate().getValue());
//            sTime = dfTim6.format(tr.getStgSTime().getValue());
//            wYear = Integer.valueOf(sDate.substring(0, 4));
//            wMon = Integer.valueOf(sDate.substring(4, 6));
//            wDa = Integer.valueOf(sDate.substring(6, 8));
//            wHr = Integer.valueOf(sTime.substring(0, 2));
//            wMin = Integer.valueOf(sTime.substring(2, 4));
//            wSec = Integer.valueOf(sTime.substring(4, 6));
//            wStatDt.set(wYear, wMon -1, wDa, wHr, wMin, wSec);
//            sDate = dfDat8.format(tr.getStgCmpDat().getValue());
            sTime = dfTim6.format(tr.getStgCmpTim().getValue());
//            wYear = Integer.valueOf(sDate.substring(0, 4));
//            wMon = Integer.valueOf(sDate.substring(4, 6));
//            wDa = Integer.valueOf(sDate.substring(6, 8));
//            wHr = Integer.valueOf(sTime.substring(0, 2));
//            wMin = Integer.valueOf(sTime.substring(2, 4));
//            wSec = Integer.valueOf(sTime.substring(4, 6));
//            wCmpDt.set(wYear, wMon - 1, wDa, wHr, wMin, wSec);
            enTime = sTime;
            dDollars = tr.getDollars().getValue();
            dUnits = tr.getUnits().getValue();
            stTime = dfTim6.format(tr.getStgSTime().getValue());
            // sUnits = sUnits.replace(",", "");
            //dUnits = Double.valueOf(sUnits);
            // sDollars = sDollars.replace("$", "");
            //sDollars = sDollars.replace(",", "");
            // dDollars = Double.valueOf(sDollars);
            for (colIx = 0; colIx < tblCol.size(); colIx++) {
                TableColumn<PickTicketDetailByCartonRow, ?> col = tblCol.get(colIx);
                String tst = col.getText();
                Cell cell = row.createCell(colIx);
                
                switch (col.getText()) {
                    case COL_CRTN_NO:
                        cell.setCellValue(tr.getCrtnNo().getValue());
                        break;
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
                    case COL_SHIP_VIA:
                        cell.setCellValue(tr.getsVia().getValue());
                        break;
                    case COL_WHSE:
                        cell.setCellValue(tr.getWhse().getValue());
                        break;
                    case COL_SKU_CNT:
                        cell.setCellValue(tr.getTotSku().getValue());
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
                    case COL_CRTN_STAT:
                        cell.setCellValue(tr.getCtnStat().getValue());
                        break;
                    case COL_STAT_ST_DATE:
                        //cell.setCellValue(wStatDt);
                        cell.setCellValue(double2String(tr.getStgSDate().getValue()));
                        cell.setCellStyle(datCellStyle);
                        break;
                    case COL_STAT_CMP_DATE:
//                        cell.setCellValue(wCmpDt);
                        cell.setCellValue(double2String(tr.getStgCmpDat().getValue()));
                        cell.setCellStyle(datCellStyle);
                        break;
                    case COL_STAT_ST_TIME:
                        cell.setCellValue(stTime.substring(0, 2) + ":"
                                + stTime.substring(2, 4) + ":"
                                + stTime.substring(4, 6));
                        break;
                    case COL_STAT_CMP_TIME:
                        cell.setCellValue(enTime.substring(0, 2) + ":"
                                + enTime.substring(2, 4) + ":"
                                + enTime.substring(4, 6));
                        break;
                    case COL_STAT_DUR:
                        cell.setCellValue(tr.getTimDifStr());
                        break;
                    case COL_OPR:
                        cell.setCellValue(tr.getOperator().getValue());
                        break;
                    case COL_PRT_DT:
                        //cell.setCellValue(wDate);
                        cell.setCellStyle(datCellStyle);
                        cell.setCellValue(double2String(tr.getPrtDate().getValue()));
                        break;
                    case COL_PRT_TM:
                        sTime = dfTim6.format(tr.getPrtTime().getValue());
                        cell.setCellValue(sTime.substring(0, 2) + ":"
                                + sTime.substring(2, 4) + ":"
                                + sTime.substring(4, 6));
                        cell.setCellStyle(datCellStyle);
                        break;
                    case COL_ORD_STRDT:
                        cell.setCellValue(double2String(tr.getOrStrDt().getValue()));
                        cell.setCellStyle(datCellStyle);
                        break;
                    case COL_ORD_CMPDT:
                        cell.setCellValue(double2String(tr.getOrCmpDt().getValue()));
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
                Logger.getLogger(PickTicketDetailByCrtnController.class.getName()).log(Level.SEVERE, null, ex);
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
