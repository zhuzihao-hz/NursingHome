package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.*;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;
import static NursingHome.GlobalInfo.*;
import static NursingHome.SQLMethod.*;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class PeopleAdminUIController implements Initializable {
    private Main application;
    @FXML
    private TableColumn<StringProperty, String> workerID;
    @FXML
    private TableColumn<StringProperty, String> workerName;
    @FXML
    private TableColumn<StringProperty, String> workerBirth;
    @FXML
    private TableColumn<StringProperty, Integer> workerSalary;
    @FXML
    private TableColumn<StringProperty, String> workerRank;
    @FXML
    private TableColumn<StringProperty, String> workerCustomerRank;
    @FXML
    private TableColumn<IntegerProperty, Integer> workerCustomerNumber;
    @FXML
    private TableView<Worker> workerTableView;
    @FXML
    private Tab workerTab;
    private static ObservableList<Worker> workerObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<StringProperty, String> doctorID;
    @FXML
    private TableColumn<StringProperty, String> doctorName;
    @FXML
    private TableColumn<StringProperty, String> doctorBirth;
    @FXML
    private TableColumn<StringProperty, String> doctorMajor;
    @FXML
    private TableColumn<StringProperty, Integer> doctorSalary;
    @FXML
    private TableView<Doctor> doctorTableView;
    @FXML
    private Tab doctorTab;
    private static ObservableList<Doctor> doctorObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<StringProperty, String> doorBoyID;
    @FXML
    private TableColumn<StringProperty, String> doorBoyName;
    @FXML
    private TableColumn<StringProperty, String> doorBoyBirth;
    @FXML
    private TableColumn<StringProperty, String> doorBoyWorkPlace;
    @FXML
    private TableColumn<StringProperty, Integer> doorBoySalary;
    @FXML
    private TableView<DoorBoy> doorBoyTableView;
    @FXML
    private Tab doorBoyTab;
    private static ObservableList<DoorBoy> doorBoyObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<StringProperty, String> adminID;
    @FXML
    private TableColumn<StringProperty, String> adminName;
    @FXML
    private TableColumn<StringProperty, String> adminBirth;
    @FXML
    private TableColumn<StringProperty, String> adminPosition;
    @FXML
    private TableColumn<StringProperty, Integer> adminSalary;
    @FXML
    private TableView<Administrator> adminTableView;
    @FXML
    private Tab adminTab;
    private static ObservableList<Administrator> adminObservableList = FXCollections.observableArrayList();

    @FXML
    private Text dateText;
    @FXML
    private Label nameLabel;
    @FXML
    private TextField searchText;
    @FXML
    private JFXComboBox<String> contextComboBox = new JFXComboBox<>();
    @FXML
    private ImageView imageView;

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameLabel.setText(MANAGER_NAME);
        showtime(dateText);
        workerSelected();
        displayDoctor();
        bindDoctor();
        displayDoorBoy();
        bindDoorBoy();
        displayWorker();
        bindWorker();
        displayAdministrator();
        bindAdministrator();
    }

    /**
     * 登出
     */
    public void logout() {
        MANAGER_ID = "";
        MANAGER_NAME = "";
        MANAGER_PRIV = -1;
        MANAGER_PASSWORD = "";
        application.stage.close();
        application.gotoAdminLoginUI();
    }

    /**
     * 退出
     */
    public void quit() {
        application.stage.close();
    }

    /**
     * 查看用户信息
     */
    public void personInfo() {
        getApp().createPersonalInfoUI();
    }

    /**
     * 查看用户信息
     */
    public void personInfoImage() {
        getApp().createPersonalInfoUI();
    }

    /**
     * 返回主界面
     */
    public void backToMainMenu() {
        quit();
        application.gotoAdminMainUI();
    }

    /**
     * 帮助信息
     */
    public void aboutInfo() {
        application.createAboutInfoUI();
    }

    /**
     * 上传头像
     */
    public void uploadImage() {
        if (MANAGER_PRIV == 0) {
            if (doctorTab.isSelected()) {
                List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
                if (doctorSelected.size() > 0) {
                    ChangePasswordUIController.setPeopleId(doctorSelected.get(0).getId());
                    getApp().createUploadImageUI();
                }
            } else if (doorBoyTab.isSelected()) {
                List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
                if (doorBoySelected.size() > 0) {
                    if (doorBoySelected.get(0).getWorkPlace().equals("前台")) {
                        ChangePasswordUIController.setPeopleId(doorBoySelected.get(0).getId());
                        getApp().createUploadImageUI();
                    } else {
                        showAlert("[警告]该员工没有账户！");
                    }
                }
            } else if (adminTab.isSelected()) {
                List<Administrator> adminSelected = adminTableView.getSelectionModel().getSelectedItems();
                if (adminSelected.size() > 0) {
                    ChangePasswordUIController.setPeopleId(adminSelected.get(0).getId());
                    getApp().createUploadImageUI();
                }
            } else {
                showAlert("[警告]该员工没有账户！");
            }
        } else {
            showAlert("[警告]您没有上传头像的权限！");
        }
    }

    /**
     * 选中workerTab时更改下拉框
     */
    public void workerSelected() {
        contextComboBox.getItems().clear();
        contextComboBox.getItems().add("--无--");
        contextComboBox.getItems().add("工号");
        contextComboBox.getItems().add("姓名");
        contextComboBox.getItems().add("年龄");
        contextComboBox.getItems().add("薪水");
        contextComboBox.getItems().add("级别");
        contextComboBox.getItems().add("目标护理级别");
        contextComboBox.getItems().add("护理人数");
        contextComboBox.setValue("--无--");
        contextComboBox.setEditable(false);
    }

    /**
     * 选中doctorTab时更改下拉框
     */
    public void doctorSelected() {
        contextComboBox.getItems().clear();
        contextComboBox.getItems().add("--无--");
        contextComboBox.getItems().add("工号");
        contextComboBox.getItems().add("姓名");
        contextComboBox.getItems().add("年龄");
        contextComboBox.getItems().add("科室");
        contextComboBox.getItems().add("薪水");
        contextComboBox.setValue("--无--");
        contextComboBox.setEditable(false);
    }

    /**
     * 选中doorboyTab时更改下拉框
     */
    public void doorBoySelected() {
        contextComboBox.getItems().clear();
        contextComboBox.getItems().add("--无--");
        contextComboBox.getItems().add("工号");
        contextComboBox.getItems().add("姓名");
        contextComboBox.getItems().add("年龄");
        contextComboBox.getItems().add("岗位");
        contextComboBox.getItems().add("薪水");
        contextComboBox.setValue("--无--");
        contextComboBox.setEditable(false);
    }

    /**
     * 选中administratorTab时更改下拉框
     */
    public void administratorSelected() {
        contextComboBox.getItems().clear();
        contextComboBox.getItems().add("--无--");
        contextComboBox.getItems().add("工号");
        contextComboBox.getItems().add("姓名");
        contextComboBox.getItems().add("年龄");
        contextComboBox.getItems().add("薪水");
        contextComboBox.getItems().add("岗位");
        contextComboBox.setValue("--无--");
        contextComboBox.setEditable(false);
    }

    /**
     * 搜索
     * <p>用于搜索TableView中的内容</p>
     * 护工表可以搜索护工编号、护工姓名
     * 医生表可以搜索医生编号、医生姓名
     * 勤杂人员表可以搜索勤杂人员编号、勤杂人员姓名
     * 行政人员表可以搜索行政人员标号、行政人员姓名
     */
    public void search() {
        if (workerTab.isSelected()) {
            displayWorker();
            bindWorker();
        } else if (doctorTab.isSelected()) {
            displayDoctor();
            bindDoctor();
        } else if (doorBoyTab.isSelected()) {
            displayDoorBoy();
            bindDoorBoy();
        } else {
            displayAdministrator();
            bindAdministrator();
        }
    }

    /**
     * 插入员工
     */
    public void insertPeople() {
        // TODO 新增员工
        if (MANAGER_PRIV == 0) {
            // TODO 只有主管才能新增行政人员
            PeopleSetInfoUIController.setPeople(new Object());
            if (workerTab.isSelected()) {
                PeopleSetInfoUIController.peopleType = "护工";
                application.createPeopleSetInfoUI();
            } else if (doctorTab.isSelected()) {
                PeopleSetInfoUIController.peopleType = "医生";
                application.createPeopleSetInfoUI();
            } else if (doorBoyTab.isSelected()) {
                PeopleSetInfoUIController.peopleType = "勤杂人员";
                application.createPeopleSetInfoUI();
            } else if (adminTab.isSelected()) {
                PeopleSetInfoUIController.peopleType = "行政人员";
                application.createPeopleSetInfoUI();
            }
        } else {
            showAlert("[警告]您没有新增人员的权限！");
        }
    }

    /**
     * 为客户自动分配护工
     *
     * @param customer   要分配的客户对象
     * @param rank       客户护理等级
     * @param workerRank 客户要求等护工的等级
     * @return 返回分配完等客户对象
     */
    public Customer autoAllocate(Customer customer, int rank, String workerRank) {
        // TODO 自动分配
        Connection conn;
        Statement stmt;

        // TODO 获得护工号
        // TODO 获得房间号
        double room_idDouble = Double.valueOf(customer.getRoomID().substring(1));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql = "SELECT worker_id FROM NursingHome.worker WHERE worker_rank='" + workerRank + "' AND worker_customerrank=" + rank + " ORDER BY abs(" + room_idDouble + "-worker_vispos) ASC, worker_customernumber ASC";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    customer.setCareWorker(rs.getString(1));
                }
                String sql1 = "UPDATE NursingHome.worker SET worker_customernumber=worker_customernumber+1 WHERE worker_id='" + customer.getCareWorker() + "'";
                String sql2 = "UPDATE NursingHome.worker SET worker_vispos=(worker_vispos*(worker_customernumber-1)+" + room_idDouble + ")/worker_customernumber WHERE worker_id='" + customer.getCareWorker() + "'";
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

        return customer;
    }

    /**
     * 删除员工信息
     */
    public void deletePeople() {
        // TODO 删除员工
        //  其中删除前台、行政人员、医生时还得把密码删掉
        if (MANAGER_PRIV == 0) {
            if (workerTab.isSelected()) {
                List<Worker> workerSelected = workerTableView.getSelectionModel().getSelectedItems();
                int N;
                for (int i = 0; i < workerSelected.size(); i++) {
                    // TODO 在数据库中删除护工，并且在历史员工里把状态改为离职（0）

                    String workerRank = workerSelected.get(i).getRank();
                    String workerId = workerSelected.get(i).getId();

                    // TODO 先搜索是否有该需求的其他护工，没有的话不能解雇
                    N = 0;
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                            String sql = "SELECT count(*) FROM NursingHome.worker WHERE worker_rank = '" + workerSelected.get(i).getRank() + "' AND worker_customerrank = '" + workerSelected.get(i).getCustomerRank() + "';";
                            stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery(sql);
                            if (rs.first()) {
                                N = rs.getInt(1);
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

                    if (N == 1) {
                        showAlert("[警告]该类型护工只有一个了，不能解雇！");
                    } else {
                        // TODO 相同需求的护工不止一个，可以删除该护工
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                                String sql = "DELETE FROM NursingHome.worker WHERE worker_id='" + workerSelected.get(i).getId() + "'";
                                String sql1 = "UPDATE NursingHome.historical_staff SET staff_status=0 WHERE staff_id='" + workerSelected.get(i).getId() + "'";
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

                        // TODO 在删除护工之后，重新分配
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                                String sql = "SELECT * FROM NursingHome.customer WHERE customer_careworker='" + workerId + "'";
                                stmt = conn.createStatement();
                                ResultSet rs = stmt.executeQuery(sql);
                                while (rs.next()) {
                                    Customer customer = new Customer();
                                    customer.setId(rs.getString(1));
                                    customer.setName(rs.getString(2));
                                    customer.setDate(rs.getString(3));
                                    customer.setEnterTime(rs.getString(4));
                                    customer.setRoomID(rs.getString(5));
                                    customer.setBedID(rs.getString(6));
                                    customer.setPhone(rs.getString(7));
                                    customer.setCareWorker(rs.getString(8));
                                    customer.setRank(rs.getInt(9));
                                    customer.setRelationName(rs.getString(10));
                                    customer.setRelation(rs.getString(11));
                                    customer.setRelationPhone(rs.getString(12));
                                    customer = autoAllocate(customer, customer.getRank(), workerRank);

                                    // TODO 重新分配后更改数据库中的客户表
                                    try {
                                        Class.forName("com.mysql.jdbc.Driver");
                                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                                        if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                                            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                                            String sql1 = "UPDATE NursingHome.customer SET customer_name='" + customer.getName() + "', customer_date='" + customer.getDate() + "', customer_entertime='" + customer.getEnterTime() + "', customer_roomid='" + customer.getRoomID() + "', customer_bedid='" + customer.getBedID() + "', customer_phone='" + customer.getPhone() + "', customer_careworker='" + customer.getCareWorker() + "', customer_rank='" + customer.getRank() + "', customer_relationname='" + customer.getRelationName() + "', customer_relation='" + customer.getRelation() + "', customer_relationphone='" + customer.getRelationPhone() + "' WHERE customer_id='" + customer.getId() + "'";
                                            stmt = conn.createStatement();
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
                    }
                }
                System.out.println("解雇Worker成功");
                displayWorker();
                bindWorker();
            } else if (doctorTab.isSelected()) {
                // TODO 删除医生,并且把历史员工表里的状态改为（0）
                List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
                for (int i = 0; i < doctorSelected.size(); i++) {
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                            String sql = "DELETE FROM NursingHome.doctor WHERE doctor_id='" + doctorSelected.get(i).getId() + "'";
                            String sql2 = "DELETE FROM NursingHome.manager WHERE manager_id='" + doctorSelected.get(i).getId() + "'";
                            String sql1 = "UPDATE NursingHome.historical_staff SET staff_status=0 WHERE staff_id='" + doctorSelected.get(i).getId() + "'";
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql);
                            stmt.executeUpdate(sql2);
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
                }
                displayDoctor();
                bindDoctor();
                System.out.println("解雇Doctor成功");
            } else if (doorBoyTab.isSelected()) {
                // TODO 删除勤杂工同时将历史员工表里的状态改为离职（0）
                List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
                for (int i = 0; i < doorBoySelected.size(); i++) {
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                            String sql = "DELETE FROM NursingHome.doorboy WHERE doorboy_id='" + doorBoySelected.get(i).getId() + "'";
                            String sql1 = "UPDATE NursingHome.historical_staff SET staff_status=0 WHERE staff_id='" + doorBoySelected.get(i).getId() + "'";
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql);
                            if (doorBoySelected.get(i).getWorkPlace().equals("前台")) {
                                String sql2 = "DELETE FROM NursingHome.manager WHERE manager_id='" + doorBoySelected.get(i).getId() + "'";
                                stmt.executeUpdate(sql2);
                            }
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
                }
                displayDoorBoy();
                bindDoorBoy();
                System.out.println("解雇DoorBoy成功");
            } else if (adminTab.isSelected()) {
                // TODO 删除行政人员同时将历史员工表里的状态改为离职（0）
                List<Administrator> adminSelected = adminTableView.getSelectionModel().getSelectedItems();
                for (int i = 0; i < adminSelected.size(); i++) {
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                            conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                            String sql = "DELETE FROM NursingHome.administrator WHERE administrator_id='" + adminSelected.get(i).getId() + "'";
                            String sql2 = "DELETE FROM NursingHome.manager WHERE manager_id='" + adminSelected.get(i).getId() + "'";
                            String sql1 = "UPDATE NursingHome.historical_staff SET staff_status=0 WHERE staff_id='" + adminSelected.get(i).getId() + "'";
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql);
                            stmt.executeUpdate(sql2);
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
                }
                displayAdministrator();
                bindAdministrator();
                System.out.println("解雇Administrator成功");
            }
        } else {
            showAlert("[警告]您没有删除员工的权限！");
        }
    }

    /**
     * 进入设置员工信息界面
     */
    public void setPeopleInfo() {
        // TODO 查看员工信息，主管和高层（院长、总经理）都能查看，但主管才能修改
        if (MANAGER_PRIV == 0 || MANAGER_PRIV == 1) {
            if (workerTab.isSelected()) {
                List<Worker> workerSelected = workerTableView.getSelectionModel().getSelectedItems();
                if (workerSelected.size() > 0) {
                    PeopleSetInfoUIController.setPeople((workerSelected.get(0)));
                    getApp().createPeopleSetInfoUI();
                }
            } else if (doctorTab.isSelected()) {
                List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
                if (doctorSelected.size() > 0) {
                    PeopleSetInfoUIController.setPeople((doctorSelected.get(0)));
                    getApp().createPeopleSetInfoUI();
                }
            } else if (doorBoyTab.isSelected()) {
                List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
                if (doorBoySelected.size() > 0) {
                    PeopleSetInfoUIController.setPeople((doorBoySelected.get(0)));
                    getApp().createPeopleSetInfoUI();
                }
            } else if (adminTab.isSelected()) {
                List<Administrator> adminSelected = adminTableView.getSelectionModel().getSelectedItems();
                if (adminSelected.size() > 0) {
                    PeopleSetInfoUIController.setPeople((adminSelected.get(0)));
                    getApp().createPeopleSetInfoUI();
                }
            }
        } else {
            showAlert("[警告]您没有查看用户信息的权限！");
        }
    }

    /**
     * 在表格中修改或插入员工信息
     *
     * @param isInsert     是否是插入
     * @param people       修改或插入的员工对象
     * @param insertFailed 是否插入失败
     */
    public static void setInfoTableView(boolean isInsert, Object people, boolean insertFailed) {
        // TODO 在新增或者修改完信息后，在TableView中也修改或者新增信息
        if (isInsert) {
            if (people.getClass().getName().equals("NursingHome.dataclass.Worker")) {
                Worker worker = ((Worker) people);
                workerObservableList.add(worker);
            } else if (people.getClass().getName().equals("NursingHome.dataclass.Doctor")) {
                Doctor doctor = ((Doctor) people);
                doctorObservableList.add(doctor);
            } else if (people.getClass().getName().equals("NursingHome.dataclass.DoorBoy")) {
                DoorBoy doorBoy = ((DoorBoy) people);
                doorBoyObservableList.add(doorBoy);
            } else {
                // TODO 在数据库中已经避免了重复增加的问题，insertFailed为假时代表插入成功，需要插入
                if (!insertFailed) {
                    Administrator admin = ((Administrator) people);
                    adminObservableList.add(admin);
                }
            }
        } else {
            if (people.getClass().getName().equals("NursingHome.dataclass.Worker")) {
                Worker worker = ((Worker) people);
                for (int i = 0; i < workerObservableList.size(); i++) {
                    if (workerObservableList.get(i).getId().equals(worker.getId())) {
                        workerObservableList.get(i).setName(worker.getName());
                        workerObservableList.get(i).setSalary(worker.getSalary());
                        workerObservableList.get(i).setRank(worker.getRank());
                        workerObservableList.get(i).setDate(worker.getDate());
                        workerObservableList.get(i).setCustomerRank(worker.getCustomerRank());
                        break;
                    }
                }
            } else if (people.getClass().getName().equals("NursingHome.dataclass.Doctor")) {
                Doctor doctor = ((Doctor) people);
                for (int i = 0; i < doctorObservableList.size(); i++) {
                    if (doctorObservableList.get(i).getId().equals(doctor.getId())) {
                        doctorObservableList.get(i).setName(doctor.getName());
                        doctorObservableList.get(i).setSalary(doctor.getSalary());
                        doctorObservableList.get(i).setMajor(doctor.getMajor());
                        doctorObservableList.get(i).setDate(doctor.getDate());
                        break;
                    }
                }
            } else if (people.getClass().getName().equals("NursingHome.dataclass.DoorBoy")) {
                DoorBoy doorBoy = ((DoorBoy) people);
                for (int i = 0; i < doorBoyObservableList.size(); i++) {
                    if (doorBoyObservableList.get(i).getId().equals(doorBoy.getId())) {
                        doorBoyObservableList.get(i).setName(doorBoy.getName());
                        doorBoyObservableList.get(i).setSalary(doorBoy.getSalary());
                        doorBoyObservableList.get(i).setWorkPlace(doorBoy.getWorkPlace());
                        doorBoyObservableList.get(i).setDate(doorBoy.getDate());
                        break;
                    }
                }
            } else {
                Administrator administrator = ((Administrator) people);
                for (int i = 0; i < adminObservableList.size(); i++) {
                    if (adminObservableList.get(i).getId().equals(administrator.getId())) {
                        adminObservableList.get(i).setName(administrator.getName());
                        adminObservableList.get(i).setPosition(administrator.getPosition());
                        adminObservableList.get(i).setDate(administrator.getDate());
                        adminObservableList.get(i).setSalary(administrator.getSalary());
                        break;
                    }
                }
            }
        }
    }

    /**
     * 双击进入详细信息界面
     */
    public void clickIntoDetail() {
        // TODO 双击进入修改信息界面
        if (workerTab.isSelected()) {
            // TODO 选中workerTab时，修改
            workerTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Worker> workerSelected = workerTableView.getSelectionModel().getSelectedItems();
            workerTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && workerSelected.size() == 1) {
                    try {
                        if (MANAGER_PRIV == 0 || MANAGER_PRIV == 1) {
                            //TODO 只有主管和院长、总经理能够进入详细信息界面，但是主管才能修改，在详细界面中实现
                            PeopleSetInfoUIController.setPeople((workerSelected.get(0)));
                            getApp().createPeopleSetInfoUI();
                        } else {
                            showAlert("[警告]您没有查看用户信息的权限！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (doctorTab.isSelected()) {
            // TODO 选中doctorTab时，修改
            doctorTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
            doctorTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && doctorSelected.size() == 1) {
                    try {
                        if (MANAGER_PRIV == 0 || MANAGER_PRIV == 1) {
                            //TODO 只有主管和院长、总经理能够进入详细信息界面，但是主管才能修改，在详细界面中实现
                            PeopleSetInfoUIController.setPeople((doctorSelected.get(0)));
                            getApp().createPeopleSetInfoUI();
                        } else {
                            showAlert("[警告]您没有查看用户信息的权限！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (doorBoyTab.isSelected()) {
            // TODO 选中doorBoyTab时，修改
            doorBoyTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
            doorBoyTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && doorBoySelected.size() == 1) {
                    try {
                        if (MANAGER_PRIV == 0 || MANAGER_PRIV == 1) {
                            //TODO 只有主管和院长、总经理能够进入详细信息界面，但是主管才能修改，在详细界面中实现
                            PeopleSetInfoUIController.setPeople((doorBoySelected.get(0)));
                            getApp().createPeopleSetInfoUI();
                        } else {
                            showAlert("[警告]您没有查看用户信息的权限！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (adminTab.isSelected()) {
            // TODO 选中adminTab时，修改
            adminTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Administrator> adminSelected = adminTableView.getSelectionModel().getSelectedItems();
            adminTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && adminSelected.size() == 1) {
                    try {
                        if (MANAGER_PRIV == 0 || MANAGER_PRIV == 1) {
                            //TODO 只有主管和院长、总经理能够进入详细信息界面，但是主管才能修改，在详细界面中实现
                            PeopleSetInfoUIController.setPeople((adminSelected.get(0)));
                            getApp().createPeopleSetInfoUI();
                        } else {
                            showAlert("[警告]您没有查看用户信息的权限！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 修改密码
     */
    public void changePassword() {
        if (MANAGER_PRIV == 0) {
            if (doctorTab.isSelected()) {
                List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
                if (doctorSelected.size() > 0) {
                    ChangePasswordUIController.setPeopleId(doctorSelected.get(0).getId());
                    getApp().createChangePasswordUI();
                }
            } else if (doorBoyTab.isSelected()) {
                List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
                if (doorBoySelected.size() > 0) {
                    if (doorBoySelected.get(0).getWorkPlace().equals("前台")) {
                        ChangePasswordUIController.setPeopleId(doorBoySelected.get(0).getId());
                        getApp().createChangePasswordUI();
                    } else {
                        showAlert("[警告]该员工没有账户！");
                    }
                }
            } else if (adminTab.isSelected()) {
                List<Administrator> adminSelected = adminTableView.getSelectionModel().getSelectedItems();
                if (adminSelected.size() > 0) {
                    ChangePasswordUIController.setPeopleId(adminSelected.get(0).getId());
                    getApp().createChangePasswordUI();
                }
            } else {
                showAlert("[警告]该员工没有账户！");
            }
        } else {
            showAlert("[警告]您没有修改密码的权限！");
        }
    }

    /**
     * 绑定并显示护工数据
     */
    public void bindWorker() {
        workerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        workerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        workerBirth.setCellValueFactory(new PropertyValueFactory<>("date"));
        workerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        workerRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        workerCustomerRank.setCellValueFactory(new PropertyValueFactory<>("customerRank"));
        workerCustomerNumber.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));
        workerTableView.setVisible(true);
        workerTableView.setEditable(false);
        workerTableView.setTableMenuButtonVisible(true);
        workerTableView.setItems(workerObservableList);
    }

    /**
     * 绑定并显示医生数据
     */
    public void bindDoctor() {
        doctorID.setCellValueFactory(new PropertyValueFactory<>("id"));
        doctorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doctorBirth.setCellValueFactory(new PropertyValueFactory<>("date"));
        doctorSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        doctorMajor.setCellValueFactory(new PropertyValueFactory<>("major"));
        doctorTableView.setVisible(true);
        doctorTableView.setEditable(false);
        doctorTableView.setTableMenuButtonVisible(true);
        doctorTableView.setItems(doctorObservableList);
    }

    /**
     * 绑定并显示勤杂人员数据
     */
    public void bindDoorBoy() {
        doorBoyID.setCellValueFactory(new PropertyValueFactory<>("id"));
        doorBoyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doorBoyBirth.setCellValueFactory(new PropertyValueFactory<>("date"));
        doorBoySalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        doorBoyWorkPlace.setCellValueFactory(new PropertyValueFactory<>("workPlace"));
        doorBoyTableView.setVisible(true);
        doorBoyTableView.setEditable(false);
        doorBoyTableView.setTableMenuButtonVisible(true);
        doorBoyTableView.setItems(doorBoyObservableList);
    }

    /**
     * 绑定并显示行政人员数据
     */
    public void bindAdministrator() {
        adminID.setCellValueFactory(new PropertyValueFactory<>("id"));
        adminName.setCellValueFactory(new PropertyValueFactory<>("name"));
        adminBirth.setCellValueFactory(new PropertyValueFactory<>("date"));
        adminSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        adminPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        adminTableView.setVisible(true);
        adminTableView.setEditable(false);
        adminTableView.setTableMenuButtonVisible(true);
        adminTableView.setItems(adminObservableList);
    }

    /**
     * 加载护工数据
     */
    public void displayWorker() {
        // TODO 显示护工信息
        workerObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql;
                if (contextComboBox.getValue().equals("--无--")) {
                    sql = "SELECT * FROM NursingHome.worker";
                } else if (contextComboBox.getValue().equals("工号")) {
                    sql = "SELECT * FROM NursingHome.worker WHERE worker_id LIKE '" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("姓名")) {
                    sql = "SELECT * FROM NursingHome.worker WHERE worker_name LIKE '%" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("年龄")) {
                    sql = "SELECT * FROM NursingHome.worker WHERE Year(sysdate())-year(worker_date) = '" + searchText.getText() + "'";
                } else if (contextComboBox.getValue().equals("薪水")) {
                    sql = "SELECT * FROM NursingHome.worker WHERE worker_salary = '" + searchText.getText() + "'";
                } else if (contextComboBox.getValue().equals("级别")) {
                    sql = "SELECT * FROM NursingHome.worker WHERE worker_rank LIKE '" + searchText.getText() + "'";
                } else if (contextComboBox.getValue().equals("目标护理级别")) {
                    sql = "SELECT * FROM NursingHome.worker WHERE worker_customerrank = '" + searchText.getText() + "'";
                } else if (contextComboBox.getValue().equals("护理人数")) {
                    sql = "SELECT * FROM NursingHome.worker WHERE worker_customernumber = '" + searchText.getText() + "'";
                } else {
                    sql = "SELECT * FROM NursingHome.worker";
                }
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Worker worker = new Worker();
                    worker.setId(rs.getString(1));
                    worker.setName(rs.getString(2));
                    worker.setDate(rs.getString(3));
                    worker.setSalary(rs.getDouble(4));
                    worker.setRank(rs.getString(5));
                    worker.setCustomerRank(rs.getInt(6));
                    worker.setCustomerNumber(rs.getInt(7));
                    workerObservableList.add(worker);
                }
                rs.close();
                stmt.close();
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("护工数据导入成功！");
    }

    /**
     * 加载医生数据
     */
    public void displayDoctor() {
        // TODO 显示医生信息
        doctorObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql;
                if (contextComboBox.getValue().equals("--无--")) {
                    sql = "SELECT * FROM NursingHome.doctor";
                } else if (contextComboBox.getValue().equals("工号")) {
                    sql = "SELECT * FROM NursingHome.doctor WHERE doctor_id LIKE '" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("姓名")) {
                    sql = "SELECT * FROM NursingHome.doctor WHERE doctor_name LIKE '%" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("年龄")) {
                    sql = "SELECT * FROM NursingHome.doctor WHERE Year(sysdate())-year(doctor_date)= '" + searchText.getText() + "'";
                } else if (contextComboBox.getValue().equals("科室")) {
                    sql = "SELECT * FROM NursingHome.doctor WHERE doctor_major LIKE '%" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("薪水")) {
                    sql = "SELECT * FROM NursingHome.doctor WHERE doctor_salary = '" + searchText.getText() + "'";
                } else {
                    sql = "SELECT * FROM NursingHome.doctor";
                }
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(rs.getString(1));
                    doctor.setName(rs.getString(2));
                    doctor.setDate(rs.getString(3));
                    doctor.setMajor(rs.getString(5));
                    doctor.setSalary(rs.getDouble(4));
                    doctorObservableList.add(doctor);
                }
                rs.close();
                stmt.close();
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("医生数据导入成功！");
    }

    /**
     * 加载勤杂人员数据
     */
    public void displayDoorBoy() {
        // TODO 显示勤杂人员信息
        doorBoyObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql;
                if (contextComboBox.getValue().equals("--无--")) {
                    sql = "SELECT * FROM NursingHome.doorboy";
                } else if (contextComboBox.getValue().equals("工号")) {
                    sql = "SELECT * FROM NursingHome.doorboy WHERE doorboy_id LIKE '" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("姓名")) {
                    sql = "SELECT * FROM NursingHome.doorboy WHERE doorboy_name LIKE '%" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("年龄")) {
                    sql = "SELECT * FROM NursingHome.doorboy WHERE Year(sysdate())-year(doorboy_date) = '" + searchText.getText() + "'";
                } else if (contextComboBox.getValue().equals("岗位")) {
                    sql = "SELECT * FROM NursingHome.doorboy WHERE doorboy_workplace LIKE '%" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("薪水")) {
                    sql = "SELECT * FROM NursingHome.doorboy WHERE doorboy_salary = '" + searchText.getText() + "'";
                } else {
                    sql = "SELECT * FROM NursingHome.doorboy";
                }
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    DoorBoy doorBoy = new DoorBoy();
                    doorBoy.setId(rs.getString(1));
                    doorBoy.setName(rs.getString(2));
                    doorBoy.setDate(rs.getString(3));
                    doorBoy.setSalary(rs.getDouble(4));
                    doorBoy.setWorkPlace(rs.getString(5));
                    doorBoyObservableList.add(doorBoy);
                }
                rs.close();
                stmt.close();
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("勤杂人员数据导入成功！");
    }

    /**
     * 加载行政人员数据
     */
    public void displayAdministrator() {
        // TODO 显示勤杂人员信息
        adminObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql;
                if (contextComboBox.getValue().equals("--无--")) {
                    sql = "SELECT * FROM NursingHome.administrator";
                } else if (contextComboBox.getValue().equals("工号")) {
                    sql = "SELECT * FROM NursingHome.administrator WHERE administrator_id LIKE '" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("姓名")) {
                    sql = "SELECT * FROM NursingHome.administrator WHERE administrator_name LIKE '%" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("年龄")) {
                    sql = "SELECT * FROM NursingHome.administrator WHERE Year(sysdate())-year(administrator_date) = '" + searchText.getText() + "'";
                } else if (contextComboBox.getValue().equals("岗位")) {
                    sql = "SELECT * FROM NursingHome.administrator WHERE administrator_position LIKE '%" + searchText.getText() + "%'";
                } else if (contextComboBox.getValue().equals("薪水")) {
                    sql = "SELECT * FROM NursingHome.administrator WHERE administrator_salary = '" + searchText.getText() + "'";
                } else {
                    sql = "SELECT * FROM NursingHome.administrator";
                }
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Administrator administrator = new Administrator();
                    administrator.setId(rs.getString(1));
                    administrator.setName(rs.getString(2));
                    administrator.setDate(rs.getString(3));
                    administrator.setSalary(rs.getDouble(5));
                    administrator.setPosition(rs.getString(4));
                    adminObservableList.add(administrator);
                }
                rs.close();
                stmt.close();
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("行政人员数据导入成功！");
    }
}
