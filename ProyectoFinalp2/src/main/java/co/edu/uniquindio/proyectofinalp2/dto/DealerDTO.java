package co.edu.uniquindio.proyectofinalp2.dto;

public class DealerDTO {
    private String idDealer;
    private String fullname;
    private String email;
    private String phone;

    public DealerDTO() {}


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdDealer() {
        return idDealer;
    }

    public void setIdDealer(String idDealer) {
        this.idDealer = idDealer;
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
