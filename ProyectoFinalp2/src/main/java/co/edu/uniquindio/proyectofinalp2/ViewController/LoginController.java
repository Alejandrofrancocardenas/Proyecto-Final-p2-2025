package co.edu.uniquindio.proyectofinalp2.ViewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private RadioButton rbUsuario;
    @FXML
    private RadioButton rbAdmin;
    @FXML
    private RadioButton rbRepartidor;

    private ToggleGroup rol;

    @FXML
    public void initialize() {
        rol = new ToggleGroup();
        rbUsuario.setToggleGroup(rol);
        rbAdmin.setToggleGroup(rol);
        rbRepartidor.setToggleGroup(rol);
    }
    @FXML
    private void handleLogin() {
        try {
            FXMLLoader loader;
            Stage stage = new Stage();
            Scene scene;

            if (rbUsuario.isSelected()) {
                loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalp2/View/UserView.fxml"));
                scene = new Scene(loader.load(), 800, 600);
                stage.setTitle("Panel Usuario");
            }
            else if (rbAdmin.isSelected()) {
                loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalp2/View/AdministratorView.fxml"));
                scene = new Scene(loader.load(), 800, 600);
                stage.setTitle("Panel Administrador");
            }
            else if (rbRepartidor.isSelected()) {
                loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalp2/View/DealerView.fxml"));
                scene = new Scene(loader.load(), 800, 600);
                stage.setTitle("Panel Repartidor");
            }
            else {
                mostrarAlerta("Error", "Seleccione un rol (Usuario, Administrador o Repartidor)");
                return;
            }

            stage.setScene(scene);
            stage.show();


            Stage currentStage = (Stage) rbUsuario.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista seleccionada.");
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
