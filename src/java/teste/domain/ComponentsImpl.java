package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

public class ComponentsImpl extends Components {


    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id",Components.class)
            .include("path",Components.class)
            .include("text",Components.class)
            .include("idSection",Components.class)
            .create();


    public static ComponentsImpl fromJson(JSONObject jsonObject)
    {
        return genson.deserialize(jsonObject.toString(), ComponentsImpl.class);
    }

    public String toJson()
    {
        return genson.serialize(this);
    }

    @Override
    public String toString() {
        return "Component{" +
                "id=" + getId() +
                ", path='" + getPath() +
                ", text='" + getText() +
                ", idSection='" + getSection()+
                '}';
    }


}
