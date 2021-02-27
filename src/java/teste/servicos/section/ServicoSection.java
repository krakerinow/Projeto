package teste.servicos.section;

import org.apache.log4j.Logger;
import teste.domain.*;
//import org.hibernate.Transaction;
//import org.hibernate.classic.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import teste.domain.Section;
import teste.domain.SectionImpl;
import teste.domain.dao.DaoFactory;
import teste.servicos.componentes.ServicoComponentes;
import teste.utils.HibernateUtils;
import teste.servicepack.security.logic.HasRole;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.isAuthenticated;
import teste.web.LoginServlet;

public class ServicoSection {


    private static final Logger logger = Logger.getLogger(ServicoComponentes.class);

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
    public JSONArray returnComponents(JSONObject dummy) throws JSONException
    {
        Page page = DaoFactory.createPageDao().load(dummy.getLong("idpag"));
        JSONArray resultados = new JSONArray();

        logger.info("ESTE E O DUMMY " + dummy);

        for(Section s: page.getSections()) {
            logger.info("S getID " + s.getId());
            logger.info("dummy ID " + dummy.getLong("id"));
            logger.info(s.getId() == dummy.getLong("id"));
            if (s.getId() == dummy.getLong("id")) {
                for (Components c : s.getComponents()) {
                    resultados.put(new JSONObject(((ComponentsImpl) c).toJson()));
                }
            }
        }
        return resultados;
    }

    @isAuthenticated
    @Transaction
    public JSONArray returnSections(JSONObject dummy) throws JSONException
    {
        Page page = DaoFactory.createPageDao().load(dummy.getLong("id"));
        JSONArray resultados = new JSONArray();


        for(Section s: page.getSections())
        {
                resultados.put(new JSONObject(((SectionImpl) s).toJson()));
        };

        return resultados;
    }

    @isAuthenticated
    @HasRole(role = "admin")
    @Transaction
    public void deleteSection(JSONObject section) {
        Section s = (Section) HibernateUtils.getCurrentSession().load(Section.class, section.getLong("id"));

        HibernateUtils.getCurrentSession().delete(s);
    }




}
