package teste.servicos.core;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import teste.domain.Student;
import teste.domain.StudentImpl;
import teste.domain.UnidadeCurricularImpl;
import teste.domain.dao.DaoFactory;
import teste.utils.HibernateUtils;

import java.util.HashSet;
import java.util.List;

public class ServicoStudents {

    public JSONObject addStudent(JSONObject estudante)
    {
        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();

        StudentImpl s = StudentImpl.fromJson(estudante);

        if(s.getId() > 0)
        {
                StudentImpl sPersistente = (StudentImpl) DaoFactory.createStudentDao().get(s.getId());

                sPersistente.setNome(s.getNome());
                sPersistente.setAtivo(s.isAtivo());
                sPersistente.setNumeroDeAluno(s.getNumeroDeAluno());
                JSONObject jsonObject = new JSONObject(sPersistente.toJson());
                t.commit();
                return jsonObject;
        }
        else
        {
            sess.save(s);
        }
        t.commit();

        return new JSONObject(s.toJson());
    }

    public JSONObject loadStudent(JSONObject idObj)
    {
        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();


        Long id = idObj.getLong("id");

        StudentImpl sPersistente = (StudentImpl)
                DaoFactory.createStudentDao().get(id);

        JSONObject jsonObject = new JSONObject(sPersistente.toJson());

        t.rollback();

        return jsonObject;

    }



    public JSONArray loadAll(JSONObject dummy)
    {
        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();

        List<Student> students = DaoFactory.createStudentDao().createCriteria().list();

        JSONArray resultados = new JSONArray();
        for(Student s: students)
        {
            resultados.put(new JSONObject(((StudentImpl)s).toJson()));
        }

        t.rollback();

        return resultados;
    }


    public JSONObject addUc(JSONObject pedido)
    {
        Session sess = HibernateUtils.getCurrentSession();
        Transaction t = sess.beginTransaction();


        long studentId = pedido.getLong("studentId");
        UnidadeCurricularImpl uc = UnidadeCurricularImpl.fromJson(pedido.getJSONObject("uc"));

        Student student = DaoFactory.createStudentDao().load(studentId);

        DaoFactory.createUnidadeCurricularDao().save(uc);

        student.getUnidadeCurriculares().add(uc);

        JSONObject jsonObject = new JSONObject(uc.toJson());

        t.commit();


        return jsonObject;


    }
}
