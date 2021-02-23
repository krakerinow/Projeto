package teste.servicos.componentes;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.json.JSONObject;
import teste.domain.Components;
import teste.domain.ComponentsImpl;
import teste.domain.ComponentTextImpl;
import teste.domain.dao.DaoFactory;
import teste.utils.HibernateUtils;

public class ServicoComponentes {

    public JSONObject addComponentText(JSONObject component){
        ComponentTextImpl s = ComponentTextImpl.fromJson(component);

        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();

        if(s.getId() > 0)
        {
            ComponentTextImpl sPersistente = (ComponentTextImpl) DaoFactory.createComponentsDao().get(s.getId());

            sPersistente.setText(s.getText());

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

    public void deleteComponent(JSONObject component) {
        Components c = (Components) HibernateUtils.getCurrentSession().load(Components.class, component.getLong("idComponent"));

        HibernateUtils.getCurrentSession().delete(c);
    }
}
