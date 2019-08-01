package com.kalashnyk.denys.airqualitymonitoring.utils.realm_adapter;

import android.content.Context;

import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;

import io.realm.RealmResults;

public class RealmAirQualitiesAdapter extends RealmModelAdapter<AirQualityRealm> {

    public RealmAirQualitiesAdapter(Context context, RealmResults<AirQualityRealm> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

}