package edu.pk.pucit.booksdata;

public class HomeModel {
    private final String day;
    private final String id;


    public HomeModel(String day, String id){
        this.day = day;
        this.id = id;
    }

    public String getDayList() {
        return day;
    }

    public String getIdList() {
        return id;
    }
}
