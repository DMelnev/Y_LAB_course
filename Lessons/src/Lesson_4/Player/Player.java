/**
 * class Player
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_4.Player;

import Lesson_4.Data.Data;
import Lesson_4.Parser.FileWorker;
import Lesson_4.Parser.ParserXML;

import java.util.Locale;

public class Player extends Lesson_4.Game.Game {

    public void FromFile(String fileName) {
        Data root = null;
        System.out.println("Playing file: " + fileName);
        StringBuilder result = FileWorker.readFile(fileName);

        String START_XML = "<?xml";
        if (result.substring(0, START_XML.length()).toLowerCase(Locale.ROOT).equals(START_XML)) {
            System.out.println("XML detected");
            root = new ParserXML().stringToData(result.toString());
        }
        String START_JSON = "{";
        if (result.substring(0, START_JSON.length()).equals(START_JSON)) {
            System.out.println("JSON detected");
//            MyParser parser = new ParserXML();
//            root = parser.stringToData(result);
        }

        FromData(root);
    }

    public void FromData(Data data) {

        if (!data.getTagName().equals("GamePlay")) {
            System.out.println("Не верный формат");
            return;
        }
        for (Data game : data.getChildNodes()) {
            if (game.getTagName().equals("Player")) {//в ТЗ не используется
                System.out.printf("Player %s name is %s as %s\n",
                        game.getAttrByName("id"),
                        game.getAttrByName("name"),
                        game.getAttrByName("symbol"));
                continue;
            }

            System.out.printf("Game %s set %s\n",
                    game.getAttrByName("map"),
                    game.getAttrByName("set"));
            set = Integer.parseInt(game.getAttrByName("set"));
            size = Integer.parseInt(game.getAttrByName("map").split("x")[0]);
            initMap();

            for (Data step : game.getChildNodes()) {
                if (step.getTagName().equals("GameResult")) {
                    if (step.getText().equals("Draw!")) {
                        System.out.println("Draw!");
                    } else {
                        Data winner = step.getChildNodeByName("Player");
                        System.out.printf("Player %s -> %s is winner as '%s'!\n",
                                winner.getAttrByName("id"),
                                winner.getAttrByName("name"),
                                winner.getAttrByName("symbol"));
                    }
                    System.out.println();
                    continue;
                }
                if (step.getTagName().equals("Step")) {
                    char mark = (step.getAttrByName("playerId").equals("1")) ? DOT_HUMAN : DOT_AI;
                    String[] coord = step.getText().split(",");
                    if (coord.length != 2) continue;
                    map[(Integer.parseInt(coord[1]) - 1)][(Integer.parseInt(coord[0]) - 1)] = mark;
                    printMap();
                    System.out.println();
                }
            }
        }
    }
}
