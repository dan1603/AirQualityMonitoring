package com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.help_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.IBackPressed;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpFragment extends Fragment implements IBackPressed.IBackPressedHelp {

    @BindView(R.id.wv_help)
    WebView mWebViewHelp;

    public HelpFragment() {
    }

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, v);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Help Screen");
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        mWebViewHelp.setWebViewClient(new WebViewClient());
        mWebViewHelp.loadUrl(getString(R.string.help_url));
        return v;
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
}
