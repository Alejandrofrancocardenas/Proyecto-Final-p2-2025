package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.service.ShippingService;

public class SignatureCostStrategy implements ShippingCostStrategy {

    /**
     * Calcula el costo base del envío, incluyendo un recargo por el servicio de Firma Requerida.
     * 💥 CRÍTICO: Firma actualizada para recibir ambas direcciones (originAddress y destinationAddress).
     */
    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {

        // 1. Tarifa Base Fija por el servicio de Firma Requerida
        double baseSignatureFee = 4000; // Un recargo fijo razonable por la gestión

        // 2. Lógica de costo estándar (Peso, Valor Declarado)
        // Usando los factores de multiplicación de tu versión original (1.1 y 0.6)
        double transportCost = pkg.getWeight() * 1.1 + pkg.getHeightCm() * 0.6;

        // 3. Costo por Distancia (lógica asumida)
        // 💡 Aquí iría la lógica para calcular la distancia entre las direcciones

        // Retornamos el costo total base de la estrategia
        return baseSignatureFee + transportCost;
    }

    // 💥 MÉTODOS OBSOLETOS ELIMINADOS:
    // Se eliminan las implementaciones de los métodos que usaban la firma antigua:
    // calculateCost(PackageModel pkg, Address address)
    // y calculateCost(Shipment shipment, ShippingService service)
}