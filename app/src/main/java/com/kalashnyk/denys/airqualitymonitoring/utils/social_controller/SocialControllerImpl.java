package com.kalashnyk.denys.airqualitymonitoring.utils.social_controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.kalashnyk.denys.airqualitymonitoring.R;
import com.kalashnyk.denys.airqualitymonitoring.main_operation.MainActivity;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQualityRealm;
import com.kalashnyk.denys.airqualitymonitoring.utils.file_controller.IFileController;
import com.kalashnyk.denys.airqualitymonitoring.utils.network.INetworkCheck;

import java.util.Arrays;

import javax.inject.Inject;


public class SocialControllerImpl implements ISocialController {

    private MainActivity activity;
    private INetworkCheck networkCheck;
    private IFileController fileController;
    private AirQualityRealm airQualityRealm;
    public static final int FACEBOOK_FILE = 1;
    public static final int INSTAGRAM_FILE = 2;

    @Inject
    public SocialControllerImpl(MainActivity activity, INetworkCheck networkCheck, IFileController fileController) {
        this.activity = activity;
        this.networkCheck = networkCheck;
        this.fileController = fileController;
    }

    @Override
    public void doSharing(int i, AirQualityRealm airQualityRealm) {
        if (!networkCheck.isOnline()) {
            Toast.makeText(activity, activity.getString(R.string.error_text_no_internet), Toast.LENGTH_LONG).show();
            return;
        }
        this.airQualityRealm = airQualityRealm;
        activity.setAirQualityRealm(airQualityRealm);
        String msg;
        if(airQualityRealm != null) {
            msg = this.airQualityRealm.getAddress() +", PM10 " + this.airQualityRealm.getP10() + ", PM2.5 " + this.airQualityRealm.getP2_5();
        }else {
            msg = "";
        }
        if (i == 1) {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, "Select File"), FACEBOOK_FILE);
        } else if (i == 2) {
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + msg;
            Uri uri = Uri.parse(tweetUrl);
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } else if (i == 3) {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, "Select File"), INSTAGRAM_FILE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, AirQualityRealm airQualityRealm) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FACEBOOK_FILE) {
                Uri uri = data.getData();
                if(airQualityRealm != null) {
                    SocialControllerImpl.this.sharingFB(uri, SocialControllerImpl.this.airQualityRealm.getAddress() +", PM10 " + this.airQualityRealm.getP10() + ", PM2.5 " + this.airQualityRealm.getP2_5());
                }else {
                    SocialControllerImpl.this.sharingFB(uri, "");

                }
            } else if (requestCode == INSTAGRAM_FILE) {
                Uri uri = data.getData();
                SocialControllerImpl.this.createInstagramIntent(uri);
            }
        }
    }

    public void sharingFB(Uri uri, String msg) {
        if (!networkCheck.isOnline()) {
            Toast.makeText(activity, activity.getString(R.string.error_text_no_internet), Toast.LENGTH_LONG).show();
            return;
        }

        LoginManager.getInstance().logInWithPublishPermissions(activity,
                Arrays.asList("publish_actions"));


        activity.getLoginManager().registerCallback(activity.getCallbackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                SharePhoto photo = new SharePhoto.Builder()
                        .setImageUrl(uri)
                        .setCaption(msg)
                        .build();

                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                ShareApi.share(content, null);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }


    private void createInstagramIntent(Uri uri) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        activity.startActivity(Intent.createChooser(share, "Share to"));
    }


}
