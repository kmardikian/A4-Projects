/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptktFilter;

import com.a4.utils.ConnectAs400;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Callback;
import ptktData.CNTLP;
import ptktData.CUSTP;
import ptktData.CustomException;
import ptktData.WrhData;

/* 
 * FXML Controller class
 *
 * @author Khatchik
 */
public class PtktFilterController implements Initializable {

    @FXML
    private JFXTextField txtFrmPtkt;
    @FXML
    private JFXTextField txtToPtkt;

    @FXML
    private JFXTextField txtToOrd;

    @FXML
    private JFXTextField txtFrmOrd;

    @FXML
    private DatePicker dpFrmPrtDt;

    @FXML
    private DatePicker dpToPrtDt;

    @FXML
    private DatePicker dpFrmStDt;

    @FXML
    private DatePicker dpToStDt;

    @FXML
    private JFXTextField txtSoldTo1;
    @FXML
    private JFXTextField txtSoldTo2;
    @FXML
    private JFXTextField txtSoldTo3;
    @FXML
    private JFXTextField txtSoldTo4;
    @FXML
    private JFXTextField txtSoldTo5;
    @FXML
    private JFXTextField txtSvia1;

    @FXML
    private JFXTextField txtSvia2;

    @FXML
    private JFXTextField txtSvia3;

    @FXML
    private JFXTextField txtSvia4;

    @FXML
    private JFXTextField txtSvia5;

    @FXML
    private JFXCheckBox cbxPrt;

    @FXML
    private JFXCheckBox cbxDst;

    @FXML
    private JFXCheckBox cbxAsg;

    @FXML
    private JFXCheckBox cbxPck;

    @FXML
    private JFXCheckBox cbxPak;

    @FXML
    private JFXCheckBox cbxCpu;

    @FXML
    private JFXCheckBox cbxWgh;

    @FXML
    private JFXCheckBox cbxInv;

    @FXML
    private Button btnApplyFilter;
    @FXML
    private ListView<String> lvWrh;
    @FXML
    private Label lblCus1Nam;

    @FXML
    private Label lblCus2Nam;

    @FXML
    private Label lblCus3Nam;

    @FXML
    private Label lblCus4Nam;

    @FXML
    private Label lblCus5Nam;
    @FXML
    private Label lblSvia1;

    @FXML
    private Label lblSvia2;

    @FXML
    private Label lblSvia3;

    @FXML
    private Label lblSvia4;

    @FXML
    private Label lblSvia5;
    @FXML
    private Label lblPtktMsg;

    @FXML
    private Label lblOrdMsg;
    @FXML
    private Label lblPrtDtMsg;

    @FXML
    private Label lblStDtMsg;

    private String curDat;
    private final SimpleDateFormat datFmt = new SimpleDateFormat("yyyyMMdd");
    private final DateTimeFormatter dtFormater = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final ArrayList<ChangedFilterListener> filterListener = new ArrayList<>();
    private PtktFilter ptktFilter;
    private CNTLP cntlp;
    private CUSTP custp;
    private ObservableList<String> selWrh = FXCollections.observableArrayList();

    private String sPtktFrm;
    private String sPtktTo;
    private String sOrdFrm;
    private String sOrdTo;
    private final String INV_CUST = "Invalid Customer";
    private final String INV_SVIA = "Invalid Ship Via";
    private final String TOPTK_LS_FRPTK = "To Pick ticket can not be less that From Pick ticket";
    private final String TOORD_LS_FTORD = "To Order Can Not be less than from order";
    private final String TOPRTDT_LS_FRPRTDT = "To Print Date can not be before from print Date";
    private final String TOSTRDT_LS_FRSTRDT = "To Start Date can not be before from start Datet";
    private final String Inv_Date_FMT = "Invalid Date Format";

