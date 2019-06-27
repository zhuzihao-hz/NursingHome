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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.showtime;
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
        if (workerTab.isSelected()){
            PeopleSetInfoUIController.peopleType="护工";
            application.createPeopleSetInfoUI();
        }else if (doctorTab.isSelected()){
            PeopleSetInfoUIController.peopleType="医生";
            application.createPeopleSetInfoUI();
        }else if (doorBoyTab.isSelected()){
            PeopleSetInfoUIController.peopleType="勤杂人员";
            application.createPeopleSetInfoUI();
        }
    }

    public void deletePeople(){
        // TODO 删除员工
        if (workerTab.isSelected()){
            List<Worker> workerSelected = workerTableView.getSelectionModel().getSelectedItems();
            for(int i=0;i<workerSelected.size();i++){
                //System.out.println(workerSelected.get(i).getId());
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql="DELETE FROM NursingHome.worker WHERE worker_id='"+workerSelected.get(i).getId()+"'";
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
            List<Doctor> doctorSelected = doctorTableView.getSelectionModel().getSelectedItems();
            for(int i=0;i<doctorSelected.size();i++){
                //System.out.println(doctorSelected.get(i).getId());
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql="DELETE FROM NursingHome.doctor WHERE doctor_id='"+doctorSelected.get(i).getId()+"'";
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
            List<DoorBoy> doorBoySelected = doorBoyTableView.getSelectionModel().getSelectedItems();
            for(int i=0;i<doorBoySelected.size();i++){
                //System.out.println(doorBoySelected.get(i).getId());
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql="DELETE FROM NursingHome.doorboy WHERE doorboy_id='"+doorBoySelected.get(i).getId()+"'";
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
    }

    public void setPeopleInfo() {
        // TODO 设置员工信息
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
        }
    }

    public static void setInfoTableView(boolean isInsert,Object people){
        // TODO 在新增或者修改完信息后，在TableView中也修改或者新增信息
        if (isInsert){
            if (people.getClass().getName().equals("NursingHome.dataclass.Worker")){
                Worker worker=((Worker)people);
                workerObservableList.add(worker);
            }else if (people.getClass().getName().equals("NursingHome.dataclass.Doctor")){
                Doctor doctor=((Doctor)people);
                doctorObservableList.add(doctor);
            }else{
                DoorBoy doorBoy=((DoorBoy)people);
                doorBoyObservableList.add(doorBoy);
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
            }else{
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
                        PeopleSetInfoUIController.setPeople((workerSelected.get(0)));
                        getApp().createPeopleSetInfoUI();
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
                        PeopleSetInfoUIController.setPeople((doctorSelected.get(0)));
                        getApp().createPeopleSetInfoUI();
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
                        PeopleSetInfoUIController.setPeople((doorBoySelected.get(0)));
                        getApp().createPeopleSetInfoUI();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
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

    public void displayDoctor(){
        // TODO 显示医生信息
        doctorObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
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
