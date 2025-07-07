package ru.mentee.power.conditions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RockPaperScissorsTest {

    private RockPaperScissors game;

    private static final String ROCK = "Камень";
    private static final String PAPER = "Бумага";
    private static final String SCISSORS = "Ножницы";
    private static final String PLAYER_WINS = "Победа";
    private static final String COMPUTER_WINS = "Поражение";
    private static final String DRAW = "Ничья";
    private static final String ERROR = "Ошибка";

    @BeforeEach
    void setUp() {
        game = new RockPaperScissors();
    }

    @Test
    @DisplayName("Камень побеждает ножницы")
    void rockBeatsScissors() {
        // Arrange
        String playerChoice = ROCK;
        String computerChoice = SCISSORS;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(PLAYER_WINS);
    }

    @Test
    @DisplayName("Ножницы побеждают бумагу")
    void scissorsBeatsPaper() {
        // Arrange
        String playerChoice = SCISSORS;
        String computerChoice = PAPER;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(PLAYER_WINS);
    }

    @Test
    @DisplayName("Бумага побеждает камень")
    void paperBeatsRock() {
        // Arrange
        String playerChoice = PAPER;
        String computerChoice = ROCK;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(PLAYER_WINS);
    }

    @Test
    @DisplayName("Ничья при одинаковом выборе (Камень)")
    void drawWhenSameChoiceRock() {
        // Arrange
        String playerChoice = ROCK;
        String computerChoice = ROCK;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    @DisplayName("Ничья при одинаковом выборе (Бумага)")
    void drawWhenSameChoicePaper() {
        // Arrange
        String playerChoice = PAPER;
        String computerChoice = PAPER;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    @DisplayName("Ничья при одинаковом выборе (Ножницы)")
    void drawWhenSameChoiceScissors() {
        // Arrange
        String playerChoice = SCISSORS;
        String computerChoice = SCISSORS;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    @DisplayName("Обработка некорректного выбора игрока")
    void handleInvalidPlayerChoice() {
        // Arrange
        String playerChoice = "Колодец"; // Некорректный выбор
        String computerChoice = ROCK;

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(ERROR);
    }

    @Test
    @DisplayName("Обработка некорректного выбора компьютера")
    void handleInvalidComputerChoice() {
        // Arrange
        String playerChoice = ROCK;
        String computerChoice = "Огонь"; // Некорректный выбор

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(ERROR);
    }

    @Test
    @DisplayName("Обработка некорректного выбора у обоих")
    void handleInvalidBothChoices() {
        // Arrange
        String playerChoice = "Вода";
        String computerChoice = "Воздух";

        // Act
        String result = game.determineWinner(playerChoice, computerChoice);

        // Assert
        assertThat(result).isEqualTo(ERROR);
    }

    @RepeatedTest(10) // Повторим тест 10 раз для большей уверенности в случайности
    @DisplayName("Генерация случайного выбора компьютера")
    void generateComputerChoiceReturnsValidOption() {
        // Act
        String choice = game.generateComputerMove();

        // Assert
        assertThat(choice).isIn(ROCK, PAPER, SCISSORS);
    }

    @ParameterizedTest
    @CsvSource({ // playerChoice, computerChoice, expectedResult
            "Камень, Ножницы, Победа",
            "Ножницы, Камень, Поражение",
            "Бумага, Бумага, Ничья",
            "Ножницы, Бумага, Победа",
            "Камень, Бумага, Поражение",
            "Бумага, Ножницы, Поражение"
    })
    @DisplayName("Различные комбинации выборов для determineWinner")
    void testVariousChoiceCombinationsDetermineWinner(String playerChoice, String computerChoice, String expectedResult) {
        String result = game.determineWinner(playerChoice, computerChoice);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Тестирование метода playGame (без моков)")
    void testPlayGame_ValidChoice() {
        // Arrange
        String playerChoice = ROCK;

        // Act
        game.playGame(playerChoice);

        // Assert
        // нет Assert, т.к. метод void и выводит информацию в консоль
    }

    @Test
    @DisplayName("Тестирование метода generateComputerMove")
    void testGenerateComputerMove() {
        // Act
        String computerMove = game.generateComputerMove();

        // Assert
        assertThat(computerMove).isIn(ROCK, PAPER, SCISSORS);
    }
}