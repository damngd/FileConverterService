package main.FileConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.FileConverter.Creation.JSON.JSON;
import main.FileConverter.Creation.JSON.JSONcharacter;
import main.FileConverter.Creation.XML.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XML_TO_JSON extends DefaultHandler {
    private static final XML DOTA = new XML();

    public static XML getDota() {
        return DOTA;
    }

    public static XML parseXML(String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        File fl = new File(path);
        parser.parse(fl, handler);
        return DOTA;
    }

    public static JSON convert(){
        JSON jsonCharacters = new JSON();

        for (int i = 0; i< DOTA.returnLength(); i++) {
            //get current attribute
            XMLAttribute Attribute = DOTA.getAttributes().get(i);

            for (int j = 0; j<Attribute.returnLength(); j++){
                //get current type
                XMLTypeOfAttack type = Attribute.getTypeOfAttacks().get(j);

                for (int k = 0; k<type.returnLength(); k++) {
                    //get current character
                    XMLcharacter character = type.getCharacters().get(k);


                    JSONcharacter checker = getCurrentCharacter(character.getName(),jsonCharacters.getCharacters() );
                    //add current character to list, if it doesn't exist so far
                    if (checker==null) {
                        //add new character to JSON list
                        jsonCharacters.addCharacter(character.getName(), character.getComplexity(), Attribute.getName());

                        //create variable to collect roles
                        JSONcharacter jsoNcharacter = jsonCharacters.getCharacters().get(jsonCharacters.returnLength() - 1);

                        //collect all roles
                        for (int l = 0; l < character.returnLength(); l++) {
                            XMLrole role = character.getRoles().get(l);

                            //add role to JSON.character list
                            jsoNcharacter.addRole(role.getName());
                        }

                        //add TypeOfAttack to current game
                        jsoNcharacter.addTypeOfAttack(type.getName());
                    }
                    else{
                        //if we have current character in list, we need to add typeofattack studio to it

                        //adding typeofattack
                        checker.addTypeOfAttack(type.getName());
                    }

                }
            }
        }

        return jsonCharacters;
    }



    public static void createJSON(JSON json, String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path),json);
    }

    private static JSONcharacter getCurrentCharacter(String nameToFind, ArrayList<JSONcharacter> listToLookIn){
        JSONcharacter foundCharacter = null;

        for (JSONcharacter jsoNcharacter : listToLookIn) {
            if (jsoNcharacter.getName().equals(nameToFind))
                foundCharacter = jsoNcharacter;
        }

        return foundCharacter;
    }


    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("Attribute")) {
                String name = attributes.getValue("name");
                DOTA.addAttribute(name);
            }
            else if (qName.equals("TypeOfAttack")){
                String name = attributes.getValue("name");
                DOTA.getAttributes().get(DOTA.returnLength()-1).addTypeOfAttack(name);
            }
            else if (qName.equals("Character")){
                String name = attributes.getValue("name");

                int complexity = Integer.parseInt(attributes.getValue("complexity"));

                DOTA.getAttributes().get(DOTA.returnLength()-1).getTypeOfAttacks()
                        .get(DOTA.getAttributes().get(DOTA.returnLength()-1).returnLength()-1).addCharacter(name,complexity);
            }
            else if (qName.equals("Role")){
                String name = attributes.getValue("name");

                int DOTAListLength = DOTA.returnLength()-1;

                XMLAttribute attribute = DOTA.getAttributes().get(DOTAListLength);
                int attributeListLength = attribute.returnLength()-1;

                XMLTypeOfAttack typeOfAttack = attribute.getTypeOfAttacks().get(attributeListLength);
                int TypeOfAttackListLength = typeOfAttack.returnLength()-1;

                XMLcharacter character = typeOfAttack.getCharacters().get(TypeOfAttackListLength);
                character.addRole(name);

            }
        }
    }
}
