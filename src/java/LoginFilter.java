
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebFilter("/*")
public class LoginFilter implements Filter{
    
    public void doFilter(ServletRequest sr, ServletResponse sr1,FilterChain fc) throws IOException, ServletException {
    
        HttpServletRequest request=(HttpServletRequest) sr;
        HttpServletResponse response=(HttpServletResponse) sr1;
        
        String url=request.getRequestURI();
        System.out.println("-------"+url+"--------");
        fc.doFilter(sr, sr1);
        
        HttpSession session=request.getSession();
        Customer customer=null;
        
        if(session!=null) {
            customer = (Customer) session.getAttribute("validCustomer") ;
        }
        if(customer==null) {
            if(url.contains("logout") || url.contains("private")) {
                response.sendRedirect(request.getContextPath()+ "/login.xhtml");
            }
            else {
                fc.doFilter(sr,sr1);
            }
        }
        else {
            
            if(url.contains("register")) {
                response.sendRedirect(request.getContextPath()+ "/index.xhtml");
            }
            else if (url.contains("logout")) {
                session.invalidate();
                response.sendRedirect(request.getContextPath()+ "/login.xhtml");
            }
            else {
                fc.doFilter(sr,sr1);
            }
        }
        
  }

    @Override
    public boolean isLoggable(LogRecord record) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
