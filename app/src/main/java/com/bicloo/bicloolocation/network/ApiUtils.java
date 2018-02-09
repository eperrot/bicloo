package com.bicloo.bicloolocation.network;

public class ApiUtils {

    public static ApiService getApiService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(ApiService.class);
    }

}
