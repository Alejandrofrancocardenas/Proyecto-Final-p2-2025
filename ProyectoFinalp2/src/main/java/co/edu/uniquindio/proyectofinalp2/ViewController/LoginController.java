package co.edu.uniquindio.proyectofinalp2.ViewController;

import co.edu.uniquindio.proyectofinalp2.service.Sesion;
import co.edu.uniquindio.proyectofinalp2.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private RadioButton rdbUsuario;

    @FXML
    private RadioButton rdbAdministrador;

    @FXML
    private RadioButton rdbRepartidor;

    private ToggleGroup rol;

    @FXML
    public void initialize() {
        rol = new ToggleGroup();
        rdbUsuario.setToggleGroup(rol);
        rdbAdministrador.setToggleGroup(rol);
        rdbRepartidor.setToggleGroup(rol);
    }


    @FXML
    private void handleRegistro() {
        System.out.println("Botón de registro presionado");

        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        RadioButton seleccionado = (RadioButton) rol.getSelectedToggle();

        if (correo.isEmpty() || contrasena.isEmpty() || seleccionado == null) {
            mostrarAlerta("Por favor, complete todos los campos y seleccione un rol.");
            return;
        }

        String rolSeleccionado = seleccionado.getText();

        // Crear nuevo usuario con el patrón Builder
        User nuevo = new User.Builder()
                .email(correo)
                .password(contrasena)
                .rol(rolSeleccionado)
                .build();

        // Registrar usuario en la sesión
        Sesion.registrarUsuario(nuevo);

        mostrarAlerta("Registro exitoso. Ahora puede iniciar sesión.");
        limpiarCampos();
    }

    @FXML
    private void handleLogin() {
        System.out.println("Botón de inicio de sesión presionado");

        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        RadioButton seleccionado = (RadioButton) rol.getSelectedToggle();

        if (correo.isEmpty() || contrasena.isEmpty() || seleccionado == null) {
            mostrarAlerta("Por favor, complete todos los campos y seleccione un rol.");
            return;
        }

        String rolSeleccionado = seleccionado.getText();

        // Verificar credenciales
        boolean credencialesCorrectas = Sesion.verificarCredenciales(correo, contrasena, rolSeleccionado);

        if (credencialesCorrectas) {
            mostrarAlerta("Inicio de sesión exitoso como " + rolSeleccionado);
            abrirVentanaPorRol(rolSeleccionado);
        } else {
            mostrarAlerta("Credenciales incorrectas o rol no coincide.");
        }
    }

    private void abrirVentanaPorRol(String rolSeleccionado) {
        String vista = "";

        switch (rolSeleccionado) {
            case "Usuario":
                vista = "/co/edu/uniquindio/proyectofinalp2/View/UserView.fxml";
                break;
            case "Administrador":
                vista = "/co/edu/uniquindio/proyectofinalp2/View/AdministratorView.fxml";
                break;
            case "Repartidor":
                vista = "/co/edu/uniquindio/proyectofinalp2/View/DealerView.fxml";
                break;
            default:
                mostrarAlerta("Rol no reconocido.");
                return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Panel de " + rolSeleccionado);
            stage.show();

            // Cerrar ventana de login
            Stage loginStage = (Stage) txtCorreo.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al abrir la ventana del rol: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtCorreo.clear();
        txtContrasena.clear();
        rol.selectToggle(null);
    }
}
