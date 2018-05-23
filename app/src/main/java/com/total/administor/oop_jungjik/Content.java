package com.total.administor.oop_jungjik;

import java.io.Serializable;

public class Content implements Serializable {
    private String Name = " ", Day = " ";
    private int second = 0, minute=0, daynum=0;



    public Content(String name, String day,int sec,int min )
    {
        Name = name;
        Day=day;
        second=sec;
        minute=min;
        if(day=="둘째 날")
        {daynum=1;}
        else if(day=="셋째 날")
        {daynum=2;}
        else if(day=="마지막 날")
        {daynum=3;}
        else {daynum=0;}
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int min) {
        minute=min;
    }

    public int getSeond() {
        return second;
    }

    public void setSecond(int sec) {
        second =sec;
    }

    public String getName(){
        return Name;
    }

    public void setDay(String day, int n) {
        Day = day;
       daynum=n;
    }

    public int getIntDay() {
        return daynum;
    }
    public String getDay() {
        return Day;
    }
}
