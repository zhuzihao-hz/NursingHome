package NursingHome.controller;

import NursingHome.Main;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

import static NursingHome.GlobalInfo.*;

public class BusinessAdminUIController implements Initializable {
    private Main application;
    @FXML TableColumn<StringProperty,String> busineddID;
    public void setApp(Main app) {
        this.application = app;
    }
    public Main getApp() {return this.application; }
    @Override
    public void initialize(URL url, ResourceBundle rb){ }

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
    public void personalInfo() {
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

}
