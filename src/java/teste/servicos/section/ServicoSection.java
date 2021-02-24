package teste.servicos.section;

import teste.domain.*;
//import org.hibernate.Transaction;
//import org.hibernate.classic.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import teste.domain.Section;
import teste.domain.SectionImpl;
import teste.domain.dao.DaoFactory;
import teste.utils.HibernateUtils;
import teste.servicepack.security.logic.HasRole;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.isAuthenticated;

public class ServicoSection {

    @isAuthenticated
    @HasRole(role="admin")
    @Transaction
    public JSONObject addSection(JSONObject section){

        long idPage = section.getLong("idPage");
        Page page = DaoFactory.createPageDao().load(idPage);
        SectionImpl s = SectionImpl.fromJson(section);

        if(s.getId() > 0)
        {
            SectionImpl sPersistente = (SectionImpl) DaoFactory.createSectionDao().get(s.getId());

            sPersistente.setId(s.getId());
            sPersistente.setTitle(s.getTitle());
            sPersistente.setPage(s.getPage());

            JSONObject jsonObject = new JSONObject(sPersistente.toJson());

            return jsonObject;
        }
        else
        {
            page.getSections().add(s);
            HibernateUtils.getCurrentSession().save(s);
        }

        return new JSONObject(s.toJson());
    }

    @isAuthenticated
    @Transaction
    public JSONArray returnAll(JSONObject dummy) throws JSONException
    {
        Page page = DaoFactory.createPageDao().load(dummy.getLong("id"));
        JSONArray resultados = new JSONArray();


        for(Section s: page.getSections())
        {
            for(Components c : s.getComponents()) {
                resultados.put(new JSONObject(((ComponentsImpl) c).toJson()));
            }
        }

        return resultados;
    }

    @isAuthenticated
    @HasRole(role = "admin")
    @Transaction
    public void deleteSection(JSONObject section) {
        Section s = (Section) HibernateUtils.getCurrentSession().load(Section.class, section.getLong("idSection"));

        HibernateUtils.getCurrentSession().delete(s);
    }




}
