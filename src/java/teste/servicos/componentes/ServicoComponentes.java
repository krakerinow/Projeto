package teste.servicos.componentes;

import org.json.JSONObject;
import teste.domain.Components;
import teste.domain.ComponentTextImpl;
import teste.domain.Section;
import teste.domain.dao.DaoFactory;
import teste.servicepack.security.logic.HasRole;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.isAuthenticated;
import teste.utils.HibernateUtils;

public class ServicoComponentes {

    @isAuthenticated
    @HasRole(role="admin")
    @Transaction
    public JSONObject addComponentText(JSONObject component){
        long idSection = component.getLong("idSection");
        ComponentTextImpl s = ComponentTextImpl.fromJson(component);
        Section section = DaoFactory.createSectionDao().load(idSection);

        if(s.getId() > 0)
        {
            ComponentTextImpl sPersistente = (ComponentTextImpl) DaoFactory.createComponentsDao().get(s.getId());

            sPersistente.setText(s.getText());

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
        Components c = (Components) HibernateUtils.getCurrentSession().load(Components.class, component.getLong("idComponent"));

        HibernateUtils.getCurrentSession().delete(c);
    }
}
