package fr.efrei.badtracker.services;

import java.util.concurrent.ExecutionException;

import fr.efrei.badtracker.api.MatchApi;
import fr.efrei.badtracker.api.ApiTask;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private final MatchApi matchApi;

    private static ApiService apiService;

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://badtracker.thomaslacaze.fr/")
                .addConverterFactory(GsonConverterFactory.create())
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
