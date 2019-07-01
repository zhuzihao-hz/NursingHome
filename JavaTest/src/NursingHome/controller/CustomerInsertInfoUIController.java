package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Customer;
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
        ControllerUtils.initCustomerComboBox(customerAgeComboBox, customerRankComboBox, customerWorkerRankComboBox, customerRoomRankComboBox);
    }

    /**
     * 生成新的客户的编号
     *
     * @return 新的客户编号
     */
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

    /**
     * 为新增的客户分配护工和房间
     *
     * @param customer   客户对象
     * @param rank       客户要求的护理等级
     * @param workerRank 客户要求的护工等级
     * @param roomRank   客户要求的房间等级
     * @return 分配完成的客户对象
     */
    public Customer autoAllocate(Customer customer, int rank, String workerRank, String roomRank) {
        // TODO 自动分配
        // TODO 获得房间号
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql = "SELECT room_id FROM NursingHome.room WHERE room_usedbed<room_totalbed AND room_rank='" + roomRank + "' ORDER BY room_id ASC;";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                customer.setRoomID(rs.getString(1));
            }
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // TODO 获得床位号 并且修改床位状态和房间状态
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql = "SELECT bed_id FROM NursingHome.bed WHERE bed_roomid='" + customer.getRoomID() + "' AND bed_status=0; ";
            String sql1 = "UPDATE NursingHome.bed SET bed_staus=1 WHERE bed_id='" + customer.getBedID() + "' AND bed_roomid='" + customer.getRoomID() + "';";
            String sql2 = "UPDATE NursingHome.room SET room_usedbed=room_usedbed+1 WHERE room_id='" + customer.getRoomID() + "';";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            if (rs.next()) {
                customer.setBedID(rs.getString(1));
            }
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // TODO 获得护工号
        double room_idDouble = Double.valueOf(customer.getRoomID().substring(1));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql = "SELECT worker_id FROM worker WHERE worker_rank='" + workerRank + "' AND worker_customerrank=" + rank + " ORDER BY abs(" + room_idDouble + "-worker_vispos) ASC, worker_customernumber ASC";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                customer.setCareWorker(rs.getString(1));
            }
            String sql1 = "UPDATE worker SET worker_customernumber=worker_customernumber+1 WHERE worker_id='" + customer.getCareWorker() + "'";
            String sql2 = "UPDATE worker SET worker_vispos=(worker_vispos*(worker_customernember-1)+" + room_idDouble + ")/worker_customernumber WHERE worker_id='" + customer.getCareWorker() + "'";
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    /**
     * 保存插入的客户信息
     */
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

        customer = autoAllocate(customer, rank, workerRank, roomRank);

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

    /**
     * 返回业务管理界面
     */
    public void backToBusinessMenu() {
        getApp().floatStage.close();
    }
}
