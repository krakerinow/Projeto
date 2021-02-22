package teste.domain.dao;

import org.hibernate.Query;
import org.hibernate.criterion.Order;
import teste.domain.Student;
import teste.domain.UnidadeCurricular;

import java.util.List;

import static org.hibernate.criterion.Restrictions.like;

public class UnidadeCurricularDao extends AbstractDao<UnidadeCurricular>
{

    private UnidadeCurricularDao() {
    }

    private static UnidadeCurricularDao instance = new UnidadeCurricularDao();

    protected static UnidadeCurricularDao getInstance()
    {
        return instance;
    }

    @Override
    public Class obtainDomainClass() {
        return  UnidadeCurricular.class;
    }


}
