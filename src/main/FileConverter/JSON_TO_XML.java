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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class JSON_TO_XML {
    private static JSON characters = new JSON();

    public static JSON parseJSON(String path) throws IOException {
        File fl = new File(path);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(fl);

        //point to
        parser.nextToken();
        parser.nextToken();


        if (parser.nextToken() == JsonToken.START_ARRAY) {
            //loop until token equal to "]"
            while (parser.nextToken() != JsonToken.END_ARRAY) {

                //checker if we're looking at "{" / "}"
                if (parser.getCurrentName() == null)
                    continue;
                String key = parser.getCurrentName();

                if (key.equals("name")) {
                    characters.addCharacter("place_holder", -1, "place_holder");
                    parser.nextToken();
                    characters.returnLastCharacter().setName(parser.getText());
                } else if (key.equals("complexity")) {
                    parser.nextToken();
                    characters.returnLastCharacter().setComplexity(Integer.parseInt(parser.getText()));
                } else if (key.equals("attribute")) {
                    parser.nextToken();
                    characters.returnLastCharacter().setAttribute(parser.getText());
                } else if (key.equals("roles")) {
                    parser.nextToken();
                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                        parser.nextToken();
                        if (parser.getCurrentName().equals("name")) {
                            parser.nextToken();
                            characters.returnLastCharacter().addRole(parser.getText());
                            parser.nextToken();
                        }
                    }
                } else if (key.equals("typeOfAttacks")) {
                    parser.nextToken();
                    parser.nextToken();

                    //loop until end of devStudios
                    while (parser.nextToken() != JsonToken.END_ARRAY) {

                        //checker if we're looking at "{" / "}"
                        if (parser.getCurrentName() == null)
                            continue;
                        if (parser.getCurrentName().equals("name")) {
                            parser.nextToken();
                            characters.returnLastCharacter().addTypeOfAttack(parser.getText());
                        }
                    }
                }

            }
        } else
            return null;


        //Getting the token name


        return characters;
    }

    public static XML convert() {
        XML DOTA = new XML();

        //getting started
        DOTA.addAttribute(characters.getCharacters().get(0).getAttribute());


        for (int i=0;i<characters.returnLength();i++){
            //get current character in json
            JSONcharacter jsoNcharacter = characters.getCharacters().get(i);

            //check if we need to create new attribute
            if (!checkIfAttrExists(jsoNcharacter.getAttribute(),DOTA.getAttributes()))
                DOTA.addAttribute(jsoNcharacter.getAttribute());

            XMLAttribute XMLattribute = findAttr(jsoNcharacter.getAttribute(),DOTA.getAttributes());

            //collect roles
            ArrayList<String> XMLroles = new ArrayList<>();
            for (int j=0;j<jsoNcharacter.getRoles().size();j++){
                XMLroles.add(jsoNcharacter.getRoles().get(j).getName());
            }

            for (int j=0;j<jsoNcharacter.getTypeOfAttacks().size();j++){

                //get current TypeOfAttack in json
                JSONTypeOfAttack jsonTypeOfAttack = jsoNcharacter.getTypeOfAttacks().get(j);

                XMLTypeOfAttack XMLtype;
                //check if we need to create new type
                if (!checkIfTypeExists(jsonTypeOfAttack.getName(), XMLattribute.getTypeOfAttacks())){
                    //if it doesn't exist
                    //update gamePublisher to avoid "place_holder"-----------------------------
                    XMLattribute.setName(jsoNcharacter.getAttribute());

                    //create new dev
                    XMLattribute.addTypeOfAttack(jsonTypeOfAttack.getName());
                }
                XMLtype = findType(jsonTypeOfAttack.getName(), XMLattribute.getTypeOfAttacks());

                //add game
                XMLtype.addCharacter(jsoNcharacter.getName(), jsoNcharacter.getComplexity());

                //set all platforms
                XMLcharacter XMLcharacter = XMLtype.getCharacters().get(XMLtype.returnLength()-1);
                for (String xmlRole : XMLroles) {
                    XMLcharacter.addRole(xmlRole);
                }
            }
        }

        return DOTA;
    }

    public static void createXML(XML xmlClass, String path) {
        try(FileOutputStream out = new FileOutputStream(path)){
            writeXml(out, xmlClass);
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private static void writeXml(OutputStream out, XML xmlClass) throws XMLStreamException {
        //stax cursor
        XMLOutputFactory output = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = output.createXMLStreamWriter(out,"utf-8");

        writer.writeStartDocument("utf-8", "1.0");

        //header
        writer.writeStartElement("DOTA");


        writer.writeStartElement("Attributes");


        // insides

        for (int i = 0; i < xmlClass.returnLength(); i++) {
            writer.writeStartElement("Attribute");
            writer.writeAttribute("name", xmlClass.getAttributes().get(i).getName());

            writer.writeStartElement("TypeOfAttacks");


            for (int j = 0; j < xmlClass.getAttributes().get(i).getTypeOfAttacks().size(); j++) {
                XMLTypeOfAttack type = xmlClass.getAttributes().get(i).getTypeOfAttacks().get(j);

                writer.writeStartElement("TypeOfAttack");
                writer.writeAttribute("name", type.getName());

                writer.writeStartElement("Characters");

                for (int k = 0; k < type.getCharacters().size(); k++) {
                    XMLcharacter character = type.getCharacters().get(k);

                    writer.writeStartElement("Character");
                    writer.writeAttribute("name", character.getName());
                    writer.writeAttribute("complexity", ((Integer)character.getComplexity()).toString());

                    writer.writeStartElement("Roles");

                    for (int l = 0; l < character.getRoles().size(); l++) {
                        XMLrole xmLrole = character.getRoles().get(l);

                        writer.writeStartElement("Role");
                        writer.writeAttribute("name", xmLrole.getName());
                        writer.writeEndElement();

                    }
                    writer.writeEndElement();//end roles
                    writer.writeEndElement();//end character

                }
                writer.writeEndElement();//end characters
                writer.writeEndElement();//end TypeOfAttack
            }
            writer.writeEndElement();//end types
            writer.writeEndElement();//end attribute

        }

        writer.writeEndElement();//end attributes
        writer.writeEndElement();//end DOTA



        writer.flush();

        writer.close();
    }

    //region Utils
    private static boolean checkIfTypeExists(String name, ArrayList<XMLTypeOfAttack> types){
        for (XMLTypeOfAttack type : types) {
            if (type.getName().equals(name))
                return true;
        }
        return false;
    }
    private static XMLTypeOfAttack findType(String name, ArrayList<XMLTypeOfAttack> type){
        for (int i=type.size()-1;i>=0;i--){
            if (type.get(i).getName().equals(name))
                return type.get(i);
        }
        return null;
    }
    private static boolean checkIfAttrExists(String name, ArrayList<XMLAttribute> attrs){
        for (XMLAttribute attr : attrs) {
            if (attr.getName().equals(name))
                return true;
        }
        return false;
    }
    private static XMLAttribute findAttr(String name, ArrayList<XMLAttribute> attr){
        for (int i=attr.size()-1;i>=0;i--){
            if (attr.get(i).getName().equals(name))
                return attr.get(i);
        }
        return null;
    }

    //endregion
}
