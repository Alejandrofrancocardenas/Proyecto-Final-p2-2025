package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminService {

    private CompanyService companyService;

    public AdminService() {
        this.companyService = companyService;
    }


    // Buscar usuario
    public Optional<User> findUserByID(String id) {
        return companyService.findUserByID(id);
    }

    //Buscar Dealer
    public Optional<Dealer> findDealerByID(String id) {
        return companyService.findDealerByID(id);
    }

    // Metodos Administrador: GestionarUsuarios
    public boolean addUserAdmin(User newUser) {
        if (findUserByID(newUser.getId()).isPresent()) {
            System.out.println("⚠️ El usuario ya existe con ID: " + newUser.getId());
            return false;
        }

        companyService.getCompany().getUsers().add(newUser);
        System.out.println("✅ Usuario añadido correctamente: " + newUser.getFullname());
        return true;
    }

    public boolean deleteUserAdmin(User newUser) {
        Optional<User> userOpt = findUserByID(newUser.getId());

        if (userOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró ningún usuario con ID: " + newUser.getId());
            return false;
        }

        companyService.getCompany().getUsers().remove(userOpt.get());
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

        // Actualizar los datos del usuario
        existingUser.setFullname(newUser.getFullname());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setPhone(newUser.getPhone());
        existingUser.setPassword(newUser.getPassword());
        existingUser.setRol(newUser.getRole());

        System.out.println("✅ Usuario actualizado correctamente: " + existingUser.getFullname());
        return true;
    }

    public void showAllUsersAdmin() {
        List<User> users = companyService.getCompany().getUsers();

        if (users.isEmpty()) {
            System.out.println("⚠️ No hay usuarios registrados actualmente.");
            return;
        }

        System.out.println("📋 Lista de usuarios registrados:");
        for (User user : users) {
            System.out.println("----------------------------------------");
            System.out.println("🆔 ID: " + user.getId());
            System.out.println("👤 Nombre completo: " + user.getFullname());
            System.out.println("📧 Correo: " + user.getEmail());
            System.out.println("📞 Teléfono: " + user.getPhone());
            System.out.println("🎭 Rol: " + user.getRole());
        }
        System.out.println("----------------------------------------");
    }

    // Metodos Administrador: GestionarDealers
    public boolean addDealerAdmin(Dealer newDealer) {
        if (findDealerByID(newDealer.getId()).isPresent()) {
            System.out.println("⚠️ El repartidor ya existe con ID: " + newDealer.getId());
            return false;
        }

        companyService.getCompany().getDealers().add(newDealer);
        System.out.println("✅ Repartidor añadido correctamente: " + newDealer.getFullname());
        return true;
    }

    public boolean deleteDealerAdmin(Dealer newDealer) {
        Optional<Dealer> dealerOpt = findDealerByID(newDealer.getId());

        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró ningún repartidor con ID: " + newDealer.getId());
            return false;
        }

        companyService.getCompany().getDealers().remove(dealerOpt.get());
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
        existingDealer.setAvaliable(newDealer.getAvaliable());
        existingDealer.setDeliveriesMade(newDealer.getDeliveriesMade());

        System.out.println("✅ Repartidor actualizado correctamente: " + existingDealer.getFullname());
        return true;
    }

    public void showAllDealersAdmin() {
        List<Dealer> dealers = companyService.getCompany().getDealers();

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
            System.out.println("🚦 Disponibilidad: " + dealer.getAvaliable());
            System.out.println("📦 Entregas realizadas: " + dealer.getDeliveriesMade());
        }
        System.out.println("----------------------------------------");
    }

