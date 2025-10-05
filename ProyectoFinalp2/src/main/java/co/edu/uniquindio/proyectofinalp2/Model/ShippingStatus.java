package co.edu.uniquindio.proyectofinalp2.model;

public enum ShippingStatus {
    REQUIRED(1),
    ASSIGNED(2),
    ENROUTE(3),
    DELIVERED(4),
    INCIDENCE(5);

    private int value;

    ShippingStatus(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
