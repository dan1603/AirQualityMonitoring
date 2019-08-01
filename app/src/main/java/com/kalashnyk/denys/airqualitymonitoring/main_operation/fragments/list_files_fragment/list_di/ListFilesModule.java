package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.list_di;


import android.app.Application;

import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.ListFilesFragment;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.ListFilesPresenterImpl;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;

import dagger.Module;
import dagger.Provides;

@Module
public class ListFilesModule {
    private ListFilesFragment fragment;

    public ListFilesModule(ListFilesFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @ListFilesScope
    ListFilesFragment provideMainActivity() {
        return fragment;
    }

    @Provides
    @ListFilesScope
    IPresenterContract.IListFilesPresenter provideListPresenter(ListFilesFragment fragment, IFileController fileController) {
        return new ListFilesPresenterImpl(fragment, fileController);
    }

}
