package main.FileConverter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import main.FileConverter.Creation.JSON.JSON;
import main.FileConverter.Creation.JSON.JSONTypeOfAttack;
import main.FileConverter.Creation.JSON.JSONcharacter;
import main.FileConverter.Creation.XML.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JSON_TO_XML {
    private final JsonFactory factory = new JsonFactory();
    private final XMLOutputFactory output = XMLOutputFactory.newInstance();

    public JSON parseJson(final String path) throws IOException {
        JSON characters = new JSON();


        File fl = new File(path);
        if (!fl.exists())
            throw new IllegalArgumentException();

        JsonParser parser = factory.createParser(fl);

        startParsing(characters, parser);

        return characters;
    }


    private void startParsing(final JSON characters, JsonParser parser) throws IOException {
        parser.nextToken();
        parser.nextToken();

        if (parser.nextToken() != JsonToken.START_ARRAY)
            throw new RuntimeException("Invalid file structure");

        // "]"
        while (parser.nextToken() != JsonToken.END_ARRAY) {

            //"{" / "}"
            if (parser.getCurrentName() == null) {
                continue;
            }

            switch (parser.getCurrentName()) {
                case "name" -> getName(characters, parser);
                case "complexity" -> getComplexity(characters, parser);
                case "roles" -> getRoles(characters, parser);
                case "attribute" -> getAttribute(characters, parser);
                case "typeOfAttacks" -> getTypeOfAttacks(characters, parser);
                default -> throw new RuntimeException("Invalid file structure");
            }
        }
    }

    private static void getName(JSON characters, JsonParser parser) throws IOException {
        characters.addCharacter("place_holder", 1, "place_holder");
        parser.nextToken();
        characters.returnLastCharacter().setName(parser.getText());
    }

    private static void getComplexity(JSON characters, JsonParser parser) throws IOException {
        parser.nextToken();
        characters.returnLastCharacter().setComplexity(Integer.parseInt(parser.getText()));
    }

    private static void getAttribute(JSON characters, JsonParser parser) throws IOException {
        parser.nextToken();
        characters.returnLastCharacter().setAttribute(parser.getText());
    }

    private static void getRoles(JSON characters, JsonParser parser) throws IOException {
        parser.nextToken();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            parser.nextToken();
            if (parser.getCurrentName().equals("name")) {
                parser.nextToken();
                characters.returnLastCharacter().addRole(parser.getText());
                parser.nextToken();
            }
        }
    }

    private static void getTypeOfAttacks(JSON characters, JsonParser parser) throws IOException {
        parser.nextToken();
        parser.nextToken();

        while (parser.nextToken() != JsonToken.END_ARRAY) {

            //"{" / "}"
            if (parser.getCurrentName() == null) {
                continue;
            }

            if (parser.getCurrentName().equals("name")) {
                parser.nextToken();
                characters.returnLastCharacter().addTypeOfAttack(parser.getText(), "place_holder");
            } else if (parser.getCurrentName().equals("name2")) {
                parser.nextToken();
                characters.returnLastCharacter().returnLastTypeOfAttack().setName2(parser.getText());
            }
        }
    }

    //endregion

    public XML convert(JSON characters) {
        XML DOTA = new XML();

        startConvert(characters, DOTA);

        return DOTA;
    }

    private void startConvert(JSON characters, XML DOTA) {
        DOTA.addAttribute(characters.getCharacters().get(0).getAttribute());

        for (int i = 0; i < characters.returnLength(); i++) {
            //get current game in json
            JSONcharacter jsonCharacter = characters.getCharacters().get(i);

            XMLAttribute XmlAttribute = findAttribute(jsonCharacter, DOTA);

            ArrayList<String> XmlRoles = collectRoles(jsonCharacter);

            convertTypeOfAttacks(jsonCharacter, XmlAttribute, XmlRoles);
        }
    }

    private static ArrayList<String> collectRoles(JSONcharacter jsonCharacter) {
        ArrayList<String> XmlRoles = new ArrayList<>();
        for (int j = 0; j < jsonCharacter.getRoles().size(); j++) {
            XmlRoles.add(jsonCharacter.getRoles().get(j).getName());
        }
        return XmlRoles;
    }

    private void convertTypeOfAttacks(JSONcharacter jsoNcharacter, XMLAttribute XmlAttribute, ArrayList<String> XmlRoles) {
        for (int j = 0; j < jsoNcharacter.getTypeOfAttacks().size(); j++) {

            JSONTypeOfAttack jsonTypeOfAttack = jsoNcharacter.getTypeOfAttacks().get(j);

            XMLTypeOfAttack xmlTypeOfAttack = findType(jsonTypeOfAttack, XmlAttribute);


            xmlTypeOfAttack.addCharacter(jsoNcharacter.getName(), jsoNcharacter.getComplexity());

            XMLcharacter XMLCharacter = xmlTypeOfAttack.getCharacters().get(xmlTypeOfAttack.returnLength() - 1);
            for (String xmlRole : XmlRoles) {
                XMLCharacter.addRole(xmlRole);
            }
        }
    }


    private XMLTypeOfAttack findType(JSONTypeOfAttack type, XMLAttribute attribute) {
        List<XMLTypeOfAttack> types = attribute.getTypeOfAttacks();
        for (int i = types.size() - 1; i >= 0; i--) {
            if (types.get(i).getName().equals(type.getName())) {
                return types.get(i);
            }
        }

        attribute.addTypeOfAttack(type.getName(), type.getName2());

        int index = attribute.returnLength();
        return attribute.getTypeOfAttacks().get(index-1);
    }

    private XMLAttribute findAttribute (JSONcharacter jsonCharacter, XML xml) {
        List<XMLAttribute> gameAttributes = xml.getAttributes();
        for (int i = gameAttributes.size() - 1; i >= 0; i--) {
            if (gameAttributes.get(i).getName().equals(jsonCharacter.getAttribute())) {
                return gameAttributes.get(i);
            }
        }

        xml.addAttribute(jsonCharacter.getAttribute());


        int index = xml.returnLength();
        return xml.getAttributes().get(index-1);
    }



    public void createXML(XML xmlUpperClassClass, String path) throws FileNotFoundException, XMLStreamException {
        FileOutputStream out = new FileOutputStream(path);
        writeXml(out, xmlUpperClassClass);
    }

    private void writeXml(OutputStream out,XML xmlUpperClassClass) throws XMLStreamException {
        XMLStreamWriter writer = output.createXMLStreamWriter(out);

        startWriting(xmlUpperClassClass, writer);

        writer.flush();
        writer.close();
    }

    private static void startWriting(XML xmlUpperClassClass, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartDocument("utf-8", "1.0");

        // header
        writer.writeStartElement("DOTA");
        writer.writeStartElement("Attributes");

        writeAttributes(xmlUpperClassClass, writer);

        writer.writeEndElement();
        writer.writeEndElement();
    }

    private static void writeAttributes(XML xmlUpperClassClass, XMLStreamWriter writer) throws XMLStreamException {
        for (int i = 0; i < xmlUpperClassClass.returnLength(); i++) {
            writer.writeStartElement("Attribute");
            writer.writeAttribute("name", xmlUpperClassClass.getAttributes().get(i).getName());

            writer.writeStartElement("typeOfAttacks");

            writeTypeOfAttacks(xmlUpperClassClass, writer, i);
            writer.writeEndElement();
            writer.writeEndElement();
        }
    }

    private static void writeTypeOfAttacks(XML xmlUpperClassClass, XMLStreamWriter writer, int i) throws XMLStreamException {
        for (int j = 0; j < xmlUpperClassClass.getAttributes().get(i).getTypeOfAttacks().size(); j++) {
            XMLTypeOfAttack dev = xmlUpperClassClass.getAttributes().get(i).getTypeOfAttacks().get(j);

            writer.writeStartElement("typeOfAttack");
            writer.writeAttribute("name", dev.getName());
            writer.writeAttribute("name2", dev.getName2());

            writer.writeStartElement("Characters");

            writeGames(writer, dev);
            writer.writeEndElement();
            writer.writeEndElement();
        }
    }

    private static void writeGames(XMLStreamWriter writer, XMLTypeOfAttack type) throws XMLStreamException {
        for (int k = 0; k < type.getCharacters().size(); k++) {
            XMLcharacter character = type.getCharacters().get(k);

            writer.writeStartElement("Character");
            writer.writeAttribute("name", character.getName());
            writer.writeAttribute("Role", ((Integer) character.getComplexity()).toString());

            writer.writeStartElement("Roles");

            writeRoles(writer, character);
            writer.writeEndElement();
            writer.writeEndElement();
        }
    }

    private static void writeRoles(XMLStreamWriter writer, XMLcharacter character) throws XMLStreamException {
        for (int l = 0; l < character.getRoles().size(); l++) {
            XMLrole xmlrole = character.getRoles().get(l);

            writer.writeStartElement("Role");
            writer.writeAttribute("name", xmlrole.getName());
            writer.writeEndElement();
        }
    }
}
