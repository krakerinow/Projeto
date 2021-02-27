package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

public class PageImpl extends Page {


    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id", Page.class)
            .include("title", Page.class)
            .include("roles", Page.class)
            //.include("user", Page.class)
            .include("sections", Page.class)
            .include("title", Section.class)
            .include("id", Section.class)
            .include("components", Section.class)
            .include("text", Components.class)
            .include("path", Components.class)
            .include("id", Components.class)
            .create();

    static Genson gensonSingle = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id", Page.class)
            .include("title", Page.class)
            .include("roles", Page.class)
            //.include("user", Page.class)
            .include("sections", Page.class)
            .include("title", Section.class)
            .include("id", Section.class)
            .include("components", Section.class)
            .include("text", Components.class)
            .include("path", Components.class)
            .include("id", Components.class)
            .create();

    public static PageImpl fromJson(JSONObject jsonObject) {
        return genson.deserialize(jsonObject.toString(), PageImpl.class);
    }

    public String toJson() {
        return genson.serialize(this);
    }

    public String toJsonSingle() { return gensonSingle.serialize(this); }

    @Override
    public String toString() {
        return "Page{" +
                ", id='" + getId() + '\'' +
                ", title='" + getTitle() + '\'' +
                ", role='" + getRoles() + '\'' +
                ", sections='" + getSections() + '\'' +
                ", roles='" + getRoles() + '\'' +
                '}';
    }


}
