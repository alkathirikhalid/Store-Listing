package com.alkathirikhalid.storelisting.data.rest;

import android.database.Observable;

import com.alkathirikhalid.storelisting.data.remote.response.body.ListingResponseBody;
import com.alkathirikhalid.storelisting.data.remote.response.body.ListingUpdateResponseBody;
import com.alkathirikhalid.storelisting.data.remote.response.body.LoginResponseBody;
import com.alkathirikhalid.storelisting.util.Constant;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */
public interface AdvisoryAppsAPI {
    @FormUrlEncoded
    @POST(Constant.APIEndPoint.LOGIN)
    Observable<LoginResponseBody>
    login(@Field("email") String email, @Field("password") String password);

    @GET(Constant.APIEndPoint.LISTING)
    Observable<ListingResponseBody>
    listing(@Query("id") String id, @Query("token") String token);

    @FormUrlEncoded
    @POST(Constant.APIEndPoint.UPDATE)
    Observable<ListingUpdateResponseBody>
    update(@Field("id") String id, @Field("token") String token,
           @Field("listing_id") String listing_id, @Field("listing_name") String listing_name,
           @Field("distance") String distance);
}
