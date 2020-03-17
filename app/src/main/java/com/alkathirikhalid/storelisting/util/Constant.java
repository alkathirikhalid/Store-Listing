package com.alkathirikhalid.storelisting.util;

/**
 * Created by alkathirikhalid on 3/17/2020.
 * Store Listing.
 */
public class Constant {
    public static final class APIConfig {
        public static final int CONNECTION_TIMEOUT_SEC = 10;
        public static final String BASE_URL = "https://interview.advisoryapps.com/";
    }

    public static final class APIEndPoint {
        public static final String LOGIN = "login";
        public static final String LISTING = "listing";
        public static final String UPDATE = "listing/update";
    }

    public static final class AppPrefsSettings {
        public static final String APP_PREFS_NAME = "appprefs";
    }

    public static final class AppPrefsKey {
        public static final String APP_PREFS_KEY_ID = "id";
        public static final String APP_PREFS_KEY_TOKEN = "token";
    }
}
