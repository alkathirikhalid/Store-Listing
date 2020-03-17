package com.alkathirikhalid.storelisting.data.remote.response.body;

import com.alkathirikhalid.storelisting.data.Listing;
import com.alkathirikhalid.storelisting.data.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListingResponseBody {
    @JsonProperty("listing")
    private ArrayList<Listing> listings;
    private Status status;

    public ArrayList<Listing> getListings() {
        return listings;
    }

    public Status getStatus() {
        return status;
    }

}

