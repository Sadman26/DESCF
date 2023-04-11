package com.example.snake;

public class Snake {
    String id, name, auth_name, loc, photourl,time,randomid;
    public Snake() {
    }

    public Snake(String id, String name, String auth_name, String loc, String photourl, String time) {
        this.id = id;
        this.name = name;
        this.auth_name = auth_name;
        this.loc = loc;
        this.photourl = photourl;
        this.time = time;
    }

    public Snake(String id, String name, String auth_name, String loc, String photourl, String time, String randomid) {
        this.id = id;
        this.name = name;
        this.auth_name = auth_name;
        this.loc = loc;
        this.time=time;
        this.photourl = photourl;
        this.randomid=randomid;
    }

    public Snake(String name, String loc, String photourl, String time) {
        this.name = name;
        this.loc = loc;
        this.photourl = photourl;
        this.time = time;
    }

    public Snake(String id, String name, String loc, String photourl, String time) {
        this.id = id;
        this.name = name;
        this.loc = loc;
        this.photourl = photourl;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRandomid() {
        return randomid;
    }

    public void setRandomid(String randomid) {
        this.randomid = randomid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuth_name() {
        return auth_name;
    }

    public void setAuth_name(String auth_name) {
        this.auth_name = auth_name;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}
