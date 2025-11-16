package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;
import co.edu.uniquindio.proyectofinalp2.strategy.ShippingCostStrategy;

public class SecureCostStrategy implements ShippingCostStrategy {
    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {


        double baseFee = 10000;

        double transportCost = pkg.getWeight() * 1.2 + pkg.getHeightCm() * 0.10; // 10% del valor

        return baseFee + transportCost;
    }
}