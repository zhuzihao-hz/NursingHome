package NursingHome.controller;

import NursingHome.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static NursingHome.ControllerUtils.*;
import static NursingHome.GlobalInfo.MANAGER_PASSWORD;

public class ChangePasswordUIController implements Initializable {
    private Main application;

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    @FXML
    private PasswordField newPassword1;
    @FXML
    private PasswordField newPassword2;
    private static String peopleid;

    public static String getPeopleid() {
        return peopleid;
    }

    public static void setPeopleid(String peopleid) {
        ChangePasswordUIController.peopleid = peopleid;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * 保存新设置的密码
     */
    public void confirm() {
        if (newPassword1.getText().equals(newPassword2.getText())) {
            String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(newPassword1.getText());

            if (matcher.matches()) {
                // TODO 往数据库中写入新密码

                Connection conn;
                Statement stmt;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                    String sql = "UPDATE NursingHome.manager SET manager_password='" + md5(newPassword1.getText()) + "' WHERE manager_id='" + peopleid + "'";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    stmt.close();
                    conn.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                showAlert("修改成功");
                getApp().floatStage.close();
            } else {
                if (newPassword1.getText().length() < 6) {
                    showAlert("新密码必须大于6位");
                } else if (newPassword1.getText().length() > 16) {
                    showAlert("新密码必须小于16位");
                } else {
                    showAlert("新密码必须是数字加字母的组合");
                }
            }
        } else {
            showAlert("两次密码输入不同");
        }
    }
}
