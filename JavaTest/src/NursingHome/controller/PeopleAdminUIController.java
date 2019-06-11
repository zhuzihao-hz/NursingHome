package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.Doctor;
import NursingHome.dataclass.DoorBoy;
import NursingHome.dataclass.Worker;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static NursingHome.GlobalInfo.*;

public class PeopleAdminUIController implements Initializable {
    private Main application;
    @FXML private TableColumn<StringProperty,String> workerID;
    @FXML private TableColumn<StringProperty,String> workerName;
    @FXML private TableColumn<StringProperty,String> workerAge;
    @FXML private TableColumn<StringProperty,Integer> workerSalary;
    @FXML private TableView<Worker> workerTableView;
    private ObservableList<Worker> workerObservableList= FXCollections.observableArrayList();

    @FXML private TableColumn<StringProperty,String> doctorID;
    @FXML private TableColumn<StringProperty,String> doctorName;
    @FXML private TableColumn<StringProperty,String> doctorAge;
    @FXML private TableColumn<StringProperty,String> doctorMajor;
    @FXML private TableColumn<StringProperty,Integer> doctorSalary;
    @FXML private TableView<Doctor> doctorTableView;
    private ObservableList<Doctor> doctorObservableList= FXCollections.observableArrayList();

    @FXML private TableColumn<StringProperty,String> doorBoyID;
    @FXML private TableColumn<StringProperty,String> doorBoyName;
    @FXML private TableColumn<StringProperty,String> doorBoyAge;
    @FXML private TableColumn<StringProperty,String> doorBoyWorkPlace;
    @FXML private TableColumn<StringProperty,Integer> doorBoySalary;
    @FXML private TableView<DoorBoy> doorBoyTableView;
    private ObservableList<DoorBoy>doorBoyObservableList= FXCollections.observableArrayList();

    public void setApp(Main app) {
        this.application = app;
    }
    public Main getApp() {return this.application; }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        displayDoctor();
        bindDoctor();
        displayDoorBoy();
        bindDoorBoy();
        displayWorker();
        bindWorker();
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

    public void backtoMainMenu() throws Exception{
        application.stage.close();
        application.gotoAdminMainUI();
    }
    public void aboutInfo() {
        application.createAboutInfoUI();
    }

    public void insertWorker(){
        // TODO 新增护工
    }

    public void deleteWorker(){
        // TODO 删除护工
    }

    public void setWorkerInfo(){
        // TODO 设置护工信息
    }

    public void insertDoctor(){
        // TODO 新增医生
    }

    public void deleteDoctor(){
        // TODO 删除医生
    }

    public void setDoctorInfo(){
        // TODO 设置医生信息
    }

    public void insertDoorBoy(){
        // TODO 新增勤杂人员
    }

    public void deleteDoorBoy(){
        // TODO 删除勤杂人员
    }

    public void setDoorBoyInfo(){
        // TODO 设置勤杂人员信息
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

    public void displayDoctor(){
        // TODO 显示医生信息
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            String sql="SELECT * FROM NursingHome.dotcor";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Doctor doctor=new Doctor();
                doctor.setId(rs.getString(1));
                doctor.setName(rs.getString(2));
                doctor.setAge(rs.getInt(3));
                doctor.setMajor(rs.getString(4));
                doctor.setSalary(rs.getDouble(5));
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
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            String sql="SELECT * FROM NursingHome.worker";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Worker worker=new Worker();
                worker.setId(rs.getString(1));
                worker.setName(rs.getString(2));
                worker.setAge(rs.getInt(3));
                worker.setSalary(rs.getDouble(4));
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
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
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

}
