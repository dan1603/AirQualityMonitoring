package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog.sharing_di;


import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog.SharingDialog;

import dagger.Subcomponent;

@SharingScope
@Subcomponent(
        modules = SharingModule.class
)
public interface SharingComponent {

    void inject(SharingDialog fragment);
}
