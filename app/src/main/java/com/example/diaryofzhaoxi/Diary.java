package com.example.diaryofzhaoxi;

public class Diary {
    private String name;
    private int id;
    public Diary(String name,int id)
    {
        this.name =name;
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
}
