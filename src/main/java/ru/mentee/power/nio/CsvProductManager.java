package ru.mentee.power.nio;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + ", quantity=" + quantity + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        return id == other.id && Double.compare(price, other.price) == 0 && quantity == other.quantity && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity);
    }
}

public class CsvProductManager {
    static final String CSV_HEADER = "ID;Name;Price;Quantity";
    private static final int EXPECTED_FIELDS = 4;

    /**
     * Сохраняет список продуктов в CSV файл.
     *
     * @param products Список продуктов.
     * @param filename Имя файла.
     * @param delimiter Разделитель полей.
     */
    public static void saveProductsToCsv(List<Product> products, String filename, String delimiter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(CSV_HEADER);
            writer.newLine();

            for (Product product : products) {
                String line = formatProductAsCsv(product, delimiter);
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Продукты сохранены в " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в CSV: " + e.getMessage());
        }
    }

    private static String formatProductAsCsv(Product product, String delimiter) {
        return product.getId() + delimiter +
                product.getName() + delimiter +
                product.getPrice() + delimiter +
                product.getQuantity();
    }

    /**
     * Загружает список продуктов из CSV файла.
     *
     * @param filename Имя файла.
     * @param delimiter Разделитель полей.
     * @return Список загруженных продуктов или пустой список в случае ошибки.
     */
    public static List<Product> loadProductsFromCsv(String filename, String delimiter) {
        List<Product> products = new ArrayList<>();
        Path filePath = Paths.get(filename);

        if (!Files.exists(filePath)) {
            System.err.println("Файл не найден: " + filename);
            return products;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String firstLine = reader.readLine(); // Пропускаем заголовок
            if (firstLine == null || !firstLine.contains(CSV_HEADER)) {
                System.err.println("Ошибка: Файл не содержит правильного заголовка");
                return products;
            }

            String currentLine;
            int lineNumber = 2; // Начинаем с 2, так как первая строка - заголовок
            while ((currentLine = reader.readLine()) != null) {
                Product product = parseProductFromCsvLine(currentLine, delimiter, lineNumber);
                if (product != null) {
                    products.add(product);
                }
                lineNumber++; // Увеличиваем номер строки для следующей строки
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла CSV: " + filename + " -> " + e.getMessage());
        }
        return products;
    }

    /**
     * Загружает список продуктов из строки, содержащей CSV данные.
     *
     * @param csvData Строка с CSV данными (включая заголовок).
     * @param delimiter Разделитель полей.
     * @return Список загруженных продуктов или пустой список в случае ошибки.
     */
    public static List<Product> loadProductsFromString(String csvData, String delimiter) {
        List<Product> products = new ArrayList<>();
        if (csvData == null || csvData.isEmpty()) {
            return products;
        }

        String[] lines = csvData.split("\\R"); // Универсальное разбиение на строки
        if (lines.length <= 1) {
            System.err.println("Ошибка: Данные CSV не содержат строк после заголовка.");
            return products;
        }

        // Пропускаем заголовок (первая строка)
        for (int i = 1; i < lines.length; i++) {
            Product product = parseProductFromCsvLine(lines[i], delimiter, i + 1); // i+1 — номер строки
            if (product != null) {
                products.add(product);
            }
        }
        System.out.println("Продукты загружены из строки.");
        return products;
    }

    /**
     * Парсит одну строку CSV и создаёт объект Product.
     *
     * @param line Строка CSV.
     * @param delimiter Разделитель.
     * @param lineNumber Номер строки (для сообщений об ошибках).
     * @return Объект Product или null в случае ошибки парсинга.
     */
    public static Product parseProductFromCsvLine(String line, String delimiter, int lineNumber) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(delimiter);

        if (parts.length != EXPECTED_FIELDS) {
            System.err.println("Ошибка в строке №" + lineNumber + ": неверное количество полей (" + parts.length + ").");
            return null;
        }

        try {
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            double price = Double.parseDouble(parts[2].trim());
            int quantity = Integer.parseInt(parts[3].trim());
            return new Product(id, name, price, quantity);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка в строке №" + lineNumber + ": невозможно распарсить число.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Ошибка в строке №" + lineNumber + ": индекс выходит за пределы массива.");
        } catch (Exception e) {
            System.err.println("Ошибка в строке №" + lineNumber + ": общая ошибка.");
        }
        return null;
    }

    public static void main(String[] args) {
        String filename = "products.csv";
        String delimiter = ";";

        // 1. Создать тестовые данные
        List<Product> initialProducts = new ArrayList<>();
        initialProducts.add(new Product(101, "Ноутбук Alpha", 75000.50, 15));
        initialProducts.add(new Product(102, "Мышь Gamma", 1200.00, 55));
        initialProducts.add(new Product(103, "Клавиатура Omega", 3500.75, 30));

        // 2. Сохранить в CSV
        System.out.println("Сохранение продуктов...");
        saveProductsToCsv(initialProducts, filename, delimiter);

        // 3. Загрузить из CSV (из файла)
        System.out.println("\nЗагрузка продуктов из файла...");
        List<Product> loadedFromFile = loadProductsFromCsv(filename, delimiter);
        System.out.println("Загружено из файла:");
        loadedFromFile.forEach(System.out::println);

        // 4. Загрузить из строки (демонстрация)
        System.out.println("\nЗагрузка продуктов из строки...");
        String csvDataString = CSV_HEADER + "\n" +
                "201;Monitor;25000;5\n" +
                "202;Webcam;4500;25";
        List<Product> loadedFromString = loadProductsFromString(csvDataString, delimiter);
        System.out.println("Загружено из строки:");
        loadedFromString.forEach(System.out::println);

        // 5. Пример с ошибками
        System.out.println("\nЗагрузка продуктов из строки с ошибками...");
        String csvDataWithError = CSV_HEADER + "\n" +
                "301;Correct;10.0;1\n" +
                "invalid line\n" +
                "303;ErrorPrice;abc;5\n" +
                "304;CorrectToo;20.5;10";
        List<Product> loadedWithError = loadProductsFromString(csvDataWithError, delimiter);
        System.out.println("Загружено из строки с ошибками (только корректные): ");
        loadedWithError.forEach(System.out::println);

        // Очистка файла (опционально)
        // new File(filename).delete();
    }
}