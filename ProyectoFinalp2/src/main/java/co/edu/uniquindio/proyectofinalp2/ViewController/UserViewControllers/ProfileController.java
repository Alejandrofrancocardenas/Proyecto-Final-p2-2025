package co.edu.uniquindio.proyectofinalp2.ViewController.UserViewControllers;

import co.edu.uniquindio.proyectofinalp2.service.UserService;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.ViewController.ServiceInjectable; // Asegúrate de que esta ruta es correcta

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// 💡 Implementamos la interfaz ServiceInjectable<UserService>
public class ProfileController implements ServiceInjectable<UserService> {

    // Campos FXML (del FXML que eliminó el Email como campo editable, usando Contraseña)
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo; // El campo de email sigue en el FXML pero no es editable
    @FXML private PasswordField txtPassword;

    private UserService userService;


    public void setUserService(UserService userService) {

    }

    /**
     * 1. Implementación de ServiceInjectable. El contenedor llama a este método.
     * @param service El UserService principal.
     */
    @Override
    public void setService(UserService service) {
        this.userService = service;
        // Solo después de tener el servicio, podemos cargar los datos
        cargarDatosUsuario();
    }

    /**
     * 2. Carga los datos del usuario actual al iniciar la vista.
     */
    public void cargarDatosUsuario() {
        if (userService != null) {
            // Usamos el DTO del servicio para obtener los datos actuales
            UserDTO userDTO = userService.getCurrentUserProfileDTO();

            if (userDTO != null) {
                // El campo de Nombre es editable
                txtNombre.setText(userDTO.getFullname() != null ? userDTO.getFullname() : "");

                // El campo de Email es solo lectura, si existe en la vista
                if (txtCorreo != null) {
                    txtCorreo.setText(userDTO.getEmail() != null ? userDTO.getEmail() : "");
                    txtCorreo.setEditable(false);
                }

                // La contraseña siempre se deja vacía al cargar
                if (txtPassword != null) {
                    txtPassword.setText("");
                }
            }
        }
    }

    /**
     * 3. Maneja el evento de guardar el perfil.
     */
    @FXML
    private void onGuardarPerfil() {
        String nuevoNombre = txtNombre.getText().trim();
        String nuevaContrasena = txtPassword.getText();

        if (nuevoNombre.isEmpty()) {
            mostrarAlerta("Advertencia", "El nombre no puede estar vacío.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Creamos el DTO de actualización
            UserDTO perfilDTO = new UserDTO();
            perfilDTO.setFullname(nuevoNombre);

            // Solo establecemos la contraseña si el campo no está vacío
            if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
                perfilDTO.setPassword(nuevaContrasena);
            }

            // Llamamos al servicio para ejecutar la actualización
            userService.updateUserProfile(perfilDTO);

            mostrarAlerta("Éxito", "Perfil actualizado correctamente.", Alert.AlertType.INFORMATION);

            // Limpiamos la contraseña después de guardar por seguridad
            if (txtPassword != null) {
                txtPassword.setText("");
            }

        } catch (IllegalArgumentException e) {
            // Captura el error de validación del servicio (ej. nombre vacío)
            mostrarAlerta("Error de Actualización", "No se pudo actualizar el perfil: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            // Captura cualquier otro error
            mostrarAlerta("Error de Actualización", "Error al guardar: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // --- Método Auxiliar ---
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}