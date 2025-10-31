package co.edu.uniquindio.proyectofinalp2;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.service.CompanyService;
import co.edu.uniquindio.proyectofinalp2.service.UserService;

import java.time.LocalDateTime;

public class MainTest {
    public static void main(String[] args) {

        System.out.println("=== 🚀 Iniciando pruebas del sistema de envíos ===");

        // Instancia del servicio principal (Singleton)
        CompanyService company = CompanyService.getInstance();

        // 1️⃣ Registrar un usuario
        User user = new User.Builder()
                .id("1001")
                .name("Juan Pérez")
                .email("juan@example.com")
                .phone("3123456789")
                .password("abc123")
                .build();

        System.out.println("\n✅ Usuario registrado: " + user.getFullname());

        // 2️⃣ Crear direcciones y agregarlas al usuario
        Address home = new Address();
        home.setIdAddress("ADDR1");
        home.setAlias("Casa");
        home.setStreet("Calle 10 # 4-23");
        home.setCity("Armenia");
        home.setCoordinates("4.5356, -75.6753");

        Address office = new Address();
        office.setIdAddress("ADDR2");
        office.setAlias("Oficina");
        office.setStreet("Cra 14 # 5-30");
        office.setCity("Calarcá");
        office.setCoordinates("4.5310, -75.6402");

        UserService.addAddressToUser(user, home);
        UserService.addAddressToUser(user, office);

        System.out.println("📍 Direcciones agregadas: ");
        user.getAddresses().forEach(a -> System.out.println(" - " + a));

        // 3️⃣ Crear una solicitud de envío temporal
//        Shipment shipment = UserService.createShipment("SHIP1", user.getId(), "Zona Norte", "Octubre 2025");
//        shipment.setOrigin(home);
//        shipment.setDestination(office);
//        shipment.addService("Seguro");
//        shipment.setWeight(120);
//        shipment.setVolume(400);
//        shipment.setPrice( UserService.getPrice("Armenia", "Calarcá", shipment.getWeight(), shipment.getVolume(), "2") );
//
//        System.out.println("\n📦 Envío creado:");
//        System.out.println(shipment.track());

        // 4️⃣ Confirmar el envío
        UserService.makeShipment("SHIP1");
        System.out.println("\n🚚 Estado actualizado:");
       // System.out.println(shipment.track());

        // 5️⃣ Simular pago
        //Payment payment = new Payment("PAY1", shipment.getPrice(), LocalDateTime.now(), true);
        //user.getPayments().add(payment);
        //shipment.setPayment(payment);

        System.out.println("\n💳 Pago realizado con éxito:");
        //System.out.println(payment);

        // 6️⃣ Rastrear el envío
        //String trackingInfo = CompanyService.trackerShipment(shipment);
        //System.out.println("\n📍 Rastreo actual del envío:\n" + trackingInfo);

        System.out.println("\n=== ✅ Fin de pruebas ===");
    }
}