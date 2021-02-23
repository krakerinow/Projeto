package teste.domain.dao;

import teste.domain.UserSession;

public class UserSessionDao extends AbstractDao<UserSession>
{

    private UserSessionDao() {
    }

    private static UserSessionDao instance = new UserSessionDao();

    protected static UserSessionDao getInstance()
    {
        return instance;
    }

    @Override
    public Class obtainDomainClass() {
        return UserSession.class;
    }

}