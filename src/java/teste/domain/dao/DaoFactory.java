package teste.domain.dao;

public class DaoFactory
{

    public static UserDao createUserDao()
    {
        return UserDao.getInstance();
    }

}
