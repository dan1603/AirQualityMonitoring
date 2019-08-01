package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.IDialogItemListener;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.delete_all_items.DeleteAllItemsDialog;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.delete_dialog.delete_single_item.DeleteSingleItemDialog;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.item.QualityAdapter;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.list_di.ListModule;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainComponent;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;
import com.kalashnyk.denys.airqualitymonitoring.utils.realm_adapter.RealmAirQualitiesAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class ListFragment extends BaseFragment implements IBackPressed.IBackPressedList, IBaseView.IListView, IDialogItemListener<AirQualityRealm> {

    @Inject
    IPresenterContract.IListPresenter presenter;
    @BindView(R.id.iv_list_delete_all)
    ImageView mImageDeleteAll;

    @BindView(R.id.rv_list_files)
    RecyclerView mRvListFiles;

    @BindView(R.id.rl_list_delete_all)
    RelativeLayout mListDeleteAll;
    @BindView(R.id.progress_list)
    ProgressBar mProgress;
    @BindView(R.id.progress_layout_list)
    RelativeLayout mLayoutProgress;
    private ArrayList<AirQualityRealm> list;
    private QualityAdapter listAdapter;
    private FragmentManager fragmentManager;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MainComponent.class).plus(new ListModule()).inject(this);
        presenter.init(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);
        this.initView();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("List History");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        presenter.getListQualities();
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        fragmentManager = getActivity().getSupportFragmentManager();
        mImageDeleteAll.setColorFilter(0xe04ae3ea, PorterDuff.Mode.SRC_ATOP);

        list = new ArrayList<>();
        mRvListFiles.setHasFixedSize(true);
        LinearLayoutManager managerPayment = new LinearLayoutManager(getContext());
        managerPayment.setOrientation(LinearLayoutManager.VERTICAL);
        mRvListFiles.setLayoutManager(managerPayment);
        listAdapter = new QualityAdapter(getContext(), this, list);
        mRvListFiles.setAdapter(listAdapter);
    }

    @OnClick(R.id.rl_list_delete_all)
    public void clickDeleteAll() {
        DeleteAllItemsDialog.newInstance(this).show(fragmentManager, "Delete all items");
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
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
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
    public void showRealmResult(RealmResults<AirQualityRealm> realms) {
        if (list != null) {
            RealmAirQualitiesAdapter realmAdapter = new RealmAirQualitiesAdapter(this.getContext(), realms, true);
            listAdapter.setRealmAdapter(realmAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void deleteItem(long id) {
        presenter.doDeleteItem(id);
        presenter.getListQualities();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void openDeleteItemDialog(AirQualityRealm airQualityRealm) {
        String qualityTitle = airQualityRealm.getDate() +
                " " + airQualityRealm.getTime() +
                " PM10 " + airQualityRealm.getP10() +
                " PM2.5 " + airQualityRealm.getP2_5();
        DeleteSingleItemDialog.newInstance(this, airQualityRealm.getId(), qualityTitle).show(fragmentManager, "Delete single item");
    }

    @Override
    public void deleteAllItems() {
        presenter.doDeleteAllItems();
        presenter.getListQualities();
    }
}
