package NursingHome.controller;

import NursingHome.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.downloadPic;
import static NursingHome.ControllerUtils.showAlert;
import static NursingHome.GlobalInfo.MANAGER_ID;

public class UploadImageUIController implements Initializable {
    private Main application;
    @FXML
    private ImageView imageView;
    private String fileName = "";
    private static String id;

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        UploadImageUIController.id = id;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != imageView
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        imageView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    fileName = db.getFiles().toString();
                    // TODO 拖上去后就显示图片
                    imageView.setImage(new Image(fileName));
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }

    public void upload() {
        // TODO 上传头像到数据库
        System.out.println(fileName);
        boolean flag = true;
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "12345678");
            Statement stmt;
            PreparedStatement ps = conn.prepareStatement("UPDATE NursingHome.manager SET manager_pic=? WHERE manager_id='" + getId() + "';");
            InputStream in = new FileInputStream(fileName);
            ps.setBinaryStream(1, in, in.available());
            stmt = conn.createStatement();
            ps.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (flag) {
            // TODO 上传
            if (getId().equals(MANAGER_ID)) {
                // TODO 如果主管修改自己的头像，则修改完后应该刷新现在用户(主管)的头像
                downloadPic();
                PeopleAdminUIController.flushPic();
                //AdminMainUIController.flushPic();
                //BusinessAdminUIController.flushPic();
                id = "";
            }
            getApp().floatStage.close();
        } else {
            showAlert("[警告]您上传的图片大小超过限制！");
        }

    }
}
