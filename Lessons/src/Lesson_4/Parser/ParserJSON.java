/**
 * class ParserJSON
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_4.Parser;

import Lesson_4.Data.Data;
import Lesson_4.Data.Encoding;
import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;


public class ParserJSON implements MyParser {

    private Encoding charSet = Encoding.UTF8;


    @Override
    public Data stringToData(String string) {
        Data data = new Data("GamePlay");
        if (string.equals("")) return null;

        List<Map<String, Object>> res = (List<Map<String, Object>>) JsonPath.parse(string);

        res.forEach(System.out::println);



        return null;
    }

    @Override
    public String dataToString(Data root) {
        return null;
    }

    @Override
    public void setCharSet(Encoding charSet) {
        this.charSet = charSet;
    }
}
