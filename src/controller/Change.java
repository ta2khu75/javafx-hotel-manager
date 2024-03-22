package controller;

import dao.StaffDAO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import model.Staff;
import util.Auth;
import util.Msg;

public class Change {
    private Staff staff=Auth.get();
    @FXML
    private PasswordField txtConfir;

    @FXML
    private PasswordField txtNew;

    @FXML
    private PasswordField txtOld;

    @FXML
    void changePass(MouseEvent event) {
        String old = txtOld.getText();
        String neww = txtNew.getText();
        String confirm = txtConfir.getText();
        if (old.isEmpty() || neww.isEmpty() || confirm.isEmpty()) {
            Msg.printWarn("Vui lòng nhập đầy đủ thông tin");
        } else {
            if (old.equals(staff.getPassword())) {
                if (neww.equals(confirm)) {
                    staff.setPassword(neww);
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.update(staff);
                    Msg.printInfo("Đổi mật khẩu thành công");
                } else {
                    Msg.printWarn("Mật khẩu mới và xác nhận mật khẩu phải giống nhau");
                }
            } else {
                Msg.printWarn("Mật khẩu cũ không đúng");
            }
        }
    }

}
