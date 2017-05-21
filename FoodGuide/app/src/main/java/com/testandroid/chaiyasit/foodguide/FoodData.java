package com.testandroid.chaiyasit.foodguide;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chaiyasit on 5/16/2017.
 */

public class FoodData {
    private String menuName;
    private ArrayList<String>data = new ArrayList<>();
    private ArrayList<String>condiment = new ArrayList<>();

    public FoodData(String mName){
        menuName = mName;
    }
    public void addData(String[] tmpData){
        for (String tmp : tmpData){
            data.add(tmp);
        }
    }
    public void addCondiment(String[] tmpData){
        for (String tmp : tmpData){
            condiment.add(tmp);
        }
    }
    public ArrayList<String> getCondiment(){
        return this.condiment;
    }
    public String GetmenuName(){
        return this.menuName;
    }
    public ArrayList<String> getData(){
        return this.data;
    }
}

