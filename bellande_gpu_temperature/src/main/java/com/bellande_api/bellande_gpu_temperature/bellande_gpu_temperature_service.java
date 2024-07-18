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

package com.bellande_api.bellande_gpu_temperature;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class bellande_gpu_temperature_service {
    private final bellande_gpu_temperature_api gpuTemperatureApi;
    private final String apiAccessKey;
    private final String inputEndpoint;
    private final String outputEndpoint;

    public bellande_gpu_temperature_service(String apiUrl, String inputEndpoint, String outputEndpoint, String apiAccessKey, bellande_gpu_temperature_api gpuTemperatureApi) {
        this.gpuTemperatureApi = gpuTemperatureApi;
        this.apiAccessKey = apiAccessKey;
        this.inputEndpoint = inputEndpoint;
        this.outputEndpoint = outputEndpoint;
    }

    public String getGpuTemperature(String connectivityPasscode) {
        bellande_gpu_temperature_api.RequestBody apiRequestBody = new bellande_gpu_temperature_api.RequestBody("get_gpu_temperature", connectivityPasscode);

        try {
            Response<bellande_gpu_temperature_api.BellandeResponse> response = gpuTemperatureApi.getBellandeResponse(inputEndpoint, apiRequestBody, apiAccessKey).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getGpuTemperature();
            } else {
                throw new RuntimeException("Error getting GPU TEMPERATURE usage: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error getting GPU TEMPERATURE usage: " + e.getMessage());
        }
    }

    public String sendGpuTemperature(String gpuTemperature, String connectivityPasscode) {
        bellande_gpu_temperature_api.RequestBody apiRequestBody = new bellande_gpu_temperature_api.RequestBody(gpuTemperature, connectivityPasscode);

        try {
            Response<bellande_gpu_temperature_api.BellandeResponse> response = gpuTemperatureApi.sendBellandeResponse(outputEndpoint, apiRequestBody, apiAccessKey).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getStatus();
            } else {
                throw new RuntimeException("Error sending GPU TEMPERATURE usage: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error sending GPU TEMPERATURE usage: " + e.getMessage());
        }
    }
}
