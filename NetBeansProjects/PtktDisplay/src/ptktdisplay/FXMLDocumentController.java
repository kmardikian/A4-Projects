/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktdisplay;

import ptktDetailDisplay.PickTicketDetailController;
import com.a4.utils.ConnectAs400;
import com.a4.utils.ConnectionException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import ptkDetailByCrtn.PickTicketDetailByCrtnController;
import ptktData.PKHDRP;
import ptktData.AppParms;
import ptktData.CustomException;
import ptktData.PtktSum;
import ptktData.PtktSumByPrd;
import ptktData.PtktSumByStat;
import ptktData.PtktSumByPkr;
import ptktFilter.ChangeFilterEvent;
import ptktFilter.ChangedFilterListener;
import ptktFilter.PtktFilter;
import ptktFilter.PtktFilterController;

/**
 *
 * @author Khatchik
 */
public class FXMLDocumentController implements Initializable, ChangedFilterListener {

    PKHDRP pkhdrp;
    @FXML
    private VBox prtNode;

    //@FXML
    //private PieChart pChrtPtktSta;
//    @FXML
//    private BarChart<String, Number> bChartPtktSta;
//    @FXML
//    private CategoryAxis xAxis;
//    @FXML
//    private NumberAxis yAxis;
//    @FXML
//    private Label caption;
    @FXML
    private Label lblRefTime;
    @FXML
    private ProgressIndicator prInRefresh;

    @FXML
    private ToggleGroup tglDspDtlBy;
    @FXML
    private RadioButton rbCrtDtl;
    @FXML
    private RadioButton rbPckp;
    @FXML
    private TableView<PtktStatTblRec> tblPtktSumStat;
    @FXML
    private TableColumn<PtktStatTblRec, String> tcStat;
    @FXML
    private TableColumn<PtktStatTblRec, Integer> tcCount;
    @FXML
    private TableColumn<PtktStatTblRec, String> tcUnit;
    @FXML
    private TableColumn<PtktStatTblRec, String> tcDlrs;

    // pick ticket by picker/Stat
    @FXML
    private TableView<PtktByPkrTblRow> tblPkrSum;

    @FXML
    private TableColumn<PtktByPkrTblRow, String> tcPkrNam;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrPrt;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrDis;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrCus;

//    @FXML
//    private TableColumn<PtktByPkrTblRow, Integer> tcPkrAsg;
    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrPck;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrPcm;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrQua;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrCpu;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrWgh;
    @FXML

    private TableColumn<PtktByPkrTblRow, Integer> tcPkrShp;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrInv;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrTot;

    @FXML
    private TableColumn<PtktByPkrTblRow, Integer> tcPkrCrtn;

    // pick ticket summary by period 
    @FXML
    private TableView<PtktSumPrdRow> tblPtktSumPrd;

    @FXML
    private TableColumn<PtktSumPrdRow, String> tcPrdPrd;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdPrt;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdDis;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdCus;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdShp;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdPck;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdPcm;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdQua;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdCpk;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdWgh;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdInv;

    @FXML
    private TableColumn<PtktSumPrdRow, Integer> tcPrdTot;

    @FXML
    private StackedBarChart<String, Number> bChartPtktPrd;

    @FXML
    private CategoryAxis bChartPtktPrdX;

    @FXML
    private NumberAxis bChartPtktPrdY;
    @FXML
    private Button btnPrint;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnDspDtl;

    @FXML
    private Button btnDspCrtDtl;

    @FXML
    private Button btnFilter;
    @FXML
    private Button btnAutoRefresh;
    @FXML
    private Spinner<Integer> spnRefInt;

    private PtktSum ptktSum;
    private PtktFilter ptktFilter;
    private ArrayList<PtktSumByStat> ptktSumList;
    private final SvcRefPkSumStat svcRefPkSumStat = new SvcRefPkSumStat();
    private int refreshInt = 0;
    private final String stTot = "Total:";
    private final Glow glow = new Glow(.8);
    private boolean autoRefresh = true;
    private final String STR_AUTO_REFRESH = "Re-Start Auto Refresh";
    private final String STP_AUTO_REFRESH = "Stop Auto Refresh";
    private int conFailCnt = 0;
    private final String DTL_BY_PKP = "PTK";
    private final String DTL_BY_CRT = "CRT";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ptktFilter = new PtktFilter();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyyMMdd"); 
        LocalDate strDate = LocalDate.now()
                .minusMonths(1L)
                .withDayOfMonth(01);
        
       // ptktFilter.setPrtDatFr(AppParms.getCurDate());
       ptktFilter.setPrtDatFr(strDate.format(formater));
        ptktFilter.setPrtDatTo(AppParms.getCurDate());
        ptktFilter.getWrhList().add(AppParms.getDftWrh());
        pkhdrp = new PKHDRP(ConnectAs400.getLib(), ptktFilter);
        try {
            ptktSum = pkhdrp.getOpnPtktByStat();
            ptktSumList = ptktSum.getPtktSumStat();
            pieChartUnit();
            bldSumPrd(ptktSum.getPtktSumPrd());
            bldSumPkr(ptktSum.getPtkByPkr());
        } catch (CustomException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving Pick Ticked data\n" + ex.getLocalizedMessage());
            alert.setTitle("Error retrieving Pick Ticked data");
            alert.showAndWait();
        }
        svcRefPkSumStat.setPkhdrp(pkhdrp);
        //svcRefPkSumStat.setDelay(Duration.ZERO);
        // Configure the spinner for auto Refresh 
        ObservableList<Integer> spnValues
                = FXCollections.observableArrayList(30, 60, 90, 120, 150, 180,
                        210, 240, 270, 300, 330, 360);
        SpinnerValueFactory<Integer> spnFac
                = new SpinnerValueFactory.ListSpinnerValueFactory<>(spnValues);
        spnFac.setValue(spnValues.get(9));
        refreshInt = spnFac.getValue();
        spnRefInt.setValueFactory(spnFac);
        //refreshInt = spnValues.get(2);

