package ru.mentee.power.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextFileReadWriteDemo {

    private static final Logger LOGGER =
            Logger.getLogger(TextFileReadWriteDemo.class.getName());

    private static final String FILE_NAME = "lines.txt";
    private static final List<String> LINES_TO_WRITE = List.of(
            "Это первая строка для записи.",
            "А это уже вторая.",
            "И, наконец, третья строка!"
    );

    public static void main(String[] args) {
        writeLinesToFile();
        readFileAndPrint();
    }

    /**
     * Записывает в файл построчно содержимое константы LINES_TO_WRITE.
     */
    private static void writeLinesToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            for (String line : LINES_TO_WRITE) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
            LOGGER.info(() -> String.format(
                    "Строки успешно записаны в файл '%s'.", FILE_NAME
            ));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    String.format("Ошибка при записи строк в файл '%s'.", FILE_NAME),
                    e);
        }
    }

    /**
     * Читает файл посимвольно и выводит его содержимое в лог.
     */
    private static void readFileAndPrint() {
        LOGGER.info(() -> "\n--- Чтение строк из файла ---");
        try (FileReader reader = new FileReader(FILE_NAME)) {
            int charCode;
            StringBuilder content = new StringBuilder();
            while ((charCode = reader.read()) != -1) {
                content.append((char) charCode);
            }
            LOGGER.info(() -> content.toString());
            LOGGER.info("Чтение завершено.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    String.format("Ошибка при чтении файла '%s'.", FILE_NAME),
                    e);
        }
    }
}
