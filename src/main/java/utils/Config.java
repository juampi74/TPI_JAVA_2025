package utils;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static Properties props = new Properties();

    static {
        try {
            System.out.println(">>> Intentando cargar config.properties...");

            InputStream input = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("config.properties");

            System.out.println(">>> input = " + input);

            if (input == null) {
                throw new RuntimeException("No se encontró config.properties en el classpath");
            }

            props.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Error cargando config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
