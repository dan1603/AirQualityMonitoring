package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.main_fragment_di;


import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.MainFragment;

import dagger.Subcomponent;

@MainFragmentScope
@Subcomponent(
        modules = MainFragmentModule.class
)
public interface MainFragmentComponent {

    void inject(MainFragment fragment);
}