        spnRefInt.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            refreshInt = newValue;
            svcRefPkSumStat.setDelay(Duration.seconds(refreshInt));
            svcRefPkSumStat.setPeriod(Duration.seconds(refreshInt));
        });
        svcRefPkSumStat.setDelay(Duration.seconds(refreshInt));
        svcRefPkSumStat.setPeriod(Duration.seconds(refreshInt));

        svcRefPkSumStat.setOnSucceeded((WorkerStateEvent event) -> {
            //PtktSum ptktSum;
            ptktSum = svcRefPkSumStat.getValue();
            System.gc();
            ptktSumList = ptktSum.getPtktSumStat();
            pieChartUnit();
            bldSumPrd(ptktSum.getPtktSumPrd());
            bldSumPkr(ptktSum.getPtkByPkr());
            conFailCnt = 0;
        });
        svcRefPkSumStat.setOnFailed(e -> {

            if (++conFailCnt > 5) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving Pick Ticked data\n" + e.getSource().getException().getLocalizedMessage());
                alert.showAndWait();
                prInRefresh.setVisible(false);
                btnRefresh.setDisable(false);
                btnDspDtl.setDisable(false);
                btnDspCrtDtl.setDisable(false);
            }
            try {
                ConnectAs400.conAs400();
            } catch (ConnectionException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        svcRefPkSumStat.setOnRunning((WorkerStateEvent event) -> {
            // ptktSum=null;
            // System.gc();
            prInRefresh.setProgress(-1);
            prInRefresh.setVisible(true);
            btnDspDtl.setDisable(true);
            btnDspCrtDtl.setDisable(true);
        });

        //       System.out.println("Starting Service");
        svcRefPkSumStat.start();
//        prInRefresh.progressProperty().bind(svcRefPkSumStat.progressProperty());
        autoRefresh = true;
        btnAutoRefresh.setText(STP_AUTO_REFRESH);
        ImageView ivPrinter = new ImageView(new Image(getClass().getResourceAsStream("/res/printer.PNG")));
        ivPrinter.setFitHeight(15);
        ivPrinter.setFitWidth(15);
        btnPrint.setGraphic(ivPrinter);
        ImageView ivFilter = new ImageView(new Image(getClass().getResourceAsStream("/res/filter+.png")));
        ivFilter.setFitHeight(15);
        ivFilter.setFitWidth(15);
        btnFilter.setGraphic(ivFilter);

        ImageView ivRefresh = new ImageView(new Image(getClass().getResourceAsStream("/res/update.png")));
        ivRefresh.setFitHeight(15);
        ivRefresh.setFitWidth(15);
        btnRefresh.setGraphic(ivRefresh);

        ImageView ivDetail = new ImageView(new Image(getClass().getResourceAsStream("/res/detail.jpg")));
        ivDetail.setFitHeight(15);
        ivDetail.setFitWidth(15);
        btnDspDtl.setGraphic(ivDetail);

        ImageView ivCtnDtl = new ImageView(new Image(getClass().getResourceAsStream("/res/cartons.png")));
        ivCtnDtl.setFitHeight(15);
        ivCtnDtl.setFitWidth(15);
        btnDspCrtDtl.setGraphic(ivCtnDtl);

        rbPckp.setUserData(DTL_BY_PKP);
        rbCrtDtl.setUserData(DTL_BY_CRT);

        //      System.out.println("Getting pick sum first time");
        // try {
        // ptktSumList = pkhdrp.getOpnPtktByStat();
        // }
        // catch (Exception ex ) {
        //     ex.printStackTrace();
        //     return;
        // }
        // System.out.println("mapping pie chart");
        // pieChartUnit();
    }

    public void btnPrtClicked(ActionEvent e) {
        prtScr(prtNode);
    }

    private void prtScr(Node node) {
        PrinterJob job;

        job = PrinterJob.createPrinterJob();
        if (job == null) {
            return;
        }

        JobSettings jobSettings = job.getJobSettings();
        Printer printer;

        PageLayout pageLayout;

        svcRefPkSumStat.cancel();
        Stage owner = (Stage) btnPrint.getScene().getWindow();

        printer = job.getPrinter();

        pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        jobSettings.setPageLayout(pageLayout);

        boolean proceed = job.showPrintDialog(owner);
        double scalex = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaley = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        Scale scale = new Scale(scalex, scaley);
        node.getTransforms().add(scale);
        if (proceed) {
            jobSettings.setJobName("Pick Ticket Dashboard Display");
            boolean printed = job.printPage(node);
            if (printed) {
                job.endJob();
            }
        }
        prtNode.getTransforms().remove(scale);
        svcRefPkSumStat.restart();
    }

    public void refreshBtnClicked(ActionEvent e) {
        refresh();

    }

    public void refresh() {
        ptktSum = null;
        System.gc();
        TskRefPkSumStat refPkSumStat = new TskRefPkSumStat();
        refPkSumStat.setPkhdrp(pkhdrp);
        prInRefresh.setVisible(true);
        prInRefresh.setProgress(-1);
        btnRefresh.setDisable(true);
        btnDspDtl.setDisable(true);
        btnDspCrtDtl.setDisable(true);

        Task<PtktSum> refTask = refPkSumStat;
        refTask.setOnSucceeded(e -> {
            ptktSum = refTask.getValue();
            // ptktSum =(PtktSum) e.getSource().getValue();  this works also.
            //ptktSum = refPkSumStat.getValue();
            ptktSumList = ptktSum.getPtktSumStat();
            pieChartUnit();
            bldSumPrd(ptktSum.getPtktSumPrd());
            bldSumPkr(ptktSum.getPtkByPkr());
            prInRefresh.setVisible(false);
            btnRefresh.setDisable(false);
        });

        refTask.setOnFailed((e) -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving Pick Ticked data\n" + e.getSource().getException().getLocalizedMessage());
            alert.showAndWait();
            prInRefresh.setVisible(false);
            btnRefresh.setDisable(false);

        });
//        This can replace the above refTask 
//        Task<PtktSum> rTask = new Task() {
//            @Override
//            protected PtktSum call() throws Exception {
//                PtktSum psum = pkhdrp.getOpnPtktByStat();
//                return psum;
//                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        };

        //Worker<PtktSum> tskREfPkSumStat = refPkSumStat.getWorker();
        //tskREfPkSumStat.a
        new Thread((Runnable) refTask).start();

//        try {
//            // prInRefresh.setVisible(true);
//            // prInRefresh.setProgress(-1);
//            // Worker<PtktSum> tskREfPkSumStat = refPkSumStat.getWorker();
//
//            ptktSum = pkhdrp.getOpnPtktByStat();
//
//            ptktSumList = ptktSum.getPtktSumStat();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return;
//        }
//        pieChartUnit();
//        bldSumPrd(ptktSum.getPtktSumPrd());
//        bldSumPkr(ptktSum.getPtkByPkr());
    }

    public void dspDetailClicked(ActionEvent event) {
        dspPckDetail(" ", " ", AppParms.ALL);

    }

    public void btnFilterClicked(ActionEvent event) {
        PtktFilterController ptktFilterCtl;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ptktFilter/PtktFilter.fxml"));

            Parent dtlRoot = (Parent) fxmlLoader.load();
            ptktFilterCtl = (PtktFilterController) fxmlLoader.getController();
            ptktFilterCtl.setPtktFilter(ptktFilter);
            ptktFilterCtl.addFilterListener(this);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/filter+.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Pick Ticket Filter");
            stage.setScene(new Scene(dtlRoot));

            stage.setOnCloseRequest(e -> {
                // when filter is closed by pressing apply button onClose is not triggered 
                if (autoRefresh) {
                    svcRefPkSumStat.restart();
                }
            });
            svcRefPkSumStat.cancel();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    /**
     * Display Carton Detail Button clicked
     */
    public void dspCrtDetailClicked(ActionEvent event) {
        dspCrtDetail(" ", " ", AppParms.ALL);

    }

    public void btnAutoRefreshClicked(ActionEvent event) {
        if (autoRefresh) {
            autoRefresh = false;
            svcRefPkSumStat.cancel();
            btnAutoRefresh.setText(STR_AUTO_REFRESH);
        } else {
            autoRefresh = true;
            // svcRefPkSumStat.setDelay(Duration.seconds(60));
            svcRefPkSumStat.restart();
            btnAutoRefresh.setText(STP_AUTO_REFRESH);
        }

    }

    public void dspDetail(String prd, String stat, String oprPkr) {
        if (tglDspDtlBy.getSelectedToggle().getUserData().toString().equals(DTL_BY_PKP)) {
            dspPckDetail(prd, stat, oprPkr);
        } else {
            dspCrtDetail(prd, stat, oprPkr);
        }

    }

    public void dspPckDetail(String prd, String stat, String oprPkr) {

        PickTicketDetailController ptktDtlCtl;
        if (prd.equals(stTot)) {
            prd = " ";
        }
        if (stat.equals(stTot)) {
            stat = " ";
        }
        if (oprPkr.equals(stTot)) {
            oprPkr = AppParms.ALL;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ptktDetailDisplay/PickTicketDetail.fxml"));

            Parent dtlRoot = (Parent) fxmlLoader.load();
            ptktDtlCtl = (PickTicketDetailController) fxmlLoader.getController();
            ptktDtlCtl.setFltrPrd(prd);
            ptktDtlCtl.setFltrStat(stat);
            ptktDtlCtl.setFltrPkr(oprPkr);
            ptktDtlCtl.setPtktsum(ptktSum);

            ptktDtlCtl.loadDtlDsp();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Pick Ticket status Detail Display");
            stage.setScene(new Scene(dtlRoot));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/detail.jpg")));

            stage.setOnCloseRequest(e -> {
                if (autoRefresh) {
                    svcRefPkSumStat.restart();
                }
            });
            svcRefPkSumStat.cancel();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Display Carton Detail
     *
     * @param prd Period
     * @param stat Status
     * @param oprPkr Picker
     * @param ptkNo Pick ticket no
     */
    private void dspCrtDetail(String prd, String stat, String oprPkr) {

        PickTicketDetailByCrtnController ptktDtlCrtnCtl;
        if (prd.equals(stTot)) {
            prd = " ";
        }
        if (stat.equals(stTot)) {
            stat = " ";
        }
        if (oprPkr.equals(stTot)) {
            oprPkr = AppParms.ALL;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ptkDetailByCrtn/PickTicketDetailByCrtn.fxml"));
            //   FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ptktDetailDisplay/PickTicketDetail.fxml"));

            Parent dtlRoot = (Parent) fxmlLoader.load();
            ptktDtlCrtnCtl = (PickTicketDetailByCrtnController) fxmlLoader.getController();
            ptktDtlCrtnCtl.setFltrPrd(prd);
            ptktDtlCrtnCtl.setFltrStat(stat);
            ptktDtlCrtnCtl.setFltrPkr(oprPkr);
            ptktDtlCrtnCtl.setFltrPtk(AppParms.ALL);
            ptktDtlCrtnCtl.setPtktsum(ptktSum);

            ptktDtlCrtnCtl.bldCrtnDspl();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Pick Ticket Detail by Carton");
            stage.setScene(new Scene(dtlRoot));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/cartons.png")));

            stage.setOnCloseRequest(e -> {
                if (autoRefresh) {
                    svcRefPkSumStat.restart();
                }
            });
            svcRefPkSumStat.cancel();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void pieChartUnit() {
//        ObservableList<PieChart.Data> pieChartData;
        //pieChartData.clear();

        // ptktSumList.forEach((pSumStat) -> {
        //     pieChartData.add(new PieChart.Data(pSumStat.getPtktStat(),pSumStat.getPtktCnt()));
        // });
        int totCnt = 0;
        BigDecimal totUnit = BigDecimal.ZERO;
        BigDecimal totDlr = BigDecimal.ZERO;

        SimpleDateFormat datTimeFmt = new SimpleDateFormat("MM-dd-yy   hh:mm:ss a");
        String sDate = "";
        sDate = datTimeFmt.format(new Date());
        lblRefTime.setText(sDate);
        // pieChartData = FXCollections.observableArrayList();
        // ObservableList<String> pkStat = FXCollections.observableArrayList();  // bar chart
        // XYChart.Series<String, Number> series = new XYChart.Series<>();   // bar chart
        // table

        ObservableList<PtktStatTblRec> pkStatTblRow = FXCollections.observableArrayList();

        for (PtktSumByStat pSumStat : ptktSumList) {
            //System.out.println("stat=" + pSumStat.getPtktStat() + " count=" +  pSumStat.getPtktCnt());
            String wStat;
            int pkStatCnt = pSumStat.getPtktCnt();
            totCnt += pkStatCnt;
            totUnit = totUnit.add(pSumStat.getTotUnt());
            totDlr = totDlr.add(pSumStat.getTotDlr());
            wStat = pSumStat.getPtktStat();
//            pieChartData.add(new PieChart.Data(pSumStat.getPtktStat().trim()
//                    + " " + String.format("%d", pkStatCnt).trim(),
//                    pkStatCnt));
            //Table
            pkStatTblRow.add(new PtktStatTblRec(pSumStat.getPtktStat(), pSumStat.getPtktCnt(),
                    pSumStat.getTotUnt(), pSumStat.getTotDlr()));
            // Bar Chart
//            pkStat.add(wStat);
//            series.getData().add(new XYChart.Data(wStat,
//                    pSumStat.getPtktCnt()));

        }
        pkStatTblRow.add(new PtktStatTblRec(stTot, totCnt, totUnit, totDlr));

        //pChrtPtktSta.setData(pieChartData);
        // pChrtPtktSta.setTitle("Open Pick tickets by status");
//        bChartPtktSta.getData().clear();  // bar chart
//        xAxis.setCategories(pkStat);       // bar chart
//        bChartPtktSta.getData().add(series); // bar chart
//        xAxis.setLabel("Status");
//        yAxis.setLabel("Count");
//        }
        //series.setName("Pick ticket count by Status");
        tcStat.setCellValueFactory(cellData -> cellData.getValue().StatProperty());

        tcCount.setCellValueFactory(cellData -> cellData.getValue().CountProperty().asObject());
        tcUnit.setCellValueFactory(cellData -> cellData.getValue().UnitsProperty());
        tcDlrs.setCellValueFactory(cellData -> cellData.getValue().DlrsProperty());

        tblPtktSumStat.setItems(pkStatTblRow);
        tcCount.setCellFactory(tc -> {
            TableCell<PtktStatTblRec, Integer> cell = new TableCell<PtktStatTblRec, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getTableRow().getStyleClass().add("table-row-cellSel");
                    }
                }

            };
            return cell;
        });

        tblPtktSumStat.setRowFactory(e -> {
            TableRow<PtktStatTblRec> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    PtktStatTblRec rowClicked = row.getItem();
                    if (rowClicked.getCount() != 0) {
                        dspDetail(" ", rowClicked.getStat().trim(), AppParms.ALL);
                    }
                }
            });
            return row;
        });

