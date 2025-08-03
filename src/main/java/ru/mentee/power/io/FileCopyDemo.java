package ru.mentee.power.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileCopyDemo {

    private static final Logger LOGGER =
            Logger.getLogger(FileCopyDemo.class.getName());

    private static final String SOURCE_FILE_NAME      = "source.txt";
    private static final String DESTINATION_FILE_NAME = "copy_of_source.txt";
    private static final String DEFAULT_CONTENT       =
            "Это содержимое файла по умолчанию.%nСтрока 2.";
    private static final int    BUFFER_SIZE           = 4 * 1024;

    public static void main(String[] args) {
        Path sourcePath = Paths.get(SOURCE_FILE_NAME);

        createSourceIfNotExists(sourcePath);

        copyFileWithBuffer(SOURCE_FILE_NAME, DESTINATION_FILE_NAME);
    }

    /**
     * Проверяет, существует ли исходный файл, и при отсутствии создаёт его с дефолтным содержимым.
     */
    private static void createSourceIfNotExists(final Path sourcePath) {
        if (Files.exists(sourcePath)) {
            return;
        }

        try {
            String content = String.format(DEFAULT_CONTENT);
            Files.writeString(sourcePath, content, StandardCharsets.UTF_8);
            LOGGER.info("Исходный файл не найден. Файл создан с текстом по умолчанию.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Не удалось создать исходный файл: " + sourcePath, e);
            // Прекращаем дальнейшее выполнение, так как без исходника нет смысла продолжать копирование
            System.exit(1);
        }
    }

    /**
     * Копирует файл побайтово, используя буфер.
     */
    private static void copyFileWithBuffer(final String sourceFileName,
                                           final String destinationFileName) {
        byte[] buffer = new byte[BUFFER_SIZE];

        try (FileInputStream fis = new FileInputStream(sourceFileName);
             FileOutputStream fos = new FileOutputStream(destinationFileName)) {

            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            LOGGER.info(String.format(
                    "Файл успешно скопирован из '%s' в '%s'.",
                    sourceFileName, destinationFileName
            ));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    String.format("Ошибка при копировании из '%s' в '%s'.",
                            sourceFileName, destinationFileName),
                    e);
        }
    }
}
