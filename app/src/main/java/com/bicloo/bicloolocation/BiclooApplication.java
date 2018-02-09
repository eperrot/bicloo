package com.bicloo.bicloolocation;

import android.app.Application;

import com.bicloo.bicloolocation.network.ApiUtils;
import com.bicloo.bicloolocation.network.ApiService;


public class BiclooApplication extends Application {


    private ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        apiService = ApiUtils.getApiService(getString(R.string.jcdecaux_base_url));
    }


    public ApiService getApiService() {
        return apiService;
    }
}
