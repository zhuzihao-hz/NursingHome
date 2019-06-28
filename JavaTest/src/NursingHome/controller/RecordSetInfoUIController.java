package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.Record;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.StringToDate;

public class RecordSetInfoUIController implements Initializable {
    private Main application;
    @FXML private TextField recordIdTextField;
    @FXML private TextField recordCustomerIdTextField;
    @FXML private TextField recordCustomerNameTextField;
    @FXML private TextField recordDoctorIdTextField;
    @FXML private JFXDatePicker recordDatePicker;
    @FXML private JFXTextArea recordContext;
    public static boolean isInsert;
    private static Record record;

    public void setApp(Main app) { this.application = app; }
    public Main getApp() {return this.application; }

    public static Record getRecord() { return record; }
    public static void setRecord(Record record) { RecordSetInfoUIController.record = record; }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        if (isInsert){
            recordIdTextField.setText("R"+ getRecordNumber());
            recordCustomerIdTextField.setText("");
            recordCustomerNameTextField.setText("");
            recordDoctorIdTextField.setText("");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String string=sdf.format(date);
            recordDatePicker.setValue(StringToDate(string));
            recordContext.setText("");
        }else{
            // TODO 貌似不用修改？？？？？？？
            recordIdTextField.setText(record.getId());
            recordCustomerIdTextField.setText(record.getCustomerId());
            recordCustomerNameTextField.setText(record.getCustomerName());
            recordDoctorIdTextField.setText(record.getDoctorId());
            recordDatePicker.setValue(StringToDate(record.getDate()));
            recordContext.setText(record.getContext());
            recordIdTextField.setEditable(false);
            recordCustomerIdTextField.setEditable(false);
            recordCustomerNameTextField.setEditable(false);

        }
    }

    public int getRecordNumber(){
        int N=0;
        // TODO 获取记录条数加1
        return N;
    }
    public void saveRecordInfo(){
        if (isInsert){
            // TODO 新增记录

        }else{
            // TODO 修改记录
        }

        backToBusinessAdmin();
    }
    public void backToBusinessAdmin(){
        record=new Record();
        getApp().floatStage.close();
    }
}
