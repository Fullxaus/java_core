package ru.mentee.power.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Класс для хранения записи результата игры
public class HighScoreEntryClass {
    private final String playerName;
    private final int score;

    public HighScoreEntryClass(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HighScoreEntryClass)) return false;
        HighScoreEntryClass that = (HighScoreEntryClass) o;
        return score == that.score && Objects.equals(playerName, that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, score);
    }

    @Override
    public String toString() {
        return "HighScoreEntryClass{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                '}';
    }
}

// Менеджер результатов игры
class HighScoreManager {

    /**
     * Метод сохраняет список игровых рекордов в бинарный файл.
     *
     * @param scores   Список объектов HighScoreEntryClass.
     * @param filename Имя файла для сохранения.
     */
    public static void saveScores(List<HighScoreEntryClass> scores, String filename) {
        try (
                FileOutputStream fos = new FileOutputStream(filename); // Поток для вывода байтов в файл
                BufferedOutputStream bos = new BufferedOutputStream(fos); // Буферизованный поток для оптимизации скорости
                DataOutputStream dos = new DataOutputStream(bos)          // Поток для записи примитивных типов данных
        ) {
            // Сначала записать количество элементов в файле
            dos.writeInt(scores.size()); // Кол-во записей (int)

            // Далее пройдем по каждому элементу списка и сохраним поля
            for (HighScoreEntryClass entry : scores) {
                dos.writeUTF(entry.getPlayerName()); // Запись имени игрока (строка в кодировке UTF-8)
                dos.writeInt(entry.getScore());      // Запись очков (целое число)
            }
            System.out.println("Данные успешно сохранены в " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    /**
     * Метод загружает игровые рекорды из бинарного файла и возвращает список объектов HighScoreEntryClass.
     *
     * @param filename Имя файла для чтения.
     * @return Список объектов HighScoreEntryClass или пустой список в случае ошибок.
     */
    public static List<HighScoreEntryClass> loadScores(String filename) {
        List<HighScoreEntryClass> loadedScores = new ArrayList<>();
        try (
                FileInputStream fis = new FileInputStream(filename);       // Поток для ввода байтов из файла
                BufferedInputStream bis = new BufferedInputStream(fis);   // Буферизованный поток для ускорения чтения
                DataInputStream dis = new DataInputStream(bis)             // Поток для чтения примитивных типов данных
        ) {
            // Читаем сначала количество записей
            int numRecords = dis.readInt();
            System.out.println("Ожидаем прочитать записей: " + numRecords);

            // Цикл по количеству записей
            for (int i = 0; i < numRecords; i++) {
                // Чтение строки имени игрока
                String playerName = dis.readUTF();
                // Чтение числа очков
                int score = dis.readInt();

                // Создание нового объекта HighScoreEntryClass
                HighScoreEntryClass entry = new HighScoreEntryClass(playerName, score);
                // Добавление объекта в список
                loadedScores.add(entry);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл '" + filename + "' не найден.");
        } catch (EOFException e) {
            System.err.println("Ошибка: неожиданный конец файла при чтении данных. Возможно, файл поврежден.");
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных: " + e.getMessage());
            loadedScores.clear(); // Очистка списка при ошибке
        }
        return loadedScores;
    }

    public static void main(String[] args) {
        String filename = "highscores.dat"; // Имя файла для сохранения/загрузки данных

        // Тестовые данные — создадим примерный список рекордов
        List<HighScoreEntryClass> testScores = new ArrayList<>();
        testScores.add(new HighScoreEntryClass("Alice", 1200)); // Игрок Alice набрал 1200 очков
        testScores.add(new HighScoreEntryClass("Bob", 1000));   // Игрок Bob набрал 1000 очков
        testScores.add(new HighScoreEntryClass("Charlie", 1550)); // Charlie набрал 1550 очков
        testScores.add(new HighScoreEntryClass("David", 1100));   // David набрал 1100 очков

        // Сохраняем тестовые данные в файл highscores.dat
        saveScores(testScores, filename);

        // Теперь попробуем загрузить ранее сохранённые данные
        System.out.println("\nЗагрузка рекордов...");
        List<HighScoreEntryClass> loadedScores = loadScores(filename);

        // Проверим, что мы действительно получили ожидаемые данные
        if (loadedScores.isEmpty()) {
            System.out.println("Данные не были загружены.");
        } else {
            System.out.println("--- Загруженные данные ---");
            for (HighScoreEntryClass entry : loadedScores) {
                System.out.println(entry);
            }
        }
    }
}