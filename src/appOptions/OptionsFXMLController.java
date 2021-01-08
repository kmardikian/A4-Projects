/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appOptions;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pickticketprint.AppParms;
import pickticketprint.PtktMonMainController;
import pickticketprint.UpsShprParms;

/**
 * FXML Controller class
 *
 * @author Khatchik
 */
public class OptionsFXMLController implements Initializable {

    @FXML
    private TextField txtAs400Host;

    @FXML
    private TextField txtAs400Uid;

    @FXML
    private TextField txtAs400Pwd;

    @FXML
    private TextField txtAs400DtaLib;

    @FXML
    private TextField txtAs400ExpLib;

    @FXML
    private TextField txtUpsUid;

    @FXML
    private TextField txtUpsPwd;

    @FXML
    private TextField txtUpsLic;

    @FXML
    private TextField txtUpsShUrl;

    @FXML
    private TextField txtUpsVodUrl;

    @FXML
    private TextField txtLblLoc;
    @FXML 
    
    private TextField txtPdfRtn;

    @FXML
    private Button btnLblLoc;

    @FXML
    private TextField txtCmpLogoLoc;

    @FXML
    private Button btnCmpLogoLoc;

    @FXML
    private TextField txtJaspLoc;

    @FXML
    private Button btnJaspLoc;

    @FXML
    private Button btnAccept;

    @FXML
    private Label lblAs400Host;

    @FXML
    private Label lblAs400Uid;

    @FXML
    private Label lblAs400Pwd;

    @FXML
    private Label lblAs400DtaLib;

    @FXML
    private Label lblAs400ExpLib;

    @FXML
    private Label lblUpsUid;

    @FXML
    private Label lblUpsPwd;

    @FXML
    private Label lblUpsLic;

    @FXML
    private Label lblUpsShUrl;

    @FXML
    private Label lblUpsVodUrl;

    @FXML
    private Label lblLblLoc;

    @FXML
    private Label lblCmpLogoLoc;

    @FXML
    private Label lblJaspLoc;
    
    @FXML
    private Label lblPdfRtn;
    
    @FXML
    private TextField txtFxKey;

    @FXML
    private TextField txtFxPwd;

    @FXML
    private TextField txtFxAcctNo;

    @FXML
    private TextField txtFxMeterNo;

    @FXML
    private TextField txtFxShUrl;
    
    @FXML
    private TextField txtFxLblLoc;

    private Stage curStage;
    private UpsShprParms upsParm;
    private PtktMonMainController ptktMonMainController;

