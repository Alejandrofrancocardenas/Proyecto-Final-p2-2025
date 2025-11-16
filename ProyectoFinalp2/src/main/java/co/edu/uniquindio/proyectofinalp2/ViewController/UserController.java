package co.edu.uniquindio.proyectofinalp2.ViewController;

import co.edu.uniquindio.proyectofinalp2.App;
import co.edu.uniquindio.proyectofinalp2.Model.User;
import co.edu.uniquindio.proyectofinalp2.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class UserController {

    @FXML private StackPane contenedorCentral;
    private UserService userService;
    private User userLogueado;

    public void initData(User userLogueado) {
        this.userLogueado = userLogueado;
        this.userService = new UserService(userLogueado);
        System.out.println("✅ DEBUG: UserService creado para el usuario: " + userLogueado.getFullname());

        Label lblBienvenida = new Label("👋 Bienvenido(a) " + userLogueado.getFullname());
        contenedorCentral.getChildren().setAll(lblBienvenida);
    }


    @FXML
    private void onMostrarPerfil() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/UserScenes/ProfileView.fxml");
    }

    @FXML
    private void onMostrarDirecciones() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/UserScenes/AdressManagementView.fxml");
    }

    @FXML
    private void onMostrarPagos() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/UserScenes/PaymentManagementView.fxml");
    }

    @FXML
    private void mostrarCotizacion() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/UserScenes/ShipmentQuotationView.fxml");
    }

    @FXML
    private void mostrarEnvios() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/UserScenes/ShipmentTableView.fxml");
    }

    @FXML
    private void onMostrarProductos() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/AdministratorMethods/ProductManagement.fxml");
    }

    @FXML
    private void mostrarHistorialPagos() {
        Label lbl = new Label("📊 Historial de Pagos");
        contenedorCentral.getChildren().setAll(lbl);
    }

    @FXML
    private void mostrarReportes() {
        Label lbl = new Label("📄 Generador de Reportes");
        contenedorCentral.getChildren().setAll(lbl);
    }

    private void cargarVista(String fxmlPath) {
        if (userService == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error Crítico",
                    "El UserService no ha sido inicializado. Inicie sesión correctamente.");
            return;
        }

        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.err.println("🔴 ERROR DE RUTA: Recurso FXML no encontrado en: " + fxmlPath);
                Label errorLabel = new Label("❌ ERROR: Vista no encontrada.");
                contenedorCentral.getChildren().setAll(errorLabel);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            Object subController = loader.getController();

            if (subController == null) {
                System.err.println("🔴 ERROR: No se encontró el controlador para la vista: " + fxmlPath);
                Label errorLabel = new Label("❌ ERROR: Controlador FXML no encontrado.");
                contenedorCentral.getChildren().setAll(errorLabel);
                return;
            }

            if (subController instanceof ServiceInjectable) {
                @SuppressWarnings("unchecked")
                ServiceInjectable<UserService> injectable = (ServiceInjectable<UserService>) subController;
                injectable.setService(this.userService);
                System.out.println("✅ DEBUG: Inyección de UserService completada para: " + fxmlPath);
            }

            contenedorCentral.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
            Label errorLabel = new Label("❌ Error al cargar la vista: " + e.getMessage());
            contenedorCentral.getChildren().setAll(errorLabel);
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            System.out.println("🚪 Cerrando sesión del usuario: " +
                    (userLogueado != null ? userLogueado.getFullname() : "Desconocido"));

            App.setRoot("LoginView.fxml");

        } catch (IOException e) {
            System.err.println("❌ Error al cerrar sesión y volver al Login.");
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo volver a la pantalla de inicio de sesión.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}