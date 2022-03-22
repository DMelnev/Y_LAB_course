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
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParserJSON implements MyParser {

    private Encoding charSet = Encoding.UTF8;
    private static final StringBuilder INDENT = new StringBuilder("    "); //отступ


    @Override
    public Data stringToData(String string) {
        JSONParser parser = new JSONParser();
        Data data = null;
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(string);
            JSONArray gamePlay = (JSONArray) jsonObject.get("GamePlay");
//            System.out.println(gamePlay);
            data = new Data("GamePlay");

            for (Object o : gamePlay) {
                JSONObject step = (JSONObject) o;

                if (step.toString().startsWith("{\"Player\"")) {
                    JSONObject www = (JSONObject) step.get("Player");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("symbol", (String) www.get("symbol"));
                    map.put("name", (String) www.get("name"));
                    map.put("id", (String) www.get("id"));
                    Data player = new Data("Player", "", map);
                    player.setParent(data);
                    data.addChildNode(player);
                }
                if (step.toString().startsWith("{\"Game\"")) {
                    JSONArray gameJ = (JSONArray) step.get("Game");
                    Data game = null;
                    for( Object ob : gameJ){
                        JSONObject stp = (JSONObject) ob;

                        if (stp.toString().startsWith("{\"Attr\"")) {
                            JSONObject www = (JSONObject) stp.get("Attr");
                            HashMap<String, String> map = new HashMap<>();
                            map.put("set", (String) www.get("set"));
                            map.put("map", (String) www.get("map"));
                            game = new Data("Game", "", map);
                            data.addChildNode(game);
                        }

                        if (stp.toString().startsWith("{\"Steps\"")) {
                            JSONArray stepsJ = (JSONArray) stp.get("Steps");
                            for( Object os : stepsJ) {
                                JSONObject stepJ = (JSONObject) os;
                                if (stepJ.toString().startsWith("{\"Step\"")) {
                                    JSONObject www = (JSONObject) stepJ.get("Step");
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("Coordinate", (String) www.get("Coordinate"));
                                    map.put("num", (String) www.get("num"));
                                    map.put("playerId", (String) www.get("playerId"));
                                    Data stepD = new Data("Step","",map);
                                    stepD.setParent(game);
                                    game.addChildNode(stepD);
                                }
                                if (stepJ.toString().startsWith("{\"GameResult\"")) {
                                    if (stepJ.get("GameResult").equals("Draw!")){
                                        Data stepR = new Data("GameResult","Draw!");
                                        stepR.setParent(game);
                                        game.addChildNode(stepR);
                                    }else{
                                        JSONObject gameResult = (JSONObject) stepJ.get("GameResult");
                                        JSONObject player = (JSONObject) gameResult.get("Player");
                                        HashMap<String, String> map = new HashMap<>();
                                        map.put("symbol", (String) player.get("symbol"));
                                        map.put("name", (String) player.get("name"));
                                        map.put("id", (String) player.get("id"));
                                        Data gr = new Data("GameResult", "");
                                        gr.setParent(game);
                                        game.addChildNode(gr);
                                        Data stepR = new Data("Player","", map);
                                        stepR.setParent(gr);
                                        gr.addChildNode(stepR);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return data;
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
            switch (temp.charAt(i)) {
                case '{' -> {
                    temp.insert(i + 1, "\n" + tempIndent);
                    if (i != 0) temp.insert(i, "\n" + tempIndent);
                    i += tempIndent.length() + 1;
                    tempIndent = tempIndent.append(INDENT);
                }
                case '}' -> {
                    if (i < (temp.length() - 1)) {
                        tempIndent = new StringBuilder(tempIndent.substring(INDENT.length()));
                        if (i > 0) temp.insert(i, "\n" + tempIndent);
                        i += tempIndent.length() + ((temp.charAt(i + 1) == ',') ? 2 : 1);
                    }
                }
                case ',' -> {
                    if ((temp.charAt(i - 1) == '"' || temp.charAt(i - 1) == ']')) {
                        temp.insert(i + 1, "\n" + tempIndent.substring(INDENT.length()));
                    }
                }
                case ']' -> {
                    temp.insert(i, "\n" + tempIndent);
                    i += tempIndent.length() + 1;
                }
            }

        }
        return temp.toString();
    }
}
