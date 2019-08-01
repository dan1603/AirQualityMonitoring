package com.kalashnyk.denys.airqualitymonitoring.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.kalashnyk.denys.airqualitymonitoring.app.AppAirQualityMonitoring;
import com.kalashnyk.denys.airqualitymonitoring.app.app_di.AppComponent;


public abstract class BaseActivity extends AppCompatActivity {
    protected FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(AppAirQualityMonitoring.get(this).getAppComponent());
        fragmentManager = getSupportFragmentManager();
        initView();
    }

    protected abstract void setupComponent(AppComponent appComponent);

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void addFragment(int id, Fragment fragment){
        fragmentManager.beginTransaction().add(id, fragment).commit();
    }
}
