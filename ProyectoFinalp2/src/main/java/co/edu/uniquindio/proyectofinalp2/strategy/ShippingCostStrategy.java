package co.edu.uniquindio.proyectofinalp2.strategy;// Archivo: co.edu.uniquindio.proyectofinalp2.strategy.ShippingCostStrategy.java

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;

public interface ShippingCostStrategy {

    double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress);


}