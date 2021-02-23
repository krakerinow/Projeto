package teste.web;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import teste.domain.User;
import teste.domain.UserImpl;
import teste.servicos.login.ServicoLogin;
import teste.utils.HibernateUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends AbstractServlet
{
    private static final Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ServicoLogin sLogin = new ServicoLogin();

        if(sLogin.Login(username, password ,null)){
            String roles = sLogin.returnRole();
            if(roles!=null){
                HttpSession session = req.getSession();
                session.setAttribute("user", username);
                session.setAttribute("roles", roles);
               // session.setMaxInactiveInterval(30*60);
                Cookie userName = new Cookie("user", username);
                resp.addCookie(userName);
                String encodedURL = resp.encodeRedirectURL("home.do");
                resp.sendRedirect(encodedURL);
            }
            else{
                HttpSession session = req.getSession();
                session.setAttribute("user", username);
                session.setAttribute("roles", "guest");
                //session.setMaxInactiveInterval(30*60);
                Cookie userName = new Cookie("user", username);
                resp.addCookie(userName);
                String encodedURL = resp.encodeRedirectURL("home.do");
                resp.sendRedirect(encodedURL);
            }
        }else{
            String encodedURL = resp.encodeRedirectURL("http://localhost:8080/es/login.do?passe_errada");
            resp.sendRedirect(encodedURL);
        }























    }
}