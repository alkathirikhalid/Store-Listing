package com.alkathirikhalid.storelisting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alkathirikhalid.storelisting.R;
import com.alkathirikhalid.storelisting.base.BaseView;
import com.alkathirikhalid.storelisting.data.remote.request.body.LoginRequestBody;
import com.alkathirikhalid.storelisting.data.remote.response.body.LoginResponseBody;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseView {
    @BindView(R.id.email_edit_text)
    TextInputEditText email_edit_text;

    @BindView(R.id.password_edit_text)
    TextInputEditText password_edit_text;

    @BindView(R.id.email_text_input)
    TextInputLayout email_text_input;

    @BindView(R.id.password_text_input)
    TextInputLayout password_text_input;

    @BindView(R.id.login_button)
    MaterialButton login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!appPrefs.getId().equals("") || !appPrefs.getToken().equals("") || appPrefs.getToken().length() > 3) {
            goToListing();
        }
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    void login() {
        String email = email_edit_text.getText().toString();
        String password = password_edit_text.getText().toString();
        if (email.isEmpty()) {
            email_text_input.setError(getString(R.string.email_required));
            return;
        } else {
            email_text_input.setError(null);
        }

        if (password.isEmpty()) {
            password_text_input.setError(getString(R.string.password_required));
            return;
        } else {
            password_text_input.setError(null);
        }

        LoginRequestBody loginRequestBody = new LoginRequestBody();
        loginRequestBody.setEmail(email);
        loginRequestBody.setPassword(password);
        remoteLogin(loginRequestBody);
    }

    void goToListing() {
        Intent intent = new Intent(this, ListingActivity.class);
        startActivity(intent);
        finish();
    }

    private void remoteLogin(LoginRequestBody loginRequestBody) {
        showDialog(getString(R.string.login), getString(R.string.loading));
        DisposableObserver<LoginResponseBody> observer = new DisposableObserver<LoginResponseBody>() {
            @Override
            public void onNext(LoginResponseBody loginResponseBody) {
                if (isError(loginResponseBody.getStatus())) {
                    showMessage(loginResponseBody.getStatus().getMessage());
                } else {
                    appPrefs.setId(loginResponseBody.getId());
                    appPrefs.setToken(loginResponseBody.getToken());
                    goToListing();
                }
            }

            @Override
            public void onError(Throwable e) {
                dismissDialog();
                showMessage(e.getMessage());
            }

            @Override
            public void onComplete() {
                dismissDialog();
            }
        };
        advisoryAppsClient.login(loginRequestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        compositeDisposable.add(observer);
    }
}
