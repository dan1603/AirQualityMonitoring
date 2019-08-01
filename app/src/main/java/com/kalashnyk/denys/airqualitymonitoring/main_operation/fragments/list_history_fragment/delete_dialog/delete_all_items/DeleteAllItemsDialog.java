package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.delete_all_items;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.IDialogItemListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteAllItemsDialog extends DialogFragment {

    private IDialogItemListener listener;

    public DeleteAllItemsDialog(IDialogItemListener listener) {
        this.listener = listener;
    }

    public static DeleteAllItemsDialog newInstance(IDialogItemListener listener) {
        DeleteAllItemsDialog dialog = new DeleteAllItemsDialog(listener);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_delete_all_items, null);
        ButterKnife.bind(this, v);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return v;
    }

    @OnClick(R.id.btn_dialog_cancel)
    public void onDialogCancel() {
        this.dismiss();
    }

    @OnClick(R.id.btn_dialog_delete)
    public void onDialogDelete() {
        listener.deleteAllItems();
        this.dismiss();
    }

}
