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
    public void start(Stage primaryStage){
        stage = primaryStage;
        try {
            gotoAdminLoginUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoAdminLoginUI() throws Exception {
        stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        AdminLoginUIController loginUI = (AdminLoginUIController)replaceSceneContent("fxml/AdminLoginUI.fxml");
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

    public void gotoPeopleAdminUI() throws Exception {
        stage = new Stage();
        stage.setResizable(false);
        PeopleAdminUIController adminUI = (PeopleAdminUIController)replaceSceneContent("fxml/PeopleAdminUI.fxml");
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
        stage.show();
    }

    public void gotoBusinessAdminUI() throws Exception {
        stage = new Stage();
        stage.setResizable(false);
        BusinessAdminUIController adminUI = (BusinessAdminUIController)replaceSceneContent("fxml/BusinessAdminUI.fxml");
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
        stage.show();
    }

    public void gotoAdminMainUI() throws Exception {
        stage.close();
        stage = new Stage();
        stage.setTitle("养老院管理系统(管理员) Nursing Home(Admin)");
        AdminMainUIController adminMainUI = (AdminMainUIController)replaceSceneContent("fxml/AdminMainUI.fxml");
        adminMainUI.setApp(this);
        stage.show();
    }

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
        PersonalInfoUIController personalInfoUI= loader.getController();
        personalInfoUI.setApp(this);
    }

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
        PeopleSetInfoUIController peopleSetInfoUI= loader.getController();
        peopleSetInfoUI.setApp(this);
    }

    public void createBedSetInfoUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("床位管理 - 床位信息 Administrator Bed Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/BedSetInfoUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/BedSetInfoUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        BedSetInfoUIController bedSetInfoUI= loader.getController();
        bedSetInfoUI.setApp(this);
    }

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
        CustomerSetInfoUIController customerSetInfoUI= loader.getController();
        customerSetInfoUI.setApp(this);
    }


    public void createRecordSetInfoUI() {
        AnchorPane page = new AnchorPane();
        floatStage = new Stage();
        floatStage.setAlwaysOnTop(true);
        floatStage.initModality(Modality.APPLICATION_MODAL);
        floatStage.setTitle("档案管理 - 档案信息 Administrator Record Information");
        floatStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        try (InputStream ignored = Main.class.getResourceAsStream("fxml/RecordSetInfoUI.fxml")) {
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource("fxml/RecordSetInfoUI.fxml"));
            page = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(page);

        floatStage.setScene(scene);
        floatStage.show();
        RecordSetInfoUIController recordSetInfoUI= loader.getController();
        recordSetInfoUI.setApp(this);
    }

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
        AboutInfoUIController aboutInfoUI= loader.getController();
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
