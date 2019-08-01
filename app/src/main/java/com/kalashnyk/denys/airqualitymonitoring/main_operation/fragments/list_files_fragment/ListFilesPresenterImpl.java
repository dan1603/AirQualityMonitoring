package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment;

import com.kalashnyk.denys.airqualitymonitoring.common.BasePresenter;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;

import javax.inject.Inject;


public class ListFilesPresenterImpl extends BasePresenter<IBaseView.IListFilesView> implements IPresenterContract.IListFilesPresenter {

    private ListFilesFragment fragment;

    @Inject
    public ListFilesPresenterImpl(ListFilesFragment fragment, IFileController fileController) {
        this.fileController = fileController;
        this.fragment = fragment;
    }

    @Override
    public void getListFiles() {
        view.showListFiles(fileController.getContentDirectory());
    }

    @Override
    public void doDeleteItem(String fileName, int position) {
        boolean isDeleted = fileController.deleteSingleFile(fileName);
        if (isDeleted) {
            view.updateAfterRemoveItem(position);
        }
    }

    @Override
    public void doDeleteAllItems() {
        fileController.deleteAllFiles();
        view.updateAfterRemoveAllItems();
    }

    @Override
    public void openFile(String fileName) {
        fileController.openFile(fileName, fragment);
    }

    @Override
    public void sharingFile(String fileName) {
        fileController.shareFile(fileName, fragment);
    }

    @Override
    public void init(IBaseView.IListFilesView view) {
        super.init(view);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


}
