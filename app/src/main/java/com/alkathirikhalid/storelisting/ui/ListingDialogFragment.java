package com.alkathirikhalid.storelisting.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alkathirikhalid.storelisting.R;
import com.alkathirikhalid.storelisting.base.BaseView;
import com.alkathirikhalid.storelisting.data.Listing;
import com.alkathirikhalid.storelisting.data.remote.request.body.ListingUpdateRequestBody;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */
public class ListingDialogFragment extends AppCompatDialogFragment {
    private static String KEY_ID = "EXTRA_KEY_ID";
    private static String KEY_LIST_NAME = "EXTRA_KEY_LIST_NAME";
    private static String KEY_DISTANCE = "EXTRA_KEY_DISTANCE";
    @BindView(R.id.et_update_id)
    EditText et_update_id;
    @BindView(R.id.et_update_name)
    EditText et_update_name;
    @BindView(R.id.et_update_distance)
    EditText et_update_distance;
    @BindView(R.id.bt_update_update)
    MaterialButton bt_update_update;
    private String id;
    private String list_name;
    private String distance;

    static ListingDialogFragment newInstance(Listing listing) {
        ListingDialogFragment listingDialogFragment = new ListingDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ID, listing.getId());
        bundle.putString(KEY_LIST_NAME, listing.getList_name());
        bundle.putString(KEY_DISTANCE, listing.getDistance());
        listingDialogFragment.setArguments(bundle);

        return listingDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString(KEY_ID);
        list_name = bundle.getString(KEY_LIST_NAME);
        distance = bundle.getString(KEY_DISTANCE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_listing, container, false);
        ButterKnife.bind(this, view);
        bt_update_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        et_update_id.setText(id);
        et_update_name.setText(list_name);
        et_update_distance.setText(distance);
    }

    private void update() {
        if ("".equals(et_update_id.getText().toString()) || "".equals(et_update_name.getText().toString()) || "".equals(et_update_distance.getText().toString())) {
            ((BaseView) getActivity()).showMessage(getString(R.string.all_values_required));
        } else {
            ListingUpdateRequestBody listingUpdateRequestBody = new ListingUpdateRequestBody();
            listingUpdateRequestBody.setListing_id(et_update_id.getText().toString());
            listingUpdateRequestBody.setListing_name(et_update_name.getText().toString());
            listingUpdateRequestBody.setDistance(et_update_distance.getText().toString());
            ((ListingActivity) getActivity()).remoteUpdate(listingUpdateRequestBody);
        }
    }
}