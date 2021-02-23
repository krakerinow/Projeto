package teste.domain.dao;

import teste.domain.ComponentText;



public class ComponentsTextDao extends AbstractDao<ComponentText>
{

    private ComponentsTextDao() {
    }

    private static ComponentsTextDao instance = new ComponentsTextDao();

    protected static ComponentsTextDao getInstance()
    {
        return instance;
    }

    @Override
    public Class obtainDomainClass() { return ComponentText.class;}

}
