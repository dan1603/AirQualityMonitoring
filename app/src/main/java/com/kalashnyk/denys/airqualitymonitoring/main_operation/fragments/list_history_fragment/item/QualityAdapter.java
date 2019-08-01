package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.IDialogItemListener;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;
import com.kalashnyk.denys.airqualitymonitoring.utils.realm_adapter.RealmRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class QualityAdapter extends RealmRecyclerViewAdapter<AirQualityRealm> {

    private final List<AirQualityRealm> mValues;
    private Context mContext;
    private IDialogItemListener listener;

    public QualityAdapter(Context context, IDialogItemListener listener, List<AirQualityRealm> items) {
        this.mValues = items;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public QualityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new QualityHolder(mContext, listener, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final QualityHolder holder = (QualityHolder) viewHolder;
        holder.bindData(getItem(position));
    }

    @Override
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public void addNewItems(ArrayList<AirQualityRealm> items) {
        if (items.size() == 0) {
            return;
        }
        mValues.addAll(items);
        this.notifyDataSetChanged();
    }
}
