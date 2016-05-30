package com.yoyo.jopet.androidorgapp;

/**
 * Created by Jopet on 5/30/2016.
 */
public class Entry {

    public static final int TYPE_NOTE = 0;
    public static final int TYPE_TODO = 1;

    private int id;
    private String title;
    private String content;
    private String activity;
    private int status;
    private int type;

    public Entry() {

    }

    public Entry(int id, String title, String content, int status) {
        this.id = id;
        this.title = title;
        this.activity = title;
        this.content = content;
        this.status = status;
        this.type = TYPE_NOTE;
    }

    public Entry(int id, String activity, int status, String content) {
        this.id = id;
        this.activity = activity;
        this.title = activity;
        this.content = content;
        this.status = status;
        this.type = TYPE_TODO;
    }

    public Entry(String title, String content, int status, int type) {
        this.title = title;
        this.activity = title;
        this.content = content;
        this.status = status;
        this.type = type;
    }

    public Entry(String activity, int status, String content, int type) {
        this.activity = activity;
        this.title = activity;
        this.content = content;
        this.status = status;
        this.type = type;
    }


    public Entry(String title, String content) {
        this.title = title;
        this.content = content;
        this.type = TYPE_NOTE;
    }

    public Entry(String activity, int status) {
        this.activity = activity;
        this.status = status;
        this.type = TYPE_TODO;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

}
