package teste.domain.dao;

import teste.domain.Page;

public class PageDao extends AbstractDao<Page>{

    private PageDao() {
    }

    private static PageDao instance = new PageDao();

    protected static PageDao getInstance()
    {
        return instance;
    }

    @Override
    public Class obtainDomainClass() {
        return Page.class;
    }

}