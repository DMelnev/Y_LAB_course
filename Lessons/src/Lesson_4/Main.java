package Lesson_4;

import Lesson_4.Data.Data;
import Lesson_4.Data.Encoding;
import Lesson_4.Game.Game;
import Lesson_4.Parser.FileWorker;
import Lesson_4.Parser.MyParser;
import Lesson_4.Parser.ParserJSON;
import Lesson_4.Parser.ParserXML;
import Lesson_4.Player.Player;

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

//        Game game = new Game(root);
//        game.run();
//
        MyParser parser = new ParserXML();
        MyParser parser2 = new ParserJSON();

        parser.setCharSet(Encoding.WINDOWS1251);
//
//        String string = parser.dataToString(root);
//
//        FileWorker.setCharSet(Encoding.WINDOWS1251);
//        if (!FileWorker.writeFile(string, FILE_NAME)) System.out.println("Не удалось записать в файл!");
//
//        Player player = new Player();
//        player.FromFile("log.xml");

        root = parser.stringToData(FileWorker.readFile(FILE_NAME).toString());

        String test = parser2.dataToString(root);

        FileWorker.writeFile(test,"test.JSON");
        System.out.println(test);

    }
}
