package co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

import co.edu.uniquindio.proyectofinalp2.Model.Dealer;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de generar y manejar las métricas del sistema.
 * RF-013: Panel de métricas.
 * RF-014: Visualización de métricas con JavaFX Charts.
 */
public class MetricManagement {

    private final List<Shipment> shipments;
    private final List<Dealer> dealers;

    public MetricManagement(List<Shipment> shipments, List<Dealer> dealers) {
        this.shipments = shipments;
        this.dealers = dealers;
    }
    private AdministratorController administratorController;
    // ======================================================
    // MÉTODOS DE CÁLCULO DE MÉTRICAS
    // ======================================================

    /** Calcula el tiempo promedio de entrega de los envíos completados. */
    public double calcularTiempoPromedioEntrega() {
        double totalTiempo = 0;
        int count = 0;

        for (Shipment s : shipments) {
            if (s.getEstimatedDeliveryDate() > 0) {
                totalTiempo += s.getEstimatedDeliveryDate();
                count++;
            }
        }
        return count > 0 ? totalTiempo / count : 0.0;
    }

    /** Calcula los ingresos totales por periodo (por ejemplo, mes o semana). */
    public Map<String, Double> calcularIngresosPorPeriodo() {
        Map<String, Double> ingresos = new HashMap<>();

        for (Shipment s : shipments) {
            String periodo = s.getPeriod(); // ejemplo: "Octubre 2025"
            ingresos.put(periodo, ingresos.getOrDefault(periodo, 0.0) + s.getPrice());
        }
        return ingresos;
    }

    /** Calcula el número de incidencias reportadas por zona. */
    public Map<String, Integer> calcularIncidenciasPorZona() {
        Map<String, Integer> incidencias = new HashMap<>();

        for (Shipment s : shipments) {
            if (s.getIncident() != null && !s.getIncident().isEmpty()) {
                String zona = s.getZone();
                incidencias.put(zona, incidencias.getOrDefault(zona, 0) + 1);
            }
        }
        return incidencias;
    }

    /** Calcula la frecuencia de servicios adicionales usados (por ejemplo, "Seguro", "Entrega exprés"). */
    public Map<String, Integer> calcularServiciosMasUsados() {
        Map<String, Integer> servicios = new HashMap<>();

        for (Shipment s : shipments) {
            for (String servicio : s.getAdditionalServices()) {
                servicios.put(servicio, servicios.getOrDefault(servicio, 0) + 1);
            }
        }
        return servicios;
    }

    // ======================================================
    // MÉTODOS DE VISUALIZACIÓN CON JAVAFX CHARTS
    // ======================================================

    /** Genera un gráfico de pastel con los servicios adicionales más usados. */
    public PieChart generarGraficoServicios() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        Map<String, Integer> servicios = calcularServiciosMasUsados();

        for (Map.Entry<String, Integer> entry : servicios.entrySet()) {
            data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        PieChart chart = new PieChart(data);
        chart.setTitle("Servicios Adicionales Más Usados");
        return chart;
    }

    /** Genera un gráfico de barras de ingresos por periodo. */
    public XYChart.Series<String, Number> generarGraficoIngresos() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos por periodo");

        Map<String, Double> ingresos = calcularIngresosPorPeriodo();
        for (Map.Entry<String, Double> entry : ingresos.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        return series;
    }

    /** Genera un gráfico de líneas para los tiempos promedio de entrega por repartidor. */
    public XYChart.Series<String, Number> generarGraficoTiemposPromedioPorDealer() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Tiempo promedio por repartidor");

        for (Dealer d : dealers) {
            double promedio = d.calcularTiempoPromedioEntregas();
            series.getData().add(new XYChart.Data<>(d.getFullname(), promedio));
        }

        return series;
    }

    public void setAdministradorController(AdministratorController administratorController) {
        this.administratorController = administratorController;
    }
}
