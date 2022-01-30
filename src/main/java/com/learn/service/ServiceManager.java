package com.learn.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class содержит метод для получения данных из properties-файла
 *
 * @author Volodymyr Lopachak
 * @version 1.1 24 October 2021
 */
public class ServiceManager {
    public static final Logger LOGGER = Logger.getLogger(ServiceManager.class.getName());

    public ServiceManager() {
        loadApplicationProperties();
    }

    // здесь хранятся все Properties
    private final Properties applicationProperties = new Properties();

    /**
     * Метод возвращает значение проперти по его ключу
     *
     * @param key - ключ по которому можна получить значение проперти
     * @return - значение проперти по его ключу
     */
    public String getApplicationProperty(String key) {
        return processEnvironmentVariablesIfNecessary(applicationProperties.getProperty(key));
    }

    private String processEnvironmentVariablesIfNecessary(String value) {
        int index = value.indexOf("${");
        if (index == -1) {
            return value;
        } else {
            final StringBuilder stringBuilder = new StringBuilder().append(value.substring(0, index));
            while (true) {
                int endIndex = value.indexOf('}', index + 2);
                if (endIndex == -1) {
                    throw new IllegalArgumentException("Invalid property value: Missing '}':" + value);
                }
                String expression = value.substring(index + 2, endIndex);
                String[] data = expression.split(":");
                if (data.length != 2) {
                    throw new IllegalArgumentException("Invalid expression: " + expression);
                }
                String envValue = System.getenv(data[0]);
                stringBuilder.append(envValue != null ? envValue : data[1]);

                index = value.indexOf("${", endIndex);
                if (index == -1) {
                    stringBuilder.append(value.substring(endIndex + 1));
                    break;
                } else {
                    stringBuilder.append(value.substring(endIndex + 1, index));
                }
            }
            return stringBuilder.toString();
        }
    }

    /**
     * метод загружаее данные с файла jdbc.properties
     */
    private void loadApplicationProperties() {
        try (InputStream in = ServiceManager.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
            applicationProperties.load(in);
        } catch (IOException e) {
            LOGGER.error("ERROR of getting data");
        }
    }
}
