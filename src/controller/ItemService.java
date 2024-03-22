package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Service;

public class ItemService {
    public static Stage stage=new Stage();
    @FXML
    private Label lblItem;

    @FXML
    private Label lblPrice;
    DetailBooking detailBooking;
    Service service;
    
    void setData(Service service, DetailBooking detailBooking) {
        lblItem.setText(service.getName());
        lblPrice.setText("Price: " + service.getPrice());
        this.detailBooking = detailBooking;
        this.service = service;
    }
    @FXML
    void openOption(MouseEvent event) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/quantity.fxml"));
        Scene scene = new Scene(load.load());
        stage.setScene(scene);
        Quantity controller = load.getController();
        controller.setData(service, detailBooking);
        stage.show();
    }
}
