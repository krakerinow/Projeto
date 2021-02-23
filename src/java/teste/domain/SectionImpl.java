package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

public class SectionImpl extends Section {


    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id",Section.class)
            .include("title",Section.class)
            .include("idpage",Section.class)
            .create();


    public static SectionImpl fromJson(JSONObject jsonObject)
    {
        return genson.deserialize(jsonObject.toString(), SectionImpl.class);
    }

    public String toJson()
    {
        return genson.serialize(this);
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                '}';
    }


}
