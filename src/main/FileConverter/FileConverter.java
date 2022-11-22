package main.FileConverter;

import main.FileConverter.Creation.XML.XML;
import main.FileConverter.Creation.XML.XMLAttribute;

import java.util.ArrayList;
import java.util.List;

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



}

