package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog.sharing_di;


import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog.SharingPresenterImpl;
import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.social_controller.ISocialController;

import dagger.Module;
import dagger.Provides;

@Module
public class SharingModule {

    @Provides
    @SharingScope
    IPresenterContract.ISharingPresenter provideSharingPresenter(ISocialController socialController, IRealmService realmService) {
        return new SharingPresenterImpl(socialController, realmService);
    }

}
