package Lesson_4.Parser;

import Lesson_4.Data.Data;
//import Lesson_4.Data.Encoding;
/**
 * interface Parser
 *
 * @author Melnev Dmitry
 * @version 2022
 */
public interface MyParser {

    Data stringToData(String string);
    String dataToString(Data root);
//    void setCharSet(Encoding charSet);

}
