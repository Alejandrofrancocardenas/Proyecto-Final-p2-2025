package co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

import co.edu.uniquindio.proyectofinalp2.Model.Dealer;
import co.edu.uniquindio.proyectofinalp2.service.DealerService;
import co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
public class DealerManagement implements Initializable {

    private final DealerService dealerService = DealerService.getInstance();

    private ObservableList<Dealer> listaDealers;

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private CheckBox chkDisponible;
    @FXML private TableView<Dealer> tablaDealers;
    @FXML private TableColumn<Dealer, String> colNombre;
    @FXML private TableColumn<Dealer, String> colCorreo;
    @FXML private TableColumn<Dealer, String> colTelefono;
    @FXML private TableColumn<Dealer, Boolean> colDisponible;
    @FXML private TableColumn<Dealer, Integer> colEntregas;

    private AdministratorController administratorController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Dealer> dealersFromModel = dealerService.listAllDealers();
        this.listaDealers = FXCollections.observableArrayList(dealersFromModel);

        configurarTabla();
        tablaDealers.setItems(listaDealers);

        tablaDealers.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarDatosDealer(newSel);
            }
        });
    }

    public void setAdministratorController(AdministratorController administratorController) {
        this.administratorController = administratorController;
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFullname()));
        colCorreo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));
        colTelefono.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPhone()));
        colDisponible.setCellValueFactory(cellData ->
                new SimpleBooleanProperty(cellData.getValue().getAvailable()));
        colEntregas.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getDeliveriesMade()).asObject());
    }
private boolean existeDealerConCorreo(String correo) {
        return listaDealers.stream()
                .anyMatch(dealer -> dealer.getEmail().equalsIgnoreCase(correo));
    }


    @FXML
    private void onAgregarDealer() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        boolean disponible = chkDisponible.isSelected();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa todos los campos.", Alert.AlertType.WARNING);
            return;
        }

        if (existeDealerConCorreo(correo)) {
            mostrarAlerta("Error de Validación", "Ya existe un repartidor registrado con el correo: " + correo, Alert.AlertType.ERROR);
            return;
        }
        Dealer nuevoDealer = new Dealer.Builder()
                .name(nombre)
                .email(correo)
                .phone(telefono)
                .available(disponible)
                .deliveriesMade(0)
                .build();


        boolean agregado = dealerService.addDealer(nuevoDealer);

        if (agregado) {

            listaDealers.add(nuevoDealer);
            limpiarCampos();
            mostrarAlerta("Éxito", "Dealer agregado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo agregar el Dealer. Verifique ID/Correo.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onEliminarDealer() {
        Dealer seleccionado = tablaDealers.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un dealer para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        boolean eliminado = dealerService.deleteDealer(seleccionado.getId());

        if (eliminado) {
            listaDealers.remove(seleccionado);
            limpiarCampos();
            mostrarAlerta("Éxito", "Dealer eliminado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el Dealer del modelo.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void onActualizarDealer() {
        Dealer seleccionado = tablaDealers.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un dealer para actualizar.", Alert.AlertType.WARNING);
            return;
        }
        Dealer dealerActualizado = new Dealer.Builder()
                .id(seleccionado.getId())
                .name(txtNombre.getText().trim())
                .email(txtCorreo.getText().trim())
                .phone(txtTelefono.getText().trim())
                .available(chkDisponible.isSelected())
                .deliveriesMade(seleccionado.getDeliveriesMade())
                .build();

        if (!seleccionado.getEmail().equalsIgnoreCase(dealerActualizado.getEmail()) && existeDealerConCorreo(dealerActualizado.getEmail())) {
            mostrarAlerta("Error de Validación", "El nuevo correo ya está en uso por otro repartidor.", Alert.AlertType.ERROR);
            return;
        }
        boolean actualizado = dealerService.updateDealer(dealerActualizado);

        if (actualizado) {
            tablaDealers.refresh();
            limpiarCampos();
            mostrarAlerta("Éxito", "Dealer actualizado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo actualizar el Dealer en el modelo.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        chkDisponible.setSelected(false);
        tablaDealers.getSelectionModel().clearSelection();
    }

    private void cargarDatosDealer(Dealer dealer) {
        txtNombre.setText(dealer.getFullname());
        txtCorreo.setText(dealer.getEmail());
        txtTelefono.setText(dealer.getPhone());
        chkDisponible.setSelected(dealer.getAvailable());
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}