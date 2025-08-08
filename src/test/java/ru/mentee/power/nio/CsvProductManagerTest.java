package ru.mentee.power.nio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

@DisplayName("Тесты менеджера продуктов CSV (CsvProductManager)")
public class CsvProductManagerTest {

    @TempDir
    Path tempDir;

    private Path testFilePath;
    private final String delimiter = ";";
    private List<Product> sampleProducts;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_products.csv");
        sampleProducts = new ArrayList<>();
        sampleProducts.add(new Product(1, "Laptop", 999.99, 10));
        sampleProducts.add(new Product(2, "Mouse", 25.50, 50));
        sampleProducts.add(new Product(3, "Keyboard", 75.00, 30));
    }

    // --- Тесты для saveProductsToCsv и loadProductsFromCsv ---
    @Nested
    @DisplayName("Тесты сохранения/загрузки из файла")
    class FileSaveLoadTests {

        @Test
        @DisplayName("Должен сохранять и загружать список продуктов из файла")
        void shouldSaveAndLoadProductsFromFile() throws IOException {
            // Когда (when)
            CsvProductManager.saveProductsToCsv(sampleProducts, testFilePath.toString(), delimiter);
            List<Product> loadedProducts = CsvProductManager.loadProductsFromCsv(testFilePath.toString(), delimiter);

            // Тогда (then)
            assertThat(loadedProducts).usingRecursiveComparison()
                    .ignoringFields("price") // Цена округляется из-за особенностей double
                    .isEqualTo(sampleProducts);
        }

        @Test
        @DisplayName("Должен корректно обрабатывать пустой список при сохранении/загрузке из файла")
        void shouldHandleEmptyListFile() throws IOException {
            // Дано (given)
            List<Product> emptyList = new ArrayList<>();

            // Когда (when)
            CsvProductManager.saveProductsToCsv(emptyList, testFilePath.toString(), delimiter);
            List<Product> loadedProducts = CsvProductManager.loadProductsFromCsv(testFilePath.toString(), delimiter);

            // Тогда (then)
            assertThat(loadedProducts).isEmpty();
            assertThat(Files.readAllLines(testFilePath)).containsExactly(CsvProductManager.CSV_HEADER);
        }

        @Test
        @DisplayName("loadProductsFromCsv должен возвращать пустой список, если файл не найден")
        void loadFromFileShouldReturnEmptyListWhenFileNotExists() {
            // Дано (given)
            assertThat(testFilePath).doesNotExist();

            // Когда (when)
            List<Product> loadedProducts = CsvProductManager.loadProductsFromCsv(testFilePath.toString(), delimiter);

            // Тогда (then)
            assertThat(loadedProducts).isEmpty();
        }

        @Test
        @DisplayName("loadProductsFromCsv должен пропускать некорректные строки")
        void loadFromFileShouldSkipMalformedLines() throws IOException {
            // Дано (given)
            String csvContent = CsvProductManager.CSV_HEADER + "\n" +
                    "1;ValidProduct;10.0;5\n" +
                    "invalid line data\n" + // Неверное число полей
                    "3;AnotherProduct;twenty;15\n" + // Ошибка парсинга цены
                    "4;LastProduct;50.0;2";
            Files.writeString(testFilePath, csvContent);

            // Когда (when)
            List<Product> loadedProducts = CsvProductManager.loadProductsFromCsv(testFilePath.toString(), delimiter);

            // Тогда (then)
            assertThat(loadedProducts).hasSize(2); // Только две правильные строки
            assertThat(loadedProducts.get(0))
                    .extracting(Product::getId, Product::getName, Product::getPrice, Product::getQuantity)
                    .containsExactly(1, "ValidProduct", 10.0, 5);
            assertThat(loadedProducts.get(1))
                    .extracting(Product::getId, Product::getName, Product::getPrice, Product::getQuantity)
                    .containsExactly(4, "LastProduct", 50.0, 2);
        }
    }

    // --- Тесты для loadProductsFromString ---
    @Nested
    @DisplayName("Тесты загрузки из строки")
    class StringLoadTests {

        @Test
        @DisplayName("loadProductsFromString должен загружать корректные данные")
        void loadFromStringShouldLoadCorrectData() {
            // Дано (given)
            String csvData = CsvProductManager.CSV_HEADER + "\n" +
                    "10;ProductA;1.1;11\n" +
                    "20;ProductB;2.2;22";

            // Когда (when)
            List<Product> loadedProducts = CsvProductManager.loadProductsFromString(csvData, delimiter);

            // Тогда (then)
            assertThat(loadedProducts).hasSize(2);
            assertThat(loadedProducts.get(0))
                    .extracting(Product::getId, Product::getName, Product::getPrice, Product::getQuantity)
                    .containsExactly(10, "ProductA", 1.1, 11);
            assertThat(loadedProducts.get(1))
                    .extracting(Product::getId, Product::getName, Product::getPrice, Product::getQuantity)
                    .containsExactly(20, "ProductB", 2.2, 22);
        }

        @Test
        @DisplayName("loadProductsFromString должен пропускать некорректные строки")
        void loadFromStringShouldSkipMalformedLines() {
            // Дано (given)
            String csvData = CsvProductManager.CSV_HEADER + "\n" +
                    "1;ValidProduct;10.0;5\n" +
                    "invalid line data\n" +
                    "3;AnotherProduct;twenty;15\n" +
                    "4;LastProduct;50.0;2";

            // Когда (when)
            List<Product> loadedProducts = CsvProductManager.loadProductsFromString(csvData, delimiter);

            // Тогда (then)
            assertThat(loadedProducts).hasSize(2);
            assertThat(loadedProducts.get(0))
                    .extracting(Product::getId, Product::getName, Product::getPrice, Product::getQuantity)
                    .containsExactly(1, "ValidProduct", 10.0, 5);
            assertThat(loadedProducts.get(1))
                    .extracting(Product::getId, Product::getName, Product::getPrice, Product::getQuantity)
                    .containsExactly(4, "LastProduct", 50.0, 2);
        }

        @Test
        @DisplayName("loadProductsFromString должен возвращать пустой список для пустой строки или строки с одним заголовком")
        void loadFromStringShouldReturnEmptyForEmptyOrHeaderOnly() {
            // Когда (when)
            List<Product> result1 = CsvProductManager.loadProductsFromString(null, delimiter);
            List<Product> result2 = CsvProductManager.loadProductsFromString("", delimiter);
            List<Product> result3 = CsvProductManager.loadProductsFromString(CsvProductManager.CSV_HEADER, delimiter);

            // Тогда (then)
            assertThat(result1).isEmpty();
            assertThat(result2).isEmpty();
            assertThat(result3).isEmpty();
        }
    }

    // --- Тесты для parseProductFromCsvLine ---
    @Nested
    @DisplayName("Тесты парсинга одной строки CSV (parseProductFromCsvLine)")
    class LineParsingTests {

        private final String delimiter = ";"; // Используем тот же разделитель

        @Test
        @DisplayName("Должен корректно парсить валидную строку")
        void shouldParseValidLine() {
            // Дано (given)
            String validLine = "10;Test Product;19.99;5";

            // Когда (when)
            Product product = CsvProductManager.parseProductFromCsvLine(validLine, delimiter, 1);

            // Тогда (then)
            assertThat(product).isNotNull();
            assertThat(product.getId()).isEqualTo(10);
            assertThat(product.getName()).isEqualTo("Test Product");
            assertThat(product.getPrice()).isCloseTo(19.99, within(0.1)); // Округление double
            assertThat(product.getQuantity()).isEqualTo(5);
        }

        @Test
        @DisplayName("Должен возвращать null для строки с неверным количеством полей")
        void shouldReturnNullForIncorrectFieldCount() {
            // Дано (given)
            String invalidLine = "10;Test Product;19.99"; // Нет количества (quantity)

            // Когда (when)
            Product product = CsvProductManager.parseProductFromCsvLine(invalidLine, delimiter, 1);

            // Тогда (then)
            assertThat(product).isNull();
        }

        @Test
        @DisplayName("Должен возвращать null при ошибке парсинга числа")
        void shouldReturnNullOnNumberFormatException() {
            // Дано (given)
            String invalidLine = "10;Test Product;nineteen ninety nine;5"; // Цена указана текстом

            // Когда (when)
            Product product = CsvProductManager.parseProductFromCsvLine(invalidLine, delimiter, 1);

            // Тогда (then)
            assertThat(product).isNull();
        }

        @Test
        @DisplayName("Должен корректно обрабатывать пробелы по краям полей")
        void shouldHandleLeadingTrailingSpaces() {
            // Дано (given)
            String lineWithSpaces = " 20 ; Spaced Product ; 15.50 ; 100 ";

            // Когда (when)
            Product product = CsvProductManager.parseProductFromCsvLine(lineWithSpaces, delimiter, 1);

            // Тогда (then)
            assertThat(product).isNotNull();
            assertThat(product.getId()).isEqualTo(20);
            assertThat(product.getName()).isEqualTo("Spaced Product");
            assertThat(product.getPrice()).isCloseTo(15.50, within(0.1));
            assertThat(product.getQuantity()).isEqualTo(100);
        }

        @Test
        @DisplayName("Должен возвращать null для пустой или null строки")
        void shouldReturnNullForNullOrEmptyLine() {
            // Когда (when)
            Product result1 = CsvProductManager.parseProductFromCsvLine(null, delimiter, 1);
            Product result2 = CsvProductManager.parseProductFromCsvLine("", delimiter, 1);
            Product result3 = CsvProductManager.parseProductFromCsvLine("   ", delimiter, 1);

            // Тогда (then)
            assertThat(result1).isNull();
            assertThat(result2).isNull();
            assertThat(result3).isNull();
        }
    }
}