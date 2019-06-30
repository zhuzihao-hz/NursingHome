package NursingHome;

import NursingHome.controller.PeopleSetInfoUIController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Random;

import static NursingHome.GlobalInfo.MANAGER_PRIV;

public class ControllerUtils {
    public static String MYSQL_URL = "jdbc:mysql://localhost:3306";
    public static String MYSQL_USER = "root";
    public static String MYSQL_PASSWORD = "12345678";

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

    public static void initPeopleComboBox(ComboBox<String> peopleTypeComboBox, String peopleType, ComboBox<Integer> peopleAgeComboBox, ComboBox<String> peopleOtherComboBox) {
        peopleTypeComboBox.getItems().add("护工");
        peopleTypeComboBox.getItems().add("医生");
        peopleTypeComboBox.getItems().add("勤杂人员");
        if (MANAGER_PRIV == 0) {
            peopleTypeComboBox.getItems().add("行政人员");
        }
        peopleTypeComboBox.setValue(peopleType);
        peopleTypeComboBox.setEditable(false);
        for (int i = 18; i < 61; i++) {
            peopleAgeComboBox.getItems().add(i);
        }
        peopleAgeComboBox.setValue(30);
        if (PeopleSetInfoUIController.peopleType.equals("护工")) {
            peopleOtherComboBox.getItems().add("高级护工");
            peopleOtherComboBox.getItems().add("中级护工");
            peopleOtherComboBox.getItems().add("低级护工");
            peopleOtherComboBox.getItems().add("实习护工");
            peopleOtherComboBox.setValue("低级护工");
        } else if (PeopleSetInfoUIController.peopleType.equals("医生")) {
            peopleOtherComboBox.getItems().add("全科");
            peopleOtherComboBox.getItems().add("外科");
            peopleOtherComboBox.getItems().add("内科");
            peopleOtherComboBox.getItems().add("实习");
            peopleOtherComboBox.setValue("全科");
        } else if (PeopleSetInfoUIController.peopleType.equals("勤杂人员")) {
            peopleOtherComboBox.getItems().add("厨房");
            peopleOtherComboBox.getItems().add("保洁");
            peopleOtherComboBox.getItems().add("门卫");
            peopleOtherComboBox.getItems().add("前台");
            peopleOtherComboBox.setValue("厨房");
        } else {
            peopleOtherComboBox.getItems().add("主管");
            peopleOtherComboBox.getItems().add("总经理");
            peopleOtherComboBox.getItems().add("院长");
            peopleOtherComboBox.setValue("主管");
        }
    }

    public static void changeComboBox(String peopleType, ComboBox<String> peopleOtherComboBox) {
        peopleOtherComboBox.getItems().clear();
        if (peopleType.equals("护工")) {
            peopleOtherComboBox.getItems().add("高级护工");
            peopleOtherComboBox.getItems().add("中级护工");
            peopleOtherComboBox.getItems().add("低级护工");
            peopleOtherComboBox.getItems().add("实习护工");
            peopleOtherComboBox.setValue("低级护工");
        } else if (peopleType.equals("医生")) {
            peopleOtherComboBox.getItems().add("全科");
            peopleOtherComboBox.getItems().add("外科");
            peopleOtherComboBox.getItems().add("内科");
            peopleOtherComboBox.getItems().add("实习");
            peopleOtherComboBox.setValue("全科");
        } else if (peopleType.equals("勤杂人员")) {
            peopleOtherComboBox.getItems().add("厨房");
            peopleOtherComboBox.getItems().add("保洁");
            peopleOtherComboBox.getItems().add("门卫");
            peopleOtherComboBox.getItems().add("前台");
            peopleOtherComboBox.setValue("厨房");
        } else if (peopleType.equals("行政人员")) {
            peopleOtherComboBox.getItems().add("主管");
            peopleOtherComboBox.getItems().add("总经理");
            peopleOtherComboBox.getItems().add("院长");
            peopleOtherComboBox.setValue("主管");
        }
    }

    public static String localDateToString(LocalDate localDate) {
        return String.valueOf(localDate);
    }

    public static LocalDate StringToDate(String string) {
        int year, month, day;
        year = Integer.parseInt(string.substring(0, 4));
        month = Integer.parseInt(string.substring(5, 7));
        day = Integer.parseInt(string.substring(8, 10));
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDate;
    }

    public static void initCustomerComboBox(ComboBox<Integer> customerAgeComboBox, ComboBox<Integer> customerRankComboBox) {
        for (int i = 50; i < 120; i++) {
            customerAgeComboBox.getItems().add(i);
        }
        customerAgeComboBox.setValue(60);
        for (int i = 1; i <= 3; i++) {
            customerRankComboBox.getItems().add(i);
        }
        customerRankComboBox.setValue(3);
    }

    public static void showtime(Text text) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        text.setText(df.format(new Date()));
    }

    public static String randomPassword() {
        String password = "";
        Random random = new Random();
        int len = random.nextInt(10) + 6;
        for (int i = 0; i < len; i++) {
            int temp = random.nextInt(36);
            if (temp < 10) {
                password = password + temp;
            } else {
                password = password + (char) (87 + temp);
            }
        }
        return password;
    }
}
