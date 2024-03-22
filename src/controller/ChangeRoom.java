/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.RoomDAO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Room;
import util.Fx;

/**
 * FXML Controller class
 *
 * @author ta2khu75
 */
public class ChangeRoom implements Initializable {

    private DetailBooking detailBooking;

    @FXML
    private Label lblChangerRoom;
    @FXML
    private GridPane menuRoom;

    @FXML
    void back(MouseEvent event) throws IOException {
        FXMLLoader loadChild = new FXMLLoader(getClass().getResource("/view/detailBooking.fxml"));
        Parent parentChild = loadChild.load();
        DetailBooking detailBookingChild = loadChild.getController();
        detailBookingChild.setData(this.detailBooking.getItemRoom());
        homePage.viewDetailBooking(parentChild);
        Fx.setRoot(stage, parent);
    }

    public void setDate(DetailBooking detailBooking) {
        this.detailBooking = detailBooking;
        stage = Fx.getStage(lblChangerRoom);
        try {
            parent = load.load();
            homePage = load.getController();
        } catch (IOException ex) {
            Logger.getLogger(ChangeRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblChangerRoom.setText(String.format("Change room %s to room", this.detailBooking.getItemRoom().room.getId()));
        menuRoom.getChildren().clear();
        menuRoom.getRowConstraints().clear();
        menuRoom.getColumnConstraints().clear();
        int row = 0;
        int col = 0;
        for (Room room : listRoom) {
            FXMLLoader load = new FXMLLoader(getClass().getResource("/view/itemRoom.fxml"));
            try {
                Parent parent = load.load();
                ItemRoom ir = load.getController();
                ir.setData(this.parent  ,homePage, room, detailBooking);
                if (col == 6) {
                    row++;
                    col = 0;
                }
                menuRoom.add(parent, col++, row);
            } catch (IOException ex) {
                Logger.getLogger(ChangeRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    RoomDAO roomDao = new RoomDAO();
    List<Room> listRoom = roomDao.selectAll();
    FXMLLoader load = new FXMLLoader(getClass().getResource("/view/homePage.fxml"));
    Parent parent;
    HomePage homePage;
    Stage stage;// = Fx.getStage(lblChangerRoom);

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
