package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Customer;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.StringToDate;
import static NursingHome.ControllerUtils.showAlert;

public class CustomerSetInfoUIController implements Initializable {
    public static boolean isInsert=true;
    private Main application;
    private static Customer customer;
    @FXML private TextField customerIdTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField customerRoomIdTextField;
    @FXML private TextField customerBedIdTextField;
    @FXML private TextField customerPhoneTextField;
    @FXML private TextField customerCareWorkerIdTextField;
    @FXML private TextField customerRelationNameTextField;
    @FXML private TextField customerRelationTextField;
    @FXML private TextField customerRelationPhoneTextField;
    @FXML private ComboBox<Integer> customerAgeComboBox;
    @FXML private ComboBox<Integer> customerCareTypeComboBox;
    @FXML private JFXDatePicker customerEnterTimeDatePicker;

    public void setApp(Main app) { this.application = app; }
    public Main getApp() {return this.application; }

    public static Customer getCustomer() { return customer; }
    public static void setCustomer(Customer customer) { CustomerSetInfoUIController.customer = customer; }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ControllerUtils.initCustomerComboBox(customerAgeComboBox,customerCareTypeComboBox);
        if (!isInsert){
            customerIdTextField.setText(customer.getId());
            customerIdTextField.setEditable(false);
            customerAgeComboBox.setValue(customer.getAge());
            customerNameTextField.setText(customer.getName());
            customerCareTypeComboBox.setValue(customer.getCareType());
            customerRoomIdTextField.setText(String.valueOf(customer.getRoomID()));
            customerBedIdTextField.setText(String.valueOf(customer.getBedID()));
            customerPhoneTextField.setText(customer.getPhone());
            customerCareWorkerIdTextField.setText(customer.getCareWorker());
            customerRelationNameTextField.setText(customer.getRelationName());
            customerRelationTextField.setText(customer.getRelation());
            customerRelationPhoneTextField.setText(customer.getRelationPhone());
            LocalDate localDate=StringToDate(customer.getEnterTime());
            customerEnterTimeDatePicker.setValue(localDate);
        }
    }

    public void saveCustomerInfo() {
        // TODO 保存客户信息
        customer.setId(customerIdTextField.getText());
        customer.setName(customerNameTextField.getText());
        customer.setAge(customerAgeComboBox.getValue());
        customer.setRoomID(Integer.parseInt(customerRoomIdTextField.getText()));
        customer.setBedID(Integer.parseInt(customerBedIdTextField.getText()));
        try {
            customer.setPhone(customerPhoneTextField.getText());
            customer.setRelationPhone(customerRelationPhoneTextField.getText());
        }catch (NumberFormatException e){
            showAlert("[错误]电话格式错误");
        }
        customer.setCareWorker(customerCareWorkerIdTextField.getText());
        customer.setCareType(customerCareTypeComboBox.getValue());
        customer.setRelation(customerRelationTextField.getText());
        customer.setRelationName(customerRelationNameTextField.getText());
        customer.setEnterTime(ControllerUtils.localDateToString(customerEnterTimeDatePicker.getValue()));

        if(isInsert){
            // TODO 在数据库中新增信息
            if (customerIdTextField.getText().equals("")||customerBedIdTextField.getText().equals("")||customerCareWorkerIdTextField.getText().equals("")||customerRoomIdTextField.getText().equals("")){
                // TODO 当id,bedId,roomId,careWorker有没填的时，自动填充
                showAlert("[警告]有未填充的信息，将进行自动安排");
                fillCustomerInfo();
            }else{
                // TODO 否则新增信息
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql="INSERT INTO NursingHome.customer VALUES "+customer.getCustomerInfo();
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else{
            // TODO 保存修改的信息
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                String sql="UPDATE NursingHome.customer SET customer_name='"+customer.getName()+"', customer_age='"+customer.getAge()+"', customer_entertime='"+customer.getEnterTime()+"', customer_roomid='"+customer.getRoomID()+"', customer_bedid='"+customer.getBedID()+"', customer_phone='"+customer.getPhone()+"', customer_careworker='"+customer.getCareWorker()+"', customer_caretype='"+customer.getCareType()+"', customer_relationname='"+customer.getRelationName()+"', customer_relation='"+customer.getRelation()+"', customer_relationphone='"+customer.getRelationPhone()+"' WHERE customer_id='"+customer.getId()+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        BusinessAdminUIController.setInfoTableView(false,isInsert,customer);
        getApp().floatStage.close();
    }

    public void fillCustomerInfo(){
        // TODO 自动安排
        //  customer
        /**
        * 这里进行自动安排！！！！！！！！！！！！！
        * */

        if (isInsert) {
            // TODO 新增自动安排的信息
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                String sql="INSERT INTO NursingHome.customer VALUES "+customer.getCustomerInfo();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            // TODO 保存修改的信息
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                String sql="UPDATE NursingHome.customer SET customer_name='"+customer.getName()+"', customer_age='"+customer.getAge()+"', customer_entertime='"+customer.getEnterTime()+"', customer_roomid='"+customer.getRoomID()+"', customer_bedid='"+customer.getBedID()+"', customer_phone='"+customer.getPhone()+"', customer_careworker='"+customer.getCareWorker()+"', customer_caretype='"+customer.getCareType()+"', customer_relationname='"+customer.getRelationName()+"', customer_relation='"+customer.getRelation()+"', customer_relationphone='"+customer.getRelationPhone()+"' WHERE customer_id='"+customer.getId()+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        BusinessAdminUIController.setInfoTableView(false,isInsert,customer);
        getApp().floatStage.close();
    }

    public void backToPeopleAdmin(ActionEvent actionEvent) {
        getApp().floatStage.close();
    }
}
