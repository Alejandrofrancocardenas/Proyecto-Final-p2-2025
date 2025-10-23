package co.edu.uniquindio.proyectofinalp2.service;

public class ShippingService {
    public ShippingService() {
    }

    /**
     * metodo para calcular tarifa de envío según origen, destino, peso, volumen y prioridad.
     * @return double ShippingService
     */
    public static double calculatePrice(String origin, String destination, double weight
            , double volume, String priority) {
        double price = 0;
        //price += calculatePriceByDistance(origin, destination);

        if (weight > 0 &&  volume > 0) {
            if (weight < 100 && volume < 500) {
                price += 2000;
            } else if (weight < 500 && volume < 1000) {
                price += 4000;
            }else {
                price += 99999;
            }
        }

        switch (priority) {
            case "1" -> price += 3000;
            case "2" -> price += 5000;
            case "3" -> price += 7000;
        }
        return price;
    }
}
