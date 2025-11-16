package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;
import co.edu.uniquindio.proyectofinalp2.Model.Shipment;
import co.edu.uniquindio.proyectofinalp2.service.ShippingService;

public class FragileCostStrategy implements ShippingCostStrategy {

    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {
       double baseFragileFee = 3500;

        double transportCost = pkg.getWeight() * 1.3 + pkg.getHeightCm() * 0.7;

        return baseFragileFee + transportCost;
    }

}