package co.edu.uniquindio.proyectofinalp2;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.service.CompanyService;
import co.edu.uniquindio.proyectofinalp2.service.ShippingService;
import co.edu.uniquindio.proyectofinalp2.service.UserService;

import java.time.LocalDateTime;

public class MainTest {

    public static void main(String[] args) {

        // --- 1️⃣ Inicializar servicios globales ---
        CompanyService companyService = CompanyService.getInstance();
        ShippingService shippingService = ShippingService.getInstance();

        // --- 2️⃣ Crear un usuario ---
        User user = new User.Builder()
                .id("U001")
                .name("Juan Pérez")
                .email("juan@example.com")
                .phone("3001234567")
                .build();


        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddresses(user.getAddresses());

        companyService.registerUser(userDTO);

        UserService userService = new UserService(user);

        System.out.println("✅ Usuario registrado: " + user.getFullname());

        // --- 3️⃣ Crear un paquete ---
        PackageModel pack = new PackageModel("xxxx", 0, 0);
        pack.setWeight(200);
        pack.setVolume(700);

        // --- 4️⃣ Crear un envío básico ---
        Shipment shipment = new NormalShipment("SHP001", user, "Zona Norte", "Octubre 2025");
        shipment.setPackageModel(pack);

        // Calcular precio base
        double basePrice = shippingService.calculateBasePrice(shipment);
        shipment.setPrice(basePrice);

        System.out.println("\n💰 Precio base del envío: " + basePrice);

        // --- 5️⃣ Aplicar decoradores (servicios adicionales) ---
        shipment = shippingService.applyDecorators(
                shipment,
                true,   // prioridad
                true,   // frágil
                false,  // sin seguro
                true    // con firma requerida
        );

        System.out.println("\n🚀 Envío con decoradores aplicado:");
        System.out.println("Precio total: " + shipment.getPrice());
        System.out.println("Tracking:\n" + shipment.track());

        // --- 6️⃣ Registrar el envío en la compañía ---
        companyService.getCompany().getShipments().add(shipment);

        System.out.println("\n📦 Envío registrado exitosamente en la compañía.");
    }
}