package NursingHome.controller;

import NursingHome.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.showtime;
import static NursingHome.GlobalInfo.*;

public class AdminMainUIController implements Initializable {
    private Main application;
    public void setApp(Main app) {
        this.application = app;
    }
    public Main getApp() {return this.application; }
    @FXML Text welcome;
    @FXML Text dateText;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        welcome.setText(MANAGER_NAME);
        showtime(dateText);
    }

    public void personalInfo() {
        getApp ().createPersonalInfoUI();
    }

    public void personInfoImage(){getApp().createPersonalInfoUI();}

    public void quit() {
        application.stage.close();
    }

    public void logout() throws Exception{
        MANAGER_ID = "";
        MANAGER_NAME = "";
        MANAGER_PRIV = -1;
        MANAGER_PASSWORD = "";
        application.stage.close();
        application.gotoAdminLoginUI();
    }

    public void movePeopleAdmin() throws Exception{
        application.stage.close();
        application.gotoPeopleAdminUI();
    }

    public void moveBusinessAdmin() throws Exception{
        application.stage.close();
        application.gotoBusinessAdminUI();
    }

    public void movePeopleAdminMenu() throws Exception{
        application.stage.close();
        application.gotoPeopleAdminUI();
    }

    public void moveBusinessAdminMenu() throws Exception{
        application.stage.close();
        application.gotoBusinessAdminUI();
    }

    public void aboutInfo() {
        application.createAboutInfoUI();
    }

}
