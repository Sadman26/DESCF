package com.example.snake;

public class historymodel {
    String snakeid,snakecode;
    public historymodel(){

    }
    public historymodel(String snakeid, String snakecode) {
        this.snakeid = snakeid;
        this.snakecode = snakecode;
    }
    public String getSnakeid() {
        return snakeid;
    }
    public void setSnakeid(String snakeid) {
        this.snakeid = snakeid;
    }
    public String getSnakecode() {
        return snakecode;
    }
    public void setSnakecode(String snakecode) {
        this.snakecode = snakecode;
    }
}