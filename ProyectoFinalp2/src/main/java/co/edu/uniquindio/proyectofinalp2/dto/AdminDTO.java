package co.edu.uniquindio.proyectofinalp2.dto;

import co.edu.uniquindio.proyectofinalp2.Model.Address;

import java.util.List;

public class AdminDTO {

    private String idAdmin;
    private String fullname;
    private String email;
    private String phone;

    public AdminDTO() {}


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
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

}
