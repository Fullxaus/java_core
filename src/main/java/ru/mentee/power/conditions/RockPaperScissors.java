package ru.mentee.power.conditions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    public static final String ROCK = "Камень";
    public static final String PAPER = "Бумага";
    public static final String SCISSORS = "Ножницы";

    public static final String WIN = "Победа";
    public static final String LOSE = "Поражение";
    public static final String DRAW = "Ничья";
    public static final String ERROR = "Ошибка";

    private static final List<String> VALID_MOVES = Arrays.asList(ROCK, PAPER, SCISSORS);

    private Random random = new Random();

    public String determineWinner(String playerMove, String computerMove) {
        if (!validateMove(playerMove) || !validateMove(computerMove)) {
            return ERROR;
        }

        if (playerMove.equals(computerMove)) {
            return DRAW;
        }

        switch (playerMove) {
            case ROCK:
                return computerMove.equals(SCISSORS) ? WIN : LOSE;
            case PAPER:
                return computerMove.equals(ROCK) ? WIN : LOSE;
            case SCISSORS:
                return computerMove.equals(PAPER) ? WIN : LOSE;
            default:
                return ERROR;
        }
    }

    private boolean validateMove(String move) {
        return VALID_MOVES.contains(move);
    }

    public String generateComputerMove() {
        return VALID_MOVES.get(random.nextInt(VALID_MOVES.size()));
    }

    public void playGame(String playerMove) {
        String computerMove = generateComputerMove();
        String result = determineWinner(playerMove, computerMove);
        System.out.println("Компьютер выбрал: " + computerMove + ". Результат: " + result);
    }

    public static void main(String[] args) {
        RockPaperScissors game = new RockPaperScissors();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите ход (Камень, Бумага, Ножницы) или 'выход' для завершения:");
            String playerMove = scanner.nextLine();

            if (playerMove.equalsIgnoreCase("выход")) {
                break;
            }

            game.playGame(playerMove);
        }

        scanner.close();
    }
}
