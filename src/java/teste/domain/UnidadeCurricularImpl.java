package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

public class UnidadeCurricularImpl extends UnidadeCurricular {


    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("nome",UnidadeCurricular.class)
            .include("id",UnidadeCurricular.class)
            .create();


    public static UnidadeCurricularImpl fromJson(JSONObject jsonObject)
    {
        return genson.deserialize(jsonObject.toString(),UnidadeCurricularImpl.class);
    }

    public String toJson()
    {
        return genson.serialize(this);
    }


}
