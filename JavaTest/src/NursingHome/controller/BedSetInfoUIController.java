package NursingHome.controller;

import NursingHome.ControllerUtils;
import NursingHome.Main;
import NursingHome.dataclass.Bed;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.showAlert;

public class BedSetInfoUIController implements Initializable {
    private Main application;
    @FXML private TextField bedIdTextField;
    @FXML private TextField bedRoomTextField;
    @FXML private JFXComboBox<String> bedUsedComboBox;
    @FXML private JFXComboBox<String> bedRankComboBox;
    public static boolean isInsert=true;
    private static Bed bed;

    public void setApp(Main app) { this.application = app; }
    public Main getApp() {return this.application; }

    public static Bed getBed() { return bed; }
    public static void setBed(Bed bed) { BedSetInfoUIController.bed = bed; }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ControllerUtils.initBedComboBox(bedUsedComboBox,bedRankComboBox);
        if (!isInsert){
            bedIdTextField.setText(bed.getId());
            bedRoomTextField.setText(bed.getRoomID());
            bedUsedComboBox.setValue(bed.getIsPeople());
            bedRankComboBox.setValue(bed.getRank());
            bedIdTextField.setEditable(false);
            bedRoomTextField.setEditable(false);
        }
    }


    public void savePeopleInfo() {
        Bed bed=new Bed();
        // TODO 关于新增床位需要再考虑一下？？？？？？？？？
        try {
            Integer.parseInt(bedIdTextField.getText());
        }catch (NumberFormatException e){
            showAlert("[错误]床位格式错误");
        }
        try {
            Integer.parseInt(bedRoomTextField.getText());
        }catch (NumberFormatException e){
            showAlert("[错误]房号格式错误");
        }
        bed.setId(bedIdTextField.getText());
        bed.setRoomID(bedRoomTextField.getText());
        bed.setIsPeople(bedUsedComboBox.getValue());
        bed.setRank(bedRankComboBox.getValue());
        Connection conn;
        Statement stmt;

        if (!isInsert){
            // TODO 修改
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                String sql="UPDATE NursingHome.bed SET bed_rank='"+bed.getRank()+"', bed_ispeople='"+bed.getIsPeople()+"' WHERE bed_id = '"+bed.getId()+"' AND bed_roomid = '"+bed.getRoomID()+"'";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("新增床位成功！");
        }else{
            // TODO 新增
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
                String sql="INSERT INTO NursingHome.bed VALUES "+bed.getBedInfo();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("新增床位成功！");
        }
        getApp().floatStage.close();
    }

    public void backToPeopleAdmin() {
        getApp().floatStage.close();
        isInsert=true;
    }
}
