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

    @PATCH("matches/{id}/team1")
    Call<String> addTeam1(@Path("id") int id, @Body List<PlayerDto> team1);

    @PATCH("matches/{id}/team2")
    Call<String> addTeam2(@Path("id") int id, @Body List<PlayerDto> team2);

    @GET("matches")
    Call<List<MatchDto>> getMatches();

    @GET("matches/{id}")
    Call<MatchDto> getMatch(@Path("id") int id);

    @DELETE("matches/{id}")
    Call<String> deleteMatch(@Path("id") int id);
}
