package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public class PriorityCostStrategy implements ShippingCostStrategy {

    @Override
    public double calculateCost(PackageModel pkg, Address Address) {
        double base = 4000;
        return base + pkg.getWeight() * 1.5 + pkg.getVolume() * 0.8;
    }
}
