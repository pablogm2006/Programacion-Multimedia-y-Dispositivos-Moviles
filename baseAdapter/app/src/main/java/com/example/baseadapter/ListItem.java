package com.example.baseadapter;

public class ListItem {
    private int imageResid;
    private String title;
    private String content;
    public ListItem(int imageResid, String title, String content){
        this.imageResid = imageResid;
        this.title = title;
        this.content= content;
    }
    public int getImageResid(){
        return imageResid;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
}
