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

    private UserService userService;
    private final ObservableList<Shipment> shipmentList = FXCollections.observableArrayList();



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

    private void setupTableView() {
        colId.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));

        colOrigin.setCellValueFactory(cellData -> {
            Address origin = cellData.getValue().getOriginAddress();
            return new SimpleStringProperty(origin != null ? origin.getCity() : "Dirección N/A");
        });
        colDestination.setCellValueFactory(cellData -> {
            Address destination = cellData.getValue().getDestinationAddress();
            return new SimpleStringProperty(destination != null ? destination.getCity() : "Dirección N/A");
        });

        colStatus.setCellValueFactory(cellData -> {
            ShippingStatus status = cellData.getValue().getStatus();
            return new SimpleStringProperty(status != null ? status.toString() : "INDEFINIDO");
        });

        colDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreationDate() != null ?
                        cellData.getValue().getCreationDate().toLocalDate().toString() : "N/A")
        );

        colPrice.setCellValueFactory(cellData -> {
            Shipment shipment = cellData.getValue();
            if (shipment.getRate() != null) {
                return new SimpleStringProperty(String.format("$ %,.2f", shipment.getPrice()));
            }
            return new SimpleStringProperty("Pendiente");
        });

        shipmentTableView.setPlaceholder(new Label("No hay solicitudes de envío en su lista temporal ni histórica."));
    }


    private void loadShipments() {
        if (userService != null) {
            List<Shipment> userPendingShipments = userService.getCurrentUser().getShipments();

            for (Shipment shipment : userPendingShipments) {
                if (shipment.getStatus() == null) {
                    shipment.setStatus(ShippingStatus.CREATED);
                }
            }

            shipmentList.setAll(userPendingShipments);
        }
    }

    @FXML
    private void onCancelShipment() {
        Shipment selectedShipment = shipmentTableView.getSelectionModel().getSelectedItem();

        if (selectedShipment == null) {
            mostrarAlerta("Advertencia", "Seleccione un envío para cancelar.", Alert.AlertType.WARNING);
            return;
        }

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

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private boolean confirmarAccion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}