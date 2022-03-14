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
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Test {
    public static void main(String[] args) {

        try {
            Data root2 = ParserXML.dataFromFile("victim.xml");

            String fileName2 = "result.xml";
            File file2 = new File(fileName2);

            if (!file2.exists()) {
                file2.createNewFile();
            }


            try (FileOutputStream outputStream = new FileOutputStream(file2);//){
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "windows-1251")) {
                outputStreamWriter.write(ParserXML.dataToXML(root2).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Data root = new Data("GamePlay");
            Game game = new Game(root);

            game.player("victim.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}