package teste.web;

import org.apache.log4j.Logger;
import teste.domain.UserSession;
import teste.servicos.Logout.ServicoLogout;
import teste.servicos.login.ServicoLogin;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends AbstractServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String User =null;
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    User = cookie.getValue();
                    break;
                }
            }
        }
        logger.info("LOGOUT SERVLET"+User);
        ServicoLogout servLogout = new ServicoLogout();
        if (servLogout.logout(User, null)) {
            HttpSession session = req.getSession();
            session.invalidate();
            String encodedURL = resp.encodeRedirectURL("login.do");
            resp.sendRedirect(encodedURL);

        }

    }

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
