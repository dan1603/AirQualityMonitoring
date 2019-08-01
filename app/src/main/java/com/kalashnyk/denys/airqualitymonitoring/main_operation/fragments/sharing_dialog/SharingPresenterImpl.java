package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog;

import android.util.Log;

import com.kalashnyk.denys.airqualitymonitoring.common.BasePresenter;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;
import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.social_controller.ISocialController;

import javax.inject.Inject;


public class SharingPresenterImpl extends BasePresenter<IBaseView.ISharingView> implements IPresenterContract.ISharingPresenter {

    @Inject
    ISocialController controller;
    private String msg;

    public SharingPresenterImpl(ISocialController socialController, IRealmService realmService) {
        this.controller = socialController;
        this.realmService = realmService;
    }

    @Override
    public void doSharing(int i) {
        this.getSharingMsg(i);
    }

    @Override
    public void init(IBaseView.ISharingView view) {
        super.init(view);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void getSharingMsg(int i) {

        realmService.getLastObject(AirQualityRealm.class)
                .subscribe(airquality -> {
                    if (airquality != null) {
                        controller.doSharing(i, airquality);
                    }
                }, throwable -> {
                    Log.d("GETLAST", " ERROR " + throwable.getMessage());
                });

    }
}
