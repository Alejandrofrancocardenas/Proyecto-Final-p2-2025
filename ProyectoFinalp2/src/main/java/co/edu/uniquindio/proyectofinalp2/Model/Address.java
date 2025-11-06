package co.edu.uniquindio.proyectofinalp2.Model;

public class Address {
    private String idAddress;
    private String origin;
    private String destination;
    private String alias;
    private String street;
    private String city;
    private String coordinates;

    public Address(){}
    public Address(String idAddress, String origin, String destination, String alias, String street, String city, String coordinates){
        this.idAddress = idAddress;
        this.origin = origin;
        this.destination = destination;
        this.alias = alias;
        this.street = street;
        this.city = city;
        this.coordinates = coordinates;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }


    @Override
    public String toString() {
        return idAddress    + " " +
                origin      + " " +
                destination + " " +
                alias       + " " +
                street      + " " +
                city        + " " +
                coordinates;
    }
}
