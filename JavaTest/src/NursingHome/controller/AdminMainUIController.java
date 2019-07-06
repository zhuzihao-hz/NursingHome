package NursingHome.controller;

import NursingHome.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static NursingHome.ControllerUtils.*;
import static NursingHome.GlobalInfo.*;

public class AdminMainUIController implements Initializable {
    private Main application;

    @FXML
    Text welcome;
    @FXML
    Text dateText;
    @FXML
    private ImageView imageView;

    public void setApp(Main app) {
        this.application = app;
    }

    public Main getApp() {
        return this.application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 需要修改
        //imageView.setImage(showImage("dummy_potrait.jpg"));
        //downloadPic();
        imageView.setImage(showImage("pic1.jpg"));
        welcome.setText(MANAGER_NAME);
        showtime(dateText);
    }

    /**
     * 查看员工信息
     */
    public void personalInfo() {
        getApp().createPersonalInfoUI();
    }

    /**
     * 查看员工信息
     */
    public void personInfoImage() {
        getApp().createPersonalInfoUI();
    }

    /**
     * 退出
     */
    public void quit() {
        application.stage.close();
    }

    /**
     * 登出
     */
    public void logout() {
        clearInfo();
        application.stage.close();
        application.gotoAdminLoginUI();
    }

    /**
     * 进入人事管理界面
     */
    public void movePeopleAdmin() {
        if (MANAGER_PRIV == 0 || MANAGER_PRIV == 1) {
            application.stage.close();
            application.gotoPeopleAdminUI();
        } else {
            showAlert("[警告]您没有进入人事管理的权限！");
        }
    }

    /**
     * 进入业务管理界面
     */
    public void moveBusinessAdmin() {
        if (MANAGER_PRIV == 2 || MANAGER_PRIV == 3) {
            application.stage.close();
            application.gotoBusinessAdminUI();
        } else {
            showAlert("[警告]您没有进入业务管理的权限！");
        }
    }

    /**
     * 进入人事管理界面
     */
    public void movePeopleAdminMenu() {
        if (MANAGER_PRIV == 0 || MANAGER_PRIV == 1) {
            application.stage.close();
            application.gotoPeopleAdminUI();
        } else {
            showAlert("[警告]您没有进入人事管理的权限！");
        }
    }

    /**
     * 进入业务管理界面
     */
    public void moveBusinessAdminMenu() {
        if (MANAGER_PRIV == 2 || MANAGER_PRIV == 3) {
            application.stage.close();
            application.gotoBusinessAdminUI();
        } else {
            showAlert("[警告]您没有进入业务管理的权限！");
        }
    }

    /**
     * 帮助信息
     */
    public void aboutInfo() {
        application.createAboutInfoUI();
    }

}
