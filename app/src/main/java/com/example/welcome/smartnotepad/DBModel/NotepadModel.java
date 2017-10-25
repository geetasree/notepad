package com.example.welcome.smartnotepad.DBModel;

/**
 * Created by Welcome on 18-Jun-17.
 */

public class NotepadModel {
    private String title;
    private String description;
    private int id;
    private String ts;

    public NotepadModel( String title, String description) {
        this.title = title;
        this.description = description;
    }

    public NotepadModel( int id, String title, String description,String ts) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.ts=ts;
    }

    public int getId() {
        return id;
    }

    public String getTs() {
        return ts;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
