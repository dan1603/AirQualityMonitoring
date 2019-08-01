package com.kalashnyk.denys.airqualitymonitoring.app.app_di;

import android.app.Application;

import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.realm.RealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.FileControllerImpl;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.INetworkCheck;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.NetworkCheckImpl;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @AppScope
    public Application provideApplication() {
        return application;
    }

    @Provides
    @AppScope
    IFileController provideFileController() {
        return new FileControllerImpl();
    }

    @Provides
    @AppScope
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @AppScope
    IRealmService provideRealmService(final Realm realm) {
        return new RealmService(realm);
    }

    @Provides
    @AppScope
    INetworkCheck provideNetworkCheck() { return new NetworkCheckImpl(application); }
}
