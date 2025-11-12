package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public class PriorityCostStrategy implements ShippingCostStrategy {

    /**
     * Calcula el costo de envío para paquetes con servicio Prioritario (rápido).
     * El costo se basa en una tarifa base alta más factores de peso y dimensión.
     * @param pkg El paquete (PackageModel) con peso y altura en cm.
     * @param originAddress Dirección de origen.
     * @param destinationAddress Dirección de destino.
     * @return Costo total calculado para el envío prioritario.
     */
    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {

        // 1. Tarifa Base ALTA (por el servicio express)
        double baseFee = 15000;

        // 2. Costo por Peso y Dimensión (factores altos para penalizar paquetes pesados o grandes)
        // Sustituimos declaredValue por getHeightCm() para incluir el factor de tamaño.
        double transportCost = pkg.getWeight() * 1.5 + pkg.getHeightCm() * 1.5;

        // 3. (Opcional: Aquí se podría añadir un cálculo de distancia si estuviera disponible)

        return baseFee + transportCost;
    }
}