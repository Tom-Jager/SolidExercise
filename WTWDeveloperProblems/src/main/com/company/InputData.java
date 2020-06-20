package main.com.company;

import java.util.*;

//Class to store all data from input CSV file
public class InputData extends HashMap<String, List<YearData>> {

    List<String> errorMsgs;
    public InputData(){
        super();
        errorMsgs = new ArrayList<String>();
    }

    //Get the earliest year for all of the products
    public Integer findEarliestOrigYear(){
        Collection<List<YearData>> productDatas = this.values();
        Integer earliestYear = Integer.MAX_VALUE;

        for(List<YearData> yds : productDatas){
            for(YearData yd : yds){
                if(yd.getOriginYear() < earliestYear) earliestYear = yd.getOriginYear();
            }
        }
        return earliestYear;
    }

    //Get the latest development year for all the products
    public Integer findLatestDevYear(){
        Collection<List<YearData>> productDatas = this.values();
        Integer latestYear = Integer.MIN_VALUE;

        for(List<YearData> yds : productDatas){
            for(YearData yd : yds){
                if(yd.getDevYear() > latestYear) latestYear = yd.getDevYear();
            }
        }
        return latestYear;
    }

    //Take the currently stored input data and generate the output data object from it
    public OutputData generateOutputData(){
        //Find the dimensions of the new triangle
        Integer earliestYear = findEarliestOrigYear();
        Integer latestYear = findLatestDevYear();
        //# of products = n, # of development years = m
        //Construct the new output data as n m*m/2 triangles with no incremental values stored yet
        OutputData outData = new OutputData(earliestYear, latestYear, this.keySet(), errorMsgs);

        //For each product
        for( Entry<String, List<YearData>> e : this.entrySet()){
            List<YearData> yearList = e.getValue();
            String product = e.getKey();

            //For each piece of year information
            for(YearData y : yearList){
                Integer originYear = y.getOriginYear();
                Integer rowInd = y.getDevYear() - y.getOriginYear();

                //Update the correct triangle with the correct increment information
                Map<Integer, Row> tri = outData.get(product);
                Row row = tri.get(originYear);
                row.set(rowInd, y.getIncrValue());
            }
        }

        return outData;
    }

    public void addErrorMsg(String errorMsg){
        this.errorMsgs.add(errorMsg);
    }

    public void addErrorMsg(String[] record, String errorMsg) {
        StringBuilder sb = new StringBuilder();
        sb.append("Record ");
        sb.append(new ArrayList<>(Arrays.asList(record)));
        sb.append(" ");
        sb.append(errorMsg);
        this.errorMsgs.add(sb.toString());
    }
}
