package com.bicloo.bicloolocation.network;


import com.bicloo.bicloolocation.modele.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // Stations:

    @GET("/vls/v1/stations")
    Call<List<Station>> getStations(@Query("contract") String contract, @Query("apiKey") String apiKey);

}
