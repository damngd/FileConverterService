package main.FileConverter.Creation.JSON;

import java.util.ArrayList;

public class JSON {
    private ArrayList<JSONcharacter> characters;

    public JSON() {
        this.characters = new ArrayList<>();
    }

    public ArrayList<JSONcharacter> getCharacters() {
        return characters;
    }

    public void addCharacter(String name, int complexity, String Attribute){
        characters.add(new JSONcharacter(name,complexity,Attribute));
    }

    public int returnLength(){
        return characters.size();
    }
    public JSONcharacter returnLastCharacter() {
        if (characters.size() > 0)
            return characters.get(characters.size() - 1);
        else
            return null;
    }

}
