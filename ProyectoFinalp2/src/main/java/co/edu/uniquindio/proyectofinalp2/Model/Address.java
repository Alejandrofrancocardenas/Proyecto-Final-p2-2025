package co.edu.uniquindio.proyectofinalp2.Model;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    // Atributos de gestión (para identificar y nombrar la dirección)
    private String idAddress; // Identificador único de la dirección (clave para UserService)
    private String name;      // Alias o nombre de la dirección (e.g., "Casa", "Trabajo")

    // Atributos de ubicación
    private String city;
    private String street;
    private String postalCode;

    /**
     * Constructor por defecto.
     */
    public Address() {
    }

    /**
     * Constructor completo para inicialización.
     */
    public Address(String idAddress, String name, String city, String street, String postalCode) {
        this.idAddress = idAddress;
        this.name = name;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    // --- Getters y Setters para idAddress ---
    public String getIdAddress() { return idAddress; }
    public void setIdAddress(String idAddress) { this.idAddress = idAddress; }

    // --- Getters y Setters para name ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // --- Getters y Setters para city ---
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    // --- Getters y Setters para street ---
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    // --- Getters y Setters para postalCode ---
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    /**
     * Representación de la dirección como una cadena legible.
     */
    @Override
    public String toString() {
        return (name != null ? name + ": " : "") + street + ", " + city + " (" + postalCode + ")";
    }
}