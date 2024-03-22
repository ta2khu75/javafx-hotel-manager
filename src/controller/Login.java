package controller;

import dao.StaffDAO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Staff;
import util.Auth;
import util.Fx;
import util.Msg;

public class Login {

    @FXML
    private TextField txtId;

    @FXML
    private PasswordField txtPass;

    @FXML
    void forget(MouseEvent event) {
        Fx.close(txtId);
        Fx.show("/view/foget");
    }

    @FXML
    void loginKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    @FXML
    void loginMouse(MouseEvent event) {
        login();
    }
    
    final StaffDAO staffDao = new StaffDAO();
    private void login() {
        String id = txtId.getText();
        String pass = txtPass.getText();
        if (id.isEmpty() || pass.isEmpty()) {
            Msg.printWarn("Vui lòng nhập đầy đủ thông tin");
        } else {
            Staff staff = staffDao.selectById(id);
            if (staff == null) {
                Msg.printErr("Tài khoản không tồn tại");
            } else {
                if (!staff.getPassword().equals(pass)) {
                    Msg.printErr("Sai mật khẩu");
                } else {
                    Auth.set(staff);
                    Fx.show("/view/homePage");
                    Fx.close(txtId);

                }
            }
        }
        refresh();
    }

    private void refresh() {
        txtId.setText("");
        txtId.requestFocus();
        txtPass.setText("");
    }
}
