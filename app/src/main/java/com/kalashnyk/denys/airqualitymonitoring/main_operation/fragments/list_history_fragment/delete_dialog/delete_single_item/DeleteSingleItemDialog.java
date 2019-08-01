package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.delete_single_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.IDialogItemListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 12.11.2017.
 */

public class DeleteSingleItemDialog extends DialogFragment {

    private long itemId;
    private String txtBody;
    private IDialogItemListener listener;
    @BindView(R.id.tv_dialog_body_single)
    TextView mTvBodyMsg;
    public static DeleteSingleItemDialog newInstance(IDialogItemListener listener, long itemId, String txtBody) {
        DeleteSingleItemDialog dialog = new DeleteSingleItemDialog(listener);
        Bundle args = new Bundle();
        args.putLong("itemId", itemId);
        args.putString("txtBody", txtBody);
        dialog.setArguments(args);
        return dialog;
    }

    public DeleteSingleItemDialog(IDialogItemListener listener) {
        this.listener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getLong("itemId");
            txtBody = getArguments().getString("txtBody");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_delete_single_item, null);
        ButterKnife.bind(this, v);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.initView();
        return v;
    }

    @OnClick(R.id.btn_dialog_cancel_single)
    public void onDialogCancel() {
        this.dismiss();
    }

    @OnClick(R.id.btn_dialog_delete_single)
    public void onDialogDelete() {
        this.listener.deleteItem(itemId);
        this.dismiss();
    }

    private void initView() {
        mTvBodyMsg.setText("Do you really want to delete "+ txtBody +"?"+ "\nThis operation can not be undone.");
    }
}
