package co.edu.uniquindio.proyectofinalp2.ViewController.UserViewControllers;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;
import co.edu.uniquindio.proyectofinalp2.Model.Rate;
import co.edu.uniquindio.proyectofinalp2.factory.ShipmentFactory;
import co.edu.uniquindio.proyectofinalp2.service.UserService;
import co.edu.uniquindio.proyectofinalp2.ViewController.ServiceInjectable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

import co.edu.uniquindio.proyectofinalp2.decorators.SecureShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SignatureRequiredShipment;


public class ShipmentQuotationController implements ServiceInjectable<UserService>, Initializable {


    @FXML
    private ComboBox<Address> cmbOrigen;
    @FXML
    private ComboBox<Address> cmbDestino;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtDimensiones;
    @FXML
    private ComboBox<String> cmbServicio;
    @FXML
    private TextArea txtDescripcion;

    @FXML
    private Label lblCostoEstimado;
    @FXML
    private Button btnCalcularCosto;
    @FXML
    private Button btnConfirmarEnvioYtoPagar;

    @FXML
    private CheckBox chkSecureShipping;
    @FXML
    private CheckBox chkSignatureRequired;

    private UserService userService;
    private Shipment currentShipment;
    private final ObservableList<Address> addressList = FXCollections.observableArrayList();

    private final ObservableList<String> serviceStrategies = FXCollections.observableArrayList(
            "Normal", "Priority", "Fragile"
    );


    @Override
    public void setService(UserService service) {
        this.userService = service;
        loadInitialData();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbServicio.setItems(serviceStrategies);
        cmbServicio.getSelectionModel().select("Normal");
        setupAddressComboBox(cmbOrigen);
        setupAddressComboBox(cmbDestino);
        lblCostoEstimado.setText("$ 0.00");

        // Limpiar costo estimado al cambiar datos de input
        cmbServicio.valueProperty().addListener((obs, old, nev) -> lblCostoEstimado.setText("$ 0.00"));
        cmbOrigen.valueProperty().addListener((obs, old, nev) -> lblCostoEstimado.setText("$ 0.00"));
        cmbDestino.valueProperty().addListener((obs, old, nev) -> lblCostoEstimado.setText("$ 0.00"));

        if (chkSecureShipping != null)
            chkSecureShipping.selectedProperty().addListener((obs, old, nev) -> lblCostoEstimado.setText("$ 0.00"));
        if (chkSignatureRequired != null)
            chkSignatureRequired.selectedProperty().addListener((obs, old, nev) -> lblCostoEstimado.setText("$ 0.00"));

        txtPeso.textProperty().addListener((obs, old, nev) -> lblCostoEstimado.setText("$ 0.00"));
        txtDimensiones.textProperty().addListener((obs, old, nev) -> lblCostoEstimado.setText("$ 0.00"));
    }

    private void setupAddressComboBox(ComboBox<Address> comboBox) {
        comboBox.setConverter(new StringConverter<Address>() {
            @Override
            public String toString(Address address) {
                return address != null ? address.getCity() + " (" + address.getStreet() + ")" : "Seleccionar Dirección";
            }

            @Override
            public Address fromString(String string) {
                return addressList.stream()
                        .filter(a -> string != null && string.startsWith(a.getCity()))
                        .findFirst().orElse(null);
            }
        });
        comboBox.setItems(addressList);
    }

