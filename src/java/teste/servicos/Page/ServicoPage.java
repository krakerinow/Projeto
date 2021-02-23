package teste.servicos.Page;

import org.json.JSONArray;
import org.json.JSONObject;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.json.JSONObject;
import teste.domain.Page;
import teste.domain.PageImpl;
import teste.domain.dao.DaoFactory;
import teste.utils.HibernateUtils;

import java.util.List;

public class ServicoPage {

    public JSONObject addPage(JSONObject page){
        PageImpl s = PageImpl.fromJson(page);

        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();

        if(s.getId() > 0)
        {
            PageImpl sPersistente = (PageImpl) DaoFactory.createPageDao().get(s.getId());

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
            sess.save(s);
        }
        t.commit();

        return new JSONObject(s.toJson());
    }

    public JSONObject loadPage(JSONObject page) {
        Long objId = page.getLong("id");
        PageImpl objPersistent = (PageImpl) DaoFactory.createPageDao().get(objId);

        JSONObject jsonObject = new JSONObject(objPersistent.toJson());

        return jsonObject;
    }

    public JSONArray loadAll(JSONObject dummy) {
        List<Page> pages = DaoFactory.createPageDao().createCriteria().list();
        JSONArray results = new JSONArray();

        for(Page p: pages) {
            results.put(new JSONObject(((PageImpl)p).toJson()));
        }

        return results;
    }

    public void deletePage(JSONObject page) {
        Page p = (Page) HibernateUtils.getCurrentSession().load(Page.class, page.getLong("id"));

        HibernateUtils.getCurrentSession().delete(p);
    }

}
