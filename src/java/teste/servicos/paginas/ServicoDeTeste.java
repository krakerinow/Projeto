package teste.servicos.paginas;

import org.json.JSONObject;

public class ServicoDeTeste {

    public JSONObject hello(JSONObject param)
    {

        System.out.println(param.getString("argumento"));

        return new JSONObject().put("result","CORREU TUDO BEM");
    }

    public JSONObject hello(String param,String teste)
    {


        return new JSONObject().put("result","CORREU TUDO BEM");
    }
}
