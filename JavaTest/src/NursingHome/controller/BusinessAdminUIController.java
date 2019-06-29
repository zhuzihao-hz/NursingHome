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

    @FXML private TableColumn<StringProperty,String> bedID;
    @FXML private TableColumn<StringProperty,String> bedRoomID;
    @FXML private TableColumn<StringProperty,String> bedStatus;
    @FXML private TableView<Bed> bedTableView;
    private static ObservableList<Bed> bedObservableList=FXCollections.observableArrayList();
    @FXML private Tab bedTab;

    @FXML private TableColumn<StringProperty,String> roomID;
    @FXML private TableColumn<StringProperty,String> roomRank;
    @FXML private TableColumn<StringProperty,String> roomTotalBed;
    @FXML private TableColumn<StringProperty,String> roomFreeBed;
    @FXML private TableView<Room> roomTableView;
    private static ObservableList<Room> roomObservableList=FXCollections.observableArrayList();
    @FXML private Tab roomTab;

    @FXML private TableColumn<StringProperty,String> recordID;
    @FXML private TableColumn<StringProperty,String> recordCustomerID;
    @FXML private TableColumn<StringProperty,String> recordCustomerName;
    @FXML private TableColumn<StringProperty,String> recordDoctorID;
    @FXML private TableColumn<StringProperty,String> recordDate;
    @FXML private TableColumn<StringProperty,String> recordContext;
    @FXML private TableView<Record> recordTableView;
    private static ObservableList<Record> recordObservableList=FXCollections.observableArrayList();
    @FXML private Tab recordTab;

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
        displayRoom();
        bindRoom();
        displayRecord();
        bindRecord();
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
        application.createCustomerInsertInfoUI();
    }

    public void deleteBusiness(){
        // TODO 删除客户，并且在record表中将该客户的档案记录一起删除
        List<Customer> customerSelected = customerTableView.getSelectionModel().getSelectedItems();
        for(int i=0;i<customerSelected.size();i++){
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                String sql1="DELETE FROM NursingHome.record WHERE customer_id='"+customerSelected.get(i).getId()+"'";
                String sql="DELETE FROM NursingHome.customer WHERE customer_id='"+customerSelected.get(i).getId()+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql1);
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (int i=0;i<customerSelected.size();i++){
            for (int j=0;j<customerObservableList.size();j++){
                if (customerSelected.get(i).getId().equals(customerObservableList.get(j).getId())){
                    customerObservableList.remove(j);
                    // TODO 将该客户的档案记录也删除
                    for (int k=0;k<recordObservableList.size();k++){
                        if (recordObservableList.get(k).getCustomerId().equals(customerSelected.get(i).getId())){
                            recordObservableList.remove(k);
                        }
                    }
                }
            }
        }
        System.out.println("删除Customer成功");
    }

    public void setBusinessInfo(){
        // TODO 设置客户信息
        //  需要自动生成一些信息，如自动分配空余的床位等
        List<Customer> customerSelected = customerTableView.getSelectionModel().getSelectedItems();
        CustomerSetInfoUIController.setCustomer(customerSelected.get(0));
        application.createCustomerSetInfoUI();
    }

    public void insertRecord(){
        // TODO 新增记录
        application.createRecordSetInfoUI();
        System.out.println("新增档案记录成功！");
    }

    public void lookRecordInfo(){
        List<Record> recordSelected = recordTableView.getSelectionModel().getSelectedItems();
        RecordLookInfoUIController.setRecord(recordSelected.get(0));
        getApp().createRecordLookInfoUI();
    }

    public void clickIntoDetail(){
        // TODO 双击进入修改信息界面
        if (customerTab.isSelected()){
            // TODO 选中customerTab时，修改
            customerTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Customer> customerSelected = customerTableView.getSelectionModel().getSelectedItems();
            customerTableView.setOnMouseClicked(event -> {
                if (event.getClickCount()==2&&customerSelected.size()==1){
                    try {
                        CustomerSetInfoUIController.setCustomer((customerSelected.get(0)));
                        getApp().createCustomerSetInfoUI();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }else if (recordTab.isSelected()){
            // TODO 点击recordTab时查看Record详细信息
            recordTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            List<Record> recordSelected = recordTableView.getSelectionModel().getSelectedItems();
            recordTableView.setOnMouseClicked(event -> {
                if (event.getClickCount()==2&&recordSelected.size()==1){
                    try {
                        RecordLookInfoUIController.setRecord((recordSelected.get(0)));
                        getApp().createRecordLookInfoUI();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void setInfoTableView(boolean isRecord,boolean isInsert,Object object){
        if (isRecord){
            Record record=(Record)object;
            if (isInsert){
                recordObservableList.add(record);
            }
        }else{
            Customer customer=(Customer)object;
            if (isInsert){
                System.out.println("Test");
                customerObservableList.add(customer);
            }else {
                for (int i=0;i<customerObservableList.size();i++){
                    if (customerObservableList.get(i).getId().equals(customer.getId())){
                        customerObservableList.get(i).setAge(customer.getAge());
                        customerObservableList.get(i).setBedID(customer.getBedID());
                        customerObservableList.get(i).setRank(customer.getRank());
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
        businessCareType.setCellValueFactory(new PropertyValueFactory<>("rank"));
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
        bedStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        bedTableView.setVisible(true);
        bedTableView.setEditable(false);
        bedTableView.setTableMenuButtonVisible(true);
        bedTableView.setItems(bedObservableList);
    }

    public void bindRoom(){
        roomID.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        roomTotalBed.setCellValueFactory(new PropertyValueFactory<>("totalBed"));
        roomFreeBed.setCellValueFactory(new PropertyValueFactory<>("freeBed"));
        roomTableView.setVisible(true);
        roomTableView.setEditable(false);
        roomTableView.setTableMenuButtonVisible(true);
        roomTableView.setItems(roomObservableList);
    }

    public void bindRecord(){
        recordID.setCellValueFactory(new PropertyValueFactory<>("id"));
        recordCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        recordCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        recordDoctorID.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        recordDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        recordContext.setCellValueFactory(new PropertyValueFactory<>("context"));
        recordTableView.setVisible(true);
        recordTableView.setEditable(false);
        recordTableView.setTableMenuButtonVisible(true);
        recordTableView.setItems(recordObservableList);
    }

    public void displayBusiness(){
        customerObservableList.clear();
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
                customer.setRoomID(rs.getString(5));
                customer.setBedID(rs.getString(6));
                customer.setPhone(rs.getString(7));
                customer.setCareWorker(rs.getString(8));
                customer.setRank(rs.getInt(9));
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
        bedObservableList.clear();
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
                bed.setStatus(rs.getInt(3));
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

    public void displayRoom(){
        roomObservableList.clear();
        // TODO 显示房间信息
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            String sql="SELECT * FROM NursingHome.room";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Room room=new Room();
                room.setId(rs.getString(1));
                room.setRank(rs.getString(2));
                room.setTotalBed(rs.getInt(3));
                room.setFreeBed(rs.getInt(4));
                roomObservableList.add(room);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("房间数据导入成功！");
    }

    public void displayRecord(){
        recordObservableList.clear();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            String sql="SELECT * FROM NursingHome.record";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Record record=new Record();
                record.setId(rs.getString(1));
                record.setCustomerId(rs.getString(2));
                record.setCustomerName(rs.getString(3));
                record.setDoctorId(rs.getString(4));
                record.setDate(rs.getString(5));
                record.setContext(rs.getString(6));
                recordObservableList.add(record);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("记录数据导入成功！");
    }
}
