/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pickticketprint;

import appOptions.OptionsFXMLController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Khatchik
 */
public class PtktMonMainController implements Initializable {

    @FXML
    private Button btnStrMon;

    @FXML
    private Spinner<Integer> spnPtktMon;
    @FXML
    private Label lblStat;
    @FXML
    private ComboBox<String> cbPrntr;
    @FXML
    private ComboBox<String> cbPrntrRptr;
    @FXML
    private ComboBox<String> cbWhse;
    @FXML
    private Button btnSettings;
    @FXML
    private Label lblJobNam;

    @FXML
    private Label lblJobUsr;

    @FXML
    private Label lblJobNum;

    private RequestedPickTickets ptktReq;
    private UpsShprParms parm;
    private int refreshInt = 0;
    private final SvcMonPtktReq svcMonPtktReq = new SvcMonPtktReq();
    private final String STR_AUTO_MON = "Re-Start Pick Ticket Monitor";
    private final String STP_AUTO_MON = "Cancel Pick Ticket Monitor";
    private boolean autoMon = true;
    private Stage curStage;

    @FXML
    private void btnStrMonPressed(ActionEvent event) {
        if (autoMon) {
            autoMon = false;
            svcMonPtktReq.cancel();
            btnStrMon.setText(STR_AUTO_MON);
        } else {
            autoMon = true;
            svcMonPtktReq.restart();
            btnStrMon.setText(STP_AUTO_MON);
        }
    }

    @FXML
    void cbPrntrAction(ActionEvent event) {
        parm.setPrinterName(
                cbPrntr.getSelectionModel().getSelectedItem());

    }

    @FXML
    void cbPrntrRptrAction(ActionEvent event) {
        parm.setPrinterNameRptr(
                cbPrntrRptr.getSelectionModel().getSelectedItem());
    }

