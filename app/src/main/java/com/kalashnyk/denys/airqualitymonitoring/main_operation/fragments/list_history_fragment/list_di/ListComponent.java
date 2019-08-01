package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.list_di;


import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.ListFragment;

import dagger.Subcomponent;

@ListScope
@Subcomponent(
        modules = ListModule.class
)
public interface ListComponent {

    void inject(ListFragment fragment);
}
