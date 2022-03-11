/**
 * class DataToXML
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_3;

import java.util.ArrayList;
import java.util.HashMap;

public class DataToXML {

    private static final StringBuilder start =
            new StringBuilder("<?xml version=\"1.0\" encoding=\"windows-1251\"?>");

    private static final StringBuilder INDENT = new StringBuilder("    "); //отступ
    private static StringBuilder currentIndent = new StringBuilder();

    public static StringBuilder start(Data root) {
        StringBuilder result = new StringBuilder();
        result.append(start);
        //root
        result.append("\n<" + root.getTagName() + getAttributes(root.getAttributes()) + ">");
        //body
        result.append(scanData(root)); // запускаем рекурсию
        //end root
        result.append("\n</" + root.getTagName() + ">\n");
        return result;
    }

    public static StringBuilder getAttributes(HashMap<String, String> attr) {
        StringBuilder res = new StringBuilder();
        if (attr.size() > 0) {
            attr.forEach((k, v) -> {
                res.append(" " + k + "=\"");
                res.append(v + "\"");
            });
        }
        return res;
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
                if (part.getText().equals("")) {
                    result.append(scanData(part));
                    currentIndent = new StringBuilder(currentIndent.substring(INDENT.length()));//обрезаем отступы
                    result.append("\n" + currentIndent);

                } else {
                    result.append(part.getText());
                }

                result.append("</" + part.getTagName() + ">");
            }
        }
        return result;
    }

}
