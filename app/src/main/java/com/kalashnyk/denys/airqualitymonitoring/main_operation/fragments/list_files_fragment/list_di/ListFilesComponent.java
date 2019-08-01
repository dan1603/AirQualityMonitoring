package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.list_di;


import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.ListFilesFragment;

import dagger.Subcomponent;

@ListFilesScope
@Subcomponent(
        modules = ListFilesModule.class
)
public interface ListFilesComponent {

    void inject(ListFilesFragment fragment);
}
