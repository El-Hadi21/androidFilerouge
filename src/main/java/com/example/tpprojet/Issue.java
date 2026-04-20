package com.example.tpprojet;

public class Issue {
    private String id;
    private String title;
    private String date;
    private String description;
    private float gravity;
    private int imageResId;

    public Issue(String id, String title, String date, String description, float gravity, int imageResId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.gravity = gravity;
        this.imageResId = imageResId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
