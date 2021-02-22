package teste.domain.dao;

public class DaoFactory
{

    public static StudentDao createStudentDao()
    {
        return StudentDao.getInstance();
    }
}
