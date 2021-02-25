package teste.web;

import org.apache.log4j.Logger;
import teste.servicos.login.ServicoLogin;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import teste.domain.User;
import teste.domain.UserImpl;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("pwd");
        ServicoLogin servLogin = new ServicoLogin();
        logger.info("User pede login: " + user);
        logger.debug("Pedido do user com a pass: " + pass);

        if(servLogin.Login(user, pass,null)){

            String roles = servLogin.returnRole();
            if(roles != null){
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setAttribute("roles", roles);
                Cookie userName = new Cookie("user", user);
                resp.addCookie(userName);
                String encodedURL = resp.encodeRedirectURL("home.do");
                resp.sendRedirect(encodedURL);
            }
            else{
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setAttribute("roles", "unassigned");
                Cookie userName = new Cookie("user", user);
                resp.addCookie(userName);
                String encodedURL = resp.encodeRedirectURL("home.do");
                resp.sendRedirect(encodedURL);
            }
        }else{
            String encodedURL = resp.encodeRedirectURL("http://localhost:8080/es/home.do");
            resp.sendRedirect(encodedURL);
        }
    }

    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}