//        pChrtPtktSta.getData().forEach((data) -> {
//            Node n = data.getNode();
//            n.setEffect(null);
//            n.setOnMouseEntered(e -> {
//                n.setEffect(glow);
//            });
//            n.setOnMouseExited(e -> {
//                n.setEffect(null);
//            });
//            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
//                dspDetail(" ", data.getName().substring(0, 3));
//            });
//        });
        btnRefresh.setDisable(false);
        btnDspDtl.setDisable(false);
        btnDspCrtDtl.setDisable(false);

    }

    private void bldSumPkr(ArrayList<PtktSumByPkr> sumByPkrList) {
        ObservableList<PtktByPkrTblRow> ptktByPkrTblRow = FXCollections.observableArrayList();
        ArrayList<PtktSumByStat> sumByStatLst = new ArrayList<>();
        int wPrt = 0;
        int wDis = 0;
        int wCus = 0;
        // int wAsg = 0;
        int wPck = 0;
        int wPcm = 0;
        int wQua = 0;
        int wCpu = 0;
        int wWgh = 0;
        int wShp = 0;
        int wInv = 0;

        int wPrtCtn = 0;
        int wDisCtn = 0;
        int wCusCtn = 0;
        int wPckCtn = 0;
        int wPcmCtn = 0;
        int wQuaCtn = 0;
        int wCpuCtn = 0;
        int wWghCtn = 0;
        int wShpCtn = 0;
        int wInvCtn = 0;

        BigDecimal wPrtU = BigDecimal.ZERO;
        BigDecimal wPrtD = BigDecimal.ZERO;
        BigDecimal wDisU = BigDecimal.ZERO;
        BigDecimal wDisD = BigDecimal.ZERO;
        BigDecimal wCusU = BigDecimal.ZERO;
        BigDecimal wCusD = BigDecimal.ZERO;
        BigDecimal wPckU = BigDecimal.ZERO;
        BigDecimal wPckD = BigDecimal.ZERO;
        BigDecimal wPcmU = BigDecimal.ZERO;
        BigDecimal wPcmD = BigDecimal.ZERO;
        BigDecimal wQuaU = BigDecimal.ZERO;
        BigDecimal wQuaD = BigDecimal.ZERO;
        BigDecimal wCpuU = BigDecimal.ZERO;
        BigDecimal wCpuD = BigDecimal.ZERO;
        BigDecimal wWghU = BigDecimal.ZERO;
        BigDecimal wWghD = BigDecimal.ZERO;
        BigDecimal wShpU = BigDecimal.ZERO;
        BigDecimal wShpD = BigDecimal.ZERO;
        BigDecimal wInvU = BigDecimal.ZERO;
        BigDecimal wInvD = BigDecimal.ZERO;
        BigDecimal wTotU = BigDecimal.ZERO;
        BigDecimal wTotD = BigDecimal.ZERO;

        for (PtktSumByPkr sumByPkr : sumByPkrList) {
            ptktByPkrTblRow.add(new PtktByPkrTblRow(sumByPkr));
            sumByStatLst = sumByPkr.getSumByStat();
            for (PtktSumByStat statSum : sumByStatLst) {
                switch (statSum.getPtktStat().trim()) {
                    case AppParms.PRT_STAT_NAME:
                        wPrt = wPrt + statSum.getPtktCnt();
                        wPrtU = wPrtU.add(statSum.getTotUnt());
                        wPrtD = wPrtD.add(statSum.getTotDlr());
                        wPrtCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.DIS_STAT_NAME:
                        wDis = wDis + statSum.getPtktCnt();
                        wDisU = wDisU.add(statSum.getTotUnt());
                        wDisD = wDisD.add(statSum.getTotDlr());
                        wDisCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.CUS_STAT_NAME:

                        wCus += statSum.getPtktCnt();
                        wCusU = wCusU.add(statSum.getTotUnt());
                        wCusD = wCusD.add(statSum.getTotDlr());
                        wDisCtn += statSum.getTotCrtn();
                        break;
//                    case AppParms.ASG_STAT_NAME:
//                        wAsg = wAsg + statSum.getPtktCnt();
//                        break;
                    case AppParms.PCK_STAT_NAME:
                        wPck = wPck + statSum.getPtktCnt();
                        wPckU = wPckU.add(statSum.getTotUnt());
                        wPckD = wPckD.add(statSum.getTotDlr());
                        wPckCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.PCM_STAT_NAME:
                        wPck = wPcm + statSum.getPtktCnt();
                        wPckU = wPcmU.add(statSum.getTotUnt());
                        wPckD = wPcmD.add(statSum.getTotDlr());
                        wPcmCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.PAK_STAT_NAME:
                        wQua = wQua + statSum.getPtktCnt();
                        wQuaU = wQuaU.add(statSum.getTotUnt());
                        wQuaD = wQuaD.add(statSum.getTotDlr());
                        wQuaCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.QUA_STAT_NAME:
                        wQua = wQua + statSum.getPtktCnt();
                        wQuaU = wQuaU.add(statSum.getTotUnt());
                        wQuaD = wQuaD.add(statSum.getTotDlr());
                        wQuaCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.CPU_STAT_NAME:
                        wCpu = wCpu + statSum.getPtktCnt();
                        wCpuU = wCpuU.add(statSum.getTotUnt());
                        wCpuD = wCpuD.add(statSum.getTotDlr());
                        wCpuCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.WGH_STAT_NAME:
                        wWgh = wWgh + statSum.getPtktCnt();
                        wWghU = wWghU.add(statSum.getTotUnt());
                        wWghD = wWghD.add(statSum.getTotDlr());
                        wWghCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.SHP_STAT_NAME:
                        wShp = wShp + statSum.getPtktCnt();
                        wShpU = wShpU.add(statSum.getTotUnt());
                        wShpD = wShpD.add(statSum.getTotDlr());
                        wShpCtn += statSum.getTotCrtn();
                        break;
                    case AppParms.INV_STAT_NAME:
                        wInv = wInv + statSum.getPtktCnt();
                        wInvU = wInvU.add(statSum.getTotUnt());
                        wInvD = wInvD.add(statSum.getTotDlr());
                        wInvCtn += statSum.getTotCrtn();
                        break;

                }
            }
        }

        ptktByPkrTblRow.add(new PtktByPkrTblRow(stTot, wPrt, wDis, wCus, wPck, wPcm,
                wQua, wCpu, wWgh, wShp, wInv, wPrtU, wPrtD, wDisU, wDisD,
                wCusU, wCusD, wPckU, wPckD, wPcmU, wPcmD,
                wQuaU, wQuaD, wCpuU, wCpuD, wWghU, wWghD,
                wShpU, wShpD, wInvU, wInvD, wPrtCtn, wDisCtn, wCusCtn,
                wPckCtn, wPcmCtn, wQuaCtn, wCpuCtn, wWghCtn, wShpCtn, wInvCtn));

        tcPkrNam.setCellValueFactory(cellData -> cellData.getValue().getNamProperty());
        tcPkrNam.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, String> cell = new TableCell<PtktByPkrTblRow, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    if (!empty) {
                        this.getStyleClass().add("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    dspDetail(" ", " ", myItem.getNamProperty().getValue());
                    //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                }
            });

            return cell;
        });
