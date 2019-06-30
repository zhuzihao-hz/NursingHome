package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.Administrator;
import NursingHome.dataclass.Doctor;
import NursingHome.dataclass.DoorBoy;
import NursingHome.dataclass.Worker;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;
import static NursingHome.GlobalInfo.*;

public class PeopleAdminUIController implements Initializable {
    private Main application;
    @FXML private TableColumn<StringProperty,String> workerID;
    @FXML private TableColumn<StringProperty,String> workerName;
    @FXML private TableColumn<StringProperty,String> workerAge;
    @FXML private TableColumn<StringProperty,Integer> workerSalary;
    @FXML private TableColumn<StringProperty,String> workerRank;
    @FXML private TableView<Worker> workerTableView;
    @FXML private Tab workerTab;
    private static ObservableList<Worker> workerObservableList= FXCollections.observableArrayList();

    @FXML private TableColumn<StringProperty,String> doctorID;
    @FXML private TableColumn<StringProperty,String> doctorName;
    @FXML private TableColumn<StringProperty,String> doctorAge;
    @FXML private TableColumn<StringProperty,String> doctorMajor;
    @FXML private TableColumn<StringProperty,Integer> doctorSalary;
    @FXML private TableView<Doctor> doctorTableView;
    @FXML private Tab doctorTab;
    private static ObservableList<Doctor> doctorObservableList= FXCollections.observableArrayList();

    @FXML private TableColumn<StringProperty,String> doorBoyID;
    @FXML private TableColumn<StringProperty,String> doorBoyName;
    @FXML private TableColumn<StringProperty,String> doorBoyAge;
    @FXML private TableColumn<StringProperty,String> doorBoyWorkPlace;
    @FXML private TableColumn<StringProperty,Integer> doorBoySalary;
    @FXML private TableView<DoorBoy> doorBoyTableView;
    @FXML private Tab doorBoyTab;
    private static ObservableList<DoorBoy>doorBoyObservableList= FXCollections.observableArrayList();

    @FXML private TableColumn<StringProperty,String> adminID;
    @FXML private TableColumn<StringProperty,String> adminName;
    @FXML private TableColumn<StringProperty,String> adminAge;
    @FXML private TableColumn<StringProperty,String> adminPosition;
    @FXML private TableColumn<StringProperty,Integer> adminSalary;
    @FXML private TableView<Administrator> adminTableView;
    @FXML private Tab adminTab;
    private static ObservableList<Administrator> adminObservableList= FXCollections.observableArrayList();

    @FXML Text dateText;
    @FXML private Label nameLabel;

