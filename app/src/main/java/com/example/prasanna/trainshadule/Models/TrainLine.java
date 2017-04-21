package com.example.prasanna.trainshadule.Models;

/**
 * Created by prasanna on 4/21/17.
 */

public class TrainLine {
    private int id;
    private String line_id;
    private String line_name;

    public TrainLine(String line_id, String line_name) {
        this.line_id = line_id;
        this.line_name = line_name;
    }

    public TrainLine(int id, String line_id, String line_name) {
        this.id = id;
        this.line_id = line_id;
        this.line_name = line_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_name() {
        return line_name;
    }

    public void setLine_name(String line_name) {
        this.line_name = line_name;
    }
}
