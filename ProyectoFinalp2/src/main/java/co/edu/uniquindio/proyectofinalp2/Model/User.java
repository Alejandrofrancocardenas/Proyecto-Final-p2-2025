package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {

    private String password;
    private String rol;
    private List<Address> addresses;
    private List<Payment> payments;
    private List<Shipment> shipments;

    private User(Builder builder) {
        super(builder);
        this.password = builder.password;
        this.rol = builder.rol;
        this.addresses = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.shipments = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    private List<PackageModel> packages;

    public List<PackageModel> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageModel> packages) {
        this.packages = packages;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public void addAddress(Address address) {
        if (address != null) {
            addresses.add(address);
        }
    }

    public void addPayment(Payment payment) {
        if (payment != null) {
            payments.add(payment);
        }
    }

    public void addShipment(Shipment shipment) {
        if (shipment != null) {
            shipments.add(shipment);
        }
    }

    public String getName() {
        return super.getFullname();
    }

    public void setName(String name) {}

    public String getPhoneNumber() {
        return super.getPhone();
    }

    public void setPhoneNumber(String phoneNumber) {
    }
    public static class Builder extends Person.Builder<Builder> {
        private String password;
        private String rol;

        public Builder password(String password) {
            this.password = password;
            return self();
        }

        public Builder rol(String rol) {
            this.rol = rol;
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {

        return "User{" +
                "name='" + getFullname() + '\'' +
                ", role='" + rol + '\'' +
                ", addresses=" + addresses +
                ", payments=" + payments.size() +
                ", shipments=" + shipments.size() +
                '}';
    }
}