package teste.servicos.user;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import teste.domain.UserImpl;
import teste.domain.User;
import teste.domain.dao.DaoFactory;
import teste.utils.HibernateUtils;

import java.util.List;

public class ServicoUser {

    public JSONObject addUser(JSONObject user){
        UserImpl s = UserImpl.fromJson(user);

        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();

        if(s.getId() > 0)
        {
                UserImpl sPersistente = (UserImpl) DaoFactory.createUserDao().get(s.getId());

                sPersistente.setNome(s.getNome());
                sPersistente.setUsername(s.getUsername());
                sPersistente.setEmail(s.getEmail());
                sPersistente.setPassword(s.getPassword());
                sPersistente.setRoles(s.getRoles());

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

    public JSONArray loadAll(JSONObject dummy)
    {
        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();

        List<User> user = DaoFactory.createUserDao().createCriteria().list();

        JSONArray resultados = new JSONArray();

        for(User s: user)
        {
            resultados.put(new JSONObject(((UserImpl)s).toJson()));
        }

        t.rollback();

        return resultados;
    }

}