    private void loadInitialData() {
        if (userService != null) {
            List<Address> addresses = userService.listAddresses();
            addressList.setAll(addresses);
            if (addresses.isEmpty()) {
                String mensajeDetallado = "No hay direcciones registradas.\n\n" +
                        "Por favor, diríjase a 'Mis Direcciones' para agregar al menos DOS direcciones " +
                        "(una de origen y una de destino).";

                mostrarAlerta("Advertencia", mensajeDetallado, Alert.AlertType.WARNING);
            } else if (addresses.size() < 2) {
                mostrarAlerta("Recomendación",
                        "Solo tienes " + addresses.size() + " dirección registrada.\n" +
                                "Se recomienda agregar al menos 2 direcciones (origen y destino).",
                        Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    private void onCalcularCosto() {
        if (userService == null) {
            mostrarAlerta("Error", "El servicio no está inicializado.", Alert.AlertType.ERROR);
            return;
        }

        if (cmbOrigen.getValue() == null) {
            mostrarAlerta("Datos Incompletos", "Debe seleccionar una dirección de ORIGEN.", Alert.AlertType.WARNING);
            return;
        }

        if (cmbDestino.getValue() == null) {
            mostrarAlerta("Datos Incompletos", "Debe seleccionar una dirección de DESTINO.", Alert.AlertType.WARNING);
            return;
        }

        if (txtPeso.getText().isEmpty() || txtDimensiones.getText().isEmpty()) {
            mostrarAlerta("Datos Incompletos", "Debe ingresar el PESO y las DIMENSIONES.", Alert.AlertType.WARNING);
            return;
        }

        if (cmbServicio.getValue() == null) {
            mostrarAlerta("Datos Incompletos", "Debe seleccionar un TIPO DE SERVICIO.", Alert.AlertType.WARNING);
            return;
        }

        try {
            currentShipment = createShipmentFromInputs();


            if (currentShipment.getOriginAddress() == null || currentShipment.getDestinationAddress() == null) {
                throw new IllegalStateException("Error interno: El envío se creó sin direcciones.");
            }


            double costo = userService.getPrice(currentShipment);

            lblCostoEstimado.setText("$ " + String.format("%,.2f", costo));

            System.out.println("✅ Costo calculado exitosamente:");
            System.out.println("   📍 Origen: " + currentShipment.getOriginAddress().getCity());
            System.out.println("   📍 Destino: " + currentShipment.getDestinationAddress().getCity());
            System.out.println("   💰 Costo: $" + costo);

            mostrarAlerta("Éxito",
                    "Costo estimado calculado correctamente.\n\n" +
                            "Descripción: " + currentShipment.getDescription() + "\n" +
                            "Origen: " + currentShipment.getOriginAddress().getCity() + "\n" +
                            "Destino: " + currentShipment.getDestinationAddress().getCity(),
                    Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Entrada",
                    "Asegúrese de ingresar valores numéricos válidos para el peso y las dimensiones.",
                    Alert.AlertType.ERROR);
        } catch (IllegalStateException e) {
            mostrarAlerta("Error Crítico de Datos",
                    "Error al inicializar el envío: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error",
                    "No se pudo calcular el costo: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onConfirmarEnvioYtoPagar() {
        if (userService == null) {
            mostrarAlerta("Error", "El servicio no está inicializado.", Alert.AlertType.ERROR);
            return;
        }

        if (currentShipment == null) {
            mostrarAlerta("Advertencia",
                    "Primero debe calcular el costo del envío antes de confirmarlo.",
                    Alert.AlertType.WARNING);
            return;
        }

        if (currentShipment.getOriginAddress() == null) {
            System.err.println("❌ ERROR CRÍTICO: currentShipment no tiene dirección de origen");
            mostrarAlerta("Error Interno",
                    "El envío no tiene dirección de origen. Por favor, vuelva a calcular el costo.",
                    Alert.AlertType.ERROR);
            currentShipment = null;
            return;
        }

        if (currentShipment.getDestinationAddress() == null) {
            System.err.println("❌ ERROR CRÍTICO: currentShipment no tiene dirección de destino");
            mostrarAlerta("Error Interno",
                    "El envío no tiene dirección de destino. Por favor, vuelva a calcular el costo.",
                    Alert.AlertType.ERROR);
            currentShipment = null;
            return;
        }

        if (!confirmarAccion("Confirmar Envío",
                "Costo: " + lblCostoEstimado.getText() +
                        "\nOrigen: " + currentShipment.getOriginAddress().getCity() +
                        "\nDestino: " + currentShipment.getDestinationAddress().getCity() +
                        "\n\n¿Desea CONFIRMAR el envío y registrarlo para pago?")) {
            return;
        }

        try {
            System.out.println("🚀 Confirmando envío...");
            System.out.println("   📦 ID: " + currentShipment.getShipmentId());
            System.out.println("   📍 Origen: " + currentShipment.getOriginAddress().getCity());
            System.out.println("   📍 Destino: " + currentShipment.getDestinationAddress().getCity());


            userService.createShipment(currentShipment);

            String mensajeDetallado = "✅ Envío registrado para pago exitosamente.\n\n" +
                    "ID: " + currentShipment.getShipmentId() + "\n" +
                    "Origen: " + currentShipment.getOriginAddress().getCity() + "\n" +
                    "Destino: " + currentShipment.getDestinationAddress().getCity() + "\n" +
                    "Costo: " + lblCostoEstimado.getText() + "\n\n" +
                    "Proceda a la gestión de pagos para completar la transacción.";

            mostrarAlerta("Éxito", mensajeDetallado, Alert.AlertType.INFORMATION);

            currentShipment = null;
            lblCostoEstimado.setText("$ 0.00");


            cmbOrigen.getSelectionModel().clearSelection();
            cmbDestino.getSelectionModel().clearSelection();
            txtPeso.clear();
            txtDimensiones.clear();
            txtDescripcion.clear();
            if (chkSecureShipping != null) chkSecureShipping.setSelected(false);
            if (chkSignatureRequired != null) chkSignatureRequired.setSelected(false);

        } catch (IllegalStateException e) {

            mostrarAlerta("Error de Validación",
                    "Error al confirmar el envío:\n" + e.getMessage() +
                            "\n\nPor favor, vuelva a calcular el costo.",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
            currentShipment = null;
        } catch (Exception e) {
            mostrarAlerta("Error",
                    "No se pudo confirmar el envío: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private Shipment createShipmentFromInputs() throws NumberFormatException {

        String shipmentId = "SHIP-" + UUID.randomUUID().toString().substring(0, 8);
        Address origin = cmbOrigen.getValue();
        Address destination = cmbDestino.getValue();
        String baseServiceStrategy = cmbServicio.getValue().toLowerCase();

        System.out.println("🔧 Creando envío:");
        System.out.println("   - ID: " + shipmentId);
        System.out.println("   - Origen: " + (origin != null ? origin.getCity() : "NULL"));
        System.out.println("   - Destino: " + (destination != null ? destination.getCity() : "NULL"));
        System.out.println("   - Servicio: " + baseServiceStrategy);

        Rate shipmentRate = userService.getRateForService(baseServiceStrategy);

        if (shipmentRate == null) {
            throw new IllegalStateException("No se pudo cargar la tarifa base para el servicio: " +
                    baseServiceStrategy + ". Verifique que el UserService tenga la información de tarifas.");
        }


        double weightKg = Double.parseDouble(txtPeso.getText().trim());
        double heightCm = Double.parseDouble(txtDimensiones.getText().trim());
        String contentDescription = txtDescripcion.getText().trim();

        PackageModel tempPackage = new PackageModel();
        tempPackage.setIdPackage("PKG-" + System.currentTimeMillis());
        tempPackage.setWeight(weightKg);
        tempPackage.setHeightCm(heightCm);
        tempPackage.setDescription(contentDescription);


        String zone = origin.getCity() + "-" + destination.getCity();

        Shipment shipment = ShipmentFactory.createShipment(
                baseServiceStrategy,
                shipmentId,
                userService.getCurrentUser(),
                zone,
                origin,
                destination,
                tempPackage,
                shipmentRate
        );


        if (shipment.getOriginAddress() == null) {
            throw new IllegalStateException("ERROR: ShipmentFactory no estableció la dirección de origen");
        }
        if (shipment.getDestinationAddress() == null) {
            throw new IllegalStateException("ERROR: ShipmentFactory no estableció la dirección de destino");
        }

        System.out.println("✅ Envío creado por Factory con direcciones correctas");


        if (chkSecureShipping != null && chkSecureShipping.isSelected()) {
            shipment = new SecureShipping(shipment);
            System.out.println("   + Decorador SecureShipping aplicado");
        }

        if (chkSignatureRequired != null && chkSignatureRequired.isSelected()) {
            shipment = new SignatureRequiredShipment(shipment);
            System.out.println("   + Decorador SignatureRequiredShipment aplicado");
        }


        if (shipment.getOriginAddress() == null || shipment.getDestinationAddress() == null) {
            throw new IllegalStateException("ERROR: Los decoradores eliminaron las direcciones del envío");
        }

        return shipment;
    }


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private boolean confirmarAccion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        Optional<ButtonType> resultado = alerta.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}