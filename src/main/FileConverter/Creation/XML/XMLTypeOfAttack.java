package main.FileConverter.Creation.XML;
import java.util.ArrayList;
public class XMLTypeOfAttack {
    private String name;
    private ArrayList<XMLcharacter> characters;

    public XMLTypeOfAttack(String name) {
        this.name = name;
        characters = new ArrayList<XMLcharacter>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<XMLcharacter> getCharacters() {
        return characters;
    }

    public void addGame(String name, int complexity){
        characters.add(new XMLcharacter(name, complexity));
    }

    public int returnLength(){
        return characters.size();
    }
}
