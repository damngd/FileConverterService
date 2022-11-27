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
import java.util.List;
import java.util.stream.Collectors;

public class XML_TO_JSON extends DefaultHandler {
    private XML DOTA;
    private SAXParserFactory factory = SAXParserFactory.newInstance();
    private XmlHandler handler = new XmlHandler();
    private ObjectMapper mapper = new ObjectMapper();

    public XML parseXML(String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = factory.newSAXParser();

        File fl = new File(path);
        parser.parse(fl, handler);

        return DOTA;
    }


    public JSON convert(XML DOTA) {
        JSON jsoncharacter = new JSON();

        startConvert(DOTA, jsoncharacter);

        return jsoncharacter;
    }

    private void startConvert(XML DOTA, JSON jsoncharacters) {
        for (int i = 0; i < DOTA.returnLength(); i++) {
            //get current publisher
            XMLAttribute attribute = DOTA.getAttributes().get(i);

            getAttribute(jsoncharacters, attribute);
        }
    }

    private void getAttribute(JSON jsoncharacters, XMLAttribute attribute) {
        for (int j = 0; j < attribute.returnLength(); j++) {
            XMLTypeOfAttack type = attribute.getTypeOfAttacks().get(j);

            getType(jsoncharacters, attribute, type);
        }
    }

    private void getType(JSON jsoncharacters, XMLAttribute attribute, XMLTypeOfAttack type) {
        for (int k = 0; k < type.returnLength(); k++) {
            XMLcharacter character = type.getCharacters().get(k);

            getGame(jsoncharacters, attribute, type, character);
        }
    }

    private void getGame(JSON jsoncharacters, XMLAttribute attribute, XMLTypeOfAttack type, XMLcharacter character) {
        JSONcharacter checker = getCurrentGame(character.getName(), jsoncharacters.getCharacters());
        if (checker == null) {
            createNewCharacter(jsoncharacters, attribute, type, character);
        } else {
            checker.addTypeOfAttack(type.getName(), type.getName2());
        }
    }
    private JSONcharacter getCurrentGame(String nameToFind, ArrayList<JSONcharacter> listToLookIn) {
        JSONcharacter foundcharacter = null;

        for (JSONcharacter jsoNcharacter : listToLookIn) {
            if (jsoNcharacter.getName().equals(nameToFind)) {
                foundcharacter = jsoNcharacter;
            }
        }
        return foundcharacter;
    }

    private void createNewCharacter(JSON jsoncharacters, XMLAttribute attribute, XMLTypeOfAttack type, XMLcharacter character) {
        jsoncharacters.addCharacter(character.getName(), character.getComplexity(),attribute.getName());
        JSONcharacter jsoNcharacter = jsoncharacters.getCharacters().get(jsoncharacters.returnLength() - 1);

        getPlatform(character, jsoNcharacter);

        jsoNcharacter.addTypeOfAttack(type.getName(), type.getName2());
    }
    private  void getPlatform(XMLcharacter character, JSONcharacter jsoncharacter) {
        for (int l = 0; l < character.returnLength(); l++) {
            XMLrole role = character.getRoles().get(l);

            jsoncharacter.addRole(role.getName());
        }
    }



    public void createJson(JSON json, String path) throws IOException {
        mapper.writeValue(new File(path), json);
    }

    private class XmlHandler extends DefaultHandler {
        public void startDocument() {
            DOTA = new XML();
        }
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            switch (qName) {
                case "Attribute" -> {
                    getAttributeSAX(attributes);
                }
                case "TypeOfAttack" -> {
                    getTypeOfAttackSAX(attributes);
                }
                case "Character" -> {
                    getCharacterSAX(attributes);
                }
                case "Role" -> {
                    getRoleSAX(attributes);
                }
            }
        }

        private void getAttributeSAX(Attributes attributes) {
            String name = attributes.getValue("name");
            DOTA.addAttribute(name);
        }

        private void getTypeOfAttackSAX(Attributes attributes) {
            String name = attributes.getValue("name");

            String name2 = attributes.getValue("name2");
            DOTA.getAttributes().get(DOTA.returnLength() - 1).addTypeOfAttack(name,name2);
        }

        private void getCharacterSAX(Attributes attributes) {
            String name = attributes.getValue("name");
            int complexity = Integer.parseInt(attributes.getValue("complexity"));

            int DOTAListLength = DOTA.returnLength() - 1;

            XMLAttribute attribute = DOTA.getAttributes().get(DOTAListLength);
            int attributeListLength = attribute.returnLength() - 1;

            XMLTypeOfAttack type = attribute.getTypeOfAttacks().get(attributeListLength);

            type.addCharacter(name, complexity);
        }

        private void getRoleSAX(Attributes attributes) {
            String name = attributes.getValue("name");

            int DOTAListLength = DOTA.returnLength() - 1;

            XMLAttribute attribute = DOTA.getAttributes().get(DOTAListLength);
            int attributeListLength = attribute.returnLength() - 1;

            XMLTypeOfAttack type = attribute.getTypeOfAttacks().get(attributeListLength);
            int typeListLength = type.returnLength() - 1;

            XMLcharacter character = type.getCharacters().get(typeListLength);
            character.addRole(name);
        }
    }
}
