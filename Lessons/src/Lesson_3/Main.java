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
        Data<Data> root = new Data<>("Gameplay", "");
        root.addChildNode(new Data<>("Player", "",new HashMap<>(Map.of("id","1", "name", "Igor", "symbol", "X"))));
        root.addChildNode(new Data<>("Player", "",new HashMap<>(Map.of("id","2", "name", "Anna", "symbol", "X"))));

        root.addChildNode(new Data<>("Game",""));
Data<T> game = root.


    }
}
