package ucalled911.AiringMovies.app;

import android.app.Application;

import ucalled911.AiringMovies.module.AppModule;
import ucalled911.AiringMovies.module.DaggerNetComponent;
import ucalled911.AiringMovies.module.NetComponent;
import ucalled911.AiringMovies.module.NetModule;

public class MyApp extends Application {
    private static NetComponent mNetComponent;

    public static NetComponent getmNetComponent() {
        return mNetComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

}
