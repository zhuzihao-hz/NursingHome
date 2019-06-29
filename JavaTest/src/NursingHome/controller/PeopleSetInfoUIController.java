package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Doctor;
import NursingHome.dataclass.DoorBoy;
import NursingHome.dataclass.Worker;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.showAlert;

public class PeopleSetInfoUIController implements Initializable {
    private Main application;
    private static Object people=new Object();
    private boolean isInsert=true;
    @FXML private JFXComboBox<String> peopleTypeComboBox;
    @FXML private Label peopleIdLabel;
    @FXML private TextField peopleNameTextField;
    @FXML private JFXComboBox<Integer> peopleAgeComboBox;
    @FXML private TextField peopleSalaryTextField;
    @FXML private JFXComboBox<String> peopleOtherComboBox;
    @FXML private Label peopleOtherLabel;
    public static String peopleType="";
    public void setApp(Main app) { this.application = app; }
    public Main getApp() {return this.application; }

    public static void setPeople(Object people) {
        PeopleSetInfoUIController.people = people;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        if (people.getClass().getName().equals("java.lang.Object")){
            ControllerUtils.initPeopleComboBox(peopleTypeComboBox,peopleType,peopleAgeComboBox,peopleOtherComboBox);
            if (peopleType.equals("护工")){
                peopleIdLabel.setText(getPeopleId(new Worker()));
            }else if (peopleType.equals("医生")){
                peopleIdLabel.setText(getPeopleId(new Doctor()));
            }else if (peopleType.equals("勤杂人员")){
                peopleIdLabel.setText(getPeopleId(new DoorBoy()));
            }
        }else if (people.getClass().getName().equals("NursingHome.dataclass.Worker")){
            peopleType="护工";
            ControllerUtils.initPeopleComboBox(peopleTypeComboBox,peopleType,peopleAgeComboBox,peopleOtherComboBox);
            peopleTypeComboBox.setDisable(true);
            Worker oldWorker= ((Worker) people);
            peopleIdLabel.setText(oldWorker.getId());
            peopleNameTextField.setText(oldWorker.getName());
            peopleAgeComboBox.setValue(oldWorker.getAge());
            peopleSalaryTextField.setText(String.valueOf(oldWorker.getSalary()));
            peopleOtherComboBox.setValue(oldWorker.getRank());
        }else if (people.getClass().getName().equals("NursingHome.dataclass.Doctor")){
            peopleType="医生";
            ControllerUtils.initPeopleComboBox(peopleTypeComboBox,peopleType,peopleAgeComboBox,peopleOtherComboBox);
            peopleTypeComboBox.setDisable(true);
            Doctor oldDoctor= ((Doctor) people);
            peopleIdLabel.setText(oldDoctor.getId());
            peopleNameTextField.setText(oldDoctor.getName());
            peopleAgeComboBox.setValue(oldDoctor.getAge());
            peopleSalaryTextField.setText(String.valueOf(oldDoctor.getSalary()));
            peopleOtherComboBox.setValue(oldDoctor.getMajor());
        }else if (people.getClass().getName().equals("NursingHome.dataclass.DoorBoy")){
            peopleType="勤杂人员";
            ControllerUtils.initPeopleComboBox(peopleTypeComboBox,peopleType,peopleAgeComboBox,peopleOtherComboBox);
            peopleTypeComboBox.setDisable(true);
            DoorBoy oldDoorBoy= ((DoorBoy) people);
            peopleIdLabel.setText(oldDoorBoy.getId());
            peopleNameTextField.setText(oldDoorBoy.getName());
            peopleAgeComboBox.setValue(oldDoorBoy.getAge());
            peopleSalaryTextField.setText(String.valueOf(oldDoorBoy.getSalary()));
            peopleOtherComboBox.setValue(oldDoorBoy.getWorkPlace());
        }
        /**
         * 要判断权限看是否能新增行政人员信息
         */
    }

    public void selectPeopleType(){
        peopleType=peopleTypeComboBox.getValue();
        if (peopleTypeComboBox.getValue().equals("护工")){
            peopleOtherLabel.setText("级别");
            peopleIdLabel.setText(getPeopleId(new Worker()));
        }else if (peopleTypeComboBox.getValue().equals("医生")){
            peopleOtherLabel.setText("科室");
            peopleIdLabel.setText(getPeopleId(new Doctor()));
        }else{
            peopleOtherLabel.setText("工作部门");
            peopleIdLabel.setText(getPeopleId(new DoorBoy()));
        }
        ControllerUtils.changeComboBox(peopleType,peopleOtherComboBox);
    }

