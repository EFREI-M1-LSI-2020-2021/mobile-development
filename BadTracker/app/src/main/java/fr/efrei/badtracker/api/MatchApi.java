package fr.efrei.badtracker.api;

import java.util.List;

import fr.efrei.badtracker.api.dtos.MatchDto;
import fr.efrei.badtracker.api.dtos.SetDto;
import fr.efrei.badtracker.api.dtos.CreateMatchDto;
import fr.efrei.badtracker.api.dtos.PlayerDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MatchApi {

    @POST("matches")
    Call<String> addMatch(@Body CreateMatchDto createMatchDto);

    @PATCH("matches/{id}/sets")
    Call<String> addSet(@Path("id") int id, @Body SetDto setDto);

    @PATCH("matches/{id}/winners")
    Call<String> addWinners(@Path("id") int id, @Body List<PlayerDto> winners);

    @PATCH("matches/{id}/losers")
    Call<String> addLosers(@Path("id") int id, @Body List<PlayerDto> losers);

    @GET("matches")
    Call<List<MatchDto>> getMatches();

    @GET("matches/{id}")
    Call<MatchDto> getMatch(@Path("id") int id);

    @DELETE("matches/{id}")
    Call<String> deleteMatch(@Path("id") int id);
}
