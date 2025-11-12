package co.edu.uniquindio.proyectofinalp2.ViewController.UserViewControllers;

import co.edu.uniquindio.proyectofinalp2.service.UserService;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException; // Añadido para mejor manejo de errores
import co.edu.uniquindio.proyectofinalp2.ViewController.ServiceInjectable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable; // Necesario para usar initialize()
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.Optional;

// 1. IMPLEMENTACIÓN CORRECTA DE SERVICEINJECTABLE<UserService> e Initializable
public class ProductManagementController implements ServiceInjectable<UserService>, Initializable {

    // --- Campos FXML (fx:id) ---
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtValor;
    @FXML private TextField txtPeso;

    @FXML private TableView<PackageModel> tablaPaquetes;
    @FXML private TableColumn<PackageModel, String> colNombre;
    @FXML private TableColumn<PackageModel, String> colDescripcion;
    @FXML private TableColumn<PackageModel, String> colValor;
    @FXML private TableColumn<PackageModel, String> colPeso;
    @FXML private TableColumn<PackageModel, String> colID;

    // --- Dependencias de Servicio ---
    private UserService userService;
    private ObservableList<PackageModel> listaPaquetes;

    // -------------------------------------------------------------------------
    // --- 2. GESTIÓN DEL CICLO DE VIDA Y DEPENDENCIA ---
    // -------------------------------------------------------------------------

    /**
     * 💡 MÉTODO DE INYECCIÓN ÚNICO Y CORREGIDO
     * Se llama por el UserController para inyectar la dependencia.
     * @param service El UserService que será inyectado.
     */
    @Override
    public void setService(UserService service) {
        this.userService = service;
        System.out.println("✅ DEBUG: UserService inyectado en ProductManagementController.");
        // CRÍTICO: Cargar los datos inmediatamente después de la inyección
        cargarPaquetes();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
        // Listener para cargar datos en el formulario al seleccionar una fila
        tablaPaquetes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarDatosPaquete(newSel);
            } else {
                onLimpiarCampos();
            }
        });
    }

    private void configurarTabla() {
        // Mapeo de columnas
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        // CRÍTICO: CORRECCIÓN DE ERROR
        // Se corrige .get() por .getDeclaredValue() para obtener el valor de la propiedad.
        colValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDeclaredValue())));

        // CRÍTICO: CORRECCIÓN DE ERROR
        // Se corrige .getWeightKg() por .getWeight() para usar el método unificado del modelo.
        colPeso.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getWeight())));

        colID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdPackage()));

        listaPaquetes = FXCollections.observableArrayList();
        tablaPaquetes.setItems(listaPaquetes);
    }

    /**
     * Carga la lista de paquetes del usuario usando el servicio. (Recarga de datos)
     */
    private void cargarPaquetes() {
        if (userService != null) {
            // Recarga la lista desde el UserService, que a su vez tiene la referencia actualizada del usuario.
            listaPaquetes.setAll(userService.listUserPackages());
            tablaPaquetes.refresh();
            System.out.println("✅ DEBUG: Paquetes recargados desde el modelo persistido.");
        }
    }

    private void cargarDatosPaquete(PackageModel paquete) {
        txtNombre.setText(paquete.getName());
        txtDescripcion.setText(paquete.getDescription());
        txtValor.setText(String.valueOf(paquete.getDeclaredValue()));
        // Aseguramos que el método usado aquí coincida con el modelo (getWeight)
        txtPeso.setText(String.valueOf(paquete.getWeight()));
    }

    // -------------------------------------------------------------------------
    // --- Lógica CRUD (Ahora con llamadas al Service) ---
    // -------------------------------------------------------------------------

    @FXML
    private void onAgregarPaquete() {
        if (userService == null) {
            mostrarAlerta("Error Crítico", "El servicio de usuario no fue inicializado correctamente.", Alert.AlertType.ERROR);
            return;
        }

        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        String valorStr = txtValor.getText();
        String pesoStr = txtPeso.getText();

        if (nombre.isEmpty() || valorStr.isEmpty() || pesoStr.isEmpty()) {
            mostrarAlerta("Error", "Los campos Nombre, Valor y Peso son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr);
            double peso = Double.parseDouble(pesoStr);

            PackageModel nuevoPaquete = new PackageModel();
            // El IDPackage se generará dentro del servicio si es nulo, pero lo asignamos aquí por seguridad.
            nuevoPaquete.setIdPackage(UUID.randomUUID().toString());
            nuevoPaquete.setName(nombre);
            nuevoPaquete.setDescription(descripcion);
            nuevoPaquete.setDeclaredValue(valor);
            nuevoPaquete.setWeight(peso); // Usa el método setWeight()

            // 🌟 CRÍTICO: Llama al servicio para añadir y PERSISTIR
            userService.addPackageToUser(nuevoPaquete);

            cargarPaquetes(); // Recarga para ver el nuevo elemento (aunque listaPaquetes.add() también serviría, recargar es más seguro)

            onLimpiarCampos();
            mostrarAlerta("Éxito", "Paquete agregado y persistido correctamente.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El valor y el peso deben ser números válidos.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo agregar el paquete: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onActualizarPaquete() {
        PackageModel seleccionado = tablaPaquetes.getSelectionModel().getSelectedItem();

        if (userService == null || seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un paquete para actualizar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            double valor = Double.parseDouble(txtValor.getText());
            double peso = Double.parseDouble(txtPeso.getText());

            // Actualizar el objeto local para pasarlo al servicio
            seleccionado.setName(txtNombre.getText());
            seleccionado.setDescription(txtDescripcion.getText());
            seleccionado.setDeclaredValue(valor);
            seleccionado.setWeight(peso); // Usa el método setWeight()

            // 🌟 CRÍTICO: Llama al servicio para actualizar y PERSISTIR
            userService.updatePackage(seleccionado);

            tablaPaquetes.refresh(); // Refrescar la tabla
            onLimpiarCampos();
            mostrarAlerta("Éxito", "Paquete actualizado y persistido correctamente.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El valor y el peso deben ser números válidos.", Alert.AlertType.ERROR);
        } catch (NotFoundException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo actualizar el paquete: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminarPaquete() {
        PackageModel seleccionado = tablaPaquetes.getSelectionModel().getSelectedItem();

        if (userService == null || seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un paquete para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        if (!confirmarAccion("Confirmar Eliminación", "¿Está seguro de que desea eliminar el paquete: " + seleccionado.getName() + "?")) {
            return;
        }

        try {
            // 🌟 CRÍTICO: Llama al servicio para eliminar y PERSISTIR
            userService.deletePackage(seleccionado.getIdPackage());

            cargarPaquetes(); // Recarga para asegurar que se eliminó visualmente
            onLimpiarCampos();
            mostrarAlerta("Éxito", "Paquete eliminado y persistido correctamente.", Alert.AlertType.INFORMATION);

        } catch (NotFoundException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo eliminar el paquete: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onLimpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        txtValor.clear();
        txtPeso.clear();
        tablaPaquetes.getSelectionModel().clearSelection();
    }

    // --- Métodos Auxiliares ---
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