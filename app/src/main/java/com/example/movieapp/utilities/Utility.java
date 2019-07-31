package com.example.movieapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.movieapp.model.TopRatedShowsModel.TopRatedDetailResults;
import com.example.movieapp.model.PopularShowsModel.Result;
import com.example.movieapp.model.FavouriteShowsResult;

public class Utility {

    public static int PAGE_ONE_SHOWS_SIZE = 0;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    public interface ClickCallBacks {
        void onClick(Result result, int position);

        void onRatedShowClick(TopRatedDetailResults ratedDetailResults, int position);

        void onFavouriteShowClick(FavouriteShowsResult favouriteShowsResult, int position);
    }

}
