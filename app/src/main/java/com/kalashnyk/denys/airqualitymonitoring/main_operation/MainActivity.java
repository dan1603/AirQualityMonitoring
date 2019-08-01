package com.kalashnyk.denys.airqualitymonitoring.main_operation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.app.app_di.AppComponent;
import com.kalashnyk.denys.airqualitymonitoring.common.BaseActivity;
import com.kalashnyk.denys.airqualitymonitoring.common.IHasComponent;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.IBackPressed;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.help_fragment.HelpFragment;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.ListFilesFragment;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_history_fragment.ListFragment;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.main_fragment.MainFragment;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.sharing_dialog.SharingDialog;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainComponent;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.main_di.MainModule;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;
import com.kalashnyk.denys.airqualitymonitoring.utils.social_controller.SocialControllerImpl;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity implements IHasComponent<MainComponent> {

    @BindView(R.id.twitter_login)
    TwitterLoginButton twitterLoginButton;
    private MainComponent mainComponent;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private UsbManager mUsbManager;
    public AirQualityRealm airQualityRealm;
    @Inject
    SocialControllerImpl socialController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mainComponent != null) {
//            Toast.makeText(this, "onResume() mainComponent != null", Toast.LENGTH_LONG).show();
        }else {
//            Toast.makeText(this, "onResume() mainComponent == null", Toast.LENGTH_LONG).show();
        }
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        mainComponent = appComponent.plus(new MainModule(this));
        mainComponent.inject(this);
    }

    public TwitterLoginButton getTwitterLoginButton() {
        return twitterLoginButton;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, MainFragment.newInstance()).addToBackStack("Detail").commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (SocialControllerImpl.FACEBOOK_FILE == requestCode) {
            socialController.onActivityResult(requestCode, resultCode, data, airQualityRealm);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void setIconInMenu(Menu menu, int menuItemId, int labelId, int iconId) {
        MenuItem item = menu.findItem(menuItemId);
        SpannableStringBuilder builder = new SpannableStringBuilder("     " + getResources().getString(labelId));
        builder.setSpan(new ImageSpan(this, iconId), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item.setTitle(builder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.setIconInMenu(menu, R.id.action_list_history, R.string.menu_list, R.mipmap.ic_menu_white_24dp);
        this.setIconInMenu(menu, R.id.action_list_files, R.string.menu_list_files, R.mipmap.ic_folder_white_24dp);
        this.setIconInMenu(menu, R.id.action_help, R.string.menu_help, R.mipmap.ic_help_white_24dp);
        this.setIconInMenu(menu, R.id.action_sharing, R.string.menu_sharing, R.mipmap.ic_share_white_24dp);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_list_history) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, ListFragment.newInstance()).addToBackStack("Detail").commit();
            return true;
        } else if (id == R.id.action_list_files) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, ListFilesFragment.newInstance()).addToBackStack("Detail").commit();
            return true;
        } else if (id == R.id.action_help) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, HelpFragment.newInstance()).addToBackStack("Detail").commit();
            return true;
        } else if (id == R.id.action_sharing) {
            SharingDialog.newInstance().show(fragmentManager, "Sharing dialog");
            return true;
        } else if (id == android.R.id.home) {
            MainActivity.this.onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        IBackPressed.IBackPressedHelp iBackPressedHelp = null;
        IBackPressed.IBackPressedList backPressedList = null;
        IBackPressed.IBackPressedListFiles backPressedListFiles = null;
        for (Fragment fragment : fm.getFragments()) {
            if (fragment instanceof IBackPressed.IBackPressedHelp) {
                iBackPressedHelp = (IBackPressed.IBackPressedHelp) fragment;
                break;
            }
        }
        for (Fragment fragment : fm.getFragments()) {
            if (fragment instanceof IBackPressed.IBackPressedList) {
                backPressedList = (IBackPressed.IBackPressedList) fragment;
                break;
            }
        }
        for (Fragment fragment : fm.getFragments()) {
            if (fragment instanceof IBackPressed.IBackPressedListFiles) {
                backPressedListFiles = (IBackPressed.IBackPressedListFiles) fragment;
                break;
            }
        }
        if (iBackPressedHelp != null) {
            iBackPressedHelp.onBackPressed();
        } else if (backPressedList != null) {
            backPressedList.onBackPressed();
        } else if (backPressedListFiles != null) {
            backPressedListFiles.onBackPressed();
        } else {
            finish();
        }
    }

    public AirQualityRealm getAirQualityRealm() {
        return airQualityRealm;
    }

    public void setAirQualityRealm(AirQualityRealm airQualityRealm) {
        this.airQualityRealm = airQualityRealm;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.airQualityRealm = null;
    }
}