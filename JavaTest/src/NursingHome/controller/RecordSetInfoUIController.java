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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;

public class RecordSetInfoUIController implements Initializable {
    private Main application;
    @FXML private Label recordIdLabel;
    @FXML private TextField recordCustomerIdTextField;
    @FXML private TextField recordCustomerNameTextField;
    @FXML private TextField recordDoctorIdTextField;
    @FXML private JFXDatePicker recordDatePicker;
    @FXML private JFXTextArea recordContext;
    private static Record record;

    public void setApp(Main app) { this.application = app; }
    public Main getApp() {return this.application; }

    public static Record getRecord() { return record; }
    public static void setRecord(Record record) { RecordSetInfoUIController.record = record; }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        recordIdLabel.setText("R"+ getRecordNumber());
        recordCustomerIdTextField.setText("");
        recordCustomerNameTextField.setText("");
        recordDoctorIdTextField.setText("");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String string=sdf.format(date);
        recordDatePicker.setValue(StringToDate(string));
        recordContext.setText("");
    }

    public int getRecordNumber(){
        int N=0;
        // TODO 获取记录条数加1
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql="SELECT count(*) FROM NursingHome.record;";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                N=rs.getInt(1);
            }
            //System.out.println(rs.getInt(0));
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(N);
        return N+1;
    }

    public void saveRecordInfo(){
        // TODO 新增记录
        Record record=new Record();
        //System.out.println(recordIdLabel.getText());
        record.setId(recordIdLabel.getText());
        //System.out.println(record.getId());
        record.setCustomerId(recordCustomerIdTextField.getText());
        record.setCustomerName(recordCustomerNameTextField.getText());
        record.setDoctorId(recordDoctorIdTextField.getText());
        record.setDate(localDateToString(recordDatePicker.getValue()));
        record.setContext(recordContext.getText());

        // TODO 在数据库中新增信息
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql="INSERT INTO NursingHome.record VALUES "+record.getRecordInfo();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BusinessAdminUIController.setInfoTableView(true,true,record);
        backToBusinessAdmin();
    }

    public void backToBusinessAdmin(){
        record=new Record();
        getApp().floatStage.close();
    }
}
