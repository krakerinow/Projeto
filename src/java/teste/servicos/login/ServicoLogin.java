package teste.servicos.login;


import teste.domain.User;
import teste.domain.UserSession;
import teste.domain.dao.DaoFactory;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.injectSession;
import teste.utils.HibernateUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ServicoLogin {



    User u = null;

    //@Transaction
    @injectSession
    public boolean Login(String user,String pwd, UserSession session) throws ServletException, IOException {

        HibernateUtils.getCurrentSession().beginTransaction().begin();

        List<User> users = DaoFactory.createUserDao().createCriteria().list();

        for (User value : users) {
            if (value.getUsername().equals(user) && value.getPassword().equals(pwd)) {
                //System.out.println(session.getCookie());
                u = value;
                session.setUser(value);
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
