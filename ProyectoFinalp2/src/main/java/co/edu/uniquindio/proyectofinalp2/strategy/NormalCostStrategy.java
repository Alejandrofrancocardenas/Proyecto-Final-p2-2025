package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public class NormalCostStrategy implements ShippingCostStrategy {
    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {


        double baseFee = 5000;

        double transportCost = pkg.getWeight() * 1.0 + pkg.getHeightCm() * 0.5;



        return baseFee + transportCost; // + distanceCost;
    }
}