    @FXML
    void cbWhseAction(ActionEvent event) {
        parm.setWhse(cbWhse.getSelectionModel().getSelectedItem().substring(0, 2));

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Integer> spnValues
                = FXCollections.observableArrayList(5, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300);
        SpinnerValueFactory<Integer> spnFac
                = new SpinnerValueFactory.ListSpinnerValueFactory<>(spnValues);
        spnFac.setValue(spnValues.get(1));
        refreshInt = spnValues.get(1);

        spnPtktMon.setValueFactory(spnFac);
        spnPtktMon.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            refreshInt = newValue;
            svcMonPtktReq.setDelay(Duration.seconds(refreshInt));
            svcMonPtktReq.setPeriod(Duration.seconds(refreshInt));
        });

    }

    /**
     * This method sets up initial values for the class
     *
     * @param parm Application parameter
     * @param jobNam AS400 Job name
     * @param jobUsr As400 Job User
     * @param jobNum As400 Job number
     * @param curStage Current stage
     */
    public void setIntParms(UpsShprParms parm, String jobNam, String jobUsr,
            String jobNum, Stage curStage) {
        this.parm = parm;
        this.ptktReq = new RequestedPickTickets(parm);
        this.curStage = curStage;
        svcMonPtktReq.setReqPtkt(ptktReq);
        svcMonPtktReq.setDelay(Duration.seconds(refreshInt));
        svcMonPtktReq.setPeriod(Duration.seconds(refreshInt));
        svcMonPtktReq.start();
        svcMonPtktReq.setOnSucceeded(event -> {
            SvcRtn svcRtn = svcMonPtktReq.getValue();
            if (svcRtn.isFatalErr()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, svcRtn.getErrMsg());
                alert.setTitle("Fatal Error");
                alert.showAndWait();
                this.curStage.close();
            }
        });

        lblStat.textProperty().bind(svcMonPtktReq.stateProperty().asString());
        lblJobNam.setText(jobNam);
        lblJobUsr.setText(jobUsr);
        lblJobNum.setText(jobNum);
        btnSettings.disableProperty().bind(svcMonPtktReq.stateProperty().isEqualTo(ScheduledService.State.RUNNING));
        btnStrMon.setText(STP_AUTO_MON);
        autoMon = true;
        ArrayList<Printer> prtList = new ArrayList<>();
        prtList.addAll(Printer.getAllPrinters());

        ObservableList<String> olPrtrs = FXCollections.observableArrayList();
        boolean bFndPrt = false;
        boolean bFndPrtRprt = false;
        String rprtPrtr = " ";
        // int prtIx = 0;
        for (Printer prt : prtList) {
            olPrtrs.add(prt.getName());
            if (prt.getName().equalsIgnoreCase(parm.getPrinterName())) {
                bFndPrt = true;
            }
            if (prt.getName().equalsIgnoreCase(parm.getPrinterNameRptr())) {
                bFndPrtRprt = true;
                rprtPrtr = prt.getName();
            }

        }
        //prtList.forEach((prt) -> {
        //    olPrtrs.add(prt.getName());
        //});
        cbPrntr.setItems(olPrtrs);
        cbPrntrRptr.setItems(olPrtrs);
        if (bFndPrt) {
            cbPrntr.getSelectionModel().select(parm.getPrinterName());
        }
        if (bFndPrtRprt) {
            cbPrntrRptr.getSelectionModel().select(rprtPrtr);
        }

        CNTLP cntlp = new CNTLP(parm);
        ArrayList<String> whList = new ArrayList<>();

        try {
            whList = cntlp.getWhList();
            ObservableList olWh = FXCollections.observableArrayList();
            boolean bFndWh = false;
            int whIx = 0;
            for (int i = 0; i < whList.size(); i++) {
                olWh.add(whList.get(i));
                if (whList.get(i).substring(0, 2).equals(parm.getWhse().trim())) {
                    whIx = i;
                    bFndWh = true;
                }
            }
            cbWhse.setItems(olWh);
            if (bFndWh) {
                cbWhse.getSelectionModel().select(whIx);
            }
        } catch (CustomException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "retreiving Warehouse list\n" + ex.getLocalizedMessage());
            alert.setTitle("Error retrieving Warehouse List");
            alert.showAndWait();
        }
    }

    public void deleteOldPtkt() {

        if (parm.getPtktRtnDays() > 0) {
            PurgeOldPtkt purgeOldTkt = new PurgeOldPtkt();
            purgeOldTkt.setUpsShprParms(parm);
            Worker worker = purgeOldTkt.getWorker();
            new Thread((Runnable) worker).start();
        }

    }

    public UpsShprParms getParm() {
        return parm;
    }

    @FXML
    void btnSettingsClicked(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/appOptions/OptionsFXML.fxml"));

            Parent optRoot = (Parent) fxmlLoader.load();
            OptionsFXMLController appOpt = (OptionsFXMLController) fxmlLoader.getController();

            Stage stage = new Stage();

            //stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/filter+.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Settings");
            appOpt.setIntParm(parm, stage, this);
            stage.setScene(new Scene(optRoot));
            svcMonPtktReq.cancel();
            stage.setOnCloseRequest(e -> {
                if (autoMon) {
                    svcMonPtktReq.restart();
                    btnStrMon.setText(STP_AUTO_MON);
                }
            });
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(PtktMonMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void restartMon() {
        if (autoMon) {
            svcMonPtktReq.restart();
            btnStrMon.setText(STP_AUTO_MON);
        }

    }

//    private void monPtktPrt() {
//        //ptktReq.rtvCrtnData();
//
//    }
    // private static class SvcRefPkSumStat extends ScheduledService<ArrayList<PtktSumByStat>> {
    private static class SvcMonPtktReq extends ScheduledService<SvcRtn> {

        private RequestedPickTickets reqPtkt;

        protected void setReqPtkt(RequestedPickTickets reqPtkt) {
            this.reqPtkt = reqPtkt;
        }

        // @Override
        // protected Task<ArrayList<PtktSumByStat>> createTask() {
        @Override
        protected Task<SvcRtn> createTask() {
            //return new Task<ArrayList<PtktSumByStat>>()
            return new Task<SvcRtn>() {
                @Override
                protected SvcRtn call() throws Exception {
                    SvcRtn svcRtn = new SvcRtn();

                    reqPtkt.rtvCrtnData();
                    svcRtn = reqPtkt.getSvcRtn();
                    reqPtkt.voidCartons();

                    return svcRtn;
                }

                @Override

                public void succeeded() {
                    super.succeeded();
                    updateMessage("The task ended successfully.");
                }

            };
        }

    }

}
