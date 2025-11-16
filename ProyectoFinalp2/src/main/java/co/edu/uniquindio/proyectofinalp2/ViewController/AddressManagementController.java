package co.edu.uniquindio.proyectofinalp2.ViewController;

import co.edu.uniquindio.proyectofinalp2.service.UserService;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;
import co.edu.uniquindio.proyectofinalp2.Model.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.Optional;
import java.util.List;

public class AddressManagementController implements ServiceInjectable, Initializable {


    @FXML private TextField txtAlias;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtCalle;
    @FXML private TextField txtCodigoPostal;

    @FXML private TableView<Address> tablaDirecciones;
    @FXML private TableColumn<Address, String> colAlias;
    @FXML private TableColumn<Address, String> colCalle;
    @FXML private TableColumn<Address, String> colCiudad;
    @FXML private TableColumn<Address, String> colCodigoPostal;
    @FXML private TableColumn<Address, String> colID;

    private UserService userService;
    private ObservableList<Address> listaDirecciones;

    @Override
    @SuppressWarnings("unchecked")
    public void setService(Object service) {
        if (service instanceof UserService) {
            this.userService = (UserService) service;
            System.out.println("✅ DEBUG: setService llamado. Servicio de usuario INYECTADO correctamente.");
         try {
                cargarDirecciones();
            } catch (Exception e) {
                System.err.println("🔴 ERROR CRÍTICO: Falló la carga inicial de direcciones después de la inyección.");
                e.printStackTrace();
                mostrarAlerta("Error de Carga", "No se pudieron cargar las direcciones iniciales. Ver consola.", Alert.AlertType.ERROR);
            }
        } else {
            System.err.println("🔴 ERROR CRÍTICO: Objeto inyectado no es una instancia de UserService.");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("DEBUG: Initialize llamado. (userService es NULL, lo cual es correcto aquí).");

        configurarTabla();

        tablaDirecciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarDatosDireccion(newSel);
            } else {
                onLimpiarCampos();
            }
        });
    }

    private void configurarTabla() {
        listaDirecciones = FXCollections.observableArrayList();
        tablaDirecciones.setItems(listaDirecciones);
        colAlias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colCalle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStreet()));
        colCiudad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
        colCodigoPostal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostalCode()));
        colID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdAddress()));
    }

    private void cargarDirecciones() {
        if (userService != null) {
            System.out.println("DEBUG: Cargando direcciones con UserService...");
            listaDirecciones.setAll(userService.listAddresses());
            System.out.println("DEBUG: Carga de lista completada. Total: " + listaDirecciones.size());
        } else {
            System.err.println("🔴 ERROR: Falló la inyección en AddressManagementController. No se puede cargar la data.");
        }
    }

    private void cargarDatosDireccion(Address address) {
        txtAlias.setText(address.getName());
        txtCalle.setText(address.getStreet());
        txtCiudad.setText(address.getCity());
        txtCodigoPostal.setText(address.getPostalCode());
    }
    @FXML
    private void onAgregarDireccion() {
        String alias = txtAlias.getText();
        String calle = txtCalle.getText();
        String ciudad = txtCiudad.getText();
        String codigoPostal = txtCodigoPostal.getText();

        if (userService == null) {
            mostrarAlerta("Error", "El servicio no está inicializado. Falla de Inyección.", Alert.AlertType.ERROR);
            return;
        }

        if (alias.isEmpty() || calle.isEmpty() || ciudad.isEmpty() || codigoPostal.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios para agregar una dirección.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Address nuevaDireccion = new Address();
            nuevaDireccion.setIdAddress(UUID.randomUUID().toString());
            nuevaDireccion.setName(alias); // CRÍTICO: Usar setName para el alias
            nuevaDireccion.setStreet(calle);
            nuevaDireccion.setCity(ciudad);
            nuevaDireccion.setPostalCode(codigoPostal); // CRÍTICO: Usar setPostalCode

            userService.addAddressToUser(nuevaDireccion);


            cargarDirecciones();

            onLimpiarCampos();
            mostrarAlerta("Éxito", "Dirección agregada correctamente.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo agregar la dirección: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    @FXML
    private void onActualizarDireccion() {
        Address seleccionada = tablaDirecciones.getSelectionModel().getSelectedItem();

        if (userService == null || seleccionada == null) {
            mostrarAlerta("Advertencia", "Seleccione una dirección válida y revise la conexión.", Alert.AlertType.WARNING);
            return;
        }

        String alias = txtAlias.getText();
        String calle = txtCalle.getText();
        String ciudad = txtCiudad.getText();
        String codigoPostal = txtCodigoPostal.getText(); // CRÍTICO: Usar el campo de código postal

        if (alias.isEmpty() || calle.isEmpty() || ciudad.isEmpty() || codigoPostal.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar llenos para actualizar.", Alert.AlertType.WARNING);
            return;
        }

        try {

            seleccionada.setName(alias);
            seleccionada.setStreet(calle);
            seleccionada.setCity(ciudad);
            seleccionada.setPostalCode(codigoPostal);

            userService.updateAddress(seleccionada.getIdAddress(), seleccionada);

            tablaDirecciones.refresh();

            onLimpiarCampos();
            mostrarAlerta("Éxito", "Dirección actualizada correctamente.", Alert.AlertType.INFORMATION);

        } catch (NotFoundException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al actualizar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminarDireccion() {
        Address seleccionada = tablaDirecciones.getSelectionModel().getSelectedItem();

        if (userService == null || seleccionada == null) {
            mostrarAlerta("Advertencia", "Seleccione una dirección válida y revise la conexión.", Alert.AlertType.WARNING);
            return;
        }

        if (!confirmarAccion("Confirmar Eliminación", "¿Está seguro de que desea eliminar la dirección: " + seleccionada.getName() + "?")) {
            return;
        }

        try {
            userService.deleteAddress(seleccionada.getIdAddress());

            listaDirecciones.remove(seleccionada);

            onLimpiarCampos();
            mostrarAlerta("Éxito", "Dirección eliminada correctamente.", Alert.AlertType.INFORMATION);

        } catch (NotFoundException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error al eliminar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onLimpiarCampos() {
        txtAlias.clear();
        txtCalle.clear();
        txtCiudad.clear();
        txtCodigoPostal.clear();
        tablaDirecciones.getSelectionModel().clearSelection();
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
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