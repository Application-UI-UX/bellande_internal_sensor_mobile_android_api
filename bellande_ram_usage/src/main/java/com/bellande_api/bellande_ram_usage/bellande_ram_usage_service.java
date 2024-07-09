/**
 * Copyright (C) 2024 Bellande Application UI UX Research Innovation Center, Ronaldson Bellande
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 **/

package com.bellande_api.bellande_ram_usage;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class bellande_ram_usage_service {
    private final bellande_ram_usage_api bellande_ram_usage_api;

    public bellande_ram_usage_service(String apiUrl, String endpointPath, String apiAccessKey, bellande_ram_usage_api bellande_ram_usage_api) {
        this.bellande_ram_usage_api = bellande_ram_usage_api;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl + endpointPath)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(bellande_ram_usage_api.class);
    }

    public bellande_ram_usage_api.BellandeResponse getPrediction(String inputText) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), inputText);
        bellande_ram_usage_api.RequestBody apiRequestBody = new bellande_ram_usage_api.RequestBody(
                inputText
        );

        try {
            Response<bellande_ram_usage_api.BellandeResponse> response = bellande_ram_usage_api.getBellandeResponse(apiRequestBody).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                throw new RuntimeException("Error getting prediction: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error getting prediction: " + e.getMessage());
        }
    }
}