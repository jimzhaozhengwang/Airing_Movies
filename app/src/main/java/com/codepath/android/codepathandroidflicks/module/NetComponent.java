package com.codepath.android.codepathandroidflicks.module;


import com.codepath.android.codepathandroidflicks.app.MainActivity;
import com.codepath.android.codepathandroidflicks.app.MovieDetailsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
    void inject(MovieDetailsActivity activity);
}