//        tcPkrPrt.setCellValueFactory(cellData -> cellData.getValue().getPrtProperty().asObject());
        tcPkrPrt.setCellValueFactory(cellData -> cellData.getValue().getPrt().asObject());
        tcPkrPrt.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(this.getIndex());
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getPrtCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getPrtUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getPrtDlr()));
                        setTooltip(tooltip);
                        // }
                    } else {
                        this.getStyleClass().remove("selCell");

                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getPrt().getValue() != 0) {
                        dspDetail(" ", AppParms.PRT_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
        tcPkrDis.setCellValueFactory(cellData -> cellData.getValue().getDisProperty().asObject());
        tcPkrDis.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(this.getIndex());
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getDisCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getDisUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getDisDlr()));
                        setTooltip(tooltip);
                        //}
                    } else {
                        this.getStyleClass().remove("selCell");

                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getDisProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.DIS_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
        // pkr Cus start 
        tcPkrCus.setCellValueFactory(cellData -> cellData.getValue().getCusProperty().asObject());
        tcPkrCus.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(this.getIndex());
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getCusCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getCusUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getCusDlr()));
                        setTooltip(tooltip);
                        //}
                    } else {
                        this.getStyleClass().remove("selCell");

                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getDisProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.CUS_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
        //pkr Cus End

//        tcPkrAsg.setCellValueFactory(cellData -> cellData.getValue().getAsgProperty().asObject());
//        tcPkrAsg.setCellFactory(tc -> {
//            //ObservableList<String> cellStyle;
//            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
//                @Override
//                protected void updateItem(Integer item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setText(empty ? null : item.toString());
//                    if (!empty) {
//                        if (item != 0) {
//                            this.getStyleClass().add("selCell");
//                        }
//                    }
//                }
//
//            };
//            cell.setOnMouseClicked(e -> {
//                if (!cell.isEmpty()) {
//                    TableRow<PtktByPkrTblRow> tblRow;
//                    tblRow = cell.getTableRow();
//                    PtktByPkrTblRow myItem = tblRow.getItem();
//                    if (myItem.getAsgProperty().getValue() != 0) {
//                        dspDetail(" ", AppParms.ASG_STAT_NAME, myItem.getNamProperty().getValue());
//                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
//                    }
//                }
//            });
//
//            return cell;
//        });
        tcPkrPck.setCellValueFactory(cellData -> cellData.getValue().getPckProperty().asObject());
        tcPkrPck.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getPckCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getPckUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getPckDlr()));
                        setTooltip(tooltip);
                        //}
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getPckProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.PCK_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
// start of PCM
        tcPkrPcm.setCellValueFactory(cellData -> cellData.getValue().getPcmProperty().asObject());
        tcPkrPcm.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getPcmCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getPcmUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getPcmDlr()));
                        setTooltip(tooltip);
                        //}
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getPcmProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.PCM_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });

