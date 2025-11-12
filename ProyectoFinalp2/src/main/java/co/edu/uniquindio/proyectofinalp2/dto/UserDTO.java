package co.edu.uniquindio.proyectofinalp2.dto;

import co.edu.uniquindio.proyectofinalp2.Model.Address;

import java.util.List;

public class UserDTO {

    private String idUser;
    private String fullname;
    private String email;
    private String phone;
    private List<Address> addresses;
private String password;

    public UserDTO() {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { // 👈 Setter Agregado
        this.password = password;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}
