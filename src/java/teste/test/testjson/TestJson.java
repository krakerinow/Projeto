package teste.test.testjson;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import teste.domain.*;
import teste.domain.dao.StudentDao;

import java.util.HashSet;

public class TestJson extends TestCase
{
    public void testJsonOrg()
    {
        String json = "{ id : 3, nome: 'nome de teste'}";

        JSONObject obj = new JSONObject(json);

        JSONArray ucs = new JSONArray();

        obj.put("ucs",ucs);

        JSONObject uc = new JSONObject();
        uc.put("nome", "Engenharia do Software");

        ucs.put(uc);

        for(int i =0; i< ucs.length();i++)
        {
            System.out.println(ucs.get(i));
        }

        System.out.println(obj);

        JSONObject jsonObject = new JSONObject(obj.toString());
        assertEquals(jsonObject.getLong("id"),3);
        assertEquals(jsonObject.getString("nome"),"nome de teste");
        JSONArray ucsLidas = jsonObject.getJSONArray("ucs");
        assertEquals(ucsLidas.length(), 1);
        assertEquals(ucsLidas.getJSONObject(0).getString("nome"),"Engenharia do Software");
        assertEquals(jsonObject.getLong("id"),3);

    }

    public void testGenson()
    {
        Student s = new StudentImpl();
        s.setNome("Joao Manel");
        s.setId(2);

        UnidadeCurricular uc = new UnidadeCurricularImpl();
        uc.setNome("Engenharia do Software");
        uc.setId(1);
        uc.setStudents(new HashSet<>());

        uc.getStudents().add(s);

        Genson genson = new GensonBuilder()
                .useMethods(true)
                .useFields(false)
                .exclude(Object.class)
                .useClassMetadata(true)
                .useRuntimeType(true)
                .include("id")
                .include("nome",Student.class)
                //.include("id",UnidadeCurricular.class)
                .include("nome",UnidadeCurricular.class)
                .include("students",UnidadeCurricular.class)

                .create();


        System.out.println(genson.serialize(uc));

        String serializada = genson.serialize(uc);

        UnidadeCurricular ucLida = genson.deserialize(serializada,UnidadeCurricular.class);

        System.out.println();

    }

    public void testGensonComVarVirtual()
    {
        Student s = new StudentImpl();
        s.setNome("Joao Manel");
        s.setId(2);

        UnidadeCurricular uc = new UnidadeCurricularImpl();
        uc.setNome("Engenharia do Software");
        uc.setId(1);
        uc.setStudents(new HashSet<>());

        uc.getStudents().add(s);

        Relatorio r = new Relatorio();
        r.setAno("201920");

        //uc.setRelatorioObj(r);

        //System.out.println(uc.toJson());



        //UnidadeCurricular ucLida2 = UnidadeCurricular.fromJson(uc.toJson());
        System.out.println();

    }

}
