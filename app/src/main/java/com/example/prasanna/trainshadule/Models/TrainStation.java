package com.example.prasanna.trainshadule.Models;

/**
 * Created by prasanna on 4/21/17.
 */

public class TrainStation {
    private int id;
    private String stationCode;
    private String stationName;
    private String lineCode;

    public TrainStation(int id, String stationCode, String stationName, String lineCode) {
        this.id = id;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.lineCode = lineCode;
    }

    public TrainStation(String stationCode, String stationName, String lineCode) {
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.lineCode = lineCode;
    }

    public int getId() {
        return id;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
