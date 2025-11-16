package co.edu.uniquindio.proyectofinalp2.ViewController.DealerViewController;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.service.DealerService;
import co.edu.uniquindio.proyectofinalp2.ViewController.ServiceInjectable;
import co.edu.uniquindio.proyectofinalp2.ViewController.DealerDataInjectable; // ⬅️ IMPORTAR
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class DealerShipmentController implements Initializable,
        ServiceInjectable<DealerService>,
        DealerDataInjectable {  // ⬅️ AÑADIR ESTA INTERFAZ

    private DealerService dealerService;
    private Dealer currentDealer;

    private final ObservableList<Shipment> shipmentsData = FXCollections.observableArrayList();

    @FXML private TableView<Shipment> tablaEnvios;
    @FXML private TableColumn<Shipment, String> colShipmentId;
    @FXML private TableColumn<Shipment, String> colCliente;
    @FXML private TableColumn<Shipment, String> colOrigen;
    @FXML private TableColumn<Shipment, String> colDestino;
    @FXML private TableColumn<Shipment, String> colEstado;
    @FXML private TableColumn<Shipment, String> colFecha;

    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private ComboBox<String> cmbNuevoEstado;
    @FXML private Label lblEnvioSeleccionado;
    @FXML private TextArea txtIncidencia;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setupComboBoxes();

        tablaEnvios.setItems(shipmentsData);

        tablaEnvios.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                lblEnvioSeleccionado.setText("ID: " + selected.getShipmentId());
            } else {
                lblEnvioSeleccionado.setText("Ninguno");
            }
        });

        System.out.println("✅ DealerShipmentController inicializado");
    }

    @Override
    public void setService(DealerService service) {
        this.dealerService = service;
        System.out.println("✅ DealerService inyectado");
    }

    @Override
    public void setDealer(Dealer dealer) {
        this.currentDealer = dealer;
        System.out.println("✅ Dealer establecido en Shipments: " + dealer.getFullname());
        System.out.println("📦 Total envíos asignados: " +
                (dealer.getAssignedShipments() != null ? dealer.getAssignedShipments().size() : 0));
        cargarEnvios();
    }


    private void setupTable() {
        colShipmentId.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));

        colCliente.setCellValueFactory(cellData -> {
            User user = cellData.getValue().getUser();
            return new SimpleStringProperty(user != null ? user.getFullname() : "N/A");
        });

        colOrigen.setCellValueFactory(cellData -> {
            Address addr = cellData.getValue().getOriginAddress();
            return new SimpleStringProperty(addr != null ? addr.getCity() : "N/A");
        });

        colDestino.setCellValueFactory(cellData -> {
            Address addr = cellData.getValue().getDestinationAddress();
            return new SimpleStringProperty(addr != null ? addr.getCity() : "N/A");
        });

        colEstado.setCellValueFactory(cellData -> {
            ShippingStatus status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status != null ? status.toString() : "N/A");
        });

        colFecha.setCellValueFactory(cellData -> {
            var date = cellData.getValue().getCreationDate();
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                return new SimpleStringProperty(date.format(formatter));
            }
            return new SimpleStringProperty("N/A");
        });
    }

    private void setupComboBoxes() {

        cmbFiltroEstado.getItems().addAll(
                "Todos",
                "CREATED",
                "IN_TRANSIT",
                "DELIVERED",
                "INCIDENCE_REPORTED",
                "CANCELLED"
        );
        cmbFiltroEstado.getSelectionModel().select("Todos");


        cmbNuevoEstado.getItems().addAll(
                "IN_TRANSIT",
                "DELIVERED",
                "INCIDENCE_REPORTED"
        );
    }


    private void cargarEnvios() {
        if (currentDealer == null) {
            System.err.println("⚠️ No se puede cargar envíos: Dealer no establecido");
            return;
        }

        shipmentsData.clear();

        List<Shipment> enviosAsignados = currentDealer.getAssignedShipments();

        if (enviosAsignados == null || enviosAsignados.isEmpty()) {
            System.out.println("ℹ️ El repartidor no tiene envíos asignados");
            return;
        }


        String filtro = cmbFiltroEstado.getSelectionModel().getSelectedItem();
        List<Shipment> enviosFiltrados;

        if (filtro != null && !filtro.equals("Todos")) {
            ShippingStatus statusFiltro = ShippingStatus.valueOf(filtro);
            enviosFiltrados = enviosAsignados.stream()
                    .filter(s -> s.getStatus() == statusFiltro)
                    .collect(Collectors.toList());
        } else {
            enviosFiltrados = enviosAsignados;
        }

        shipmentsData.addAll(enviosFiltrados);

        System.out.println("✅ Cargados " + enviosFiltrados.size() + " envíos (de " +
                enviosAsignados.size() + " totales)");
    }

    @FXML
    private void onActualizarLista() {
        cargarEnvios();
        mostrarAlerta("Información", "Lista de envíos actualizada", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void onMarcarEnRuta() {
        Shipment selected = tablaEnvios.getSelectionModel().getSelectedItem();

        if (selected == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un envío", Alert.AlertType.WARNING);
            return;
        }

        selected.setStatus(ShippingStatus.IN_TRANSIT);

        tablaEnvios.refresh();

        mostrarAlerta("Éxito", "Envío marcado como EN RUTA", Alert.AlertType.INFORMATION);

        System.out.println("✅ Envío " + selected.getShipmentId() + " marcado como EN_TRANSIT");
    }

    @FXML
    private void onMarcarEntregado() {
        Shipment selected = tablaEnvios.getSelectionModel().getSelectedItem();

        if (selected == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un envío", Alert.AlertType.WARNING);
            return;
        }
        selected.setStatus(ShippingStatus.DELIVERED);

        if (currentDealer != null) {
            currentDealer.setDeliveriesMade(currentDealer.getDeliveriesMade() + 1);
            currentDealer.setAvailable(true); // Quedar disponible nuevamente
        }

        tablaEnvios.refresh();

        mostrarAlerta("Éxito",
                "¡Envío entregado exitosamente! 🎉\nTotal de entregas: " +
                        currentDealer.getDeliveriesMade(),
                Alert.AlertType.INFORMATION);

        System.out.println("✅ Envío " + selected.getShipmentId() + " marcado como DELIVERED");
    }

    @FXML
    private void onReportarIncidencia() {
        Shipment selected = tablaEnvios.getSelectionModel().getSelectedItem();
        String incidenciaTexto = txtIncidencia.getText();

        if (selected == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un envío", Alert.AlertType.WARNING);
            return;
        }

        if (incidenciaTexto == null || incidenciaTexto.trim().isEmpty()) {
            mostrarAlerta("Advertencia", "Debe describir la incidencia", Alert.AlertType.WARNING);
            return;
        }

        Incidence incidence = new Incidence();
        incidence.setIncidenceId("INC-" + selected.getShipmentId() + "-" + System.currentTimeMillis());
        incidence.setCreationDate(LocalDate.now());
        incidence.setDescription(incidenciaTexto);
        incidence.setType("REPORTADO_DEALER");
        incidence.setReporterId(currentDealer.getId());

        selected.setIncidence(incidence);
        selected.setStatus(ShippingStatus.INCIDENCE_REPORTED);

        if (currentDealer != null) {
            currentDealer.setAvailable(true);
        }

        txtIncidencia.clear();
        tablaEnvios.refresh();

        mostrarAlerta("Éxito",
                "Incidencia reportada correctamente.\nEl envío ha sido marcado para revisión.",
                Alert.AlertType.INFORMATION);

        System.out.println("⚠️ Incidencia reportada para envío " + selected.getShipmentId());
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}