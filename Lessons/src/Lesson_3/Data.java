/**
 * Class Data
 *
 * @author Melnev Dmitriy
 * @version 2022-
 **/
package Lesson_3;

import java.util.ArrayList;
import java.util.HashMap;

public class Data<T extends Data> {
    private String tagName;
    private HashMap<String, String> attributes = new HashMap<>();
    private String text;
    private ArrayList<T> childNodes = new ArrayList<>();
    private Data<T> parent;

    public Data(String tagName, String text) {
        this.tagName = tagName;
        this.text = text;
    }

    public Data(String tagName, String text, HashMap<String, String> attributes) {
        this.tagName = tagName;
        this.text = text;
        this.attributes = attributes;
    }

    public T getChildNodeByName(String tag) {
        for (T node : childNodes) {
            if (!node.getTagName().equals(tag)) continue;
            return (T) node;
        }
        return null;
    }

    public ArrayList<T> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(Data<T> childNode) {
        childNodes.add((T) childNode);
    }

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
