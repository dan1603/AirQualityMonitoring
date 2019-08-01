package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.item;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.IDialogItemListener;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QualityHolder extends RecyclerView.ViewHolder {

    private Context mContext;


    @BindView(R.id.tv_item_list_title_file)
    TextView mTitleItem;

    @BindView(R.id.tv_item_list_address)
    TextView mAddressItem;

    @BindView(R.id.tv_item_list_pm10)
    TextView mPM10;

    @BindView(R.id.tv_item_list_pm2_5)
    TextView mPM2_5;

    @BindView(R.id.iv_item_list_delete)
    ImageView mDeleteFile;

    private IDialogItemListener listener;
    private AirQualityRealm model;

    public QualityHolder(Context context, IDialogItemListener listener, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = context;
        this.mDeleteFile.setColorFilter(0xe0afabad, PorterDuff.Mode.SRC_ATOP);
        this.listener = listener;
    }

    public void bindData(AirQualityRealm airQualityRealm) {
        if (airQualityRealm != null) {
            model = airQualityRealm;
            mTitleItem.setText(airQualityRealm.getDate() + " " + airQualityRealm.getTime());
            mPM10.setText(String.valueOf(airQualityRealm.getP10()));
            mPM2_5.setText(String.valueOf(airQualityRealm.getP2_5()));
            mAddressItem.setText(airQualityRealm.getAddress());
        }
    }

    @OnClick(R.id.iv_item_list_delete)
    public void openDeleteItemDialog() {
        this.listener.openDeleteItemDialog(model);
    }
}
