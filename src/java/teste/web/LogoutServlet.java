package teste.web;

import teste.servicos.Logout.ServicoLogout;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    user = cookie.getValue();
                    break;
                }
            }
        }
        ServicoLogout servLogout = new ServicoLogout();
        if (servLogout.logout(user, null)) {
            Cookie loginCookie = null;
            Cookie[] cookies1 = req.getCookies();
            if (cookies1 != null) {
                for (Cookie cookie : cookies1) {
                    if (cookie.getName().equals("user")) {
                        loginCookie = cookie;
                        break;
                    }
                }
            }
            if (loginCookie != null) {
                loginCookie.setMaxAge(0);
                resp.addCookie(loginCookie);
            }
            //invalidate the session if exists
            HttpSession session = req.getSession(false);
            System.out.println("User=" + session.getAttribute("user"));
            session.invalidate();
            String encodedURL = resp.encodeRedirectURL("http://localhost:8080/es/login.do");
            resp.sendRedirect(encodedURL);
        }
    }

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
