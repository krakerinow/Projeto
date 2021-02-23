package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

public class ComponentTextImpl extends ComponentText {


    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id",Components.class)
            .include("text",ComponentText.class)
            .create();


    public static ComponentTextImpl fromJson(JSONObject jsonObject)
    {
        return genson.deserialize(jsonObject.toString(), ComponentTextImpl.class);
    }

    public String toJson()
    {
        return genson.serialize(this);
    }

    @Override
    public String toString() {
        return "Component{" +
                "id=" + getId() +
                "text=" + getText() +
                '}';
    }


}
