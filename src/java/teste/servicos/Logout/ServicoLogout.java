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
    public void logout(UserSession session) {

        session.setUser(null);

    }
}