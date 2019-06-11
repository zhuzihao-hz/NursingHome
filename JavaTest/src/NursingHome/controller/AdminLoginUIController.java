package NursingHome.controller;

import NursingHome.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.md5;
import static NursingHome.ControllerUtils.showAlert;

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
        if (check()){
            application.gotoAdminMainUI();
        }else{
            showAlert("[错误]用户名或密码错误！");
        }

    }

    public boolean check(){
        id=managerId.getText();
        password=managerPassword.getText();
        // TODO 从数据库中获得用户名密码，比较，放到GlobalInfo中,password1从数据库中获得
        String password1="";
        if (password1.equals(md5(password))){
            return true;
        }

        return true;
    }
}
