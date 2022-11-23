package main.FileConverter.Creation.XML;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XMLAttribute {
    private String name;
    private ArrayList<XMLTypeOfAttack> TypeOfAttacks;

    public XMLAttribute(String name) {
        this.name = name;
        this.TypeOfAttacks = new ArrayList<XMLTypeOfAttack>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addTypeOfAttack(String name) {
        TypeOfAttacks.add(new XMLTypeOfAttack(name));
    }

    public ArrayList<XMLTypeOfAttack> getTypeOfAttacks() {
        return TypeOfAttacks;
    }

    public int returnLength() {
        return TypeOfAttacks.size();
    }

    public void addCharacter(List<String> type, String character, int complexity) {
        Stream<XMLTypeOfAttack> stream = TypeOfAttacks.stream();
        for (String typeOfAttack : type) {

            stream.filter(x -> x.getName().equals(typeOfAttack)).forEach(x -> x.addCharacter(character, complexity));
        }
    }

    public void addToEach(String add) {
        Stream<XMLTypeOfAttack> stream = TypeOfAttacks.stream();

        stream.forEach(x -> x.setName(x.getName() + " " + add));
    }

    public void map() {
        Stream<XMLTypeOfAttack> stream = TypeOfAttacks.stream();
        List<XMLcharacter> list = new ArrayList<>();

        list.stream().filter(c -> c.getComplexity() == 1).map(XMLcharacter::getName).collect(Collectors.toList());
    }

    public void getSorted() {
        Stream<XMLTypeOfAttack> stream = TypeOfAttacks.stream();

        stream.sorted(Comparator.comparing(XMLTypeOfAttack::getName)).collect(Collectors.toList());
    }

    public void filterType() {
        Stream<XMLTypeOfAttack> stream = TypeOfAttacks.stream();

        stream.filter(x->x.getName()=="Melee").forEach(System.out::println);

    }

}
