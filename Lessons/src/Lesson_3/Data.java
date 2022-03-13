package Lesson_3;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class Data
 *
 * @author Melnev Dmitriy
 * @version 2022-
 **/
public class Data {

    private final String tagName;
    private HashMap<String, String> attributes = new HashMap<>();
    private final String text;
    private final ArrayList<Data> childNodes = new ArrayList<>();
    private Data parent;

    public Data(String tagName, String text) {
        this.tagName = tagName;
        this.text = text;
    }

    public Data(String tagName) {
        this(tagName, "");
    }

    public Data(String tagName, String text, HashMap<String, String> attributes) {
        this(tagName, text);
        this.attributes = attributes;
    }
    public Data(String tagName, String text, String... attr) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < attr.length; i += 2) {
            if ((i + 1) >= attr.length) break;
            map.put(attr[i], attr[i + 1]);
        }
        this.tagName = tagName;
        this.text = text;
        this.attributes = map;
    }
    public Data getChildNodeByName(String tag) {
        for (Data node : childNodes) {
            if (!node.getTagName().equals(tag)) continue;
            return node;
        }
        return null;
    }

    public String getAttrByName(String name){
        return attributes.get(name);
    }

    public ArrayList<Data> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(Data childNode) {
        childNodes.add(childNode);
    }

    public void addChildNode(String tagName, String text) {
        Data child = new Data(tagName, text);
        addChildNode(child);
    }

    public void addChildNode(String tagName, String text, String... attr) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < attr.length; i += 2) {
            if ((i + 1) >= attr.length) break;
            map.put(attr[i], attr[i + 1]);
        }
        addChildNode(new Data(tagName, text, map));
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

    public Data getParent() {
        return parent;
    }

    public void setParent(Data parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Data{" +
                "tagName='" + tagName + '\'' +
                ", attributes=" + attributes +
                ", text='" + text + '\'' +
                '}';
    }
}
