package Lesson_3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * class Main
 *
 * @author Melnev Dmitry
 * @version 2022
 */

public class Main {
    private final static String FILE_NAME = "log.xml";

    public static void main(String[] args) {

        Data root = new Data("GamePlay");

        Game game = new Game(root);
        game.run();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(ParserXML.dataToXML(root).toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Спасибо за игру.");

        try {

            game.player("log.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}