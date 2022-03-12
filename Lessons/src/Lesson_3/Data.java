/**
 * Class Data
 *
 * @author Melnev Dmitriy
 * @version 2022-
 **/
package Lesson_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data<T extends Data> {
    private final String tagName;
    private HashMap<String, String> attributes = new HashMap<>();
    private final String text;
    private final ArrayList<T> childNodes = new ArrayList<>();
    private Data<T> parent;

    public Data(String tagName, String text) {
        this.tagName = tagName;
        this.text = text;
    }

    public Data(String tagName) {
        this.tagName = tagName;
        this.text = "";
    }

    public Data(String tagName, String text, HashMap<String, String> attributes) {
        this.tagName = tagName;
        this.text = text;
        this.attributes = attributes;
    }

    public T getChildNodeByName(String tag) {
        for (T node : childNodes) {
            if (!node.getTagName().equals(tag)) continue;
            return node;
        }
        return null;
    }

    public ArrayList<T> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(Data<T> childNode) {
        childNodes.add((T) childNode);
    }

    public void addChildNode(String tagName, String text) {
        Data<T> child = new Data<>(tagName, text);
        addChildNode(child);
    }

    public void addChildNode(String tagName) {
        addChildNode(new Data<>(tagName));
    }

    public void addChildNode(String tagName, String text, String... attr) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < attr.length; i += 2) {
            if ((i + 1) >= attr.length) break;
            map.put(attr[i], attr[i + 1]);
        }
        addChildNode(new Data<>(tagName, text, map));
    }


//        addChildNode(new Data<>("Player", "",
//                new HashMap<>(Map.of("id", "1", "name", name1, "symbol", "X"))));

    public String getTagName() {
        return tagName;
    }

    public String getText() {
        return text;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public Data<T> getParent() {
        return parent;
    }

    public void setParent(Data<T> parent) {
        this.parent = parent;
    }
}
