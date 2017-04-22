package com.example.prasanna.trainshadule.Models;

/**
 * Created by prasanna on 4/22/17.
 */

public class TrainSchedule {
    private int id;
    private String name;
    private String arrival;
    private String departure;
    private String destination;
    private String delay;
    private String comment;
    private String fdescriptioneng;
    private String tydescriptioneng;
    private String frtrstationnameeng;
    private String totrstationnameeng;

    public TrainSchedule(int id, String name, String arrival, String departure, String destination, String delay, String comment, String fdescriptioneng, String tydescriptioneng, String frtrstationnameeng, String totrstationnameeng) {
        this.id = id;
        this.name = name;
        this.arrival = arrival;
        this.departure = departure;
        this.destination = destination;
        this.delay = delay;
        this.comment = comment;
        this.fdescriptioneng = fdescriptioneng;
        this.tydescriptioneng = tydescriptioneng;
        this.frtrstationnameeng = frtrstationnameeng;
        this.totrstationnameeng = totrstationnameeng;
    }

    public TrainSchedule(String name, String arrival, String departure, String destination, String delay, String comment, String fdescriptioneng, String tydescriptioneng, String frtrstationnameeng, String totrstationnameeng) {
        this.name = name;
        this.arrival = arrival;
        this.departure = departure;
        this.destination = destination;
        this.delay = delay;
        this.comment = comment;
        this.fdescriptioneng = fdescriptioneng;
        this.tydescriptioneng = tydescriptioneng;
        this.frtrstationnameeng = frtrstationnameeng;
        this.totrstationnameeng = totrstationnameeng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFdescriptioneng() {
        return fdescriptioneng;
    }

    public void setFdescriptioneng(String fdescriptioneng) {
        this.fdescriptioneng = fdescriptioneng;
    }

    public String getTydescriptioneng() {
        return tydescriptioneng;
    }

    public void setTydescriptioneng(String tydescriptioneng) {
        this.tydescriptioneng = tydescriptioneng;
    }

    public String getFrtrstationnameeng() {
        return frtrstationnameeng;
    }

    public void setFrtrstationnameeng(String frtrstationnameeng) {
        this.frtrstationnameeng = frtrstationnameeng;
    }

    public String getTotrstationnameeng() {
        return totrstationnameeng;
    }

    public void setTotrstationnameeng(String totrstationnameeng) {
        this.totrstationnameeng = totrstationnameeng;
    }
}

