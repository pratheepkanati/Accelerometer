package com.lirctek.ics.database;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
@Entity
public class DriveEvent {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventTimeStamp() {
        return eventTimeStamp;
    }

    public void setEventTimeStamp(long eventTimeStamp) {
        this.eventTimeStamp = eventTimeStamp;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getAcc() {
        return acc;
    }

    public void setAcc(float acc) {
        this.acc = acc;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    @Id
    private long id;
    private String eventName;
    private long eventTimeStamp;
    private  String eventTime;
    private int speed;

    public int getChangeinSpeed() {
        return changeinSpeed;
    }

    public void setChangeinSpeed(int changeinSpeed) {
        this.changeinSpeed = changeinSpeed;
    }

    private int changeinSpeed;
    private float acc;
    private double gravity;

    public  static  void  insert(DriveEvent driveEvent){
        Box<DriveEvent> sensorDataBox=ObjectBox.get().boxFor(DriveEvent.class);
        sensorDataBox.put(driveEvent);
    }
}
