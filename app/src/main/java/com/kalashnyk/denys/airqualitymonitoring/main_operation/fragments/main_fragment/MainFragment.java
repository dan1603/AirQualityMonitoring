package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.common.BaseFragment;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.MainActivity;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.IDialogGpsListener;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.gps_dialog.GPSDialog;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.main_fragment_di.MainFragmentModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.FileDialog;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.IFileDialogListener;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainComponent;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends BaseFragment implements IBaseView.IMainFragmentView, IDialogGpsListener, CompoundButton.OnCheckedChangeListener {

    @Inject
    IPresenterContract.IMainPresenter presenter;

    @BindView(R.id.iv_main_footer_event)
    ImageView mIvFooterEvent;
    @BindView(R.id.tv_main_footer_event)
    TextView mTvFooterEvent;
    @BindView(R.id.iv_main_header_circle_status_gps)
    AppCompatButton mIvGPSStatus;
    @BindView(R.id.tv_main_header_circle_status_gps)
    TextView mTvGPSStatus;
    @BindView(R.id.iv_main_header_circle_status_sensor)
    AppCompatButton mIvUSBStatus;
    @BindView(R.id.tv_main_header_circle_status_sensor)
    TextView mTvUSBStatus;
    @BindView(R.id.rl_main_footer_gps_on)
    RelativeLayout mStartGps;
    @BindView(R.id.rl_main_footer_gps_off)
    RelativeLayout mStopGps;
    @BindView(R.id.progress_layout_main)
    RelativeLayout mLayoutProgress;
    @BindView(R.id.progress_main)
    ProgressBar mProgress;
    @BindView(R.id.iv_current_mp2_5)
    TextView mCurrentMP2;
    @BindView(R.id.iv_current_mp10)
    TextView mCurrentMP10;
    private UsbManager mUsbManager;
    @BindView(R.id.graph)
    GraphView graph;
    @BindDrawable(R.drawable.circle_view)
    Drawable enable;
    @BindDrawable(R.drawable.status_sensor)
    Drawable disable;
    @BindView(R.id.tv_main_footer_gps_on)
    TextView mTvGPSOn;
    @BindView(R.id.iv_main_circle_gps_on)
    AppCompatButton mIvGPSOn;
    @BindView(R.id.tv_main_footer_gps_off)
    TextView mTvGPSOff;
    @BindView(R.id.iv_main_circle_gps_off)
    AppCompatButton mIvGPSOff;

    private final Handler mHandler = new Handler();
    private Runnable mTimer;
    private double timeA;
    private ToggleButton toggleSensor;
    private Date DATA = new Date();
    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private double graphValueXLineA = 0;
    private double graphValueXLineB = 0;
    private double dataStartValuieSec;
    private Date date;
    private double timeB;
    private boolean isRunning;
    private boolean isGPS;
    private int counter = 0;

    private int pointA;
    private int pointB;
    private int checkA = 1001;
    private int checkB = 1001;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainComponent.class).plus(new MainFragmentModule(this)).inject(this);
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Main Screen");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        this.initView();
        toggleSensor = (ToggleButton) v.findViewById(R.id.btn_sensor);
        toggleSensor.setOnCheckedChangeListener(this);
        return v;
    }

    public void startSensor() {
        if (isRunning) {
            try {
                mTimer = new Runnable() {
                    @Override
                    public void run() {
                        if (!isRunning) {
                            return;
                        }
                        if (counter == 0) {
                            mSeries1.appendData(new DataPoint(0, 1), false, 120);
                            mSeries2.appendData(new DataPoint(0, 1), false, 120);
                            counter++;
                        } else {
                            presenter.getUSBData(mUsbManager);
                            if (isGPS) {
                                presenter.getLocation();
                            }
                            AirQualityRealm airQualityRealm = presenter.generatedData();

                            pointA = (int)airQualityRealm.getP2_5();
                            pointB = (int)airQualityRealm.getP10();

                            if (pointA > 1000) {
                                checkA = pointA > checkA ? pointA : checkA;
                                if(checkA <= pointA) {
                                    graph.getViewport().setMaxY(pointA);
                                }
                            }

                            if (pointB > 1000) {
                                checkB = checkB > checkB ? checkB : checkB;
                                if(checkB <= pointB) {
                                    graph.getViewport().setMaxY(pointB);
                                }
                            }

                            date = new Date();
                            timeB = date.getTime();
                            graphValueXLineA = timer(timeA, timeB);
                            graphValueXLineB = timer(timeA, timeB);

                            if (pointA != 0) {
                                mSeries1.appendData(new DataPoint(graphValueXLineA, pointA), false, 120);
                            }
                            if (pointB != 0) {
                                mSeries2.appendData(new DataPoint(graphValueXLineB, pointB), false, 120);
                            }
                            if (pointA == 0 && pointB == 0) {
                                Toast.makeText(getActivity(), " Sensor is running ", Toast.LENGTH_LONG).show();
                            }

//                            int pm2 = (int)airQualityRealm.getP2_5();
//                            int pm10 = (int)airQualityRealm.getP10();
//
//                            if (pm2 > 1000) {
//                                graph.getViewport().setMaxY(pm2 + 1000);
//                            }
//                            if (pm10 > 1000) {
//                                graph.getViewport().setMaxY(pm10 + 1000);
//                            }
//
//                            date = new Date();
//                            timeB = date.getTime();
//                            graphValueXLineA = timer(timeA, timeB);
//                            graphValueXLineB = timer(timeA, timeB);
//                            if (pm2 != 0 && counter != 0) {
//                                mSeries1.appendData(new DataPoint(graphValueXLineA,pm2), false, 120);
//                            }
//                            if (pm10 != 0.0d && counter != 0) {
//                                mSeries2.appendData(new DataPoint(graphValueXLineB, pm10), false, 120);
//                            }
//                            if (pm2 == 0 && pm10 == 0 && counter != 0) {
//                                Toast.makeText(getActivity(), " Sensor is running ", Toast.LENGTH_LONG).show();
//                            }
                        }
                        mHandler.postDelayed(this, 30_000);
                    }
                };

                mHandler.postDelayed(mTimer, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private double timer(double timeA, double timeB) {
//        long convertTime = 0;
//        convertTime += (timeB - timeA);
//        String str = formatDuration(convertTime);
//        int timeMin = +Integer.parseInt(str.trim());
//        return timeMin;
        double convertTime = 0;
        convertTime = (timeB - timeA);
        if(convertTime >= 3600000.0){
            isRunning = false;
            date = new Date();
        }
        String str = formatDuration((long)convertTime);
        str = String.valueOf(str.trim());
        double timeMin = Float.parseFloat(str);
        double newTime = new BigDecimal(timeMin).setScale(2, RoundingMode.UP).doubleValue();
        return newTime;
    }

    private static String formatDuration(long duration) {
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
//        return String.format("%2d", minutes);
        return String.format("%2d.%02d",duration / 1000 / 60 % 60, duration / 1000 % 60);
    }

    @Override
    public void onResume() {
        super.onResume();
        startSensor();
    }

    @Override
    public void updateGraphic(AirQualityRealm airQualityRealm) {
        mCurrentMP2.setText(String.valueOf(Integer.valueOf((int) airQualityRealm.getP2_5())));
        mCurrentMP10.setText(String.valueOf(Integer.valueOf((int) airQualityRealm.getP10())));
    }

    private void initView() {
        mUsbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
        mIvFooterEvent.setColorFilter(0xe0afabad, PorterDuff.Mode.SRC_ATOP);
        mStopGps.setEnabled(false);

        timeA = DATA.getTime();
        mSeries1 = new LineGraphSeries<>();
        mSeries1.setColor(getActivity().getResources().getColor(R.color.pm2_5_view_background));
        mSeries1.setDrawDataPoints(true);
        mSeries1.setDataPointsRadius(20);
        mSeries1.setThickness(7);
        mSeries1.setOnDataPointTapListener((series, dataPoint) -> Toast.makeText(getActivity(), "PM5: " + dataPoint, Toast.LENGTH_SHORT).show());

        mSeries2 = new LineGraphSeries<>();
        mSeries2.setColor(getActivity().getResources().getColor(R.color.pm10_view_background));
        mSeries2.setDrawDataPoints(true);
        mSeries2.setDataPointsRadius(20);
        mSeries2.setThickness(7);
        mSeries2.setOnDataPointTapListener((series, dataPoint) -> Toast.makeText(getActivity(), "PM10: " + dataPoint, Toast.LENGTH_SHORT).show());

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(1);
        graph.getViewport().setMaxY(1000);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return value != 0 ? super.formatLabel(value, isValueX) + "\nm" : super.formatLabel(0, isValueX);
                } else {
                    Html.fromHtml("<sup>3</sup>");
                    return value != 0 ? super.formatLabel(value, isValueX) + "\n" + Html.fromHtml("<sup>pm</sup>") : super.formatLabel(0, isValueX);
                }
            }
        });
        graph.addSeries(mSeries1);
        graph.addSeries(mSeries2);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        isRunning = b ? true : false;
        if (isRunning) {
            this.highlightedUSBSwitch();
        } else {
            this.notHighlightedUSBSwitch();
        }
        onResume();
    }

    @OnClick(R.id.rl_main_footer_gps_on)
    public void startGPS() {

        if (!isGPS && !presenter.canGetLocation()) {
            isGPS = true;
            presenter.getLocation();
        } else {
            this.showGPSDialog();
        }
        mStartGps.setEnabled(false);
        mStopGps.setEnabled(true);
        mTvGPSOn.setTextColor(MainFragment.this.getResources().getColor(R.color.dialog_selector_blue));//
        mTvGPSOff.setTextColor(MainFragment.this.getResources().getColor(R.color.color_common_text));
        mIvGPSOn.setBackground(enable);
        mIvGPSOff.setBackground(disable);
    }

    @OnClick(R.id.rl_main_footer_gps_off)
    public void stopGPS() {
        isGPS = false;
        presenter.stopUsingGPS();
        if (isRunning) {
            mTimer.run();
        }
        mStopGps.setEnabled(false);
        mStartGps.setEnabled(true);
        if(isRunning) {
            mTvGPSOff.setTextColor(MainFragment.this.getResources().getColor(R.color.dialog_selector_blue));
            mIvGPSOff.setBackground(enable);
        }
        mTvGPSOn.setTextColor(MainFragment.this.getResources().getColor(R.color.color_common_text));
        mIvGPSOn.setBackground(disable);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataStartValuieSec = 0;
        DATA = new Date();
        counter = 0;
        isRunning = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataStartValuieSec = 0;
        isRunning = false;
        DATA = null;
        presenter.dismiss();

    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
        mLayoutProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
        mLayoutProgress.setVisibility(View.GONE);
    }


    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.rl_main_footer_event)
    public void writeLog() {
        FileDialog.newInstance(onFileDialogListener).show(getActivity().getSupportFragmentManager(), "Write File");
    }

    @Override
    public void highlightedGPSSwitch() {
        mIvGPSStatus.setBackground(enable);
        mTvGPSStatus.setTextColor(getActivity().getResources().getColor(R.color.dialog_selector_white));
    }

    @Override
    public void notHighlightedGPSSwitch() {
        mIvGPSStatus.setBackground(disable);
        mTvGPSStatus.setTextColor(getActivity().getResources().getColor(R.color.color_common_text));
    }

    @Override
    public void highlightedUSBSwitch() {
        mIvUSBStatus.setBackground(enable);
        mTvUSBStatus.setTextColor(getActivity().getResources().getColor(R.color.dialog_selector_white));
    }

    @Override
    public void notHighlightedUSBSwitch() {
        mIvUSBStatus.setBackground(disable);
        ;
        mTvUSBStatus.setTextColor(getActivity().getResources().getColor(R.color.color_common_text));
    }

    @Override
    public void turnOnGPS() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        getActivity().startActivity(intent);
    }

    private void showGPSDialog() {
        GPSDialog.newInstance(this).show(getActivity().getSupportFragmentManager(), "GPS settings");
    }

    private IFileDialogListener onFileDialogListener = new IFileDialogListener() {
        @Override
        public void selectAria(boolean b) {
            if (b) {
                mIvFooterEvent.setColorFilter(0xe04ae3ea, PorterDuff.Mode.SRC_ATOP);
                mTvFooterEvent.setTextColor(getActivity().getResources().getColor(R.color.dialog_selector_blue));
            } else {
                mIvFooterEvent.setColorFilter(0xe0afabad, PorterDuff.Mode.SRC_ATOP);
                mTvFooterEvent.setTextColor(getActivity().getResources().getColor(R.color.color_common_text));
            }
        }
    };
}