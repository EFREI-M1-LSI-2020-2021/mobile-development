package fr.efrei.badtracker.api;

import java.util.List;

import fr.efrei.badtracker.api.dtos.CreateMatchDto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MatchApi {

    @POST("matches")
    Call<ResponseBody> addMatch(@Body CreateMatchDto createMatchDto);

    @GET("matches")
    Call<List<MatchDto>> getMatches();

    @GET("matches/{id}")
    Call<MatchDto> getMatch(@Path("id") int id);

    @DELETE("matches/{id}")
    Call<String> deleteMatch(@Path("id") int id);
}
