package com.example.hp.materialtest.user;

/**
 * Created by HP on 2017/4/29.
 */

public class Head {
    private String name;
    private String nickname;
    private int imageID;

    public Head(String name, String nickname, int imageID) {
        this.name = name;
        this.nickname = nickname;
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
