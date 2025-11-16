package co.edu.uniquindio.proyectofinalp2.ViewController;

import co.edu.uniquindio.proyectofinalp2.App;
import co.edu.uniquindio.proyectofinalp2.Model.Dealer;
import co.edu.uniquindio.proyectofinalp2.service.DealerService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class DealerController {

    @FXML private StackPane contenedorCentral;
    @FXML private Button btnMisEnvios;
    @FXML private Button btnEntregasRealizadas;
    @FXML private Button btnMiPerfil;
    @FXML private Button btnEstadisticas;
    @FXML private Button btnSalir;

    private Dealer dealerLogueado;
    private DealerService dealerService;

    public void initData(Dealer dealer) {
        if (dealer == null) {
            throw new IllegalArgumentException("El dealer no puede ser nulo");
        }

        this.dealerLogueado = dealer;
        this.dealerService = DealerService.getInstance();

        System.out.println("✅ Repartidor logueado: " + dealer.getFullname());

        Label lblBienvenida = new Label("🚚 Bienvenido(a) " + dealer.getFullname());
        contenedorCentral.getChildren().setAll(lblBienvenida);

        onMisEnvios();
    }

    @FXML
    private void onMisEnvios() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/DealerScenes/DealerShipmentView.fxml");
    }

    @FXML
    private void onEntregasRealizadas() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/DealerScenes/DealerHistoryView.fxml");
    }

    @FXML
    private void onMiPerfil() {
        String info = String.format(
                "👤 Perfil del Repartidor\n\n" +
                        "Nombre: %s\n" +
                        "Email: %s\n" +
                        "Teléfono: %s\n" +
                        "Estado: %s\n" +
                        "Entregas realizadas: %d",
                dealerLogueado.getFullname(),
                dealerLogueado.getEmail(),
                dealerLogueado.getPhone(),
                dealerLogueado.isAvailable() ? "✅ Disponible" : "🔴 No disponible",
                dealerLogueado.getDeliveriesMade()
        );

        Label lblPerfil = new Label(info);
        contenedorCentral.getChildren().setAll(lblPerfil);
    }

    @FXML
    private void onEstadisticas() {
        int totalEnvios = dealerLogueado.getAssignedShipments() != null ?
                dealerLogueado.getAssignedShipments().size() : 0;
        int entregasRealizadas = dealerLogueado.getDeliveriesMade();
        double promedioTiempo = dealerLogueado.calcularTiempoPromedioEntregas();

        String stats = String.format(
                "📊 Estadísticas del Repartidor\n\n" +
                        "Total de envíos asignados: %d\n" +
                        "Entregas completadas: %d\n" +
                        "Tiempo promedio de entrega: %.2f horas\n" +
                        "Estado actual: %s",
                totalEnvios,
                entregasRealizadas,
                promedioTiempo,
                dealerLogueado.isAvailable() ? "✅ Disponible" : "🔴 No disponible"
        );

        Label lblStats = new Label(stats);
        contenedorCentral.getChildren().setAll(lblStats);
    }

    @FXML
    private void onCerrarSesion() {
        try {
            System.out.println("🚪 Cerrando sesión del repartidor: " +
                    (dealerLogueado != null ? dealerLogueado.getFullname() : "Desconocido"));

            App.setRoot("LoginView.fxml");

        } catch (IOException e) {
            System.err.println("❌ Error al cerrar sesión: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo volver a la pantalla de inicio de sesión.",
                    Alert.AlertType.ERROR);
        }
    }

    private void cargarVista(String fxmlPath) {
        if (dealerService == null || dealerLogueado == null) {
            mostrarAlerta("Error", "El servicio o el repartidor no están inicializados.",
                    Alert.AlertType.ERROR);
            return;
        }

        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.err.println("❌ Error: Recurso FXML no encontrado en: " + fxmlPath);
                Label errorLabel = new Label("❌ ERROR: Vista no encontrada - " + fxmlPath);
                contenedorCentral.getChildren().setAll(errorLabel);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof ServiceInjectable) {
                @SuppressWarnings("unchecked")
                ServiceInjectable<DealerService> injectable = (ServiceInjectable<DealerService>) controller;
                injectable.setService(dealerService);
                System.out.println("✅ Servicio DealerService inyectado en: " + fxmlPath);
            }

            if (controller instanceof DealerDataInjectable) {
                ((DealerDataInjectable) controller).setDealer(dealerLogueado);
                System.out.println("✅ Datos del Dealer inyectados en: " + fxmlPath);
            }

            contenedorCentral.getChildren().setAll(root);

        } catch (IOException e) {
            System.err.println("❌ Error al cargar vista: " + fxmlPath);
            e.printStackTrace();
            Label errorLabel = new Label("❌ ERROR: No se pudo cargar la vista.");
            contenedorCentral.getChildren().setAll(errorLabel);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}