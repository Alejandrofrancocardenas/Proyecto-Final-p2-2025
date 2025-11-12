package co.edu.uniquindio.proyectofinalp2.facade;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.dto.*;
import co.edu.uniquindio.proyectofinalp2.service.*;
import co.edu.uniquindio.proyectofinalp2.chain.PaymentProcessor;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectEmailException;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectPasswordException;

import java.time.LocalDate;
import java.util.*;

/**
 * SystemFacade
 *
 * Este patrón *Facade* centraliza el acceso a todos los servicios del sistema,
 * proporcionando un único punto de entrada para los controladores, pruebas o interfaces gráficas.
 *
 * Su propósito es simplificar la comunicación entre las capas del sistema.
 */
public class SystemFacade {


    //  INSTANCIAS DE SERVICIO

    private final CompanyService companyService;
    private final AdminService adminService;
    private final DealerService dealerService;
    private final LoginService loginService;
    private final ReportService reportService;
    private final ShippingService shippingService;


    // SINGLETON: solo una instancia del Facade

    private static SystemFacade instance;

    public static SystemFacade getInstance() {
        if (instance == null) {
            instance = new SystemFacade();
        }
        return instance;
    }

    private SystemFacade() {
        this.companyService = CompanyService.getInstance();
        this.adminService = AdminService.getInstance();
        this.dealerService = DealerService.getInstance();
        this.loginService = LoginService.getInstance();
        this.reportService = ReportService.getInstance();
        this.shippingService = ShippingService.getInstance();
    }


    //==============================================================
    // SECCIÓN 1: LOGIN, REGISTRO Y SESIÓN (Actualizada y Completa)
    //==============================================================

    /**
     * Intenta iniciar sesión y devuelve el objeto (User, Admin o Dealer) si es exitoso.
     * @param email Correo del usuario
     * @param password Contraseña
     * @param role Rol a verificar ("Usuario", "Administrador", "Repartidor")
     * @return El objeto logueado (User, Admin o Dealer).
     * @throws Exception Si las credenciales son incorrectas o el rol no existe.
     */
    public Object login(String email, String password, String role) throws IncorrectEmailException, IncorrectPasswordException {
        switch (role) {
            case "Usuario":
                return companyService.login(email, password);
            case "Administrador":
                return companyService.loginAdmin(email, password);
            case "Repartidor":
                return companyService.loginDealer(email, password);
            default:
                throw new IllegalArgumentException("Rol de inicio de sesión no reconocido: " + role);
        }
    }

    /**
     * Registra un nuevo usuario en la empresa.
     */
    public void registerUser(User user) {
        companyService.registerUser(user);
    }

    /**
     * Registra un nuevo administrador en el sistema.
     */
    public void registerAdmin(Admin admin) {
        companyService.registerAdmin(admin);
    }

    /**
     * Registra un nuevo repartidor en el sistema.
     */
    public void registerDealer(Dealer dealer) {
        companyService.registerDealer(dealer);
    }

    // --- Métodos de Sesión Específicos (Sin llamar a CompanyService, asumiendo LoginService/Sesion manejan esto) ---

    /**
     * Registra un nuevo usuario en el sistema de inicio de sesión.
     */
    public void registerUserLogin(String email, String password) {
        loginService.registrarUsuario(email, password);
    }

    /**
     * Verifica si las credenciales de inicio de sesión son válidas.
     */
    public boolean verifyLoginCredentials(String email, String password) {
        return loginService.verificarCredenciales(email, password);
    }

