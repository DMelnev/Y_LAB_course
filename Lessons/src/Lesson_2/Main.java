package Lesson_2;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        File file = new File("statistic.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Введите Ваше имя:");
        String name = input.next();

        Game game = new Game();
        int result = game.run();
        String text = "";
        System.out.println("Game over.");
        switch (result) {
            case -1:
                text = name + " проиграл(а).";
                break;
            case 1:
                text = name + " выиграл(а)!";
                break;
            case 0:
                text = name + " сыграл(а) в ничью.";
                break;
            default:
                break;
        }
        text = text + "\n";

        try (OutputStream outputStream = new FileOutputStream(file, true)){
            outputStream.write(text.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
