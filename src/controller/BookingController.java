package controller;

import dao.BookingDAO;
import dao.GuestDAO;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Booking;
import util.Msg;

public class BookingController implements Initializable {

    @FXML
    private TableColumn<Booking, Integer> colId;

    @FXML
    private TableColumn<Booking, Date> colIn;

    @FXML
    private TableColumn<Booking, String> colName;

    @FXML
    private TableColumn<Booking, Date> colOut;

    @FXML
    private TableColumn<Booking, String> colRoom;

    @FXML
    private TableColumn<Booking, String> colStatus;

    @FXML
    private TableColumn<Booking, Float> colTotal;

    @FXML
    private TableColumn<Booking, String> colType;

    @FXML
    private DatePicker daFrom;

    @FXML
    private DatePicker daTo;

    @FXML
    private TableView<Booking> tblBill;

    @FXML
    void findBill(MouseEvent event) {
        if (daFrom.getValue() == null || daTo.getValue() == null) {
            list.clear();
            list.addAll(bookDao.selectAll());
            Msg.printWarn("Vui lòng điền đầy đủ thông tin");
        } else {
            list.clear();
            list.addAll(bookDao.selectByDate(daFrom.getValue(), daTo.getValue()));
        }
    }
    private BookingDAO bookDao = new BookingDAO();
    private GuestDAO guestDAO = new GuestDAO();
    private ObservableList<Booking> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FXCollections.observableArrayList(bookDao.selectAll());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory((p) -> {
            String name = guestDAO.selectById(p.getValue().getIdGuest()).getFullName();
            return new SimpleObjectProperty<>(name);
        });
        colRoom.setCellValueFactory(new PropertyValueFactory<>("idRoom"));
        colIn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colOut.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colType.setCellValueFactory((p) -> {
            String type = p.getValue().isType() ? "Day" : "Hour";
            return new SimpleObjectProperty<>(type);
        });
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalMoney"));
        colStatus.setCellValueFactory((p) -> {
            String status = p.getValue().isStatus() ? "Đã thanh toán" : "Chưa thanh toán";
            return new SimpleObjectProperty<>(status);
        });
        tblBill.setItems(list);
    }

}
