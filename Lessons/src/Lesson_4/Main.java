package Lesson_4;

import Lesson_4.Data.Data;
import Lesson_4.Data.Encoding;
import Lesson_4.Game.Game;
import Lesson_4.Parser.FileWorker;
import Lesson_4.Parser.MyParser;
import Lesson_4.Parser.ParserXML;

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

        MyParser parser = new ParserXML();
        parser.setCharSet(Encoding.WINDOWS1251);
        StringBuilder string = parser.dataToString(root);

        if (!FileWorker.writeFile(string, FILE_NAME)) System.out.println("Не удалось записать в файл!");

        game.playerFromFile("log.xml");


    }
}
