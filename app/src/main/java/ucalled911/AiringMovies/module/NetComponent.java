package ucalled911.AiringMovies.module;


import javax.inject.Singleton;

import dagger.Component;
import ucalled911.AiringMovies.app.MainActivity;
import ucalled911.AiringMovies.app.MovieDetailsActivity;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);

    void inject(MovieDetailsActivity activity);
}
