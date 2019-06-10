package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.Doctor;
import NursingHome.dataclass.DoorBoy;
import NursingHome.dataclass.Worker;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

import static NursingHome.GlobalInfo.*;

public class PeopleAdminUIController implements Initializable {
    private Main application;
    @FXML private TableColumn<StringProperty,String> workerID;
    @FXML private TableColumn<StringProperty,String> workerName;
    @FXML private TableColumn<StringProperty,String> workerAge;
    @FXML private TableColumn<StringProperty,Integer> workerSalary;
    @FXML private TableView<Worker> workerTableView;
    private ObservableList<Worker> workerObservableList= FXCollections.observableArrayList();

    @FXML private TableColumn<StringProperty,String> doctorID;
    @FXML private TableColumn<StringProperty,String> doctorName;
    @FXML private TableColumn<StringProperty,String> doctorAge;
    @FXML private TableColumn<StringProperty,String> doctorMajor;
    @FXML private TableColumn<StringProperty,Integer> doctorSalary;
    @FXML private TableView<Doctor> doctorTableView;
    private ObservableList<Doctor> doctorObservableList= FXCollections.observableArrayList();

    @FXML private TableColumn<StringProperty,String> doorBoyID;
    @FXML private TableColumn<StringProperty,String> doorBoyName;
    @FXML private TableColumn<StringProperty,String> doorBoyrAge;
    @FXML private TableColumn<StringProperty,String> doorBoyWorkPlace;
    @FXML private TableColumn<StringProperty,Integer> doorBoySalary;
    @FXML private TableView<DoorBoy> doorBoyTableView;
    private ObservableList<DoorBoy>doorBoyObservableList= FXCollections.observableArrayList();

    public void setApp(Main app) {
        this.application = app;
    }
    public Main getApp() {return this.application; }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        displayDoctor();
        bindDoctor();
        displayDoorBoy();
        bindDoorBoy();
        displayWorker();
        bindWorker();
    }

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
    public void personInfo() {
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


    public void bindDoctor(){

    }
    public void bindWorker(){

    }
    public void bindDoorBoy(){

    }

    public void displayDoctor(){

    }
    public void displayWorker(){

    }
    public void displayDoorBoy(){

    }

}
