package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IHasComponent;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog.sharing_di.SharingModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 22.10.2017.
 */
public class SharingDialog extends DialogFragment implements IBaseView.ISharingView{

    @Inject
    IPresenterContract.ISharingPresenter presenter;
    public static SharingDialog newInstance() {
        SharingDialog dialog = new SharingDialog();
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainComponent.class).plus(new SharingModule()).inject(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_sharing, null);
        ButterKnife.bind(this, v);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.initView();
        presenter.init(this);
        return v;
    }

    @OnClick(R.id.btn_sharing_cancel)
    public void onDialogCancel(){
        this.dismiss();
        presenter.dismiss();
    }

    @OnClick(R.id.btn_sharing_facebook)
    public void onSharingFacebook(){
        presenter.doSharing(1);
        dismiss();
    }

    @OnClick(R.id.btn_sharing_twitter)
    public void onSharingTwitter(){
        presenter.doSharing(2);
        dismiss();
    }

    @OnClick(R.id.btn_sharing_instagram)
    public void onSharingInstagram(){
        presenter.doSharing(3);
        dismiss();
    }

    private void initView() {}

    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>)getActivity()).getComponent());
    }
}

