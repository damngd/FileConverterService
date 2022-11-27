package main;

import main.FileConverter.Creation.JSON.JSON;
import main.FileConverter.Creation.XML.XML;
import main.FileConverter.JSON_TO_XML;
import main.FileConverter.XML_TO_JSON;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Main {
    private static final XML_TO_JSON xmlToJsonParser = new XML_TO_JSON();
    private static final JSON_TO_XML jsonToXmlParser = new JSON_TO_XML();

    public static void main(String[] args) throws Exception {

        if (args.length != 0)
            if (args[0].contains(".json"))
                parseJson(args[0], args[1]);
            else if (args[0].contains(".xml"))
                parseXml(args[0], args[1]);
            else
                throw new Exception("Wrong");
        else {
            parseXml("C:\\practice2\\FileConverterService\\src\\test\\TestXML.xml", "C:\\practice2\\FileConverterService\\Created.json");

        }
    }
    private static void parseXml(String path, String newPath) {
        XML parsedClass;
        try {
            parsedClass = xmlToJsonParser.parseXML(path);
        } catch (ParserConfigurationException | SAXException | IOException exception) {
            System.out.println("Failed to parse xml file");
            exception.printStackTrace();
            return;
        }

        try {
            xmlToJsonParser.createJson(xmlToJsonParser.convert(parsedClass), newPath);
        } catch (IOException ioException) {
            System.out.println("Failed to create json file");
            ioException.printStackTrace();
        }
    }
    private static void parseJson(String path, String newPath) {
        JSON jsonClass;
        try {
            jsonClass = jsonToXmlParser.parseJson(path);
        } catch (IOException exception) {
            System.out.println("Failed to parse json file.");
            exception.printStackTrace();
            return;
        }

        try {
            jsonToXmlParser.createXML(jsonToXmlParser.convert(jsonClass), newPath);
        } catch (FileNotFoundException exception) {
            System.out.println("File not found.");
            exception.printStackTrace();
        } catch (XMLStreamException exception) {
            System.out.println("Failed to create json file.");
            exception.printStackTrace();
        }
    }
}
