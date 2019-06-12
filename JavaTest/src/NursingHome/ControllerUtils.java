package NursingHome;

import NursingHome.controller.PeopleSetInfoUIController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ControllerUtils {
    public static void showAlert(String message) {
        Stage window = new Stage();
        window.setAlwaysOnTop(true);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setMinWidth(300);
        window.setMinHeight(150);
        JFXButton button = new JFXButton("确定");
        button.setOnAction(e -> window.close());
        Label label = new Label(message);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public static void initPeopleComboBox(ComboBox<String> peopleTypeComboBox,String peopleType,ComboBox<Integer> peopleAgeComboBox,ComboBox<String> peopleOtherComboBox) {
        peopleTypeComboBox.getItems().add("护工");
        peopleTypeComboBox.getItems().add("医生");
        peopleTypeComboBox.getItems().add("勤杂人员");
        peopleTypeComboBox.setValue(peopleType);
        for (int i=18;i<61;i++){
            peopleAgeComboBox.getItems().add(i);
        }
        peopleAgeComboBox.setValue(30);
        if (PeopleSetInfoUIController.peopleType.equals("护工")){
            peopleOtherComboBox.getItems().add("高级护工");
            peopleOtherComboBox.getItems().add("中级护工");
            peopleOtherComboBox.getItems().add("低级护工");
            peopleOtherComboBox.getItems().add("实习护工");
            peopleOtherComboBox.setValue("低级护工");
        }else if (PeopleSetInfoUIController.peopleType.equals("医生")){
            peopleOtherComboBox.getItems().add("全科");
            peopleOtherComboBox.getItems().add("外科");
            peopleOtherComboBox.getItems().add("内科");
            peopleOtherComboBox.getItems().add("实习");
            peopleOtherComboBox.setValue("全科");
        }else if (PeopleSetInfoUIController.peopleType.equals("勤杂人员")){
            peopleOtherComboBox.getItems().add("厨房");
            peopleOtherComboBox.getItems().add("保洁");
            peopleOtherComboBox.getItems().add("门卫");
            peopleOtherComboBox.getItems().add("前台");
            peopleOtherComboBox.setValue("厨房");
        }
    }
    public static void changeComboBox(String peopleType,ComboBox<String> peopleOtherComboBox){
        peopleOtherComboBox.getItems().clear();
        if (peopleType.equals("护工")){
            peopleOtherComboBox.getItems().add("高级护工");
            peopleOtherComboBox.getItems().add("中级护工");
            peopleOtherComboBox.getItems().add("低级护工");
            peopleOtherComboBox.getItems().add("实习护工");
            peopleOtherComboBox.setValue("低级护工");
        }else if (peopleType.equals("医生")){
            peopleOtherComboBox.getItems().add("全科");
            peopleOtherComboBox.getItems().add("外科");
            peopleOtherComboBox.getItems().add("内科");
            peopleOtherComboBox.getItems().add("实习");
            peopleOtherComboBox.setValue("全科");
        }else {
            peopleOtherComboBox.getItems().add("厨房");
            peopleOtherComboBox.getItems().add("保洁");
            peopleOtherComboBox.getItems().add("门卫");
            peopleOtherComboBox.getItems().add("前台");
            peopleOtherComboBox.setValue("厨房");
        }
    }
}
