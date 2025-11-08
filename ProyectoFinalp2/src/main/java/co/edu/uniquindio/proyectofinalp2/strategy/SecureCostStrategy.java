package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public class SecureCostStrategy implements ShippingCostStrategy {

    @Override
    public double calculateCost(PackageModel pkg, Address address) {
        double base = 10000;
        // calcular la distacia supongo
        return base + pkg.getWeight() * 1.9 + pkg.getVolume() * 0.9;
    }
}
