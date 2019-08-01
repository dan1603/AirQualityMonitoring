package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.write_file_di;


import android.app.Application;

import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.FilePresenterImpl;
import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;

import dagger.Module;
import dagger.Provides;

@Module
public class FileModule {

    @Provides
    @FileScope
    IPresenterContract.IFilePresenter provideSharingPresenter(IFileController fileController, IRealmService realmService, Application app) {
        return new FilePresenterImpl(fileController, realmService, app);
    }

}
