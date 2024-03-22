package controller;

import dao.RoomDAO;
import dao.TypeOfRoomDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Room;
import model.TypeOfRoom;
import util.Msg;

public class RoomController implements Initializable {

    int id = 0;
    @FXML
    private ComboBox<String> cboStatus;

    @FXML
    private ComboBox<String> cboType;

    @FXML
    private TableColumn<TypeOfRoom, Float> colDate;

    @FXML
    private TableColumn<TypeOfRoom, Float> colHour;

    @FXML
    private TableColumn<Room, String> colIdRoom;

    @FXML
    private TableColumn<TypeOfRoom, Integer> colIdType;

    @FXML
    private TableColumn<TypeOfRoom, String> colName;

    @FXML
    private TableColumn<Room, String> colStatus;

    @FXML
    private TableColumn<Room, String> colType;

    @FXML
    private TableView<Room> tblRoom;

    @FXML
    private TableView<TypeOfRoom> tblType;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtHour;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtType;

    @FXML
    void newRoom(MouseEvent event) {
        refrechRoom();
    }

    @FXML
    void newType(MouseEvent event) {
        refrechType();
    }

    @FXML
    void deleteRoom(MouseEvent event) {
        Room room = checkInputRoom();
        if (room != null) {
            roomDao.delete(room);
            Msg.printInfo("Delete complete");
            fillTtbRoom();
        }
    }

    @FXML
    void deleteType(MouseEvent event) {
        TypeOfRoom type = checkInputType();
        if (type != null) {
            type.setId(id);
            typeDao.delete(type);
            Msg.printInfo("Delete complete");
            fillTtbType();

        }
    }

    @FXML
    void insertRoom(MouseEvent event) {
        Room room = checkInputRoom();
        if (room != null) {
            roomDao.insert(room);
            Msg.printInfo("Insert complete");
            fillTtbRoom();

        }
    }

    @FXML
    void insertType(MouseEvent event) {
        TypeOfRoom type = checkInputType();
        if (type != null) {
            typeDao.insert(type);
            Msg.printInfo("insert complete");
            fillTtbType();

        }
    }

    @FXML
    void updateRoom(MouseEvent event) {
        Room room = checkInputRoom();
        if (room != null) {
            roomDao.update(room);
            Msg.printInfo("Update complete");
            fillTtbRoom();
        }
    }

    @FXML
    void updateType(MouseEvent event) {
        TypeOfRoom type = checkInputType();
        if (type != null) {
            type.setId(id);
            typeDao.update(type);
            Msg.printInfo("Update complete");
            fillTtbType();
        }
    }

    @FXML
    void fillFromRoom(MouseEvent event) {
        fillRoom();
    }

    @FXML
    void fillFormType(MouseEvent event) {
        fillType();
    }
    TypeOfRoomDAO typeDao = new TypeOfRoomDAO();
    RoomDAO roomDao = new RoomDAO();
    ObservableList<Room> roomList = FXCollections.observableArrayList(roomDao.selectAll());
    ObservableList<TypeOfRoom> typeList = FXCollections.observableArrayList(typeDao.selectAll());
    ObservableList<String> statusList = FXCollections.observableArrayList("Đang trống", "Đã đặt", "Đang dọn", "Bảo trì");
    ObservableList<String> nameTypeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colHour.setCellValueFactory(new PropertyValueFactory<TypeOfRoom, Float>("hourlyPrice"));
        colDate.setCellValueFactory(new PropertyValueFactory<TypeOfRoom, Float>("pricePerDay"));
        colIdType.setCellValueFactory(new PropertyValueFactory<TypeOfRoom, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<TypeOfRoom, String>("name"));

        colIdRoom.setCellValueFactory(new PropertyValueFactory<Room, String>("id"));
        colType.setCellValueFactory((p) -> {
            int id = p.getValue().getIdTypeofRoom();
            return new SimpleStringProperty(typeDao.selectById(id).getName());
        });

        colStatus.setCellValueFactory((p) -> {
            int id = p.getValue().getStatus();
            String result = statusList.get(id);
            return new SimpleStringProperty(result);
        });

        cboStatus.setItems(statusList);
        tblRoom.setItems(roomList);
        tblType.setItems(typeList);
        fillCboType();
    }

    public void fillCboType() {
        cboType.getItems().clear();
        for (TypeOfRoom x : typeList) {
            nameTypeList.add(x.getName());
        }
        cboType.setItems(nameTypeList);
    }

    private Room checkInputRoom() {
        String id = txtId.getText();
        if (id.isEmpty()) {
            Msg.printErr("Vui long nhap day du thong tin");
            return null;
        }
        int stage = cboStatus.getSelectionModel().getSelectedIndex();
        String nameType = cboType.getSelectionModel().getSelectedItem();
        int type = cboType.getSelectionModel().getSelectedIndex();
        if (stage < 0 || type < 0) {
            Msg.printErr("Please choose status and type room");
            return null;
        }
        for (int i = 0; i < typeList.size(); i++) {
            if (typeList.get(i).getName().equals(nameType)) {
                type = typeList.get(i).getId();
                break;
            }
        }
        return new Room(id, stage, type);
    }

    private TypeOfRoom checkInputType() {
        String name = txtType.getText();
        if (name.isEmpty() || txtHour.getText().isEmpty() || txtDate.getText().isEmpty()) {
            Msg.printErr("Vui long nhap day du thong tin");
            return null;
        }
        float day;
        float hour;
        try {
            hour = Float.parseFloat(txtHour.getText());
            day = Float.parseFloat(txtDate.getText());
        } catch (Exception e) {
            Msg.printErr("price Hour or price Day is a number");
            return null;
        }
        return new TypeOfRoom(name, hour, day);
    }

    private void fillRoom() {
        Room room = tblRoom.getSelectionModel().getSelectedItem();
        int index = tblRoom.getSelectionModel().getSelectedIndex();
        txtId.setText(room.getId());
        cboStatus.getSelectionModel().select(colStatus.getCellData(index));
        cboType.getSelectionModel().select(colType.getCellData(index));
    }

    private void fillType() {
        TypeOfRoom room = tblType.getSelectionModel().getSelectedItem();
        txtDate.setText(room.getPricePerDay() + "");
        txtType.setText(room.getName());
        txtHour.setText(room.getHourlyPrice() + "");
        id = room.getId();
    }

    private void fillTtbRoom() {
        roomList.clear();
        roomList.addAll(roomDao.selectAll());
        refrechRoom();
    }

    private void fillTtbType() {
        typeList.clear();
        typeList.addAll(typeDao.selectAll());
        refrechType();
    }

    private void refrechRoom() {
        cboStatus.getSelectionModel().select(-1);
        cboType.getSelectionModel().select(-1);
        txtId.setText("");
    }

    private void refrechType() {
        txtDate.setText("");
        txtHour.setText("");
        txtType.setText("");
    }
}
