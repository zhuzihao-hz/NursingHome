package NursingHome;

import NursingHome.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {
    public Stage stage;
    public Stage floatStage;

    private double x0, y0, x_stage, y_stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        try {
            gotoAdminLoginUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到登陆界面
     */
    public void gotoAdminLoginUI() {
        stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        AdminLoginUIController loginUI = (AdminLoginUIController) replaceSceneContent("fxml/AdminLoginUI.fxml");
        loginUI.setApp(this);
        stage.getScene().setOnMouseDragged(event -> {
            stage.setX(x_stage + event.getScreenX() - x0);
            stage.setY(y_stage + event.getScreenY() - y0);
        });
        stage.getScene().setOnDragEntered(null);
        stage.getScene().setOnMousePressed(event -> {
            x0 = event.getScreenX();
            y0 = event.getScreenY();
            x_stage = stage.getX();
            y_stage = stage.getY();
        });
        stage.show();
    }

    /**
     * 跳转到人事管理界面
     */
    public void gotoPeopleAdminUI() {
        stage = new Stage();
        stage.setResizable(false);
        PeopleAdminUIController adminUI = (PeopleAdminUIController) replaceSceneContent("fxml/PeopleAdminUI.fxml");
        adminUI.setApp(this);
        stage.getScene().setOnMouseDragged(event -> {
            stage.setX(x_stage + event.getScreenX() - x0);
            stage.setY(y_stage + event.getScreenY() - y0);
        });
        stage.getScene().setOnDragEntered(null);
        stage.getScene().setOnMousePressed(event -> {
            x0 = event.getScreenX();
            y0 = event.getScreenY();
            x_stage = stage.getX();
            y_stage = stage.getY();
        });
        stage.setTitle("管理员 - 人事管理 Administrator People Manage");
        stage.show();
    }

    /**
     * 跳转到业务管理界面
     */
    public void gotoBusinessAdminUI() {
        stage = new Stage();
        stage.setResizable(false);
        BusinessAdminUIController adminUI = (BusinessAdminUIController) replaceSceneContent("fxml/BusinessAdminUI.fxml");
        adminUI.setApp(this);
        stage.getScene().setOnMouseDragged(event -> {
            stage.setX(x_stage + event.getScreenX() - x0);
            stage.setY(y_stage + event.getScreenY() - y0);
        });
        stage.getScene().setOnDragEntered(null);
        stage.getScene().setOnMousePressed(event -> {
            x0 = event.getScreenX();
            y0 = event.getScreenY();
            x_stage = stage.getX();
            y_stage = stage.getY();
        });
        stage.setTitle("管理员 - 业务管理 Administrator Business Manage");
        stage.show();
    }

    /**
     * 跳转到主界面
     */
    public void gotoAdminMainUI() {
        stage.close();
        stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("养老院管理系统(管理员) Nursing Home(Admin)");
        AdminMainUIController adminMainUI = (AdminMainUIController) replaceSceneContent("fxml/AdminMainUI.fxml");
        adminMainUI.setApp(this);
        stage.show();
    }

    /**
     * 创建用户信息窗口
     */
    public void createPersonalInfoUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("管理员 - 个人信息 Administrator Personal Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/PersonalInfoUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/PersonalInfoUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        PersonalInfoUIController personalInfoUI = loader.getController();
        personalInfoUI.setApp(this);
    }

    /**
     * 创建更改密码窗口
     */
    public void createChangePasswordUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("管理员 - 修改密码 Administrator Change Password");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/ChangePasswordUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/ChangePasswordUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        ChangePasswordUIController changePAsswordUI = loader.getController();
        changePAsswordUI.setApp(this);
    }

    /**
     * 创建员工详细信息窗口
     */
    public void createPeopleSetInfoUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("人事管理 - 个人信息 Administrator Personal Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/PeopleSetInfoUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/PeopleSetInfoUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        PeopleSetInfoUIController peopleSetInfoUI = loader.getController();
        peopleSetInfoUI.setApp(this);
    }

    /**
     * 创建客户详细信息窗口
     */
    public void createCustomerSetInfoUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("客户管理 - 客户信息 Administrator Customer Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/CustomerSetInfoUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/CustomerSetInfoUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        CustomerSetInfoUIController customerSetInfoUI = loader.getController();
        customerSetInfoUI.setApp(this);
    }

    /**
     * 创建新增客户的窗口
     */
    public void createCustomerInsertInfoUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("客户管理 - 客户信息 Administrator Customer Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/CustomerInsertInfoUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/CustomerInsertInfoUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        CustomerInsertInfoUIController customerInsertInfoUI = loader.getController();
        customerInsertInfoUI.setApp(this);
    }

    /**
     * 创建新增记录的窗口
     */
    public void createRecordInsertUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("档案管理 - 档案信息 Administrator Record Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/RecordInsertUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/RecordInsertUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        RecordInsertUIController recordInsertUI = loader.getController();
        recordInsertUI.setApp(this);
    }

    /**
     * 创建查看记录详细信息的窗口
     */
    public void createRecordLookInfoUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("档案管理 - 档案信息 Administrator Record Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/RecordLookInfoUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/RecordLookInfoUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        RecordLookInfoUIController recordLookInfoUI = loader.getController();
        recordLookInfoUI.setApp(this);
    }

    /**
     * 创建帮助信息的窗口
     */
    public void createAboutInfoUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("养老院管理系统 - 关于 Nursing Home Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/AboutInfoUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/AboutInfoUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);
        floatStage.setScene(scene);
        floatStage.show();
        AboutInfoUIController aboutInfoUI = loader.getController();
        aboutInfoUI.setApp(this);
    }

    private Initializable replaceSceneContent(String fxml) {
        AnchorPane page = new AnchorPane();
        FXMLLoader loader = new FXMLLoader();
        try (InputStream in = Main.class.getResourceAsStream(fxml)) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource(fxml));
            page = loader.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
