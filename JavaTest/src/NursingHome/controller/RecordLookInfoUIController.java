package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.Record;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.StringToDate;

public class RecordLookInfoUIController implements Initializable {
    private Main application;
    @FXML
    private Label recordIdLabel;
    @FXML
    private TextField recordCustomerIdTextField;
    @FXML
    private TextField recordCustomerNameTextField;
    @FXML
    private TextField recordDoctorIdTextField;
    @FXML
    private JFXDatePicker recordDatePicker;
    @FXML
    private JFXTextArea recordContext;
    private static Record record;

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    public static Record getRecord() {
        return record;
    }

    public static void setRecord(Record record) {
        RecordLookInfoUIController.record = record;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recordIdLabel.setText(getRecord().getId());
        recordCustomerIdTextField.setText(getRecord().getCustomerId());
        recordCustomerNameTextField.setText(getRecord().getCustomerName());
        recordDoctorIdTextField.setText(getRecord().getDoctorId());
        recordDatePicker.setValue(StringToDate(getRecord().getDate()));
        recordContext.setText(getRecord().getContext());
        recordCustomerIdTextField.setEditable(false);
        recordCustomerNameTextField.setEditable(false);
        recordDoctorIdTextField.setEditable(false);
        recordDatePicker.setEditable(false);
        recordDatePicker.setDisable(true);
        recordContext.setEditable(false);
    }

    public void backToBusinessAdmin() {
        record = new Record();
        getApp().floatStage.close();
    }
}
