package controller;

import dao.BookingDAO;
import dao.GuestDAO;
import dao.RoomDAO;
import dao.TypeOfRoomDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import model.Booking;
import model.Guest;
import model.Room;
import model.TypeOfRoom;
import util.Fx;
import util.Msg;

public class CheckIn {

    @FXML
    private DatePicker chsDay;

    @FXML
    private DatePicker chsIn;

    @FXML
    private DatePicker chsOut;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rdoDay;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private RadioButton rdoHour;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private ToggleGroup type;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblRoom;

    public ItemRoom getItemRoom() {
        return itemRoom;
    }

    public void setData(ItemRoom ir) {
        this.itemRoom = ir;
        room = itemRoom.room;
        lblRoom.setText(room.getId());
        TypeOfRoom type = typeDao.selectById(room.getIdTypeofRoom());
        price[0] = type.getPricePerDay();
        price[1] = type.getHourlyPrice();
        lblPrice.setText("Price: " + price[0] + "đ");
        rdoDay.setSelected(true);
        chsIn.setValue(LocalDate.now());
    }

    private TypeOfRoomDAO typeDao = new TypeOfRoomDAO();
    private GuestDAO guestDAO = new GuestDAO();
    private ItemRoom itemRoom;
    private Room room;
    private float[] price = new float[2];
    private BookingDAO bookDao = new BookingDAO();
    private RoomDAO roomDao = new RoomDAO();

    @FXML
    void checkIn(MouseEvent event) {
        String id = txtId.getText();
        if (id.isEmpty()) {
            Msg.printErr("Please enter id");
            return;
        } else {
            Pattern pattern = Pattern.compile("^([A-Z0-9]{9,13})$");
            Matcher matcher = pattern.matcher(id);
            if (matcher.matches()) {
                LocalDate in = chsIn.getValue();
                LocalDate out = chsOut.getValue();
                boolean type = rdoDay.isSelected() || rdoHour.isSelected() ? false : true;
                if (in == null || out == null || type) {
                    Msg.printErr("Vui lòng chọn ngày và thời gian ra và loại hình thuê");
                } else {
                    LocalDateTime dateIn = in.atTime(LocalTime.now());
                    LocalDateTime dateOut = out.atTime(LocalTime.now());
                    Guest guest = guestDAO.selectById(id);
                    if (guest != null) {
                        Booking booking = new Booking(guest.getId(), room.getId(), dateIn, dateOut, rdoDay.isSelected() ? true : false, rdoDay.isSelected() ? price[0] : price[1], false);
                        bookDao.insert(booking);
                        itemRoom.homePage.fillMenuRoom();
                        Fx.hide(chsDay);
                        Msg.printInfo("Đặt phòng thành công");
                        // itemRoom.setStatus(roomDao.selectById(booking.getIdRoom()));
                    } else {
                        String name = txtName.getText();
                        String phone = txtPhone.getText();
                        LocalDate date = chsDay.getValue();
                        boolean gender = rdoFemale.isSelected() || rdoMale.isSelected() ? false : true;
                        if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || date == null || gender) {
                            Msg.printErr("Vui lòng nhập đầy đủ thông tin");
                            return;
                        }
                        if (LocalDate.now().getYear() - date.getYear() < 18) {
                            Msg.printWarn("Người trên 18 tuồi mới được thuê phòng");
                            return;
                        }
                        guest = new Guest(id, name, date, rdoMale.isSelected() ? true : false, phone);
                        guestDAO.insert(guest);
                        Booking booking = new Booking(guest.getId(), room.getId(), dateIn, dateOut, rdoDay.isSelected() ? true : false, rdoDay.isSelected() ? price[0] : price[1], false);
                        bookDao.insert(booking);
                        itemRoom.homePage.fillMenuRoom();
                       // itemRoom.setStatus(roomDao.selectById(booking.getIdRoom()));
                        Msg.printInfo("Đặt phòng thành công");
                    }
                }
            } else {
                Msg.printErr("Căn cước công dân không hợp lệ");
            }
        }
    }

    @FXML
    void setPriceDay(MouseEvent event
    ) {
        lblPrice.setText("Price: " + price[0] + "đ");
    }

    @FXML
    void setPriceHour(MouseEvent event
    ) {
        lblPrice.setText("Price: " + price[1] + "đ");
    }

}
