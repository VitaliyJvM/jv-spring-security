package mate.academy.spring.service;

import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import mate.academy.spring.model.CinemaHall;
import mate.academy.spring.model.Movie;
import mate.academy.spring.model.MovieSession;
import org.springframework.stereotype.Component;

@Component
public class DataInjector {
    private final MovieSessionService movieSessionService;
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;
    private final AuthenticationService authService;

    public DataInjector(MovieSessionService movieSessionService, MovieService movieService,
                        CinemaHallService cinemaHallService, AuthenticationService authService) {
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
        this.authService = authService;
    }

    @PostConstruct
    public void init() {
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        Movie movieTerminator = new Movie("The Terminator");
        movieTerminator.setDescription("Action with Arnold Schwarzenegger. 1984");
        movieService.add(movieTerminator);

        CinemaHall yellow = new CinemaHall();
        yellow.setCapacity(20);
        yellow.setDescription("Yellow hall");
        cinemaHallService.add(yellow);

        CinemaHall green = new CinemaHall();
        green.setCapacity(80);
        green.setDescription("Green hall");
        cinemaHallService.add(green);

        MovieSession msFastAndFurious1 = new MovieSession(fastAndFurious, yellow,
                LocalDateTime.of(2021, 9, 12, 9, 0));
        movieSessionService.add(msFastAndFurious1);

        MovieSession msFastAndFurious2 = new MovieSession(fastAndFurious, yellow,
                LocalDateTime.of(2021, 9, 12, 12, 0));
        movieSessionService.add(msFastAndFurious2);

        MovieSession msFastAndFurious3 = new MovieSession(fastAndFurious, green,
                LocalDateTime.of(2021, 9, 12, 18, 0));
        movieSessionService.add(msFastAndFurious3);

        MovieSession msFastAndFurious4 = new MovieSession(fastAndFurious, yellow,
                LocalDateTime.of(2021, 9, 14, 9, 0));
        movieSessionService.add(msFastAndFurious4);

        MovieSession msFastAndFurious5 = new MovieSession(fastAndFurious, green,
                LocalDateTime.of(2021, 9, 14, 9, 0));
        movieSessionService.add(msFastAndFurious5);

        MovieSession msTerminator1 = new MovieSession(movieTerminator, green,
                LocalDateTime.of(2021, 9, 12, 9, 0));
        movieSessionService.add(msTerminator1);

        MovieSession msTerminator2 = new MovieSession(movieTerminator, yellow,
                LocalDateTime.of(2021, 9, 12, 18, 0));
        movieSessionService.add(msTerminator2);

        authService.register("bob@i.ua", "1234");
        authService.register("alice@i.ua", "4321");
    }
}
