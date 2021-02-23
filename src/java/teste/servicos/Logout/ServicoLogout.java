package teste.servicos.Logout;

import teste.domain.UserSession;
import teste.domain.dao.DaoFactory;
//import teste.servicepack.security.logic.*;
import teste.servicepack.security.logic.AtributeSession;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.IsAuthenticated;
import java.util.List;

public class ServicoLogout {

    @IsAuthenticated
    @Transaction
    @AtributeSession
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