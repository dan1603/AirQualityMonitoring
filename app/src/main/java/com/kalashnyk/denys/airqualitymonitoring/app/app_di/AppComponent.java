package com.kalashnyk.denys.airqualitymonitoring.app.app_di;

import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainComponent;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainModule;

import dagger.Component;

@AppScope
@Component(
        modules = {
                AppModule.class,
        }
)
public interface AppComponent {

        MainComponent plus(MainModule module);
}
