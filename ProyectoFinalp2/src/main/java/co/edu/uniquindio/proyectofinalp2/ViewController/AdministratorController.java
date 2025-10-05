package co.edu.uniquindio.proyectofinalp2.ViewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Controlador principal del panel del Administrador.
 * Permite navegar entre las diferentes secciones del panel:
 * - Gestión de usuarios
 * - Gestión de repartidores
 * - Asignación de envíos
 * - Panel de métricas
 */
public class AdministratorController {

    @FXML
    private BorderPane mainPane; // Panel principal donde se cargan las vistas

    @FXML
    private StackPane contenedorCentral; // (Opcional) contenedor interno si lo usas en el FXML

    @FXML
    private Button btnUsuarios, btnRepartidores, btnEnvios, btnMetricas;

    /**
     * Inicializa el panel del administrador cargando una vista por defecto.
     */
    @FXML
    public void initialize() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/AdministratorMethods/UserManagement.fxml");
    }

    /**
     * Acción: Mostrar la vista de gestión de usuarios.
     * RF-010: Gestionar usuarios (crear, actualizar, eliminar, listar)
     */
    @FXML
    private void onGestionUsuarios() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/GestionUsuariosView.fxml");
    }

    /**
     * Acción: Mostrar la vista de gestión de repartidores.
     * RF-011: Gestionar repartidores y disponibilidad
     */
    @FXML
    private void onGestionRepartidores() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/GestionRepartidoresView.fxml");
    }

    /**
     * Acción: Mostrar la vista de asignación de envíos.
     * RF-012: Asignar/Reasignar envíos, registrar incidencias, cambios de estado
     */
    @FXML
    private void onAsignacionEnvios() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/AsignacionEnviosView.fxml");
    }

    /**
     * Acción: Mostrar el panel de métricas.
     * RF-013 y RF-014: Métricas, estadísticas y visualización con JavaFX Charts
     */
    @FXML
    private void onPanelMetricas() {
        cargarVista("/co/edu/uniquindio/proyectofinalp2/View/MetricasView.fxml");
    }

    /**
     * Método reutilizable para cargar una vista FXML dentro del panel principal del administrador.
     *
     * @param rutaFXML Ruta del archivo FXML que se desea cargar.
     */
    private void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            if (loader.getLocation() == null) {
                throw new IllegalStateException("⚠️ La ruta FXML no es válida: " + rutaFXML);
            }

            Parent vista = loader.load();

            // Si el BorderPane principal existe, usamos su centro:
            if (mainPane != null) {
                mainPane.setCenter(vista);
            } else if (contenedorCentral != null) {
                // Alternativamente, si usas StackPane en lugar de BorderPane:
                contenedorCentral.getChildren().setAll(vista);
            }

        } catch (IOException e) {
            System.err.println("❌ Error al cargar la vista: " + rutaFXML);
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("❌ Error de ruta FXML: " + e.getMessage());
        }
    }
}
