package co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController;

import co.edu.uniquindio.proyectofinalp2.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorController;


import co.edu.uniquindio.proyectofinalp2.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controlador encargado de gestionar los usuarios (RF-010)
 * Permite crear, actualizar, eliminar y listar usuarios.
 */
public class UserManagement {

    // Campos de texto (vinculados desde el FXML)
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

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

    private final ObservableList<User> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
    }
    private AdministratorController administratorController;
public void setAdministratorController(AdministratorController administratorController) {
        this.administratorController = administratorController;
}
    /**
     * Configura las columnas de la tabla.
     */
    private void configurarTabla() {
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullname()));
        colCorreo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        colPassword.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPassword()));

        tablaUsuarios.setItems(listaUsuarios);
    }

    /**
     * Agrega un nuevo usuario.
     */
    @FXML
    private void onAgregarUsuario() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String password = txtPassword.getText();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.WARNING);
            return;
        }

        // Crear el nuevo usuario usando el patrón Builder
        User nuevoUsuario = new User.Builder()
                .name(nombre)
                .email(correo)
                .password(password)
                .build();

        listaUsuarios.add(nuevoUsuario);
        limpiarCampos();

        mostrarAlerta("Éxito", "Usuario agregado correctamente", Alert.AlertType.INFORMATION);
    }

    /**
     * Elimina el usuario seleccionado.
     */
    @FXML
    private void onEliminarUsuario() {
        User seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un usuario para eliminar", Alert.AlertType.WARNING);
            return;
        }

        listaUsuarios.remove(seleccionado);
        mostrarAlerta("Éxito", "Usuario eliminado correctamente", Alert.AlertType.INFORMATION);
    }

    /**
     * Actualiza la información del usuario seleccionado.
     */
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

        if (nuevoNombre.isEmpty() || nuevoCorreo.isEmpty() || nuevoPassword.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.WARNING);
            return;
        }

        seleccionado.setFullname(nuevoNombre);
        seleccionado.setEmail(nuevoCorreo);
        seleccionado.setPassword(nuevoPassword);

        tablaUsuarios.refresh();
        limpiarCampos();

        mostrarAlerta("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
    }

    /**
     * Limpia los campos de texto.
     */
    @FXML
    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtPassword.clear();
    }

    /**
     * Muestra una alerta en pantalla.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
