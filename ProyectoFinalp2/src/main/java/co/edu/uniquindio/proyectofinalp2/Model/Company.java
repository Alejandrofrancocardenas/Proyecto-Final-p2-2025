package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private String nit;
    private List<User> users; // representa la lista de clientes finales
    private List<Admin> admins; // representa la lista de administradores
    private List<Dealer> dealers; //representa la lista de repartidores
    private List<Shipment> shipments; // representa la lista de envios realizados


    public Company(String name, String nit) {
        this.name = name;
        this.nit = nit;
        this.users = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.dealers = new ArrayList<>();
        this.shipments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public List<Dealer> getDealers() {
        return dealers;
    }

    public void setDealers(List<Dealer> dealers) {
        this.dealers = dealers;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", nit='" + nit + '\'' +
                ", users=" + users +
                ", admins=" + admins +
                ", dealers=" + dealers +
                ", shipments=" + shipments +
                '}';
    }
}
