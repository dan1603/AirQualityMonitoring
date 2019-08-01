package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.kalashnyk.denys.airqualitymonitoring.common.BasePresenter;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;
import com.kalashnyk.denys.airqualitymonitoring.realm.IRealmService;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.INetworkCheck;
import com.kalashnyk.denys.airqualitymonitoring.utils.usb_utils.CallbackResult;
import com.kalashnyk.denys.airqualitymonitoring.utils.usb_utils.PMData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainFragmentPresenterImpl extends BasePresenter<IBaseView.IMainFragmentView> implements IPresenterContract.IMainPresenter, LocationListener {

    private MainFragment mainFragment;
    private LocationManager locationManager;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location location;
    private double latitude;
    private double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private ReadAsync readAsync;
    private AirQualityRealm airQualityRealm;
    private float distanceInMeters;
    private long secondsByStart;

    @Inject
    public MainFragmentPresenterImpl(MainFragment mainFragment, IRealmService realmService, INetworkCheck networkCheck, IFileController fileController) {
        this.realmService = realmService;
        this.networkCheck = networkCheck;
        this.fileController = fileController;
        this.mainFragment = mainFragment;

        this.locationManager = (LocationManager) mainFragment.getActivity()
                .getSystemService(mainFragment.getActivity().getApplication().LOCATION_SERVICE);

        this.isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        this.isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        this.airQualityRealm = new AirQualityRealm();
    }

    @Override
    public void init(IBaseView.IMainFragmentView view) {

        super.init(view);
    }

    @Override
    public void getLocation() {

        Observable.just(locationManager)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(manager -> {
                            if (!isGPSEnabled && !isNetworkEnabled) {
                            } else {
                                this.canGetLocation = true;
                                view.highlightedGPSSwitch();
                                if (isNetworkEnabled) {
                                    manager.requestLocationUpdates(
                                            LocationManager.NETWORK_PROVIDER,
                                            MIN_TIME_BW_UPDATES,
                                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                                    if (manager != null) {
                                        location = manager
                                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                        airQualityRealm.setLatitude(location.getLatitude());
                                        airQualityRealm.setLongitude(location.getLongitude());
                                    }
                                }
                                if (isGPSEnabled) {
                                    if (location == null) {
                                        manager.requestLocationUpdates(
                                                LocationManager.GPS_PROVIDER,
                                                MIN_TIME_BW_UPDATES,
                                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                                        if (manager != null) {
                                            location = manager
                                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                            airQualityRealm.setLatitude(location.getLatitude());
                                            airQualityRealm.setLongitude(location.getLongitude());
                                        }
                                    }
                                }
                            }
                        }, Throwable::printStackTrace
                );
    }

    @Override
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(MainFragmentPresenterImpl.this);
            this.canGetLocation = false;
            view.notHighlightedGPSSwitch();
        }
    }

    @Override
    public void stopUSBManager() {
        if (readAsync != null) {
            readAsync.cancel(false);
        }
    }

    @Override
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    @Override
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    @Override
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
//        view.highlightedGPSSwitch();
    }

    @Override
    public void onProviderDisabled(String s) {
//        view.notHighlightedGPSSwitch();
    }

    @Override
    public void getUSBData(UsbManager usbManager) {
        if (usbManager != null) readAsync = (ReadAsync) new ReadAsync(data -> {

            if (data != null) { //check, pm5h, pm5l, pm10l, pm10h, res1, res2;
                airQualityRealm.setPM2_5(data != null ? data.getPm5() : 0.0d);
                airQualityRealm.setPM10(data != null ? data.getPm10() : 0.0d);
                airQualityRealm.setCheck(data != null ? data.getCheck() : 0.0d);
                airQualityRealm.setPm5h(data != null ? data.getPm5h() : 0.0d);
                airQualityRealm.setPm5l(data != null ? data.getPm5l() : 0.0d);
                airQualityRealm.setPm10l(data != null ? data.getPm10l() : 0.0d);
                airQualityRealm.setPm10h(data != null ? data.getPm10h() : 0.0d);
                airQualityRealm.setRes1(data != null ? data.getRes1() : 0.0d);
                airQualityRealm.setRes2(data != null ? data.getRes2() : 0.0d);
            }


        }).execute(usbManager);
        else {
            Toast.makeText(mainFragment.getActivity(), "USB null", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public AirQualityRealm generatedData() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar c = Calendar.getInstance(Locale.UK);
        Date realTime = c.getTime();
        airQualityRealm.setTimeSeconds(c.getTimeInMillis());
        airQualityRealm.setTime(timeFormat.format(realTime));
        airQualityRealm.setDate(dateFormat.format(realTime));


        realmService.getObjects(AirQualityRealm.class).doOnError(throwable -> {
            Log.d("AIRLOGGER", " ERROR " + throwable.getMessage());
        }).subscribe(next -> {
            float distance = 0;
            ArrayList<AirQualityRealm> list = new ArrayList<>();

            for (AirQualityRealm a : next) {
                if (a.getLatitude() != 0.0d && a.getLongitude() != 0.0d) {
                    list.add(a);
                }
                if (list.size() != 0) {
                    AirQualityRealm root = list.get(0);

                    Location loc1 = new Location("");
                    loc1.setLatitude(root.getLatitude());
                    loc1.setLongitude(root.getLongitude());
                    Location loc2 = new Location("");
                    loc2.setLatitude(airQualityRealm.getLatitude());
                    loc2.setLongitude(airQualityRealm.getLongitude());
                    distance = loc1.distanceTo(loc2);
                } else {
                    distance = 0;
                }
            }
            airQualityRealm.setDistanceByStart(distance);
        });
        if (airQualityRealm.getLatitude() != 0.0d && airQualityRealm.getLongitude() != 0.0d) {
            this.getAddress(airQualityRealm.getLatitude(), airQualityRealm.getLongitude());
        }
        if (airQualityRealm.getP10() != 0.0d || airQualityRealm.getP2_5() != 0.0d) {
            realmService.addObject(airQualityRealm, AirQualityRealm.class).doOnError(Throwable::printStackTrace).subscribe(next ->
                    view.updateGraphic(next), Throwable::getStackTrace
            );
        }

        return airQualityRealm;
    }

    private void getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mainFragment.getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            airQualityRealm.setAddress(address);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (readAsync != null) {
            readAsync.onCancelled();
        }
    }

    private class ReadAsync extends AsyncTask<UsbManager, Object, PMData> {
        CallbackResult result;
        private volatile boolean running = true;

        public ReadAsync(CallbackResult result) {
            this.result = result;
        }

        public ReadAsync() {
            super();
        }

        @Override
        protected PMData doInBackground(UsbManager... usbManagers) {
            if (running) {
                UsbSerialPort port = null;

                try {

                    for (int y = 0; y < 1000000; y++) {

                        UsbManager mUsbManager = usbManagers[0];
                        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);
                        if (availableDrivers.isEmpty()) {
                            return null;
                        }
                        UsbSerialDriver driver = availableDrivers.get(0);
                        UsbDeviceConnection connection = mUsbManager.openDevice(driver.getDevice());
                        if (connection == null) {
                            return null;
                        }
                        port = driver.getPorts().get(0);
                        port.open(connection);
                        port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
                        byte buffer[] = new byte[16];

                        int numBytesRead = port.read(buffer, 5000);
                        port.close();
                        if (numBytesRead == 10) {
                            int check = ((Byte) buffer[8]).intValue();
                            int pm5h = ((Byte) buffer[3]).intValue();
                            int pm5l = ((Byte) buffer[2]).intValue();
                            int pm10l = ((Byte) buffer[4]).intValue();
                            int pm10h = ((Byte) buffer[5]).intValue();
                            int res1 = ((Byte) buffer[6]).intValue();
                            int res2 = ((Byte) buffer[7]).intValue();

                            if (check == res1 + res2 + pm5h + pm5l + pm10h + pm10l) {
                                int pm5 = ((pm5h * 256) + pm5l / 10);
                                int pm10 = ((pm10h * 256) + pm10l / 10);
                                pm5 = pm5 < 0 ? 0 : pm5;
                                pm10 = pm10 < 0 ? 0 : pm10;

                                PMData pmData = new PMData();
                                pmData.setPm5(pm5);
                                pmData.setPm10(pm10);

                                pmData.setCheck(check);
                                pmData.setPm5h(pm5h);
                                pmData.setPm5l(pm5l);
                                pmData.setPm10l(pm10l);
                                pmData.setPm10h(pm10h);
                                pmData.setRes1(res1);
                                pmData.setRes2(res2);

                                return pmData;
                            }

                        }
                    }
                } catch (IOException e) {

                } finally {
                    try {
                        if (port != null) port.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(PMData pmData) {
            super.onPostExecute(pmData);
            result.pmResult(pmData);
        }

        @Override
        protected void onCancelled(PMData pmData) {
            super.onCancelled(pmData);
        }

        @Override
        protected void onCancelled() {
            running = false;
        }
    }
}

//Log.d("GETLAST", " ADDRESS address - " + address + " city " + city + " state "
//        + state + " country " + country + " postalCode " + postalCode + " knowName " + knownName);
//        Toast.makeText(mainFragment.getActivity(), " ADDRESS\naddress - " + address + "\ncity - " + city + "\nstate - "
//        + state + "\ncountry - " + country + "\npostalCode - " + postalCode + "\nknowName - " + knownName, Toast.LENGTH_LONG).show();