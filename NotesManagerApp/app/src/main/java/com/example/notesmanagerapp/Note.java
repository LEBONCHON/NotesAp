package com.example.notesmanagerapp;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String id;
    private String title;
    private String content;
    private Date createdDate;
    private Date modifiedDate;

    public Note() {
        this.id = String.valueOf(System.currentTimeMillis());
        this.createdDate = new Date();
        this.modifiedDate = new Date();
    }

    public Note(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { 
        this.title = title;
        this.modifiedDate = new Date();
    }

    public String getContent() { return content; }
    public void setContent(String content) { 
        this.content = content;
        this.modifiedDate = new Date();
    }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public Date getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { this.modifiedDate = modifiedDate; }

    @Override
    public String toString() {
        return title != null ? title : "Untitled Note";
    }
}