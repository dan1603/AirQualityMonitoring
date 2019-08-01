package com.kalashnyk.denys.airqualitymonitoring.app;

import android.app.Application;
import android.content.Context;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.app.app_di.AppComponent;
import com.kalashnyk.denys.airqualitymonitoring.app.app_di.AppModule;
import com.kalashnyk.denys.airqualitymonitoring.app.app_di.DaggerAppComponent;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppAirQualityMonitoring extends Application {
    private AppComponent appComponent;

    public static AppAirQualityMonitoring get(Context context) {
        return (AppAirQualityMonitoring) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig =
                new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                        getString(R.string.twitter_consumer_secret));

        Fabric.with(this, new Twitter(authConfig));
        initRealmConfiguration();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void initRealmConfiguration() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
