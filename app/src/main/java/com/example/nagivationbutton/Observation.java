package com.example.nagivationbutton;

public class Observation {
    private int Id;
    private String Name;
    private String Time;
    private String Comment;

    private int hikingID ;


    public Observation(int id, String name, String time, String comment, int hikingID) {
        this.Id = id;
        this.Name = name;
        this.Time = time;
        this.Comment = comment;
        this.hikingID = hikingID;
    }
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public int getHikingID() {
        return hikingID;
    }

    public void setHikingID(int hikingID) {
        this.hikingID = hikingID;
    }
}
