package com.kalashnyk.denys.airqualitymonitoring.utils.usb_utils;

/**
 * Created by ShiftHackZ on 31.10.2017.
 */

public class PMData {


    double pm5, pm10, check, pm5h, pm5l, pm10l, pm10h, res1, res2;

    public PMData() {}

    public double getPm5() {
        return pm5;
    }

    public void setPm5(double pm5) {
        this.pm5 = pm5;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
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
}
