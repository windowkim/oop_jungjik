package com.total.administor.oop_jungjik;

import java.io.Serializable;
import java.util.Calendar;

public class User implements Serializable{
    private int ID;
    private  String Name;
    private boolean isEditor;

    public boolean isEditor() {
        return isEditor;
    }

    public int getID(){
        return ID;
    }

    public String getName(){
        return Name;
    }

    public User(int userid, String username)
    {
        ID=userid;
        Name=username;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (userid / 10000 == year) // 새내기
        {
            isEditor=false;
        }
        else
        {
            isEditor=true;
        }
    }
}
