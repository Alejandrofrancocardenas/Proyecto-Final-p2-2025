package co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.service.AdminService;
import co.edu.uniquindio.proyectofinalp2.ViewController.ServiceInjectable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ShipmentManagement implements Initializable, ServiceInjectable<AdminService> {


    private AdminService adminService;

    private final ObservableList<Shipment> shipmentData = FXCollections.observableArrayList();
    private final ObservableList<Dealer> dealersData = FXCollections.observableArrayList();

    @FXML private TableView<Shipment> tablaEnvios;
    @FXML private TableColumn<Shipment, String> colEnvioTracking;
    @FXML private TableColumn<Shipment, String> colEnvioCliente;
    @FXML private TableColumn<Shipment, String> colEnvioDireccion;
    @FXML private TableColumn<Shipment, String> colEnvioEstado;
    @FXML private TableColumn<Shipment, String> colEnvioDealer;
    @FXML private Label lblEnvioSeleccionado;
    @FXML private Label lblDealerSeleccionado;
    @FXML private Button btnAsignarEnvio;
    @FXML private ComboBox<String> cmbNuevoEstado;
    @FXML private TextArea txtIncidencia;
    @FXML private TableView<Dealer> tablaRepartidores;
    @FXML private TableColumn<Dealer, String> colDealerNombre;
    @FXML private TableColumn<Dealer, String> colDealerDisponibilidad;


    @Override
    public void setService(AdminService service) {
        this.adminService = service;
        System.out.println("DIAGNÓSTICO: (1) Servicio AdminService inyectado. Procediendo a cargar datos.");
        cargarEnviosPendientes();
        cargarRepartidores();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DIAGNÓSTICO: (2) Método initialize ejecutado. Las columnas están configuradas.");

        setupShipmentTable();
        setupDealerTable();

        tablaEnvios.setItems(shipmentData);
        tablaRepartidores.setItems(dealersData);

        cmbNuevoEstado.getItems().addAll(
                "IN_TRANSIT",          // En ruta
                "DELIVERED",           // Entregado
                "INCIDENCE_REPORTED",  // Incidencia reportada
                "CANCELLED"            // Cancelado
        );

        tablaEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblEnvioSeleccionado.setText("Envío: " + newSelection.getShipmentId());
            } else {
                lblEnvioSeleccionado.setText("Envío: N/A");
            }
        });

        tablaRepartidores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblDealerSeleccionado.setText("Repartidor: " + newSelection.getFullname() +
                        " (" + (newSelection.isAvailable() ? "Disponible" : "Ocupado") + ")");
            } else {
                lblDealerSeleccionado.setText("Repartidor: N/A");
            }
        });

        System.out.println("DIAGNÓSTICO: (3) initialize finalizado. La carga de datos debe ser en setService().");
    }



    private void setupShipmentTable() {
        colEnvioTracking.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));
        colEnvioEstado.setCellValueFactory(cellData -> {
            ShippingStatus status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status != null ? status.toString() : "INDEFINIDO");
        });
        colEnvioCliente.setCellValueFactory(cellData -> {
            User user = cellData.getValue().getUser();
            return new SimpleStringProperty(user != null ? user.getFullname() : "N/A");
        });
        colEnvioDireccion.setCellValueFactory(cellData -> {
            Address address = cellData.getValue().getOriginAddress();
            return new SimpleStringProperty(address != null ? address.toString() : "N/A");
        });
        colEnvioDealer.setCellValueFactory(cellData -> {
            Dealer dealer = cellData.getValue().getAssignedDealer();
            return new SimpleStringProperty(dealer != null ? dealer.getFullname() : "Pendiente");
        });
    }

    private void setupDealerTable() {
        colDealerNombre.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        colDealerDisponibilidad.setCellValueFactory(cellData -> {
            Dealer dealer = cellData.getValue();
            String status = dealer.isAvailable() ? "SÍ" : "NO";
            return new SimpleStringProperty(status);
        });
    }

    private void cargarRepartidores() {
        if (adminService == null) {
            System.err.println("DIAGNÓSTICO: ERROR CRÍTICO: adminService es null en cargarRepartidores.");
            return;
        }

        dealersData.clear();

        List<Dealer> loadedDealers = adminService.getCompany().getDealers();

        if (loadedDealers == null) {
            System.err.println("DIAGNÓSTICO: ERROR: La lista de repartidores obtenida de Company es null.");
            return;
        }

        System.out.println("DIAGNÓSTICO: (4) Se encontraron " + loadedDealers.size() + " repartidores en la Company.");

        List<Dealer> availableDealers = loadedDealers.stream()
                .filter(Dealer::isAvailable)
                .collect(Collectors.toList());

        System.out.println("DIAGNÓSTICO: De los " + loadedDealers.size() + " repartidores, "
                + availableDealers.size() + " están DISPONIBLES.");

        if (!availableDealers.isEmpty()) {
            Dealer firstDealer = availableDealers.get(0);
            System.out.println("DIAGNÓSTICO: Primer Repartidor Disponible: Nombre=" + firstDealer.getFullname() +
                    ", Disponible=" + firstDealer.isAvailable());
        } else {
            System.out.println("DIAGNÓSTICO: ⚠️ No hay repartidores DISPONIBLES. Asegúrate de marcar el checkbox 'Disponible' al crearlos.");
        }


        dealersData.addAll(availableDealers);

        System.out.println("DIAGNÓSTICO: (5) ObservableList de Repartidores tiene " + dealersData.size() + " elementos DISPONIBLES.");
    }

    private void cargarEnviosPendientes() {
        if (adminService == null) {
            System.err.println("DIAGNÓSTICO ENVÍOS: ERROR - adminService es null");
            return;
        }

        shipmentData.clear();

        List<Shipment> allShipments = adminService.getCompany().getShipments();

        if (allShipments == null) {
            System.err.println("DIAGNÓSTICO ENVÍOS: ERROR - La lista de envíos es null");
            return;
        }

        System.out.println("DIAGNÓSTICO ENVÍOS: (1) Total de envíos en Company: " + allShipments.size());


        if (!allShipments.isEmpty()) {
            System.out.println("DIAGNÓSTICO ENVÍOS: (2) Listado de todos los envíos:");
            for (Shipment s : allShipments) {
                System.out.println("  - ID: " + s.getShipmentId() +
                        ", Estado: " + s.getStatus() +
                        ", Usuario: " + (s.getUser() != null ? s.getUser().getFullname() : "N/A"));
            }
        }


        List<Shipment> pending = allShipments.stream()
                .filter(s -> s.getStatus() == ShippingStatus.CREATED ||
                        s.getStatus() == ShippingStatus.IN_TRANSIT ||
                        s.getStatus() == ShippingStatus.PENDING_PICKUP)
                .collect(Collectors.toList());

        System.out.println("DIAGNÓSTICO ENVÍOS: (3) Envíos pendientes filtrados: " + pending.size());

        if (pending.isEmpty()) {
            System.out.println("⚠️ DIAGNÓSTICO ENVÍOS: No hay envíos con estados CREATED, IN_TRANSIT o PENDING_PICKUP");
            System.out.println("⚠️ Verifica que los envíos se estén creando con uno de estos estados.");
        }

        shipmentData.addAll(pending);

        System.out.println("DIAGNÓSTICO ENVÍOS: (4) ObservableList de Envíos tiene " + shipmentData.size() + " elementos");
    }



    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    private void onAsignarEnvio() {
        Shipment selectedShipment = tablaEnvios.getSelectionModel().getSelectedItem();
        Dealer selectedDealer = tablaRepartidores.getSelectionModel().getSelectedItem();

        if (selectedShipment == null || selectedDealer == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un Envío y un Repartidor.", Alert.AlertType.WARNING);
            return;
        }

        if (!selectedDealer.isAvailable() && selectedShipment.getAssignedDealer() == null) {
            mostrarAlerta("Advertencia", "El repartidor seleccionado no está disponible.", Alert.AlertType.WARNING);
            return;
        }

        try {
            boolean success = adminService.assignOrReassignShipment(selectedShipment.getShipmentId(), selectedDealer.getId());

            if (success) {
                mostrarAlerta("Éxito", "Envío " + selectedShipment.getShipmentId() + " asignado a " + selectedDealer.getFullname(), Alert.AlertType.INFORMATION);
                cargarEnviosPendientes();
                cargarRepartidores();
                tablaRepartidores.getSelectionModel().clearSelection();
                lblDealerSeleccionado.setText("Repartidor: N/A");
            } else {
                mostrarAlerta("Error", "Fallo al asignar. Verifique el estado del repartidor o si ya estaba asignado.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al procesar la asignación: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onActualizarEstado() {
        Shipment selectedShipment = tablaEnvios.getSelectionModel().getSelectedItem();
        String nuevoEstadoStr = cmbNuevoEstado.getSelectionModel().getSelectedItem();

        if (selectedShipment == null || nuevoEstadoStr == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un Envío y un nuevo Estado.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // ✅ CORREGIDO: valueOf ahora recibe los valores correctos del enum
            ShippingStatus newStatus = ShippingStatus.valueOf(nuevoEstadoStr);
            boolean success = adminService.updateShipmentStatus(selectedShipment.getShipmentId(), newStatus);

            if (success) {
                mostrarAlerta("Éxito", "Estado del envío " + selectedShipment.getShipmentId() + " actualizado a " + newStatus, Alert.AlertType.INFORMATION);
                cargarEnviosPendientes();
                cargarRepartidores();
            } else {
                mostrarAlerta("Error", "Fallo al actualizar el estado.", Alert.AlertType.ERROR);
            }

        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", "El estado seleccionado no es válido: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error inesperado al actualizar el estado: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegistrarIncidencia() {
        Shipment selectedShipment = tablaEnvios.getSelectionModel().getSelectedItem();
        String incidenciaText = txtIncidencia.getText();

        if (selectedShipment == null || incidenciaText.trim().isEmpty()) {
            mostrarAlerta("Advertencia", "Debe seleccionar un Envío y escribir la incidencia.", Alert.AlertType.WARNING);
            return;
        }

        LocalDate today = LocalDate.now();
        String incidenceId = "INC-" + selectedShipment.getShipmentId() + "-" + System.currentTimeMillis();
        String incidenceType = "REPORTADO_ADMIN";
        String adminId = "ADM001";

        try {
            Incidence newIncidence = new Incidence();
            newIncidence.setIncidenceId(incidenceId);
            newIncidence.setCreationDate(today);
            newIncidence.setDescription(incidenciaText);
            newIncidence.setType(incidenceType);
            newIncidence.setReporterId(adminId);

            boolean success = adminService.registerShipmentIncidence(selectedShipment.getShipmentId(), newIncidence);

            if (success) {
                mostrarAlerta("Éxito", "Incidencia registrada para el envío " + selectedShipment.getShipmentId() + " y estado actualizado a INCIDENCE_REPORTED.", Alert.AlertType.INFORMATION);
                cargarEnviosPendientes();
                cargarRepartidores();
                txtIncidencia.clear();
            } else {
                mostrarAlerta("Error", "Fallo al registrar la incidencia.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar la incidencia: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}