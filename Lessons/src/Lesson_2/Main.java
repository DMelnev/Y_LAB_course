package Lesson_2;

import java.util.Scanner;

/**
 * class Main
 *
 * @author Melnev Dmitry
 * @version 2022
 */

public class Main {


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Выберите вариант игры(1/2):");
        if (input.nextInt() == 1) {
            Game game = new Game();
            game.run();
        } else {
            Game2 game = new Game2();
            game.run();
        }



        System.out.println("Спасибо за игру.");

    }
}
