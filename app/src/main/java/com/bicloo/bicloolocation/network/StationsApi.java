package com.bicloo.bicloolocation.network;


import android.content.res.Resources;
import android.util.Log;

import com.bicloo.bicloolocation.R;
import com.bicloo.bicloolocation.events.StationsResponseEvent;
import com.bicloo.bicloolocation.modele.Station;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationsApi {


    public static void fetchStations(ApiService service, Resources res) {
        service.getStations(res.getString(R.string.contract_value),
                res.getString(R.string.jcdecaux_apikey)).enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {

                if(response.isSuccessful()) {
                    Log.d("MainActivity", "posts loaded from API");

                    EventBus.getDefault().postSticky(new StationsResponseEvent(response.body()));

                } else {
                    int statusCode = response.code();
                    EventBus.getDefault().postSticky(new StationsResponseEvent(new Exception("Network issue, http code=" + statusCode)));
                }
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Log.w("StationsApi", "error loading from API: " + t.getMessage());

                EventBus.getDefault().post(new StationsResponseEvent(t));
            }
        });
    }

}
