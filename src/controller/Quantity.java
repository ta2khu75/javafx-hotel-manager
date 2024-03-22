package controller;

import dao.ServiceRoomDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Service;
import model.ServiceRoom;
import util.Fx;
import util.Msg;

public class Quantity {

    private boolean role;
    @FXML
    private TextField txtQuantity;

    @FXML
    private Label lblItem;

    @FXML
    void agree(MouseEvent event) {
        if (role) {
            String quantityString = txtQuantity.getText();
            int quantity = 0;
            try {
                quantity = Integer.parseInt(quantityString);
            } catch (Exception e) {
                txtQuantity.setText("1");
                Msg.printErr("Quantity must be a number");
                return;
            }
            ServiceRoomDAO serviceRoomDAO = new ServiceRoomDAO();
            ServiceRoom serviceRoom = new ServiceRoom(detailBooking.getBooking().getId(), service.getId(), quantity);
            serviceRoomDAO.insert(serviceRoom);
            detailBooking.totalService();
            Fx.hide(lblItem);
        }else{
            String quantityString = txtQuantity.getText();
            int quantity = 0;
            try {
                quantity = Integer.parseInt(quantityString);
            } catch (Exception e) {
                txtQuantity.setText("1");
                Msg.printErr("Quantity must be a number");
                return;
            }
            ServiceRoomDAO serviceRoomDAO = new ServiceRoomDAO();
            serviceRoom.setQuantity(quantity);
            serviceRoomDAO.update(serviceRoom);
           detailBooking.totalService();
            Fx.hide(lblItem);
        }

    }

    @FXML
    void deduction(MouseEvent event) {
        String quantityString = txtQuantity.getText();
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (Exception e) {
            txtQuantity.setText("1");
            return;
        }
        if (quantity == 0) {
            txtQuantity.setText("0");
        } else {
            quantity--;
            txtQuantity.setText(quantity + "");
        }
    }

    @FXML
    void sum(MouseEvent event) {
        String quantityString = txtQuantity.getText();
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (Exception e) {
            txtQuantity.setText("1");
            return;
        }
        quantity++;
        txtQuantity.setText(quantity + "");
    }
    private Service service;
    private ServiceRoom serviceRoom;
    private DetailBooking detailBooking;

    public void setData(Service service, DetailBooking detailBooking) {
        this.service = service;
        this.detailBooking = detailBooking;
        this.role=true;
        lblItem.setText(this.service.getName());
    }
    public void setData(ServiceRoom service, String name, DetailBooking detailBooking) {
        this.serviceRoom = service;
        this.detailBooking = detailBooking;
        this.role=false;
        txtQuantity.setText(service.getQuantity()+"");
        lblItem.setText(name);
    }
}
