package com.kalashnyk.denys.airqualitymonitoring.common;

import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;

import java.io.File;
import java.util.ArrayList;

import io.realm.RealmResults;


public interface IBaseView {

    interface IMainFragmentView extends IBaseView {
        void updateGraphic(AirQualityRealm qualityRealm);
        void highlightedGPSSwitch();
        void notHighlightedGPSSwitch();
        void highlightedUSBSwitch();
        void notHighlightedUSBSwitch();
        void showProgress();
        void hideProgress();
        void showError(String error);
    }

    interface IListView extends IBaseView {
        void showRealmResult(RealmResults<AirQualityRealm> realms);
        void showProgress();
        void hideProgress();
        void showError(String error);
    }

    interface IListFilesView extends IBaseView {
        void showListFiles(ArrayList<File> list);
        void showProgress();
        void hideProgress();
        void updateAfterRemoveItem(int position);
        void updateAfterRemoveAllItems();
        void showError(String error);
    }

    interface ISharingView extends IBaseView {}

    interface IFileView extends IBaseView {
        void showMsg(String msg);
    }

}
