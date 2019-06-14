package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.Customer;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerSetInfoUIController implements Initializable {
    public static boolean isInsert=true;
    private Main application;

    public void setApp(Main app) { this.application = app; }
    public Main getApp() {return this.application; }

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public static void setCustomer(Customer customer) {
    }
}
