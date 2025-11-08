package co.edu.uniquindio.proyectofinalp2;

import co.edu.uniquindio.proyectofinalp2.Model.*;
import co.edu.uniquindio.proyectofinalp2.decorators.PriorityShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SecureShipping;
import co.edu.uniquindio.proyectofinalp2.decorators.SignatureRequiredShipment;
import co.edu.uniquindio.proyectofinalp2.dto.UserDTO;
import co.edu.uniquindio.proyectofinalp2.factory.ShipmentFactory;
import co.edu.uniquindio.proyectofinalp2.observer.DealerNotification;
import co.edu.uniquindio.proyectofinalp2.observer.NotificationHandler;
import co.edu.uniquindio.proyectofinalp2.observer.UserNotification;
import co.edu.uniquindio.proyectofinalp2.proxy.ShipmentProxy;
import co.edu.uniquindio.proyectofinalp2.service.*;
import co.edu.uniquindio.proyectofinalp2.strategy.FragileCostStrategy;
import co.edu.uniquindio.proyectofinalp2.strategy.NormalCostStrategy;
import co.edu.uniquindio.proyectofinalp2.strategy.PriorityCostStrategy;

import java.time.LocalDateTime;

public class MainTest {

    public static void main(String[] args) {
        CompanyService companyService = CompanyService.getInstance();
        ShippingService shippingService = ShippingService.getInstance();
        ReportService reportService = ReportService.getInstance();
        LoginService loginService = LoginService.getInstance();

        System.out.println("=== 🔹 INICIO DE PRUEBAS DEL SISTEMA DE ENVÍOS 🔹 ===\n");

        // 1️⃣ Crear usuarios (solo clientes)
        User cliente1 = new User.Builder()
                .id("U001")
                .name("Juan Pérez")
                .role("cliente")
                .email("c1@gmail.com")
                .password("123")
                .build();

        User cliente2 = new User.Builder()
                .id("U002")
                .name("María Gómez")
                .role("cliente")
                .email("c2@gmail.com")
                .password("1234")
                .build();

        // registrarse como cliente
        companyService.registerUser(cliente1);
        UserService uService = new UserService(cliente1); // una vez registrado se "desbloquean" las funciones de user
        companyService.registerUser(cliente2);
        System.out.println(companyService.getCompany().getUsers());

        //iniciar sesion
        //supongo que este es el que coje el controller
        UserDTO udto = companyService.login("c1@gmail.com", "123");
        if (udto != null) {
            System.out.println("inicio de sesion exitoso");
        }

        // 2️⃣ Crear direcciones
        Address direccion1 = new Address("A1", "Quimbaya", "Medellin", "LARUTA", "Cra 45 # 12-34", "Quimbaya", "001,011");
        Address direccion2 = new Address("A2", "Bogota", "Cali", "LAVIA", "Cra 45 # 10-34", "Bogota", "021,051");

        // agrega las direcciones al cliente
        uService.addAddressToUser(uService.getUser(), direccion1);
        uService.addAddressToUser(uService.getUser(), direccion2);
        System.out.println(uService.getUser().getAddresses());

        // 3️⃣ Crear paquetes
        PackageModel paquete1 = new PackageModel("P001", 2.5, 30.0);
        PackageModel paquete2 = new PackageModel("P002", 10.0, 80.0);

        // 4️⃣ Crear envíos usando la FACTORY
        // aca los datos los proporciona el user desde la GUI y supongo que el controller llama al factory y hace algo asi como lo de aca abajo
        // luego el userService hace el proceso final
        System.out.println("🧩 Creando envíos con diferentes tipos...");
        Shipment envioNormal = ShipmentFactory.createShipment("normal", "S001", cliente1, "Zona Norte", direccion2, paquete1);
        Shipment envioPrioritario = ShipmentFactory.createShipment("priority", "S002", cliente2, "Zona Sur", direccion2, paquete2);
        Shipment envioFragil = ShipmentFactory.createShipment("fragile", "S003", cliente1, "Zona Norte", direccion2, paquete1);

        // aca el userService hacel el procesos final
        uService.createShipment(envioNormal);
        uService.createShipment(envioPrioritario);
        uService.createShipment(envioFragil);
        System.out.println(uService.getUser().getShipments());

        // 5️⃣ Asignar tarifa con STRATEGY
        System.out.println("\n💰 Calculando tarifas...");
        Rate rateNormal = new Rate("R001", new NormalCostStrategy());
        double costoNormal = rateNormal.calculateShipmentRate(paquete1, direccion2);
        rateNormal.setBase(costoNormal);
        envioNormal.setRate(rateNormal);
        System.out.println("Costo envío normal: " + costoNormal);

        Rate ratePriority = new Rate("R002", new PriorityCostStrategy());
        double costoPriority = ratePriority.calculateShipmentRate(paquete2, direccion2);
        ratePriority.setBase(costoPriority);
        envioPrioritario.setRate(ratePriority);
        System.out.println("Costo envío prioritario: " + costoPriority);

        Rate rateFragile = new Rate("R003", new FragileCostStrategy());
        double costoFragile = rateFragile.calculateShipmentRate(paquete1, direccion2);
        rateFragile.setBase(costoFragile);
        envioFragil.setRate(rateFragile);
        System.out.println("Costo envío frágil: " + costoFragile);

        // 6️⃣ Agregar observadores (USER y DEALER)
        System.out.println("\n🔔 Añadiendo observadores...");
        NotificationHandler consolaHandler = msg -> System.out.println("📢 [Notificación]: " + msg);
        envioNormal.addObserver(new UserNotification(consolaHandler));
        envioNormal.addObserver(new DealerNotification(consolaHandler));

        // 7️⃣ Simular cambio de estado y notificaciones
        System.out.println("\n🚀 Cambiando estado del envío...");
        envioNormal.setStatus(ShippingStatus.ONROUTE);
        envioNormal.setStatus(ShippingStatus.DELIVERED);

        // 8️⃣ Decorar un envío con servicios adicionales
        System.out.println("\n🎁 Aplicando decoradores...");
        Shipment envioDecorado = new SecureShipping(new SignatureRequiredShipment(new PriorityShipping(envioNormal)));
        envioDecorado.addService("Seguro + Firma + Prioridad");
        System.out.println("Decorador aplicado a envío: " + envioDecorado.getShipmentId());
        System.out.println("Servicios extra: " + envioDecorado.getAdditionalServices());

        // 9️⃣ PROXY: validar permisos de cancelación
        System.out.println("\n🛑 Probando Proxy de cancelación...");
        ShipmentProxy proxyEnvio = new ShipmentProxy(envioNormal, cliente1);
        proxyEnvio.cancel();
        System.out.println("El envío " + envioNormal.getShipmentId() + " fue cancelado con éxito.");

        // 🔟 Simular pago (CHAIN OF RESPONSIBILITY)
        System.out.println("\n💳 Simulando proceso de pago (ejemplo para Chain of Responsibility)...");
        Payment pago = new Payment("PAY001", 20000.0, LocalDateTime.now(), true);
        envioNormal.setPayment(pago);
        System.out.println("Pago asignado al envío: " + envioNormal.getPayment());

        // 🔚 Finalizar
        System.out.println("\n✅ Fin de pruebas del sistema de envíos.");
    }
}