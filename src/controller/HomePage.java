/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.RoomDAO;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Room;
import util.Auth;
import util.Fx;
import util.Sound;

/**
 * FXML Controller class
 *
 * @author ta2khu75
 */
public class HomePage implements Initializable {

    @FXML
    private Label lblUser;

    @FXML
    private Label lblClock;

    @FXML
    private GridPane pneItem;

    @FXML
    void bookRoom(MouseEvent event) {
        Sound.setSound("click.wav");
        fillMenuRoom();
    }

    @FXML
    void managerBill(MouseEvent event) throws IOException {
        Sound.setSound("click.wav");
        pneItem.getChildren().clear();
        AnchorPane pane = (AnchorPane) Fx.loadFXML("/view/booking");
        pneItem.getChildren().add(pane);

    }

    @FXML
    void changePass(MouseEvent event) throws IOException {
        Sound.setSound("click.wav");
        pneItem.getChildren().clear();
        BorderPane pane = (BorderPane) Fx.loadFXML("/view/change");
        pneItem.getChildren().add(pane);
    }

    @FXML
    void managerCustomer(MouseEvent event) throws IOException {
        Sound.setSound("click.wav");
        pneItem.getChildren().clear();
        AnchorPane pane = (AnchorPane) Fx.loadFXML("/view/guest");
        pneItem.getChildren().add(pane);
    }

    @FXML
    void managerRoom(MouseEvent event) throws IOException {
        Sound.setSound("click.wav");
        pneItem.getChildren().clear();
        AnchorPane pane = (AnchorPane) Fx.loadFXML("/view/room");
        pneItem.getChildren().add(pane);
    }

    @FXML
    void managerService(MouseEvent event) throws IOException {
        Sound.setSound("click.wav");
        pneItem.getChildren().clear();
        AnchorPane pane = (AnchorPane) Fx.loadFXML("/view/service");
        pneItem.getChildren().add(pane);
    }

    @FXML
    void signOut(MouseEvent event) {
        Sound.setSound("click.wav");
        Fx.hide(lblClock);
        Auth.remove();
        Fx.show("/view/login");
    }

    @FXML
    void managerStaff(MouseEvent event) throws IOException {
        Sound.setSound("click.wav");
        pneItem.getChildren().clear();
        AnchorPane pane = (AnchorPane) Fx.loadFXML("/view/staff");
        pneItem.getChildren().add(pane);
    }

    @FXML
    void managerStatistic(MouseEvent event) throws IOException {
        Sound.setSound("click.wav");
        pneItem.getChildren().clear();
        AnchorPane pane = (AnchorPane) Fx.loadFXML("/view/statistic");
        pneItem.getChildren().add(pane);
    }

    /**
     * Initializes the controller class.
     */
    Parent parent;
    final RoomDAO roomDao = new RoomDAO();
    List<Room> roomList;
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm:ss aa");
            lblClock.setText(dateFormat.format(new Date()));
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        timer.start();
        lblUser.setText("Welcome " + Auth.get().getFullName());
        fillMenuRoom();
    }

    void fillMenuRoom() {
        roomList = roomDao.selectAll();
        int col = 0;
        int row = 0;
        pneItem.getChildren().clear();
        pneItem.getColumnConstraints().clear();
        pneItem.getRowConstraints().clear();
        for (Room x : roomList) {
            FXMLLoader load = new FXMLLoader(getClass().getResource("/view/itemRoom.fxml"));
            VBox pane = null;
            try {
                pane = load.load();
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
            ItemRoom control = load.getController();
            control.setData(x, this);
            if (col == 5) {
                col = 0;
                row++;
            }
            pneItem.add(pane, col++, row);
            GridPane.setMargin(pane, new Insets(10));
        }
    }

    void viewDetailBooking(Parent pane) {
        pneItem.getChildren().clear();
        pneItem.getChildren().add(pane);
    }
}
