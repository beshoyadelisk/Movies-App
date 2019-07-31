package com.example.movieapp.utilities;




import com.example.movieapp.model.CharactersModel.CharacterResult;
import com.example.movieapp.model.PopularShowsModel.PopularPageResult;
import com.example.movieapp.model.SimilarShowsResult.SimilarShowResults;
import com.example.movieapp.model.TopRatedShowsModel.TopRatedResults;
import com.example.movieapp.model.VideoModel.VideoResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("popular")
    io.reactivex.Observable<PopularPageResult> getPopularShows(@Query("api_key") String apiKey,
                                                  @Query("page") int page);

    @GET("top_rated")
    io.reactivex.Observable<TopRatedResults> getTopRatedShows(@Query("api_key") String apiKey,
                                                 @Query("page") int page);

    @GET("{tv_id}/videos")
    io.reactivex.Observable<VideoResult> getTrailers(@Path("tv_id") int id,
                                                     @Query("api_key") String apiKey);

    @GET("{tv_id}/credits")
    Observable<CharacterResult> getCrew(@Path("tv_id") int tvId,
                                        @Query("api_key") String key);

    @GET("{tv_id}/similar")
    Observable<SimilarShowResults> getSimilarShows(@Path("tv_id") int showId,
                                                   @Query("api_key") String key);

}

