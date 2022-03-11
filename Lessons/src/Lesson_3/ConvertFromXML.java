/**
 * class ConvertFromXML
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class ConvertFromXML {
    private String FILE;
    Data result;

    public ConvertFromXML(String file) {
        FILE = file;
    }

    public void readFile(String file) throws Exception {
        Scanner scanner = new Scanner(new File(file));
        String rootName = null;
        String endRoot = null;
        Data<Data> prev = null;
        Data<Data> current = null;

        while (scanner.hasNext()) {
            String nextLine = scanner.nextLine().trim();
            String[] data = nextLine.split(" ");

            if (data[0].toLowerCase(Locale.ROOT).equals("<?xml")) {  // первая строка
                nextLine = scanner.nextLine().trim();
                data = nextLine.split(" ");
                rootName = data[0].substring(1, data[0].length() - 1); //забираем root name
                endRoot = "</" + rootName + ">";
                result = new Data(rootName, "");
                current = result;
                continue;
            }
            if (current == null) {
                throw new Exception("File is not correct");
            }

            if (data.length > 1) {



            } else {
                if (data[0].startsWith("</")) {
//                    current = prev;

                    continue;
                }
                if (data[0].endsWith("/>")) {
                    continue;
                }
                if (!data[0].contains("/")){

                    System.out.println(data[0]);
                }
            }


        }
    }
}
