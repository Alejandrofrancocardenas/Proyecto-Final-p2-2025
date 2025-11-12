package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public class NormalCostStrategy implements ShippingCostStrategy {
    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {

        // 1. Tarifa Base Baja
        double baseFee = 5000;

        // 2. Costo por Peso y Valor (factores bajos)
        double transportCost = pkg.getWeight() * 1.0 + pkg.getHeightCm() * 0.5;

        // 3. Costo por Distancia (debe calcularse usando originAddress y destinationAddress)
        // double distanceCost = calculateDistanceBetween(originAddress, destinationAddress);

        return baseFee + transportCost; // + distanceCost;
    }
}