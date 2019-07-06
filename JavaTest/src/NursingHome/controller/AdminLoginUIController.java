package NursingHome.controller;

import NursingHome.GlobalInfo;
import NursingHome.Main;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;
import static NursingHome.SQLMethod.*;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class AdminLoginUIController implements Initializable {
    private Main application;
    @FXML
    private JFXPasswordField managerPassword;
    @FXML
    private JFXTextField managerId;
    private String password;
    private String id;
    private Image image;

    public AdminLoginUIController() {
    }

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void close() {
        getApp().stage.close();
    }

    /**
     * 登陆
     */
    public void login() {
        if (check()) {
            downloadPic();
            application.gotoAdminMainUI();
        } else {
            showAlert("[错误]用户名或密码错误！");
        }
    }

    /**
     * 检查密码是否正确
     *
     * @return 密码是否正确
     */
    public boolean check() {
        id = managerId.getText();
        password = managerPassword.getText();
        // TODO 从数据库中获得用户名密码，比较，放到GlobalInfo中,password1从数据库中获得
        int tempPri = 0;
        String password1 = "";
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql = "SELECT * FROM NursingHome.manager WHERE manager_id='" + id + "'";
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    tempPri = rs.getInt(2);
                    password1 = rs.getString(3);
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

        if (password1.equals(md5(password))) {
            GlobalInfo.MANAGER_PRIV = tempPri;
            GlobalInfo.MANAGER_ID = id;
            GlobalInfo.MANAGER_PASSWORD = password1;
            // TODO 确认身份的情况下，
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                if (conn.getTransactionIsolation() == Connection.TRANSACTION_REPEATABLE_READ) {
                    conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                    String sql = "";
                    if (id.charAt(0) == 'D') {
                        sql = "SELECT doctor_name FROM NursingHome.doctor WHERE doctor_id='" + id + "'";
                    } else if (id.charAt(0) == 'A') {
                        sql = "SELECT administrator_name FROM NursingHome.administrator WHERE administrator_id='" + id + "'";
                    } else if (id.charAt(0) == 'B') {
                        sql = "SELECT doorboy_name FROM NursingHome.doorboy WHERE doorboy_id='" + id + "'";
                    }
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        GlobalInfo.MANAGER_NAME = rs.getString(1);
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
            return true;
        } else {
            return false;
        }
    }
}
