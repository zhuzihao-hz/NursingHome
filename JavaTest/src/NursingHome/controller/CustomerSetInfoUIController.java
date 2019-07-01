package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Customer;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;

public class CustomerSetInfoUIController implements Initializable {
    private Main application;
    private static Customer customer;
    @FXML
    private TextField customerIdTextField;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField customerRoomIdTextField;
    @FXML
    private TextField customerBedIdTextField;
    @FXML
    private TextField customerPhoneTextField;
    @FXML
    private TextField customerCareWorkerIdTextField;
    @FXML
    private TextField customerRelationNameTextField;
    @FXML
    private TextField customerRelationTextField;
    @FXML
    private TextField customerRelationPhoneTextField;
    @FXML
    private ComboBox<Integer> customerAgeComboBox;
    @FXML
    private ComboBox<Integer> customerCareTypeComboBox;
    @FXML
    private JFXDatePicker customerEnterTimeDatePicker;
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

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        CustomerSetInfoUIController.customer = customer;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ControllerUtils.initCustomerComboBox(customerAgeComboBox, customerCareTypeComboBox, customerWorkerRankComboBox, customerRoomRankComboBox);
        customerIdTextField.setText(customer.getId());
        customerIdTextField.setEditable(false);
        customerAgeComboBox.setValue(customer.getAge());
        customerNameTextField.setText(customer.getName());
        customerCareTypeComboBox.setValue(customer.getRank());
        customerRoomIdTextField.setText(String.valueOf(customer.getRoomID()));
        customerBedIdTextField.setText(String.valueOf(customer.getBedID()));
        customerPhoneTextField.setText(customer.getPhone());
        customerCareWorkerIdTextField.setText(customer.getCareWorker());
        customerRelationNameTextField.setText(customer.getRelationName());
        customerRelationTextField.setText(customer.getRelation());
        customerRelationPhoneTextField.setText(customer.getRelationPhone());
        LocalDate localDate = StringToDate(customer.getEnterTime());
        customerEnterTimeDatePicker.setValue(localDate);
        customerEnterTimeDatePicker.setDisable(true);
        customerCareWorkerIdTextField.setEditable(false);
        customerRoomIdTextField.setEditable(false);
        customerBedIdTextField.setEditable(false);
    }

    /**
     * 为修改信息的客户再分配护工和房间
     *
     * @param customer   客户对象
     * @param rank       客户的护理等级
     * @param workerRank 客户原来的护工的等级
     * @param roomRank   客户原来房间的等级
     * @return 再分配完毕后的客户对象
     */
    public Customer autoAllocate(Customer customer, int rank, String workerRank, String roomRank) {
        // TODO 自动再分配
        //  参数为原来的护理等级(新)、护工等级（旧）、房间等级（旧）

        // TODO 检查是否需要换房间
        double roomOldDouble = 0;
        if (!roomRank.equals(customerRoomRankComboBox.getValue())) {
            // TODO 需要换房间

            // TODO 修改原来房间和床位的信息
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                String sql = "UPDATE NursingHome.bed SET bed_status=0 WHERE bed_id='" + customer.getBedID() + "' AND bed_roomid='" + customer.getRoomID() + "';";
                String sql1 = "UPDATE Nursinghome.room SET room_usedbed=room_usedbed-1 WHERE room_id='" + customer.getRoomID() + "';";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.executeUpdate(sql1);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            String roomIdNew = "";
            // TODO 重新分配新的房间和床位
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                String sql = "SELECT room_id FROM NursingHome.room WHERE room_usedbed<room_totalbed AND room_rank='" + customerRoomRankComboBox.getValue() + "' ORDER BY room_id ASC;";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    roomIdNew = rs.getString(1);
                }
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // TODO 修改护工的位置
            roomOldDouble = Double.parseDouble(customer.getRoomID().substring(1));
            double roomNewDouble = Double.parseDouble(roomIdNew.substring(1));
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                String sql = "UPDATE NursingHome.worker SET worker_vispos=(worker_vispos*worker_customernumber-" + roomOldDouble + "+" + roomNewDouble + ")/worker_customernumber WHERE worker_id='" + customer.getCareWorker() + "'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            customer.setRoomID(roomIdNew);

        }

        // TODO 检查是否需要更改护工
        if (!workerRank.equals(customerWorkerRankComboBox.getValue())) {
            // TODO 更改原来护工位置
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                String sql = "UPDATE NursingHome.worker SET worker_vispos=(worker_vispos*worker_customernumber-" + roomOldDouble + ")/(worker_customernumber-1) WHERE worker_id='" + customer.getCareWorker() + "'";
                String sql1 = "UPDATE NursingHome.worker SET worker_customernumber=worker_customernumber-1 WHERE worker_id='" + customer.getCareWorker() + "';";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.executeUpdate(sql1);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // TODO 获得新的护工，更改新的护工的位置
            double room_idDouble = Double.valueOf(customer.getRoomID().substring(1));
            String workerIdNew = "";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                String sql = "SELECT worker_id FROM worker WHERE worker_rank='" + customerWorkerRankComboBox.getValue() + "' AND worker_customerrank=" + rank + " ORDER BY abs(" + room_idDouble + "-worker_vispos) ASC, worker_customernumber ASC";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    workerIdNew = rs.getString(1);
                }
                String sql1 = "UPDATE NursingHome.worker SET worker_customernumber=worker_customernumber+1 WHERE worker_id='" + workerIdNew + "'";
                String sql2 = "UPDATE NursingHome.worker SET worker_vispos=(worker_vispos*(worker_customernember-1)+" + room_idDouble + ")/worker_customernumber WHERE worker_id='" + workerIdNew + "'";
                String sql3 = "UPDATE NursingHome.customer SET customer_careworker='" + workerIdNew + "' WHERE customer_id='" + customer.getId() + "'";
                stmt.executeUpdate(sql1);
                stmt.executeUpdate(sql2);
                stmt.executeUpdate(sql3);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            customer.setCareWorker(workerIdNew);
        }

        return customer;
    }

    /**
     * 保存修改的员工信息
     */
    public void saveCustomerInfo() {
        // TODO 保存修改的客户信息
        customer.setId(customerIdTextField.getText());
        customer.setName(customerNameTextField.getText());
        customer.setAge(customerAgeComboBox.getValue());
        customer.setRoomID(customerRoomIdTextField.getText());
        customer.setBedID(customerBedIdTextField.getText());
        try {
            customer.setPhone(customerPhoneTextField.getText());
            customer.setRelationPhone(customerRelationPhoneTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("[错误]电话格式错误");
        }
        customer.setCareWorker(customerCareWorkerIdTextField.getText());
        customer.setRelation(customerRelationTextField.getText());
        customer.setRelationName(customerRelationNameTextField.getText());
        customer.setEnterTime(ControllerUtils.localDateToString(customerEnterTimeDatePicker.getValue()));
        customer.setRank(customerCareTypeComboBox.getValue());

        // TODO 如果客户改变护理等级、护工等级、房间等级，需要重新分配

        // TODO 先查询该客户的房间等级、护工等级、护理等级（已知）
        String workerId = customer.getCareWorker();  //当前护工编号
        String workerRankOld = "";
        String roomRankOld = "";
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql = "SELECT worker_rank FROM NursingHome.worker WHERE worker_id='" + workerId + "';";
            String sql1 = "SELECT room_rank FROM NursingHome.room WHERE room_id='" + customer.getRoomID() + "';";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                workerRankOld = rs.getString(1);
            }
            ResultSet rs1 = stmt.executeQuery(sql1);
            if (rs1.next()) {
                roomRankOld = rs1.getString(1);
            }
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // TODO 将该客户重新分配一个护工
        autoAllocate(customer, customer.getRank(), workerRankOld, roomRankOld);

        // TODO 保存修改的信息
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql = "UPDATE NursingHome.customer SET customer_name='" + customer.getName() + "', customer_age='" + customer.getAge() + "', customer_entertime='" + customer.getEnterTime() + "', customer_roomid='" + customer.getRoomID() + "', customer_bedid='" + customer.getBedID() + "', customer_phone='" + customer.getPhone() + "', customer_careworker='" + customer.getCareWorker() + "', customer_rank='" + customer.getRank() + "', customer_relationname='" + customer.getRelationName() + "', customer_relation='" + customer.getRelation() + "', customer_relationphone='" + customer.getRelationPhone() + "' WHERE customer_id='" + customer.getId() + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BusinessAdminUIController.setInfoTableView(false, false, customer);
        getApp().floatStage.close();
    }

    /**
     * 返回人事管理界面
     */
    public void backToPeopleAdmin() {
        getApp().floatStage.close();
    }
}
