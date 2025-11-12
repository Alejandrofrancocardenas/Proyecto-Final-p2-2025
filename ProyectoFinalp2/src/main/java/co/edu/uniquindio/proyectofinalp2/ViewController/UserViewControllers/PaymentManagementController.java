package co.edu.uniquindio.proyectofinalp2.ViewController.UserViewControllers;

import co.edu.uniquindio.proyectofinalp2.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
// Importa la clase de tu modelo para Pagos (asumo que se llama Payment o PaymentModel)
// import co.edu.uniquindio.proyectofinalp2.Model.Payment;

public class PaymentManagementController {

    @FXML private TextField txtAliasTarjeta;
    @FXML private TextField txtNumeroTarjeta;
    @FXML private TextField txtCVV;
    @FXML private TableView tablaPagos;
    // @FXML private TableColumn<Payment, String> colAliasPago; // Descomentar al crear el modelo Payment

    private UserService userService;
    // private ObservableList<Payment> listaPagos; // Descomentar al crear el modelo Payment

    /**
     * Inyección de dependencia desde UserController.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
        cargarMetodosPago();
    }

    @FXML
    public void initialize() {
        // Aquí se configuraría la tabla de pagos.
    }

    private void cargarMetodosPago() {
        if (userService != null) {
            // Ejemplo: listaPagos.setAll(userService.listPayments());
        }
    }

    @FXML
    private void onGuardarMetodoPago() {
        String alias = txtAliasTarjeta.getText();
        String numero = txtNumeroTarjeta.getText();
        String cvv = txtCVV.getText();

        if (userService == null) {
            mostrarAlerta("Error", "Servicio no inicializado.", Alert.AlertType.ERROR);
            return;
        }

        if (alias.isEmpty() || numero.isEmpty() || cvv.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        // Lógica para crear y guardar el objeto Payment/Card

        mostrarAlerta("Éxito", "Método de pago guardado/actualizado.", Alert.AlertType.INFORMATION);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}