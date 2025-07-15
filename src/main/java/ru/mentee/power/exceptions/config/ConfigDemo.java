package ru.mentee.power.exceptions.config;

import ru.mentee.power.exceptions.config.exception.ConfigException;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.HashMap;
import java.util.Map;

/**
 * Демонстрация работы ConfigManager.
 */
public class ConfigDemo {

    public static void main(String[] args) {
        // Собираем конфигурацию
        Map<String, String> config = new HashMap<>();
        config.put("database.url", "jdbc:mysql://localhost:3306/mydb");
        config.put("database.user", "root");
        config.put("database.password", "secret");
        config.put("server.port", "8080");
        config.put("debug.mode", "true");
        config.put("max.connections", "10");  // Значение корректное - число
        config.put("timeout", "30");

        ConfigManager manager = new ConfigManager(config);

        try {
            // Корректные запросы
            String url = manager.getRequiredValue("database.url");
            int port = manager.getRequiredIntValue("server.port");
            boolean debug = manager.getRequiredBooleanValue("debug.mode");

            System.out.println("URL       = " + url);
            System.out.println("Port      = " + port);
            System.out.println("Debug     = " + debug);

            // Проверяем, существует ли ключ, прежде чем получить значение
            if (config.containsKey("no.such.key")) {
                System.out.println("Missing   = " + manager.getRequiredValue("no.such.key"));
            } else {
                System.out.println("Ключ no.such.key отсутствует");
            }

            // Парсим числовое значение max.connections
            System.out.println("MaxConns  = " + manager.getRequiredIntValue("max.connections"));

            // Комментарии:
            // Не вызываем getRequiredValue или getRequiredIntValue для ключа no.such.key,
            // так как его нет в config — это предотвратит выброс исключения MissingConfigKeyException.

        } catch (MissingConfigKeyException e) {
            System.err.printf(
                    "ERROR: ключ '%s' отсутствует (%s)%n",
                    e.getMissingKey(),
                    e.getMessage()
            );
        } catch (InvalidConfigValueException e) {
            System.err.printf(
                    "ERROR: значение '%s' для ключа '%s' некорректно (%s)%n",
                    e.getValue(),
                    e.getKey(),
                    e.getMessage()
            );
        }
    }
}
