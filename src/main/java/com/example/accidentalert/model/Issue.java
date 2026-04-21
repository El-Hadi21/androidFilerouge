package com.example.accidentalert.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Issue implements Parcelable {
    private String title;
    private String description;
    private String location;
    private String type;
    private float status;
    private int priority;
    private long timestamp;

    public Issue(String title, String description, String location, String type, float status, int priority, long timestamp) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.timestamp = timestamp;
    }

    protected Issue(Parcel in) {
        title = in.readString();
        description = in.readString();
        location = in.readString();
        type = in.readString();
        status = in.readFloat();
        priority = in.readInt();
        timestamp = in.readLong();
    }

    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel in) {
            return new Issue(in);
        }

        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeString(type);
        dest.writeFloat(status);
        dest.writeInt(priority);
        dest.writeLong(timestamp);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getStatus() {
        return status;
    }

    public void setStatus(float status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return title;
    }
}
