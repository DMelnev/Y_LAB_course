/**
 * Class Main
 *
 * @author Melnev Dmitriy
 * @version 2022
 **/
package Lesson_3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Data<Data> root = new Data<>("GamePlay", "");
        root.addChildNode(new Data<>("Player", "",
                new HashMap<>(Map.of("id", "1", "name", "Igor", "symbol", "X"))));
        root.addChildNode(new Data<>("Player", "",
                new HashMap<>(Map.of("id", "2", "name", "Anna", "symbol", "O"))));


        Data<Data> game = new Data<>("Game", "");
        root.addChildNode(game);
        game.addChildNode(new Data<>("Step", "1,1"
                , new HashMap<>(Map.of("num", "1", "playerId", "1"))));
        game.addChildNode(new Data<>("Step", "1,2"
                , new HashMap<>(Map.of("num", "2", "playerId", "2"))));
        game.addChildNode(new Data<>("Step", "2,2"
                , new HashMap<>(Map.of("num", "3", "playerId", "1"))));
        game.addChildNode(new Data<>("Step", "2,1"
                , new HashMap<>(Map.of("num", "4", "playerId", "2"))));
        game.addChildNode(new Data<>("Step", "3,3"
                , new HashMap<>(Map.of("num", "5", "playerId", "1"))));

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

        File file = new File("history.xml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            outputStream.write(ConvertToXML.tooXml(root).toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
