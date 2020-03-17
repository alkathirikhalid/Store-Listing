package com.alkathirikhalid.storelisting.ui;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alkathirikhalid.storelisting.R;
import com.alkathirikhalid.storelisting.adaptor.ListingRecycleViewAdaptor;
import com.alkathirikhalid.storelisting.base.BaseView;
import com.alkathirikhalid.storelisting.data.Listing;
import com.alkathirikhalid.storelisting.data.remote.request.body.ListingRequestBody;
import com.alkathirikhalid.storelisting.data.remote.request.body.ListingUpdateRequestBody;
import com.alkathirikhalid.storelisting.data.remote.response.body.ListingResponseBody;
import com.alkathirikhalid.storelisting.data.remote.response.body.ListingUpdateResponseBody;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ListingActivity extends BaseView implements ListingRecycleViewAdaptor.ListingListener {
    @BindView(R.id.rv_listing_items)
    RecyclerView rv_listing_items;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ListingDialogFragment listingDialogFragment;
    int updatedPosition;
    ArrayList<Listing> listings;
    @BindView(R.id.btn_listing_logout)
    MaterialButton btn_listing_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ButterKnife.bind(this);
        btn_listing_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        if (!appPrefs.getId().equals("") || !appPrefs.getToken().equals("") || appPrefs.getToken().length() > 3) {
            ListingRequestBody listingRequestBody = new ListingRequestBody();
            listingRequestBody.setId(appPrefs.getId());
            listingRequestBody.setToken(appPrefs.getToken());
            remoteListing(listingRequestBody);
            rv_listing_items.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rv_listing_items.setLayoutManager(layoutManager);
        } else {
            logOut();
        }
    }

    private void remoteListing(ListingRequestBody listingRequestBody) {
        showDialog(getString(R.string.listing), getString(R.string.loading));
        DisposableObserver<ListingResponseBody> observer = new DisposableObserver<ListingResponseBody>() {
            @Override
            public void onNext(ListingResponseBody listingResponseBody) {
                if (isError(listingResponseBody.getStatus())) {
                    showMessage(listingResponseBody.getStatus().getMessage());
                    logOut();
                } else {
                    listings = listingResponseBody.getListings();
                    adapter = new ListingRecycleViewAdaptor(listings, ListingActivity.this);
                    rv_listing_items.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
        advisoryAppsClient.listing(listingRequestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        compositeDisposable.add(observer);
    }

    @Override
    public void onItemClicked(int position, Listing listing) {
        showMessage("Name: " + listing.getList_name() + "\nDistance: " + listing.getDistance());
    }

    @Override
    public void onItemLongClicked(int position, Listing listing) {
        this.updatedPosition = position;
        listingDialogFragment = ListingDialogFragment.newInstance(listing);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(getString(R.string.update));
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.add(listingDialogFragment, getString(R.string.update));
        fragmentTransaction.commitAllowingStateLoss();
    }

    void remoteUpdate(final ListingUpdateRequestBody listingUpdateRequestBody) {
        listingUpdateRequestBody.setId(appPrefs.getId());
        listingUpdateRequestBody.setToken(appPrefs.getToken());
        showDialog(getString(R.string.update), getString(R.string.loading));
        DisposableObserver<ListingUpdateResponseBody> observer = new DisposableObserver<ListingUpdateResponseBody>() {
            @Override
            public void onNext(ListingUpdateResponseBody listingUpdateResponseBody) {
                if (isError(listingUpdateResponseBody.getStatus())) {
                    showMessage(listingUpdateResponseBody.getStatus().getMessage());
                } else {
                    showMessage(listingUpdateResponseBody.getStatus().getMessage());
                    Listing listing = new Listing();
                    listing.setList_name(listingUpdateRequestBody.getListing_name());
                    listing.setDistance(listingUpdateRequestBody.getDistance());
                    listing.setId(listingUpdateRequestBody.getListing_id());
                    updateComplete(listing);
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
        advisoryAppsClient.update(listingUpdateRequestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        compositeDisposable.add(observer);
    }

    void updateComplete(Listing listing) {
        if (listingDialogFragment != null) {
            listingDialogFragment.dismiss();
        }
        listings.set(updatedPosition, listing);
        adapter.notifyDataSetChanged();

    }
}