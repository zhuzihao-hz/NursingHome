package NursingHome.controller;

import NursingHome.Main;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class BedSetInfoUIController implements Initializable {
    private Main application;
    @FXML private TextField bedIdTextField;
    @FXML private TextField bedRoomTextField;
    @FXML private JFXComboBox<Integer> bedUsedComboBox;
    @FXML private JFXComboBox<Integer> bedRankComboBox;

    public void setApp(Main app) { this.application = app; }
    public Main getApp() {return this.application; }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }


    public void savePeopleInfo() {
    }

    public void backToPeopleAdmin() {
        getApp().floatStage.close();
    }
}
