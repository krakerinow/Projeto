package teste.web;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import teste.domain.User;
import teste.domain.UserImpl;
import teste.utils.HibernateUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends AbstractServlet
{
    private static final Logger logger = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        logger.info("Recebendo pedido de login do artista: " + user);
        logger.debug("Recebendo pedido de login do artista com pass " + pass);

        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();
        t.begin();
        User s = new UserImpl();
        req.getSession().setAttribute("user",s);
        s.setNome(user);
        sess.save(s);
        t.commit();


        encaminha("/");
    }
}