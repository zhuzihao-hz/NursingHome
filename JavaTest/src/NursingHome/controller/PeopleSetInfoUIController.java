package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Administrator;
import NursingHome.dataclass.Doctor;
import NursingHome.dataclass.DoorBoy;
import NursingHome.dataclass.Worker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;
import static NursingHome.GlobalInfo.MANAGER_PRIV;

public class PeopleSetInfoUIController implements Initializable {
    private Main application;
    private static Object people = new Object();
    private boolean isInsert = true;
    @FXML
    private JFXComboBox<String> peopleTypeComboBox;
    @FXML
    private Label peopleIdLabel;
    @FXML
    private TextField peopleNameTextField;
    @FXML
    private JFXDatePicker peopleBirthDatePicker;
    @FXML
    private TextField peopleSalaryTextField;
    @FXML
    private JFXComboBox<String> peopleOtherComboBox;
    @FXML
    private Label peopleOtherLabel;
    @FXML
    private Label customerRankLabel;
    @FXML
    private JFXComboBox<Integer> customerRankComboBox;
    public static String peopleType = "";
    private String adminOldPos = "";
    private String doorBoyOldPos = "";

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    public static void setPeople(Object people) {
        PeopleSetInfoUIController.people = people;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 权限是主管时，可以修改，院长、总经理只能查看
        adminOldPos = "";
        doorBoyOldPos = "";
        if (people.getClass().getName().equals("java.lang.Object")) {
            // TODO 新增员工
            if (MANAGER_PRIV == 0) {
                ControllerUtils.initPeopleComboBox(peopleTypeComboBox, peopleType, peopleOtherComboBox, customerRankComboBox);
                if (peopleType.equals("护工")) {
                    peopleIdLabel.setText(getPeopleId(new Worker()));
                } else if (peopleType.equals("医生")) {
                    peopleIdLabel.setText(getPeopleId(new Doctor()));
                } else if (peopleType.equals("勤杂人员")) {
                    peopleIdLabel.setText(getPeopleId(new DoorBoy()));
                } else if (peopleType.equals("行政人员")) {
                    peopleIdLabel.setText(getPeopleId(new Administrator()));
                }
            }
        } else if (people.getClass().getName().equals("NursingHome.dataclass.Worker")) {
            peopleType = "护工";
            ControllerUtils.initPeopleComboBox(peopleTypeComboBox, peopleType, peopleOtherComboBox, customerRankComboBox);
            peopleTypeComboBox.setDisable(true);
            Worker oldWorker = ((Worker) people);
            customerRankComboBox.setValue(oldWorker.getCustomerRank());
            peopleIdLabel.setText(oldWorker.getId());
            peopleNameTextField.setText(oldWorker.getName());
            peopleBirthDatePicker.setValue(StringToDate(oldWorker.getDate()));
            peopleSalaryTextField.setText(String.valueOf(oldWorker.getSalary()));
            peopleOtherComboBox.setValue(oldWorker.getRank());

            // TODO 查询该护工是否有护理任务，有的话不能修改护理等级
            Connection conn;
            Statement stmt;
            int N = 0;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                String sql = "SELECT count(*) FROM NursingHome.customer WHERE customer_careworker='" + oldWorker.getId() + "'";
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
            if (N>0){
                customerRankComboBox.setDisable(true);
                peopleOtherComboBox.setDisable(true);
            }
        } else if (people.getClass().getName().equals("NursingHome.dataclass.Doctor")) {
            peopleType = "医生";
            ControllerUtils.initPeopleComboBox(peopleTypeComboBox, peopleType, peopleOtherComboBox, customerRankComboBox);
            customerRankComboBox.setVisible(false);
            customerRankLabel.setVisible(false);
            peopleTypeComboBox.setDisable(true);
            Doctor oldDoctor = ((Doctor) people);
            peopleIdLabel.setText(oldDoctor.getId());
            peopleNameTextField.setText(oldDoctor.getName());
            peopleBirthDatePicker.setValue(StringToDate(oldDoctor.getDate()));
            peopleSalaryTextField.setText(String.valueOf(oldDoctor.getSalary()));
            peopleOtherComboBox.setValue(oldDoctor.getMajor());
        } else if (people.getClass().getName().equals("NursingHome.dataclass.DoorBoy")) {
            peopleType = "勤杂人员";
            ControllerUtils.initPeopleComboBox(peopleTypeComboBox, peopleType, peopleOtherComboBox, customerRankComboBox);
            customerRankComboBox.setVisible(false);
            customerRankLabel.setVisible(false);
            peopleTypeComboBox.setDisable(true);
            DoorBoy oldDoorBoy = ((DoorBoy) people);
            peopleIdLabel.setText(oldDoorBoy.getId());
            peopleNameTextField.setText(oldDoorBoy.getName());
            peopleBirthDatePicker.setValue(StringToDate(oldDoorBoy.getDate()));
            peopleSalaryTextField.setText(String.valueOf(oldDoorBoy.getSalary()));
            peopleOtherComboBox.setValue(oldDoorBoy.getWorkPlace());
            doorBoyOldPos = oldDoorBoy.getWorkPlace();
        } else if (people.getClass().getName().equals("NursingHome.dataclass.Administrator")) {
            peopleType = "行政人员";
            peopleTypeComboBox.getItems().add("行政人员");
            ControllerUtils.initPeopleComboBox(peopleTypeComboBox, peopleType, peopleOtherComboBox, customerRankComboBox);
            customerRankComboBox.setVisible(false);
            customerRankLabel.setVisible(false);
            peopleTypeComboBox.setDisable(true);
            Administrator oldAdmin = ((Administrator) people);
            peopleIdLabel.setText(oldAdmin.getId());
            peopleNameTextField.setText(oldAdmin.getName());
            peopleBirthDatePicker.setValue(StringToDate(oldAdmin.getDate()));
            peopleSalaryTextField.setText(String.valueOf(oldAdmin.getSalary()));
            peopleOtherComboBox.setValue(oldAdmin.getPosition());
            adminOldPos = oldAdmin.getPosition();
        }

        // TODO 院长、总经理只能查看
        if (MANAGER_PRIV == 1) {
            peopleSalaryTextField.setEditable(false);
            peopleNameTextField.setEditable(false);
            peopleOtherComboBox.setDisable(true);
            peopleBirthDatePicker.setDisable(true);
            peopleTypeComboBox.setDisable(true);
        }
    }

