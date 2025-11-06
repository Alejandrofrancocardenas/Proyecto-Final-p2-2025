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
                .password("1")
                .build();


        //parece que este DTO lo unico que sirve es para que se lo lleve el controlador
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(user.getId());
        userDTO.setFullname(user.getFullname());
        userDTO.setEmail("nuevoEmail@gmail.com");
        userDTO.setPhone("30000000");
        userDTO.setAddresses(user.getAddresses());

        //registrar usuario
        companyService.registerUser(user);
        UserService userService = new UserService(user);

        System.out.println("✅ Usuario registrado: " + user.getFullname());
        System.out.println("los usuarios" +companyService.getCompany().getUsers());

        // iniciar sesion
        UserDTO userAux = companyService.login("juan@example.com", "1");

        if (userAux != null) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }

        // el usuario quiere actualizar su información
        companyService.updateUser(userDTO);
        System.out.println("los usuarios" +companyService.getCompany().getUsers());

        // el usuario quiere crear un direccion
        Address address = new Address("002", "quimbaya", "medellin","la ruta","carrera 75", "Quimbaya", "204, 105");
        userService.addAddressToUser(userService.getUser(), address);
        System.out.println("direcciones del usuario" + userService.getUser().getAddresses());

        // --- 3️⃣ Crear un paquete ---
        PackageModel pack = new PackageModel("xxxx", 0, 0);
        pack.setWeight(200);
        pack.setVolume(700);

        // --- 4️⃣ Crear un envío básico ---
        Shipment shipment = new NormalShipment.Builder()
                .shipmentId("01")
                .zone("Barrio")
                .user(user)
                .address(address)
                .packageModel(pack)
                .build();

        // --- 4️⃣ Cotizar precio de un envío básico ---
        System.out.println("el envio de este packete te cuesta: $" + userService.getPrice(shipment));


        // el usuario quiere crear una solicitud de envio pero no lo confirma aun
        userService.createShipment(shipment);
        System.out.println(user.getShipments());

        // el usuario confirma que quiere hacer el envio
        userService.confirmShipment("01");
        System.out.println(user.getShipments());
        System.out.println("aca va bien");
        // el usuario paga el envio
        userService.payShipment(userService.getUser(), "01", 4000);
        System.out.println(user.getShipments());


        // --- 5️⃣ Aplicar decoradores (servicios adicionales) ---
        shipment = shippingService.applyDecorators(
                shipment,
                true,   // prioridad
                true,   // frágil
                false,  // sin seguro
                true    // con firma requerida
        );

        System.out.println("\n🚀 Envío con decoradores aplicado:");
        System.out.println("Precio total: " + shipment.getRate().getBase());
        System.out.println("Tracking:\n" + shipment.track());

        // --- 6️⃣ Registrar el envío en la compañía ---
        companyService.getCompany().getShipments().add(shipment);

        System.out.println("\n📦 Envío registrado exitosamente en la compañía.");

        System.out.println("historial");
        System.out.println(userService.shipmentsHistory(userService.getUser(), LocalDateTime.now(), ShippingStatus.ONROUTE));
    }
}