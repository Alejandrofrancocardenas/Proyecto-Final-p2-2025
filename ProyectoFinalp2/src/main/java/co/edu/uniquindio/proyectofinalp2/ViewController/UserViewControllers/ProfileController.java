package co.edu.uniquindio.proyectofinalp2.ViewController.UserViewControllers;

import co.edu.uniquindio.proyectofinalp2.service.UserService;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.ViewController.ServiceInjectable; // Asegúrate de que esta ruta es correcta

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class ProfileController implements ServiceInjectable<UserService> {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    private UserService userService;


    public void setUserService(UserService userService) {

    }

    @Override
    public void setService(UserService service) {
        this.userService = service;
        cargarDatosUsuario();
    }

    public void cargarDatosUsuario() {
        if (userService != null) {
            UserDTO userDTO = userService.getCurrentUserProfileDTO();

            if (userDTO != null) {
                txtNombre.setText(userDTO.getFullname() != null ? userDTO.getFullname() : "");

                if (txtCorreo != null) {
                    txtCorreo.setText(userDTO.getEmail() != null ? userDTO.getEmail() : "");
                    txtCorreo.setEditable(false);
                }

                if (txtPassword != null) {
                    txtPassword.setText("");
                }
            }
        }
    }
    @FXML
    private void onGuardarPerfil() {
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaContrasena = txtPassword.getText();

        if (nuevoNombre.isEmpty()) {
            mostrarAlerta("Advertencia", "El nombre no puede estar vacío.", Alert.AlertType.WARNING);
            return;
        }

        try {

            UserDTO perfilDTO = new UserDTO();
            perfilDTO.setFullname(nuevoNombre);


            if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
                perfilDTO.setPassword(nuevaContrasena);
            }


            userService.updateUserProfile(perfilDTO);

            mostrarAlerta("Éxito", "Perfil actualizado correctamente.", Alert.AlertType.INFORMATION);

            if (txtPassword != null) {
                txtPassword.setText("");
            }

        } catch (IllegalArgumentException e) {

            mostrarAlerta("Error de Actualización", "No se pudo actualizar el perfil: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {

            mostrarAlerta("Error de Actualización", "Error al guardar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}