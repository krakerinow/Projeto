package teste.servicos.componentes;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import teste.domain.Components;
import teste.domain.ComponentsImpl;
import teste.domain.Section;
import teste.domain.SectionImpl;
import teste.domain.dao.DaoFactory;
import teste.servicepack.security.logic.HasRole;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.isAuthenticated;
import teste.servicos.section.ServicoSection;
import teste.utils.HibernateUtils;

public class ServicoComponentes {




    @isAuthenticated
    @HasRole(role="admin")
    @Transaction
    public JSONObject addComponent(JSONObject component){
        long idSection = component.getLong("idSection");
        ComponentsImpl s = ComponentsImpl.fromJson(component);
        Section section = DaoFactory.createSectionDao().load(idSection);

        if(s.getId() > 0)
        {
            ComponentsImpl sPersistente = (ComponentsImpl) DaoFactory.createComponentsDao().get(s.getId());

            sPersistente.setId(s.getId());
            sPersistente.setText(s.getText());
            sPersistente.setPath(s.getPath());

            JSONObject jsonObject = new JSONObject(sPersistente.toJson());

            return jsonObject;
        }
        else
        {
            section.getComponents().add(s);
            HibernateUtils.getCurrentSession().save(s);
        }

        return new JSONObject(s.toJson());
    }

    @isAuthenticated
    @HasRole(role="admin")
    @Transaction
    public void deleteComponent(JSONObject component) {
        Components c = (Components) HibernateUtils.getCurrentSession().load(Components.class, component.getLong("id"));

        HibernateUtils.getCurrentSession().delete(c);
    }
}
