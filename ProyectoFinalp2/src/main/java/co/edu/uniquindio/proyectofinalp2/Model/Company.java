package co.edu.uniquindio.proyectofinalp2.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Company {

    private static Company instance;

    private List<User> users;
    private List<Admin> admins;
    private List<Dealer> dealers;
    private List<Shipment> shipments;

    public Company() {
        this.users = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.dealers = new ArrayList<>();
        this.shipments = new ArrayList<>();
    }

    public static Company getInstance() {
        if (instance == null) {
            instance = new Company();
        }
        return instance;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> loadedUsers) {

        this.users = loadedUsers;
    }

    public List<Dealer> getDealers() {
        return dealers;
    }

    public void setDealers(List<Dealer> list) {
        this.dealers = list;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> list) {
        this.shipments = list;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> list) {
        this.admins = list;
    }
}