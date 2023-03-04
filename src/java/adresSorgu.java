
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "adresBean")
@SessionScoped

public class adresSorgu implements Serializable {

    String dbUrl = "jdbc:derby://localhost:1527/mydb";
    String user = "username";
    String pword = "password";
    private String cadde;
    private String il;
    private String ilce;
    private String bulunacak_adres;

    public void setBulunacak_adres(String bulunacak_adres) {
        this.bulunacak_adres = bulunacak_adres;
    }

    public String getBulunacak_adres() {
        return bulunacak_adres;
    }
    CachedRowSet rowSet = null;

    public void setCadde(String cadde) {
        this.cadde = cadde;
    }

    public String getCadde() {
        return cadde;
    }

    public void setIl(String il) {
        this.il = il;
    }

    public String getil() {
        return il;
    }

    public void setIlce(String ilce) {
        this.ilce = ilce;
    }

    public String getIlce() {
        return ilce;
    }
    DataSource dataSource;

    public Connection conn;

    public Connection baglan() {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            System.out.println("Bağlandı.");
            conn = DriverManager.getConnection(dbUrl, user, pword);
        } catch (Exception e) {
            System.out.println("Bağlanamadı.");
        }
        return conn;
    }

    public void bul() {

        if (conn == null) {
            System.out.println("Bağlanıyor...");
            baglan();
        }
        int flag = 0;
        try {
            Statement stmt;
            stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM USERNAME.ADDRESSES");
            while (rs.next()) {
                if (rs.getString(2).equals(getCadde()) && rs.getString(3).equals(getil()) && rs.getString(4).equals(getIlce())) {
                    flag = 1;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (flag == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Maalesef yokuz"));
        } else if (flag == 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Yaşasın! Hemen yakınındayız, en kısa sürede görüşmek üzere!"));
        }

    }
}