    public String getPeopleId(Object object){
        String peopleId="";
        // TODO 从历史员工表获取people(不同类型的职工)数量，加一得到新员工的ID
        Connection conn;
        Statement stmt;
        int N=0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            String sql="";
            if (object.getClass().getName().equals("NursingHome.dataclass.Worker")){
                sql="SELECT count(*) FROM NursingHome.historical_staff WHERE staff_id LIKE 'W%';";
                peopleId="W";
            }else if (object.getClass().getName().equals("NursingHome.dataclass.Doctor")){
                sql="SELECT count(*) FROM NursingHome.historical_staff WHERE staff_id LIKE 'D%';";
                peopleId="D";
            }else if (object.getClass().getName().equals("NursingHome.dataclass.DoorBoy")){
                sql="SELECT count(*) FROM NursingHome.historical_staff WHERE staff_id LIKE 'B%';";
                peopleId="B";
            }else if (object.getClass().getName().equals("NursingHome.dataclass.Administrator")){
                sql="SELECT count(x) FROM NursingHome.historical_staff WHERE staff_id LIKE 'A%';";
                peopleId="A";
            }
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                N=rs.getInt(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        peopleId=peopleId+(N+1);
        return peopleId;
    }

    public void savePeopleInfo(){
        // TODO 获取信息并保存
        if (people.getClass().getName().equals("java.lang.Object")){
            // TODO 新增员工
            isInsert=true;
            if (peopleTypeComboBox.getValue().equals("护工")){
                Worker worker=new Worker();
                worker.setId(getPeopleId(worker));
                worker.setAge(peopleAgeComboBox.getValue());
                worker.setName(peopleNameTextField.getText());
                try {
                    worker.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                }catch (NumberFormatException e){
                    showAlert("[错误]薪水格式错误");
                }
                worker.setRank(peopleOtherComboBox.getValue());
                System.out.println(worker.getWorkerInfo());

                // TODO 在数据库中新增信息,在历史员工表中也要添加信息
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql1="INSERT INTO NursingHome.historical_staff VALUES ('"+worker.getId()+"','1')";
                    String sql="INSERT INTO NursingHome.worker VALUES "+worker.getWorkerInfo();
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
                people=(Object)worker;
            }else if (peopleTypeComboBox.getValue().equals("医生")){
                Doctor doctor=new Doctor();
                doctor.setId(getPeopleId(doctor));
                doctor.setAge(peopleAgeComboBox.getValue());
                doctor.setName(peopleNameTextField.getText());
                try {
                    doctor.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                }catch (NumberFormatException e){
                    showAlert("[错误]薪水格式错误");
                }
                doctor.setMajor(peopleOtherComboBox.getValue());

                // TODO 在数据库中新增信息
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql1="INSERT INTO NursingHome.historical_staff VALUES ('"+doctor.getId()+"','1')";
                    String sql="INSERT INTO NursingHome.doctor VALUES "+doctor.getDoctorInfo();
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
                people=(Object)doctor;
            }else{
                DoorBoy doorBoy=new DoorBoy();
                doorBoy.setId(getPeopleId(doorBoy));
                doorBoy.setAge(peopleAgeComboBox.getValue());
                doorBoy.setName(peopleNameTextField.getText());
                try {
                    doorBoy.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                }catch (NumberFormatException e){
                    showAlert("[错误]薪水格式错误");
                }
                doorBoy.setWorkPlace(peopleOtherComboBox.getValue());

                // TODO 在数据库中新增信息
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql1="INSERT INTO NursingHome.historical_staff VALUES ('"+doorBoy.getId()+"','1')";
                    String sql="INSERT INTO NursingHome.doorboy VALUES "+doorBoy.getDoorBoyInfo();
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
                people=(Object)doorBoy;
            }
        }else{
            isInsert=false;
            // TODO 修改员工信息
            if (peopleTypeComboBox.getValue().equals("护工")){
                // TODO 修改护工信息
                Worker worker=new Worker();
                worker.setId(peopleIdLabel.getText());
                worker.setAge(peopleAgeComboBox.getValue());
                worker.setName(peopleNameTextField.getText());
                try {
                    worker.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                }catch (NumberFormatException e){
                    showAlert("[错误]薪水格式错误");
                }
                worker.setRank(peopleOtherComboBox.getValue());
                System.out.println(worker.getWorkerInfo());
                // TODO 在数据库中修改信息
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql="UPDATE NursingHome.worker SET worker_name='"+worker.getName()+"', worker_age='"+worker.getAge()+"', worker_salary='"+worker.getSalary()+"', worker_rank='"+worker.getRank()+"' WHERE worker_id='"+worker.getId()+"'";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                people=(Object)worker;
            }else if (peopleTypeComboBox.getValue().equals("医生")){
                // TODO 修改医生信息
                Doctor doctor=new Doctor();
                doctor.setId(peopleIdLabel.getText());
                doctor.setAge(peopleAgeComboBox.getValue());
                doctor.setName(peopleNameTextField.getText());
                try {
                    doctor.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                }catch (NumberFormatException e){
                    showAlert("[错误]薪水格式错误");
                }
                doctor.setMajor(peopleOtherComboBox.getValue());

                // TODO 在数据库中修改信息
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql="UPDATE NursingHome.doctor SET doctor_name='"+doctor.getName()+"', doctor_age='"+doctor.getAge()+"', doctor_salary='"+doctor.getSalary()+"', doctor_major='"+doctor.getMajor()+"' WHERE doctor_id='"+doctor.getId()+"'";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                people=(Object)doctor;
            }else{
                // TODO 修改勤杂人员信息
                DoorBoy doorBoy=new DoorBoy();
                doorBoy.setId(peopleIdLabel.getText());
                doorBoy.setAge(peopleAgeComboBox.getValue());
                doorBoy.setName(peopleNameTextField.getText());
                try {
                    doorBoy.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                }catch (NumberFormatException e){
                    showAlert("[错误]薪水格式错误");
                }
                doorBoy.setWorkPlace(peopleOtherComboBox.getValue());

                // TODO 在数据库中修改信息
                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                    String sql="UPDATE NursingHome.doorboy SET doorboy_name='"+doorBoy.getName()+"', doorboy_age='"+doorBoy.getAge()+"', doorboy_salary='"+doorBoy.getSalary()+"', doorboy_workplace='"+doorBoy.getWorkPlace()+"' WHERE doorboy_id='"+doorBoy.getId()+"'";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                people=(Object)doorBoy;
            }
        }
        PeopleAdminUIController.setInfoTableView(isInsert,people);
        getApp().floatStage.close();
    }

    public void backToPeopleAdmin() {
        getApp().floatStage.close();
        people=new Object();
    }
}
