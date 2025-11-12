package co.edu.uniquindio.proyectofinalp2.strategy;// Archivo: co.edu.uniquindio.proyectofinalp2.strategy.ShippingCostStrategy.java

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public interface ShippingCostStrategy {

    // 💥 Contrato Estandarizado: Fuerza a usar Origen y Destino
    double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress);

    // (Elimina cualquier otra firma como calculateCost(PackageModel pkg, Address address))
    // (Elimina la firma calculateCost(Shipment shipment, ShippingService service) si no la usas)
}