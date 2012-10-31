package net.andersand.andventure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author asn
 */
public class PropertyHolder {

    private static Properties properties;

    public static String get(String key) {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(new FileInputStream("src/main/resources/andventure.properties"));
                return properties.getProperty(key);
            }
            catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.valueOf(get(key));
    }
    
}
