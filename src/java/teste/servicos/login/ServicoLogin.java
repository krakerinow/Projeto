package teste.servicos.login;

import org.apache.log4j.Logger;
import teste.domain.User;
import teste.domain.UserSession;
import teste.domain.dao.DaoFactory;
import teste.utils.HibernateUtils;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.injectSession;
import teste.web.LoginServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ServicoLogin {


    User u = null;

    @Transaction
    @injectSession
    public boolean Login(String user,String pwd, UserSession session) throws ServletException, IOException {

        List<User> users = DaoFactory.createUserDao().createCriteria().list();

        for (User value : users) {
            if (value.getUsername().equals(user) && value.getPassword().equals(pwd)) {
                System.out.println(session.getCookie());
                u = value;
                session.setUser(value);
                return true;
            }
        }
        return false;

    }

    @Transaction
    public String returnRole(){
        return u.getRoles();
    }
}
