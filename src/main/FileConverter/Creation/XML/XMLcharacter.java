package main.FileConverter.Creation.XML;

import java.util.ArrayList;

public class XMLcharacter {
    private String name;
    private int complexity;
    private ArrayList<XMLrole> roles;

    public XMLcharacter(String name, int complexity) {
        this.name = name;
        this.complexity = complexity;
        this.roles = new ArrayList<>();
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public int getComplexity(){
        return complexity;
    }

    public ArrayList<XMLrole> getRoles() {
        return roles;
    }

    public void addRole(String name){
        roles.add(new XMLrole(name));
    }

    public int returnLength(){
        return roles.size();
    }
}
