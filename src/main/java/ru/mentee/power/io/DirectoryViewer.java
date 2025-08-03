package ru.mentee.power.io;

import java.io.File;

public class DirectoryViewer {

    public static void main(String[] args) {
        // 1. Проверить наличие аргумента командной строки
        if (args.length == 0) {
            System.err.println("Ошибка: Не указан путь к директории.");
            System.err.println("Использование: java ru.mentee.power.io.DirectoryViewer <путь_к_директории>");
            return; // Завершить программу
        }

        String directoryPath = args[0];
        // 2. Создать объект File
        File directory = new File(directoryPath);

        // 3. Проверить, существует ли путь и является ли он директорией
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Ошибка: Путь не существует или не является директорией: " + directoryPath);
            return;
        }

        // 4. Вывести информацию о директории
        System.out.println("Содержимое директории: " + directory.getAbsolutePath());
        System.out.println("--------------------------------------------------");

        // 5. Получить список файлов и поддиректорий
        File[] files = directory.listFiles();

        // 6. Проверить, не вернул ли listFiles() null
        if (files == null) {
            System.err.println("Ошибка: Не удалось получить доступ к содержимому директории (проверьте права доступа).");
            return;
        }

        // 7. Проверить, пуста ли директория
        if (files.length == 0) {
            System.out.println("Директория пуста.");
            return;
        }

        // 8. Перебрать содержимое и вывести информацию
        for (File file : files) {
            // Определить тип (файл/директория)
            String type = file.isDirectory() ? "[DIR] " : "[FILE]";

            // Получить имя
            String name = file.getName();

            // Получить размер (только для файлов)
            String sizeInfo = "";
            if (file.isFile()) {
                sizeInfo = " (" + file.length() + " bytes)";
            }

            // Вывести строку в нужном формате
            System.out.println(type + " " + name + sizeInfo);
        }
    }
}
