package main.FileConverter.Creation.XML;
import main.FileConverter.XML_TO_JSON;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XMLTypeOfAttack {
    private String name;
    private String name2;
    private ArrayList<XMLcharacter> characters;

    public XMLTypeOfAttack(String name, String name2) {
        this.name = name;
        this.name2 = name2;
        characters = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getName2(){return name2;}

    public ArrayList<XMLcharacter> getCharacters() {
        return characters;
    }


    public int returnLength(){
        return characters.size();
    }

    public void addCharacter(String character, int complexity) {
        characters.add(new XMLcharacter(character,complexity));
    }


    public void filterType() {
       Stream<XMLcharacter> stream = characters.stream();

        stream.filter(x->x.getRoles().equals("Nuker")).map(XMLcharacter::getName).forEach(System.out::println);

    }

    public void sortNameLength(){
        Stream<XMLcharacter> list = characters.stream();
        list.filter(x->x.getName().length()>5).sorted().collect(Collectors.toList());
    }

    public void sortedName(){
        Stream<XMLcharacter> list = characters.stream();
        list.sorted().forEach(System.out::println);
    }

    public void collectRoles(){
        Stream<XMLcharacter> list = characters.stream();
        list.filter(x->x.getComplexity()==2).collect(Collectors.toList());
    }


}
