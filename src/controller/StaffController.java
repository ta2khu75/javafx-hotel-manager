package controller;

import dao.StaffDAO;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Staff;
import util.Msg;

public class StaffController implements Initializable {

    @FXML
    private TableColumn<Staff, String> colEmail;

    @FXML
    private TableColumn<Staff, String> colId;

    @FXML
    private TableColumn<Staff, String> colName;

    @FXML
    private TableColumn<Staff, String> colRole;

    @FXML
    private RadioButton rdoManager;

    @FXML
    private RadioButton rdoStaff;

    @FXML
    private ToggleGroup role;

    @FXML
    private TableView<Staff> tblStaff;

    @FXML
    private PasswordField txtConf;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPass;

    @FXML
    void delete(MouseEvent event) {
        Staff staff = checkInput();
        if (staff != null) {
            staffDAO.delete(staff);
            Msg.printInfo("Xoa thanh cong");
            refresh();
            fillTable();
        }
    }

    @FXML
    void homePage(MouseEvent event) {

    }

    @FXML
    void insert(MouseEvent event) {
        Staff staff = checkInput();
        if (staff != null) {
            staffDAO.insert(staff);
            Msg.printInfo("Them thanh cong");
            refresh();
            fillTable();
        }
    }

    @FXML
    void update(MouseEvent event) {
        Staff staff = checkInput();
        if (staff != null) {
            staffDAO.update(staff);
            Msg.printInfo("Cap nhap thanh cong");
            refresh();
            fillTable();
        }
    }
    private StaffDAO staffDAO = new StaffDAO();
    private ObservableList<Staff> list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FXCollections.observableArrayList(staffDAO.selectAll());
        colEmail.setCellValueFactory(new PropertyValueFactory<Staff, String>("email"));
        colId.setCellValueFactory(new PropertyValueFactory<Staff, String>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Staff, String>("fullName"));
        colRole.setCellValueFactory((p) -> {
            boolean b = p.getValue().isRole();
            String role = b ? "Manager" : "Staff";
            return new SimpleStringProperty(role);
        });
        tblStaff.setItems(list);
    }

    private Staff checkInput() {
        String id = txtId.getText();
        String name = txtName.getText();
        String pass = new String(txtPass.getText());
        String confir = new String(txtConf.getText());
        String email = txtEmail.getText();
        boolean role = rdoManager.isSelected() || rdoStaff.isSelected() ? false : true;
        if (id.isEmpty() || name.isEmpty() || pass.isEmpty() || confir.isEmpty() || email.isEmpty() || role) {
            Msg.printErr("Vui lòng nhập đầy đủ thông tin");
            return null;
        }
        if (pass.equals(confir)) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                return new Staff(id, name, email, pass, rdoManager.isSelected() ? true : false);
            }
            Msg.printErr("Email không hợp lệ\n"
                    + "VD hợp lệ: minh@gmail.com");
            return null;
        }
        Msg.printErr("Mật khẩu phải giống nhau");
        return null;
    }

    private void refresh() {
        txtId.setText("");
        txtPass.setText("");
        txtConf.setText("");
        txtEmail.setText("");
        txtName.setText("");
        rdoManager.setSelected(false);
        rdoStaff.setSelected(false);
    }
    @FXML
    void neww(MouseEvent event) {
        refresh();
    }

    private void fillTable() {
        list = FXCollections.observableArrayList(staffDAO.selectAll());
        tblStaff.setItems(list);
    }

    @FXML
    void getItem(MouseEvent event) {
        int index = tblStaff.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        Staff staff = list.get(index);
        txtId.setText(colId.getCellData(index));
        txtName.setText(colName.getCellData(index));
        txtEmail.setText(colEmail.getCellData(index));
        txtPass.setText(staff.getPassword());
        txtConf.setText(staff.getPassword());
        if (colRole.getCellData(index).equals("Quản lý")) {
            rdoManager.setSelected(true);
        } else {
            rdoStaff.setSelected(true);
        }

    }

}
