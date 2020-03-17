package com.alkathirikhalid.storelisting.data.rest;

import android.database.Observable;

import com.alkathirikhalid.storelisting.BuildConfig;
import com.alkathirikhalid.storelisting.data.remote.request.body.ListingRequestBody;
import com.alkathirikhalid.storelisting.data.remote.request.body.ListingUpdateRequestBody;
import com.alkathirikhalid.storelisting.data.remote.request.body.LoginRequestBody;
import com.alkathirikhalid.storelisting.data.remote.response.body.ListingResponseBody;
import com.alkathirikhalid.storelisting.data.remote.response.body.ListingUpdateResponseBody;
import com.alkathirikhalid.storelisting.data.remote.response.body.LoginResponseBody;
import com.alkathirikhalid.storelisting.util.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */
public class AdvisoryAppsClient {

    private AdvisoryAppsAPI advisoryAppsAPI;

    public AdvisoryAppsClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(Constant.APIConfig.CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(Constant.APIConfig.CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            httpClient.interceptors().add(httpLoggingInterceptor);
        }
        advisoryAppsAPI = new Retrofit.Builder()
                .baseUrl(Constant.APIConfig.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build().create(AdvisoryAppsAPI.class);
    }

    public Observable<LoginResponseBody>
    login(LoginRequestBody loginRequestBody) {
        return advisoryAppsAPI.login(loginRequestBody.getEmail(), loginRequestBody.getPassword());
    }

    public Observable<ListingResponseBody>
    listing(ListingRequestBody listingRequestBody) {
        return advisoryAppsAPI.listing(listingRequestBody.getId(), listingRequestBody.getToken());
    }

    public Observable<ListingUpdateResponseBody>
    update(ListingUpdateRequestBody listingUpdateRequestBody) {
        return advisoryAppsAPI.update(
                listingUpdateRequestBody.getId(),
                listingUpdateRequestBody.getToken(),
                listingUpdateRequestBody.getListing_id(),
                listingUpdateRequestBody.getListing_name(),
                listingUpdateRequestBody.getDistance()
        );
    }
}
