/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.StatisticDAO;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author ta2khu75
 */
public class Statistic implements Initializable {

    @FXML
    private PieChart pieService;
    @FXML
    private PieChart chartTypeRoom;

    StatisticDAO statisticDAO = new StatisticDAO();
    int totalService = statisticDAO.getTotalService();
    HashMap<String, Integer> totalServiceName = statisticDAO.getTotalServiceName();
    int totalRoom = statisticDAO.getTotalRoom();
    Map<String, Integer> totalTypeRoom = statisticDAO.getTotalTypeRoom();

    @FXML
    private StackedBarChart<String, Integer> chartRevenue;
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    List<Object[]> revenueDay = statisticDAO.getTotalDayOfWeek();
//    HashMap<String, Integer> revenueDay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Biểu đồ dịch vụ
        for (Map.Entry<String, Integer> entry : totalServiceName.entrySet()) {
            PieChart.Data data = new PieChart.Data(entry.getKey(), ((double) entry.getValue() / totalService) * 100);
            StringBuilder amount = new StringBuilder(String.format("_%.2f", data.getPieValue()));
            data.nameProperty().bind(Bindings.concat(data.getName(), amount.append("%")));
            pieService.getData().add(data);
        }

        //Biểu đồ kiểu phòng
        totalTypeRoom.forEach((t, u) -> {
            Data data = new Data(t, ((double) u / totalRoom) * 100);
            chartTypeRoom.getData().add(data);
        });

        //Biểu đồ doanh thu hằng ngày
        xAxis.setLabel("Doanh thu");
        yAxis.setLabel("Ngày");
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Doanh thu");
        for (int i = revenueDay.size() - 1; i >= 0; i--) {
            series.getData().add(new XYChart.Data<>(revenueDay.get(i)[0].toString(), (int)revenueDay.get(i)[1]));
        }
        chartRevenue.getData().add(series);
    }

}
