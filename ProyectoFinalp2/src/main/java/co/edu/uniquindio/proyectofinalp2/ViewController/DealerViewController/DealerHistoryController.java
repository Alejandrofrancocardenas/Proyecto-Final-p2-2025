package co.edu.uniquindio.proyectofinalp2.ViewController.DealerViewController;

import co.edu.uniquindio.proyectofinalp2.Model.*;

import co.edu.uniquindio.proyectofinalp2.ViewController.DealerDataInjectable;
import co.edu.uniquindio.proyectofinalp2.service.DealerService;
import co.edu.uniquindio.proyectofinalp2.ViewController.ServiceInjectable;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DealerHistoryController implements Initializable, ServiceInjectable<DealerService>, DealerDataInjectable {

    private DealerService dealerService;
    private Dealer currentDealer;

    private final ObservableList<Shipment> historyData = FXCollections.observableArrayList();
    @FXML private TableView<Shipment> tableViewHistorial;
    @FXML private TableColumn<Shipment, String> colShipmentId;
    @FXML private TableColumn<Shipment, String> colCliente;
    @FXML private TableColumn<Shipment, String> colDestino;
    @FXML private TableColumn<Shipment, String> colEstadoFinal;
    @FXML private TableColumn<Shipment, String> colFecha;
    @FXML private Button btnRefresh;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        tableViewHistorial.setItems(historyData);
        System.out.println("✅ DealerHistoryController inicializado");
    }

    @Override
    public void setService(DealerService service) {
        this.dealerService = service;
        System.out.println("✅ DealerService inyectado en History");
    }

    @Override
    public void setDealer(Dealer dealer) {
        this.currentDealer = dealer;
        System.out.println("✅ Dealer establecido en History: " + dealer.getFullname());
        cargarHistorial();
    }

    private void setupTable() {
        colShipmentId.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));

        colCliente.setCellValueFactory(cellData -> {
            User user = cellData.getValue().getUser();
            return new SimpleStringProperty(user != null ? user.getFullname() : "N/A");
        });

        colDestino.setCellValueFactory(cellData -> {
            Address addr = cellData.getValue().getDestinationAddress();
            return new SimpleStringProperty(addr != null ? addr.getCity() : "N/A");
        });

        colEstadoFinal.setCellValueFactory(cellData -> {
            ShippingStatus status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status != null ? status.toString() : "N/A");
        });

        colFecha.setCellValueFactory(cellData -> {
            var date = cellData.getValue().getCreationDate();
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return new SimpleStringProperty(date.format(formatter));
            }
            return new SimpleStringProperty("N/A");
        });
    }

    private void cargarHistorial() {
        if (currentDealer == null) {
            System.err.println("⚠️ No se puede cargar historial: Dealer no establecido");
            return;
        }

        historyData.clear();
        List<Shipment> enviosAsignados = currentDealer.getAssignedShipments();

        if (enviosAsignados == null || enviosAsignados.isEmpty()) {
            System.out.println("ℹ️ El repartidor no tiene envíos en su historial");
            return;
        }

        List<Shipment> enviosFiltrados = enviosAsignados.stream()
                .filter(s -> s.getStatus() == ShippingStatus.DELIVERED ||
                        s.getStatus() == ShippingStatus.CANCELLED ||
                        s.getStatus() == ShippingStatus.INCIDENCE_REPORTED)
                .collect(Collectors.toList());

        historyData.addAll(enviosFiltrados);

        System.out.println("✅ Cargados " + enviosFiltrados.size() + " envíos en el historial.");
    }

    @FXML
    private void onActualizarLista() {
        cargarHistorial();
        mostrarAlerta("Información", "Historial actualizado", Alert.AlertType.INFORMATION);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}