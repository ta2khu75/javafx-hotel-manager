/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

/**
 *
 * @author ta2khu75
 */
public class Fx {

    private static Map<String, Stage> stageMap = new HashMap<>();

    public static void putStage(String fxml, Control control) {
        stageMap.put(fxml, getStage(control));
    }

    public static void putStage(Control control) {
        Stage stage=getStage(control);
        System.out.println(stage.getUserData().toString());
        stageMap.put(stage.getUserData().toString(), stage);
    }

    public static void putStage(String fxml, Stage stage) {
        stageMap.put(fxml, stage);
    }

    public static Stage getStage(String fxml) {
        return stageMap.get(fxml);
    }

    public static void remove(String fxml) {
        stageMap.remove(fxml);
    }
    public static void setRoot(Stage stage, String fxml) throws IOException {
        stage.getScene().setRoot(loadFXML(fxml));
    }
     public static void setRoot(Stage stage, Parent parent) throws IOException {
        stage.getScene().setRoot(parent);
    }
    public static Scene getScene(String fxml) {
        Scene scene = null;
        try {
            scene = new Scene(loadFXML(fxml));
            scene.setUserData(fxml);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Fx.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Fx.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void show(String fxml) {
        Stage stage = new Stage();
        stage.setScene(getScene(fxml));
        //stage.setUserData(fxml);
        stage.show();
    }

    public static void hide(Control control) {
        getStage(control).hide();
        //putStage(control);
    }

    public static void close(Control control) {
        getStage(control).close();
       // putStage(control);
    }

    public static Stage getStage(Control control) {
        Stage stage = (Stage) control.getScene().getWindow();
        return stage;
    }

    public static void setScene(Control control, String fxml) {
        getStage(control).setScene(getScene(fxml));
    }
}
