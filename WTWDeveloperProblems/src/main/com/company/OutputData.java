package main.com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;

public class OutputData extends HashMap<String, Map<Integer, Row>> {
    private final Integer earliestYear;
    private final Integer latestYear;
    private final Set<String> productSet;
    private final List<String> errorMessages;

    //Initialize the output data with the correct number of empty rows
    public OutputData(Integer earliestYear, Integer latestYear, Set<String> productSet, List<String> errorMessages){
        super();
        this.earliestYear = earliestYear;
        this.latestYear = latestYear;
        this.productSet = productSet;
        this.errorMessages = errorMessages;

        for( String p : productSet){
            this.put(p, genEmptyRows());
        }
    }

    //Fill in the rows of the triangle as 0s, R.T O(n^2)
    private Map<Integer, Row> genEmptyRows() {
        Map<Integer, Row> newRows = new HashMap<Integer, Row>();
        for(Integer i = earliestYear; i <= latestYear; i++){
            Row newRow = new Row();
            for(Integer j = latestYear-i; j >= 0; j--){
                newRow.add(Float.valueOf(0));
            }
            newRows.put(i,newRow);
        }
        return newRows;
    }

    //Method to process the triangle into the cumulative form
    //Make each row accumulate
    public void processIncrs(){
        for( Map<Integer, Row> triangle : this.values()){
            for(Row row : triangle.values()){
                row.accumulate();
            }
        }
    }

    //Get the string representation of all the triangles
    public String getTrianglesString(){
        //add year and number of development years
        StringBuilder sb = new StringBuilder();
        sb.append(earliestYear);
        sb.append(",");
        sb.append(latestYear-earliestYear+1);
        //add the string representation of each triangle
        for(Entry<String, Map<Integer, Row>> e : this.entrySet()){
            sb.append("\n");
            sb.append(getTriangleString(e.getKey(), e.getValue()));
        }

        return sb.toString();
    }

    //Get the string representation of a single triangle
    private String getTriangleString(String product, Map<Integer, Row> rowMap) {
        StringBuilder tb = new StringBuilder();
        tb.append(product.concat(","));
        String rowString = rowMap.values().toString();
        //remove the square brackets and spaces from string representation
        rowString = rowString.replaceAll("\\[|\\]|\\s", "");
        tb.append(rowString);
        return tb.toString();
    }


    public List<String> getErrorMessages() {
        return this.errorMessages;
    }
}
