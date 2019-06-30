package NursingHome.controller;

import NursingHome.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    public Main getApp() {return this.application; }

    @FXML private PasswordField oldPassword1;
    @FXML private PasswordField newPassword1;
    @FXML private PasswordField newPassword2;
    @FXML private Button confirmBtn;
    private String oldPassword;
    private static String peopleid;

    public static String getPeopleid() { return peopleid; }
    public static void setPeopleid(String peopleid) { ChangePasswordUIController.peopleid = peopleid; }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            String sql="SELECT manager_password FROM NursingHome.manager WHERE manager_id='"+peopleid+"'";
            stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery(sql);
            if (rs.next()){
                oldPassword=rs.getString(1);
            }
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void confirm(ActionEvent actionEvent) {
        System.out.println(oldPassword);
        System.out.println(MANAGER_PASSWORD);
        if(oldPassword.equals(md5(oldPassword1.getText()))){
            if(newPassword1.getText().equals(newPassword2.getText()))
            {
                String reg ="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(newPassword1.getText());
                if (newPassword1.getText().equals(oldPassword1.getText())){
                    showAlert("新密码必须与旧密码不同");
                }
                else if (matcher.matches()) {
                    // TODO 往数据库中写入新密码

                    Connection conn;
                    Statement stmt;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                        String sql="UPDATE NursingHome.manager SET manager_password='"+md5(newPassword1.getText())+"' WHERE manager_id='"+peopleid+"'";
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
                }
                else{
                    if (newPassword1.getText().length()<6){
                        showAlert("新密码必须大于6位");
                    }else if (newPassword1.getText().length()>16){
                        showAlert("新密码必须小于16位");
                    }else{
                        showAlert("新密码必须是数字加字母的组合");
                    }
                }
            }
            else{
                showAlert("两次密码输入不同");
            }
        }
        else{
            showAlert("旧密码输入错误");
        }
    }
}
