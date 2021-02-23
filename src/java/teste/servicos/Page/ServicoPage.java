package teste.servicos.Page;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONObject;
import teste.domain.Page;
import teste.domain.PageImpl;
import teste.domain.UserSession;
import teste.domain.dao.DaoFactory;
import teste.servicepack.security.SecurityContextProvider;
import teste.utils.HibernateUtils;
import teste.servicepack.security.logic.HasRole;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.IsAuthenticated;
import java.util.List;

public class ServicoPage {
    @IsAuthenticated
    @HasRole(role="admin")
    @Transaction
    public JSONObject addPage(JSONObject page){

        PageImpl s = PageImpl.fromJson(page);
        String cookie = SecurityContextProvider.getInstance().getSecuritySessionContext().getRequester();
        UserSession session = (UserSession) HibernateUtils.getCurrentSession().load(UserSession.class,cookie);

        if(s.getId() > 0)
        {
            PageImpl sPersistente = (PageImpl) DaoFactory.createPageDao().get(s.getId());
           // sPersistente.setTitle(s.getTitle());
            sPersistente.setId(s.getId());
            sPersistente.setTitle(s.getTitle());
            sPersistente.setRoles(s.getRoles());
            sPersistente.setUser(s.getUser());
            sPersistente.setSections(s.getSections());

            JSONObject jsonObject = new JSONObject(sPersistente.toJson());

            return jsonObject;
        }
        else
        {
            s.setUser(session.getUser());
            HibernateUtils.getCurrentSession().save(s);
            return new JSONObject(s.toJson());
        }


    }
    @IsAuthenticated
    @Transaction
    public JSONObject loadPage(JSONObject page) {
        Long sId = page.getLong("id");
        PageImpl sPersistente = (PageImpl) DaoFactory.createPageDao().get(sId);

        JSONObject jsonObject = new JSONObject(sPersistente.toJson());

        return jsonObject;
    }

    @IsAuthenticated
    @Transaction
    public JSONArray loadAll(JSONObject dummy) {
        List<Page> pages = DaoFactory.createPageDao().createCriteria().list();
        JSONArray results = new JSONArray();

        for(Page p: pages) {
            results.put(new JSONObject(((PageImpl)p).toJson()));
        }

        return results;
    }

    @IsAuthenticated
    @HasRole(role="admin")
    @Transaction
    public void deletePage(JSONObject page) {
        Page p = (Page) HibernateUtils.getCurrentSession().load(Page.class, page.getLong("id"));

        HibernateUtils.getCurrentSession().delete(p);
    }

}
