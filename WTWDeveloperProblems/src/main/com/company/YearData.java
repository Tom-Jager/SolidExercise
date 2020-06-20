package main.com.company;

//Stores the origin year, dev year and increment
public class YearData {
    private Integer originYear;
    private Integer devYear;
    private Float incrValue;

    public YearData(Integer originYear, Integer devYear, Float incrValue){

        this.originYear = originYear;
        this.devYear = devYear;
        this.incrValue = incrValue;
    }
    public Integer getOriginYear() {
        return originYear;
    }

    public Integer getDevYear() {
        return devYear;
    }

    public Float getIncrValue() {
        return incrValue;
    }

    @Override
    public String toString(){
        String sOriginYear = "O-Year:".concat(String.valueOf(originYear));
        String sDevYear = "D-Year:".concat(String.valueOf(devYear));
        String sIncrValue = "Incr-Value:".concat(String.valueOf(incrValue));
        return "<".concat(sOriginYear).concat(", ").concat(sDevYear).concat(", ").concat(sIncrValue).concat(">");
    }


    @Override
    public boolean equals(Object o){
        YearData yd;
        if(o instanceof YearData){
            yd = (YearData) o;
            if((yd.getOriginYear() == originYear) && (yd.getDevYear() == devYear)){
                return true;
            }
        }
        return false;
    }

}
