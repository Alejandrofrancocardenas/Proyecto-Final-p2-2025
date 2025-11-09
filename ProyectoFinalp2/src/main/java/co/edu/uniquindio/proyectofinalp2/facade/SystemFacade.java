package co.edu.uniquindio.proyectofinalp2.facade;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.dto.*;
import co.edu.uniquindio.proyectofinalp2.exceptions.*;
import co.edu.uniquindio.proyectofinalp2.service.*;
import co.edu.uniquindio.proyectofinalp2.chain.PaymentProcessor;

import java.time.LocalDate;
import java.util.*;

/**
 *  SystemFacade
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
        this.adminService = new AdminService();
        this.dealerService = new DealerService(companyService);
        this.loginService = LoginService.getInstance();
        this.reportService = ReportService.getInstance();
        this.shippingService = ShippingService.getInstance();
    }


    //  SECCIÓN LOGIN / SESIÓN

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
    public boolean verifySessionCredentials(String email, String password, String role) {
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


    // SECCIÓN USERS (CRUD)

    /**
     * Registra un nuevo usuario en la empresa.
     */
    public void registerUser(User user) {
        companyService.registerUser(user);
    }

    /**
     * Devuelve los datos de un usuario a partir de su ID.
     */
    public UserDTO viewUser(String id) {
        return companyService.readUser(id);
    }

    /**
     * Actualiza la información de un usuario.
     */
    public void updateUser(UserDTO dto) {
        companyService.updateUser(dto);
    }

    /**
     * Elimina un usuario por ID.
     */
    public void deleteUser(String id) {
        companyService.deleteUser(id);
    }

    /**
     * Lista todos los usuarios del sistema.
     */
    public List<UserDTO> listUsers() {
        return companyService.listUserDTO();
    }

    /**
     * Busca un usuario según su ID.
     */
    public Optional<User> findUserById(String id) {
        return companyService.findUserByID(id);
    }


    //  SECCIÓN ADMIN (gestión avanzada)

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


    //  MÉTRICAS (Estadísticas para el Administrador)

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


    // SECCIÓN DEALER (repartidores)

    public boolean addDealer(Dealer dealer) {
        return dealerService.addDealer(dealer);
    }

    public boolean updateDealer(Dealer dealer) {
        return dealerService.updateDealer(dealer);
    }

    public boolean deleteDealer(String id) {
        return dealerService.deleteDealer(id);
    }

    public boolean changeDealerAvailability(String dealerId, boolean available) {
        return dealerService.changeDealerAvailability(dealerId, available);
    }

    public void showShipmentsByDealer(String dealerId) {
        dealerService.showShipmentsByDealer(dealerId);
    }


    // SECCIÓN SHIPPING / ENVÍOS

    public double calculateShipmentRate(Shipment shipment) {
        return shippingService.calculateBasePrice(shipment);
    }

    public Shipment applyShipmentDecorators(Shipment shipment, boolean priority, boolean fragile, boolean secure, boolean signature) {
        return shippingService.applyDecorators(shipment, priority, fragile, secure, signature);
    }

    public void createShipment(Shipment shipment) {
        companyService.makeShipment(shipment);
    }

    public List<Shipment> filterShipments(LocalDate date, ShippingStatus status, String zone) {
        return companyService.filterShipments(date, status, zone);
    }

    public String trackShipment(Shipment shipment) {
        return companyService.trackerShipment(shipment);
    }


    //  REPORTES (CSV / PDF)

    public void generateCsvReport(List<Shipment> shipments, String path) {
        reportService.generateCsvReport(shipments, path);
    }

    public void generatePdfReport(List<Shipment> shipments, String path) {
        reportService.generatePdfReport(shipments, path);
    }


    //  UTILIDADES Y CADENA DE PAGO (CHAIN OF RESPONSIBILITY)

    /**
     * Retorna la instancia principal de la empresa.
     */
    public Company getCompany() {
        return companyService.getCompany();
    }

    /**
     * Procesa el pago de un envío utilizando el patrón Chain of Responsibility.
     * Cada handler valida una parte del proceso (usuario, datos, confirmación final).
     */
    public void processPaymentChain(Payment payment) {
        PaymentProcessor processor = new PaymentProcessor();
        processor.processPayment(payment);
    }
}
