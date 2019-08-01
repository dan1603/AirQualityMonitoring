package com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di;


import com.kalashnyk.denys.airqualitymonitoring.main_operation.MainActivity;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.INetworkCheck;
import com.kalashnyk.denys.airqualitymonitoring.utils.social_controller.ISocialController;
import com.kalashnyk.denys.airqualitymonitoring.utils.social_controller.SocialControllerImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainActivity mainActivity;

    public MainModule(MainActivity activity) {
        this.mainActivity = activity;
    }

    @Provides
    @MainScope
    MainActivity provideMainActivity() {
        return mainActivity;
    }

    @Provides
    @MainScope
    ISocialController provideSocialController(MainActivity activity, INetworkCheck networkCheck, IFileController fileController) {
        return new SocialControllerImpl(activity, networkCheck, fileController);
    }

}
