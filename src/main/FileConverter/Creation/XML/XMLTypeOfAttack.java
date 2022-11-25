package main.FileConverter.Creation.XML;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    public int returnLength(){
        return characters.size();
    }

    public void addCharacter(String character, int complexity) {
        characters.add(new XMLcharacter(character,complexity));
    }

    public void map() {
        Stream<XMLcharacter> stream = characters.stream();
        stream.filter(c -> c.getComplexity() == 1).map(XMLcharacter::getName).collect(Collectors.toList());
    }
    public void filterType() {
        Stream<XMLcharacter> stream = characters.stream();

        stream.filter(x->x.getRoles().equals("Nuker")).forEach(System.out::println);

    }
}
