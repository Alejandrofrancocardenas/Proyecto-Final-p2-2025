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
    // CORRECCIÓN: El setter debe usar 'role' como argumento y asignar a 'this.role'
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

    // ------------------------------------------------------------------
    // MÉTODOS HEREDADOS DE PERSON (Añadidos aquí para CLARIDAD, asumiendo
    // que existen en la clase Person para que el ProfileController funcione)
    // ------------------------------------------------------------------

    // Si la propiedad 'name' en el DTO se mapea al método 'getFullname()' o 'getName()'
    // Este método debe existir en la clase Person.
    // public abstract String getFullname(); // Asumiendo que Person es abstracta o tiene este método

    // Si 'name' en el DTO se mapea a un campo de nombre simple:
    public String getName() {
        // Asumiendo que este método existe en Person y devuelve el nombre
        return super.getFullname(); // O super.getName() si usas solo un campo de nombre
    }
    public void setName(String name) {
        // Asumiendo que este método existe en Person y actualiza el nombre
        // super.setName(name);
        // Si Person usa fullname, podrías necesitar un método en Person para actualizarlo
    }

    public String getPhoneNumber() {
        // Asumiendo que este método existe en Person y devuelve el teléfono
        return super.getPhone();
    }
    public void setPhoneNumber(String phoneNumber) {
        // Asumiendo que este método existe en Person y actualiza el teléfono
        // super.setPhoneNumber(phoneNumber);
    }

    // El email ya está siendo accedido directamente como getEmail()

    // ------------------------------------------------------------------

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
        // Nota: Si Person tiene un campo llamado 'name', se usa getName().
        // Si usa 'getFullname()', entonces el DTO.name se mapea a esa propiedad.
        return "User{" +
                "name='" + getFullname() + '\'' +
                ", role='" + rol + '\'' +
                ", addresses=" + addresses +
                ", payments=" + payments.size() +
                ", shipments=" + shipments.size() +
                '}';
    }
}