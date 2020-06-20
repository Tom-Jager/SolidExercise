package main.com.company;

public class Converter {
    //Ensures arguments are correct, loads the input csv file, converts it and prints the converted version
    public static void main(String[] args){
        if(args.length < 2){
            System.out.println("Converter requires an input filename and an output filename");
        }
        else if(args.length > 2){
            System.out.println("Only input file and output filename are required as arguments");
        }
        else{
            CSVFileConverter fileConverter = new CSVFileConverter();
            fileConverter.readCsv(args[0]);
            fileConverter.convertCSV();
            fileConverter.printCSV(args[1]);
        }

    }
}
