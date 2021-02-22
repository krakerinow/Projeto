package teste.domain.dao;

import teste.domain.UnidadeCurricularImpl;

public class DaoFactory
{

    public static StudentDao createStudentDao()
    {
        return StudentDao.getInstance();
    }
    public static UnidadeCurricularDao createUnidadeCurricularDao()
    {
        return UnidadeCurricularDao.getInstance();
    }
}
