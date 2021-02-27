package teste.domain;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import teste.servicos.section.ServicoSection;

public class SectionImpl extends Section {
    private static final Logger logger = Logger.getLogger(SectionImpl.class);

    static Genson genson = new GensonBuilder()
            .useMethods(true)
            .useFields(false)
            .exclude(Object.class)
            .useClassMetadata(true)
            .useRuntimeType(true)
            .include("id",Section.class)
            .include("title",Section.class)
            .include("idPage",Section.class)
            .create();


    public static SectionImpl fromJson(JSONObject jsonObject)
    {
        logger.info("OBJETO JSON  " + jsonObject);
        logger.info("SECTIONIMPL  " + genson.deserialize(jsonObject.toString(), SectionImpl.class));
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
