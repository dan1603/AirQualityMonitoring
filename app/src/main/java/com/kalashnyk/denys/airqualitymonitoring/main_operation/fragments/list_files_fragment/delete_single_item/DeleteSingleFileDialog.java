package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.delete_single_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.item.IDialogFileListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DeleteSingleFileDialog extends DialogFragment {

    private String fileName;
    private int position;
    private IDialogFileListener listener;

    @BindView(R.id.tv_dialog_body_single_file)
    TextView mTvBodyMsg;

    public static DeleteSingleFileDialog newInstance(IDialogFileListener listener, String fileName, int position) {
        DeleteSingleFileDialog dialog = new DeleteSingleFileDialog(listener);
        Bundle args = new Bundle();
        args.putString("fileName", fileName);
        args.putInt("position", position);
        dialog.setArguments(args);
        return dialog;
    }

    public DeleteSingleFileDialog(IDialogFileListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fileName = getArguments().getString("fileName");
            position = getArguments().getInt("position");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_delete_single_file, null);
        ButterKnife.bind(this, v);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.initView();
        return v;
    }

    @OnClick(R.id.btn_dialog_cancel_single_file)
    public void onDialogCancel() {
        this.dismiss();
    }

    @OnClick(R.id.btn_dialog_delete_single_file)
    public void onDialogDelete() {
        this.listener.deleteItem(fileName, position);
        this.dismiss();
    }

    private void initView() {
        mTvBodyMsg.setText("Do you really want to delete "+ fileName +"?"+ "\nThis operation can not be undone.");
    }
}
