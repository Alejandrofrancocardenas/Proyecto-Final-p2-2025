package co.edu.uniquindio.proyectofinalp2.strategy;

import co.edu.uniquindio.proyectofinalp2.Model.Address;
import co.edu.uniquindio.proyectofinalp2.Model.PackageModel;
import co.edu.uniquindio.proyectofinalp2.strategy.ShippingCostStrategy;

public class SecureCostStrategy implements ShippingCostStrategy {
    @Override
    public double calculateShippingRate(PackageModel pkg, Address originAddress, Address destinationAddress) {

        // 1. Tarifa Base Media (por gestión de seguridad)
        double baseFee = 10000;

        // 2. Costo por Peso y Valor (factor de peso normal, pero factor de valor alto por el seguro)
        // 💥 El recargo principal aquí es un porcentaje del valor declarado.
        double transportCost = pkg.getWeight() * 1.2 + pkg.getHeightCm() * 0.10; // 10% del valor

        // 3. Costo por Distancia (si aplica)

        return baseFee + transportCost;
    }
}