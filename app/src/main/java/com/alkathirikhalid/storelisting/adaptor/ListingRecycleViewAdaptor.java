package com.alkathirikhalid.storelisting.adaptor;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alkathirikhalid.storelisting.R;
import com.alkathirikhalid.storelisting.data.Listing;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */
public class ListingRecycleViewAdaptor extends RecyclerView.Adapter<ListingRecycleViewAdaptor.ListingRecycleViewHolder> {
    ListingListener listingListener;
    private ArrayList<Listing> listings;
    private int selectedItem = -1;

    public ListingRecycleViewAdaptor(ArrayList<Listing> listings, ListingListener listingListener) {
        this.listings = listings;
        this.listingListener = listingListener;
    }

    @NonNull
    @Override
    public ListingRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_listing_item, parent, false);
        return new ListingRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingRecycleViewHolder holder, int position) {
        holder.tv_listing_item.setText("Store no: " + listings.get(position).getId());
        if (position == selectedItem) {
            holder.itemView.setBackgroundColor(Color.GREEN);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    @Override
    public int getItemCount() {
        return (listings != null) ? listings.size() : 0;
    }

    public interface ListingListener {
        void onItemClicked(int position, Listing listing);

        void onItemLongClicked(int position, Listing listing);
    }

    class ListingRecycleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_listing_item)
        TextView tv_listing_item;

        public ListingRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listingListener.onItemClicked(getAdapterPosition(), listings.get(getAdapterPosition()));
                    selectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, listings.size());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listingListener.onItemLongClicked(getAdapterPosition(), listings.get(getAdapterPosition()));
                    selectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, listings.size());
                    return true;
                }
            });
        }
    }
}