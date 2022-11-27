package main.FileConverter.Creation.JSON;

public class JSONTypeOfAttack {
    private String name;
    private String name2;

    public JSONTypeOfAttack(String name, String name2) {
        this.name = name;
        this.name2 = name2;

    }

    public String getName() {
        return name;
    }
    public String getName2(){return name2;}


    public void setName(String name) {
        this.name = name;
    }
    public void setName2(String name2){
        this.name2 = name2;
    }
}
