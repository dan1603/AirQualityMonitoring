package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.main_fragment_di;


import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.MainFragment;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.MainFragmentPresenterImpl;
import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.INetworkCheck;

import dagger.Module;
import dagger.Provides;

@Module
public class MainFragmentModule {

    private MainFragment mainFragment;

    public MainFragmentModule(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    @Provides
    @MainFragmentScope
    MainFragment provideMainFragment() {
        return mainFragment;
    }

    @Provides
    @MainFragmentScope
    IPresenterContract.IMainPresenter provideMainPresenter(MainFragment mainFragment, IRealmService realmService, INetworkCheck networkCheck, IFileController fileController) {
        return new MainFragmentPresenterImpl(mainFragment, realmService, networkCheck, fileController);
    }

}
