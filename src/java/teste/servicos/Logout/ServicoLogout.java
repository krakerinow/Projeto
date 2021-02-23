package teste.servicos.Logout;

import teste.domain.UserSession;
import teste.domain.dao.DaoFactory;
//import teste.servicepack.security.logic.*;

import java.util.List;

public class ServicoLogout {

   // @isAuthenticated
   // @Transaction
   // @injectSession
    public boolean logout(String user, UserSession session) {

        List<UserSession> users = DaoFactory.createUserSessionDao().createCriteria().list();

        for (UserSession value : users) {
            if (value.getUser().getUsername().equals(user)) {
                DaoFactory.createUserSessionDao().delete(session);
                return true;
            }
        }
        return false;
    }


}