    @FXML
    public void btnApplyFilterClicked(ActionEvent event) {

        if (tstError()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please correct items in Red");
            alert.setTitle("Entered data contains errors");
            alert.showAndWait();
            return;
        }

        ptktFilter.getWrhList().clear();
        selWrh = lvWrh.getSelectionModel().getSelectedItems();
//        System.out.println("selWrh size=" + selWrh.size());
        for (int i = 0; i < selWrh.size(); i++) {
            if (selWrh.get(i)== null) {
               // System.out.println("selWrh " + i + "is null");
                // known javafx bug deselcting lower level return null 
                // first time but second time works fine 
            }
 //           System.out.println("selWrh-" +  i +"= " + selWrh.get(i));
            ptktFilter.getWrhList().add(selWrh.get(i).substring(0, 2));

        }
        ArrayList<String> solList = new ArrayList<>();
        if (!txtSoldTo1.getText().trim().isEmpty()) {
            solList.add(txtSoldTo1.getText());
        }

        if (!txtSoldTo2.getText().trim().isEmpty()) {
            solList.add(txtSoldTo2.getText());
        }

        if (!txtSoldTo3.getText().trim().isEmpty()) {
            solList.add(txtSoldTo3.getText());
        }

        if (!txtSoldTo4.getText().trim().isEmpty()) {
            solList.add(txtSoldTo4.getText());
        }

        if (!txtSoldTo5.getText().trim().isEmpty()) {
            solList.add(txtSoldTo5.getText());
        }
        ptktFilter.setSoldToList(solList);

        ArrayList<String> sViaList = new ArrayList<>();

        if (!txtSvia1.getText().trim().isEmpty()) {
            sViaList.add(txtSvia1.getText());
        }

        if (!txtSvia2.getText().trim().isEmpty()) {
            sViaList.add(txtSvia2.getText());
        }

        if (!txtSvia3.getText().trim().isEmpty()) {
            sViaList.add(txtSvia3.getText());
        }

        if (!txtSvia4.getText().trim().isEmpty()) {
            sViaList.add(txtSvia4.getText());
        }

        if (!txtSvia5.getText().trim().isEmpty()) {
            sViaList.add(txtSvia5.getText());
        }

        ptktFilter.setShipViaList(sViaList);

        fireFilterChangeEvent();

        // String sFrmPrtDt = curDat;
        // LocalDate ldt;
        // Calendar frmPrtDt = Calendar.getInstance();
        // if (dpFrmPrtDt.getValue() != null) {
        //   ldt = dpFrmPrtDt.getValue();
        //   sFrmPrtDt = dpFrmPrtDt.getValue().format(dtFormater);
        //   wYear = dpFrmPrtDt.getValue().getYear();
        //   wMon = dpFrmPrtDt.getValue().getMonthValue();
        //   wDay = dpFrmPrtDt.getValue().getDayOfMonth();
        //   frmPrtDt.set(wYear, wMon, wDay);
        // }
        //System.out.println("selected print date =" + sFrmPrtDt);
        Stage stage = (Stage) btnApplyFilter.getScene().getWindow();
        stage.close();

    }

    private boolean tstError() {
        boolean rtn = false;
        if (lblPtktMsg.getStyleClass().contains("error")
                || lblOrdMsg.getStyleClass().contains("error")
                || lblCus1Nam.getStyleClass().contains("error")
                || lblCus2Nam.getStyleClass().contains("error")
                || lblCus3Nam.getStyleClass().contains("error")
                || lblCus4Nam.getStyleClass().contains("error")
                || lblCus5Nam.getStyleClass().contains("error")
                || lblSvia1.getStyleClass().contains("error")
                || lblSvia2.getStyleClass().contains("error")
                || lblSvia3.getStyleClass().contains("error")
                || lblSvia4.getStyleClass().contains("error")
                || lblSvia5.getStyleClass().contains("error")
                || lblPrtDtMsg.getStyleClass().contains("error")
                || lblStDtMsg.getStyleClass().contains("error")) {
            rtn = true;
        }
        return rtn;
    }

