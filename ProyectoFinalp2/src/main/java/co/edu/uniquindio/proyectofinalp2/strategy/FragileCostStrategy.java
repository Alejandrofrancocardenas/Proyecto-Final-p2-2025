package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.service.ShippingService;

public class FragileCostStrategy implements ShippingCostStrategy {

    /**
     * Calcula el costo base del envío, incluyendo un recargo por el manejo de objetos frágiles.
     * 💥 CRÍTICO: Firma actualizada para recibir ambas direcciones (originAddress y destinationAddress).
     */
    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {

        // 1. Tarifa Base Fija
        // Usamos tu base original (3000) más un recargo fijo por manejo frágil (500)
        double baseFragileFee = 3500;

        // 2. Lógica de costo estándar (Peso, Valor Declarado)
        // Usando los factores de multiplicación de tu versión original (1.3 y 0.7)
        double transportCost = pkg.getWeight() * 1.3 + pkg.getHeightCm() * 0.7;

        // 3. Costo por Distancia (lógica asumida)
        // 💡 Aquí iría la lógica para calcular la distancia entre las direcciones

        // Retornamos el costo total base de la estrategia
        return baseFragileFee + transportCost;
    }

    // 💥 MÉTODOS OBSOLETOS ELIMINADOS:
    // Se eliminan las implementaciones de los métodos que usaban la firma antigua
    // calculateCost(PackageModel pkg, Address Address)
    // y calculateCost(Shipment shipment, ShippingService service)
}