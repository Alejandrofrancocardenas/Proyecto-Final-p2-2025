package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public class PriorityCostStrategy implements ShippingCostStrategy {

    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {


        double baseFee = 15000;


        double transportCost = pkg.getWeight() * 1.5 + pkg.getHeightCm() * 1.5;

        return baseFee + transportCost;
    }
}