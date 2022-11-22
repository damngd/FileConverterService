package main.FileConverter.Creation.JSON;

import java.util.ArrayList;

public class JSONcharacter {
    private String name;
    private int complexity;
    private String Attribute;
    private ArrayList<JSONrole> roles;
    private ArrayList<JSONTypeOfAttack> TypeOfAttacks;

    public JSONcharacter(String name, int complexity, String Attribute) {
        this.name = name;
        this.complexity = complexity;
        this.Attribute = Attribute;
        this.roles = new ArrayList<JSONrole>();
        this.TypeOfAttacks = new ArrayList<JSONTypeOfAttack>();
    }

    public String getName() {
        return name;
    }

    public int getComplexity() {
        return complexity;
    }

    public String getAttribute() {
        return Attribute;
    }

    public ArrayList<JSONrole> getRoles() {
        return roles;
    }

    public ArrayList<JSONTypeOfAttack> getTypeOfAttacks() {
        return TypeOfAttacks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public void setAttribute(String Attribute) {
        this.Attribute = Attribute;
    }

    public void addRole(String name){
        roles.add(new JSONrole(name));
    }
    public void addTypeOfAttack(String name){
        TypeOfAttacks.add(new JSONTypeOfAttack(name));
    }

}
