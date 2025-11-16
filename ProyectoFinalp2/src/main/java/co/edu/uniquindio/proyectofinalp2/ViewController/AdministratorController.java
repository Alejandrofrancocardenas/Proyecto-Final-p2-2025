package co.edu.uniquindio.proyectofinalp2.ViewController;

import co.edu.uniquindio.proyectofinalp2.App;
import co.edu.uniquindio.proyectofinalp2.Model.Admin;
import co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController.UserManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

import java.io.IOException;

public class AdministratorController {

    private Admin adminLogueado;

    @FXML private BorderPane mainPane;
    @FXML private StackPane contenedorCentral;
    @FXML private Button btnUsuarios, btnRepartidores, btnEnvios, btnMetricas, btnSalida;

    public void initData(Admin adminLogueado) {
        this.adminLogueado = adminLogueado;
        System.out.println("✅ Administrador logueado: " + adminLogueado.getEmail());

        Label lblBienvenida = new Label("⚙️ Bienvenido Administrador(a) " + adminLogueado.getFullname());

        if (contenedorCentral != null) {
            contenedorCentral.getChildren().setAll(lblBienvenida);
        } else if (mainPane != null) {
            mainPane.setCenter(lblBienvenida);
        }
    }

    @FXML
    public void initialize() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/AdministratorMethods/UserManagement.fxml");
    }

    @FXML
    private void onGestionUsuarios() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/AdministratorMethods/UserManagement.fxml");
    }

    @FXML
    private void onGestionRepartidores() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/AdministratorMethods/DealerManagement.fxml");
    }

    @FXML
    private void onAsignacionEnvios() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/AdministratorMethods/ShipmentManagement.fxml");
    }

    @FXML
    private void onPanelMetricas() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/AdministratorMethods/AdminMetricsView.fxml");
    }

    @FXML
    private void onSalida(ActionEvent event) {
        try {
            System.out.println("🚪 Cerrando sesión del administrador: " +
                    (adminLogueado != null ? adminLogueado.getFullname() : "Desconocido"));

            App.setRoot("LoginView.fxml");

        } catch (IOException e) {
            System.err.println("❌ Error al cerrar sesión y volver al Login.");
            e.printStackTrace();
        }
    }

    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));

            if (loader.getLocation() == null) {
                throw new IllegalStateException("⚠️ La ruta FXML no es válida: " + rutaFXML);
            }

            Parent vista = loader.load();
            Object controller = loader.getController();

            if (controller instanceof UserManagement) {
                ((UserManagement) controller).setAdministratorController(this);
            }

            if (controller instanceof co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController.DealerManagement) {
                ((co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController.DealerManagement) controller)
                        .setAdministratorController(this);
            }

            if (controller instanceof co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController.ShipmentManagement) {
                co.edu.uniquindio.proyectofinalp2.service.AdminService adminService =
                        co.edu.uniquindio.proyectofinalp2.service.AdminService.getInstance();
                ((co.edu.uniquindio.proyectofinalp2.ViewController.AdministratorMethodsController.ShipmentManagement) controller)
                        .setService(adminService);
                System.out.println("✅ AdminService inyectado correctamente en ShipmentManagement");
            }

            if (mainPane != null) {
                mainPane.setCenter(vista);
            } else if (contenedorCentral != null) {
                contenedorCentral.getChildren().setAll(vista);
            }

        } catch (IOException e) {
            System.err.println("❌ Error de I/O al cargar la vista FXML: " + rutaFXML);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}