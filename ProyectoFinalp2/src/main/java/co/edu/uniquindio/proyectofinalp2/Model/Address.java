package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;


    private String idAddress;
    private String name;


    private String city;
    private String street;
    private String postalCode;

    public Address() {
    }

    public Address(String idAddress, String name, String city, String street, String postalCode) {
        this.idAddress = idAddress;
        this.name = name;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }


    public String getIdAddress() { return idAddress; }
    public void setIdAddress(String idAddress) { this.idAddress = idAddress; }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }


    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }


    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    @Override
    public String toString() {
        return (name != null ? name + ": " : "") + street + ", " + city + " (" + postalCode + ")";
    }
}