package main.FileConverter;

import main.FileConverter.Creation.XML.XML;
import main.FileConverter.Creation.XML.XMLAttribute;
import main.FileConverter.Creation.XML.XMLTypeOfAttack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileConverter {
    public void convertToJson(String pathToXML, String pathToNewFile) {
        try {
            XML_TO_JSON.parseXML(pathToXML);
            XML_TO_JSON.createJSON(XML_TO_JSON.convert(), pathToNewFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void convertToXML(String pathToJSON, String pathToNewFile)  {
        try {
            JSON_TO_XML.parseJSON(pathToJSON);
            JSON_TO_XML.createXML(JSON_TO_XML.convert(), pathToNewFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public void ST(){
        XML w = XML_TO_JSON.getDota();
        for(XMLAttribute attribute : w.getAttributes()){

            List<String> type = new ArrayList<>();
            type.add("RangeAndMelee");
            attribute.addCharacter(type,"Troll Warlord", 1);
            attribute.map();

        }

    }*/




}

