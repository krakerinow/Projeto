package teste.domain.dao;

import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import teste.domain.Student;

import java.util.List;

import static org.hibernate.criterion.Restrictions.*;

public class StudentDao extends AbstractDao<Student>
{

    private StudentDao() {
    }

    private static StudentDao instance = new StudentDao();

    protected static StudentDao getInstance()
    {
        return instance;
    }

    @Override
    public Class obtainDomainClass() {
        return  Student.class;
    }

    public List<Student> listStudentsUsingUcNameQuery(String name)
    {
        Query q = getCurrentSession().createQuery(
                "select u from class  " + Student.class.getName() + " " +
                        "join unidadeCurricular uc " +
                        "where uc.nome like :nome");
        q.setString("nome","%" + name + "%");

        return q.list();
    }

    public List<Student> listStudentsUsingUcNameCriteria(String name)
    {
        return createCriteria()
                //.setProjection(Projections.property("id"))
                //.setProjection(Projections.projectionList()
                //    .add(Projections.property("id"))
                //    .add(Projections.count("id")))
                .createAlias("unidadeCurricular","uc")
                .add(like("uc.nome","%" + name +  "%"))
                //.add(eq("ativo",true))
                .addOrder(Order.asc("nome"))
                .list();


    }
}
