package co.edu.uniquindio.proyectofinalp2.Model;

public enum ShippingStatus {
    REQUIRED(1),
    ASSIGNED(2),
    ENROUTE(3),
    DELIVERED(4),
    INCIDENCE(5),
    CANCELLED(6);
    private final int value;

    ShippingStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
