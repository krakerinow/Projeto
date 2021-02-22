package teste.domain.dao;

import teste.domain.UnidadeCurricularImpl;

public class DaoFactory
{

    public static UserDao createUserDao()
    {
        return UserDao.getInstance();
    }

}
