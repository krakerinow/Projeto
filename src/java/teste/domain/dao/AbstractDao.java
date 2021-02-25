package teste.domain.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import teste.utils.HibernateUtils;
import teste.web.LoginServlet;

import java.io.Serializable;

public abstract class AbstractDao<CLAZZ>
{

    public abstract Class obtainDomainClass();


    public Criteria createCriteria(){
        return getCurrentSession().createCriteria(obtainDomainClass());
    }

    public Session getCurrentSession()
    {
        return HibernateUtils.getCurrentSession();
    }
    public CLAZZ load(Serializable id)
    {
        return (CLAZZ) HibernateUtils.getCurrentSession().load(obtainDomainClass(),id);
    }

    public CLAZZ get(Serializable id)
    {
        return (CLAZZ) HibernateUtils.getCurrentSession().get(obtainDomainClass(),id);
    }


    public void save(CLAZZ c)
    {
        HibernateUtils.getCurrentSession().save(c);
    }

    public void refresh(CLAZZ c)
    {
        HibernateUtils.getCurrentSession().refresh(c);
    }

    public void update(CLAZZ c)
    {
        HibernateUtils.getCurrentSession().update(c);
    }
    public void delete(CLAZZ c)
    {
        HibernateUtils.getCurrentSession().delete(c);
    }

}
