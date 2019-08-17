package com.bashar.rickmortyepisodes;

import android.content.Context;
import android.net.ConnectivityManager;

class NetworkManager {

    boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
