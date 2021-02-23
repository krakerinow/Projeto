package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

public class UserSessionImpl extends UserSession {
    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id",UserSession.class)
            .include("cookie",UserSession.class)
            .include("user",User.class)
            .create();

    public static UserSessionImpl fromJson(JSONObject jsonObject)
    {
        return genson.deserialize(jsonObject.toString(), UserSessionImpl.class);
    }

    public String toJson()
    {
        return genson.serialize(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", cookie='" + getCookie() + '\'' +
                '}';
    }
}
