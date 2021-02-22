package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

public class StudentImpl extends Student
{


    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id",Student.class)
            .include("nome",Student.class)
            .include("numeroDeAluno",Student.class)
            .include("ativo",Student.class)
            .include("unidadeCurriculares",Student.class)

            .include("nome",UnidadeCurricular.class)
            .include("id",UnidadeCurricular.class)
            .create();


    public static StudentImpl fromJson(JSONObject jsonObject)
    {
        return genson.deserialize(jsonObject.toString(),StudentImpl.class);
    }

    public String toJson()
    {
        return genson.serialize(this);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                '}';
    }
}
