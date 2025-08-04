package ru.mentee.power.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LineProcessor {

    public static void main(String[] args) {
        // Используем аргументы командной строки для имен файлов
        String inputFileName = args.length > 0 ? args[0] : "input_for_processor.txt";
        String outputFileName = args.length > 1 ? args[1] : "output_processed.txt";
        Path inputPath = Paths.get(inputFileName);

        // Проверка и создание входного файла, если он не существует
        try {
            if (Files.notExists(inputPath)) {
                List<String> defaultLines = List.of("Первая строка.", "Second Line with MixEd Case", "третья");
                Files.write(inputPath, defaultLines, StandardCharsets.UTF_8);
                System.out.println("Создан файл по умолчанию: " + inputFileName);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла по умолчанию: " + e.getMessage());
            return;
        }

        // Используем try-with-resources для открытия файлов
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName, StandardCharsets.UTF_8));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName, StandardCharsets.UTF_8))) {

            String line;
            System.out.println("Обработка файла " + inputFileName + "...");

            // Чтение строк из входного файла
            while ((line = bufferedReader.readLine()) != null) {
                // Преобразование строки в верхний регистр
                String processedLine = line.toUpperCase();
                // Запись обработанной строки в выходной файл
                bufferedWriter.write(processedLine);
                // Добавление переноса строки
                bufferedWriter.newLine();
            }

            System.out.println("Файл успешно обработан. Результат в " + outputFileName);

        } catch (IOException e) {
            System.err.println("Ошибка при обработке файла: " + e.getMessage());
        }
    }
}
