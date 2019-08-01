package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.common.BaseFragment;
import com.kalashnyk.denys.airqualitymonitoring.common.IBaseView;
import com.kalashnyk.denys.airqualitymonitoring.common.IPresenterContract;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.MainActivity;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.IBackPressed;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.delete_all_items.DeleteAllFilesDialog;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.delete_single_item.DeleteSingleFileDialog;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.item.FilesAdapter;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.item.IDialogFileListener;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.list_di.ListFilesModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.shared_file_dialog.SharedOrOpenFileDialog;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainComponent;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListFilesFragment extends BaseFragment implements IBaseView.IListFilesView, IBackPressed.IBackPressedListFiles {

    @Inject
    IPresenterContract.IListFilesPresenter presenter;
    @BindView(R.id.iv_list_delete_all_files)
    ImageView mImageDeleteAll;

    @BindView(R.id.rv_list_files)
    RecyclerView mRvListFiles;

    @BindView(R.id.rl_list_delete_all_files)
    RelativeLayout mListDeleteAll;
    @BindView(R.id.progress_list_files)
    ProgressBar mProgress;
    @BindView(R.id.progress_layout_list_files)
    RelativeLayout mLayoutProgress;
    private FilesAdapter listAdapter;
    private FragmentManager fragmentManager;

    public ListFilesFragment() {
    }

    public static ListFilesFragment newInstance() {
        ListFilesFragment fragment = new ListFilesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainComponent.class).plus(new ListFilesModule(this)).inject(this);
        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_files, container, false);
        ButterKnife.bind(this, v);
        this.initView();
        return v;
    }

    private void initView() {
        fragmentManager = getActivity().getSupportFragmentManager();
        mImageDeleteAll.setColorFilter(0xe04ae3ea, PorterDuff.Mode.SRC_ATOP);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("List Files");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRvListFiles.setHasFixedSize(true);
        LinearLayoutManager managerPayment = new LinearLayoutManager(getContext());
        managerPayment.setOrientation(LinearLayoutManager.VERTICAL);
        mRvListFiles.setLayoutManager(managerPayment);
        listAdapter = new FilesAdapter(getContext(), onItemListener);
        mRvListFiles.setAdapter(listAdapter);
        presenter.getListFiles();
    }

    private IDialogFileListener<File> onItemListener = new IDialogFileListener<File>() {

        @Override
        public void deleteItem(String fileName, int position) {
            presenter.doDeleteItem(fileName, position);
        }

        @Override
        public void openDeleteItemDialog(File file, int position) {
            DeleteSingleFileDialog.newInstance(this, file.getName(), position).show(fragmentManager, "Delete single item");
        }

        @Override
        public void openSharingFileDialog(File file, int position) {
            SharedOrOpenFileDialog.newInstance(this, file.getName(), position).show(fragmentManager, "Shared or open file");
        }

        @Override
        public void deleteAllFiles() {
            presenter.doDeleteAllItems();
        }

        @Override
        public void openFile(String name) {
            presenter.openFile(name);
        }

        @Override
        public void sharingFile(String name) {
            presenter.sharingFile(name);
        }

        @Override
        public void selectedOrUnselectedItem(boolean b, int position) {
            listAdapter.selectedOrUnselected(b, position);
        }

    };

    @Override
    public void updateAfterRemoveItem(int position) {
        listAdapter.removeItem(position);
    }

    @Override
    public void updateAfterRemoveAllItems() {
        listAdapter.removeAll();
    }

    @OnClick(R.id.rl_list_delete_all_files)
    public void clickDeleteAll() {
        DeleteAllFilesDialog.newInstance(onItemListener).show(fragmentManager, "Delete all items");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showListFiles(ArrayList<File> files) {
        listAdapter.addNewItems(files);
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
        mLayoutProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
        mLayoutProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
