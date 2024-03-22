package controller;

import dao.GuestDAO;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Guest;
import util.Msg;

public class GuestController implements Initializable {

    @FXML
    private DatePicker chsDay;

    @FXML
    private TableColumn<Guest, LocalDate> colBirthDay;

    @FXML
    private TableColumn<Guest, String> colGender;

    @FXML
    private TableColumn<Guest, Integer> colId;

    @FXML
    private TableColumn<Guest, String> colName;

    @FXML
    private TableColumn<Guest, String> colPhone;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;
    @FXML
    private TableView<Guest> tblGuest;

    @FXML
    void deleteGuest(MouseEvent event) {
        Guest guest = checkGuest();
        if (guest != null) {
            guestDAO.delete(guest);
            fillTable();
            Msg.printInfo("Xóa thành công");
        }
    }

    @FXML
    void insertGuest(MouseEvent event) {
        Guest guest = checkGuest();
        if (guest != null) {
            guestDAO.insert(guest);
            fillTable();
            Msg.printInfo("Thêm thành công");
        }
    }

    @FXML
    void newGuest(MouseEvent event) {
        reset();
    }

    @FXML
    void updateGuest(MouseEvent event) {
        Guest guest = checkGuest();
        if (guest != null) {
            guestDAO.update(guest);
            fillTable();
            Msg.printInfo("Cập nhập thành công");
        }
    }

    @FXML
    void fillForm(MouseEvent event) {
        getGuest();
    }
    private GuestDAO guestDAO = new GuestDAO();
    private ObservableList<Guest> list;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FXCollections.observableArrayList(guestDAO.selectAll());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colBirthDay.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colGender.setCellValueFactory((p) -> {
            String gender = p.getValue().isGender() ? "Male" : "Female";
            return new SimpleObjectProperty<>(gender);
        });
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        tblGuest.setItems(list);
    }

    private Guest checkGuest() {
        String id = txtId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        LocalDate date = chsDay.getValue();
        boolean gender = rdoFemale.isSelected() || rdoMale.isSelected() ? false : true;
        if (id.isEmpty() || name.isEmpty() || phone.isEmpty() || date == null || gender) {
            Msg.printErr("Vui lòng nhập đầy đủ thông tin");
            return null;
        }
        if (LocalDate.now().getYear() - date.getYear() < 18) {
            Msg.printWarn("Người trên 18 tuồi mới được thuê phòng");
            return null;
        }
        Pattern pattern = Pattern.compile("^([A-Z0-9]{9,13})$");
        Matcher matcher = pattern.matcher(id);
        if (matcher.matches()) {
            return new Guest(id, name, chsDay.getValue(), rdoMale.isSelected() ? true : false, phone);
        }
        Msg.printErr("Căn cước công dân không hợp lệ");
        return null;
    }

    private void reset() {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        rdoFemale.setSelected(false);
        rdoMale.setSelected(false);
        chsDay.setValue(null);
    }

    private void fillTable() {
        list.clear();
        list.addAll(guestDAO.selectAll());
        reset();
    }

    private void getGuest() {
        Guest guest = tblGuest.getSelectionModel().getSelectedItem();
        txtId.setText(guest.getId());
        txtName.setText(guest.getFullName());
        txtPhone.setText(guest.getPhoneNum());
        chsDay.setValue(guest.getBirthDate());
        if (guest.isGender()) {
            rdoMale.setSelected(true);
        } else {
            rdoFemale.setSelected(true);
        }
    }
}
