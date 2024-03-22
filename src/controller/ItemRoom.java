package controller;

import dao.BookingDAO;
import dao.GuestDAO;
import dao.TypeOfRoomDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Booking;
import model.Guest;
import model.Room;
import model.TypeOfRoom;
import util.Fx;
import util.Msg;

public class ItemRoom implements Initializable {

    @FXML
    private ImageView imgRoom;

    @FXML
    private Label lblCustomer;

    @FXML
    private Label lblId;

    @FXML
    private VBox pnlRoom;

    @FXML
    private VBox itemRoom;

    @FXML
    private BorderPane pneCustomer;

    @FXML
    void viewOption(MouseEvent event) throws IOException {
        if (event.getButton() == MouseButton.SECONDARY && condition) {
            switch (room.getStatus()) {
                case 0:
                    item1.setDisable(false);
                    item2.setDisable(true);
                    item3.setDisable(true);
                    item4.setDisable(false);
                    break;
                case 1:
                    item1.setDisable(true);
                    item2.setDisable(false);
                    item3.setDisable(true);
                    item4.setDisable(true);
                    break;
                case 2:
                    item1.setDisable(true);
                    item2.setDisable(true);
                    item3.setDisable(false);
                    item4.setDisable(true);
                    break;
                case 3:
                    if (room.getStatus() == 3) {
                        item4.setText("Updated");
                    }
                    item1.setDisable(true);
                    item2.setDisable(true);
                    item3.setDisable(true);
                    item4.setDisable(false);
                    break;
            }
            contextMenu.show(itemRoom, event.getScreenX(), event.getScreenY());
        }
        if (!condition && room.getStatus() == 0) {
            if (Msg.printConfir("Đổi phòng", "Bạn có muốn đổi  qua phòng " + room.getId() + " không?")) {
                Booking booking = detailBooking.getBooking();
                booking.setIdRoom(room.getId());
                BookingDAO bookingDAO = new BookingDAO();
                bookingDAO.update(booking);
                guest = guestDao.selectById(booking.getIdGuest());
                Fx.setRoot(Fx.getStage(lblId), parent);
                FXMLLoader loadChild = new FXMLLoader(getClass().getResource("/view/detailBooking.fxml"));
                try {
                    Parent parentChild = loadChild.load();
                    homePage.viewDetailBooking(parentChild);
                    DetailBooking detailBookingChild = loadChild.getController();
                    detailBookingChild.setData(this);
                } catch (IOException ex) {
                    Logger.getLogger(ItemRoom.class.getName()).log(Level.SEVERE, null, ex);
                }
                Msg.printInfo("Đổi phòng thành công");
            }
        }
    }

    void setStatus() {
        Background background = new Background(colorList[room.getStatus()]);
        pnlRoom.setBackground(background);
        if (room.getStatus() != 1) {
            pneCustomer.setVisible(false);
        } else {

            guest = guestDao.selectByRoom(room.getId());
            lblCustomer.setText(guest.getFullName());
        }
        setType();
    }

    void setType() {
        Image image;
        if (typeOfRoom.getName().equalsIgnoreCase("đôi")) {
            image = new Image(getClass().getResourceAsStream("/icon/double-bed.png"));
        } else {
            image = new Image(getClass().getResourceAsStream("/icon/single-bed.png"));
        }
        imgRoom.setImage(image);
    }

    void setData(Room r, HomePage homepage) {
        this.homePage = homepage;
        this.room = r;
        lblId.setText(room.getId());
        typeOfRoom = typeOfRoomDAO.selectById(room.getIdTypeofRoom());
        setStatus();
    }

    void setData(Parent parent, HomePage homePage, Room r, DetailBooking detailBooking) {
        this.parent = parent;
        this.homePage = homePage;
        this.room = r;
        this.detailBooking = detailBooking;
        this.condition = false;
        lblId.setText(room.getId());
        typeOfRoom = typeOfRoomDAO.selectById(room.getIdTypeofRoom());
        setStatus();
    }
    DetailBooking detailBooking;
    Parent parent;
    boolean condition = true;
    String[] statusList = {"Đang trống", "Đã đặt", "Đang dọn", "Bảo trì"};
    BackgroundFill[] colorList = {new BackgroundFill(Color.GREEN, null, null), new BackgroundFill(Color.RED, null, null), new BackgroundFill(Color.YELLOW, null, null), new BackgroundFill(Color.BLUE, null, null)};
    TypeOfRoomDAO typeOfRoomDAO = new TypeOfRoomDAO();
    Room room;
    TypeOfRoom typeOfRoom;
    HomePage homePage;
    ContextMenu contextMenu = new ContextMenu();
    MenuItem item1 = new MenuItem("Book room");
    MenuItem item2 = new MenuItem("Room details");
    MenuItem item3 = new MenuItem("Cleaned");
    MenuItem item4 = new MenuItem("Update");
    GuestDAO guestDao = new GuestDAO();
    Guest guest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contextMenu.getItems().add(item1);
        contextMenu.getItems().add(item2);
        contextMenu.getItems().add(item3);
        contextMenu.getItems().add(item4);

        item1.setOnAction((t) -> {
            FXMLLoader load = new FXMLLoader(getClass().getResource("/view/checkIn.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(load.load());
            } catch (IOException ex) {
                Logger.getLogger(ItemRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
            CheckIn checkIn = load.getController();
            checkIn.setData(this);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });
        item2.setOnAction((t) -> {
            try {
                FXMLLoader load = new FXMLLoader(getClass().getResource("/view/detailBooking.fxml"));
                AnchorPane pane = load.load();
                DetailBooking detailBooking = load.getController();
                detailBooking.setData(this);
                homePage.viewDetailBooking(pane);
            } catch (IOException ex) {
                Logger.getLogger(ItemRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        item3.setOnAction((t) -> {
            room.setStatus(0);
            homePage.roomDao.update(room);
            setStatus();
        });
        item4.setOnAction((t) -> {
            if (room.getStatus() == 3) {
                room.setStatus(0);
                setStatus();
            } else {
                room.setStatus(3);
                setStatus();
            }
            homePage.roomDao.update(room);
        });
    }

}
