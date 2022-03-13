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

public class Test {
    public static void main(String[] args){

        try {
            Data root2 = ParserXML.dataFromFile("victim.xml");

            String fileName2 = "result.xml";
            File file2 = new File(fileName2);

            if (!file2.exists()) {
                file2.createNewFile();
            }

            try (FileOutputStream outputStream = new FileOutputStream(file2)) {
                outputStream.write(ParserXML.dataToXML(root2).toString().getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}