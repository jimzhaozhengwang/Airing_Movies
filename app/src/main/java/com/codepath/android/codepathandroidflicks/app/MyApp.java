package com.codepath.android.codepathandroidflicks.app;

import android.app.Application;

import com.codepath.android.codepathandroidflicks.module.AppModule;
import com.codepath.android.codepathandroidflicks.module.DaggerNetComponent;
import com.codepath.android.codepathandroidflicks.module.NetComponent;
import com.codepath.android.codepathandroidflicks.module.NetModule;

public class MyApp extends Application{
    private static NetComponent mNetComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

    public static NetComponent getmNetComponent(){
        return mNetComponent;
    }

}
