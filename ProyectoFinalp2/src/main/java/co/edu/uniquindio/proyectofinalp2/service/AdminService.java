package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminService {
    private static AdminService instance;

    private final CompanyService companyService;
    private final Company company;

    private AdminService() {
        this.companyService = CompanyService.getInstance();
        this.company = Company.getInstance();
    }
    public static AdminService getInstance() {
        if (instance == null) {
            instance = new AdminService();
        }
        return instance;
    }

    public Company getCompany(){
        return company;
    }

    public Optional<User> findUserByID(String id) {
        return company.getUsers().stream()
                .filter(user -> user.getId() != null && user.getId().equals(id))
                .findFirst();
    }

    public Optional<Dealer> findDealerByID(String id) {
        return company.getDealers().stream()
                .filter(d -> d.getId() != null && d.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findUserByEmail(String email) {
        return company.getUsers().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }




    public List<User> listAllUsers() {
        return company.getUsers();
    }

    public boolean addUserAdmin(User newUser) {
        if (findUserByID(newUser.getId()).isPresent()) {
            System.out.println("⚠️ El usuario ya existe con ID: " + newUser.getId());
            return false;
        }
        company.getUsers().add(newUser);
        System.out.println("✅ Usuario añadido correctamente: " + newUser.getFullname());
        return true;
    }

    public boolean deleteUserAdmin(User newUser) {
        Optional<User> userOpt = findUserByID(newUser.getId());

        if (userOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró ningún usuario con ID: " + newUser.getId());
            return false;
        }

        company.getUsers().remove(userOpt.get());
        System.out.println("🗑️ Usuario eliminado correctamente: " + newUser.getFullname());
        return true;
    }

    public boolean updateUserAdmin(User newUser) {
        Optional<User> userOpt = findUserByID(newUser.getId());

        if (userOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró ningún usuario con ID: " + newUser.getId());
            return false;
        }

        User existingUser = userOpt.get();

        existingUser.setFullname(newUser.getFullname());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setPhone(newUser.getPhone());
        existingUser.setPassword(newUser.getPassword());
        existingUser.setRol(newUser.getRol());

        System.out.println("✅ Usuario actualizado correctamente: " + existingUser.getFullname());
        return true;
    }

    public void showAllUsersAdmin() {
        List<User> users = company.getUsers();

        if (users.isEmpty()) {
            System.out.println("⚠️ No hay usuarios registrados actualmente.");
            return;
        }

        System.out.println("📋 Lista de usuarios registrados:");
        for (User user : users) {
            System.out.println("----------------------------------------");
            System.out.println("🆔 ID: " + (user.getId() != null ? user.getId() : "N/A"));
            System.out.println("👤 Nombre completo: " + user.getFullname());
            System.out.println("📧 Correo: " + user.getEmail());
            System.out.println("📞 Teléfono: " + user.getPhone());
            System.out.println("🎭 Rol: " + user.getRol());
        }
        System.out.println("----------------------------------------");
    }


    public boolean addDealerAdmin(Dealer newDealer) {
        if (findDealerByID(newDealer.getId()).isPresent()) {
            System.out.println("⚠️ El repartidor ya existe con ID: " + newDealer.getId());
            return false;
        }

        company.getDealers().add(newDealer);
        System.out.println("✅ Repartidor añadido correctamente: " + newDealer.getFullname());
        return true;
    }

    public boolean deleteDealerAdmin(Dealer newDealer) {
        Optional<Dealer> dealerOpt = findDealerByID(newDealer.getId());

        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró ningún repartidor con ID: " + newDealer.getId());
            return false;
        }

        company.getDealers().remove(dealerOpt.get());
        System.out.println("🗑️ Repartidor eliminado correctamente: " + newDealer.getFullname());
        return true;
    }

    public boolean updateDealerAdmin(Dealer newDealer) {
        Optional<Dealer> dealerOpt = findDealerByID(newDealer.getId());

        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró ningún repartidor con ID: " + newDealer.getId());
            return false;
        }

        Dealer existingDealer = dealerOpt.get();

        // Actualizar los datos del repartidor
        existingDealer.setFullname(newDealer.getFullname());
        existingDealer.setEmail(newDealer.getEmail());
        existingDealer.setPhone(newDealer.getPhone());
        existingDealer.setAvailable(newDealer.getAvailable());
        existingDealer.setDeliveriesMade(newDealer.getDeliveriesMade());

        System.out.println("✅ Repartidor actualizado correctamente: " + existingDealer.getFullname());
        return true;
    }

    public void showAllDealersAdmin() {
        List<Dealer> dealers = company.getDealers();

        if (dealers.isEmpty()) {
            System.out.println("⚠️ No hay repartidores registrados actualmente.");
            return;
        }

        System.out.println("📋 Lista de repartidores registrados:");
        for (Dealer dealer : dealers) {
            System.out.println("----------------------------------------");
            System.out.println("🆔 ID: " + dealer.getId());
            System.out.println("👤 Nombre completo: " + dealer.getFullname());
            System.out.println("📧 Correo: " + dealer.getEmail());
            System.out.println("📞 Teléfono: " + dealer.getPhone());
            System.out.println("🚦 Disponibilidad: " + dealer.getAvailable());
            System.out.println("📦 Entregas realizadas: " + dealer.getDeliveriesMade());
        }
        System.out.println("----------------------------------------");
    }

    public boolean assignOrReassignShipment(String idShipment, String idDealer) {
        Optional<Shipment> shipmentOpt = company.getShipments().stream()
                .filter(s -> s.getShipmentId() != null && s.getShipmentId().equals(idShipment))
                .findFirst();

        Optional<Dealer> dealerOpt = findDealerByID(idDealer);

        if (shipmentOpt.isEmpty() || dealerOpt.isEmpty()) {
            System.out.println("⚠️ Error: Envío o repartidor no encontrado.");
            return false;
        }

        Shipment shipment = shipmentOpt.get();
        Dealer newDealer = dealerOpt.get();
        Dealer previousDealer = shipment.getAssignedDealer();

        if (shipment.getOriginAddress() == null || shipment.getDestinationAddress() == null) {
            User user = shipment.getUser();

            if (user == null) {
                System.err.println("❌ ERROR: El envío " + idShipment + " no tiene usuario asociado.");
                return false;
            }

            if (user.getAddresses() == null || user.getAddresses().isEmpty()) {
                System.err.println("❌ ERROR: El usuario " + user.getFullname() +
                        " no tiene direcciones registradas.");
                System.err.println("❌ El usuario debe registrar al menos 2 direcciones antes de crear envíos.");
                return false;
            }

            System.out.println("⚠️ ADVERTENCIA: Envío " + idShipment + " sin direcciones.");
            System.out.println("⚠️ Estableciendo direcciones del usuario automáticamente...");

            if (shipment.getOriginAddress() == null) {
                Address origin = user.getAddresses().get(0);
                shipment.setOriginAddress(origin);
                System.out.println("✅ Origen establecido: " + origin.getCity() + ", " + origin.getStreet());
            }

            if (shipment.getDestinationAddress() == null) {
                if (user.getAddresses().size() > 1) {
                    Address destination = user.getAddresses().get(1);
                    shipment.setDestinationAddress(destination);
                    System.out.println("✅ Destino establecido: " + destination.getCity() + ", " + destination.getStreet());
                } else {
                    Address destination = user.getAddresses().get(0);
                    shipment.setDestinationAddress(destination);
                    System.out.println("⚠️ Solo hay una dirección. Usando como origen Y destino temporalmente.");
                    System.out.println("⚠️ Se recomienda que el usuario registre una segunda dirección.");
                }
            }
        }

        if (shipment.getOriginAddress() == null || shipment.getDestinationAddress() == null) {
            System.err.println("❌ ERROR CRÍTICO: No se pudieron establecer las direcciones del envío.");
            return false;
        }

        if (previousDealer != null) {
            if (previousDealer.equals(newDealer)) {
                System.out.println("⚠️ El envío ya está asignado a este mismo repartidor.");
                return false;
            }

            previousDealer.getAssignedShipments().remove(shipment);
            previousDealer.setAvailable(true);
            System.out.println("🔄 Envío " + idShipment + " reasignado de " +
                    previousDealer.getFullname() + " a " + newDealer.getFullname());
        } else {
            System.out.println("🚚 Envío " + idShipment + " asignado a " + newDealer.getFullname());
        }

        shipment.setAssignedDealer(newDealer);  // Shipment → Dealer
        newDealer.addShipment(shipment);         // Dealer → Shipment (CRÍTICO)

        shipment.setStatus(ShippingStatus.CREATED);
        newDealer.setAvailable(false);

        System.out.println("✅ Asignación exitosa:");
        System.out.println("   📦 Envío: " + shipment.getShipmentId());
        System.out.println("   👤 Usuario: " + shipment.getUser().getFullname());
        System.out.println("   📍 Origen: " + shipment.getOriginAddress().getCity());
        System.out.println("   📍 Destino: " + shipment.getDestinationAddress().getCity());
        System.out.println("   🚚 Repartidor: " + newDealer.getFullname());
        System.out.println("   📊 Total envíos del repartidor: " + newDealer.getAssignedShipments().size());

        return true;
    }


    public boolean registerShipmentIncidence(String idShipment, Incidence incidence) {
        Optional<Shipment> shipmentOpt = company.getShipments().stream()
                .filter(s -> s.getShipmentId() != null && s.getShipmentId().equals(idShipment))
                .findFirst();

        if (shipmentOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró el envío con ID: " + idShipment);
            return false;
        }

        Shipment shipment = shipmentOpt.get();
        if (shipment.getIncidence() != null) {
            System.out.println("⚠️ El envío " + idShipment + " ya tenía una incidencia registrada.");
        }

        shipment.setIncidence(incidence);
        shipment.setStatus(ShippingStatus.INCIDENCE_REPORTED);
        System.out.println("⚠️ Nueva incidencia registrada para el envío " + idShipment);

        Dealer dealer = shipment.getAssignedDealer();
        if (dealer != null) {
            // El envío permanece en la lista del dealer para seguimiento
            dealer.setAvailable(true); // Se libera para recibir nuevos envíos
            System.out.println("🚫 Repartidor " + dealer.getFullname() +
                    " marcado como disponible. Envío permanece en su lista para seguimiento.");
        }
        return true;
    }


    public boolean updateShipmentStatus(String idShipment, ShippingStatus newStatus) {
        Optional<Shipment> shipmentOpt = company.getShipments().stream()
                .filter(s -> s.getShipmentId() != null && s.getShipmentId().equals(idShipment))
                .findFirst();

        if (shipmentOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró el envío con ID: " + idShipment);
            return false;
        }

        Shipment shipment = shipmentOpt.get();
        ShippingStatus oldStatus = shipment.getStatus();
        shipment.setStatus(newStatus);

        System.out.println("🔄 Estado del envío " + idShipment + " cambiado de " + oldStatus + " a " + newStatus);

        Dealer dealer = shipment.getAssignedDealer();
        if (dealer != null) {
            switch (newStatus) {
                case DELIVERED, CANCELLED -> {
                    dealer.setAvailable(true);
                    System.out.println("✅ Repartidor " + dealer.getFullname() +
                            " ahora está disponible. Envío permanece en historial.");
                }
                case IN_TRANSIT -> {
                    dealer.setAvailable(false);
                    System.out.println("🚚 Repartidor " + dealer.getFullname() + " en ruta.");
                }
                case CREATED -> {
                    dealer.setAvailable(false);
                    System.out.println("📦 Envío creado, repartidor ocupado.");
                }
                case INCIDENCE_REPORTED -> {
                    dealer.setAvailable(true);
                    System.out.println("⚠️ Incidencia reportada, repartidor disponible.");
                }
            }
        }
        return true;
    }



    public Map<String, Double> getAverageDeliveryTimeByZone() {
        return company.getShipments().stream()
                .filter(s -> s.getStatus() == ShippingStatus.DELIVERED && s.getEstimatedDeliveryDate() > 0 && s.getZone() != null)
                .collect(Collectors.groupingBy(
                        Shipment::getZone,
                        Collectors.averagingDouble(Shipment::getEstimatedDeliveryDate)
                ));
    }

    public Map<String, Long> getMostUsedAdditionalServices() {
        return company.getShipments().stream()
                .flatMap(s -> s.getAdditionalServices().stream())
                .collect(Collectors.groupingBy(
                        service -> service,
                        Collectors.counting()
                ));
    }

    public Map<String, Double> getIncomeByPeriod() {
        return company.getShipments().stream()
                .filter(s -> s.getPeriod() != null && s.getRate() != null && s.getRate().getBasePrice() > 0)
                .collect(Collectors.groupingBy(
                        Shipment::getPeriod,
                        Collectors.summingDouble(Shipment::getPrice)
                ));
    }

    public Map<String, Long> getIncidencesByZone() {
        return company.getShipments().stream()
                .filter(s -> s.getStatus() == ShippingStatus.INCIDENCE_REPORTED && s.getZone() != null)
                .collect(Collectors.groupingBy(
                        Shipment::getZone,
                        Collectors.counting()
                ));
    }
}