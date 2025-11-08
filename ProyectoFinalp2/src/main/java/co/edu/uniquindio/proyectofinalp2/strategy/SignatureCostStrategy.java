package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public class SignatureCostStrategy implements ShippingCostStrategy {

    @Override
    public double calculateCost(PackageModel pkg, Address address) {
        double base = 1000;
        // calcular la distacia supongo
        return base + pkg.getWeight() * 1.1 + pkg.getVolume() * 0.6;
    }
}
