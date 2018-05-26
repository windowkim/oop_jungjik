package com.total.administor.oop_jungjik;

import java.io.Serializable;

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

    public User(int userid, String username, String edit)
    {
        ID=userid;
        Name=username;
        if(edit.equals("User")) {
            isEditor = false;
        }
        else
        {isEditor=true;}
    }

    }
