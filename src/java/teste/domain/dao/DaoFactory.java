package teste.domain.dao;

import teste.domain.UserSession;

public class DaoFactory
{

    public static UserDao createUserDao()
    {
        return UserDao.getInstance();
    }
    public static UserSessionDao createUserSessionDao() { return UserSessionDao.getInstance(); }
    public static SectionDao createSectionDao()
    {
        return SectionDao.getInstance();
    }
    public static PageDao createPageDao()
    {
        return PageDao.getInstance();
    }
   /* public static ComponentsTextDao createComponentsTextDao()
    {
        return ComponentsTextDao.getInstance();
    }*/
    public static ComponentsDao createComponentsDao()  { return ComponentsDao.getInstance(); }

}
