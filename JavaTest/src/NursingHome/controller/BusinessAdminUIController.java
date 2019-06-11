package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.Bed;
import NursingHome.dataclass.Customer;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static NursingHome.GlobalInfo.*;

public class BusinessAdminUIController implements Initializable {
    private Main application;
    @FXML private TableColumn<StringProperty,String> businessID;
    @FXML private TableColumn<StringProperty,String> businessName;
    @FXML private TableColumn<StringProperty,Integer> businessAge;
    @FXML private TableColumn<StringProperty,String> businessEnterTime;
    @FXML private TableColumn<StringProperty,Integer> businessRoomID;
    @FXML private TableColumn<StringProperty,Integer> businessBedID;
    @FXML private TableColumn<StringProperty,String> businessPhone;
    @FXML private TableColumn<StringProperty,String> businessCareWorkerID;
    @FXML private TableColumn<StringProperty,Integer> businessCareType;
    @FXML private TableColumn<StringProperty,String> businessRelationName;
    @FXML private TableColumn<StringProperty,String> businessRelation;
    @FXML private TableColumn<StringProperty,String> businessRelationPhone;
    @FXML private TableView<Customer> customerTableView;
    private ObservableList<Customer> customerObservableList= FXCollections.observableArrayList();

    @FXML private TableColumn<StringProperty,Integer> bedID;
    @FXML private TableColumn<StringProperty,Integer> bedRoomID;
    @FXML private TableColumn<StringProperty,Integer> bedRank;
    @FXML private TableColumn<StringProperty,Boolean> bedIsPeople;
    @FXML private TableView<Bed> bedTableView;
    private ObservableList<Bed> bedObservableList=FXCollections.observableArrayList();

    public void setApp(Main app) {
        this.application = app;
    }
    public Main getApp() {return this.application; }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        displayBusiness();
        bindBusiness();
        displayBed();
        bindBed();
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
    public void personalInfo() {
        getApp ().createPersonalInfoUI();
    }

    public void personInfoImage(){getApp().createPersonalInfoUI();}

    public void backToMainMenu() throws Exception{
        application.stage.close();
        application.gotoAdminMainUI();
    }
    public void aboutInfo() {
        application.createAboutInfoUI();
    }

    public void insertBusiness(){
        // TODO 新增客户
    }

    public void insertBed(){
        // TODO 新增床位
    }

    public void deleteBusiness(){
        // TODO 删除客户
    }

    public void deleteBed(){
        // TODO 删除床位
    }

    public void setBusinessInfo(){
        // TODO 设置客户信息
    }

    public void setBedInfo(){
        // TODO 设置床位信息
    }

    public void bindBusiness(){
        businessID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        businessName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        businessAge.setCellValueFactory(new PropertyValueFactory<>("customerAge"));
        businessBedID.setCellValueFactory(new PropertyValueFactory<>("customerBedID"));
        businessCareType.setCellValueFactory(new PropertyValueFactory<>("customerCareType"));
        businessEnterTime.setCellValueFactory(new PropertyValueFactory<>("customerEnterTime"));
        businessPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        businessRelation.setCellValueFactory(new PropertyValueFactory<>("customerRelation"));
        businessRelationName.setCellValueFactory(new PropertyValueFactory<>("customerRelationName"));
        businessRelationPhone.setCellValueFactory(new PropertyValueFactory<>("customerRelationPhone"));
        businessRoomID.setCellValueFactory(new PropertyValueFactory<>("customerRoomID"));
        businessCareWorkerID.setCellValueFactory(new PropertyValueFactory<>("customerCareWorker"));
        customerTableView.setVisible(true);
        customerTableView.setEditable(false);
        customerTableView.setTableMenuButtonVisible(true);
        customerTableView.setItems(customerObservableList);
    }

    public void bindBed(){
        bedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        bedRoomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        bedIsPeople.setCellValueFactory(new PropertyValueFactory<>("isPeople"));
        bedRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        bedTableView.setVisible(true);
        bedTableView.setEditable(false);
        bedTableView.setTableMenuButtonVisible(true);
        bedTableView.setItems(bedObservableList);
    }

    public void displayBusiness(){
        // TODO 显示客户信息
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            String sql="SELECT * FROM NursingHome.customer";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Customer customer=new Customer();
                customer.setCustomerId(rs.getString(1));
                customer.setCustomerName(rs.getString(2));
                customer.setCustomerAge(rs.getInt(3));
                customer.setCustomerEnterTime(rs.getString(4));
                customer.setCustomerRoomID(rs.getInt(5));
                customer.setCustomerBedID(rs.getInt(6));
                customer.setCustomerPhone(rs.getString(7));
                customer.setCustomerCareWorker(rs.getString(8));
                customer.setCustomerCareType(rs.getInt(9));
                customer.setCustomerRelationName(rs.getString(10));
                customer.setCustomerRelation(rs.getString(11));
                customer.setCustomerRelationPhone(rs.getString(12));
                customerObservableList.add(customer);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("客户数据导入成功！");
    }

    public void displayBed(){
        // TODO 显示床位信息
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            String sql="SELECT * FROM NursingHome.bed";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Bed bed=new Bed();
                bed.setId(rs.getString(1));
                bed.setRoomID(rs.getInt(2));
                bed.setIsPeople(rs.getBoolean(3));
                bed.setRank(rs.getInt(4));
                bedObservableList.add(bed);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("床位数据导入成功！");
    }

}
