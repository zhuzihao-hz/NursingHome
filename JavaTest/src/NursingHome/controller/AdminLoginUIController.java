package NursingHome.controller;

import NursingHome.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginUIController implements Initializable {
    private Main application;
    @FXML private JFXPasswordField managerPassword;
    @FXML private JFXTextField managerId;
    @FXML private JFXButton loginButton;
    private String password;
    private String id;
    public AdminLoginUIController() {
    }

    public void setApp(Main app) {
        this.application = app;
    }
    public Main getApp() {return this.application; }
    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void close() {
        getApp().stage.close();
    }

    public void login() throws Exception{
        id=managerId.getText();
        password=managerPassword.getText();

        // TODO 从数据库中获得用户名密码，比较，放到GlobalInfo中

        application.gotoAdminMainUI();
    }
}
