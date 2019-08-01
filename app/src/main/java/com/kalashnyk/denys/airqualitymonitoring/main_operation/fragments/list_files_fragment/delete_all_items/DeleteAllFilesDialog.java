package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.delete_all_items;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.item.IDialogFileListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteAllFilesDialog extends DialogFragment {

    private IDialogFileListener listener;

    public DeleteAllFilesDialog(IDialogFileListener listener) {
        this.listener = listener;
    }

    public static DeleteAllFilesDialog newInstance(IDialogFileListener listener) {
        DeleteAllFilesDialog dialog = new DeleteAllFilesDialog(listener);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_delete_all_files, null);
        ButterKnife.bind(this, v);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return v;
    }

    @OnClick(R.id.btn_dialog_cancel_all_files)
    public void onDialogCancel() {
        this.dismiss();
    }

    @OnClick(R.id.btn_dialog_delete_all_files)
    public void onDialogDelete() {
        listener.deleteAllFiles();
        this.dismiss();
    }

}
