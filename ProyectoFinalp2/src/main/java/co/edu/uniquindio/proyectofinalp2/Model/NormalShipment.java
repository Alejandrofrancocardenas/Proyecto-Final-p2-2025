package co.edu.uniquindio.proyectofinalp2.Model;


public class NormalShipment extends Shipment {

    private NormalShipment(Builder builder) {
        // Llama al constructor protegido de la clase base Shipment,
        // que maneja la asignación de todas las propiedades,
        // incluyendo originAddress y destinationAddress.
        super(builder);
    }

    /**
     * El Builder de NormalShipment hereda automáticamente los métodos de construcción
     * (shipmentId, user, packageModel, zone) y ahora también
     * originAddress y destinationAddress desde Shipment.Builder.
     */
    public static class Builder extends Shipment.Builder<Builder> {

        // Aquí no se necesita lógica adicional, solo se implementan los métodos abstractos
        // requeridos por la clase base.

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public NormalShipment build() {
            return new NormalShipment(this);
        }
    }


    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public double getPrice() {
        return 0;
    }


    @Override
    public String track() {
        return "\nEl envío está " + this.getStatus();
    }

}