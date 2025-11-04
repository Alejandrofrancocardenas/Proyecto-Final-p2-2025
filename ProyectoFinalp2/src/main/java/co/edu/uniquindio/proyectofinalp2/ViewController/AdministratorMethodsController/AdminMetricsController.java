package co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

import co.edu.uniquindio.proyectofinalp2.service.AdminService;
import co.edu.uniquindio.proyectofinalp2.service.CompanyService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminMetricsController {

    @FXML
    private BarChart<String, Number> deliveryTimeChart;

    @FXML
    private PieChart servicesPieChart;

    @FXML
    private LineChart<String, Number> incomeLineChart;

    @FXML
    private BarChart<String, Number> incidenceBarChart;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {
        loadDeliveryTimeChart();
        loadServicesPieChart();
        loadIncomeLineChart();
        loadIncidenceBarChart();
    }

    // =============== MÉTRICAS ===============

    private void loadDeliveryTimeChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Promedio de entrega (horas)");

        adminService.getAverageDeliveryTimeByZone().forEach((zone, avgTime) ->
                series.getData().add(new XYChart.Data<>(zone, avgTime))
        );

        deliveryTimeChart.getData().add(series);
    }

    private void loadServicesPieChart() {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        adminService.getMostUsedAdditionalServices().forEach((service, count) ->
                pieData.add(new PieChart.Data(service, count))
        );

        servicesPieChart.setData(pieData);
    }

    private void loadIncomeLineChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos por periodo");

        adminService.getIncomeByPeriod().forEach((period, income) ->
                series.getData().add(new XYChart.Data<>(period, income))
        );

        incomeLineChart.getData().add(series);
    }

    private void loadIncidenceBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Incidencias por zona");

        adminService.getIncidencesByZone().forEach((zone, count) ->
                series.getData().add(new XYChart.Data<>(zone, count))
        );

        incidenceBarChart.getData().add(series);
    }
}
