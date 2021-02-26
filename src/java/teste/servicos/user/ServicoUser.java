package teste.servicos.user;


import org.hibernate.proxy.HibernateProxy;
import org.json.JSONArray;
import org.json.JSONObject;
import teste.domain.UserImpl;
import teste.domain.User;
import teste.domain.dao.DaoFactory;
import teste.servicepack.security.logic.HasRole;
import teste.servicepack.security.logic.Transaction;
import teste.servicepack.security.logic.isAuthenticated;

import teste.utils.HibernateUtils;

import java.util.List;

public class ServicoUser {

    @Transaction
    @isAuthenticated
    @HasRole(role="admin")
    public JSONObject addUser(JSONObject user){
        UserImpl s = UserImpl.fromJson(user);

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
            HibernateUtils.getCurrentSession().save(s);
        }


        return new JSONObject(s.toJson());
    }

    @Transaction
    @isAuthenticated
    @HasRole(role="admin")
    public JSONObject loadUser(JSONObject idObj)
    {

        Long id = idObj.getLong("id");

        UserImpl userPersistente = (UserImpl)DaoFactory.createUserDao().get(id);

        JSONObject jsonObject = new JSONObject(userPersistente.toJson());

        return jsonObject;

    }

    @isAuthenticated
    @Transaction
    public JSONArray loadAll(JSONObject dummy)
    {
        List<User> user = DaoFactory.createUserDao().createCriteria().list();

        JSONArray resultados = new JSONArray();


        for(User u: user) {
            if(u instanceof HibernateProxy) {
                resultados.put(new JSONObject(((UserImpl)((HibernateProxy)u).getHibernateLazyInitializer().getImplementation()).toJson()));
            } else {
                resultados.put(new JSONObject(((UserImpl)u).toJson()));
            }

        }
        return resultados;
    }

    @Transaction
    @isAuthenticated
    @HasRole(role="admin")
    public void deleteUser(JSONObject user) {
        User u = (User) HibernateUtils.getCurrentSession().load(User.class, user.getLong("UserID"));

        HibernateUtils.getCurrentSession().delete(u);
    }


}
