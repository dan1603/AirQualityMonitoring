package com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di;


import com.kalashnyk.denys.airqualitymonitoring.main_operation.MainActivity;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.list_di.ListFilesComponent;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.list_di.ListFilesModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.list_di.ListComponent;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.list_di.ListModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.main_fragment_di.MainFragmentComponent;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.main_fragment_di.MainFragmentModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog.sharing_di.SharingComponent;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog.sharing_di.SharingModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.write_file_di.FileComponent;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.write_file_di.FileModule;

import dagger.Subcomponent;

@MainScope
@Subcomponent(
        modules = MainModule.class
)
public interface MainComponent {

    void inject(MainActivity mainActivity);

    MainFragmentComponent plus(MainFragmentModule mainFragmentModule);

    ListComponent plus(ListModule listModule);

    ListFilesComponent plus(ListFilesModule listModule);

    SharingComponent plus(SharingModule sharingModule);

    FileComponent plus(FileModule fileModule);
}