// end of PCM
        tcPkrQua.setCellValueFactory(cellData -> cellData.getValue().getQuaProperty().asObject());
        tcPkrQua.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0)  {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getQuaCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getQuaUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getQuaDlr()));
                        setTooltip(tooltip);
                        // }
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getQuaProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.QUA_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
        tcPkrCpu.setCellValueFactory(cellData -> cellData.getValue().getCpuProperty().asObject());
        tcPkrCpu.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getCpuCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getCpuUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getCpuDlr()));
                        setTooltip(tooltip);
                        // }
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getCpuProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.CPU_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
        tcPkrWgh.setCellValueFactory(cellData -> cellData.getValue().getWghProperty().asObject());
        tcPkrWgh.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getWghCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getWghUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getWghDlr()));
                        setTooltip(tooltip);
                        // }
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getWghProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.WGH_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
        // SHP stat
        tcPkrShp.setCellValueFactory(cellData -> cellData.getValue().getShpProperty().asObject());
        tcPkrShp.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getShpCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getShpUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getShpDlr()));
                        setTooltip(tooltip);
                        // }
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getShpProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.SHP_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
        // invoice
        tcPkrInv.setCellValueFactory(cellData -> cellData.getValue().getInvProperty().asObject());
        tcPkrInv.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getInvCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getInvUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getInvDlr()));
                        setTooltip(tooltip);
                        // }
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getInvProperty().getValue() != 0) {
                        dspDetail(" ", AppParms.INV_STAT_NAME, myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
        tcPkrTot.setCellValueFactory(cellData -> cellData.getValue().getTotProperty().asObject());
        tcPkrTot.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getTotCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getTotUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getTotDlr()));
                        setTooltip(tooltip);
                        // }
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getTotProperty().getValue() != 0) {
                        dspDetail(" ", " ", myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
// total cartons
        tcPkrCrtn.setCellValueFactory(cellData -> cellData.getValue().getTotCtnProperty().asObject());
        tcPkrCrtn.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktByPkrTblRow, Integer> cell = new TableCell<PtktByPkrTblRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        //if (item != 0) {
                        this.getStyleClass().add("selCell");
                        final int row = this.getIndex();
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        Tooltip tooltip = new Tooltip();
                        PtktByPkrTblRow thisRow;
                        thisRow = getTableView().getItems().get(row);
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(thisRow.getTotCtn()) + "\n"
                                + "units  : " + numFormatter.format(thisRow.getTotUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(thisRow.getTotDlr()));
                        setTooltip(tooltip);
                        // }
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktByPkrTblRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktByPkrTblRow myItem = tblRow.getItem();
                    if (myItem.getTotProperty().getValue() != 0) {
                        dspDetail(" ", " ", myItem.getNamProperty().getValue());
                        //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());
                    }
                }
            });

            return cell;
        });
//
        tblPkrSum.setItems(ptktByPkrTblRow);
        prInRefresh.setVisible(false);
    }

    private void bldSumPrd(ArrayList<PtktSumByPrd> sumByPrdList) {

        ObservableList<PtktSumPrdRow> pktSumTblPrdRow = FXCollections.observableArrayList();
        XYChart.Series<String, Number> serPrt = new XYChart.Series<>();
        XYChart.Series<String, Number> serDis = new XYChart.Series<>();
//        XYChart.Series<String, Number> serAsg = new XYChart.Series<>();
        XYChart.Series<String, Number> serCus = new XYChart.Series<>();
        XYChart.Series<String, Number> serPck = new XYChart.Series<>();
        XYChart.Series<String, Number> serPcm = new XYChart.Series<>();
        XYChart.Series<String, Number> serQua = new XYChart.Series<>();
        XYChart.Series<String, Number> serCpk = new XYChart.Series<>();
        XYChart.Series<String, Number> serWgh = new XYChart.Series<>();
        XYChart.Series<String, Number> serShp = new XYChart.Series<>();
        XYChart.Series<String, Number> serInv = new XYChart.Series<>();
        ObservableList<String> pkStat = FXCollections.observableArrayList();
        ArrayList<PtktSumByStat> sumByStatList = new ArrayList<>();
        BigDecimal tPrtU = BigDecimal.ZERO;
        BigDecimal tPrtD = BigDecimal.ZERO;
        BigDecimal tDisU = BigDecimal.ZERO;
        BigDecimal tDisD = BigDecimal.ZERO;
        BigDecimal tCusU = BigDecimal.ZERO;
        BigDecimal tCusD = BigDecimal.ZERO;
        BigDecimal tPckU = BigDecimal.ZERO;
        BigDecimal tPckD = BigDecimal.ZERO;
        BigDecimal tPcmU = BigDecimal.ZERO;
        BigDecimal tPcmD = BigDecimal.ZERO;
        BigDecimal tQuaU = BigDecimal.ZERO;
        BigDecimal tQuaD = BigDecimal.ZERO;
        BigDecimal tCpuU = BigDecimal.ZERO;
        BigDecimal tCpuD = BigDecimal.ZERO;
        BigDecimal tWghU = BigDecimal.ZERO;
        BigDecimal tWghD = BigDecimal.ZERO;
        BigDecimal tShpU = BigDecimal.ZERO;
        BigDecimal tShpD = BigDecimal.ZERO;
        BigDecimal tInvU = BigDecimal.ZERO;
        BigDecimal tInvD = BigDecimal.ZERO;
        BigDecimal tTotU = BigDecimal.ZERO;
        BigDecimal tTotD = BigDecimal.ZERO;
        int tPrt = 0;
        int tDis = 0;
        int tCus = 0;
        int tShp = 0;
        int tPck = 0;
        int tPcm = 0;
        int tQua = 0;
        int tCpu = 0;
        int tWgh = 0;
        int tInv = 0;
        int tTot = 0;

        int tPrtCtn = 0;
        int tDisCtn = 0;
        int tCusCtn = 0;
        int tShpCtn = 0;
        int tPckCtn = 0;
        int tPcmCtn = 0;
        int tQuaCtn = 0;
        int tCpuCtn = 0;
        int tWghCtn = 0;
        int tInvCtn = 0;
        int tTotCtn = 0;

        int uLim = 0;  // bar chart upper limit  

        for (PtktSumByPrd sumPrd : sumByPrdList) {
//            sumByStatList = sumPrd.getSumByStat();
//            pktSumTblPrdRow.add(new PtktSumPrdRow(sumPrd.getPeriod(), sumPrd.getPrtProperty(),
//                    sumPrd.getDisProperty(), sumPrd.getPckProperty(), sumPrd.getQuaProperty(), sumPrd.getCpk(),
//                    sumPrd.getWghProperty(), sumPrd.getShpProperty(), sumPrd.getInvProperty(), sumPrd.getTotProperty()));
            PtktSumPrdRow ptkRow;
            ptkRow = new PtktSumPrdRow(sumPrd);
            pktSumTblPrdRow.add(ptkRow);
            tPrt = tPrt + ptkRow.getPrt();
            tPrtU = tPrtU.add(ptkRow.getPrtUnt());
            tPrtD = tPrtD.add(ptkRow.getPrtDlr());
            tPrtCtn += ptkRow.getPrtCtn();
            tDis = tDis + ptkRow.getDis();
            tDisU = tDisU.add(ptkRow.getDisUnt());
            tDisD = tDisD.add(ptkRow.getDisDlr());
            tDisCtn += ptkRow.getDisCtn();
            tCus += ptkRow.getCus();
            tCusU = tCusU.add(ptkRow.getCusUnt());
            tCusD = tCusD.add(ptkRow.getCusDlr());
            tCusCtn += ptkRow.getCusCtn();
            tPck = tPck + ptkRow.getPck();
            tPckU = tPckU.add(ptkRow.getPckUnt());
            tPckD = tPckD.add(ptkRow.getPckDlr());
            tPckCtn += ptkRow.getPckCtn();
            tPcm = tPcm + ptkRow.getPcm();
            tPcmU = tPcmU.add(ptkRow.getPcmUnt());
            tPcmD = tPcmD.add(ptkRow.getPcmDlr());
            tPckCtn += ptkRow.getPcmCtn();
            tQua = tQua + ptkRow.getQua();
            tQuaU = tQuaU.add(ptkRow.getQuaUnt());
            tQuaD = tQuaD.add(ptkRow.getQuaDlr());
            tQuaCtn += ptkRow.getQuaCtn();
            tCpu = tCpu + ptkRow.getCpu();
            tCpuU = tCpuU.add(ptkRow.getCpuUnt());
            tCpuD = tCpuD.add(ptkRow.getCpuDlr());
            tCpuCtn += ptkRow.getCpuCtn();
            tShp = tShp + ptkRow.getShp();
            tShpU = tShpU.add(ptkRow.getShpUnt());
            tShpD = tShpD.add(ptkRow.getShpDlr());
            tShpCtn += ptkRow.getShpCtn();
            tWgh = tWgh + ptkRow.getWgh();
            tWghU = tWghU.add(ptkRow.getWghUnt());
            tWghD = tWghD.add(ptkRow.getWghDlr());
            tWghCtn += ptkRow.getWghCtn();
            tInv = tInv + ptkRow.getInv();
            tInvU = tInvU.add(ptkRow.getInvUnt());
            tInvD = tInvD.add(ptkRow.getInvDlr());
            tInvCtn += ptkRow.getInvCtn();

            if (ptkRow.getTot() > uLim) {
                uLim = ptkRow.getTot();
            }
//            tPrt = tPrt + sumPrd.getPrt();
//            tDis = tDis + sumPrd.getDis();
////            tAsg = tAsg + sumPrd.getAsg();
//            tPck = tPck + sumPrd.getPck();
//            tPak = tPak + sumPrd.getQua();
//            tCpu = tCpu + sumPrd.getCpk();
//            tShp = tShp + sumPrd.getShp();
//            tWgh = tWgh + sumPrd.getWgh();
//            tInv = tInv + sumPrd.getInv();
            serPrt.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getPrt()));
            serDis.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getDis()));
            serCus.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getCus()));
