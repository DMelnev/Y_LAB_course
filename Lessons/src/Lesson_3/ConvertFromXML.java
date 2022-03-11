/**
 * class ConvertFromXML
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class ConvertFromXML {
    private static String FILE;
    private static Data result;

    public ConvertFromXML(String file) {
        FILE = file;
    }

    public static Data<Data> start(String file) throws Exception {
        Scanner scanner = new Scanner(new File(file));
        String rootName = null;
        Data<Data> prev = null;
        Data<Data> current = null;

        while (scanner.hasNext()) {
            String nextLine = correctSpaces(scanner.nextLine()).trim();
            if (nextLine.equals("")) continue;
            String[] data = nextLine.split(" ");

            if (data[0].toLowerCase(Locale.ROOT).equals("<?xml")) {  // первая строка
                nextLine = correctSpaces(scanner.nextLine()).trim();
                data = nextLine.split(" ");
                rootName = data[0].substring(1, data[0].length() - 1); //забираем root name
                result = new Data(rootName, "");
                current = result;
                continue;
            }
            if (current == null) {
                throw new Exception("File is not correct");
            }

            if (data.length > 1) {
                int max = data.length - 1;
                String name = data[0].replace("<", "");
                HashMap<String, String> map = new HashMap<>();
                String text = "";

                if (data[(max)].contains("/>")) {//вариант 1
                    for (int i = 1; i <= max; i++) {
                        String[] attrData = data[i].split("=");
                        if (attrData.length != 2) {
                            throw new IndexOutOfBoundsException("File is not correct");
                        }
                        String second = attrData[1].replace("\"", "").replace("/>", "");
                        map.put(attrData[0], second);
                    }
                    current.addChildNode(new Data<>(name, text, map));
                    continue;
                }

                if (data[(max)].contains("</")) {//вариант 2
                    int j = 1;
                    while (j < max) {
                        String[] attrData = data[j].split("=");
                        if (attrData.length != 2) {
                            throw new IndexOutOfBoundsException("File is not correct");
                        }
                        String second = attrData[1].replace("\"", "").replace(">", "");
                        map.put(attrData[0], second);
                        if (data[j].contains(">")) break;
                        j++;
                    }
                    if (max - j == 2) text = data[max - 1];
                    current.addChildNode(new Data<>(name, text, map));
                    continue;
                }

                if (data[(max)].contains("\">")) {//вариант 3
                    for (int i = 1; i <= max; i++) {
                        String[] attrData = data[i].split("=");
                        if (attrData.length != 2) {
                            throw new IndexOutOfBoundsException("File is not correct");
                        }
                        String second = attrData[1].replace("\"", "").replace(">", "");
                        map.put(attrData[0], second);
                    }
                    prev = current;
                    current = new Data<>(name, text, map);
                    prev.addChildNode(current);
                    current.setParent(prev);
                    continue;
                }

            } else {//если всего один тэг
                if (data[0].startsWith("</")) { //поднимаемся вверх
                    current = current.getParent();
                    continue;
                }
                if (data[0].endsWith("/>")) {
                    current.addChildNode(new Data<>(data[0].substring(1, data[0].length() - 2), ""));//новый тег без спуска
                    continue;
                }
                if (data[0].startsWith("<") && data[0].endsWith(">") && !data[0].contains("/")) { //новый тэг и спускаемся в него
                    prev = current;
                    current = new Data<>(data[0].substring(1, data[0].length() - 1), "");
                    current.setParent(prev);
                    prev.addChildNode(current);
                    continue;
                }
            }
            throw new Exception("File is not correct");
        }
        return result;
    }

    private static String correctSpaces(String string) {

        string = string.replace(" />", "/>");
        string = string.replace("</ ", "</");
        string = string.replace("< ", "<");
        string = string.replace("<", " <");
        string = string.replace(" >", ">");
        string = string.replace(">", "> ");

        while (string.contains("  ")) {
            string = string.replace("  ", " ");
        }

        return string;
    }
}
