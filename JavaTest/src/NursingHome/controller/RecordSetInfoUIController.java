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
import static NursingHome.SQLMethod.*;

public class RecordSetInfoUIController implements Initializable {
    private Main application;
    @FXML
    private Label recordIdLabel;
    @FXML
    private TextField recordCustomerIdTextField;
    @FXML
    private TextField recordDoctorIdTextField;
    @FXML
    private JFXDatePicker recordDatePicker;
    @FXML
    private JFXTextArea recordContext;

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recordIdLabel.setText(generateId('R'));
        recordCustomerIdTextField.setText("");
        recordDoctorIdTextField.setText("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String string = sdf.format(new Date());
        recordDatePicker.setValue(StringToDate(string));
        recordContext.setText("");
    }

    /**
     * 获得指定客户号的客户姓名
     *
     * @param id 客户编号
     * @return 返回客户姓名，若无此客户则返回空字符串
     */
    private String getCustomerName(String id) {
        String name = "";
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql = "SELECT customer_name FROM NursingHome.customer WHERE customer_id = '" + id + "'";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.first()) {
                name = rs.getString(1);
            } else {
                showAlert("[警告]没有这个客户！");
            }
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 保存新记录的信息
     */
    public void saveRecordInfo() {
        // TODO 新增记录
        Record record = new Record();
        record.setId(recordIdLabel.getText());
        record.setCustomerId(recordCustomerIdTextField.getText());
        record.setDoctorId(recordDoctorIdTextField.getText());
        record.setDate(localDateToString(recordDatePicker.getValue()));
        record.setContext(recordContext.getText());

        String name = getCustomerName(record.getCustomerId());
        if (name.equals("")) {
            recordCustomerIdTextField.setText("");
        } else {
            record.setCustomerName(name);
            // TODO 在数据库中新增信息
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                String sql = "INSERT INTO NursingHome.record VALUES " + record.getRecordInfo();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            BusinessAdminUIController.setInfoTableView(true, true, record);
            backToBusinessAdmin();
        }
    }

    /**
     * 返回业务管理界面
     */
    public void backToBusinessAdmin() {
        getApp().floatStage.close();
    }
}
