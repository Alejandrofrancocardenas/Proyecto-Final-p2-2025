package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.service.ShippingService;

public class SignatureCostStrategy implements ShippingCostStrategy {

    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {

        double baseSignatureFee = 4000;

        double transportCost = pkg.getWeight() * 1.1 + pkg.getHeightCm() * 0.6;

        return baseSignatureFee + transportCost;
    }

}