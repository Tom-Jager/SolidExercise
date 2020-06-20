package main.com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;




public class CSVFileConverter {

    private InputData preConv;
    private OutputData postConv;

    //Initialize data objects as empty
    public CSVFileConverter() {
        List<String> errs = new ArrayList<>();
        errs.add("File not converted yet");

        preConv = new InputData();
        postConv = new OutputData(0,0,new HashSet<String>(), errs);
    }

    //Reads in a new input file into the InputData object
    public void readCsv(String fileName) {
        this.preConv = new InputData();

        BufferedReader br = null;
        String line = "";

        //Access the input file and report error if the file does not exist
        try {
            br = new BufferedReader(new FileReader(fileName));
        }
        catch(FileNotFoundException e){
            preConv.addErrorMsg("Input file cannot be found");
            return;
        }
        //Discard the header line
        try {
            br.readLine();
        }
        catch(IOException e){
            preConv.addErrorMsg("Error when reading first line of file");
            return;
        }
        try {
            while ((line = br.readLine()) != null) {
                //Strip whitespace
                line = line.replaceAll("\\s", "");


                String[] record = line.split(",");
                //Validate the record
                if (validRecord(record)) {
                    String product = record[0];
                    Integer originYear = Integer.valueOf(record[1]);
                    Integer devYear = Integer.valueOf(record[2]);
                    Float incrValue = Float.valueOf(record[3]);
                    YearData yd = new YearData(originYear, devYear, incrValue);
                    //Add information about the year increment into the InputData object
                    if (preConv.containsKey(product)) {
                        preConv.get(product).add(yd);
                    } else {
                        List<YearData> yearList = new ArrayList<>();
                        yearList.add(yd);
                        preConv.put(product, yearList);
                    }
                }
            }
        }
        catch(IOException e){
            preConv.addErrorMsg("Error in reading file");
            return;
        }
        return;
    }
    //Generate the output data triangles and then do the increment calculations
    public void convertCSV(){
        this.postConv = preConv.generateOutputData();
        postConv.processIncrs();
    }

    //output the new triangles in the right form
    public void printCSV(String filename){
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(postConv.getTrianglesString());
            out.println();
            for( String e : postConv.getErrorMessages()){
                out.println(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    //validate the record for errors
    //Check length
    //Check for numbers
    //Ensure that dev year is not before origin year
    private boolean validRecord(String[] record) {
        Boolean check = true;
        if(record.length != 4){
            preConv.addErrorMsg(record, "does not have the correct number of fields");
            check = false;
            return check;
        }
        if(!isInteger(record[1])){
            preConv.addErrorMsg(record, record[1].concat(" is not a number"));
            check = false;
        }
        if(!isInteger(record[2])){
            preConv.addErrorMsg(record, record[2].concat(" is not a number"));
            check = false;
        }
        if(!isFloat(record[3])){
            preConv.addErrorMsg(record, record[3].concat(" is not a number"));
            check = false;
        }
        if(check){
            Integer oYear = Integer.valueOf(record[1]);
            Integer dYear = Integer.valueOf(record[2]);
            if(oYear > dYear){
                preConv.addErrorMsg(record, "Development year cannot be before origin year");
                check = false;
            }
        }
        return check;
    }

    //Determines whether the string is a float
    private boolean isFloat(String s) {
        try{
            Float.valueOf(s);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;

    }

    //Determines whether the string is an integer
    private boolean isInteger(String s) {
        try{
            Integer.valueOf(s);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }


}