    @FXML
    void btnAcceptClicked(ActionEvent event) {
         if (txtAs400Host.getStyleClass().contains("error")
                || txtAs400Uid.getStyleClass().contains("error")
                || txtAs400Pwd.getStyleClass().contains("error")
                || txtAs400DtaLib.getStyleClass().contains("error")
                || txtAs400ExpLib.getStyleClass().contains("error")
                || txtUpsUid.getStyleClass().contains("error")
                || txtUpsPwd.getStyleClass().contains("error")
                 || txtUpsLic.getStyleClass().contains("error")
                 || txtUpsShUrl.getStyleClass().contains("error")
                 || txtUpsVodUrl.getStyleClass().contains("error")
                 || txtLblLoc.getStyleClass().contains("error")
                 || txtCmpLogoLoc.getStyleClass().contains("error")
                 || txtJaspLoc.getStyleClass().contains("error")
                 || txtPdfRtn.getStyleClass().contains("error")
                 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please correct items in Red");
            alert.setTitle("Entered data contains errors");
            alert.showAndWait();
        } else {
            upsParm.setAs400SystemName(txtAs400Host.getText());
            upsParm.setAs400UserId(txtAs400Uid.getText());
            upsParm.setAs400Password(txtAs400Pwd.getText());
            upsParm.setAs400Lib(txtAs400DtaLib.getText());
            upsParm.setUpsExpLib(txtAs400ExpLib.getText());
            upsParm.setShUserId(txtUpsUid.getText());
            upsParm.setShPassWord(txtUpsPwd.getText());
            upsParm.setShLicense(txtUpsLic.getText());
            upsParm.setShUrl(txtUpsShUrl.getText());
            upsParm.setVoidUrl(txtUpsVodUrl.getText());
            upsParm.setUpsLblLoc(txtLblLoc.getText());
            upsParm.setCmpLogoLoc(txtCmpLogoLoc.getText());
            upsParm.setJasperLoc(txtJaspLoc.getText());
            upsParm.setPtktRtnDays(Integer.valueOf(txtPdfRtn.getText()));
            upsParm.setFxAcctNo(txtFxAcctNo.getText());
            upsParm.setFxKey(txtFxKey.getText());
            upsParm.setFxMeterNo(txtFxMeterNo.getText());
            upsParm.setFxPassWord(txtFxPwd.getText());
            upsParm.setFxUrl(txtFxShUrl.getText());
            upsParm.setFxLblLoc(txtFxLblLoc.getText());
            
            AppParms.writeParmFile(upsParm);
            this.ptktMonMainController.restartMon();
            Stage stage = (Stage) btnAccept.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    void btnCmpLogoLocClicked(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Company Logo Folder");

        File file = dirChooser.showDialog(curStage);
        if (file != null) {
            txtCmpLogoLoc.setText(file.getPath());
            edtCmpLogoLoc();
        }

    }

    @FXML
    void btnJaspLocClicked(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Jasper report script Folder");
        File file = dirChooser.showDialog(curStage);
        if (file != null) {
            txtJaspLoc.setText(file.getPath());
            edtJaspLoc();
        }

    }

    @FXML
    void btnLblLocClicked(ActionEvent event) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select UPS Label Location");
        File file = dirChooser.showDialog(curStage);
        if (file != null) {
            txtLblLoc.setText(file.getPath());
            edtJaspLoc();
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtAs400Host.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtAs400Host();
            }
        });
        txtAs400Uid.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtAs400Uid();
            }
        });
        txtAs400Pwd.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtAs400Pwd();
            }
        });
        txtAs400DtaLib.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtAs400DtaLib();
            }
        });
        txtAs400ExpLib.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtAs400ExpLib();
            }
        });
        txtUpsUid.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtUpsUid();
            }
        });
        txtUpsPwd.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtUpsPwd();
            }
        });
        txtUpsLic.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtUpsLic();
            }
        });
        txtUpsShUrl.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtUpsShUrl();
            }
        });
        txtUpsVodUrl.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtUpsVodUrl();
            }
        });
        txtLblLoc.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtLblLoc();
            }
        });
        txtCmpLogoLoc.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtCmpLogoLoc();
            }
        });
        txtJaspLoc.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtJaspLoc();
            }
        });
        
        // only except 3 digit numeric field
        txtPdfRtn.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        String s;
                if (!newValue.matches("\\d*")) {
                    s = newValue.replaceAll("[^\\d]", "");
                } else {
                    s = newValue;
                }
                if (s.trim().length() > 3) {
                    s = s.trim().substring(0, 3);
                } else {
                    s = s.trim();
                }
                txtPdfRtn.setText(s);
        });
        txtPdfRtn.focusedProperty().addListener((arg0, oldPropVal, newPropVal) -> {
            if (!newPropVal) {
                edtPdfRtnDays();
            }
        });
    }

    public void setIntParm(UpsShprParms upsParm, Stage stage, PtktMonMainController ptktMonMainController) {
        this.upsParm = upsParm;
        this.curStage = stage;
        txtAs400Host.setText(this.upsParm.getAs400SystemName());
        txtAs400Uid.setText(this.upsParm.getAs400UserId());
        txtAs400Pwd.setText(this.upsParm.getAs400UserId());
        txtAs400DtaLib.setText(this.upsParm.getAs400Lib());
        txtAs400ExpLib.setText(this.upsParm.getUpsExpLib());
        txtUpsUid.setText(this.upsParm.getShUserId());
        txtUpsPwd.setText(this.upsParm.getShPassWord());
        txtUpsLic.setText(this.upsParm.getShLicense());
        txtUpsShUrl.setText(this.upsParm.getShUrl());
        txtUpsVodUrl.setText(this.upsParm.getVoidUrl());
        txtFxAcctNo.setText(this.upsParm.getFxAcctNo());
        txtFxKey.setText(this.upsParm.getFxKey());
        txtFxMeterNo.setText(this.upsParm.getFxMeterNo());
        txtFxPwd.setText(this.upsParm.getFxPassWord());
        txtFxShUrl.setText(this.upsParm.getFxUrl());
        txtFxLblLoc.setText(this.upsParm.getFxLblLoc());
        txtLblLoc.setText(this.upsParm.getUpsLblLoc());
        txtCmpLogoLoc.setText(this.upsParm.getCmpLogoLoc());
        txtJaspLoc.setText(this.upsParm.getJasperLoc());
        txtPdfRtn.setText(String.valueOf(this.upsParm.getPtktRtnDays()));
        this.ptktMonMainController = ptktMonMainController;
        edtAs400Host();
        edtAs400Uid();
        edtAs400Pwd();
        edtAs400DtaLib();
        edtAs400ExpLib();
        edtUpsUid();
        edtUpsPwd();
        edtUpsLic();
        edtUpsShUrl();
        edtUpsVodUrl();
        edtLblLoc();
        edtCmpLogoLoc();
        edtJaspLoc();
        edtPdfRtnDays();
    }

    private void edtAs400Host() {

        ObservableList<String> olTxtClass = txtAs400Host.getStyleClass();
        ObservableList<String> olMsgClass = lblAs400Host.getStyleClass();
        if (txtAs400Host.getText().isEmpty()) {
            lblAs400Host.setText("AS400 Host May not Be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblAs400Host.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }
    }

    private void edtAs400Uid() {
        ObservableList<String> olTxtClass = txtAs400Uid.getStyleClass();
        ObservableList<String> olMsgClass = lblAs400Uid.getStyleClass();
        if (txtAs400Uid.getText().isEmpty()) {
            lblAs400Uid.setText("AS400 UserId May not Be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblAs400Uid.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }
    }

    private void edtAs400Pwd() {
        ObservableList<String> olTxtClass = txtAs400Pwd.getStyleClass();
        ObservableList<String> olMsgClass = lblAs400Pwd.getStyleClass();
        if (txtAs400Pwd.getText().isEmpty()) {
            lblAs400Pwd.setText("AS400 password can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblAs400Pwd.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }
    }

    private void edtAs400DtaLib() {
        ObservableList<String> olTxtClass = txtAs400DtaLib.getStyleClass();
        ObservableList<String> olMsgClass = lblAs400DtaLib.getStyleClass();
        if (txtAs400DtaLib.getText().isEmpty()) {
            lblAs400DtaLib.setText("AS400 Data Lib can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblAs400DtaLib.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }

    }

    private void edtAs400ExpLib() {
        ObservableList<String> olTxtClass = txtAs400ExpLib.getStyleClass();
        ObservableList<String> olMsgClass = lblAs400ExpLib.getStyleClass();
        if (txtAs400ExpLib.getText().isEmpty()) {
            lblAs400ExpLib.setText("AS400 Interface Lib can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblAs400ExpLib.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }

    }

    private void edtUpsUid() {

        ObservableList<String> olTxtClass = txtUpsUid.getStyleClass();
        ObservableList<String> olMsgClass = lblUpsUid.getStyleClass();
        if (txtUpsUid.getText().isEmpty()) {
            lblUpsUid.setText("UPS User ID can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblUpsUid.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }

    }

    private void edtUpsPwd() {

        ObservableList<String> olTxtClass = txtUpsPwd.getStyleClass();
        ObservableList<String> olMsgClass = lblUpsPwd.getStyleClass();
        if (txtUpsPwd.getText().isEmpty()) {
            lblUpsPwd.setText("UPS User Password can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblUpsPwd.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }

    }

    private void edtUpsLic() {

        ObservableList<String> olTxtClass = txtUpsLic.getStyleClass();
        ObservableList<String> olMsgClass = lblUpsLic.getStyleClass();
        if (txtUpsLic.getText().isEmpty()) {
            lblUpsLic.setText("UPS License can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblUpsLic.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }

    }

    private void edtUpsShUrl() {

        ObservableList<String> olTxtClass = txtUpsShUrl.getStyleClass();
        ObservableList<String> olMsgClass = lblUpsShUrl.getStyleClass();
        if (txtUpsShUrl.getText().isEmpty()) {
            lblUpsShUrl.setText("UPS Shipping URL can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblUpsShUrl.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }

    }

    private void edtUpsVodUrl() {

        ObservableList<String> olTxtClass = txtUpsVodUrl.getStyleClass();
        ObservableList<String> olMsgClass = lblUpsVodUrl.getStyleClass();
        if (txtUpsVodUrl.getText().isEmpty()) {
            lblUpsVodUrl.setText("UPS Void Trk URL can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            lblUpsVodUrl.setText("");
            olTxtClass.removeAll(Collections.singleton("error"));
            olMsgClass.removeAll(Collections.singleton("error"));
        }

    }

    private void edtLblLoc() {

        ObservableList<String> olTxtClass = txtLblLoc.getStyleClass();
        ObservableList<String> olMsgClass = lblLblLoc.getStyleClass();
        if (txtLblLoc.getText().isEmpty()) {
            lblLblLoc.setText("UPS Label Location can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            File tstFile = new File(txtLblLoc.getText());
            if (tstFile.isDirectory()) {
                olTxtClass.removeAll(Collections.singleton("error"));
                olMsgClass.removeAll(Collections.singleton("error"));
                lblLblLoc.setText("");
            } else {
                lblLblLoc.setText("Invalid Folder");
                olTxtClass.add("error");
                olMsgClass.add("error");
            }
        }
    }

    private void edtCmpLogoLoc() {

        ObservableList<String> olTxtClass = txtCmpLogoLoc.getStyleClass();
        ObservableList<String> olMsgClass = lblCmpLogoLoc.getStyleClass();
        if (txtCmpLogoLoc.getText().isEmpty()) {
            lblCmpLogoLoc.setText("Company Logo Location can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            File tstFile = new File(txtCmpLogoLoc.getText());
            if (tstFile.isDirectory()) {
                olTxtClass.removeAll(Collections.singleton("error"));
                olMsgClass.removeAll(Collections.singleton("error"));
                lblCmpLogoLoc.setText("");
            } else {
                lblCmpLogoLoc.setText("Invalid Folder");
                olTxtClass.add("error");
                olMsgClass.add("error");
            }
        }
    }

    private void edtJaspLoc() {

        ObservableList<String> olTxtClass = txtJaspLoc.getStyleClass();
        ObservableList<String> olMsgClass = lblJaspLoc.getStyleClass();
        if (txtJaspLoc.getText().isEmpty()) {
            lblJaspLoc.setText("Company Logo Location can not be blank");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            File tstFile = new File(txtJaspLoc.getText());
            if (tstFile.isDirectory()) {
                olTxtClass.removeAll(Collections.singleton("error"));
                olMsgClass.removeAll(Collections.singleton("error"));
                lblJaspLoc.setText("");
            } else {
                lblJaspLoc.setText("Invalid Folder");
                olTxtClass.add("error");
                olMsgClass.add("error");
            }
        }
    }
    public void edtPdfRtnDays() {
        
        ObservableList<String> olTxtClass = txtPdfRtn.getStyleClass();
        ObservableList<String> olMsgClass = lblPdfRtn.getStyleClass();
        if (txtPdfRtn.getText().isEmpty()) {
            lblPdfRtn.setText("Pick Ticket (PDF) retention in days must > 0");
            olTxtClass.add("error");
            olMsgClass.add("error");
        } else {
            int rtnDays = Integer.valueOf(txtPdfRtn.getText());
            if (rtnDays > 0) {
                olTxtClass.removeAll(Collections.singleton("error"));
                olMsgClass.removeAll(Collections.singleton("error"));
                lblPdfRtn.setText("");
            } else {
                lblPdfRtn.setText("Pick Ticket (PDF) retention in days must > 0");
                olTxtClass.add("error");
                olMsgClass.add("error");
            }
        }
        
    }

}
