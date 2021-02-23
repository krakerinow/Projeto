package teste.servicepack.pluginSession;

import java.io.IOException;
import java.util.Properties;

public class PluginFactory
{
    static final Properties properties = new Properties();
    static
    {
        try {
            properties.load(PluginFactory.class.getResourceAsStream("/plugins.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getPlugin(Class iface) throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        String classNameVerdadeiro = properties.getProperty(iface.getName());
        return Class.forName(classNameVerdadeiro).newInstance();
    }
}
