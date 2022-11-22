package main.FileConverter.Creation.XML;
import java.util.ArrayList;
public class XML {
    private ArrayList<XMLAttribute> attributes;

    public XML(){
        attributes = new ArrayList<XMLAttribute>();
    }

    public void addAttribute(String name){
        attributes.add(new XMLAttribute(name));
    }

    public ArrayList<XMLAttribute> getAttributes(){
        return attributes;
    }

    public int returnLength(){
        return attributes.size();
    }
}
