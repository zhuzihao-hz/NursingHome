package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Customer;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;
import static NursingHome.SQLMethod.*;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class CustomerSetInfoUIController implements Initializable {
    private Main application;
    private static Customer customer;
    private boolean available = false;
    @FXML
    private Label customerIdLabel;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private Label customerRoomIdLabel;
    @FXML
    private Label customerBedIdLabel;
    @FXML
    private TextField customerPhoneTextField;
    @FXML
    private Label customerCareWorkerLabel;
    @FXML
    private TextField customerRelationNameTextField;
    @FXML
    private TextField customerRelationTextField;
    @FXML
    private TextField customerRelationPhoneTextField;
    @FXML
    private JFXDatePicker customerBirthDatePicker;
    @FXML
    private ComboBox<Integer> customerCareTypeComboBox;
    @FXML
    private JFXDatePicker customerEnterTimeDatePicker;
    @FXML
    private ComboBox<String> customerWorkerRankComboBox;
    @FXML
    private ComboBox<String> customerRoomRankComboBox;
    private String workerRankOld = "";  //原来护工等级
    private String roomRankOld = "";    //原来房间等级

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
        workerRankOld = "";
        roomRankOld = "";
        ControllerUtils.initCustomerComboBox(customerCareTypeComboBox, customerWorkerRankComboBox, customerRoomRankComboBox);
        // TODO 读取房间等级
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql = "SELECT room_rank FROM NursingHome.room WHERE room_id = '" + customer.getRoomID() + "'";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.first()) {
                    roomRankOld = rs.getString(1);
                    customerRoomRankComboBox.setValue(roomRankOld);
                }
                String sql2 = "SELECT worker_rank FROM NursingHome.worker WHERE worker_id = '" + customer.getCareWorker() + "'";
                rs = stmt.executeQuery(sql2);
                if (rs.next()) {
                    workerRankOld = rs.getString(1);
                    customerWorkerRankComboBox.setValue(workerRankOld);
                }
                stmt.close();
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerBirthDatePicker.setValue(StringToDate(customer.getDate()));
        customerIdLabel.setText(customer.getId());
        customerNameTextField.setText(customer.getName());
        customerCareTypeComboBox.setValue(customer.getRank());
        customerRoomIdLabel.setText(String.valueOf(customer.getRoomID()));
        customerBedIdLabel.setText(String.valueOf(customer.getBedID()));
        customerPhoneTextField.setText(customer.getPhone());
        customerCareWorkerLabel.setText(customer.getCareWorker());
        customerRelationNameTextField.setText(customer.getRelationName());
        customerRelationTextField.setText(customer.getRelation());
        customerRelationPhoneTextField.setText(customer.getRelationPhone());
        LocalDate localDate = StringToDate(customer.getEnterTime());
        customerEnterTimeDatePicker.setValue(localDate);
        // TODO 客户入院时间不能修改，房间号床号护工号不能直接修改
        customerEnterTimeDatePicker.setDisable(true);
    }

    /**
     * 为修改信息的客户再分配护工和房间
     *
     * @param customer 客户对象
     * @param rank     客户的原来的护理等级
     * @return 再分配完毕后的客户对象
     */
    public Customer autoAllocate(Customer customer, int rank) {
        // TODO 自动再分配
        //  参数为原来的护理等级(新)

        // TODO 检查是否需要换房间
        String roomIdOld = customer.getRoomID(); //原来的房间号
        if (!roomRankOld.equals(customerRoomRankComboBox.getValue())) {
            // TODO 需要换房间

            Connection conn;
            Statement stmt;
            String roomIdNew = ""; //新房间编号
            // TODO 重新分配新的房间和床位，先检查是否有满足需要的房间，没有的话退出，有的话记录房间
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                    String sql = "SELECT room_id FROM NursingHome.room WHERE room_usedbed<room_totalbed AND room_rank='" + customerRoomRankComboBox.getValue() + "' ORDER BY room_id ASC;";
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.first()) {
                        roomIdNew = rs.getString(1);
                    } else {
                        // TODO 如果没有满足需求的房间，则返回，并且不能修改掉
                        showAlert("[警告]没有满足需求的房间！");
                        available = false;
                        rs.close();
                        stmt.close();
                        conn.close();
                        return customer;
                    }
                    stmt.close();
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                }
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // TODO 此时有满足需求的房间，修改原来房间和床位的信息，并且修改新的房间的信息
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                    String sql = "UPDATE NursingHome.bed SET bed_status=0 WHERE bed_id='" + customer.getBedID() + "' AND bed_roomid='" + customer.getRoomID() + "';";
                    String sql1 = "UPDATE Nursinghome.room SET room_usedbed=room_usedbed-1 WHERE room_id='" + customer.getRoomID() + "';";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    stmt.executeUpdate(sql1);
                    stmt.close();
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                }
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String bedIdNew = ""; //新床位编号
            // TODO 搜索新的房间床位并且修改新的房间的信息
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                    String sql = "SELECT bed_id FROM NursingHome.bed WHERE bed_roomid = '" + roomIdNew + "' AND bed_status = 0;";
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.first()) {
                        bedIdNew = rs.getString(1);
                    }
                    String sql1 = "UPDATE NursingHome.bed SET bed_status=1 WHERE bed_id='" + bedIdNew + "' AND bed_roomid='" + roomIdNew + "';";
                    String sql2 = "UPDATE Nursinghome.room SET room_usedbed=room_usedbed+1 WHERE room_id='" + roomIdNew + "';";
                    stmt.executeUpdate(sql1);
                    stmt.executeUpdate(sql2);
                    stmt.close();
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                }
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // TODO 修改护工的位置
            changeWorkerPos(false, customer.getCareWorker(), customer.getRoomID());
            changeWorkerPos(true, customer.getCareWorker(), roomIdNew);

            customer.setRoomID(roomIdNew);
            customer.setBedID(bedIdNew);
        }

        // TODO 检查是否需要更改护工
        if (!workerRankOld.equals(customerWorkerRankComboBox.getValue()) || (rank != customerCareTypeComboBox.getValue())) {
            // TODO 需要更改新的护工
            Connection conn;
            Statement stmt;
            // TODO 查询是否有符合要求的新的护工，若有则更改原来护工的位置，没有则返回退出
            double room_idDouble = Double.valueOf(customer.getRoomID().substring(1));  //房间号，可能是换过的房间
            String workerIdNew = "";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                    String sql = "SELECT worker_id FROM NursingHome.worker WHERE worker_rank='" + customerWorkerRankComboBox.getValue() + "' AND worker_customerrank=" + customerCareTypeComboBox.getValue() + " ORDER BY abs(" + room_idDouble + "-worker_vispos) ASC, worker_customernumber ASC";
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        workerIdNew = rs.getString(1);
                    } else {
                        showAlert("[警告]没有满足需求的护工！");
                        available = false;
                        rs.close();
                        stmt.close();
                        conn.close();
                        return customer;
                    }
                    stmt.close();
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                }
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // TODO 更改原来护工的位置
            changeWorkerPos(false, customer.getCareWorker(), roomIdOld);
            // TODO 更改新的护工位置信息
            changeWorkerPos(true, workerIdNew, customer.getRoomID());

            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                    stmt = conn.createStatement();
                    String sql = "UPDATE NursingHome.customer SET customer_careworker='" + workerIdNew + "' WHERE customer_id='" + customer.getId() + "'";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                }
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            customer.setCareWorker(workerIdNew);
        }

        available = true;
        return customer;
    }

    /**
     * 保存修改的员工信息
     */
    public void saveCustomerInfo() {
        // TODO 保存修改的客户信息
        customer.setName(customerNameTextField.getText());
        customer.setDate(localDateToString(customerBirthDatePicker.getValue()));
        try {
            customer.setPhone(customerPhoneTextField.getText());
            customer.setRelationPhone(customerRelationPhoneTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("[错误]电话格式错误");
        }
        customer.setRelation(customerRelationTextField.getText());
        customer.setRelationName(customerRelationNameTextField.getText());
        customer.setEnterTime(ControllerUtils.localDateToString(customerEnterTimeDatePicker.getValue()));

        // TODO 如果客户改变护理等级、护工等级、房间等级，需要重新分配

        // TODO 将该客户重新分配一个护工
        customer = autoAllocate(customer, customer.getRank());

        if (available) {
            // TODO 保存修改的信息
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                    String sql = "UPDATE NursingHome.customer SET customer_name='" + customer.getName() + "', customer_date='" + customer.getDate() + "', customer_entertime='" + customer.getEnterTime() + "', customer_roomid='" + customer.getRoomID() + "', customer_bedid='" + customer.getBedID() + "', customer_phone='" + customer.getPhone() + "', customer_careworker='" + customer.getCareWorker() + "', customer_rank='" + customer.getRank() + "', customer_relationname='" + customer.getRelationName() + "', customer_relation='" + customer.getRelation() + "', customer_relationphone='" + customer.getRelationPhone() + "' WHERE customer_id='" + customer.getId() + "'";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                }
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            BusinessAdminUIController.setInfoTableView(false, false, customer);
            getApp().floatStage.close();
        }
    }

    /**
     * 返回人事管理界面
     */
    public void backToPeopleAdmin() {
        getApp().floatStage.close();
    }
}
