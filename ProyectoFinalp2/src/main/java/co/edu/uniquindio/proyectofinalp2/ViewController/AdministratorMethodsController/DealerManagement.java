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

/**
 * Controlador encargado de gestionar los Dealers (repartidores).
 * RF-011: Crear / Actualizar / Eliminar / Listar, con validación de correo único.
 */
public class DealerManagement implements Initializable {

    // 1. Instancia del servicio Singleton (ÚNICA FUENTE DE VERDAD)
    private final DealerService dealerService = DealerService.getInstance();

    // 2. La lista observable ahora estará vinculada a la lista del modelo
    private ObservableList<Dealer> listaDealers;

    // --- Componentes FXML ---
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

        // 3. OBTENER LA LISTA DE DEALERS DEL MODELO
        // ❌ Línea original que fallaba: List<Dealer> dealersFromModel = companyService.getCompany().getDealers();

        // ✅ CORRECCIÓN: Delegamos la obtención de la lista al DealerService.
        // Asumimos que DealerService tiene un método para obtener la lista del modelo central.
        List<Dealer> dealersFromModel = dealerService.listAllDealers();
        this.listaDealers = FXCollections.observableArrayList(dealersFromModel);

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

    // --- MÉTODOS DE LÓGICA DE NEGOCIO ---

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
                new SimpleBooleanProperty(cellData.getValue().getAvailable()));
        colEntregas.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getDeliveriesMade()).asObject());
    }

    /**
     * Verifica si ya existe un repartidor con el correo electrónico proporcionado.
     * Usa la lista observable actual.
     */
    private boolean existeDealerConCorreo(String correo) {
        return listaDealers.stream()
                .anyMatch(dealer -> dealer.getEmail().equalsIgnoreCase(correo));
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

        if (existeDealerConCorreo(correo)) {
            mostrarAlerta("Error de Validación", "Ya existe un repartidor registrado con el correo: " + correo, Alert.AlertType.ERROR);
            return;
        }

        // Se construye el Dealer
        Dealer nuevoDealer = new Dealer.Builder()
                .name(nombre)
                .email(correo)
                .phone(telefono)
                .available(disponible)
                .deliveriesMade(0)
                .build();

        // CLAVE: Usar el método del servicio que también maneja la persistencia
        boolean agregado = dealerService.addDealer(nuevoDealer);

        if (agregado) {
            // Actualizar la lista observable ligada a la tabla
            listaDealers.add(nuevoDealer);
            limpiarCampos();
            mostrarAlerta("Éxito", "Dealer agregado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo agregar el Dealer. Verifique ID/Correo.", Alert.AlertType.ERROR);
        }
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

        // CLAVE: Usar el servicio Singleton para eliminarlo del modelo central
        boolean eliminado = dealerService.deleteDealer(seleccionado.getId());

        if (eliminado) {
            // Si se elimina del modelo, se elimina de la lista observable
            listaDealers.remove(seleccionado);
            limpiarCampos();
            mostrarAlerta("Éxito", "Dealer eliminado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el Dealer del modelo.", Alert.AlertType.ERROR);
        }
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

        // Crear un objeto DTO/Dealer temporal para pasar al servicio
        Dealer dealerActualizado = new Dealer.Builder()
                .id(seleccionado.getId()) // Mantener el ID original
                .name(txtNombre.getText().trim())
                .email(txtCorreo.getText().trim())
                .phone(txtTelefono.getText().trim())
                .available(chkDisponible.isSelected())
                .deliveriesMade(seleccionado.getDeliveriesMade())
                .build();

        // --- VALIDACIÓN DE CORREO ÚNICO EN ACTUALIZACIÓN ---
        if (!seleccionado.getEmail().equalsIgnoreCase(dealerActualizado.getEmail()) && existeDealerConCorreo(dealerActualizado.getEmail())) {
            mostrarAlerta("Error de Validación", "El nuevo correo ya está en uso por otro repartidor.", Alert.AlertType.ERROR);
            return;
        }

        // CLAVE: Usar el servicio Singleton para actualizar el modelo central
        boolean actualizado = dealerService.updateDealer(dealerActualizado);

        if (actualizado) {
            // Dado que el servicio actualiza el objeto en el modelo, solo necesitamos refrescar la tabla.
            tablaDealers.refresh();
            limpiarCampos();
            mostrarAlerta("Éxito", "Dealer actualizado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo actualizar el Dealer en el modelo.", Alert.AlertType.ERROR);
        }
    }

    // --- MÉTODOS AUXILIARES ---

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