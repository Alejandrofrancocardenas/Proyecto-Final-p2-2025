package co.edu.uniquindio.proyectofinalp2.ViewController.UserViewControllers;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.Model.ShippingStatus;
import co.edu.uniquindio.proyectofinalp2.service.UserService;
import co.edu.uniquindio.proyectofinalp2.ViewController.ServiceInjectable;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.List;

public class ShipmentTableController implements ServiceInjectable<UserService>, Initializable {

    // --- Campos FXML ---
    @FXML
    private TableView<Shipment> shipmentTableView;
    @FXML
    private TableColumn<Shipment, String> colId;
    @FXML
    private TableColumn<Shipment, String> colOrigin;
    @FXML
    private TableColumn<Shipment, String> colDestination;
    @FXML
    private TableColumn<Shipment, String> colStatus;
    @FXML
    private TableColumn<Shipment, String> colDate;
    @FXML
    private TableColumn<Shipment, String> colPrice;

    // --- Dependencias ---
    private UserService userService;
    private final ObservableList<Shipment> shipmentList = FXCollections.observableArrayList();

    // -------------------------------------------------------------------------
    // 1. INYECCIÓN DE DEPENDENCIA Y CICLO DE VIDA
    // -------------------------------------------------------------------------

    @Override
    public void setService(UserService service) {
        this.userService = service;
        loadShipments();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableView();
        shipmentTableView.setItems(shipmentList);
    }

    // -------------------------------------------------------------------------
    // 2. CONFIGURACIÓN DE LA TABLA
    // -------------------------------------------------------------------------

    private void setupTableView() {
        colId.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));

        // ✅ Origen: Maneja el caso en que getOriginAddress() sea null.
        colOrigin.setCellValueFactory(cellData -> {
            Address origin = cellData.getValue().getOriginAddress();
            return new SimpleStringProperty(origin != null ? origin.getCity() : "Dirección N/A");
        });

        // ✅ Destino: Maneja el caso en que getDestinationAddress() sea null.
        colDestination.setCellValueFactory(cellData -> {
            Address destination = cellData.getValue().getDestinationAddress();
            return new SimpleStringProperty(destination != null ? destination.getCity() : "Dirección N/A");
        });

        // 🟢 CORRECCIÓN: Estado. Maneja el caso en que getStatus() sea null.
        colStatus.setCellValueFactory(cellData -> {
            ShippingStatus status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status != null ? status.toString() : "INDEFINIDO");
        });

        // Fecha: Se mantiene la verificación de nulo.
        colDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreationDate() != null ?
                        cellData.getValue().getCreationDate().toLocalDate().toString() : "N/A")
        );

        // Precio: Se mantiene la verificación de nulo.
        colPrice.setCellValueFactory(cellData -> {
            Shipment shipment = cellData.getValue();
            if (shipment.getRate() != null) {
                return new SimpleStringProperty(String.format("$ %,.2f", shipment.getPrice()));
            }
            return new SimpleStringProperty("Pendiente");
        });

        shipmentTableView.setPlaceholder(new Label("No hay solicitudes de envío en su lista temporal ni histórica."));
    }

    // -------------------------------------------------------------------------
    // 3. CARGA DE DATOS
    // -------------------------------------------------------------------------

    private void loadShipments() {
        if (userService != null) {
            List<Shipment> userPendingShipments = userService.getCurrentUser().getShipments();

            // 🟢 CORRECCIÓN TEMPORAL PARA DATOS VIEJOS:
            // Asegura que cualquier envío sin estado se corrija al cargar.
            for (Shipment shipment : userPendingShipments) {
                if (shipment.getStatus() == null) {
                    // Si es nulo, asígnale el estado inicial correcto
                    shipment.setStatus(ShippingStatus.CREATED);
                }
            }
            // ----------------------------------------------------

            shipmentList.setAll(userPendingShipments);
            // ...
        }
        // ...
    }
    // -------------------------------------------------------------------------
    // 4. MÉTODOS DE GESTIÓN (EJ. Cancelar)
    // -------------------------------------------------------------------------

    @FXML
    private void onCancelShipment() {
        Shipment selectedShipment = shipmentTableView.getSelectionModel().getSelectedItem();

        if (selectedShipment == null) {
            mostrarAlerta("Advertencia", "Seleccione un envío para cancelar.", Alert.AlertType.WARNING);
            return;
        }

        // 🟢 CORRECCIÓN LÓGICA: Se usan los estados CREADO o PENDIENTE DE PAGO
        // para la cancelación, que coinciden con el mensaje de advertencia.
        if (selectedShipment.getStatus() == ShippingStatus.CREATED || selectedShipment.getStatus() == ShippingStatus.IN_TRANSIT) {

            String mensajeConfirmacion = "¿Está seguro de que desea cancelar el envío " + selectedShipment.getShipmentId() + "? Esta acción no se puede deshacer.";

            if (confirmarAccion("Confirmar Cancelación", mensajeConfirmacion)) {
                try {
                    userService.cancelShipment(selectedShipment.getShipmentId());
                    loadShipments(); // Recargar la tabla
                    mostrarAlerta("Éxito", "El envío ha sido cancelado exitosamente.", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    mostrarAlerta("Error", "No se pudo cancelar el envío: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Solo se pueden cancelar envíos en estado CREADO o PENDIENTE DE PAGO.", Alert.AlertType.WARNING);
        }
    }

    // -------------------------------------------------------------------------
    // 5. MÉTODOS AUXILIARES DE ALERTA (Implementados aquí)
    // -------------------------------------------------------------------------

    /**
     * Muestra una alerta simple.
     * @param titulo Título de la ventana de alerta.
     * @param contenido Mensaje principal de la alerta.
     * @param tipo Tipo de alerta (ERROR, WARNING, INFORMATION).
     */
    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    /**
     * Muestra un diálogo de confirmación.
     * @param titulo Título de la ventana de confirmación.
     * @param mensaje Mensaje de la pregunta.
     * @return true si el usuario presiona OK, false en caso contrario.
     */
    private boolean confirmarAccion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}