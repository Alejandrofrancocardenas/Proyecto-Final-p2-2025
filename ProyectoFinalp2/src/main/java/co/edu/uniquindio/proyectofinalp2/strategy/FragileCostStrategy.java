package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public class FragileCostStrategy implements ShippingCostStrategy {

    @Override
    public double calculateCost(PackageModel pkg, Address Address) {
        double base = 3000;
        return base + pkg.getWeight() * 1.3 + pkg.getVolume() * 0.7 + 500;
    }
}
