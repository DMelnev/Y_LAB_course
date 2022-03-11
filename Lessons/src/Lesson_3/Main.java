/**
 * Class Main
 *
 * @author Melnev Dmitriy
 * @version 2022
 **/
package Lesson_3;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Data<Data> root = new Data<>("GamePlay", "");
        root.addChildNode(new Data<>("Player", "",
                new HashMap<>(Map.of("id", "1", "name", "Igor", "symbol", "X"))));
        root.addChildNode(new Data<>("Player", "",
                new HashMap<>(Map.of("id", "2", "name", "Anna", "symbol", "X"))));


        Data<Data> game = new Data<>("Game", "");
        root.addChildNode(game);
        game.addChildNode(new Data<>("Step", "1,1"
                , new HashMap<>(Map.of("num", "1", "playerId", "1"))));
        game.addChildNode(new Data<>("Step", "1,2"
                , new HashMap<>(Map.of("num", "1", "playerId", "2"))));
        game.addChildNode(new Data<>("Step", "2,2"
                , new HashMap<>(Map.of("num", "1", "playerId", "1"))));
        game.addChildNode(new Data<>("Step", "2,1"
                , new HashMap<>(Map.of("num", "1", "playerId", "2"))));
        game.addChildNode(new Data<>("Step", "3,3"
                , new HashMap<>(Map.of("num", "1", "playerId", "1"))));

        Data<Data> test = new Data<>("Test1", "");
        root.addChildNode(test);
        Data<Data> test2 = new Data<>("Test2", "");
        test.addChildNode(test2);
        Data<Data> test3 = new Data<>("Test3", "");
        test.addChildNode(test3);
        test3.addChildNode(new Data<>("Step", "2,2"
                , new HashMap<>(Map.of("num", "1", "playerId", "1"))));
        test3.addChildNode(new Data<>("Step", "3,3"
                , new HashMap<>(Map.of("num", "1", "playerId", "1"))));

        Data<Data> gameResult = new Data<>("GameResult", "");
        root.addChildNode(gameResult);
        gameResult.addChildNode(new Data<>("Player", "",
                new HashMap<>(Map.of("id", "1", "name", "Igor", "symbol", "X"))));


        System.out.println(ConvertToXML.tooXml(root));


    }
}
