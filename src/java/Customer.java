
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Customer {
    String tckn, password, name, lastname, email, phone;
    String appointments[][];
    
    
    Connection conn;

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
    
    public String[][] getAppointments() {
        return appointments;
    }

    public void setAppointments(String[][] appointments) {
        this.appointments = appointments;
    }
    
   

    
    
}
