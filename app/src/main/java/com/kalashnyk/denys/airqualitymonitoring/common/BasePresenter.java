package com.kalashnyk.denys.airqualitymonitoring.common;


import android.app.Application;

import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.INetworkCheck;

public abstract class BasePresenter<V extends IBaseView> {

    protected V view;
    protected IRealmService realmService;
    protected INetworkCheck networkCheck;
    protected IFileController fileController;
    protected Application app;

    public void init(V view) {
        this.view = view;
    }


    public void dismiss() {
        this.view = null;
    }
}
