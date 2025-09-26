package co.edu.uniquindio.proyectofinalp2.ViewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private RadioButton rbUsuario;
    @FXML private RadioButton rbAdmin;

    private ToggleGroup rol;

    @FXML
    public void initialize() {
        rol = new ToggleGroup();
        rbUsuario.setToggleGroup(rol);
        rbAdmin.setToggleGroup(rol);
    }

    @FXML
    private void handleLogin() {
        try {
            if (rbUsuario.isSelected()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalp2/View/UsuarioView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Panel Usuario");
                stage.setScene(new Scene(loader.load(), 800, 600));
                stage.show();
            } else if (rbAdmin.isSelected()) {
                mostrarAlerta("Info", "Aquí abriría el Panel del Administrador");
            } else {
                mostrarAlerta("Error", "Seleccione un rol (Usuario o Administrador)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegistro() {
        mostrarAlerta("Registro", "Aquí abriría la ventana de registro de usuario");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