// Metodos Asignar/reasignar envíos a repartidores, registrar incidencias y cambios de estado

    public boolean assignOrReassignShipment(String idShipment, String idDealer) {
        Optional<Shipment> shipmentOpt = companyService.getCompany().getShipments().stream()
                .filter(s -> s.getShipmentId().equals(idShipment))
                .findFirst();

        Optional<Dealer> dealerOpt = companyService.getCompany().getDealers().stream()
                .filter(d -> d.getId().equals(idDealer))
                .findFirst();

        if (shipmentOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró el envío con ID: " + idShipment);
            return false;
        }

        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró el repartidor con ID: " + idDealer);
            return false;
        }

        Shipment shipment = shipmentOpt.get();
        Dealer newDealer = dealerOpt.get();

        Dealer previousDealer = shipment.getAssignedDealer();

        // Si ya tenía repartidor, reasignamos
        if (previousDealer != null && !previousDealer.equals(newDealer)) {
            previousDealer.setAvaliable(true); // liberar al anterior
            System.out.println("🔄 Envío " + idShipment + " reasignado de "
                    + previousDealer.getFullname() + " a " + newDealer.getFullname());
        } else if (previousDealer != null && previousDealer.equals(newDealer)) {
            System.out.println("⚠️ El envío ya está asignado a este mismo repartidor.");
            return false;
        } else {
            System.out.println("🚚 Envío " + idShipment + " asignado a " + newDealer.getFullname());
        }

        shipment.setAssignedDealer(newDealer);
        shipment.setStatus(ShippingStatus.ASSIGNED);
        newDealer.setAvaliable(false);
        return true;
    }


    public boolean registerShipmentIncidence(String idShipment, Incidence incidence) {
        Optional<Shipment> shipmentOpt = companyService.getCompany().getShipments().stream()
                .filter(s -> s.getShipmentId().equals(idShipment))
                .findFirst();

        if (shipmentOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró el envío con ID: " + idShipment);
            return false;
        }

        Shipment shipment = shipmentOpt.get();

        // Verificamos si ya tenía una incidencia previa
        if (shipment.getIncidence() != null) {
            System.out.println("⚠️ El envío " + idShipment + " ya tenía una incidencia registrada.");
        }

        shipment.setIncidence(incidence);
        shipment.setStatus(ShippingStatus.INCIDENCE);

        System.out.println("⚠️ Nueva incidencia registrada para el envío " + idShipment + ": " + incidence.getDescription());

        Dealer dealer = shipment.getAssignedDealer();
        if (dealer != null) {
            dealer.setAvaliable(false); // bloquea temporalmente al repartidor
            System.out.println("🚫 Repartidor " + dealer.getFullname() + " marcado como no disponible por incidencia.");
        }

        return true;
    }


    public boolean updateShipmentStatus(String idShipment, ShippingStatus newStatus) {
        Optional<Shipment> shipmentOpt = companyService.getCompany().getShipments().stream()
                .filter(s -> s.getShipmentId().equals(idShipment))
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

        // Cambiamos disponibilidad del repartidor según el estado del envío
        if (dealer != null) {
            switch (newStatus) {
                case DELIVERED -> {
                    dealer.setAvaliable(true);
                    System.out.println("✅ Envío entregado. Repartidor " + dealer.getFullname() + " ahora está disponible.");
                }
                case CANCELLED, INCIDENCE -> {
                    dealer.setAvaliable(true);
                    System.out.println("🚫 Envío cancelado/incidencia. Repartidor liberado.");
                }
                case ENROUTE, ASSIGNED -> dealer.setAvaliable(false);
            }
        }

        return true;
    }


// Metodos metricas

    /**
     * Calcula el tiempo promedio de entrega por zona.
     * Usa el campo estimatedDeliveryDate del envío (horas estimadas de entrega).
     * Retorna un Map con zona -> promedio en horas.
     */
    public Map<String, Double> getAverageDeliveryTimeByZone() {
        return companyService.getCompany().getShipments().stream()
                .filter(s -> s.getStatus() == ShippingStatus.DELIVERED && s.getEstimatedDeliveryDate() > 0)
                .collect(Collectors.groupingBy(
                        Shipment::getZone,
                        Collectors.averagingDouble(Shipment::getEstimatedDeliveryDate)
                ));
    }

    /**
     * Cuenta los servicios adicionales más usados.
     * Usa la lista additionalServices de cada envío.
     * Retorna un Map con nombre del servicio -> cantidad de veces usado.
     */
    public Map<String, Long> getMostUsedAdditionalServices() {
        return companyService.getCompany().getShipments().stream()
                .flatMap(s -> s.getAdditionalServices().stream())
                .collect(Collectors.groupingBy(
                        service -> service,
                        Collectors.counting()
                ));
    }

    /**
     * Calcula los ingresos totales agrupados por periodo (por ejemplo: "Octubre 2025").
     * Usa el atributo period de Shipment y el precio total del envío.
     * Retorna un Map con periodo -> suma de precios.
     */
    public Map<String, Double> getIncomeByPeriod() {
        return companyService.getCompany().getShipments().stream()
                .filter(s -> s.getPeriod() != null && s.getRate().getBase() > 0)
                .collect(Collectors.groupingBy(
                        Shipment::getPeriod,
                        Collectors.summingDouble(s -> s.getRate().getBase())
                ));
    }

    /**
     * Cuenta cuántas incidencias existen por zona.
     * Usa el atributo zone y que el estado sea INCIDENCE.
     * Retorna un Map con zona -> cantidad de incidencias.
     */
    public Map<String, Long> getIncidencesByZone() {
        return companyService.getCompany().getShipments().stream()
                .filter(s -> s.getStatus() == ShippingStatus.INCIDENCE && s.getZone() != null)
                .collect(Collectors.groupingBy(
                        Shipment::getZone,
                        Collectors.counting()
                ));
    }



}
