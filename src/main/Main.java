package main;

import main.FileConverter.FileConverter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        //String path;
        //String newPath;



        //if(args.length == 0) {
        //    Scanner inputPath = new Scanner(System.in);
         //   System.out.print("Path to file: ");
         //   path = inputPath.nextLine();

         //   Scanner inputNewPath = new Scanner(System.in);
         //   System.out.print("Path to new file: ");
          //  newPath = inputNewPath.nextLine();
          //  args[0] = path;
          //  args[1] = newPath;
        //}
        FileConverter convert = new FileConverter();
        if (args.length != 0)
            if (args[0].contains(".json"))
                convert.convertToXML(args[0], args[1]);
            else if (args[0].contains(".xml"))
                convert.convertToJson(args[0], args[1]);
            else
                throw new Exception("Wrong");
        else {
            convert.convertToJson("C:\\practice2\\FileConverterService\\src\\test\\TestXML.xml", "C:\\practice2\\FileConverterService\\Created.json");

        }
    }
}
