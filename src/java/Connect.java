
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean(name = "connectBean")
@RequestScoped

public class Connect {
    
    public Connection conn;
    
    String dbUrl="jdbc:derby://localhost:1527/mydb";
    String user="username";
    String password="password";
    static String onClickAlert;
    
    public Connection baglan() {  // 1- derby veri tabanına bağlanmak için fonksiyon
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                System.out.println("Bağlantı başarılı.");
                conn=DriverManager.getConnection(dbUrl, user,password);
            }
            catch (Exception e) {
                System.out.println("Bağlantı başarısız.");
            }
            
            return conn;
    }
    
    
    public int register(String id,String name, String lastname, String email,String phone,String password) {
            
        if(conn==null) {
            System.out.println("Bağlanıyor...");
            baglan();
        }
        
        int flag=0;

        try {
            
            Statement stmt=conn.createStatement();
            ResultSet rs =stmt.executeQuery("SELECT * FROM USERNAME.CUSTOMERS");
            
            while(rs.next()) {
                if(rs.getString(1).equals(id)) {   
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Bu Kimliğe Kayıtlı Bir Kullanıcı Zaten Var."));
                    flag=1;
                    return 1;
                    
                }
            }
            
            if(flag==0) {
                PreparedStatement ps= conn.prepareStatement("INSERT INTO USERNAME.CUSTOMERS "+ "(ID,NAME,LASTNAME,EMAIL,PHONE,PASSWORD)"+ "VALUES (?, ?, ?, ?, ?, ?)");
                ps.setString(1, id);
                ps.setString(2, name);
                ps.setString(3, lastname);
                ps.setString(4, email);
                ps.setString(5, phone);
                ps.setString(6, password);

                ps.executeUpdate();
            }
            

        }

        catch(Exception e ) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public void setInfos(String id,String p,Customer customer) { //loginden giriş yapan hesabın bilgilerini tabledan çeker,loginBean içindeki değişkenlere atar
        
        if(conn==null) {
                System.out.println("Bağlanıyor...");
                baglan();
        }
        
        try {
            
            
            Statement stmt=conn.createStatement();
            ResultSet rs =stmt.executeQuery("SELECT * FROM USERNAME.CUSTOMERS");

            while(rs.next()) { 
                
                
                if(rs.getString(1).equals(id) && rs.getString(6).equals(p)) {   
                    
                    customer.name=rs.getString(2);
                    customer.lastname=rs.getString(3);
                    customer.email=rs.getString(4);
                    customer.phone=rs.getString(5);
                    
                }
            }
        }
            
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
    
    
    
    public Customer login(String id,String password) {
               
        if(conn==null) {
                System.out.println("Bağlanıyor...");
                baglan();
        }
        
        
        
            
        return null;
            
    }
    
    
    
}
