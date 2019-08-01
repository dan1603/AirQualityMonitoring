package com.kalashnyk.denys.airqualitymonitoring.common;

import android.hardware.usb.UsbManager;

import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;

/**
 * Created by StupidUser'sBrain on 25.09.2017.
 */

public interface IPresenterContract{

    void dismiss();

    interface IMainPresenter<V extends IBaseView.IMainFragmentView> extends IPresenterContract {
        void getLocation();
        void getUSBData(UsbManager usbManager);
        void stopUsingGPS();
        void stopUSBManager();
        AirQualityRealm generatedData();
        boolean canGetLocation();
        double getLatitude();
        double getLongitude();
        void init(V view);
    }

    interface IListPresenter<V extends IBaseView.IListView> extends IPresenterContract {
        void getListQualities();
        void doDeleteItem(long itemId);
        void doDeleteAllItems();
        void init(V view);
    }

    interface ISharingPresenter<V extends IBaseView.ISharingView> extends IPresenterContract {
        void doSharing(int i);
        void init(V view);
    }

    interface IFilePresenter<V extends IBaseView.IFileView> extends IPresenterContract {
        void doWriteFile(int i);
        void init(V view);
    }

    interface IListFilesPresenter<V extends IBaseView.IListFilesView> extends IPresenterContract {
        void getListFiles();
        void doDeleteItem(String fileName, int position);
        void doDeleteAllItems();
        void openFile(String fileName);
        void sharingFile(String fileName);
        void init(V view);
    }
}
