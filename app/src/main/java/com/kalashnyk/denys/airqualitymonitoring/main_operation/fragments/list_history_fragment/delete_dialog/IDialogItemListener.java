package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog;

/**
 * Created by User on 12.11.2017.
 */

public interface IDialogItemListener<M> {

    void deleteItem(long id);
    void openDeleteItemDialog(M m);
    void deleteAllItems();
}
