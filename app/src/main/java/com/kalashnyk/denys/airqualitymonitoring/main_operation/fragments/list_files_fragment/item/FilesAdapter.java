package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalashnyk.denys.airqualitymonitoring.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesHolder> {

    private final List<File> mValues = new ArrayList<>();
    private Context mContext;
    private IDialogFileListener listener;
    private FilesHolder holder;
    public FilesAdapter(Context context, IDialogFileListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public FilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_files_list, parent, false);
        holder = new FilesHolder(mContext, listener, view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FilesHolder holder, final int position) {
        holder.bindData(mValues.get(position));
        holder.mDeleteFile.setOnClickListener(view ->{
            this.listener.openDeleteItemDialog(mValues.get(position), position);
        });

        holder.mTitleItem.setOnClickListener(view ->{
            this.listener.openSharingFileDialog(mValues.get(position), position);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addNewItems(ArrayList<File> items) {
        if (items.size() == 0) {
            return;
        }
        mValues.addAll(items);
        this.notifyDataSetChanged();
    }

    public void removeItem(int position){
        mValues.remove(position);
        this.notifyDataSetChanged();
    }

    public void removeAll(){
        mValues.clear();
        this.notifyDataSetChanged();
    }

    public void selectedOrUnselected(boolean b, int position){
        if(holder.getAdapterPosition() == position){
            holder.selectItem(b);
        }
    }
}
