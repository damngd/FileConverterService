package main;

import main.FileConverter.FileConverter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        FileConverter convert = new FileConverter();
        if (args.length != 0)
            if (args[0].contains(".json"))
                convert.convertToXML(args[0], args[1]);
            else if (args[0].contains(".xml"))
                convert.convertToJson(args[0], args[1]);
            else
                throw new Exception("Wrong");
        else {
            convert.convertToXML("C:\\practice2\\FileConverterService\\src\\test\\TestJSON.json", "C:\\practice2\\FileConverterService\\Created2.xml");

        }
    }
}
