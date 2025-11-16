package co.edu.uniquindio.proyectofinalp2.ViewController;

import co.edu.uniquindio.proyectofinalp2.Model.Admin;
import co.edu.uniquindio.proyectofinalp2.Model.Dealer;
import co.edu.uniquindio.proyectofinalp2.dto.AdminDTO;
import co.edu.uniquindio.proyectofinalp2.dto.DealerDTO;
import co.edu.uniquindio.proyectofinalp2.service.CompanyService; // 💡 Importar CompanyService
import co.edu.uniquindio.proyectofinalp2.Model.User;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectEmailException;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectPasswordException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private RadioButton rdbUsuario;
    @FXML private RadioButton rdbAdministrador;
    @FXML private RadioButton rdbRepartidor;
    private ToggleGroup rol;

    private final CompanyService companyService = CompanyService.getInstance();

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
        String rolId = seleccionado.getId();

        System.out.println("ID del rol seleccionado: " + rolId);

        try {
            String idGenerado = "REG-" + correo.split("@")[0] + "-" + String.valueOf(System.currentTimeMillis()).substring(7);

            if (rolId.equals("rdbUsuario")) {
                User nuevoUser = new User.Builder()
                        .id(idGenerado)
                        .email(correo)
                        .password(contrasena)
                        .rol("CLIENT")
                        .build();
                companyService.registerUser(nuevoUser);
                mostrarAlerta("Registro exitoso de Usuario. Ahora puede iniciar sesión.");

            } else if (rolId.equals("rdbAdministrador")) {
                Admin nuevoAdmin = new Admin.Builder()
                        .id(idGenerado)
                        .email(correo)
                        .password(contrasena)
                        .rol("ADMIN")
                        .build();
                companyService.registerAdmin(nuevoAdmin);
                mostrarAlerta("Registro exitoso de Administrador. Ahora puede iniciar sesión.");

            } else if (rolId.equals("rdbRepartidor")) {
                Dealer nuevoDealer = new Dealer.Builder()
                        .id(idGenerado)
                        .email(correo)
                        .password(contrasena)
                        .build();
                companyService.registerDealer(nuevoDealer);
                mostrarAlerta("Registro exitoso de Repartidor. Ahora puede iniciar sesión.");

            } else {
                mostrarAlerta("Rol de registro no válido. ID recibido: " + rolId);
                return;
            }

            limpiarCampos();

        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error de registro: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error inesperado al registrar: " + e.getMessage());
        }
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
        String rolId = seleccionado.getId();
        String rolParaMostrar = seleccionado.getText();

        System.out.println("ID del rol seleccionado: " + rolId);

        Optional<?> objetoLogeado = Optional.empty();

        try {
            if (rolId.equals("rdbUsuario")) {
                objetoLogeado = Optional.of(companyService.login(correo, contrasena));

            } else if (rolId.equals("rdbAdministrador")) {
                objetoLogeado = Optional.of(companyService.loginAdmin(correo, contrasena));

            } else if (rolId.equals("rdbRepartidor")) {
                objetoLogeado = Optional.of(companyService.loginDealer(correo, contrasena));

            } else {
                mostrarAlerta("Rol no reconocido. ID: " + rolId);
                return;
            }

            if (objetoLogeado.isPresent()) {
                mostrarAlerta("Inicio de sesión exitoso como " + rolParaMostrar);
                abrirVentanaPorRol(objetoLogeado.get(), rolId);
            }

        } catch (IncorrectEmailException | IncorrectPasswordException e) {
            mostrarAlerta(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de inicio de sesión inesperado: " + e.getMessage());
        }
    }
    private void abrirVentanaPorRol(Object objetoLogeado, String rolId) {
        String vista = "";

        if (rolId.equals("rdbUsuario")) {
            vista = "/co/edu/uniquindio/proyectofinalp2/View/UserView.fxml";
        } else if (rolId.equals("rdbAdministrador")) {
            vista = "/co/edu/uniquindio/proyectofinalp2/View/AdministratorView.fxml";
        } else if (rolId.equals("rdbRepartidor")) {
            vista = "/co/edu/uniquindio/proyectofinalp2/View/DealerView.fxml";
        } else {
            mostrarAlerta("Rol no reconocido.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller != null) {
                if (rolId.equals("rdbUsuario") && controller instanceof UserController) {
                    ((UserController) controller).initData((User) objetoLogeado);
                } else if (rolId.equals("rdbAdministrador") && controller instanceof AdministratorController) {
                    ((AdministratorController) controller).initData((Admin) objetoLogeado);
                } else if (rolId.equals("rdbRepartidor") && controller instanceof DealerController) {
                    ((DealerController) controller).initData((Dealer) objetoLogeado);
                }
            }


            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Panel de Usuario");
            stage.setMaximized(true);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al abrir la ventana del rol. Revisa las rutas FXML: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error al inicializar el controlador. Revisa el initData(): " + e.getMessage());
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