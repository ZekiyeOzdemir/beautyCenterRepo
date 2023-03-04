
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@ManagedBean(name = "loginBean")
@SessionScoped
public class Login implements Serializable {
    
    private Customer customer;
    ;
    Connection conn;
    
    String dbUrl="jdbc:derby://localhost:1527/mydb";
    String user="username";
    String pword="password";
    String newpassword,chosenAppointment;
   
    

    public Customer getCustomer() {
        if(customer==null) {
            customer=new Customer();
        }
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
    
     public String getChosenAppointment() {
        return chosenAppointment;
    }

    public void setChosenAppointment(String chosenAppointment) {
        this.chosenAppointment = chosenAppointment;
    }
    
    
    public Connection baglan() {  // 1- derby veri tabanına bağlanmak için fonksiyon
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                System.out.println("Bağlantı başarılı.");
                conn=DriverManager.getConnection(dbUrl, user,pword);
            }
            catch (Exception e) {
                System.out.println("Bağlantı başarısız.");
            }
            
            return conn;
    }
  
     
   public void setInfos(String id,String p) { //loginden giriş yapan hesabın bilgilerini tabledan çeker,loginBean içindeki değişkenlere atar
        
        
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

    
    
    public String giris() {
        
       
        if(conn==null) {
                System.out.println("Bağlanıyor...");
                baglan();
        }
        int flag=0;
        
        try {
            
            
            Statement stmt=conn.createStatement();
            ResultSet rs =stmt.executeQuery("SELECT * FROM USERNAME.CUSTOMERS");

            while(rs.next()) { 
             
                if(rs.getString(1).equals(customer.tckn) && rs.getString(6).equals(customer.password)) {   
                    flag=1;
                    setInfos(customer.tckn,customer.password);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("validUser", customer);
                    
                    break;
                }
                
                
            }
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
        
        if (flag==0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kulanıcı adı ya da Şifre Yanlış"));
            return "login";
        }
        
        return "profile";
    
    }
    
    
    public String sifredegis() {
        int flag=0;
         
        if(conn==null) {
                System.out.println("Bağlanıyor...");
                baglan();
        }
         if(newpassword.equals("") ||newpassword.equals(" ") ) {
             flag=1;
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Lütfen Geçerli Bir Şifre Giriniz."));
             return "profile";
         }
        
        try {
            if(flag==0) {
                
                String sql = "UPDATE USERNAME.CUSTOMERS SET PASSWORD= ? WHERE  PASSWORD= ?";
                PreparedStatement ps = conn.prepareStatement(sql);         
                ps.setString(1, newpassword); 
                ps.setString(2, customer.password); 
                ps.executeUpdate();
                customer.password=newpassword;
            }
            
                
        }
            
        
        catch(Exception e ) {
            e.printStackTrace();
        }
        
        return "login";
        
    }
    
    
    public String logout() {
        customer=null;
        
        return "index";
    }
    
    
    
    
  
}

