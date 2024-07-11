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

package com.bellande_api.bellande_cpu_usage;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class bellande_cpu_usage_service {
    private final bellande_cpu_usage_api cpuUsageApi;
    private final String apiAccessKey;
    private final String inputEndpoint;
    private final String outputEndpoint;

    public bellande_cpu_usage_service(String apiUrl, String inputEndpoint, String outputEndpoint, String apiAccessKey, bellande_cpu_usage_api cpuUsageApi) {
        this.cpuUsageApi = cpuUsageApi;
        this.apiAccessKey = apiAccessKey;
        this.inputEndpoint = inputEndpoint;
        this.outputEndpoint = outputEndpoint;
    }

    public String getCpuUsage(String connectivityPasscode) {
        bellande_cpu_usage_api.RequestBody apiRequestBody = new bellande_cpu_usage_api.RequestBody("get_cpu_usage", connectivityPasscode);

        try {
            Response<bellande_cpu_usage_api.BellandeResponse> response = cpuUsageApi.getBellandeResponse(inputEndpoint, apiRequestBody, apiAccessKey).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getCpuUsage();
            } else {
                throw new RuntimeException("Error getting CPU USAGE: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error getting CPU USAGE: " + e.getMessage());
        }
    }

    public String sendCpuUsage(String cpuUsage, String connectivityPasscode) {
        bellande_cpu_usage_api.RequestBody apiRequestBody = new bellande_cpu_usage_api.RequestBody(cpuUsage, connectivityPasscode);

        try {
            Response<bellande_cpu_usage_api.BellandeResponse> response = cpuUsageApi.sendBellandeResponse(outputEndpoint, apiRequestBody, apiAccessKey).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getStatus();
            } else {
                throw new RuntimeException("Error sending CPU USAGE: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error sending CPU USAGE: " + e.getMessage());
        }
    }
}
