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

    public <T> Response<T> execute(Call<T> call) {
        ApiTask<T> task = new ApiTask<>();
        task.execute(call);
        try {
            return task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public MatchApi getMatchApi() {
        return matchApi;
    }
}
