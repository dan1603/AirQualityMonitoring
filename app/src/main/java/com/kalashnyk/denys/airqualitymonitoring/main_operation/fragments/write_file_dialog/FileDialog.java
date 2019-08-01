package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IHasComponent;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.write_file_dialog.write_file_di.FileModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileDialog extends DialogFragment implements IBaseView.IFileView {

    @Inject
    IPresenterContract.IFilePresenter presenter;

    @BindView(R.id.btn_write_csv)
    ImageView mCSV;
    @BindView(R.id.btn_write_xml)
    ImageView mXML;
    @BindView(R.id.btn_write_kml)
    ImageView mKML;
    private IFileDialogListener listener;

    public FileDialog(IFileDialogListener listener) {
        this.listener = listener;
    }

    public static FileDialog newInstance(IFileDialogListener listener) {
        FileDialog dialog = new FileDialog(listener);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainComponent.class).plus(new FileModule()).inject(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog, null);
        ButterKnife.bind(this, v);
        listener.selectAria(true);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        presenter.init(this);
        this.initView();
        return v;
    }

    private void initView() {
        mCSV.setColorFilter(0xe0ffffff, PorterDuff.Mode.SRC_ATOP);
        mXML.setColorFilter(0xe0ffffff, PorterDuff.Mode.SRC_ATOP);
        mKML.setColorFilter(0xe0ffffff, PorterDuff.Mode.SRC_ATOP);
    }

    @OnClick(R.id.btn_write_file_close)
    public void onDialogCancel() {
        this.dismiss();
        presenter.dismiss();
        listener.selectAria(false);
    }

    @OnClick(R.id.btn_write_xml)
    public void onWritXMLFile() {
        presenter.doWriteFile(1);
    }

    @OnClick(R.id.btn_write_csv)
    public void oonWritCSVFile() {
        presenter.doWriteFile(2);
    }

    @OnClick(R.id.btn_write_kml)
    public void onWritKMLFile() {
        presenter.doWriteFile(3);
    }

    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>) getActivity()).getComponent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listener.selectAria(false);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}

