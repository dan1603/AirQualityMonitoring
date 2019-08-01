package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment;

import android.util.Log;

import com.kalashnyk.denys.airqualitymonitoring.common.BasePresenter;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;
import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.INetworkCheck;

import javax.inject.Inject;

/**
 * Created by User on 22.10.2017.
 */

public class ListPresenterImpl extends BasePresenter<IBaseView.IListView> implements IPresenterContract.IListPresenter {

    @Inject
    public ListPresenterImpl(IRealmService realmService, INetworkCheck networkCheck) {
        this.realmService = realmService;
        this.networkCheck = networkCheck;
    }

    @Override
    public void init(IBaseView.IListView view) {
        super.init(view);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void getListQualities() {
        realmService.getObjects(AirQualityRealm.class)
                .doOnError(throwable -> {
                    Log.d("AirQuality", " THROWABLE " + throwable.getMessage());
                })
                .subscribe(
                        next -> {
//                            ArrayList<AirQualityRealm> list = new ArrayList<>();
//                            for (AirQualityRealm a : next) {
//                                list.add(a);
//                            }
                            Log.d("AirQuolity", " ARRAY " +  next);
                            view.showRealmResult(next);
//                            AirQualityRealm root = list.get(0);
//
//                            ArrayList<Float> distance = new ArrayList<Float>();
//                            for(int i = 0; i < list.size()-1; i++){
//                                Location loc1 = new Location("");
//                                loc1.setLatitude( root.getLatitude());
//                                loc1.setLongitude(root.getLongitude());
//
//                                Location loc2 = new Location("");
//                                loc2.setLatitude(list.get(i).getLatitude());
//                                loc2.setLongitude(list.get(i).getLongitude());
//
//                                float distanceInMeters = loc1.distanceTo(loc2);
//
//                                distance.add(distanceInMeters);
//                                list.get(i).setDistanceByStart(distanceInMeters);
//                                Log.d("AirQuolity", " ARRAY " +  list.get(i));
//
//                            }
                        },
                        throwable -> {
                            Log.d("AirQuolity", " THROWABLE " + throwable.getMessage());
                        }
                );
    }

    @Override
    public void doDeleteItem(long itemId) {
        realmService.deleteObject(itemId, AirQualityRealm.class).subscribe();
    }

    @Override
    public void doDeleteAllItems() {
        realmService.deleteAllObjects(AirQualityRealm.class).subscribe();
    }
}
