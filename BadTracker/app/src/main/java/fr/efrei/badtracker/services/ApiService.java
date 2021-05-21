package fr.efrei.badtracker.services;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.util.Date;

import fr.efrei.badtracker.api.ApiTask;
import fr.efrei.badtracker.api.MatchApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private final MatchApi matchApi;

    private static ApiService apiService;

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://badtracker.thomaslacaze.fr/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()))
                        .create()))
                .build();

        matchApi = retrofit.create(MatchApi.class);
    }

    public static ApiService getInstance() {
        if(apiService == null) {
            apiService = new ApiService();
        }

        return apiService;
    }

    public <T> void execute(Call<T> call, ApiTask.Listener<T> listener) {
        ApiTask<T> task = new ApiTask<>(listener);
        task.execute(call);
    }

    public MatchApi getMatchApi() {
        return matchApi;
    }
}
