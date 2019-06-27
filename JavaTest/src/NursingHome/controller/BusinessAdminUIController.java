package NursingHome.controller;

import NursingHome.Main;
import NursingHome.dataclass.*;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.showtime;
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
    private static ObservableList<Customer> customerObservableList= FXCollections.observableArrayList();
    @FXML private Tab customerTab;

    @FXML private TableColumn<StringProperty,Integer> bedID;
    @FXML private TableColumn<StringProperty,Integer> bedRoomID;
    @FXML private TableColumn<StringProperty,Integer> bedRank;
    @FXML private TableColumn<StringProperty,Boolean> bedIsPeople;
    @FXML private TableView<Bed> bedTableView;
    private static ObservableList<Bed> bedObservableList=FXCollections.observableArrayList();
    @FXML private Tab bedTab;
    @FXML Text dateText;
    @FXML private Label nameLabel;

    public void setApp(Main app) {
        this.application = app;
    }
    public Main getApp() {return this.application; }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        nameLabel.setText(MANAGER_NAME);
        showtime(dateText);
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
        application.createCustomerSetInfoUI();
    }

    public void deleteBusiness(){
        // TODO 删除客户
        List<Customer> customerSelected = customerTableView.getSelectionModel().getSelectedItems();
        for(int i=0;i<customerSelected.size();i++){
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                String sql="DELETE FROM NursingHome.customer WHERE customer_id='"+customerSelected.get(i).getId()+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("删除Customer成功");
    }

    public void setBusinessInfo(){
        // TODO 设置客户信息
        //  需要自动生成一些信息，如自动分配空余的床位等
        CustomerSetInfoUIController.isInsert=false;
        List<Customer> customerSelected = customerTableView.getSelectionModel().getSelectedItems();
        CustomerSetInfoUIController.setCustomer(customerSelected.get(0));
        application.createCustomerSetInfoUI();
    }

    public void insertBed(){
        // TODO 新增床位
        application.createBedSetInfoUI();
    }

    public void deleteBed(){
        // TODO 删除床位
        List<Bed> bedSelected = bedTableView.getSelectionModel().getSelectedItems();
        for(int i=0;i<bedSelected.size();i++){
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                String sql="DELETE FROM NursingHome.bed WHERE bed_id='"+bedSelected.get(i).getId()+"'"+" AND bed_roomid='"+bedSelected.get(i).getRoomID()+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // TODO 在tableView中也把员工信息去掉
        for (int i=0;i<bedSelected.size();i++){
            for (int j=0;j<bedObservableList.size();j++){
                if (bedSelected.get(i).getId().equals(bedObservableList.get(j).getId())){
                    bedObservableList.remove(j);
                    break;
                }
            }
        }
        System.out.println("删除Bed成功");
    }

    public void setBedInfo(){
        // TODO 设置床位信息
        BedSetInfoUIController.isInsert=false;
        List<Bed> bedSelected = bedTableView.getSelectionModel().getSelectedItems();
        BedSetInfoUIController.setBed((bedSelected.get(0)));
        getApp().createBedSetInfoUI();
    }

    public void clickIntoDetail(){
        // TODO 双击进入修改信息界面
        if (customerTab.isSelected()){
            // TODO 选中workerTab时，修改
            customerTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Customer> customerSelected = customerTableView.getSelectionModel().getSelectedItems();
            customerTableView.setOnMouseClicked(event -> {
                if (event.getClickCount()==2&&customerSelected.size()==1){
                    try {
                        CustomerSetInfoUIController.isInsert=false;
                        CustomerSetInfoUIController.setCustomer((customerSelected.get(0)));
                        getApp().createCustomerSetInfoUI();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        } else if (bedTab.isSelected()){
            // TODO 选中doorBoyTab时，修改
            bedTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Bed> bedSelected = bedTableView.getSelectionModel().getSelectedItems();
            bedTableView.setOnMouseClicked(event -> {
                if (event.getClickCount()==2&&bedSelected.size()==1){
                    try {
                        BedSetInfoUIController.isInsert=false;
                        BedSetInfoUIController.setBed((bedSelected.get(0)));
                        getApp().createBedSetInfoUI();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void setInfoTableView(boolean isBed,boolean isInsert,Object object){
        if (isBed){
            Bed bed=(Bed)object;
            if (isInsert){
                bedObservableList.add(bed);
            }else{
                for (int i=0;i<bedObservableList.size();i++){
                    if (bedObservableList.get(i).equal(bed)){
                        bedObservableList.get(i).setRank(bed.getRank());
                        bedObservableList.get(i).setIsPeople(bed.getIsPeople());
                    }
                }
            }
        }else{
            Customer customer=(Customer)object;
            if (isInsert){
                customerObservableList.add(customer);
            }else {
                for (int i=0;i<customerObservableList.size();i++){
                    if (customerObservableList.get(i).getId().equals(customer.getId())){
                        customerObservableList.get(i).setAge(customer.getAge());
                        customerObservableList.get(i).setBedID(customer.getBedID());
                        customerObservableList.get(i).setCareType(customer.getCareType());
                        customerObservableList.get(i).setCareWorker(customer.getCareWorker());
                        customerObservableList.get(i).setEnterTime(customer.getEnterTime());
                        customerObservableList.get(i).setName(customer.getName());
                        customerObservableList.get(i).setPhone(customer.getPhone());
                        customerObservableList.get(i).setRelation(customer.getRelation());
                        customerObservableList.get(i).setRelationName(customer.getRelationName());
                        customerObservableList.get(i).setRelationPhone(customer.getRelationPhone());
                        customerObservableList.get(i).setRoomID(customer.getRoomID());
                    }
                }
            }
        }
    }

    public void bindBusiness(){
        businessID.setCellValueFactory(new PropertyValueFactory<>("id"));
        businessName.setCellValueFactory(new PropertyValueFactory<>("name"));
        businessAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        businessBedID.setCellValueFactory(new PropertyValueFactory<>("bedID"));
        businessCareType.setCellValueFactory(new PropertyValueFactory<>("careType"));
        businessEnterTime.setCellValueFactory(new PropertyValueFactory<>("enterTime"));
        businessPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        businessRelation.setCellValueFactory(new PropertyValueFactory<>("relation"));
        businessRelationName.setCellValueFactory(new PropertyValueFactory<>("relationName"));
        businessRelationPhone.setCellValueFactory(new PropertyValueFactory<>("relationPhone"));
        businessRoomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        businessCareWorkerID.setCellValueFactory(new PropertyValueFactory<>("careWorker"));
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
                customer.setId(rs.getString(1));
                customer.setName(rs.getString(2));
                customer.setAge(rs.getInt(3));
                customer.setEnterTime(rs.getString(4));
                customer.setRoomID(rs.getInt(5));
                customer.setBedID(rs.getInt(6));
                customer.setPhone(rs.getString(7));
                customer.setCareWorker(rs.getString(8));
                customer.setCareType(rs.getInt(9));
                customer.setRelationName(rs.getString(10));
                customer.setRelation(rs.getString(11));
                customer.setRelationPhone(rs.getString(12));
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
                bed.setRoomID(rs.getString(2));
                bed.setIsPeople(rs.getString(3));
                bed.setRank(rs.getString(4));
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
