package com.example.anh.anhnguyen_pset5;

/**
 * Created by Anh on 2-12-2016.
 */

public class TodoList {

    private int id;
    private String nameList;


    public TodoList () {
    }

    public TodoList(String nameOfList) {
        this.nameList = nameOfList;
    }

    public void setList (String newListName){
        this.nameList = newListName;
    }

    public void setId (int ListId) {
        this.id = ListId;
    }

    public int getId (){
        return this.id;
    }




}
