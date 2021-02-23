package teste.domain.dao;

import teste.domain.Components;


public class ComponentsDao extends AbstractDao<Components>
{

    private ComponentsDao() {
    }

    private static ComponentsDao instance = new ComponentsDao();

    protected static ComponentsDao getInstance()
    {
        return instance;
    }

    @Override
    public Class obtainDomainClass() { return Components.class;}

}
