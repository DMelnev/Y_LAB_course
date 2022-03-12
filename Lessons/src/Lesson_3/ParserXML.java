package Lesson_3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * class ConvertFromXML
 *
 * @author Melnev Dmitry
 * @version 2022
 */
public class ParserXML {

    private static Data result;

    private static final StringBuilder START =
            new StringBuilder("<?xml version=\"1.0\" encoding=\"windows-1251\"?>");

    private static final StringBuilder INDENT = new StringBuilder("    "); //отступ
    private static StringBuilder currentIndent = new StringBuilder();

    public static Data dataFromFile(String file) throws Exception {
        Scanner scanner = new Scanner(new File(file));
        Data prev;
        Data current = null;

        while (scanner.hasNext()) {
            String nextLine = correctSpaces(scanner.nextLine()).trim();
            if (nextLine.equals("")) continue;
            String[] data = nextLine.split(" ");

            if (data[0].toLowerCase(Locale.ROOT).equals("<?xml")) {  // первая строка
                nextLine = correctSpaces(scanner.nextLine()).trim();
                data = nextLine.split(" ");
                String rootName = data[0].substring(1, data[0].length() - 1); //забираем root name
                result = new Data(rootName, "");
                current = result;
                continue;
            }
            if (current == null) {
                throw new Exception("File is not correct");
            }

            if (data.length > 1) {//************************************************
                int max = data.length - 1;
                String name = data[0].replace("<", "");
                HashMap<String, String> map = new HashMap<>();
                String text = "";

                if (data[(max)].contains("/>")) {//************************************************
                    for (int i = 1; i <= max; i++) {
                        String[] attrData = data[i].split("=");
                        if (attrData.length != 2) {
                            throw new IndexOutOfBoundsException("File is not correct");
                        }
                        String second = attrData[1].replace("\"", "").replace("/>", "");
                        map.put(attrData[0], second);
                    }
                    current.addChildNode(new Data(name, text, map));
                    continue;
                }

                if (data[(max)].contains("</")) {//************************************************
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
                    current.addChildNode(new Data(name, text, map));
                    continue;
                }

                if (data[(max)].contains("\">")) {//************************************************
                    for (int i = 1; i <= max; i++) {
                        String[] attrData = data[i].split("=");
                        if (attrData.length != 2) {
                            throw new IndexOutOfBoundsException("File is not correct");
                        }
                        String second = attrData[1].replace("\"", "").replace(">", "");
                        map.put(attrData[0], second);
                    }
                    prev = current;
                    current = new Data(name, text, map);
                    prev.addChildNode(current);
                    current.setParent(prev);
                    continue;
                }

            } else {//************************************************
                if (data[0].startsWith("</")) { //поднимаемся вверх
                    current = current.getParent();
                    continue;
                }
                if (data[0].endsWith("/>")) {//новый тег без спуска
                    current.addChildNode(new Data(data[0].substring(1, data[0].length() - 2), ""));
                    continue;
                }
                //новый тэг и спускаемся в него
                if (data[0].startsWith("<") && data[0].endsWith(">") && !data[0].contains("/")) {
                    prev = current;
                    current = new Data(data[0].substring(1, data[0].length() - 1), "");
                    current.setParent(prev);
                    prev.addChildNode(current);
                    continue;
                }
            }
            throw new Exception("File is not correct");
        }
        return result;
    }

    public static StringBuilder dataToXML(Data root) {
        StringBuilder result = new StringBuilder();
        currentIndent = new StringBuilder();
        result.append(START);
        //root
        result.append("\n<").append(root.getTagName()).append(getAttributes(root.getAttributes())).append(">");
        //body
        result.append(scanData(root)); // запускаем рекурсию
        //end root
        result.append("\n</").append(root.getTagName()).append(">\n");
        return result;
    }

    private static StringBuilder scanData(Data data) {
        currentIndent.append(INDENT); // добавляем отступы
        StringBuilder result = new StringBuilder();
        ArrayList<Data> list = data.getChildNodes();

        for (Data part : list) {

            result.append("\n").append(currentIndent).append("<").append(part.getTagName());
            result.append(getAttributes(part.getAttributes()));

            if ((part.getText().equals("")) && (part.getChildNodes().size() <= 0)) {
                result.append("/>");
            } else {
                result.append(">");
                if (part.getText().equals("")) {
                    result.append(scanData(part));
                    currentIndent = new StringBuilder(currentIndent.substring(INDENT.length()));//обрезаем отступы
                    result.append("\n").append(currentIndent);

                } else {
                    result.append(part.getText());
                }

                result.append("</").append(part.getTagName()).append(">");
            }
        }
        return result;
    }

    private static StringBuilder getAttributes(HashMap<String, String> attr) {
        StringBuilder res = new StringBuilder();
        if (attr.size() > 0) {
            attr.forEach((k, v) ->
                    res.append(" ").append(k).append("=\"").append(v).append("\""));
        }
        return res;
    }

    private static String correctSpaces(String line) {

        line = line.replace(" />", "/>");
        line = line.replace("</ ", "</");
        line = line.replace("< ", "<");
        line = line.replace("<", " <");
        line = line.replace(" >", ">");
        line = line.replace(">", "> ");

        while (line.contains("  ")) {
            line = line.replace("  ", " ");
        }
        return line;
    }
}
