package teste.web;

import org.apache.log4j.Logger;
import teste.domain.User;
import teste.servicos.login.ServicoLogin;
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

        User u = servLogin.Login(user, pass,null);
        if(u!=null){
            String roles = servLogin.returnRole();
            if(roles != null){
                HttpSession session = req.getSession();
                req.setAttribute("userLoggedIn",u);
                session.setAttribute("username", user);
                session.setAttribute("roles", roles);
                String encodedURL = resp.encodeRedirectURL("user.do");
                resp.sendRedirect(encodedURL);
            }
            else{
                HttpSession session = req.getSession();
                session.setAttribute("username", user);
                session.setAttribute("roles", "unassigned");
                String encodedURL = resp.encodeRedirectURL("user.do");
                resp.sendRedirect(encodedURL);
            }
        }else{
            //HttpSession session = req.getSession();
            //session.invalidate();
            String encodedURL = resp.encodeRedirectURL("http://localhost:8080/es/login.do");
            resp.sendRedirect(encodedURL);
        }
    }

    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}