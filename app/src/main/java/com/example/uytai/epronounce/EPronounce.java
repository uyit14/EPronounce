package com.example.uytai.epronounce;

/**
 * Created by uytai on 4/28/2018.
 */

public class EPronounce {
    private int ID;
    private String Content;

    public EPronounce(int ID, String content) {
        this.ID = ID;
        Content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
