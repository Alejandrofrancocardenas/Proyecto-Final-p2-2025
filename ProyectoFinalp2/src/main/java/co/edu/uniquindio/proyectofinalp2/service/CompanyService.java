package co.edu.uniquindio.proyectofinalp2.service;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.decorators.FragileShipment; // 🟢 Importar decoradores
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping; // 🟢 Importar decoradores
import co.edu.uniquindio.proyectofinalp2.dto.AdminDTO;
import co.edu.uniquindio.proyectofinalp2.dto.DealerDTO;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectEmailException;
import co.edu.uniquindio.proyectofinalp2.exceptions.IncorrectPasswordException;
import co.edu.uniquindio.proyectofinalp2.exceptions.NotFoundException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyService {
    private static CompanyService instance;
    public final Company company;

    // Archivos de persistencia (Mantener como referencia, pero no se usarán)
    private static final String USERS_FILE = "data/company_users.dat";
    private static final String ADMINS_FILE = "data/company_admins.dat";
    private static final String DEALERS_FILE = "data/company_dealers.dat";
    private static final String SHIPMENTS_FILE = "data/company_shipments.dat";

    private CompanyService() {
        this.company = Company.getInstance();
    }

    public static CompanyService getInstance() {
        if (instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }

    // --- MÉTODOS DE BÚSQUEDA ---

    public Optional<User> findUserByEmail(String email) {
        return company.getUsers().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Admin> findAdminByEmail(String email) {
        return company.getAdmins().stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Dealer> findDealerByEmail(String email) {
        return company.getDealers().stream()
                .filter(dealer -> dealer.getEmail().equals(email))
                .findFirst();
    }


    // --- REGISTRO DE ROLES (CREATE) ---

    public void registerUser(User user) {
        Optional<User> userAux = findUserByEmail(user.getEmail());
        if (userAux.isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta con el email: "+ user.getEmail());
        }
        company.getUsers().add(user);
        // persistUserData(); // ❌ Deshabilitado
    }

    // Registro de Administrador
    public void registerAdmin(Admin admin) {
        Optional<Admin> adminAux = findAdminByEmail(admin.getEmail());
        if (adminAux.isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta Admin con el email: " + admin.getEmail());
        }
        company.getAdmins().add(admin);
        // persistAdminsData(); // ❌ Deshabilitado
    }

    // Registro de Repartidor
    public void registerDealer(Dealer dealer) {
        Optional<Dealer> dealerAux = findDealerByEmail(dealer.getEmail());
        if (dealerAux.isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuenta Repartidor con el email: " + dealer.getEmail());
        }
        company.getDealers().add(dealer);
        // persistDealersData(); // ❌ Deshabilitado
    }


    // --- MÉTODOS DE INICIO DE SESIÓN (LOGIN) ---
    // (Lógica de login sin cambios, ya que opera sobre las listas en memoria)

    public User login(String email, String password) {
        Optional<User> user = findUserByEmail(email);

        if (user.isEmpty()) {
            throw new IncorrectEmailException("No existe ninguna cuenta de usuario con el email: " + email);
        }

        User userAux = user.get();
        if (userAux.getPassword() == null || !userAux.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Contraseña incorrecta, por favor inténtalo de nuevo.");
        }
        return userAux;
    }

    public Admin loginAdmin(String email, String password) {
        Optional<Admin> admin = findAdminByEmail(email);

        if (admin.isEmpty()) {
            throw new IncorrectEmailException("No existe ninguna cuenta de administrador con el email: " + email);
        }

        Admin adminAux = admin.get();
        if (adminAux.getPassword() == null || !adminAux.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Contraseña incorrecta, por favor inténtalo de nuevo.");
        }
        return adminAux;
    }

    public Dealer loginDealer(String email, String password) {
        Optional<Dealer> dealer = findDealerByEmail(email);

        if (dealer.isEmpty()) {
            throw new IncorrectEmailException("No existe ninguna cuenta de repartidor con el email: " + email);
        }

        Dealer dealerAux = dealer.get();
        if (dealerAux.getPassword() == null || !dealerAux.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Contraseña incorrecta, por favor inténtalo de nuevo.");
        }
        return dealerAux;
    }

    // --- NUEVOS MÉTODOS DE LÓGICA DE NEGOCIO PARA COTIZACIÓN ---

    /**
     * Calcula el costo final del envío aplicando todos los decoradores.
     * @param shipment El objeto Shipment (ya decorado o no)
     * @return El costo total del envío.
     */

    /**
     * Retorna una lista de las opciones de decoración de envíos disponibles
     * para que la vista pueda mostrarlas.
     * @return Lista de nombres de decoradores.
     */
    public List<String> getAvailableDecorators() {
        // Estos nombres deben coincidir con la lógica del controlador para aplicar los decoradores.
        List<String> decorators = new ArrayList<>();
        decorators.add("Fragile");
        decorators.add("Urgent");
        // 🟢 Añadir los nuevos decoradores
        decorators.add("SecureShipping");
        decorators.add("SignatureRequiredShipment");
        return decorators;
    }


    // Gestión de Envíos
    public void makeShipment(Shipment shipment) {
        this.company.getShipments().add(shipment);
        // persistShipmentData(); // ❌ Deshabilitado
        System.out.println("📦 Envío registrado con éxito: " + shipment.getShipmentId());
    }

    public void addShipmentToCompany(Shipment shipment) {
        this.company.getShipments().add(shipment);
        // persistShipmentData(); // ❌ Deshabilitado
        System.out.println("📦 Envío " + shipment.getShipmentId() + " registrado centralmente y persistido.");
    }


}