//            serAsg.getData().add(new XYChart.Data(sumPrd.getPeriod(), sumPrd.getAsg()));
            serPck.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getPck()));
            serPcm.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getPcm()));
            serQua.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getQua()));
            serCpk.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getCpu()));
            serWgh.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getWgh()));
            serShp.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getShp()));
            serInv.getData().add(new XYChart.Data(ptkRow.getPrd(), ptkRow.getInv()));
            pkStat.add(ptkRow.getPrd());
        }
        // tTot = tPrt + tDis + tPck + tPak + tCpu + tWgh + tShp + tInv;
        pktSumTblPrdRow.add(new PtktSumPrdRow(stTot, tPrt, tDis, tCus,
                tPck, tPcm, tQua, tCpu, tWgh, tShp, tInv, tPrtU, tPrtD,
                tDisU, tDisD, tCusU, tCusD, tPckU, tPckD, tPcmU, tPcmD, tQuaU, tQuaD,
                tCpuU, tCpuD, tWghU, tWghD, tShpU, tShpD, tInvU, tInvD,
                tPrtCtn, tDisCtn, tCusCtn, tPckCtn, tPcmCtn, tQuaCtn, tCpuCtn,
                tWghCtn, tShpCtn, tInvCtn));

        tcPrdPrd.setCellValueFactory(cellData -> cellData.getValue().getPrdProperty());
        tcPrdPrd.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, String> cell = new TableCell<PtktSumPrdRow, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                    if (!empty) {
                        this.getStyleClass().add("selCell");
                    }
                }

            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    dspDetail(myItem.getPrdProperty().getValue(), " ", AppParms.ALL);
                    //System.out.println("cell clicked " + cell.getText() + "period= " + myItem.getPrdProperty());

                }
            });

            return cell;
        });
        tcPrdPrt.setCellValueFactory(cellData -> cellData.getValue().getPrtProperty().asObject());
        tcPrdPrt.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if (!empty) {
                        if (item != 0) {
                            this.getStyleClass().add("selCell");
                            Locale enUSLocale
                                    = new Locale.Builder().setLanguage("en").setRegion("US").build();
                            NumberFormat currencyFormatter
                                    = NumberFormat.getCurrencyInstance(enUSLocale);
                            DecimalFormat numFormatter = new DecimalFormat("#,###");
                            PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                            Tooltip tooltip = new Tooltip();
                            tooltip.setText(
                                    "Cartons: " + numFormatter.format(curRow.getPrtCtn()) + "\n"
                                    + "Units  : " + numFormatter.format(curRow.getPrtUnt()) + "\n"
                                    + "Dollars: " + currencyFormatter.format(curRow.getPrtDlr()));
                            setTooltip(tooltip);
                        } else {
                            this.getStyleClass().remove("selCell");
                        }
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getPrtProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), "PRT", AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        tcPrdDis.setCellValueFactory(cellData -> cellData.getValue().getDisProperty().asObject());
        tcPrdDis.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getDisCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getDisUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getDisDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getDisProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), AppParms.DIS_STAT_NAME,
                                AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        // Custome stat 
        tcPrdCus.setCellValueFactory(cellData -> cellData.getValue().getCusProperty().asObject());
        tcPrdCus.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getCusCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getCusUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getCusDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getCusProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), AppParms.CUS_STAT_NAME,
                                AppParms.ALL);
                    }
                }
            });
            return cell;
        });

        // shp Stat 
        tcPrdShp.setCellValueFactory(cellData -> cellData.getValue().getShpProperty().asObject());
        tcPrdShp.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getShpCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getShpUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getShpDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getShpProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), "SHP", AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        tcPrdPck.setCellValueFactory(cellData -> cellData.getValue().getPckProperty().asObject());
        tcPrdPck.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getPckCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getPckUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getPckDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getPckProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), "PCK", AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        // start of prd pcm 
        tcPrdPcm.setCellValueFactory(cellData -> cellData.getValue().getPcmProperty().asObject());
        tcPrdPcm.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getPcmCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getPcmUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getPcmDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getPcmProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), "PCM", AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        // end of pcm 
        tcPrdQua.setCellValueFactory(cellData -> cellData.getValue().getQuaProperty().asObject());
        tcPrdQua.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getQuaCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getQuaUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getQuaDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            // cellStyle = cell.getStyleClass();
            // cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getQuaProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), AppParms.QUA_STAT_NAME, AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        tcPrdCpk.setCellValueFactory(cellData -> cellData.getValue().getCpuProperty().asObject());
        tcPrdCpk.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getCpuCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getCpuUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getCpuDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getCpuProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), AppParms.CPU_STAT_NAME,
                                AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        tcPrdWgh.setCellValueFactory(cellData -> cellData.getValue().getWghProperty().asObject());
        tcPrdWgh.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getWghCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getWghUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getWghDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCell");
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getWghProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), "WGH", AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        tcPrdInv.setCellValueFactory(cellData -> cellData.getValue().getInvProperty().asObject());
        tcPrdInv.setCellFactory(tc -> {
            //ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if ((!empty) && (item != 0)) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getInvCtn()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getInvUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getInvDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("selCall");
                    }

                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getInvProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), "INV", AppParms.ALL);
                    }
                }
            });
            return cell;
        });
        tcPrdTot.setCellValueFactory(cellData -> cellData.getValue().getTotProperty().asObject());
        tcPrdTot.setCellFactory(tc -> {
            // ObservableList<String> cellStyle;
            TableCell<PtktSumPrdRow, Integer> cell = new TableCell<PtktSumPrdRow, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                    if (!empty && item != 0) {
                        this.getStyleClass().add("selCell");
                        Locale enUSLocale
                                = new Locale.Builder().setLanguage("en").setRegion("US").build();
                        NumberFormat currencyFormatter
                                = NumberFormat.getCurrencyInstance(enUSLocale);
                        DecimalFormat numFormatter = new DecimalFormat("#,###");
                        PtktSumPrdRow curRow = getTableView().getItems().get(getTableRow().getIndex());
                        Tooltip tooltip = new Tooltip();
                        tooltip.setText(
                                "Cartons: " + numFormatter.format(curRow.getTotCtn().intValue()) + "\n"
                                + "Units  : " + numFormatter.format(curRow.getTotUnt()) + "\n"
                                + "Dollars: " + currencyFormatter.format(curRow.getTotDlr()));
                        setTooltip(tooltip);
                    } else {
                        this.getStyleClass().remove("SelCell");
                    }
                }

            };
            //cellStyle = cell.getStyleClass();
            //cellStyle.add("selCell");
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    TableRow<PtktSumPrdRow> tblRow;
                    tblRow = cell.getTableRow();
                    PtktSumPrdRow myItem = tblRow.getItem();
                    if (myItem.getTotProperty().getValue() != 0) {
                        dspDetail(myItem.getPrdProperty().getValue(), " ", AppParms.ALL);
                    }
                }
            });
            return cell;
        });

        tblPtktSumPrd.setItems(pktSumTblPrdRow);

        bChartPtktPrd.getData().clear();
        bChartPtktPrdX.setCategories(pkStat);
        bChartPtktPrdY.setAutoRanging(false);
        bChartPtktPrdY.setTickUnit(25);
        bChartPtktPrdY.setLowerBound(0);
        bChartPtktPrdY.setUpperBound(uLim);

        // bChartPtktPrdY.setBase(10);
        bChartPtktPrd.getData().addAll(serPrt, serDis, serCus, serPck, serPcm, serQua, serCpk, serWgh, serShp, serInv);