    public void cbxPrtClicked(ActionEvent event) {
        ptktFilter.setPrtSel(cbxPrt.isSelected());

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        curDat = datFmt.format(new Date());
        ptktFilter = new PtktFilter();
        DecimalFormat formater7 = new DecimalFormat("0000000");
        cntlp = new CNTLP(ConnectAs400.getLib());
        custp = new CUSTP(ConnectAs400.getLib());
        lvWrh.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> wrhList = FXCollections.observableArrayList();
        disablePastDates(dpFrmPrtDt, dpToPrtDt);
        ArrayList<WrhData> wrhArray = new ArrayList<>();

        try {
            wrhArray = (cntlp.getWrhList());
            String wrhName;
            for (WrhData wrhRec : wrhArray) {
                wrhName = wrhRec.getWrhCode() + " - " + wrhRec.getWrhName();
                wrhList.add(wrhName);
            }
            lvWrh.setItems(wrhList);

        } catch (CustomException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving warehouse list /n" + ex.getLocalizedMessage());
            alert.setTitle("Error retrirving Warehouse list");
            alert.showAndWait();
            Logger.getLogger(PtktFilterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtFrmPtkt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String s;
                if (!newValue.matches("\\d*")) {
                    s = newValue.replaceAll("[^\\d]", "");
                } else {
                    s = newValue;
                }
                if (s.trim().length() > 7) {
                    s = s.trim().substring(0, 7);
                } else {
                    s = s.trim();
                }
                txtFrmPtkt.setText(s);
                //}
            }
        });
        txtFrmPtkt.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            ObservableList<String> tfStyClass = txtToPtkt.getStyleClass();
            ObservableList<String> lblStyClass = lblPtktMsg.getStyleClass();
            if (!newPropVal) {
                ptktFilter.setPtktFr(txtFrmPtkt.getText());
                if (!txtFrmPtkt.getText().trim().isEmpty()) {
                    sPtktFrm = formater7.format(Double.valueOf(txtFrmPtkt.getText()));
                    txtFrmPtkt.setText(sPtktFrm);
                    if (!txtToPtkt.getText().trim().isEmpty()
                            && sPtktFrm.compareTo(txtToPtkt.getText()) > 0) {
                        lblPtktMsg.setText(TOPTK_LS_FRPTK);
                        tfStyClass.add("error");
                        lblStyClass.add("error");
                    } else {
                        tfStyClass.removeAll(Collections.singleton("error"));
                        lblStyClass.removeAll(Collections.singleton("error"));
                        lblPtktMsg.setText("");
                    }
                }
            }

        });

        txtToPtkt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String s;
                if (!newValue.matches("\\d*")) {
                    s = newValue.replaceAll("[^\\d]", "");
                } else {
                    s = newValue;
                }
                if (s.trim().length() > 7) {
                    s = s.trim().substring(0, 7);
                } else {
                    s = s.trim();
                }
                txtToPtkt.setText(s);
            }
        });

        txtToPtkt.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            ObservableList<String> tfStyClass = txtToPtkt.getStyleClass();
            ObservableList<String> lblStyClass = lblPtktMsg.getStyleClass();
            if (!newPropVal) {
                ptktFilter.setPtktTo(txtToPtkt.getText());
                if (!txtToPtkt.getText().trim().isEmpty()) {
                    sPtktTo = formater7.format(Double.valueOf(txtToPtkt.getText()));
                    txtToPtkt.setText(sPtktTo);
                    if (!txtToPtkt.getText().trim().isEmpty()
                            && sPtktFrm.compareTo(sPtktTo) > 0) {
                        lblPtktMsg.setText(TOPTK_LS_FRPTK);
                        tfStyClass.add("error");
                        lblStyClass.add("error");
                    } else {
                        tfStyClass.removeAll(Collections.singleton("error"));
                        lblStyClass.removeAll(Collections.singleton("error"));
                        lblPtktMsg.setText("");
                    }
                }
            }

        });

        txtFrmOrd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String s;
                if (!newValue.matches("\\d*")) {
                    s = newValue.replaceAll("[^\\d]", "");
                } else {
                    s = newValue;
                }
                if (s.trim().length() > 7) {
                    s = s.trim().substring(0, 7);
                } else {
                    s = s.trim();
                }
                txtFrmOrd.setText(s);

            }
        });

        txtFrmOrd.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            ObservableList<String> tfStyClass = txtFrmOrd.getStyleClass();
            ObservableList<String> lblStyClass = lblOrdMsg.getStyleClass();
            if (!newPropVal) {
                ptktFilter.setOrdFr(txtFrmOrd.getText());
                if (!txtFrmOrd.getText().trim().isEmpty()) {
                    sOrdFrm = formater7.format(Double.valueOf(txtFrmOrd.getText()));
                    txtFrmOrd.setText(sOrdFrm);
                    if (!txtToOrd.getText().trim().isEmpty()
                            && sOrdFrm.compareTo(sOrdTo) > 0) {
                        lblOrdMsg.setText(TOORD_LS_FTORD);
                        tfStyClass.add("error");
                        lblStyClass.add("error");
                    } else {
                        tfStyClass.removeAll(Collections.singleton("error"));
                        lblStyClass.removeAll(Collections.singleton("error"));
                        lblPtktMsg.setText("");
                    }
                }
            }

        });

        txtToOrd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String s;
                if (!newValue.matches("\\d*")) {
                    s = newValue.replaceAll("[^\\d]", "");
                } else {
                    s = newValue;
                }
                if (s.trim().length() > 7) {
                    s = s.trim().substring(0, 7);
                } else {
                    s = s.trim();
                }
                txtToOrd.setText(s);
            }
        });
        txtToOrd.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            ObservableList<String> tfStyClass = txtToOrd.getStyleClass();
            ObservableList<String> lblStyClass = lblOrdMsg.getStyleClass();
            if (!newPropVal) {
                ptktFilter.setOrdTo(txtToOrd.getText());
                if (!txtToOrd.getText().trim().isEmpty()) {
                    sOrdTo = formater7.format(Double.valueOf(txtToOrd.getText()));
                    txtToOrd.setText(sOrdTo);
                    if (!txtToOrd.getText().trim().isEmpty()
                            && sOrdFrm.compareTo(sOrdTo) > 0) {
                        lblOrdMsg.setText(TOORD_LS_FTORD);
                        tfStyClass.add("error");
                        lblStyClass.add("error");
                    } else {
                        tfStyClass.removeAll(Collections.singleton("error"));
                        lblStyClass.removeAll(Collections.singleton("error"));
                        lblOrdMsg.setText("");
                    }
                }
            }

        });

        //
        valSoldTo(txtSoldTo1, lblCus1Nam);
        valSoldTo(txtSoldTo2, lblCus2Nam);
        valSoldTo(txtSoldTo3, lblCus3Nam);
        valSoldTo(txtSoldTo4, lblCus4Nam);
        valSoldTo(txtSoldTo5, lblCus5Nam);

        valSvia(txtSvia1, lblSvia1);
        valSvia(txtSvia2, lblSvia2);
        valSvia(txtSvia3, lblSvia3);
        valSvia(txtSvia4, lblSvia4);
        valSvia(txtSvia5, lblSvia5);

        // From Prt date
        dpFrmPrtDt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    try {
                        LocalDate lcd = dpFrmPrtDt.getConverter().fromString(dpFrmPrtDt.getEditor().getText());
                        dpFrmPrtDt.setValue(lcd);
                        if (dpFrmPrtDt.getValue() != null) {
                            setPrtDtMsg();
                            ptktFilter.setPrtDatFr(lcd.format(dtFormater));
//                            lblPrtDtMsg.setText("");
//                            lblPrtDtMsg.getStyleClass().removeAll(Collections.singleton("error"));
//                            dpToPrtDt.getStyleClass().removeAll(Collections.singleton("error"));
                        } else {
                            ptktFilter.setPrtDatFr("");
                        }
                    } catch (Exception ex) {
                        lblPrtDtMsg.setText(Inv_Date_FMT);
                        lblPrtDtMsg.getStyleClass().add("error");
                        dpFrmPrtDt.getStyleClass().add("error");

                    }
                }

            }
        }
        );
        // to print date 
        dpToPrtDt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    try {
                        LocalDate lcd = dpToPrtDt.getConverter().fromString(dpToPrtDt.getEditor().getText());
                        dpToPrtDt.setValue(lcd);
                        if (dpToPrtDt.getValue() != null) {
                            setPrtDtMsg();
                            ptktFilter.setPrtDatTo(lcd.format(dtFormater));
//                            lblPrtDtMsg.setText("");
//                            lblPrtDtMsg.getStyleClass().removeAll(Collections.singleton("error"));
//                            dpToPrtDt.getStyleClass().removeAll(Collections.singleton("error"));
                        } else {
                            ptktFilter.setPrtDatTo("");
                        }
                    } catch (Exception ex) {
                        lblPrtDtMsg.setText(Inv_Date_FMT);
                        lblPrtDtMsg.getStyleClass().add("error");
                        dpToPrtDt.getStyleClass().add("error");

                    }
                }

            }
        }
        );

        // To Start Date  
        dpFrmStDt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    try {
                        LocalDate lcd = dpFrmStDt.getConverter().fromString(dpFrmStDt.getEditor().getText());
                        dpFrmStDt.setValue(lcd);
                        if (dpFrmStDt.getValue() != null) {
                            setStrDtMsg();
                            ptktFilter.setStrDatFr(lcd.format(dtFormater));
//                            lblStDtMsg.setText("");
//                            lblStDtMsg.getStyleClass().removeAll(Collections.singleton("error"));
//                            dpFrmStDt.getStyleClass().removeAll(Collections.singleton("error"));
                        } else {
                            ptktFilter.setStrDatFr("");
                        }
                    } catch (Exception ex) {
                        lblStDtMsg.setText(Inv_Date_FMT);
                        lblStDtMsg.getStyleClass().add("error");
                        dpFrmStDt.getStyleClass().add("error");
                    }
                }

            }
        }
        );
        dpToStDt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    try {
                        LocalDate lcd = dpToStDt.getConverter().fromString(dpToStDt.getEditor().getText());
                        dpToStDt.setValue(lcd);
                        if (dpToStDt.getValue() != null) {
                            setStrDtMsg();
                            ptktFilter.setStrDatTo(lcd.format(dtFormater));
//                            lblStDtMsg.setText("");
//                            lblStDtMsg.getStyleClass().removeAll(Collections.singleton("error"));
//                            dpToStDt.getStyleClass().removeAll(Collections.singleton("error"));
                        } else {
                            ptktFilter.setStrDatTo("");
                        }
                    } catch (Exception ex) {
                        lblStDtMsg.setText(Inv_Date_FMT);
                        lblStDtMsg.getStyleClass().add("error");
                        dpToStDt.getStyleClass().add("error");
                    }
                }

            }
        }
        );

    }

    private void valSoldTo(JFXTextField tfSoldTo, Label lblSolName) {
        ObservableList<String> tfStyClass = tfSoldTo.getStyleClass();
        ObservableList<String> lblStyClass = lblSolName.getStyleClass();
        tfSoldTo.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {

            if (!newPropVal) {
                if (!tfSoldTo.getText().isEmpty()) {
                    try {
                        String cuName = custp.getCust(tfSoldTo.getText());
                        if (cuName == null) {
                            lblSolName.setText(INV_CUST);
                            tfStyClass.add("error");
                            lblStyClass.add("error");
                        } else {
                            lblSolName.setText(cuName);
                            lblStyClass.removeAll(Collections.singleton("error"));
                            tfStyClass.removeAll(Collections.singleton("error"));
                        }
                    } catch (CustomException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving Customer Data /n" + ex.getLocalizedMessage());
                        alert.setTitle("Error retrirving Customer Data list");
                        alert.showAndWait();
                        Logger.getLogger(PtktFilterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // System.out.println("Sold to 1 = " + txtSoldTo1.getText());
                }
            }
        });

    }

    private void valSvia(JFXTextField tfSvia, Label lblSviaDes) {
        ObservableList<String> tfStyClass = tfSvia.getStyleClass();
        ObservableList<String> lblStyClass = lblSviaDes.getStyleClass();

        tfSvia.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {

            if (!newPropVal) {

                if (!tfSvia.getText().isEmpty()) {
                    try {
                        String sViaDes = cntlp.getShipViaDes(tfSvia.getText().toUpperCase());
                        if (sViaDes == null) {
                            lblSviaDes.setText(INV_SVIA);
                            tfStyClass.add("error");
                            lblStyClass.add("error");
                        } else {
                            lblSviaDes.setText(sViaDes);
                            lblStyClass.removeAll(Collections.singleton("error"));
                            tfStyClass.removeAll(Collections.singleton("error"));
                        }
                    } catch (CustomException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error retrieving Ship Via /n" + ex.getLocalizedMessage());
                        alert.setTitle("Error retrirving Ship Via Data ");
                        alert.showAndWait();
                        Logger.getLogger(PtktFilterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // System.out.println("Sold to 1 = " + txtSoldTo1.getText());
                }
            }
        });
    }

    //private void datConverter(DatePicker dp) {
    //    dp.setConverter(new StringConverter<LocalDate>() {
    //        @Override
    //        public String toString(LocalDate object) {
    //            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //        }
//
 //           @Override
 //           public LocalDate fromString(String string) {
  //              throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  //          }
//
 //       });

    //}

    //   @FXML
    //   public void dpFrmPrtDtAction(ActionEvent event) {
    //       System.out.println("dpFrmPrtDt Action dt = " + dpFrmPrtDt.getValue());
    //       if (dpFrmPrtDt.getValue() != null) {
    //           ptktFilter.setPrtDatFr(dpFrmPrtDt.getValue().format(dtFormater));
    // disablePastDates(dpFrmPrtDt, dpToPrtDt);
//            setPrtDtMsg();
//        } else {
//            ptktFilter.setPrtDatFr("");
//        }
//    }
//    @FXML
//    public void dpToPrtDtAction(ActionEvent event) {
//        System.out.println("Action event to Print date" + dpToPrtDt.getValue());
//        LocalDate ldt;
//        if (dpToPrtDt.getValue() != null) {
//            setPrtDtMsg();
//            ptktFilter.setPrtDatTo(dpToPrtDt.getValue().format(dtFormater));
//
//        } else {
//            ptktFilter.setPrtDatTo("");
//        }
//
 //   }

    private void setPrtDtMsg() {
        if (dpToPrtDt.getValue() != null && dpFrmPrtDt.getValue() != null) {
            if (dpFrmPrtDt.getValue().isAfter(dpToPrtDt.getValue())) {
                lblPrtDtMsg.setText(TOPRTDT_LS_FRPRTDT);
                lblPrtDtMsg.getStyleClass().add("error");
                dpToPrtDt.getStyleClass().add("error");
            } else {
                lblPrtDtMsg.setText("");
                lblPrtDtMsg.getStyleClass().removeAll(Collections.singleton("error"));
                dpToPrtDt.getStyleClass().removeAll(Collections.singleton("error"));
            }
        }

    }

//    @FXML
//    public void dpFrmStDtAction(ActionEvent event) {
//
//        if (dpFrmStDt.getValue() != null) {
//            ptktFilter.setStrDatFr(dpFrmStDt.getValue().format(dtFormater));
//            disablePastDates(dpFrmStDt, dpToStDt);
//            setStrDtMsg();
//        } else {
//            ptktFilter.setStrDatFr("");
//        }
//
//    }
//
//    @FXML
//    public void dpToStDtAction(ActionEvent event) {
//        if (dpToStDt.getValue() != null) {
//            setStrDtMsg();
//            ptktFilter.setStrDatTo(dpToStDt.getValue().format(dtFormater));
//        } else {
//            ptktFilter.setStrDatTo("");
//        }
//
//    }

    private void setStrDtMsg() {
        if (dpToStDt.getValue() != null && dpFrmStDt.getValue() != null) {
            if (dpFrmStDt.getValue().isAfter(dpToStDt.getValue())) {
                lblStDtMsg.setText(TOSTRDT_LS_FRSTRDT);
                lblStDtMsg.getStyleClass().add("error");
                //boolean tst =lblStDtMsg.getStyleClass().contains("error");
                //System.out.println("start date err:" + tst);
                dpToStDt.getStyleClass().add("errer");
            } else {
                lblStDtMsg.setText("");
                lblStDtMsg.getStyleClass().removeAll(Collections.singleton("error"));
                dpToStDt.getStyleClass().removeAll(Collections.singleton("error"));
            }
        }

    }

    private void disablePastDates(DatePicker dpFrmDt, DatePicker dpToDt) {

        Callback<DatePicker, DateCell> dayCellFac = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(dpFrmDt.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                    }
                };
            }
        };
        dpToDt.setDayCellFactory(dayCellFac);

    }

    public void setPtktFilter(PtktFilter ptktFilter) {
        this.ptktFilter = ptktFilter;
        txtFrmPtkt.setText(ptktFilter.getPtktFr());
        sPtktFrm = ptktFilter.getPtktFr();
        txtToPtkt.setText(ptktFilter.getPtktTo());
        sPtktTo = ptktFilter.getPtktTo();
        txtFrmOrd.setText(ptktFilter.getOrdFr());
        sOrdFrm = ptktFilter.getOrdFr();
        txtToOrd.setText(ptktFilter.getOrdTo());
        sOrdTo = ptktFilter.getOrdTo();
        if (!ptktFilter.getPrtDatFr().trim().isEmpty()) {
            dpFrmPrtDt.setValue(LocalDate.parse(ptktFilter.getPrtDatFr(), dtFormater));
            // disablePastDates(dpFrmPrtDt, dpToPrtDt);
        }
        if (!ptktFilter.getPrtDatTo().trim().isEmpty()) {
            dpToPrtDt.setValue(LocalDate.parse(ptktFilter.getPrtDatTo(), dtFormater));
        }
        if (!ptktFilter.getStrDatFr().trim().isEmpty()) {
            dpFrmStDt.setValue(LocalDate.parse(ptktFilter.getStrDatFr(), dtFormater));
            // disablePastDates(dpFrmStDt, dpToStDt);
        }
        if (!ptktFilter.getStrDatTo().trim().isEmpty()) {
            dpToStDt.setValue(LocalDate.parse(ptktFilter.getStrDatTo(), dtFormater));
        }
        if (!ptktFilter.getSoldToList().isEmpty()) {
            ArrayList<String> solList = ptktFilter.getSoldToList();
            try {
                for (int i = 0; i < solList.size(); i++) {
                    switch (i) {
                        case 0:
                            txtSoldTo1.setText(solList.get(i));
                            lblCus1Nam.setText(custp.getCust(solList.get(i)));
                            break;
                        case 1:
                            txtSoldTo2.setText(solList.get(i));
                            lblCus2Nam.setText(custp.getCust(solList.get(i)));
                            break;
                        case 2:
                            txtSoldTo3.setText(solList.get(i));
                            lblCus3Nam.setText(custp.getCust(solList.get(i)));
                            break;
                        case 3:
                            txtSoldTo4.setText(solList.get(i));
                            lblCus4Nam.setText(custp.getCust(solList.get(i)));
                            break;
                        case 4:
                            txtSoldTo5.setText(solList.get(i));
                            lblCus5Nam.setText(custp.getCust(solList.get(i)));
                            break;
                    }
                }
            } catch (CustomException ex) {

            }
        }
        ArrayList<String> sViaList = ptktFilter.getShipViaList();
        try {
            for (int i = 0; i < sViaList.size(); i++) {
                switch (i) {
                    case 0:
                        txtSvia1.setText(sViaList.get(i));
                        lblSvia1.setText(cntlp.getShipViaDes(sViaList.get(i).toUpperCase()));
                        break;
                    case 1:
                        txtSvia2.setText(sViaList.get(i));
                        lblSvia2.setText(cntlp.getShipViaDes(sViaList.get(i).toUpperCase()));
                        break;
                    case 2:
                        txtSvia3.setText(sViaList.get(i));
                        lblSvia3.setText(cntlp.getShipViaDes(sViaList.get(i).toUpperCase()));
                        break;
                    case 3:
                        txtSvia4.setText(sViaList.get(i));
                        lblSvia4.setText(cntlp.getShipViaDes(sViaList.get(i).toUpperCase()));
                        break;
                    case 4:
                        txtSvia5.setText(sViaList.get(i));
                        lblSvia5.setText(cntlp.getShipViaDes(sViaList.get(i).toUpperCase()));
                        break;
                }
            }
        } catch (CustomException ex) {

        }
        cbxPrt.setSelected(ptktFilter.isPrtSel());
        cbxDst.setSelected(ptktFilter.isDisSel());
        cbxAsg.setSelected(ptktFilter.isAsgSel());
        cbxPck.setSelected(ptktFilter.isPckSel());
        cbxPak.setSelected(ptktFilter.isPakSel());
        cbxCpu.setSelected(ptktFilter.isCpuSel());
        cbxWgh.setSelected(ptktFilter.isWghSel());

        cbxInv.setSelected(ptktFilter.isInvSel());

        ObservableList<String> wrhLv = lvWrh.getItems();
        ArrayList<String> wrhLst = ptktFilter.getWrhList();
        for (String wrh : wrhLst) {
            for (int i = 0; i < wrhLv.size(); i++) {
                if (wrh.equals(wrhLv.get(i).substring(0, 2))) {
                    lvWrh.getSelectionModel().select(i);
                }
            }
        }

    }

    public synchronized void addFilterListener(ChangedFilterListener l) {
        filterListener.add(l);
    }

    public synchronized void rmvFilterListener(ChangedFilterListener l) {
        filterListener.remove(l);
    }

    private synchronized void fireFilterChangeEvent() {
        ChangeFilterEvent evt = new ChangeFilterEvent(this, ptktFilter);
        for (ChangedFilterListener l : filterListener) {
            l.ChangedFilterReceived(evt);
        }
    }

}
