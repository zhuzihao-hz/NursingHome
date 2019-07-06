package NursingHome;

import NursingHome.controller.PeopleSetInfoUIController;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import static NursingHome.GlobalInfo.MANAGER_PRIV;

public class ControllerUtils {
    /**
     * 显示提示框
     *
     * @param message 提示信息
     */
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

    /**
     * md5码加密算法
     *
     * @param plainText 要加密的字符串
     * @return 加密后的字符串
     */
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

    /**
     * 人事管理信息界面初始化下拉框
     *
     * @param peopleTypeComboBox  员工类型下拉框
     * @param peopleType          员工类型
     * @param peopleOtherComboBox 员工岗位（职位）下拉框
     */
    public static void initPeopleComboBox(ComboBox<String> peopleTypeComboBox, String peopleType, ComboBox<String> peopleOtherComboBox, ComboBox<Integer> customerRankComboBox) {
        peopleTypeComboBox.getItems().add("护工");
        peopleTypeComboBox.getItems().add("医生");
        peopleTypeComboBox.getItems().add("勤杂人员");
        if (MANAGER_PRIV == 0) {
            peopleTypeComboBox.getItems().add("行政人员");
        }
        peopleTypeComboBox.setValue(peopleType);
        peopleTypeComboBox.setEditable(false);
        if (PeopleSetInfoUIController.peopleType.equals("护工")) {
            peopleOtherComboBox.getItems().add("高级护工");
            peopleOtherComboBox.getItems().add("中级护工");
            peopleOtherComboBox.getItems().add("低级护工");
            peopleOtherComboBox.getItems().add("实习护工");
            peopleOtherComboBox.setValue("低级护工");
        } else if (PeopleSetInfoUIController.peopleType.equals("医生")) {
            peopleOtherComboBox.getItems().add("全科");
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
        for (int i = 1; i <= 3; i++) {
            customerRankComboBox.getItems().add(i);
        }
        customerRankComboBox.setValue(3);
    }

    /**
     * 人事管理信息界面更改员工类型时更改下拉框内容
     *
     * @param peopleType          员工类型
     * @param peopleOtherComboBox 员工职位（岗位）下拉框
     */
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

    /**
     * 日期转字符串
     *
     * @param localDate 日期
     * @return 字符串
     */
    public static String localDateToString(LocalDate localDate) {
        return String.valueOf(localDate);
    }

    /**
     * 字符串转日期
     *
     * @param string 字符串，格式为"yyyy-mm-dd"
     * @return 日期
     */
    public static LocalDate StringToDate(String string) {
        int year, month, day;
        year = Integer.parseInt(string.substring(0, 4));
        month = Integer.parseInt(string.substring(5, 7));
        day = Integer.parseInt(string.substring(8, 10));
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDate;
    }

    /**
     * 在客户信息界面初始化下拉框
     *
     * @param customerRankComboBox       客户护理等级下拉框
     * @param customerWorkerRankComboBox 客户要求的护工的等级下拉框
     * @param customerRoomRankComboBox   客户要求的房间等级下拉框
     */
    public static void initCustomerComboBox(ComboBox<Integer> customerRankComboBox, ComboBox<String> customerWorkerRankComboBox, ComboBox<String> customerRoomRankComboBox) {
        for (int i = 1; i <= 3; i++) {
            customerRankComboBox.getItems().add(i);
        }
        customerRankComboBox.setValue(3);
        for (int i = 1; i <= 3; i++) {
            customerRoomRankComboBox.getItems().add(String.valueOf(i));
        }
        customerRoomRankComboBox.setValue("3");
        customerWorkerRankComboBox.getItems().add("高级护工");
        customerWorkerRankComboBox.getItems().add("中级护工");
        customerWorkerRankComboBox.getItems().add("低级护工");
        customerWorkerRankComboBox.getItems().add("实习护工");
        customerWorkerRankComboBox.setValue("高级护工");
    }

    /**
     * 显示日期
     *
     * @param text 文本框对象
     */
    public static void showtime(Text text) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        text.setText(df.format(new Date()));
    }

    /**
     * 生成新的用户时随机生成密码
     *
     * @return 新密码
     */
    public static String randomPassword() {
        String password = "";
        Random random = new Random();
        int len = random.nextInt(10) + 4;
        for (int i = 0; i < len; i++) {
            int temp = random.nextInt(36);
            if (temp < 10) {
                password = password + temp;
            } else {
                password = password + (char) (87 + temp);
            }
        }
        // TODO 防止密码只有数字或者字母，于是在后面加上一个数字和一个字母
        password = password + random.nextInt(10) + (char) (97 + random.nextInt(26));
        return password;
    }

    /**
     * 显示用户头像
     *
     * @param imageName 文件名
     * @return 返回Image对象
     */
    public static Image showImage(String imageName) {
        return new Image("file:" + System.getProperty("user.dir") + "/src/NursingHome/img/" + imageName);
    }

    public static void downloadPic() {
        // TODO 显示图片，先从数据库中读取，保存到本地，再重新
        InputStream in = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            Statement stmt = conn.createStatement();
            String sql = "select * from NursingHome.manager where manager_id = 'W7'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                in = rs.getBinaryStream(4);
                byte[] b = new byte[in.available()];    //新建保存图片数据的byte数组
                in.read(b);
                OutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/src/NursingHome/img/" + "pic1.jpg");
                out.write(b);
                out.flush();
                out.close();
            }
            in.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
