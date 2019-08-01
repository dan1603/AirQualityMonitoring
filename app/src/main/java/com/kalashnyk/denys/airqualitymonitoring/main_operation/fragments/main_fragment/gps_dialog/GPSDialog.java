package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.gps_dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.IDialogGpsListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 12.11.2017.
 */

public class GPSDialog extends DialogFragment {

    private IDialogGpsListener listener;

    public GPSDialog(IDialogGpsListener listener) {
        this.listener = listener;
    }

    public static GPSDialog newInstance(IDialogGpsListener listener) {
        GPSDialog dialog = new GPSDialog(listener);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_gps, null);
        ButterKnife.bind(this, v);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return v;
    }

    @OnClick(R.id.btn_dialog_cancel)
    public void onDialogCancel() {
        this.dismiss();
    }

    @OnClick(R.id.btn_dialog_turn_on)
    public void onDialogDelete() {
        listener.turnOnGPS();
        this.dismiss();
    }

}

