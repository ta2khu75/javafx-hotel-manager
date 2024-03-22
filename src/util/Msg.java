/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author ta2khu75
 */
public class Msg {

    public static void printPay(String message) {
        Sound.setSound("money.wav");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Thông báo");
        alert.setContentText(message);
        alert.show();
    }

    public static void printInfo(String message) {
        Sound.setSound("surprise.wav");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Thông báo");
        alert.setContentText(message);
        alert.show();
    }

    public static void printErr(String message) {
        Sound.setSound("error.wav");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Thông báo");
        alert.setContentText(message);
        alert.show();
    }

    public static void printWarn(String message) {
        Sound.setSound("bruh.wav");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Thông báo");
        alert.setContentText(message);
        alert.show();
    }

    public static boolean printConfir(String aler, String message) {
        Sound.setSound("surprise.wav");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(aler);
        alert.setHeaderText(aler);
        alert.setContentText(message);
        ButtonType type = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (type == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean printConfir(String message) {
        Sound.setSound("surprise.wav");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Thông báo");
        alert.setContentText(message);
        ButtonType type = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (type == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

//    public static void input(String message){
//        Alert alert=new Alert(Alert.AlertType.);
//        alert.setTitle("Thông báo");
//        alert.setHeaderText("Thông báo");
//        alert.setContentText(message);
//        alert.show();
//    }
}
