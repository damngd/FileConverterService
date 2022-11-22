package main.FileConverter.Creation.XML;

import java.util.ArrayList;

public class XMLAttribute {
    private String name;
    private ArrayList<XMLTypeOfAttack> TypeOfAttacks;

    public XMLAttribute(String name) {
        this.name = name;
        this.TypeOfAttacks = new ArrayList<XMLTypeOfAttack>();
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addTypeOfAttack(String name){
        TypeOfAttacks.add(new XMLTypeOfAttack(name));
    }

    public ArrayList<XMLTypeOfAttack> getTypeOfAttacks(){
        return TypeOfAttacks;
    }

    public int returnLength(){
        return TypeOfAttacks.size();
    }
}
