package com.alkathirikhalid.storelisting.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alkathirikhalid.storelisting.data.Status;
import com.alkathirikhalid.storelisting.data.rest.AdvisoryAppsClient;
import com.alkathirikhalid.storelisting.storage.AppPrefs;
import com.alkathirikhalid.storelisting.ui.MainActivity;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */
public abstract class BaseView extends AppCompatActivity {
    public AdvisoryAppsClient advisoryAppsClient;
    public CompositeDisposable compositeDisposable;
    public AppPrefs appPrefs;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        advisoryAppsClient = new AdvisoryAppsClient();
        appPrefs = new AppPrefs(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected boolean isError(Status status) {
        return (status.getCode() != 200);
    }

    protected void showDialog(String title, String message) {
        dialog = ProgressDialog.show(this, title, message, false);
    }

    protected void dismissDialog() {
        dialog.dismiss();
    }

    public void logOut() {
        appPrefs.cleanUp();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}