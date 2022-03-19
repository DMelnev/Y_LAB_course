/**
 * class ParserXML
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_4.Parser;

import Lesson_4.Data.Data;
import Lesson_4.Data.Encoding;

import java.io.*;
import java.util.*;

/**
 * class ParserXML
 *
 * @author Melnev Dmitry
 * @version 2022
 */
public class ParserXML implements MyParser {

    private static Data result;
    private Encoding charSet = Encoding.UTF8;

    private static final StringBuilder START =
            new StringBuilder("<?xml version=\"1.0\" encoding=\"");

    private static final StringBuilder INDENT = new StringBuilder("    "); //отступ
    private static StringBuilder currentIndent = new StringBuilder();


    @Override
    public Data stringToData(StringBuilder string) {

        Scanner scanner = new Scanner(correctFile(string));
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
                System.out.println("File is not correct");
                return null;
            }

            if (data.length > 1) {//************************************************
                int max = data.length - 1;
                String name = data[0].replace("<", "").replace(">", "");
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
                    if (max != 2) {
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
                    }
                    if ((max - j == 2) || (max == 2)) text = data[max - 1];
                    current.addChildNode(new Data(name, text, map));
                    continue;
                }

                if (data[(max)].contains("\">")) {//************************************************
                    for (int i = 1; i <= max; i++) {
                        String[] attrData = data[i].split("=");
                        if (attrData.length != 2) {
                            throw new IndexOutOfBoundsException("File is not correct");
                        }
                        String second = attrData[1]
                                .replace("\"", "")
                                .replace(">", "");
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
            System.out.println("File is not correct");
            return null;
        }
        return result;
    }

    @Override
    public StringBuilder dataToString(Data root) {
        StringBuilder result = new StringBuilder();
        currentIndent = new StringBuilder();
        result.append(START).append(charSet.getCharset()).append("\"?>");
        //root
        result.append("\n<")
                .append(root.getTagName())
                .append(getAttributes(root.getAttributes()))
                .append(">");
        //body
        result.append(scanData(root)); // запускаем рекурсию
        //end root
        result.append("\n</").append(root.getTagName()).append(">\n");
        return result;
    }

    private static StringBuilder scanData(Data root) {
        currentIndent.append(INDENT); // добавляем отступы
        StringBuilder result = new StringBuilder();
        ArrayList<Data> list = root.getChildNodes();

        for (Data node : list) {
            result.append("\n")
                    .append(currentIndent)
                    .append("<")
                    .append(node.getTagName()) //имя тега
                    .append(getAttributes(node.getAttributes())); //атрибуты если есть

            if ((node.getText().equals("")) && (node.getChildNodes().size() <= 0)) { // если нет текста и тег одинок )
                result.append("/>");
            } else {
                result.append(">"); //
                if (node.getText().equals("")) { //если нет текста
                    result.append(scanData(node)); // забираем детей в рекурсии
                    currentIndent = new StringBuilder(currentIndent.substring(INDENT.length()));//обрезаем отступы
                    result.append("\n").append(currentIndent); //отступ с новой строки

                } else {
                    result.append(node.getText()); // текст есть, значит нет детей
                }

                result.append("</")
                        .append(node.getTagName()) //закрывающий тег
                        .append(">");
            }
        }
        return result;
    }

    private static StringBuilder getAttributes(HashMap<String, String> attr) {
        StringBuilder res = new StringBuilder();
        if (attr.size() > 0) {
            attr.forEach((k, v) ->
                    res.append(" ")
                            .append(k)
                            .append("=\"")
                            .append(v)
                            .append("\""));
        }
        return res;
    }

    private static String correctSpaces(String line) {
        while (line.contains("  ")) {
            line = line.replace("  ", " ");
        }
        line = line.replaceAll(" />", "/>");
        line = line.replaceAll("</ ", "</");
        line = line.replaceAll("< ", "<");
        line = line.replaceAll("<", " <");
        line = line.replaceAll(" >", ">");
        line = line.replaceAll(">", "> ");
        return line;
    }

    private static String correctFile(StringBuilder file) {
        String str = String.valueOf(file);
        str = str.replaceAll("\n", ""); // удаляем все переносы строк
        str = str.replaceAll("<!--.*?-->", ""); // удаляем комментарии

        while (str.contains("> ")) {    // вычищаем пробелы между тегами
            str = str.replace("> ", ">");
        }
        while (str.contains(" <")) {    // вычищаем пробелы между тегами, не имеет смысла, но на всякий
            str = str.replace(" <", "<");
        }

        str = str.replaceAll("><", ">\n<"); // рисуем переносы
        return str;
    }

    @Override
    public void setCharSet(Encoding charSet) {
        this.charSet = charSet;
    }
}
