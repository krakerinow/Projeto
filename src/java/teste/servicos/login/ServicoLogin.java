package teste.servicos.login;


import org.apache.log4j.Logger;
import teste.domain.User;
import teste.domain.UserSession;
import teste.domain.dao.DaoFactory;
import teste.servicepack.security.SecurityContextProvider;
import teste.servicepack.security.SecuritySessionContext;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.injectSession;
import teste.utils.HibernateUtils;
import teste.web.LoginServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ServicoLogin {

    private static final Logger logger = Logger.getLogger(ServicoLogin.class);

    User u = null;

    //@Transaction
    //@injectSession
    public boolean Login(String user,String pwd, UserSession session) throws ServletException, IOException {

        HibernateUtils.getCurrentSession().beginTransaction().begin();

        SecuritySessionContext securitySessionContext = SecurityContextProvider.getInstance().getSecuritySessionContext();
        session = (UserSession) HibernateUtils.getCurrentSession().load(UserSession.class, securitySessionContext.getRequester());
        List<User> users = DaoFactory.createUserDao().createCriteria().list();

        for (User value : users) {
            if (value.getUsername().equals(user) && value.getPassword().equals(pwd)) {
                //System.out.println(session.getCookie());
                u = value;
                logger.info(u);
                session.setUser(value);
                logger.info("PASSOU");
                HibernateUtils.getCurrentSession().beginTransaction().commit();
                return true;
            }
        }
        HibernateUtils.getCurrentSession().beginTransaction().commit();
        return false;

    }

    //@Transaction

    public String returnRole(){
        HibernateUtils.getCurrentSession().beginTransaction().begin();
        HibernateUtils.getCurrentSession().beginTransaction().commit();
        return u.getRoles();
    }
}
