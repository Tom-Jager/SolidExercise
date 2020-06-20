package main.com.company;

import main.com.company.InputData;
import main.com.company.YearData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



class InputDataTest {

    private InputData inputData;
    private List<String> productNames = Arrays.asList(new String[]{"Comp", "Non-Comp", "ABC", "DEF", "GHI"});
    private Random rand;


    @BeforeEach
    void setUp() {
        rand = new Random();
        inputData = genInputData();
    }

    private InputData genInputData() {
        InputData gen = new InputData();
        for(String p : productNames){
            List<YearData> yds = genListOfYearData();
            gen.put(p,yds);
        }
        return gen;
    }

    private List<YearData> genListOfYearData() {
        List<YearData> yds = new ArrayList<>();
        int n = rand.nextInt(12)+1;
        YearData yd;

        for(int i = 0; i < n; i++){
            do{
                //max dev year = 7
                Integer originYear = Integer.valueOf(rand.nextInt(7))+1990;
                Integer devYear = Integer.valueOf(rand.nextInt(1997-originYear))+originYear;
                Float incrValue = Float.valueOf((rand.nextInt(17000)+3000)/100);
                yd = new YearData(originYear, devYear, incrValue);
            }
            while (yds.contains(yd));
            yds.add(yd);
        }
        return yds;
    }

    @AfterEach
    void tearDown() {
        inputData = new InputData();
    }

    @Test
    void findEarliestOrigYear() {
        Integer expectedEarliestYear = 1989;
        YearData earliestYearData = new YearData(expectedEarliestYear,1992, Float.valueOf((float) 33.3));
        inputData.get("ABC").add(earliestYearData);

        Integer realEarliestYear = inputData.findEarliestOrigYear();
        Assertions.assertEquals(expectedEarliestYear,realEarliestYear);

    }

    @Test
    void findLatestDevYear() {
        Integer expectedLatestYear = 2000;
        YearData latestYearData = new YearData(1990, expectedLatestYear, Float.valueOf((float) 33.3));
        inputData.get("DEF").add(latestYearData);

        Integer realLatestYear = inputData.findLatestDevYear();
        Assertions.assertEquals(expectedLatestYear, realLatestYear);
    }

    @Test
    void generateOutputData() {
    }

    @Test
    void addErrorMsg() {
    }

    @Test
    void addErrorMsgWithRecord() {
    }
}