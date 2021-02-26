package teste.servicos.Logout;

import teste.domain.UserSession;
import teste.domain.dao.DaoFactory;
//import teste.servicepack.security.logic.*;
import teste.servicepack.security.logic.injectSession;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.isAuthenticated;
import java.util.List;
import java.util.logging.Logger;

public class ServicoLogout {
    private static final Logger logger = Logger.getLogger(String.valueOf(ServicoLogout.class));

    @isAuthenticated
    @Transaction
    @injectSession
    public boolean logout(String user, UserSession session) {


        List<UserSession> users = DaoFactory.createUserSessionDao().createCriteria().list();

        for (UserSession value : users) {
            if (value.getUser().getUsername().equals(user)) {
                DaoFactory.createUserSessionDao().delete(session);
                logger.info("LOGOUT 113531535665654564564654553445645645645645645661513");
                return true;
            }
        }
        return false;
    }


}