    /**
     * Verifica las credenciales con un rol específico.
     */
    public User verifySessionCredentials(String email, String password, String role) {
        return Sesion.verificarCredenciales(email, password, role);
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logout() {
        Sesion.cerrarSesion();
    }

    /**
     * Obtiene el usuario actualmente logueado en la sesión.
     */
    public User getCurrentUser() {
        return Sesion.getUsuarioActual();
    }


    //==============================================================
    // SECCIÓN 2: USERS (CRUD)
    //==============================================================

    // 🔴 NOTA: Los métodos de CRUD de User (viewUser, updateUser, deleteUser, listUsers, findUserById)
    //          NO están definidos en el CompanyService proporcionado.

    // Se comentan o ajustan las llamadas a CompanyService:

    public UserDTO viewUser(String id) {
        // companyService.readUser(id); // 🔴 MÉTODO FALTANTE
        return null; // Retorno temporal
    }

    public void updateUser(UserDTO dto) {
        // companyService.updateUser(dto); // 🔴 MÉTODO FALTANTE
    }

    public void deleteUser(String id) {
        // companyService.deleteUser(id); // 🔴 MÉTODO FALTANTE
    }

    public List<UserDTO> listUsers() {
        // return companyService.listUserDTO(); // 🔴 MÉTODO FALTANTE
        return Collections.emptyList(); // Retorno temporal
    }

    public Optional<User> findUserById(String id) {
        // return companyService.findUserByID(id); // 🔴 MÉTODO FALTANTE
        return Optional.empty(); // Retorno temporal
    }


    //==============================================================
    // SECCIÓN 3: ADMIN (Gestión de CRUD avanzada y Asignaciones)
    //==============================================================

    public boolean addUserAsAdmin(User newUser) {
        return adminService.addUserAdmin(newUser);
    }

    public boolean deleteUserAsAdmin(User newUser) {
        return adminService.deleteUserAdmin(newUser);
    }

    public boolean updateUserAsAdmin(User newUser) {
        return adminService.updateUserAdmin(newUser);
    }

    public void showAllUsersAsAdmin() {
        adminService.showAllUsersAdmin();
    }

    public boolean addDealerAsAdmin(Dealer newDealer) {
        return adminService.addDealerAdmin(newDealer);
    }

    public boolean deleteDealerAsAdmin(Dealer newDealer) {
        return adminService.deleteDealerAdmin(newDealer);
    }

    public boolean updateDealerAsAdmin(Dealer newDealer) {
        return adminService.updateDealerAdmin(newDealer);
    }

    public void showAllDealersAsAdmin() {
        adminService.showAllDealersAdmin();
    }

    public boolean assignShipmentToDealer(String shipmentId, String dealerId) {
        return adminService.assignOrReassignShipment(shipmentId, dealerId);
    }

    public boolean registerShipmentIncidence(String shipmentId, Incidence incidence) {
        return adminService.registerShipmentIncidence(shipmentId, incidence);
    }

    public boolean updateShipmentStatus(String shipmentId, ShippingStatus status) {
        return adminService.updateShipmentStatus(shipmentId, status);
    }


    //==============================================================
    // SECCIÓN 4: MÉTRICAS (Estadísticas para el Administrador)
    //==============================================================

    public Map<String, Double> getAverageDeliveryTimeByZone() {
        return adminService.getAverageDeliveryTimeByZone();
    }

    public Map<String, Long> getMostUsedAdditionalServices() {
        return adminService.getMostUsedAdditionalServices();
    }

    public Map<String, Double> getIncomeByPeriod() {
        return adminService.getIncomeByPeriod();
    }

    public Map<String, Long> getIncidencesByZone() {
        return adminService.getIncidencesByZone();
    }


    //==============================================================
    // SECCIÓN 5: DEALER (Repartidores - Operaciones de datos)
    //==============================================================

    /**
     * Actualiza la información de un repartidor (usando CompanyService).
     */
    public void updateDealerData(Dealer dealer) {
        // companyService.updateDealer(dealer); // 🔴 MÉTODO FALTANTE
    }

    /**
     * Elimina un repartidor por ID (usando CompanyService).
     */
    public void deleteDealerData(String id) {
        // companyService.deleteDealer(id); // 🔴 MÉTODO FALTANTE
    }

    public boolean changeDealerAvailability(String dealerId, boolean available) {
        return dealerService.changeDealerAvailability(dealerId, available);
    }

    public void showShipmentsByDealer(String dealerId) {
        dealerService.showShipmentsByDealer(dealerId);
    }


    //==============================================================
    // SECCIÓN 6: SHIPPING / ENVÍOS
    //==============================================================

    /**
     * Aplica los decoradores al envío para añadir servicios y calcular el costo total
     * basado en la cadena de Decorator. El costo base se asume ya asignado al Rate.
     */
    public Shipment applyShipmentDecorators(Shipment shipment, boolean priority, boolean fragile, boolean secure, boolean signature) {
        return shippingService.applyDecorators(shipment, priority, fragile, secure, signature);
    }

    public void createShipment(Shipment shipment) {
        // companyService.makeShipment(shipment); // 🔴 MÉTODO FALTANTE
    }

    public List<Shipment> filterShipments(LocalDate date, ShippingStatus status, String zone) {
        // return companyService.filterShipments(date, status, zone); // 🔴 MÉTODO FALTANTE
        return Collections.emptyList(); // Retorno temporal
    }

    public String trackShipment(Shipment shipment) {
        // return companyService.trackerShipment(shipment); // 🔴 MÉTODO FALTANTE
        return "Método no implementado en CompanyService"; // Retorno temporal
    }


    //==============================================================
    // SECCIÓN 7: REPORTES (CSV / PDF)
    //==============================================================

    public void generateCsvReport(List<Shipment> shipments, String path) {
        reportService.generateCsvReport(shipments, path);
    }

    public void generatePdfReport(List<Shipment> shipments, String path) {
        reportService.generatePdfReport(shipments, path);
    }


    //==============================================================
    // SECCIÓN 8: UTILIDADES Y CADENA DE PAGO (CHAIN OF RESPONSIBILITY)
    //==============================================================

    /**
     * Retorna la instancia principal de la empresa.
     */
    public Company getCompany() {
        return companyService.company; // Acceso directo a la propiedad 'company' si es visible o usando Company.getInstance()
    }

    /**
     * Procesa el pago de un envío utilizando el patrón Chain of Responsibility.
     */
    public void processPaymentChain(Payment payment) {
        PaymentProcessor processor = new PaymentProcessor();
        processor.processPayment(payment);
    }

    /**
     * Crea un objeto Address a partir de sus componentes, incluyendo el alias (campo 'name').
     * Esto simplifica la creación de objetos de modelo en la capa de controlador/UI.
     * @param city La ciudad de la dirección.
     * @param street La calle y número de la dirección.
     * @param postalCode El código postal.
     * @param alias El nombre corto o apodo de la dirección (que se mapea al campo 'name').
     * @return El objeto Address inicializado.
     */
    public Address createAddress(String city, String street, String postalCode, String alias) {
        Address newAddress = new Address();
        newAddress.setCity(city);
        newAddress.setStreet(street);
        newAddress.setPostalCode(postalCode); // <-- Corrección: usa setPostalCode
        newAddress.setName(alias); // <-- Corrección: usa setName para el alias
        return newAddress;
    }
}