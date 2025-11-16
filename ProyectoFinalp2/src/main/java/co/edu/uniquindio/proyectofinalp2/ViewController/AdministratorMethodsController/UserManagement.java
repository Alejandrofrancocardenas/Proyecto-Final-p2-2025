package co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

import co.edu.uniquindio.proyectofinalp2.Model.User;
import co.edu.uniquindio.proyectofinalp2.service.AdminService;
import co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Optional;

public class UserManagement implements Initializable {

    private final AdminService adminService = AdminService.getInstance();


    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TableView<User> tablaUsuarios;
    @FXML
    private TableColumn<User, String> colNombre;
    @FXML
    private TableColumn<User, String> colCorreo;
    @FXML
    private TableColumn<User, String> colPassword;


    private ObservableList<User> listaUsuarios;

    private AdministratorController administratorController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.listaUsuarios = FXCollections.observableArrayList(adminService.listAllUsers());

        configurarTabla();
        tablaUsuarios.setItems(listaUsuarios);


        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarDatosUsuario(newSel);
            }
        });
    }


    public void setAdministratorController(AdministratorController administratorController) {
        this.administratorController = administratorController;
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullname()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colPassword.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
    }

    private boolean existeUsuarioConCorreo(String correo) {
        return listaUsuarios.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(correo));
    }


    @FXML
    private void onAgregarUsuario() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String password = txtPassword.getText();

        String idGenerado = "USR-" + correo.split("@")[0] + "-" + String.valueOf(System.currentTimeMillis()).substring(7);

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.WARNING);
            return;
        }

        if (existeUsuarioConCorreo(correo)) {
            mostrarAlerta("Error de Validación", "Ya existe un usuario registrado con el correo: " + correo, Alert.AlertType.ERROR);
            return;
        }

        User nuevoUsuario = new User.Builder()
                .id(idGenerado) // 🟢 Usar el ID más robusto
                .name(nombre)
                .email(correo)
                .phone(telefono)
                .password(password)
                .rol("CLIENT")
                .build();
        boolean agregado = false;
        try {
            agregado = adminService.addUserAdmin(nuevoUsuario);
        } catch (Exception e) {
            System.err.println("Error durante la persistencia: " + e.getMessage());
            mostrarAlerta("Error Crítico", "Fallo al guardar en el sistema: " + e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        if (agregado) {
            listaUsuarios.add(nuevoUsuario);
            limpiarCampos();
            mostrarAlerta("Éxito", "Usuario agregado correctamente. ID: " + idGenerado, Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo agregar el Usuario. Es probable que el ID generado (" + idGenerado + ") ya exista o que el servicio fallara.", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void onEliminarUsuario() {
        User seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un usuario para eliminar", Alert.AlertType.WARNING);
            return;
        }

        boolean eliminado = adminService.deleteUserAdmin(seleccionado);

        if (eliminado) {
            listaUsuarios.remove(seleccionado);
            limpiarCampos();
            mostrarAlerta("Éxito", "Usuario eliminado correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el usuario del modelo.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void onActualizarUsuario() {
        User seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un usuario para actualizar", Alert.AlertType.WARNING);
            return;
        }

        String nuevoNombre = txtNombre.getText();
        String nuevoCorreo = txtCorreo.getText();
        String nuevoPassword = txtPassword.getText();
        String nuevoTelefono = txtTelefono.getText();

        if (nuevoNombre.isEmpty() || nuevoCorreo.isEmpty() || nuevoPassword.isEmpty() || nuevoTelefono.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.WARNING);
            return;
        }


        User usuarioActualizado = new User.Builder()
                .id(seleccionado.getId())
                .name(nuevoNombre)
                .email(nuevoCorreo)
                .password(nuevoPassword)
                .phone(nuevoTelefono)
                .rol(seleccionado.getRol())
                .build();



        if (!seleccionado.getEmail().equalsIgnoreCase(nuevoCorreo)) {
            Optional<User> userOpt = adminService.findUserByEmail(nuevoCorreo);

            if (userOpt.isPresent() && !userOpt.get().getId().equals(seleccionado.getId())) {
                mostrarAlerta("Error de Validación", "El nuevo correo ya está en uso por otro usuario.", Alert.AlertType.ERROR);
                return;
            }
        }


        boolean actualizado = adminService.updateUserAdmin(usuarioActualizado);

        if (actualizado) {
            int index = listaUsuarios.indexOf(seleccionado);
            if (index != -1) {
                listaUsuarios.set(index, usuarioActualizado);
            }
            tablaUsuarios.refresh();

            limpiarCampos();
            mostrarAlerta("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "No se pudo actualizar el usuario en el modelo.", Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosUsuario(User user) {
        txtNombre.setText(user.getFullname());
        txtCorreo.setText(user.getEmail());
        txtPassword.setText(user.getPassword());
        if (txtTelefono != null) txtTelefono.setText(user.getPhone());
    }

    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtPassword.clear();
        if (txtTelefono != null) txtTelefono.clear();
        tablaUsuarios.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}