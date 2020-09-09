package org.playground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private static Logger logger = LoggerFactory.getLogger(AppProperties.class);
    private static String configFilename = "config.properties";

    private static AppProperties INSTANCE;
    private static Properties properties;

    public static String DATABASE_CONNECTION_URL = "database.connectionURL";
    public static String DATABASE_USERNAME = "database.username";
    public static String DATABASE_PASSWORD = "database.password";

    private AppProperties() throws RuntimeException {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFilename)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(String.format("Property file '%s' not found in the classpath", configFilename), e);
            throw new RuntimeException(String.format("Property file '%s' not found in the classpath", configFilename));
        }
        logger.info(String.format("Property file %s loaded.", configFilename));
    }

    public static AppProperties getInstance() {
        if (INSTANCE == null) {
            synchronized(AppProperties.class) {
                INSTANCE = new AppProperties();
            }
        }
        return INSTANCE;
    }

    public String getValue(String name) {
        return properties.getProperty(name);
    }

    public String getValue(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

}
