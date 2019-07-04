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
    private Label recordCustomerIdLabel;
    @FXML
    private Label recordCustomerNameLabel;
    @FXML
    private Label recordDoctorIdLabel;
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
        recordCustomerIdLabel.setText(getRecord().getCustomerId());
        recordCustomerNameLabel.setText(getRecord().getCustomerName());
        recordDoctorIdLabel.setText(getRecord().getDoctorId());
        recordDatePicker.setValue(StringToDate(getRecord().getDate()));
        recordContext.setText(getRecord().getContext());
        recordDatePicker.setEditable(false);
        recordDatePicker.setDisable(true);
        recordContext.setEditable(false);
    }

    /**
     * 返回业务管理界面
     */
    public void backToBusinessAdmin() {
        record = new Record();
        getApp().floatStage.close();
    }
}