//seupBarChartEvents(serPrt);
//seupBarChartEvents(serInv);
        //serPrt.setName("Prt");
        //serDis.setName("Dis");
//        serAsg.setName("Asg");
        //serPck.setName("Pck");
        //serPak.setName("Pak");
        //serCpk.setName("Cpu");
        // serWgh.setName("Wgh");

        serPrt.setName(AppParms.PRT_STAT_NAME);
        serDis.setName(AppParms.DIS_STAT_NAME);
        serCus.setName(AppParms.CUS_STAT_NAME);
        serPck.setName(AppParms.PCK_STAT_NAME);
        serPcm.setName(AppParms.PCM_STAT_NAME);
        serQua.setName(AppParms.QUA_STAT_NAME);
        serCpk.setName(AppParms.CPU_STAT_NAME);
        serWgh.setName(AppParms.WGH_STAT_NAME);
        serShp.setName(AppParms.SHP_STAT_NAME);
        serInv.setName(AppParms.INV_STAT_NAME);

        for (Series<String, Number> ser : bChartPtktPrd.getData()) {
            seupBarChartEvents(ser);
        }
        for (final XYChart.Data<String, Number> dtPrt : serPrt.getData()) {
            // dtPrt.getNode().setStyle("-fx-bar-fill: #7d66c4;");
//            Tooltip tooltip = new Tooltip();
//            tooltip.setText("stat = " + AppParms.PRT_STAT_NAME + "\n"
//                         +  "Count= " + dtPrt.getYValue());
////            tooltip.setText(getBarTip(dtPrt.getXValue(), AppParms.PRT_STAT_NAME));
//            Tooltip.install(dtPrt.getNode(), tooltip);
            dtPrt.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtPrt.getXValue(), "PRT", AppParms.ALL);

            });
        }

        for (final XYChart.Data<String, Number> dtDis : serDis.getData()) {
//            Tooltip tooltip = new Tooltip();
//            tooltip.setText("stat = " + AppParms.DIS_STAT_NAME + "\n"
//                         +  "Count= " + dtDis.getYValue());
////            tooltip.setText(getBarTip(dtDis.getXValue(), AppParms.DIS_STAT_NAME));
//            Tooltip.install(dtDis.getNode(), tooltip);
            dtDis.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtDis.getXValue(), "DIS", AppParms.ALL);

            });
        }

        // listen for mouse clicked on CUS on stacked bar chart 
        for (final XYChart.Data<String, Number> dtDis : serDis.getData()) {
//            Tooltip tooltip = new Tooltip();
//            tooltip.setText("stat = " + AppParms.DIS_STAT_NAME + "\n"
//                         +  "Count= " + dtDis.getYValue());
////            tooltip.setText(getBarTip(dtDis.getXValue(), AppParms.DIS_STAT_NAME));
//            Tooltip.install(dtDis.getNode(), tooltip);
            dtDis.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtDis.getXValue(), "DIS", AppParms.ALL);

            });
        }

        for (final XYChart.Data<String, Number> dtCus : serCus.getData()) {

            dtCus.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtCus.getXValue(), AppParms.CUS_STAT_NAME, AppParms.ALL);

            });
        }

        for (final XYChart.Data<String, Number> dtPck : serPck.getData()) {

            dtPck.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtPck.getXValue(), AppParms.PCK_STAT_NAME, AppParms.ALL);

            });
        }
        
        for (final XYChart.Data<String, Number> dtPcm : serPcm.getData()) {

            dtPcm.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtPcm.getXValue(), AppParms.PCM_STAT_NAME, AppParms.ALL);

            });
        }

        for (final XYChart.Data<String, Number> dtQua : serQua.getData()) {
//            Tooltip tooltip = new Tooltip();
//            tooltip.setText("stat = " + AppParms.PAK_STAT_NAME + "\n"
//                         +  "Count= " + dtPak.getYValue());
////            tooltip.setText(getBarTip(dtPak.getXValue(), AppParms.PAK_STAT_NAME));
//            Tooltip.install(dtPak.getNode(), tooltip);
            dtQua.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtQua.getXValue(), AppParms.QUA_STAT_NAME, AppParms.ALL);

            });
        }
        for (final XYChart.Data<String, Number> dtCpu : serCpk.getData()) {
//            Tooltip tooltip = new Tooltip();
//            tooltip.setText("stat = " + AppParms.CPU_STAT_NAME + "\n"
//                         +  "Count= " + dtCpu.getYValue());
////            tooltip.setText(getBarTip( dtCpu.getXValue(), AppParms.CPU_STAT_NAME));
//            Tooltip.install(dtCpu.getNode(), tooltip);
            dtCpu.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtCpu.getXValue(), "CPU", AppParms.ALL);

            });
        }

        for (final XYChart.Data<String, Number> dtWgh : serWgh.getData()) {
//            Tooltip tooltip = new Tooltip();
//            tooltip.setText("stat = " + AppParms.WGH_STAT_NAME + "\n"
//                         +  "Count= " + dtWgh.getYValue());
////            tooltip.setText(getBarTip(dtWgh.getXValue(), AppParms.WGH_STAT_NAME));
//            Tooltip.install(dtWgh.getNode(), tooltip);
            dtWgh.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtWgh.getXValue(), "WGH", AppParms.ALL);

            });
        }

        for (final XYChart.Data<String, Number> dtShp : serShp.getData()) {
//            Tooltip tooltip = new Tooltip();
//            tooltip.setText("stat = " + AppParms.SHP_STAT_NAME + "\n"
//                         +  "Count= " + dtShp.getYValue());
////            tooltip.setText(getBarTip(dtShp.getXValue(), AppParms.SHP_STAT_NAME));
//            Tooltip.install(dtShp.getNode(), tooltip);
            dtShp.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtShp.getXValue(), "SHP", AppParms.ALL);

            });
        }

        for (final XYChart.Data<String, Number> dtInv : serInv.getData()) {
//           Tooltip tooltip = new Tooltip();
//           tooltip.setText("stat = " + AppParms.INV_STAT_NAME + "\n"
//                         +  "Count= " + dtInv.getYValue());
////            tooltip.setText(getBarTip(dtInv.getXValue(), AppParms.INV_STAT_NAME));
//            Tooltip.install(dtInv.getNode(), tooltip);
            //dtInv.getNode().setStyle("-fx-bar-fill: #009973;");
            //dtInv.getNode().getStyleClass().remove("default-color0");
            // dtInv.getNode().getStyleClass().add("default-color8");
            dtInv.getNode().setOnMouseClicked((MouseEvent e) -> {
                dspDetail(dtInv.getXValue(), "INV", AppParms.ALL);

            });
        }
