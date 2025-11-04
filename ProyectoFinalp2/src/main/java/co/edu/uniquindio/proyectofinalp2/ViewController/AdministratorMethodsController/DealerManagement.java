package co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

import co.edu.uniquindio.proyectofinalp2.Model.Dealer;
import co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controlador encargado de gestionar los Dealers (repartidores).
 * RF-011: Crear / Actualizar / Eliminar / Listar
 */
public class DealerManagement {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private CheckBox chkDisponible;

    @FXML
    private TableView<Dealer> tablaDealers;

    @FXML
    private TableColumn<Dealer, String> colNombre;

    @FXML
    private TableColumn<Dealer, String> colCorreo;

    @FXML
    private TableColumn<Dealer, String> colTelefono;

    @FXML
    private TableColumn<Dealer, Boolean> colDisponible;

    @FXML
    private TableColumn<Dealer, Integer> colEntregas;

    private final ObservableList<Dealer> listaDealers = FXCollections.observableArrayList();
    private AdministratorController administratorController;

    @FXML
    public void initialize() {
        configurarTabla();
        tablaDealers.setItems(listaDealers);

        // Evento para cargar datos al seleccionar un dealer
        tablaDealers.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarDatosDealer(newSel);
            }
        });
    }

    public void setAdministratorController(AdministratorController administratorController) {
        this.administratorController = administratorController;
    }

    /**
     * Configura las columnas de la tabla.
     */
    private void configurarTabla() {
        colNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFullname()));
        colCorreo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEmail()));
        colTelefono.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPhone()));
        colDisponible.setCellValueFactory(cellData ->
                new SimpleBooleanProperty(cellData.getValue().getAvaliable()));
        colEntregas.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getDeliveriesMade()).asObject());
    }

    /**
     * Agrega un nuevo dealer.
     */
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

        Dealer nuevoDealer = new Dealer.Builder()
                .name(nombre)
                .email(correo)
                .phone(telefono)
                .avaliable(disponible)
                .deliveriesMade(0)
                .build();

        listaDealers.add(nuevoDealer);
        limpiarCampos();
        mostrarAlerta("Éxito", "Dealer agregado correctamente.", Alert.AlertType.INFORMATION);
    }

    /**
     * Elimina el dealer seleccionado.
     */
    @FXML
    private void onEliminarDealer() {
        Dealer seleccionado = tablaDealers.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un dealer para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        listaDealers.remove(seleccionado);
        limpiarCampos();
        mostrarAlerta("Éxito", "Dealer eliminado correctamente.", Alert.AlertType.INFORMATION);
    }

    /**
     * Actualiza los datos del dealer seleccionado.
     */
    @FXML
    private void onActualizarDealer() {
        Dealer seleccionado = tablaDealers.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un dealer para actualizar.", Alert.AlertType.WARNING);
            return;
        }

        String nuevoNombre = txtNombre.getText().trim();
        String nuevoCorreo = txtCorreo.getText().trim();
        String nuevoTelefono = txtTelefono.getText().trim();
        boolean disponible = chkDisponible.isSelected();

        if (nuevoNombre.isEmpty() || nuevoCorreo.isEmpty() || nuevoTelefono.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        seleccionado.setFullname(nuevoNombre);
        seleccionado.setEmail(nuevoCorreo);
        seleccionado.setPhone(nuevoTelefono);
        seleccionado.setAvaliable(disponible);

        tablaDealers.refresh();
        limpiarCampos();
        mostrarAlerta("Éxito", "Dealer actualizado correctamente.", Alert.AlertType.INFORMATION);
    }

    /**
     * Limpia los campos del formulario.
     */
    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        chkDisponible.setSelected(false);
        tablaDealers.getSelectionModel().clearSelection();
    }

    /**
     * Carga los datos del dealer seleccionado en los campos de texto.
     */
    private void cargarDatosDealer(Dealer dealer) {
        txtNombre.setText(dealer.getFullname());
        txtCorreo.setText(dealer.getEmail());
        txtTelefono.setText(dealer.getPhone());
        chkDisponible.setSelected(dealer.getAvaliable());
    }

    /**
     * Muestra una alerta informativa.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
