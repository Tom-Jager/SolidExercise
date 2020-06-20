package main.com.company;

import java.util.ArrayList;
import java.util.List;

public class Row extends ArrayList<Float>{
    //Method to calculate the cumulative increases
    public void accumulate() {
        for(int i = 1; i < this.size(); i++){
            Float newVal = this.get(i-1) + this.get(i);
            this.set(i, newVal);
        }
    }
}
