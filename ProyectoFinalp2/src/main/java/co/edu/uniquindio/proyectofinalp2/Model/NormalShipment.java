package co.edu.uniquindio.proyectofinalp2.Model;


public class NormalShipment extends Shipment {

    private NormalShipment(Builder builder) {
        super(builder);
    }


    public static class Builder extends Shipment.Builder<Builder> {

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
    public String track() {
        return "\nEl envío está " + this.status;
    }

}
