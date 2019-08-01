package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.shared_file_dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.item.IDialogFileListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 04.12.2017.
 */

public class SharedOrOpenFileDialog  extends DialogFragment {

    private String fileName;
    private int position;
    private IDialogFileListener listener;

    public static SharedOrOpenFileDialog newInstance(IDialogFileListener listener, String fileName, int position) {
        SharedOrOpenFileDialog dialog = new SharedOrOpenFileDialog(listener);
        Bundle args = new Bundle();
        args.putString("fileName", fileName);
        args.putInt("position", position);
        dialog.setArguments(args);
        return dialog;
    }

    public SharedOrOpenFileDialog(IDialogFileListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fileName = getArguments().getString("fileName");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_share_or_open_file, null);
        ButterKnife.bind(this, v);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        listener.selectedOrUnselectedItem(true, position);
        return v;
    }

    @OnClick(R.id.btn_dialog_share_or_open_cancel)
    public void onDialogCancel() {
        listener.selectedOrUnselectedItem(false, position);
        this.dismiss();
    }

    @OnClick(R.id.btn_dialog_share_or_open_open)
    public void onDialogOpen() {
        this.listener.openFile(fileName);
        listener.selectedOrUnselectedItem(false, position);
        this.dismiss();
    }
    @OnClick(R.id.btn_dialog_share_or_open_share)
    public void onDialogShared() {
        this.listener.sharingFile(fileName);
        listener.selectedOrUnselectedItem(false, position);
        this.dismiss();
    }

}

