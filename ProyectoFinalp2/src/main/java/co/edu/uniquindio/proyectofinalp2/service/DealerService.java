package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import java.util.*;
import java.util.UUID; // Importado para generar IDs únicos

public class DealerService {

    // 1. Campo estático para la única instancia del servicio
    private static DealerService instance;

    // Referencia al servicio central Singleton
    private final CompanyService companyService;
    private final Company company; // Referencia a Company para búsquedas locales y acceso a listas

    // 2. Constructor privado (Patrón Singleton)
    private DealerService() {
        // Inicializa la referencia usando el Singleton de CompanyService
        this.companyService = CompanyService.getInstance();
        this.company = Company.getInstance();
    }

    // 3. Método de acceso público y estático (El "Getter" del Singleton)
    public static DealerService getInstance() {
        if (instance == null) {
            instance = new DealerService();
        }
        return instance;
    }

    // --- MÉTODOS DE BÚSQUEDA LOCAL ---

    /**
     * Busca un repartidor por su ID de manera segura, manejando IDs nulos en la lista.
     * @param id El ID del repartidor a buscar.
     * @return Un Optional que contiene el Dealer si se encuentra, o vacío si no.
     */
    public Optional<Dealer> findDealerById(String id) {
        // Cláusula de guardia: Si el ID de búsqueda es nulo o vacío, retornamos Optional vacío inmediatamente.
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }

        return company.getDealers().stream()
                // Uso seguro de equals: Si d.getId() es null, retorna false, no lanza NPE.
                .filter(d -> id.equals(d.getId()))
                .findFirst();
    }

    /**
     * Retorna la lista completa de repartidores registrados en la compañía.
     * @return List<Dealer> La lista de todos los repartidores.
     */
    public List<Dealer> listAllDealers() {
        return company.getDealers();
    }

    // --- MÉTODOS CRUD (RF-019) ---

    // RF-019: Crear repartidor
    public boolean addDealer(Dealer dealer) {
        if (dealer == null) {
            System.err.println("⚠️ No se puede añadir un repartidor nulo.");
            return false;
        }

        // FIX CRÍTICO: Si el repartidor no tiene ID (es nuevo), se genera uno.
        if (dealer.getId() == null || dealer.getId().trim().isEmpty()) {
            // Generar un ID único (UUID)
            String newId = UUID.randomUUID().toString();
            // Asume que la clase Dealer tiene el método setId(String)
            dealer.setId(newId);

            System.out.println("🔑 ID generado automáticamente: " + newId);
        } else {
            // Si el ID ya viene establecido, comprobamos si ya existe para evitar duplicados
            if (findDealerById(dealer.getId()).isPresent()) {
                System.out.println("⚠️ Ya existe un repartidor con ID: " + dealer.getId());
                return false;
            }
        }

        // El repartidor (ahora con ID) se añade a la lista
        company.getDealers().add(dealer);
        System.out.println("✅ Repartidor añadido correctamente con ID: " + dealer.getId() + " - " + dealer.getFullname());
        return true;
    }

    // RF-019: Actualizar repartidor
    public boolean updateDealer(Dealer updatedDealer) {
        if (updatedDealer == null || updatedDealer.getId() == null) {
            System.err.println("⚠️ No se puede actualizar un repartidor nulo o sin ID.");
            return false;
        }

        Optional<Dealer> existingDealer = findDealerById(updatedDealer.getId());
        if (existingDealer.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + updatedDealer.getId());
            return false;
        }

        Dealer dealer = existingDealer.get();
        dealer.setFullname(updatedDealer.getFullname());
        dealer.setPhone(updatedDealer.getPhone());
        dealer.setEmail(updatedDealer.getEmail());
        dealer.setAvailable(updatedDealer.getAvailable());
        dealer.setDeliveriesMade(updatedDealer.getDeliveriesMade());

        System.out.println("✅ Repartidor actualizado: " + dealer.getFullname());
        return true;
    }

    // RF-019: Eliminar repartidor
    public boolean deleteDealer(String idDealer) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return false;
        }

        company.getDealers().remove(dealerOpt.get());
        System.out.println("🗑️ Repartidor eliminado con éxito.");
        return true;
    }

    // --- OTROS MÉTODOS DE DEALER ---

    // RF-020: Cambiar disponibilidad
    public boolean changeDealerAvailability(String idDealer, boolean available) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return false;
        }

        Dealer dealer = dealerOpt.get();
        dealer.setAvailable(available);
        System.out.println("🔄 Estado de disponibilidad actualizado: " + (available ? "🟢 Disponible" : "🔴 No disponible"));
        return true;
    }

    // RF-021: Consultar envíos asignados
    public void showShipmentsByDealer(String idDealer) {
        Optional<Dealer> dealerOpt = findDealerById(idDealer);
        if (dealerOpt.isEmpty()) {
            System.out.println("⚠️ No se encontró repartidor con ID: " + idDealer);
            return;
        }

        Dealer dealer = dealerOpt.get();
        // Nota: Asume que el método getAssignedShipments() existe en la clase Dealer
        List<Shipment> shipments = dealer.getAssignedShipments();

        if (shipments == null || shipments.isEmpty()) {
            System.out.println("📦 El repartidor " + dealer.getFullname() + " no tiene envíos asignados.");
            return;
        }

        System.out.println("📋 Envíos asignados al repartidor " + dealer.getFullname() + ":");
        for (Shipment s : shipments) {
            System.out.println("------------------------------------------");
            // Se asume que el objeto Shipment tiene métodos como getStatus()
            System.out.println("🆔 ID Envío: " + "ID NO DISPONIBLE EN LA CLASE BASE");
            System.out.println("🚚 Estado: " + s.getStatus());
        }
        System.out.println("------------------------------------------");
    }
}