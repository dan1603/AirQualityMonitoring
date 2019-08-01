package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.write_file_di;


import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.FileDialog;

import dagger.Subcomponent;

@FileScope
@Subcomponent(
        modules = FileModule.class
)
public interface FileComponent {

    void inject(FileDialog fragment);
}