    /**
     * 选择不同类型的员工时下拉框进行调整
     */
    public void selectPeopleType() {
        peopleType = peopleTypeComboBox.getValue();
        if (peopleTypeComboBox.getValue().equals("护工")) {
            peopleOtherLabel.setText("级别");
            peopleIdLabel.setText(getPeopleId(new Worker()));
            customerRankLabel.setVisible(true);
            customerRankComboBox.setVisible(true);
        } else if (peopleTypeComboBox.getValue().equals("医生")) {
            peopleOtherLabel.setText("科室");
            peopleIdLabel.setText(getPeopleId(new Doctor()));
            customerRankLabel.setVisible(false);
            customerRankComboBox.setVisible(false);
        } else if (peopleTypeComboBox.getValue().equals("勤杂人员")) {
            peopleOtherLabel.setText("工作部门");
            peopleIdLabel.setText(getPeopleId(new DoorBoy()));
            customerRankLabel.setVisible(false);
            customerRankComboBox.setVisible(false);
        } else if (peopleTypeComboBox.getValue().equals("行政人员")) {
            peopleOtherLabel.setText("职务");
            peopleIdLabel.setText(getPeopleId(new Administrator()));
            customerRankLabel.setVisible(false);
            customerRankComboBox.setVisible(false);
        }
        ControllerUtils.changeComboBox(peopleType, peopleOtherComboBox);
    }

    /**
     * 生成新的员工的编号
     *
     * @param object 员工对象
     * @return 新员工编号
     */
    public String getPeopleId(Object object) {
        String peopleId = "";
        // TODO 从历史员工表获取people(不同类型的职工)数量，加一得到新员工的ID
        Connection conn;
        Statement stmt;
        int N = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql = "";
            if (object.getClass().getName().equals("NursingHome.dataclass.Worker")) {
                sql = "SELECT count(*) FROM NursingHome.historical_staff WHERE staff_id LIKE 'W%';";
                peopleId = "W";
            } else if (object.getClass().getName().equals("NursingHome.dataclass.Doctor")) {
                sql = "SELECT count(*) FROM NursingHome.historical_staff WHERE staff_id LIKE 'D%';";
                peopleId = "D";
            } else if (object.getClass().getName().equals("NursingHome.dataclass.DoorBoy")) {
                sql = "SELECT count(*) FROM NursingHome.historical_staff WHERE staff_id LIKE 'B%';";
                peopleId = "B";
            } else if (object.getClass().getName().equals("NursingHome.dataclass.Administrator")) {
                sql = "SELECT count(*) FROM NursingHome.historical_staff WHERE staff_id LIKE 'A%';";
                peopleId = "A";
            }
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
        peopleId = peopleId + (N + 1);
        return peopleId;
    }

