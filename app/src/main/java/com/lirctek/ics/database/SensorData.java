package com.lirctek.ics.database;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class SensorData {
    @Id
    private long id;

    public SensorData() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private long timestamp;
    private String dateandtime;
    private float x_acc;
    private float y_acc;
    private float z_acc;
    private float x_gra;
    private float y_gra;
    private float z_gra;
    private float x_acc_g;
    private float y_acc_g;
    private float z_acc_g;
    private float speed;

    public boolean isSpeedlimit() {
        return speedlimit;
    }

    public void setSpeedlimit(boolean speedlimit) {
        this.speedlimit = speedlimit;
    }

    private boolean speedlimit;

    public int getBearing() {
        return bearing;
    }

    public void setBearing(int bearing) {
        this.bearing = bearing;
    }

    public boolean isHashbreak() {
        return hashbreak;
    }

    public void setHashbreak(boolean hashbreak) {
        this.hashbreak = hashbreak;
    }

    public boolean isHarshacceleration() {
        return harshacceleration;
    }

    public void setHarshacceleration(boolean harshacceleration) {
        this.harshacceleration = harshacceleration;
    }

    public boolean isHarshturn() {
        return harshturn;
    }

    public void setHarshturn(boolean harshturn) {
        this.harshturn = harshturn;
    }

    private int bearing;
    private boolean hashbreak;
    private  boolean harshacceleration;

    public boolean isHashbreakAcc() {
        return hashbreakAcc;
    }

    public void setHashbreakAcc(boolean hashbreakAcc) {
        this.hashbreakAcc = hashbreakAcc;
    }

    public boolean isHarshaccelerationAcc() {
        return harshaccelerationAcc;
    }

    public void setHarshaccelerationAcc(boolean harshaccelerationAcc) {
        this.harshaccelerationAcc = harshaccelerationAcc;
    }

    private boolean hashbreakAcc;
    private  boolean harshaccelerationAcc;
    private boolean harshturn;

    public float getX_acc_sf() {
        return x_acc_sf;
    }

    public void setX_acc_sf(float x_acc_sf) {
        this.x_acc_sf = x_acc_sf;
    }

    public float getY_acc_sf() {
        return y_acc_sf;
    }

    public void setY_acc_sf(float y_acc_sf) {
        this.y_acc_sf = y_acc_sf;
    }

    public float getZ_acc_sf() {
        return z_acc_sf;
    }

    public void setZ_acc_sf(float z_acc_sf) {
        this.z_acc_sf = z_acc_sf;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    private float x_acc_sf;
    private float y_acc_sf;
    private float z_acc_sf;
    private float  pitch;
    private float  roll;
    private float yaw;

    public float getPitchQ() {
        return pitchQ;
    }

    public void setPitchQ(float pitchQ) {
        this.pitchQ = pitchQ;
    }

    public float getRollQ() {
        return rollQ;
    }

    public void setRollQ(float rollQ) {
        this.rollQ = rollQ;
    }

    public float getYawQ() {
        return yawQ;
    }

    public void setYawQ(float yawQ) {
        this.yawQ = yawQ;
    }

    private float  pitchQ;
    private float  rollQ;
    private float yawQ;


    public int getSpeedinMiles() {
        return speedinMiles;
    }

    public void setSpeedinMiles(int speedinMiles) {
        this.speedinMiles = speedinMiles;
    }

    private int speedinMiles;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getX_acc() {
        return x_acc;
    }

    public void setX_acc(float x_acc) {
        this.x_acc = x_acc;
    }

    public float getY_acc() {
        return y_acc;
    }

    public void setY_acc(float y_acc) {
        this.y_acc = y_acc;
    }

    public float getZ_acc() {
        return z_acc;
    }

    public void setZ_acc(float z_acc) {
        this.z_acc = z_acc;
    }

    public float getX_gra() {
        return x_gra;
    }

    public void setX_gra(float x_gra) {
        this.x_gra = x_gra;
    }

    public float getY_gra() {
        return y_gra;
    }

    public void setY_gra(float y_gra) {
        this.y_gra = y_gra;
    }

    public float getZ_gra() {
        return z_gra;
    }

    public void setZ_gra(float z_gra) {
        this.z_gra = z_gra;
    }

    public float getX_acc_g() {
        return x_acc_g;
    }

    public void setX_acc_g(float x_acc_g) {
        this.x_acc_g = x_acc_g;
    }

    public float getY_acc_g() {
        return y_acc_g;
    }

    public void setY_acc_g(float y_acc_g) {
        this.y_acc_g = y_acc_g;
    }

    public float getZ_acc_g() {
        return z_acc_g;
    }

    public void setZ_acc_g(float z_acc_g) {
        this.z_acc_g = z_acc_g;
    }
    public static void  insert(SensorData sensorData){
        Box<SensorData>sensorDataBox=ObjectBox.get().boxFor(SensorData.class);
        sensorDataBox.put(sensorData);
    }
    public static List<SensorData> get(){
        Box<SensorData>sensorDataBox=ObjectBox.get().boxFor(SensorData.class);
        return sensorDataBox.query().orderDesc(SensorData_.timestamp).build().find(0,40);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }
}