//        System.out.println("printing userdata");
// changing lable css style
//        int lblNodIx = 0;
//        for (Node node : bChartPtktPrd.lookupAll(".chart-legend-item")) {
//            if (node instanceof Label && ((Label) node).getGraphic() != null) {
//                String tst1 = ((Label) node).getGraphic().getStyleClass().toString();
//                ((Label) node).getGraphic().getStyleClass().toString();
//                 System.out.println("ts1=" + tst1);
//                if (tst1.equals("default-color7")) {
//                    System.out.println("found default color7");
//                    lblNodIx++;
//                }
//                if (tst1.contains("series8")) {
//                    System.out.println("changing css class");
//                    ((Label) node).getGraphic().getStyleClass().remove("default-color0");
//                    ((Label) node).getGraphic().getStyleClass().add("default-color8");
//                }
//            }
//
//        }
    }

    private String getBarTip(String prd, String cat) {
        String tip = "";
        Integer cnt = 0;
        Integer crtn = 0;
        BigDecimal units = BigDecimal.ZERO;
        BigDecimal dlrs = BigDecimal.ZERO;
        Locale enUSLocale
                = new Locale.Builder().setLanguage("en").setRegion("US").build();
        NumberFormat currencyFormatter
                = NumberFormat.getCurrencyInstance(enUSLocale);
        DecimalFormat numFormatter = new DecimalFormat("#,###");
        PtktSumByPrd ptktByPrd = ptktSum.getPtktSumPrd(prd);

        if (ptktByPrd != null) {
            PtktSumByStat ptkByStat = ptktByPrd.getSumByStat(cat);
            if (ptkByStat != null) {
                cnt = ptkByStat.getPtktCnt();
                units = ptkByStat.getTotUnt();
                dlrs = ptkByStat.getTotDlr();
                crtn = ptkByStat.getTotCrtn();
                tip = "Stat = " + cat
                        + "\n" + "Ptkt Count: " + numFormatter.format(cnt)
                        + "\n" + "Crtn Count: " + numFormatter.format(crtn)
                        + "\n" + "Units     : " + numFormatter.format(units)
                        + "\n" + "Dollars   : " + currencyFormatter.format(dlrs);
                return tip;
            }

        }

        return tip;
    }

    private void seupBarChartEvents(XYChart.Series<String, Number> series) {
        for (final XYChart.Data<String, Number> dt : series.getData()) {
            final Node n = dt.getNode();
            n.setEffect(null);
            n.setOnMouseEntered((MouseEvent e) -> {
                n.setEffect(glow);
            });
            n.setOnMouseExited((MouseEvent e) -> {
                n.setEffect(null);
            });

        }

    }

    @Override
    public void ChangedFilterReceived(ChangeFilterEvent e) {
        ptktFilter = e.getPtktFilter();
        refresh();
        // when filter is closed by pressing apply button onClose is not triggered 
        if (autoRefresh) {
            svcRefPkSumStat.restart();
        }
    }

    // private static class SvcRefPkSumStat extends ScheduledService<ArrayList<PtktSumByStat>> {
    private static class SvcRefPkSumStat extends ScheduledService<PtktSum> {

        PKHDRP pkhdrp;

        protected void setPkhdrp(PKHDRP pkhdrp) {
            this.pkhdrp = pkhdrp;
        }

        protected PKHDRP getPkhdrp() {
            return pkhdrp;
        }

        // @Override
        // protected Task<ArrayList<PtktSumByStat>> createTask() {
        @Override
        protected Task<PtktSum> createTask() {
            return new Task<PtktSum>() {
                @Override
                protected PtktSum call() throws Exception {
                    //  updateProgress(-1, 1);
                    PKHDRP pkhdrp;
                    //ArrayList<PtktSumByStat> value = new ArrayList<>();
                    //PtktSum value = new PtktSum();
                    pkhdrp = getPkhdrp();
                    PtktSum value = pkhdrp.getOpnPtktByStat();
                    //value = pkhdrp.getOpnPtktByStat();
//                    try {
//                        value = pkhdrp.getOpnPtktByStat();
//                        //                        updateProgress(1, 1);
//                    } catch (Exception ex) {
//                        System.out.println("error in backgroud task");
//                        ex.printStackTrace();
//                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    return value;
                }

            };
        }
    }

//}
    private class TskRefPkSumStat extends Task<PtktSum> {

        PKHDRP pkhdrp;
        //Task<PtktSum> task;

        protected void setPkhdrp(PKHDRP pkhdrp) {
            this.pkhdrp = pkhdrp;
        }

        // protected PKHDRP getPkhdrp() {
        //     return pkhdrp;
        // }
        @Override
        protected PtktSum call() throws Exception {
            // PKHDRP pkhdrp;
            // PtktSum value = new PtktSum();
            PtktSum value = pkhdrp.getOpnPtktByStat();
            // pkhdrp = getPkhdrp();
            //value = pkhdrp.getOpnPtktByStat();
            return value;
        }

    }

}
