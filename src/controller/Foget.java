package controller;

import dao.StaffDAO;
import java.util.Properties;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Staff;
import util.Fx;
import util.Msg;

public class Foget {

    int number = 0;
    StaffDAO staffDAO = new StaffDAO();
    Staff send;
    Staff change;

    @FXML
    private TextField txtCode;

    @FXML
    private PasswordField txtConfir;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private PasswordField txtPass;

    @FXML
    void sendEmail(MouseEvent event) {
        if (txtId.getText().isEmpty()) {
            Msg.printErr("Vui lòng nhập tên đang nhập");
            return;
        }
        if (txtEmail.getText().isEmpty()) {
            Msg.printErr("Vui lòng nhập đia chỉ email");
        } else {
            send = staffDAO.selectById(txtId.getText());
            if (send == null) {
                Msg.printErr("Tên tài khoản không tồn tại");
                return;
            }
            if (!txtEmail.getText().equals(send.getEmail())) {
                Msg.printErr("Tài khoản không tồn tại email này");
                return;
            }
            final String username = "boomkings474@gmail.com";
            final String password = "plvy vcph ixtr fxec"; // Mật khẩu ứng dụng

            // Cài đặt cấu hình email server
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Tạo một phiên làm việc
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            try {
                // Tạo đối tượng MimeMessage
                Message message = new MimeMessage(session);

                // Đặt thông tin người gửi và người nhận
                message.setFrom(new InternetAddress("boomkings474@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(txtEmail.getText()));
                message.setSubject("Mã xác nhận");
                Random random = new Random();
                number = random.nextInt(9000) + 1000;
                message.setText(number + "");

                // Gửi email
                Transport.send(message);
                Msg.printInfo("Đã gữi");

            } catch (MessagingException e) {
                e.printStackTrace();
                Msg.printErr("Gữi thất bại");
            }
        }
    }

    @FXML
    void changePass(MouseEvent event) {
        String email = txtEmail.getText();
        String id = txtId.getText();
        String code = txtCode.getText();
        String pass=txtPass.getText();
        String confir=txtConfir.getText();
        if (email.isEmpty() || id.isEmpty() || code.isEmpty() || pass.isEmpty() || confir.isEmpty()) {
            Msg.printWarn("Vui lòng nhập đầy đủ thông tin");

        } else {
            if(number==0){
                Msg.printWarn("Vui lòng nhân gữi để lấy mã xác nhận trước");
                return;
            }            

            change = staffDAO.selectById(txtId.getText());
            if ( change==null || !change.getId().equals(send.getId())) {
                Msg.printErr("Đổi mật khẩu thất bại");
                return;
            }
            int cod;
            try {
                cod = Integer.parseInt(code);
            } catch (Exception e) {
                Msg.printWarn("Mã xác nhân không đúng");
                return;
            }
            if (cod == number) {
                if (pass.equals(confir)) {
                    change.setPassword(pass);
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.update(change);
                    number=0;
                    Msg.printInfo("Đổi mật khẩu thành công");
                } else {
                    Msg.printWarn("Mật khẩu mới và xác nhận mật khẩu phải giống nhau");
                }
            } else {
                Msg.printWarn("Mã xác nhân không đúng");
            }
        }
    }

    @FXML
    void goToSignIn(MouseEvent event) {
        Fx.close(txtCode);
        Fx.show("/view/login");
    }

}
