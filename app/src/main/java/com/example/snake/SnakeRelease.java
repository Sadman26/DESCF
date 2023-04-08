package com.example.snake;

public class SnakeRelease {
    String snakeid,snakecode;
    public SnakeRelease() {
    }

    public SnakeRelease(String snakeid, String snakecode) {
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
