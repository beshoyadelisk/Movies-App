package com.example.movieapp;

import android.app.Application;

import com.example.movieapp.component.DaggerNetComponent;
import com.example.movieapp.component.NetComponent;
import com.example.movieapp.module.AppModule;
import com.example.movieapp.module.NetModule;
import com.example.movieapp.utilities.Constants;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class App extends Application {

    public static Picasso picassoWithCache;
    NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Constants.BASE_URL))
                .build();
        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        Cache cache = new Cache(httpCacheDirectory, 15 * 1024 * 1024);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().cache(cache);
        picassoWithCache = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(okHttpClientBuilder.build())).build();
        Picasso.setSingletonInstance(picassoWithCache);
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
