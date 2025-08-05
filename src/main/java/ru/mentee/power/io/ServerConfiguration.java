package ru.mentee.power.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerConfiguration implements Serializable {
    @Serial
    private static final long serialVersionUID = 301L;

    private String serverAddress;
    private int serverPort;
    private boolean loggingEnabled;
    private transient String lastStatus = "Idle"; // Поле не подлежит сохранению

    public ServerConfiguration(String serverAddress, int serverPort, boolean loggingEnabled) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.loggingEnabled = loggingEnabled;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    @Override
    public String toString() {
        return "ServerConfiguration{" +
                "server='" + serverAddress + ":" + serverPort +
                ", logging=" + loggingEnabled +
                ", status='" + lastStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ServerConfiguration)) return false;
        var other = (ServerConfiguration) obj;
        return Objects.equals(serverAddress, other.serverAddress) &&
                serverPort == other.serverPort &&
                loggingEnabled == other.loggingEnabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverAddress, serverPort, loggingEnabled);
    }
}

class SettingsManager {

    /**
     * Сохраняет коллекцию Serializable объектов в файл.
     * Используется механизм сериализации Java.
     *
     * @param settings Объекты для сохранения.
     * @param filename Название файла.
     */
    public static void saveSettings(List<Serializable> settings, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            // Записываем всю коллекцию сразу одной операцией
            oos.writeObject(settings);
            System.out.println("Настройки сохранены в " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении настроек: " + e.getMessage());
        }
    }

    /**
     * Загружает коллекцию Serializable объектов из файла.
     *
     * @param filename Название файла.
     * @return Загруженная коллекция Serializable объектов или пустой список в случае ошибки.
     */
    @SuppressWarnings("unchecked")
    public static List<Serializable> loadSettings(String filename) {
        List<Serializable> loadedSettings = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Файл настроек " + filename + " не найден.");
            return loadedSettings;
        }

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            // Читаем целую коллекцию сразу одной операцией
            loadedSettings = (List<Serializable>) ois.readObject();
            System.out.println("Настройки загружены из " + filename);
        } catch (FileNotFoundException e) {
            System.err.println("Файл настроек не найден: " + filename);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Ошибка при загрузке настроек: " + e.getMessage());
            loadedSettings.clear();
        }
        return loadedSettings;
    }
    public record WindowConfiguration(
            String windowTitle,
            int width,
            int height
    ) implements Serializable {
        @Serial
        private static final long serialVersionUID = 302L;
    }


    public static void main(String[] args) {
        String filename = "settings.ser";

        // Создаем коллекцию настроек
        List<Serializable> currentSettings = new ArrayList<>();
        currentSettings.add(new ServerConfiguration("example.com", 8080, true));
        currentSettings.add(new WindowConfiguration("Главное окно", 1280, 720));

        // Изменим временное значение перед сохранением
        if (!currentSettings.isEmpty() && currentSettings.get(0) instanceof ServerConfiguration sc) {
            sc.setLastStatus("До сохранения");
        }

        // Отображаем исходные настройки
        System.out.println("Исходные настройки:");
        System.out.println(currentSettings);

        // Сохраняем настройки
        saveSettings(currentSettings, filename);

        // Загружаем настройки
        System.out.println("\nЗагружаемые настройки:");
        List<Serializable> loadedSettings = loadSettings(filename);

        // Проверяем правильность загрузки
        if (loadedSettings.isEmpty()) {
            System.out.println("Не удалось загрузить настройки.");
        } else {
            System.out.println("Загружено:");
            System.out.println(loadedSettings);

            // Дополнительно выводим информацию о каждом объекте
            for (Serializable s : loadedSettings) {
                if (s instanceof ServerConfiguration sc) {
                    System.out.println("Сервер: адрес=" + sc.getServerAddress() +
                            ", порт=" + sc.getServerPort() +
                            ", ведение журнала=" + sc.isLoggingEnabled() +
                            ", статус=" + sc.getLastStatus());
                } else if (s instanceof WindowConfiguration wc) {
                    System.out.println("Окно: название=" + wc.windowTitle() +
                            ", размеры=" + wc.width() + "x" + wc.height());
                }
            }
        }
    }
}