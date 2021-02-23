package teste.domain.dao;

import teste.domain.Section;

public class SectionDao extends AbstractDao<Section>
{

    private SectionDao() {
    }

    private static SectionDao instance = new SectionDao();

    protected static SectionDao getInstance()
    {
        return instance;
    }

    @Override
    public Class obtainDomainClass() { return Section.class;}

}
