package com.example.movieapp.component;

import com.example.movieapp.MainActivity;
import com.example.movieapp.module.AppModule;
import com.example.movieapp.module.NetModule;
import com.example.movieapp.ui.DetailsFragment;
import com.example.movieapp.ui.MainFragment;
import com.example.movieapp.ui.activity.DetailActivity;
import com.example.movieapp.ui.activity.SettingsActivity;
import com.example.movieapp.ui.activity.SplashActivity;
import com.example.movieapp.widget.ShowWidgetProvider;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(MainActivity mainActivity);

    void inject(SplashActivity splashActivity);

    void inject(MainFragment mainFragment);

    void inject(DetailsFragment detailsFragment);

    void inject(SettingsActivity.PrefFrag prefFrag);

    void inject(DetailActivity detailActivity);

    void inject(ShowWidgetProvider showWidgetProvider);
}