    /**
     * 保存员工信息
     */
    public void savePeopleInfo() {
        // TODO 获取信息并保存
        if (MANAGER_PRIV == 0) {
            // TODO 只有主管才能修改，否则直接退出
            boolean insertFailed = false;
            if (people.getClass().getName().equals("java.lang.Object")) {
                // TODO 新增员工
                //  部分员工在新增时需要自动生成密码
                isInsert = true;
                if (peopleTypeComboBox.getValue().equals("护工")) {
                    Worker worker = new Worker();
                    worker.setId(getPeopleId(worker));
                    worker.setDate(localDateToString(peopleBirthDatePicker.getValue()));
                    worker.setName(peopleNameTextField.getText());
                    try {
                        worker.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("[错误]薪水格式错误");
                    }
                    worker.setRank(peopleOtherComboBox.getValue());
                    System.out.println(worker.getWorkerInfo());

                    // TODO 在数据库中新增信息,在历史员工表中也要添加信息
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql1 = "INSERT INTO NursingHome.historical_staff VALUES ('" + worker.getId() + "','1')";
                        String sql = "INSERT INTO NursingHome.worker VALUES " + worker.getWorkerInfo();
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
                    people = (Object) worker;
                } else if (peopleTypeComboBox.getValue().equals("医生")) {
                    // TODO 新增医生时需要给出登陆密码
                    Doctor doctor = new Doctor();
                    doctor.setId(getPeopleId(doctor));
                    doctor.setDate(localDateToString(peopleBirthDatePicker.getValue()));
                    doctor.setName(peopleNameTextField.getText());
                    try {
                        doctor.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("[错误]薪水格式错误");
                    }
                    doctor.setMajor(peopleOtherComboBox.getValue());

                    // TODO 在数据库中新增医生信息
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql1 = "INSERT INTO NursingHome.historical_staff VALUES ('" + doctor.getId() + "','1')";
                        String sql = "INSERT INTO NursingHome.doctor VALUES " + doctor.getDoctorInfo();
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
                    // TODO 生成密码，并输出
                    String tempPassword = randomPassword();
                    showAlert("[提示]该医生的密码为：" + tempPassword);
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql = "INSERT INTO NursingHome.administrator VALUES ('" + doctor.getId() + "',2,'" + md5(tempPassword) + "');";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql);
                        stmt.close();
                        conn.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    people = (Object) doctor;
                } else if (peopleTypeComboBox.getValue().equals("勤杂人员")) {
                    DoorBoy doorBoy = new DoorBoy();
                    doorBoy.setId(getPeopleId(doorBoy));
                    doorBoy.setDate(localDateToString(peopleBirthDatePicker.getValue()));
                    doorBoy.setName(peopleNameTextField.getText());
                    try {
                        doorBoy.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("[错误]薪水格式错误");
                    }
                    doorBoy.setWorkPlace(peopleOtherComboBox.getValue());

                    // TODO 在数据库中新增信息
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql1 = "INSERT INTO NursingHome.historical_staff VALUES ('" + doorBoy.getId() + "','1')";
                        String sql = "INSERT INTO NursingHome.doorboy VALUES " + doorBoy.getDoorBoyInfo();
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

                    // TODO 只有前台人员需要生成密码，并输出
                    if (doorBoy.getWorkPlace().equals("前台")) {
                        String tempPassword = randomPassword();
                        showAlert("[提示]该前台人员的密码为：" + tempPassword);
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            String sql = "INSERT INTO NursingHome.manager VALUES ('" + doorBoy.getId() + "',3,'" + md5(tempPassword) + "');";
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

                    people = (Object) doorBoy;
                } else if (peopleTypeComboBox.getValue().equals("行政人员")) {
                    // TODO 新增行政人员

                    Administrator admin = new Administrator();
                    admin.setId(getPeopleId(admin));
                    admin.setDate(localDateToString(peopleBirthDatePicker.getValue()));
                    admin.setName(peopleNameTextField.getText());
                    try {
                        admin.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("[错误]薪水格式错误");
                    }
                    admin.setPosition(peopleOtherComboBox.getValue());

                    // TODO 判断是否已经有

                    Connection conn;
                    Statement stmt;
                    int N = 0;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql = "SELECT count(*) FROM NursingHome.administrator WHERE administrator_position='" + admin.getPosition() + "'";
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


                    if (N == 1) {
                        // TODO 已经有相关人员，比如总经理，此时不需要新增
                        insertFailed = true;
                        showAlert("[警告]该只为已经有工作人员，无法新增工作人员！");
                    } else {
                        // TODO 在数据库中新增信息
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            String sql1 = "INSERT INTO NursingHome.historical_staff VALUES ('" + admin.getId() + "','1')";
                            String sql = "INSERT INTO NursingHome.administrator VALUES " + admin.getAdminInfo();
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

                        String tempPassword = randomPassword();
                        showAlert("[提示]该行政人员的密码为：" + tempPassword);
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            String sql;
                            if (admin.getPosition().equals("主管")) {
                                sql = "INSERT INTO NursingHome.manager VALUES ('" + admin.getId() + "',0,'" + md5(tempPassword) + "');";
                            } else {
                                sql = "INSERT INTO NursingHome.manager VALUES ('" + admin.getId() + "',1,'" + md5(tempPassword) + "');";
                            }
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql);
                            stmt.close();
                            conn.close();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        people = (Object) admin;
                    }

                }
            } else {
                isInsert = false;
                // TODO 修改员工信息
                if (peopleTypeComboBox.getValue().equals("护工")) {
                    // TODO 修改护工信息
                    Worker worker = new Worker();
                    worker.setId(peopleIdLabel.getText());
                    worker.setDate(localDateToString(peopleBirthDatePicker.getValue()));
                    worker.setName(peopleNameTextField.getText());
                    try {
                        worker.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("[错误]薪水格式错误");
                    }
                    worker.setRank(peopleOtherComboBox.getValue());
                    System.out.println(worker.getWorkerInfo());
                    // TODO 在数据库中修改信息
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql = "UPDATE NursingHome.worker SET worker_name='" + worker.getName() + "', worker_date='" + worker.getDate() + "', worker_salary='" + worker.getSalary() + "', worker_rank='" + worker.getRank() + "' WHERE worker_id='" + worker.getId() + "'";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql);
                        stmt.close();
                        conn.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    people = (Object) worker;
                } else if (peopleTypeComboBox.getValue().equals("医生")) {
                    // TODO 修改医生信息
                    Doctor doctor = new Doctor();
                    doctor.setId(peopleIdLabel.getText());
                    doctor.setDate(localDateToString(peopleBirthDatePicker.getValue()));
                    doctor.setName(peopleNameTextField.getText());
                    try {
                        doctor.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("[错误]薪水格式错误");
                    }
                    doctor.setMajor(peopleOtherComboBox.getValue());

                    // TODO 在数据库中修改信息
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql = "UPDATE NursingHome.doctor SET doctor_name='" + doctor.getName() + "', doctor_date='" + doctor.getDate() + "', doctor_salary='" + doctor.getSalary() + "', doctor_major='" + doctor.getMajor() + "' WHERE doctor_id='" + doctor.getId() + "'";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql);
                        stmt.close();
                        conn.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    people = (Object) doctor;
                } else if (peopleTypeComboBox.getValue().equals("勤杂人员")) {
                    // TODO 修改勤杂人员信息
                    DoorBoy doorBoy = new DoorBoy();
                    doorBoy.setId(peopleIdLabel.getText());
                    doorBoy.setDate(localDateToString(peopleBirthDatePicker.getValue()));
                    doorBoy.setName(peopleNameTextField.getText());
                    try {
                        doorBoy.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("[错误]薪水格式错误");
                    }
                    String workerPlaceOld = doorBoyOldPos;
                    String workerPlaceNew = peopleOtherComboBox.getValue();
                    doorBoy.setWorkPlace(peopleOtherComboBox.getValue());

                    // TODO 在数据库中修改信息
                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql = "UPDATE NursingHome.doorboy SET doorboy_name='" + doorBoy.getName() + "', doorboy_date='" + doorBoy.getDate() + "', doorboy_salary='" + doorBoy.getSalary() + "', doorboy_workplace='" + doorBoy.getWorkPlace() + "' WHERE doorboy_id='" + doorBoy.getId() + "'";
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql);
                        stmt.close();
                        conn.close();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if (workerPlaceOld.equals("前台") && (!workerPlaceNew.equals("前台"))) {
                        // TODO 将勤杂人员从前台调到别处，需要取消该员工的登陆权限，销毁密码

                        // TODO 删除该人员的权限和密码
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            String sql = "DELETE FROM NursingHome.manager WHERE manager_id ='" + doorBoy.getId() + "'";
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql);
                            stmt.close();
                            conn.close();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        showAlert("[提示]该人员调整岗位后已无登陆权限");
                    } else if ((!workerPlaceOld.equals("前台")) && workerPlaceNew.equals("前台")) {
                        // TODO 将勤杂人员从别处调到前台，需要新增该用户的权限，登陆密码

                        // TODO 新增密码并输出，并记录
                        String tempPassword = randomPassword();
                        showAlert("[提示]该前台人员的密码为：" + tempPassword);
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            String sql = "INSERT INTO NursingHome.manager VALUES ('" + doorBoy.getId() + "',3,'" + md5(tempPassword) + "');";
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

                    people = (Object) doorBoy;
                } else if (peopleTypeComboBox.getValue().equals("行政人员")) {
                    // TODO 修改行政人员信息
                    Administrator admin = new Administrator();
                    admin.setId(peopleIdLabel.getText());
                    admin.setDate(localDateToString(peopleBirthDatePicker.getValue()));
                    admin.setName(peopleNameTextField.getText());
                    try {
                        admin.setSalary(Double.valueOf(peopleSalaryTextField.getText()));
                    } catch (NumberFormatException e) {
                        showAlert("[错误]薪水格式错误");
                    }
                    String adminPosOld = adminOldPos;
                    String adminPosNew = peopleOtherComboBox.getValue();

                    // TODO 当修改职位时，先判断该岗位是否有人，有的话则不能调
                    Connection conn;
                    Statement stmt;

                    // TODO 判断是否已经有
                    int N = 0;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql = "SELECT count(*) FROM NursingHome.administrator WHERE administrator_position='" + adminPosNew + "'";
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

                    if (N == 1) {
                        // TODO 该岗位已经有人，警告
                        showAlert("[警告]，该岗位已经有人，无法进行人事调动!");
                        // TODO 在数据库中修改其他非职位的信息
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            String sql = "UPDATE NursingHome.administrator SET administrator_name='" + admin.getName() + "', administrator_date='" + admin.getDate() + "', administrator_salary='" + admin.getSalary() + "', administrator_position='" + adminPosOld + "' WHERE administrator_id='" + admin.getId() + "'";
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql);
                            stmt.close();
                            conn.close();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        admin.setPosition(adminPosOld);
                    } else {
                        admin.setPosition(peopleOtherComboBox.getValue());

                        // TODO 在数据库中修改信息
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                            String sql = "UPDATE NursingHome.administrator SET administrator_name='" + admin.getName() + "', administrator_date='" + admin.getDate() + "', administrator_salary='" + admin.getSalary() + "', administrator_position='" + admin.getPosition() + "' WHERE administrator_id='" + admin.getId() + "'";
                            stmt = conn.createStatement();
                            stmt.executeUpdate(sql);
                            stmt.close();
                            conn.close();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        // TODO 修改权限

                        if (adminPosOld.equals("主管") && (!adminPosNew.equals("主管"))) {
                            // TODO 原来是主管现在调走，更改权限
                            try {
                                Class.forName("com.mysql.jdbc.Driver");
                                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                                String sql = "UPDATE NursingHome.manager SET manager_priv=1 WHERE manager_id ='" + admin.getId() + "'";
                                stmt = conn.createStatement();
                                stmt.executeUpdate(sql);
                                stmt.close();
                                conn.close();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else if ((!adminPosOld.equals("主管")) && adminPosNew.equals("主管")) {
                            // TODO 原来不是主管现在调回来，更改权限
                            try {
                                Class.forName("com.mysql.jdbc.Driver");
                                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                                String sql = "UPDATE NursingHome.manager SET manager_priv=1 WHERE manager_id='" + admin.getId() + "'";
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
                    }

                    people = (Object) admin;
                }
            }
            PeopleAdminUIController.setInfoTableView(isInsert, people, insertFailed);
        }
        people = new Object();
        getApp().floatStage.close();
    }

    /**
     * 返回人事管理界面
     */
    public void backToPeopleAdmin() {
        getApp().floatStage.close();
        people = new Object();
    }
}
