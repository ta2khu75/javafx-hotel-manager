/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package hotelmanager;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import util.Fx;

/**
 *
 * @author ta2khu75
 */
public class HotelManager extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(Fx.getScene("/view/login"));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}

