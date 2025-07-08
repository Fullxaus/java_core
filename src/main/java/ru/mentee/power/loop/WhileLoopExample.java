package ru.mentee.power.loop;

public class WhileLoopExample {
    public static void main(String[] args) {
        int countdown = 3;

        System.out.println("Подготовка к запуску:");
        while (countdown > 0) {
            System.out.println(countdown + "...");
            countdown--; // Уменьшаем счетчик - важно для завершения цикла!
        }
        System.out.println("Старт!");
    }
}
