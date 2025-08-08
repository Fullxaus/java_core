package ru.mentee.power.nio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

class NioFileUtil {

    /**
     * Копирует файл с использованием Files.copy.
     * Перезаписывает целевой файл, если он существует.
     * @param source Исходный путь.
     * @param destination Целевой путь.
     * @throws IOException Если происходит ошибка ввода-вывода.
     */
    public static void copyFile(Path source, Path destination) throws IOException {
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("(Внутри NioFileUtil) Копирование из " + source + " в " + destination);
    }
}

public class FilesCopyDemo {

    public static void main(String[] args) {
        Path sourcePath = Paths.get("source_files_copy.txt");
        Path destinationPath = Paths.get("destination_files_copy.txt");

        try {
            // Проверка, существует ли исходный файл. Если нет - создание.
            if (Files.notExists(sourcePath)) {
                System.out.println("Создаем исходный файл: " + sourcePath);
                String defaultContent = "Это содержимое файла по умолчанию,\nсозданного программой.";
                Files.writeString(sourcePath, defaultContent, StandardCharsets.UTF_8);
            }

            System.out.println("Вызываем NioFileUtil.copyFile...");
            // Вызов статического метода копирования из NioFileUtil
            NioFileUtil.copyFile(sourcePath, destinationPath);

            System.out.println("Файл успешно скопирован из " + sourcePath + " в " + destinationPath);

        } catch (IOException e) {
            System.err.println("Ошибка при копировании или создании файла: " + e.getMessage());
        }

        // Очистка
        try {
            Files.deleteIfExists(destinationPath);
        } catch (IOException e) {
            System.err.println("Ошибка при удалении файла: " + e.getMessage());
        }
    }
}

