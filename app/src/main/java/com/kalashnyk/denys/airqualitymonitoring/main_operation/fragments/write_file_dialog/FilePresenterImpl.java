package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog;

import android.app.Application;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.common.BasePresenter;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQuality;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;
import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;

import java.util.ArrayList;


public class FilePresenterImpl extends BasePresenter<IBaseView.IFileView> implements IPresenterContract.IFilePresenter {

    public FilePresenterImpl(IFileController fileController, IRealmService realmService, Application app) {
        this.fileController = fileController;
        this.realmService = realmService;
        this.app = app;
    }

    @Override
    public void doWriteFile(int i) {
        ArrayList<AirQuality> list = new ArrayList<>();
        realmService.getObjects(AirQualityRealm.class)
                .doOnError(Throwable::getStackTrace)
                .subscribe(
                        next -> {
                            for (AirQualityRealm a : next) {
                                AirQuality airQuality = new AirQuality(
                                        a.getId(), a.getDate(), a.getTime(),
                                        a.getLatitude(), a.getLongitude(), a.getDistanceByStart(),
                                        a.getP10(), a.getP2_5(), a.getCheck(), a.getPm5h(), a.getPm5l(),
                                        a.getPm10l(), a.getPm10h(), a.getRes1(), a.getRes2(), a.getAddress());

                                list.add(airQuality);
                            }
                        }, Throwable::getStackTrace,
                        () -> {
                            if(list.size() != 0) {
                                String fileName = "AirLogger_" + list.get(list.size() - 1).getDate() +
                                        "_" + list.get(list.size() - 1).getTime() +
                                        "_" + list.get(list.size() - 1).getId();
                                switch (i) {
                                    case 1:
                                        fileController.writeXMLFile(fileName, list);
                                        view.showMsg(app.getResources().getString(R.string.create_file_xml));
                                        break;
                                    case 2:
//                                        for (AirQuality a : list) {
//                                            String substring = a.getAddress().replaceAll(",", " ");
//                                            Log.d("REPLACE", substring);
//                                        }
                                        fileController.writeCSVFile(fileName, list);
                                        view.showMsg(app.getResources().getString(R.string.create_file_csv));
                                        break;
                                    case 3:
                                        fileController.writeKMLFile(fileName, list);
                                        view.showMsg(app.getResources().getString(R.string.create_file_kml));
                                        break;
                                }
                            }else {
                                view.showMsg(app.getResources().getString(R.string.create_file_error));
                            }
                        }
                );

    }

    @Override
    public void init(IBaseView.IFileView view) {
        super.init(view);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
