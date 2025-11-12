package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;
import co.edu.uniquindio.proyectofinalp2.strategy.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final CompanyService companyService;
    private final User user;

    /**
     * Constructor que inicializa el servicio y asegura la integridad de los datos del usuario.
     * @param user El usuario logueado.
     */
    public UserService(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario asociado al UserService no puede ser nulo.");
        }

        this.user = user;
        this.companyService = CompanyService.getInstance();

        // 💡 Inicialización de colecciones para evitar NullPointerExceptions
        if (this.user.getAddresses() == null) {
            this.user.setAddresses(new ArrayList<>());
        }
        if (this.user.getPayments() == null) {
            this.user.setPayments(new ArrayList<>());
        }
        if (this.user.getShipments() == null) {
            this.user.setShipments(new ArrayList<>());
        }
        if (this.user.getPackages() == null) {
            this.user.setPackages(new ArrayList<>());
        }

        System.out.println("✅ UserService inicializado y listas de datos verificadas.");
    }

    public User getCurrentUser() {
        return user;
    }

    // -----------------------------------------------------------------------------------
    // --- MÉTODO AÑADIDO: Obtención de Rate (Estrategia) para el Controller ---
    // -----------------------------------------------------------------------------------

    /**
     * Define la ShippingCostStrategy apropiada para el tipo de servicio y la envuelve en un objeto Rate.
     * Esto permite al Controller usar el Factory con la tarifa ya configurada.
     * @param serviceType El tipo de servicio solicitado (e.g., "priority", "fragile").
     * @return Un objeto Rate con la estrategia de costo correspondiente.
     */
    public Rate getRateForService(String serviceType) {
        ShippingCostStrategy costStrategy;
        String rateId = "RT-" + serviceType.toUpperCase() + "-" + java.util.UUID.randomUUID().toString().substring(0, 4);

        switch (serviceType.toLowerCase()) {
            case "priority":
                costStrategy = new PriorityCostStrategy();
                break;
            case "fragile":
                costStrategy = new FragileCostStrategy();
                break;
            case "secure":
                // Los Decoradores Secure/Signature se manejan en el Factory/Controller,
                // pero si el servicio tiene una tarifa base específica, se define aquí.
                costStrategy = new SecureCostStrategy();
                break;
            case "signature":
                costStrategy = new SignatureCostStrategy();
                break;
            case "normal":
            default:
                costStrategy = new NormalCostStrategy();
                break;
        }

        return new Rate(rateId, costStrategy);
    }

    // --- Métodos de Gestión de Perfil (RF-014) ---
    public UserDTO getCurrentUserProfileDTO() {
        if (this.user != null) {
            UserDTO dto = new UserDTO();
            dto.setEmail(this.user.getEmail());
            dto.setFullname(this.user.getFullname());
            dto.setPhone(this.user.getPhone());
            return dto;
        }
        return null;
    }

    public void updateUserProfile(UserDTO userDTO) {
        if (userDTO == null || userDTO.getFullname() == null || userDTO.getFullname().trim().isEmpty()) {
            throw new IllegalArgumentException("Datos del DTO incompletos (Nombre Requerido).");
        }

        User userToUpdate = this.user;

        userToUpdate.setFullname(userDTO.getFullname());
        userToUpdate.setPhone(userDTO.getPhone());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            userToUpdate.setPassword(userDTO.getPassword());
            System.out.println("DEBUG: Contraseña actualizada.");
        }
        System.out.println("✅ Perfil de usuario actualizado en memoria.");
    }

    // -----------------------------------------------------------------------------------
    // --- Métodos de Gestión de Paquetes ---
    // -----------------------------------------------------------------------------------

    public List<PackageModel> listUserPackages() {
        return this.user.getPackages();
    }

    public void addPackageToUser(PackageModel newPackage) {
        if (newPackage == null) {
            throw new IllegalArgumentException("Paquete inválido.");
        }
        if (newPackage.getIdPackage() == null || newPackage.getIdPackage().isEmpty()) {
            newPackage.setIdPackage(java.util.UUID.randomUUID().toString());
        }
        this.user.getPackages().add(newPackage);
    }

    public void updatePackage(PackageModel updatedPackage) {
        if (updatedPackage == null || updatedPackage.getIdPackage() == null) {
            throw new IllegalArgumentException("Paquete de actualización o ID inválidos");
        }

        PackageModel existingPackage = this.user.getPackages().stream()
                .filter(p -> p.getIdPackage().equals(updatedPackage.getIdPackage()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Paquete no encontrado: " + updatedPackage.getIdPackage()));

        existingPackage.setName(updatedPackage.getName());
        existingPackage.setDescription(updatedPackage.getDescription());
        existingPackage.setHeightCm(updatedPackage.getHeightCm());
        existingPackage.setWeight(updatedPackage.getWeight());
    }

    public void deletePackage(String idPackage) {
        if (idPackage == null) {
            throw new IllegalArgumentException("ID de paquete inválido");
        }

        boolean removed = this.user.getPackages().removeIf(p -> p.getIdPackage().equals(idPackage));

        if (!removed) {
            throw new NotFoundException("No se encontró ningún paquete con ID: " + idPackage);
        }
    }

    // -----------------------------------------------------------------------------------
    // --- Métodos de Gestión de Dirección (RF-015) ---
    // -----------------------------------------------------------------------------------

    public void addAddressToUser(Address newAddress) {
        if (newAddress == null) {
            throw new IllegalArgumentException("Dirección inválida");
        }
        if (newAddress.getIdAddress() == null || newAddress.getIdAddress().isEmpty()) {
            newAddress.setIdAddress(java.util.UUID.randomUUID().toString());
        }
        this.user.getAddresses().add(newAddress);
    }

    public void updateAddress(String idAddress, Address updatedAddress) {
        if (idAddress == null || updatedAddress == null) {
            throw new IllegalArgumentException("ID de dirección o dirección de actualización inválidos");
        }

        Address address = this.user.getAddresses().stream()
                .filter(a -> a.getIdAddress().equals(idAddress))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Dirección no encontrada: " + idAddress));

        address.setIdAddress(updatedAddress.getIdAddress());
        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setPostalCode(updatedAddress.getPostalCode());
    }

    public void deleteAddress(String idAddress) {
        if (idAddress == null) {
            throw new IllegalArgumentException("ID de dirección inválido");
        }

        boolean removed = this.user.getAddresses().removeIf(a -> a.getIdAddress().equals(idAddress));

        if (!removed) {
            throw new NotFoundException("No se encontró ninguna dirección con ID: " + idAddress);
        }
    }

    public List<Address> listAddresses() {
        return this.user.getAddresses();
    }

    // --- Métodos de Gestión de Pagos ---
    public List<Payment> listPayments() {
        return this.user.getPayments();
    }


    // -----------------------------------------------------------------------------------
    // --- Métodos de Gestión de Envío (RF-016, RF-017, RF-018) ---
    // -----------------------------------------------------------------------------------

    public double getPrice(Shipment shipment) {
        if (shipment.getRate() == null) {
            throw new IllegalStateException("El envío no tiene una tarifa base asignada (Rate).");
        }

        return shipment.getPrice();
    }

    public void createShipment(Shipment shipment) {
        // Validación crítica: Verificar que las direcciones estén configuradas
        if (shipment.getOriginAddress() == null) {
            throw new IllegalStateException("❌ El envío debe tener una dirección de origen (originAddress).");
        }

        if (shipment.getDestinationAddress() == null) {
            throw new IllegalStateException("❌ El envío debe tener una dirección de destino (destinationAddress).");
        }

        // Configurar datos básicos
        shipment.setCreationDate(LocalDateTime.now());
        shipment.setUser(this.user);

        if (shipment.getShipmentId() == null || shipment.getShipmentId().isEmpty()) {
            shipment.setShipmentId(java.util.UUID.randomUUID().toString());
        }

        // Establecer estado inicial
        if (shipment.getStatus() == null) {
            shipment.setStatus(ShippingStatus.PENDING_PICKUP);
        }

        // Agregar a la lista temporal del usuario
        this.user.getShipments().add(shipment);

        // Agregar a la lista central de la compañía para que el admin lo vea
        companyService.addShipmentToCompany(shipment);

        System.out.println("✅ Envío creado: " + shipment.getShipmentId() +
                " con estado " + shipment.getStatus());
        System.out.println("   📍 Origen: " + shipment.getOriginAddress().getCity());
        System.out.println("   📍 Destino: " + shipment.getDestinationAddress().getCity());
    }

// -----------------------------------------------------------------------------------
// --- MÉTODO OPCIONAL AUXILIAR: Para setear direcciones post-creación ---
// -----------------------------------------------------------------------------------

    /**
     * Método auxiliar para establecer direcciones en un envío ya creado.
     * Útil si el Controller no puede usar el Builder correctamente.
     *
     * @param shipmentId ID del envío
     * @param originAddress Dirección de origen
     * @param destinationAddress Dirección de destino
     */
    public void setShipmentAddresses(String shipmentId, Address originAddress, Address destinationAddress) {
        if (originAddress == null || destinationAddress == null) {
            throw new IllegalArgumentException("Las direcciones no pueden ser nulas");
        }

        Shipment shipment = findShipmentTempById(shipmentId);
        shipment.setOriginAddress(originAddress);
        shipment.setDestinationAddress(destinationAddress);

        System.out.println("✅ Direcciones establecidas para envío " + shipmentId);
    }

    public Shipment updateShipment(String shipmentId, String newZone, String newPeriod){
        Shipment shipmentAux = findShipmentTempById(shipmentId);
        shipmentAux.setZone(newZone);
        shipmentAux.setPeriod(newPeriod);
        return shipmentAux;
    }

    public void cancelShipment(String shipmentId){
        Shipment shipmentAux = findShipmentTempById(shipmentId);
        this.user.getShipments().remove(shipmentAux);
    }

    public void confirmShipment(String shipmentId) {
        Shipment shipmentAux = findShipmentTempById(shipmentId);

        if (shipmentAux.getRate() == null) {
            throw new IllegalStateException("El envío debe tener un Rate calculado por el Factory para ser confirmado.");
        }

        if (shipmentAux.getCreationDate() == null) {
            shipmentAux.setCreationDate(LocalDateTime.now());
        }

        System.out.println("✅ Envío " + shipmentId + " confirmado y tarifado en memoria.");
    }

    public void payShipment(String shipmentId, double amount) {
        Shipment shipment = findShipmentTempById(shipmentId);

        double finalShipmentCost = shipment.getPrice();

        if (finalShipmentCost <= 0) {
            throw new IllegalStateException("El envío no ha sido confirmado (costo no calculado o es cero).");
        }

        if (amount >= finalShipmentCost) {
            Payment payment = new Payment(
                    "PAY-" + System.currentTimeMillis(),
                    amount,
                    LocalDateTime.now(),
                    true, user
            );
            this.user.getPayments().add(payment);
            shipment.setPayment(payment);

            // ✅ FIX: Cambiar estado a CREATED cuando se paga (listo para asignar repartidor)
            shipment.setStatus(ShippingStatus.CREATED);

            // ✅ FIX: Ya no necesitamos transferir porque ya está en company.getShipments()
            // Solo removemos de la lista temporal del usuario
            this.user.getShipments().remove(shipment);

            // ❌ ELIMINAR esta línea porque el envío YA está en Company desde createShipment()
            // companyService.addShipmentToCompany(shipment);

            System.out.println("✅ Envío " + shipmentId + " pagado. Estado actualizado a CREATED (listo para asignación).");
        } else {
            throw new IllegalArgumentException("Monto insuficiente para pagar el envío.");
        }
    }


    private Shipment findShipmentTempById(String shipmentId){
        return this.user.getShipments().stream()
                .filter(s -> s.getShipmentId().equals(shipmentId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Shipment with ID: " + shipmentId + " not found in user's temporary list"));
    }


    public List<Shipment> shipmentsHistory(LocalDateTime date, ShippingStatus shippingStatus){
        return this.user.getShipments().stream()
                .filter(shipment -> shipment.getCreationDate().isBefore(date))
                .filter(shipment -> shipment.getStatus().equals(shippingStatus))
                .collect(Collectors.toList());
    }

}