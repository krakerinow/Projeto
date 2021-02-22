package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

public class Relatorio

{

   static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .create();

    private String ano;

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String toJson(){
        return genson.serialize(this);
    }

    public static Relatorio fromJson(String json){
        return genson.deserialize(json,Relatorio.class);
    }
}
