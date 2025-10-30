package co.edu.uniquindio.proyectofinalp2.ViewController;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

public class UserController {

    @FXML private StackPane contenidoPrincipal;

    @FXML
    private void onMostrarPerfil() {
        contenidoPrincipal.getChildren().setAll(new Label("Aquí se mostraría el Perfil"));
    }

    @FXML
    private void onMostrarDirecciones() {
        contenidoPrincipal.getChildren().setAll(new Label("Aquí CRUD de Direcciones"));
    }

    @FXML
    private void onMostrarPagos() {
        contenidoPrincipal.getChildren().setAll(new Label("Aquí CRUD de Métodos de Pago"));
    }

    @FXML
    private void mostrarCotizacion() {
        contenidoPrincipal.getChildren().setAll(new Label("Formulario Cotizar Envío"));
    }

    @FXML
    private void mostrarEnvios() {
        contenidoPrincipal.getChildren().setAll(new Label("Tabla de Envíos"));
    }

    @FXML
    private void mostrarHistorialPagos() {
        contenidoPrincipal.getChildren().setAll(new Label("Historial de Pagos"));
    }

    @FXML
    private void mostrarReportes() {
        contenidoPrincipal.getChildren().setAll(new Label("Generador de Reportes"));
    }

    @FXML
    private void cerrarSesion() {
        contenidoPrincipal.getScene().getWindow().hide();
    }
}
