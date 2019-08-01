package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.item;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalashnyk.denys.airqualitymonitoring.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FilesHolder extends RecyclerView.ViewHolder {

    private Context mContext;

    @BindView(R.id.tv_item_title_file)
    TextView mTitleItem;

    @BindView(R.id.iv_item_delete_file)
    ImageView mDeleteFile;

    private IDialogFileListener listener;
    private File model;
    public FilesHolder(Context context, IDialogFileListener listener, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = context;
        this.mDeleteFile.setColorFilter(0xe0afabad, PorterDuff.Mode.SRC_ATOP);
        this.listener = listener;
    }

    public void bindData(File file){
        if(file != null) {
            model = file;
            mTitleItem.setText(file.getName());
//            mTitleItem.setOnClickListener(view -> listener.openFile(file.getName()));
        }
    }

    public void selectItem(boolean b){
        if(b) {
            mTitleItem.setTextColor(mContext.getResources().getColor(R.color.dialog_selector_blue));
            this.mDeleteFile.setColorFilter(0xe04ae3ea, PorterDuff.Mode.SRC_ATOP);
        }else {
            mTitleItem.setTextColor(mContext.getResources().getColor(R.color.dialog_selector_white));
            this.mDeleteFile.setColorFilter(0xe0afabad, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
