package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

public class UserImpl extends User {


    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id",User.class)
            .include("nome",User.class)
            .include("username",User.class)
            .include("email",User.class)
            .include("password",User.class)
            .include("roles",User.class)
            .create();


    public static UserImpl fromJson(JSONObject jsonObject)
    {
        return genson.deserialize(jsonObject.toString(), UserImpl.class);
    }

    public String toJson()
    {
        return genson.serialize(this);
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", roles='" + getRoles() + '\'' +
                '}';
    }


}
