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
    private final static String FILE_XML = "log.xml";
    private final static String FILE_JSON = "log.json";

    public static void main(String[] args) {

        Data root = new Data("GamePlay");

        Game game = new Game(root);
        game.run();

        MyParser parserXML = new ParserXML();
        MyParser parserJSON = new ParserJSON();

        FileWorker.setCharSet(Encoding.UTF8);//JSON по умолчанию пытается прочитаться в кодировке UTF-8
        if (!FileWorker.writeFile(parserJSON.dataToString(root), FILE_JSON))
            System.out.println("Не удалось записать в файл!");

        FileWorker.setCharSet(Encoding.WINDOWS1251);
        if (!FileWorker.writeFile(parserXML.dataToString(root), FILE_XML))
            System.out.println("Не удалось записать в файл!");

        new Player().FromFile(FILE_XML);

        FileWorker.setCharSet(Encoding.UTF8);
        new Player().FromFile(FILE_JSON);

    }
}
