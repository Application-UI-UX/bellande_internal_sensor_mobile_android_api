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
 */
package com.bellande_api.bellande_ram_usage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bellande_api.bellande_ram_usage.bellande_ram_usage_service;
import com.bellande_api.bellande_ram_usage.bellande_ram_usage_api;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.reflect.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class bellande_ram_usage_activity extends AppCompatActivity {
    private bellande_ram_usage_service bellande_ram_usage_service;

    public bellande_ram_usage_activity(Context context) {
        Map<String, Object> config = loadConfigFromFile(context);
        String apiUrl = (String) config.get("url");
        String endpointPath = (String) ((Map<String, Object>) config.get("endpoint_path")).get("2d");
        String apiAccessKey = (String) config.get("Bellande_Framework_Access_Key");

        bellande_ram_usage_api bellande_ram_usage_api = new Retrofit.Builder()
                .baseUrl(apiUrl + endpointPath)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(bellande_ram_usage_api.class);

        bellande_ram_usage_service = new bellande_ram_usage_service(apiUrl, endpointPath, apiAccessKey, bellande_ram_usage_api);
    }

    @SuppressLint("LongLogTag")
    Map<String, Object> loadConfigFromFile(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("configs.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            Log.e("bellande_ram_usage_activity", "Error reading config file: " + e.getMessage());
        }
        return null;
    }
}
