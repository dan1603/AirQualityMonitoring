package com.kalashnyk.denys.airqualitymonitoring.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AirQualityRealm extends RealmObject {

    @PrimaryKey
    long id;
    String date;
    String time;
    double timeSeconds;
    double latitude;
    double longitude;
    float distanceByStart;
    double p10;
    double p2_5;
    //pm5, pm10, check, pm5h, pm5l, pm10l, pm10h, res1, res2;
    double check;
    double pm5h;
    double pm5l;
    double pm10l;
    double pm10h;
    double res1;
    double res2;
    String address;
    public AirQualityRealm() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getP10() {
        return p10;
    }

    public void setPM10(double p10) {
        this.p10 = p10;
    }

    public double getP2_5() {
        return p2_5;
    }

    public void setPM2_5(double p2_5) {
        this.p2_5 = p2_5;
    }

    public float getDistanceByStart() {
        return distanceByStart;
    }

    public void setDistanceByStart(float distanceByStart) {
        this.distanceByStart = distanceByStart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setP10(double p10) {
        this.p10 = p10;
    }

    public void setP2_5(double p2_5) {
        this.p2_5 = p2_5;
    }

    public double getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(double timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public double getCheck() {
        return check;
    }

    public void setCheck(double check) {
        this.check = check;
    }

    public double getPm5h() {
        return pm5h;
    }

    public void setPm5h(double pm5h) {
        this.pm5h = pm5h;
    }

    public double getPm5l() {
        return pm5l;
    }

    public void setPm5l(double pm5l) {
        this.pm5l = pm5l;
    }

    public double getPm10l() {
        return pm10l;
    }

    public void setPm10l(double pm10l) {
        this.pm10l = pm10l;
    }

    public double getPm10h() {
        return pm10h;
    }

    public void setPm10h(double pm10h) {
        this.pm10h = pm10h;
    }

    public double getRes1() {
        return res1;
    }

    public void setRes1(double res1) {
        this.res1 = res1;
    }

    public double getRes2() {
        return res2;
    }

    public void setRes2(double res2) {
        this.res2 = res2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AirQualityRealm{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", timeSeconds=" + timeSeconds +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distanceByStart=" + distanceByStart +
                ", p10=" + p10 +
                ", p2_5=" + p2_5 +
                ", check=" + check +
                ", pm5h=" + pm5h +
                ", pm5l=" + pm5l +
                ", pm10l=" + pm10l +
                ", pm10h=" + pm10h +
                ", res1=" + res1 +
                ", res2=" + res2 +
                ", address='" + address + '\'' +
                '}';
    }
}
