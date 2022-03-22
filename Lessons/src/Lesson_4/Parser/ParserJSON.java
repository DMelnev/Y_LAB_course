/**
 * class ParserJSON
 *
 * @author Melnev Dmitry
 * @version 2022
 */
package Lesson_4.Parser;

import Lesson_4.Data.Data;
import Lesson_4.Data.Encoding;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.HashMap;

public class ParserJSON implements MyParser {

    private Encoding charSet = Encoding.UTF8;
    private static final StringBuilder INDENT = new StringBuilder("    "); //отступ


    @Override
    public Data stringToData(String string) {
//        Data data = new Data("GamePlay");
//        if (string.equals("")) return null;

//        List<Map<String, Object>> res = (List<Map<String, Object>>) JsonPath.parse(string);

//        res.forEach(System.out::println);


        return null;
    }

    @Override
    public String dataToString(Data root) {
        JSONObject result = new JSONObject();
        JSONArray main = new JSONArray();

        for (Data mainNode : root.getChildNodes()) {
            if (mainNode.getTagName().equals("Player")) {
                JSONObject player = new JSONObject();
                player.put("Player", getAttributes(mainNode.getAttributes()));
                main.add(player);
            }
            if (mainNode.getTagName().equals("Game")) {
                JSONObject game = new JSONObject();
                JSONArray list = new JSONArray();
                JSONObject attributes = new JSONObject();
                JSONObject steps = new JSONObject();
                JSONArray stp = new JSONArray();

                attributes.put("Attr", getAttributes(mainNode.getAttributes()));

                for (Data step : mainNode.getChildNodes()) {
                    JSONObject stepJ = new JSONObject();
                    JSONObject tmp = getAttributes(step.getAttributes());
                    if (step.getTagName().equals("GameResult")) {
                        if (step.getText().equals("Draw!")) {
                            stepJ.put("GameResult", "Draw!");
                        } else {
                            JSONObject playerWin = getAttributes(step.getChildNodeByName("Player").getAttributes());
                            JSONObject playerWinM = new JSONObject();
                            playerWinM.put("Player", playerWin);
                            stepJ.put("GameResult", playerWinM);
                        }
                    } else {
                        tmp.put("Coordinate", step.getText());
                        stepJ.put("Step", tmp);
                    }
                    stp.add(stepJ);
                }

                steps.put("Steps", stp);
                list.add(attributes);
                list.add(steps);
                game.put("Game", list);
                main.add(game);
            }
        }

        result.put(root.getTagName(), main);
        return formatString(result.toJSONString());
    }

    @Override
    public void setCharSet(Encoding charSet) {
        this.charSet = charSet;
    }

    private JSONObject getAttributes(HashMap<String, String> attr) {
        JSONObject res = new JSONObject();
        if (attr.size() > 0) {
            attr.forEach((k, v) -> res.put(k, v));
        }
        return res;
    }

    private String formatString(String string) {
        StringBuilder temp = new StringBuilder(string);
        StringBuilder tempIndent = new StringBuilder();

        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '{') {
                temp.insert(i + 1, "\n" + tempIndent);
                if (i != 0) temp.insert(i, "\n" + tempIndent);
                i += tempIndent.length() + 1;
                tempIndent = tempIndent.append(INDENT);
            }
            if (temp.charAt(i) == '}' && i < (temp.length() - 1)) {
                tempIndent = new StringBuilder(tempIndent.substring(INDENT.length()));
                if (i > 0) temp.insert(i, "\n" + tempIndent);
                i += tempIndent.length() + ((temp.charAt(i + 1) == ',') ? 2 : 1);
            }
            if (i > 0 && temp.charAt(i) == ',' && (temp.charAt(i - 1) == '"' || temp.charAt(i - 1) == ']')) {
                temp.insert(i + 1, "\n" + tempIndent.substring(INDENT.length()));
            }
        }
        return temp.toString();
    }
}
