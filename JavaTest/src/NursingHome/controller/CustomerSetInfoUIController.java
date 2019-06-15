package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Customer;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerSetInfoUIController implements Initializable {
    public static boolean isInsert=true;
    private Main application;
    private static Customer customer;
    @FXML private TextField customerIdTextField;
    @FXML private TextField customerNameTextField;
    @FXML private TextField customerRoomIdTextField;
    @FXML private TextField customerBedIdTextField;
    @FXML private TextField customerPhoneTextField;
    @FXML private TextField customerCareWorkerIdTextField;
    @FXML private TextField customerRelationNameTextField;
    @FXML private TextField customerRelationTextField;
    @FXML private TextField customerRelationPhoneTextField;
    @FXML private ComboBox<Integer> customerAgeComboBox;
    @FXML private ComboBox<Integer> customerCareTypeComboBox;
    @FXML private JFXDatePicker customerEnterTimeDatePicker;

    public void setApp(Main app) { this.application = app; }
    public Main getApp() {return this.application; }

    public static Customer getCustomer() { return customer; }
    public static void setCustomer(Customer customer) { CustomerSetInfoUIController.customer = customer; }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ControllerUtils.initCustomerComboBox(customerAgeComboBox,customerCareTypeComboBox);
        if (!isInsert){
            customerIdTextField.setText(customer.getCustomerId());
            customerIdTextField.setEditable(false);
            customerNameTextField.setText(customer.getCustomerName());
            customerRoomIdTextField.setText(String.valueOf(customer.getCustomerRoomID()));
            customerBedIdTextField.setText(String.valueOf(customer.getCustomerBedID()));
            customerPhoneTextField.setText(customer.getCustomerPhone());
            customerCareWorkerIdTextField.setText(customer.getCustomerCareWorker());
            customerRelationNameTextField.setText(customer.getCustomerRelationName());
            customerRelationTextField.setText(customer.getCustomerRelation());
            customerRelationPhoneTextField.setText(customer.getCustomerRelationPhone());
        }
    }

    public void saveCustomerInfo() {
        // TODO 保存客户信息
    }

    public void fillCustomerInfo(){

    }

    public void backToPeopleAdmin(ActionEvent actionEvent) {
        getApp().floatStage.close();
        LocalDate localDate;
        localDate=customerEnterTimeDatePicker.getValue();
        System.out.println(localDate);
        String string=String.valueOf(localDate);
        System.out.println(string);
    }
}