    public void setApp(Main app) {
        this.application = app;
    }
    public Main getApp() {return this.application; }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        nameLabel.setText(MANAGER_NAME);
        showtime(dateText);
        displayDoctor();
        bindDoctor();
        displayDoorBoy();
        bindDoorBoy();
        displayWorker();
        bindWorker();
        displayAdministrator();
        bindAdministrator();
    }

    public void logout() throws Exception{
        MANAGER_ID = "";
        MANAGER_NAME = "";
        MANAGER_PRIV = -1;
        MANAGER_PASSWORD = "";
        application.stage.close();
        application.gotoAdminLoginUI();
    }

    public void quit() {
        application.stage.close();
    }

    public void personInfo() {
        getApp ().createPersonalInfoUI();
    }

    public void personInfoImage(){getApp().createPersonalInfoUI();}

    public void backToMainMenu() throws Exception{
        quit();
        application.gotoAdminMainUI();
    }

    public void aboutInfo() {
        application.createAboutInfoUI();
    }

    public void insertPeople(){
        // TODO 新增员工
        if (MANAGER_PRIV==0){
            // TODO 只有主管才能新增行政人员
            if (workerTab.isSelected()){
                PeopleSetInfoUIController.peopleType="护工";
                application.createPeopleSetInfoUI();
            }else if (doctorTab.isSelected()){
                PeopleSetInfoUIController.peopleType="医生";
                application.createPeopleSetInfoUI();
            }else if (doorBoyTab.isSelected()){
                PeopleSetInfoUIController.peopleType="勤杂人员";
                application.createPeopleSetInfoUI();
            }else if (adminTab.isSelected()){
                PeopleSetInfoUIController.peopleType="行政人员";
                application.createPeopleSetInfoUI();
            }
        }else{
            showAlert("[警告]您没有新增人员的权限！");
        }
    }

    public void deletePeople(){
        // TODO 删除员工
        if (MANAGER_PRIV==0){
            if (workerTab.isSelected()){
                List<Worker> workerSelected = workerTableView.getSelectionModel().getSelectedItems();
                for(int i=0;i<workerSelected.size();i++){
                    // TODO 在数据库中删除护工，并且在历史员工里把状态改为离职（0）
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql="DELETE FROM NursingHome.worker WHERE worker_id='"+workerSelected.get(i).getId()+"'";
                        String sql1="UPDATE NursingHome.historical_staff SET staff_status=0 WHERE staff_id='"+workerSelected.get(i).getId()+"'";
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
                }
                // TODO 在tableView中也把员工信息去掉
                for (int i=0;i<workerSelected.size();i++){
                    for (int j=0;j<workerObservableList.size();j++){
                        if (workerSelected.get(i).getId().equals(workerObservableList.get(j).getId())){
                            workerObservableList.remove(j);
                            break;
                        }
                    }
                }
                System.out.println("解雇Worker成功");
            }else if (doctorTab.isSelected()) {
                // TODO 删除医生,并且把历史员工表里的状态改为（0）
                List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
                for(int i=0;i<doctorSelected.size();i++){
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql="DELETE FROM NursingHome.doctor WHERE doctor_id='"+doctorSelected.get(i).getId()+"'";
                        String sql1="UPDATE NursingHome.historical_staff SET staff_status=0 WHERE staff_id='"+doctorSelected.get(i).getId()+"'";
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
                }
                // TODO 在tableView中也把员工信息去掉
                for (int i=0;i<doctorSelected.size();i++){
                    for (int j=0;j<doctorObservableList.size();j++){
                        if (doctorSelected.get(i).getId().equals(doctorObservableList.get(j).getId())){
                            doctorObservableList.remove(j);
                            break;
                        }
                    }
                }
                System.out.println("解雇Doctor成功");
            }else if (doorBoyTab.isSelected()){
                // TODO 删除勤杂工同时将历史员工表里的状态改为离职（0）
                List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
                for(int i=0;i<doorBoySelected.size();i++){
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql="DELETE FROM NursingHome.doorboy WHERE doorboy_id='"+doorBoySelected.get(i).getId()+"'";
                        String sql1="UPDATE NursingHome.historical_staff SET staff_status=0 WHERE staff_id='"+doorBoySelected.get(i).getId()+"'";
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
                }
                // TODO 在tableView中也把员工信息去掉
                for (int i=0;i<doorBoySelected.size();i++){
                    for (int j=0;j<doorBoyObservableList.size();j++){
                        if (doorBoySelected.get(i).getId().equals(doorBoyObservableList.get(j).getId())){
                            doorBoyObservableList.remove(j);
                            break;
                        }
                    }
                }
                System.out.println("解雇DoorBoy成功");
            }
        }else {
            showAlert("[警告]您没有删除员工的权限！");
        }
    }

    public void setPeopleInfo() {
        // TODO 查看员工信息，主管和高层（院长、总经理）都能查看，但主管才能修改
        if (MANAGER_PRIV==0||MANAGER_PRIV==1){
            if (workerTab.isSelected()){
                List<Worker> workerSelected = workerTableView.getSelectionModel().getSelectedItems();
                PeopleSetInfoUIController.setPeople((workerSelected.get(0)));
                getApp().createPeopleSetInfoUI();
            }else if (doctorTab.isSelected()){
                List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
                PeopleSetInfoUIController.setPeople((doctorSelected.get(0)));
                getApp().createPeopleSetInfoUI();
            }else if (doorBoyTab.isSelected()){
                List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
                PeopleSetInfoUIController.setPeople((doorBoySelected.get(0)));
                getApp().createPeopleSetInfoUI();
            }else if (adminTab.isSelected()){
                List<Administrator> adminSelected = adminTableView.getSelectionModel().getSelectedItems();
                PeopleSetInfoUIController.setPeople((adminSelected.get(0)));
                getApp().createPeopleSetInfoUI();
            }
        }else{
            showAlert("[警告]您没有查看用户信息的权限！");
        }
    }

    public static void setInfoTableView(boolean isInsert,Object people,boolean insertFailed){
        // TODO 在新增或者修改完信息后，在TableView中也修改或者新增信息
        if (isInsert){
            if (people.getClass().getName().equals("NursingHome.dataclass.Worker")){
                Worker worker=((Worker)people);
                workerObservableList.add(worker);
            }else if (people.getClass().getName().equals("NursingHome.dataclass.Doctor")){
                Doctor doctor=((Doctor)people);
                doctorObservableList.add(doctor);
            }else if (people.getClass().getName().equals("NursingHome.dataclass.DoorBoy")){
                DoorBoy doorBoy=((DoorBoy)people);
                doorBoyObservableList.add(doorBoy);
            }else{
                // TODO 在数据库中已经避免了重复增加的问题，insertFailed为假时代表插入成功，需要插入
                if (!insertFailed){
                    Administrator admin=((Administrator)people);
                    adminObservableList.add(admin);
                }
            }
        }else{
            if (people.getClass().getName().equals("NursingHome.dataclass.Worker")){
                Worker worker=((Worker)people);
                for (int i=0;i<workerObservableList.size();i++){
                    if (workerObservableList.get(i).getId().equals(worker.getId())){
                        workerObservableList.get(i).setId(worker.getId());
                        workerObservableList.get(i).setName(worker.getName());
                        workerObservableList.get(i).setSalary(worker.getSalary());
                        workerObservableList.get(i).setRank(worker.getRank());
                        workerObservableList.get(i).setAge(worker.getAge());
                        break;
                    }
                }
            }else if (people.getClass().getName().equals("NursingHome.dataclass.Doctor")){
                Doctor doctor=((Doctor)people);
                for (int i=0;i<doctorObservableList.size();i++){
                    if (doctorObservableList.get(i).getId().equals(doctor.getId())){
                        doctorObservableList.get(i).setId(doctor.getId());
                        doctorObservableList.get(i).setName(doctor.getName());
                        doctorObservableList.get(i).setSalary(doctor.getSalary());
                        doctorObservableList.get(i).setMajor(doctor.getMajor());
                        doctorObservableList.get(i).setAge(doctor.getAge());
                        break;
                    }
                }
            }else if (people.getClass().getName().equals("NursingHome.dataclass.Doorboy")){
                DoorBoy doorBoy=((DoorBoy)people);
                for (int i=0;i<doorBoyObservableList.size();i++){
                    if (doorBoyObservableList.get(i).getId().equals(doorBoy.getId())){
                        doorBoyObservableList.get(i).setId(doorBoy.getId());
                        doorBoyObservableList.get(i).setName(doorBoy.getName());
                        doorBoyObservableList.get(i).setSalary(doorBoy.getSalary());
                        doorBoyObservableList.get(i).setWorkPlace(doorBoy.getWorkPlace());
                        doorBoyObservableList.get(i).setAge(doorBoy.getAge());
                        break;
                    }
                }
            }else{
                Administrator administrator=((Administrator) people);
                for (int i=0;i<adminObservableList.size();i++){
                    if (adminObservableList.get(i).getId().equals(administrator.getId())){
                        adminObservableList.get(i).setName(administrator.getName());
                        adminObservableList.get(i).setPosition(administrator.getPosition());
                        adminObservableList.get(i).setAge(administrator.getAge());
                        adminObservableList.get(i).setSalary(administrator.getAge());
                        break;
                    }
                }
            }
        }
    }

    public void clickIntoDetail(){
        // TODO 双击进入修改信息界面
        if (workerTab.isSelected()){
            // TODO 选中workerTab时，修改
            workerTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Worker> workerSelected = workerTableView.getSelectionModel().getSelectedItems();
            workerTableView.setOnMouseClicked(event -> {
                if (event.getClickCount()==2&&workerSelected.size()==1){
                    try {
                        if (MANAGER_PRIV==0||MANAGER_PRIV==1){
                            //TODO 只有主管和院长、总经理能够进入详细信息界面，但是主管才能修改，在详细界面中实现
                            PeopleSetInfoUIController.setPeople((workerSelected.get(0)));
                            getApp().createPeopleSetInfoUI();
                        }else{
                            showAlert("[警告]您没有查看用户信息的权限！");
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }else if (doctorTab.isSelected()){
            // TODO 选中doctorTab时，修改
            doctorTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
            doctorTableView.setOnMouseClicked(event -> {
                if (event.getClickCount()==2&&doctorSelected.size()==1){
                    try {
                        if (MANAGER_PRIV==0||MANAGER_PRIV==1){
                            //TODO 只有主管和院长、总经理能够进入详细信息界面，但是主管才能修改，在详细界面中实现
                            PeopleSetInfoUIController.setPeople((doctorSelected.get(0)));
                            getApp().createPeopleSetInfoUI();
                        }else{
                            showAlert("[警告]您没有查看用户信息的权限！");
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }else if (doorBoyTab.isSelected()){
            // TODO 选中doorBoyTab时，修改
            doorBoyTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
            doorBoyTableView.setOnMouseClicked(event -> {
                if (event.getClickCount()==2&&doorBoySelected.size()==1){
                    try {
                        if (MANAGER_PRIV==0||MANAGER_PRIV==1){
                            //TODO 只有主管和院长、总经理能够进入详细信息界面，但是主管才能修改，在详细界面中实现
                            PeopleSetInfoUIController.setPeople((doorBoySelected.get(0)));
                            getApp().createPeopleSetInfoUI();
                        }else{
                            showAlert("[警告]您没有查看用户信息的权限！");
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }else if (adminTab.isSelected()){
            // TODO 选中adminTab时，修改
            adminTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Administrator> adminSelected = adminTableView.getSelectionModel().getSelectedItems();
            adminTableView.setOnMouseClicked(event -> {
                if (event.getClickCount()==2&&adminSelected.size()==1){
                    try {
                        if (MANAGER_PRIV==0||MANAGER_PRIV==1){
                            //TODO 只有主管和院长、总经理能够进入详细信息界面，但是主管才能修改，在详细界面中实现
                            PeopleSetInfoUIController.setPeople((adminSelected.get(0)));
                            getApp().createPeopleSetInfoUI();
                        }else{
                            showAlert("[警告]您没有查看用户信息的权限！");
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void changePassword(){
        if (MANAGER_PRIV==0){
            if (doctorTab.isSelected()){
                List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
                ChangePasswordUIController.setPeopleid(doctorSelected.get(0).getId());
                getApp().createChangePasswordUI();
            }else if (doorBoyTab.isSelected()){
                List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
                if (doorBoySelected.get(0).getWorkPlace().equals("前台")){
                    ChangePasswordUIController.setPeopleid(doorBoySelected.get(0).getId());
                    getApp().createChangePasswordUI();
                }
            }else if (adminTab.isSelected()){
                List<Administrator> adminSelected= adminTableView.getSelectionModel().getSelectedItems();
                ChangePasswordUIController.setPeopleid(adminSelected.get(0).getId());
                getApp().createChangePasswordUI();
            }
        }else{
            showAlert("[警告]您没有修改密码的权限");
        }
    }

    public void bindDoctor(){
        doctorID.setCellValueFactory(new PropertyValueFactory<>("id"));
        doctorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doctorAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        doctorSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        doctorMajor.setCellValueFactory(new PropertyValueFactory<>("major"));
        doctorTableView.setVisible(true);
        doctorTableView.setEditable(false);
        doctorTableView.setTableMenuButtonVisible(true);
        doctorTableView.setItems(doctorObservableList);
    }

    public void bindWorker(){
        workerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        workerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        workerAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        workerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        workerRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        workerTableView.setVisible(true);
        workerTableView.setEditable(false);
        workerTableView.setTableMenuButtonVisible(true);
        workerTableView.setItems(workerObservableList);
    }

    public void bindDoorBoy(){
        doorBoyID.setCellValueFactory(new PropertyValueFactory<>("id"));
        doorBoyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doorBoyAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        doorBoySalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        doorBoyWorkPlace.setCellValueFactory(new PropertyValueFactory<>("workPlace"));
        doorBoyTableView.setVisible(true);
        doorBoyTableView.setEditable(false);
        doorBoyTableView.setTableMenuButtonVisible(true);
        doorBoyTableView.setItems(doorBoyObservableList);
    }

    public void bindAdministrator(){
        adminID.setCellValueFactory(new PropertyValueFactory<>("id"));
        adminName.setCellValueFactory(new PropertyValueFactory<>("name"));
        adminAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        adminSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        adminPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        adminTableView.setVisible(true);
        adminTableView.setEditable(false);
        adminTableView.setTableMenuButtonVisible(true);
        adminTableView.setItems(adminObservableList);
    }

    public void displayDoctor(){
        // TODO 显示医生信息
        doctorObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql="SELECT * FROM NursingHome.doctor";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Doctor doctor=new Doctor();
                doctor.setId(rs.getString(1));
                doctor.setName(rs.getString(2));
                doctor.setAge(rs.getInt(3));
                doctor.setMajor(rs.getString(5));
                doctor.setSalary(rs.getDouble(4));
                doctorObservableList.add(doctor);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("医生数据导入成功！");
    }

    public void displayWorker(){
        // TODO 显示护工信息
        workerObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql="SELECT * FROM NursingHome.worker";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Worker worker=new Worker();
                worker.setId(rs.getString(1));
                worker.setName(rs.getString(2));
                worker.setAge(rs.getInt(3));
                worker.setSalary(rs.getDouble(4));
                worker.setRank(rs.getString(5));
                workerObservableList.add(worker);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("护工数据导入成功！");
    }

    public void displayDoorBoy(){
        // TODO 显示勤杂人员信息
        doorBoyObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql="SELECT * FROM NursingHome.doorboy";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                DoorBoy doorBoy=new DoorBoy();
                doorBoy.setId(rs.getString(1));
                doorBoy.setName(rs.getString(2));
                doorBoy.setAge(rs.getInt(3));
                doorBoy.setSalary(rs.getDouble(4));
                doorBoy.setWorkPlace(rs.getString(5));
                doorBoyObservableList.add(doorBoy);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("勤杂人员数据导入成功！");
    }

    public void displayAdministrator(){
        // TODO 显示勤杂人员信息
        adminObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql="SELECT * FROM NursingHome.administrator";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Administrator administrator=new Administrator();
                administrator.setId(rs.getString(1));
                administrator.setName(rs.getString(2));
                administrator.setAge(rs.getInt(3));
                administrator.setSalary(rs.getDouble(5));
                administrator.setPosition(rs.getString(4));
                adminObservableList.add(administrator);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("行政人员数据导入成功！");
    }
}
