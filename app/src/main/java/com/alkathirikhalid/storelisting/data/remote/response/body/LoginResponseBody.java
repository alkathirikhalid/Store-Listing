package com.alkathirikhalid.storelisting.data.remote.response.body;

import com.alkathirikhalid.storelisting.data.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseBody {
    private String id;
    private String token;
    private Status status;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Status getStatus() {
        return status;
    }
}

