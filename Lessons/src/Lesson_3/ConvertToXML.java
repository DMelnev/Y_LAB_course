/**
 * class DataToXML
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_3;

import java.util.ArrayList;
import java.util.HashMap;

public class ConvertToXML {
    private static final StringBuilder result = new StringBuilder();
    private static final StringBuilder begin =
            new StringBuilder("<?xml version=\"1.0\" encoding=\"windows-1251\"?>");
    private static final StringBuilder INDENT = new StringBuilder("    ");
    private static StringBuilder currentIndent = new StringBuilder();

    public static StringBuilder tooXml(Data data) {
        StringBuilder result = new StringBuilder();
        result.append(begin); //по идее нужно тоже собрать, но пока так

        //root
        result.append("\n<" + data.getTagName() + getAttributes(data.getAttributes()) + ">");

        //body
        result.append(scanData(data)); // запускаем рекурсию

        //end root
        result.append("\n</" + data.getTagName() + ">\n");

        return result;
    }

    public static StringBuilder getAttributes(HashMap<String, String> map) {
        StringBuilder result = new StringBuilder();
        if (map.size() > 0) {
            map.forEach((k, v) -> {
                result.append(" " + k + "=\"");
                result.append(v + "\"");
            });
        }
        return result;
    }

    public static StringBuilder scanData(Data data) {
        currentIndent.append(INDENT); // добавляем отступы
        StringBuilder result = new StringBuilder();
        ArrayList<Data> list = data.getChildNodes(); //не получилось впихнуть сразу в foreach(((

        for (Data part : list) {

            result.append("\n" + currentIndent + "<" + part.getTagName());
            result.append(getAttributes(part.getAttributes()));

            if ((part.getText().equals("")) && (part.getChildNodes().size() <= 0)) {
                result.append("/>");
            } else {
                result.append(">");
                boolean flag = false;
                if (part.getText().equals("")) {
                    result.append(scanData(part));
                    flag = true;
                } else {
                    result.append(part.getText());
                }
                if (flag) {
                    currentIndent = new StringBuilder(currentIndent.substring(INDENT.length()));//обрезаем отступы
                    result.append("\n" + currentIndent);
                }
                result.append("</" + part.getTagName() + ">");
            }
        }
        return result;
    }

}
