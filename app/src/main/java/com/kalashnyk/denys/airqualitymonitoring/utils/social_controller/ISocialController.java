package com.kalashnyk.denys.airqualitymonitoring.utils.social_controller;

import android.content.Intent;

import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;

public interface ISocialController {

    void doSharing(int i,AirQualityRealm airQualityRealm);

    void onActivityResult(int requestCode, int resultCode, Intent data, AirQualityRealm airQualityRealm);
}
