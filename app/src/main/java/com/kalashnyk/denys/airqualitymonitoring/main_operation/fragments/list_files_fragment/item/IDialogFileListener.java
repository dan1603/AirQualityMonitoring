package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.item;

/**
 * Created by User on 12.11.2017.
 */

public interface IDialogFileListener<M> {

    void deleteItem(String name, int position);
    void openDeleteItemDialog(M m, int position);
    void openSharingFileDialog(M m,  int position);
    void deleteAllFiles();
    void openFile(String name);
    void sharingFile(String name);
    void selectedOrUnselectedItem(boolean b, int position);
}
