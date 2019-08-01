package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.list_di;


import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.ListPresenterImpl;
import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.INetworkCheck;

import dagger.Module;
import dagger.Provides;

@Module
public class ListModule {

    @Provides
    @ListScope
    IPresenterContract.IListPresenter provideListPresenter(IRealmService realmService, INetworkCheck networkCheck) {
        return new ListPresenterImpl(realmService, networkCheck);
    }

}
