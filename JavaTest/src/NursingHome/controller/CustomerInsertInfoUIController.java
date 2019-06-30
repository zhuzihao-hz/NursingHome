package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;

public class CustomerInsertInfoUIController implements Initializable {
    private Main application;
    @FXML
    private Label customerIdLabel;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField customerRelationNameTextField;
    @FXML
    private TextField customerRelationTextField;
    @FXML
    private TextField customerRelationPhoneTextField;
    @FXML
    private TextField customerPhoneTextField;
    @FXML
    private ComboBox<Integer> customerAgeComboBox;
    @FXML
    private ComboBox<Integer> customerRankComboBox;
    @FXML
    private ComboBox<String> customerWorkerRankComboBox;
    @FXML
    private ComboBox<String> customerRoomRankComboBox;

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerIdLabel.setText(generateCustomerId());
        customerAgeComboBox.setEditable(false);
        customerRankComboBox.setEditable(false);
        customerRoomRankComboBox.setEditable(false);
        customerWorkerRankComboBox.setEditable(false);
        ControllerUtils.initCustomerComboBox(customerAgeComboBox, customerRankComboBox);

        customerWorkerRankComboBox.getItems().add("高级护工");
        customerWorkerRankComboBox.getItems().add("中级护工");
        customerWorkerRankComboBox.getItems().add("低级护工");
        customerWorkerRankComboBox.getItems().add("实习护工");
        customerWorkerRankComboBox.setValue("高级护工");

        customerRoomRankComboBox.getItems().add("1");
        customerRoomRankComboBox.getItems().add("2");
        customerRoomRankComboBox.getItems().add("3");
        customerRoomRankComboBox.setValue("3");
    }

    public String generateCustomerId() {
        String customerId = "";
        // TODO 自动获得客户Id,从Customer表中
        Connection conn;
        Statement stmt;
        int N = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql = "SELECT count(*) FROM NursingHome.historical_customer;";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                N = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerId = "C" + (N + 1);
        return customerId;
    }

    public Customer autoAllocate(Customer customer, int rank, String workerRank, String roomRank) {
        // TODO 自动分配


        return customer;
    }

    public void insertCustomerInfo() {
        Customer customer = new Customer();
        customer.setId(customerIdLabel.getText());
        customer.setName(customerNameTextField.getText());
        customer.setPhone(customerPhoneTextField.getText());
        customer.setRelation(customerRelationTextField.getText());
        customer.setRelationPhone(customerRelationPhoneTextField.getText());
        customer.setRelationName(customerRelationNameTextField.getText());
        customer.setAge(customerAgeComboBox.getValue());
        customer.setRank(customerRankComboBox.getValue());

        int rank = customerRankComboBox.getValue();
        String workerRank = customerWorkerRankComboBox.getValue();
        String roomRank = customerRoomRankComboBox.getValue();

        /**
         * 这里自动分配
         */
        autoAllocate(customer, rank, workerRank, roomRank);


        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql1 = "INSERT INTO NursingHome.historical_customer VALUES ('" + customer.getId() + "','" + customer.getName() + "',1);";
            String sql = "INSERT INTO NursingHome.customer VALUES " + customer.getCustomerInfo();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BusinessAdminUIController.setInfoTableView(false, true, customer);
        getApp().floatStage.close();
    }

    public void backToBusinessMenu(ActionEvent actionEvent) {
        getApp().floatStage.close();
    }
}
