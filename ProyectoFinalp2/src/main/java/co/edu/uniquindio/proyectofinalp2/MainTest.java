package co.edu.uniquindio.proyectofinalp2;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.decorators.*;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.factory.ShipmentFactory;
import co.edu.uniquindio.proyectofinalp2.facade.SystemFacade;
import co.edu.uniquindio.proyectofinalp2.observer.*;
import co.edu.uniquindio.proyectofinalp2.proxy.ShipmentProxy;
import co.edu.uniquindio.proyectofinalp2.strategy.*;
import co.edu.uniquindio.proyectofinalp2.chain.*;

import java.time.LocalDateTime;

public class MainTest {

    public static void main(String[] args) {


        // 🔹 1️⃣ Inicializar fachada (Facade)

        SystemFacade facade = SystemFacade.getInstance();
        System.out.println("=== 🚚 INICIANDO PRUEBAS DEL SISTEMA DE ENVÍOS ===\n");


        // 🔹 2️⃣ Crear usuarios (clientes)

        User client1 = new User.Builder()
                .id("U001")
                .name("Juan Pérez")
                .role("client")
                .email("c1@gmail.com")
                .password("123")
                .build();

        User client2 = new User.Builder()
                .id("U002")
                .name("María Gómez")
                .role("client")
                .email("c2@gmail.com")
                .password("1234")
                .build();

        // Registrar usuarios en la empresa
        facade.registerUser(client1);
        facade.registerUser(client2);

        System.out.println("👥 Usuarios registrados:");
        System.out.println(facade.getCompany().getUsers());


        // 🔹 3️⃣ Simular inicio de sesión

        boolean loginSuccess = facade.verifyLoginCredentials("c1@gmail.com", "123");
        if (loginSuccess) {
            System.out.println("✅ Inicio de sesión exitoso para Juan Pérez");
        } else {
            System.out.println("❌ Falló el inicio de sesión");
        }


        // 🔹 4️⃣ Crear direcciones

        Address address1 = new Address("A1", "Quimbaya", "Medellin", "LARUTA",
                "Cra 45 # 12-34", "Medellin", "001,011");
        Address address2 = new Address("A2", "Bogota", "Cali", "LAVIA",
                "Cra 45 # 10-34", "Cali", "021,051");

        client1.addAddress(address1);
        client1.addAddress(address2);

        System.out.println("🏠 Direcciones del usuario:");
        System.out.println(client1.getAddresses());


        // 🔹 5️⃣ Crear paquetes

        PackageModel package1 = new PackageModel("P001", 2.5, 30.0);
        PackageModel package2 = new PackageModel("P002", 10.0, 80.0);


        // 🔹 6️⃣ Crear envíos usando Factory

        System.out.println("\n📦 Creando envíos con la Fábrica (Factory)...");
        Shipment normalShipment = ShipmentFactory.createShipment("normal", "S001", client1, "Zona Norte", address2, package1);
        Shipment priorityShipment = ShipmentFactory.createShipment("priority", "S002", client2, "Zona Sur", address2, package2);
        Shipment fragileShipment = ShipmentFactory.createShipment("fragile", "S003", client1, "Zona Norte", address2, package1);

        facade.createShipment(normalShipment);
        facade.createShipment(priorityShipment);
        facade.createShipment(fragileShipment);

        System.out.println("📋 Envíos creados: " + client1.getShipments());


        // 🔹 7️⃣ STRATEGY: cálculo de tarifas

        System.out.println("\n💰 Calculando tarifas...");

        Rate rateNormal = new Rate("R001", new NormalCostStrategy());
        rateNormal.setBase(rateNormal.calculateShipmentRate(package1, address2));
        normalShipment.setRate(rateNormal);
        System.out.println("Costo envío normal: $" + rateNormal.getBase());

        Rate ratePriority = new Rate("R002", new PriorityCostStrategy());
        ratePriority.setBase(ratePriority.calculateShipmentRate(package2, address2));
        priorityShipment.setRate(ratePriority);
        System.out.println("Costo envío prioritario: $" + ratePriority.getBase());

        Rate rateFragile = new Rate("R003", new FragileCostStrategy());
        rateFragile.setBase(rateFragile.calculateShipmentRate(package1, address2));
        fragileShipment.setRate(rateFragile);
        System.out.println("Costo envío frágil: $" + rateFragile.getBase());


        // 🔹 8️⃣ OBSERVER: notificación de cambios

        System.out.println("\n🔔 Configurando observadores...");
        NotificationHandler handler = msg -> System.out.println("📢 [Notificación]: " + msg);

        normalShipment.addObserver(new UserNotification(handler));
        normalShipment.addObserver(new DealerNotification(handler));

        System.out.println("\n🚀 Cambiando estado del envío...");
        normalShipment.setStatus(ShippingStatus.ONROUTE);
        normalShipment.setStatus(ShippingStatus.DELIVERED);


        // 🔹 9️⃣ DECORATOR: servicios adicionales

        System.out.println("\n🎁 Aplicando decoradores...");
        Shipment decoratedShipment = new SecureShipping(
                new SignatureRequiredShipment(
                        new PriorityShipping(normalShipment)
                )
        );
        decoratedShipment.addService("Seguro + Firma + Prioritario");
        System.out.println("Envío decorado: " + decoratedShipment.getShipmentId());
        System.out.println("Servicios adicionales: " + decoratedShipment.getAdditionalServices());


        // 🔹 🔟 PROXY: cancelación segura

        System.out.println("\n🛑 Probando Proxy (cancelación segura)...");
        ShipmentProxy proxyShipment = new ShipmentProxy(normalShipment, client1);
        proxyShipment.cancel();
        System.out.println("El envío " + normalShipment.getShipmentId() + " fue cancelado correctamente.");


        // 🔹 11️⃣ CHAIN OF RESPONSIBILITY: proceso de pago

        System.out.println("\n💳 Simulando proceso de pago con Chain of Responsibility...");

        // Crear un pago válido con usuario asociado
        Payment payment = new Payment("PAY001", 20000.0, LocalDateTime.now(), "Tarjeta de crédito", true);
        payment.setUser(client1); // ✅ Asociar usuario para validación

        // Procesar el pago usando la fachada
        facade.processPaymentChain(payment);

        // Asociar el pago al envío
        normalShipment.setPayment(payment);
        System.out.println("Pago procesado y asignado al envío: " + normalShipment.getPayment());

        System.out.println("\n✅ Fin de las pruebas del sistema de envíos.");
    }
}
