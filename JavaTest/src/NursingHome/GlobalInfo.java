package NursingHome;

import javafx.scene.image.Image;

public class GlobalInfo {
    public static String MANAGER_ID = "";
    public static String MANAGER_NAME = "";
    public static String MANAGER_PASSWORD = "";
    public static int MANAGER_PRIV = -1;
    public static Image MANAGER_PIC = null;


    GlobalInfo() {
        this.MANAGER_ID = "";
        this.MANAGER_NAME = "";
        this.MANAGER_PRIV = -1;
        this.MANAGER_PASSWORD = "";
        this.MANAGER_PIC = null;
    }

    public static void clearInfo() {
        MANAGER_ID = "";
        MANAGER_NAME = "";
        MANAGER_PRIV = -1;
        MANAGER_PASSWORD = "";
        MANAGER_PIC = null;
    }
}
