package org.example.controller;

import org.example.exception.NoSuchFieldException;
import org.example.exception.NoSuchMovieException;
import org.example.generator.DateGenerator;
import org.example.model.Movie;
import org.example.service.MovieService;
import org.example.validator.DeleteMovieValidator;
import org.example.validator.EditMovieValidator;
import org.example.validator.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.utility.InputReader.read;
import static org.example.utility.InputReader.readYesOrNo;
import static org.example.utility.Utility.printMessage;
import static org.example.utility.Utility.printSpace;
import static org.example.validator.MovieValidator.validateFields;

public class VenueManagerController {
    private final MovieService movieService = new MovieService();
    private final DateGenerator dateGenerator = new DateGenerator();

    public VenueManagerController() {
    }

    public void routeOption(String option) {

        switch (option) {
            case "1":
                addController();
                break;
            case "2":
                deleteController();
                break;
            case "3":
                editController();
                break;
            case "4":
                printMessage("Logging off...");
                break;
            default:
                break;
        }
    }

    public void addController() {

        boolean finishAddingMovie = false;
        List<Movie> movies = new ArrayList<>();

        while (!finishAddingMovie) {
            printSpace();
            printMessage("Please enter the (name) of the movie you'd like to add");
            String movieName = read();

            printMessage("Please enter the available (time) showing daily in 'hh:mm' 24h format, seperated by commas");
            String availableTimes = read();

            printMessage("Please enter the start (date) in 'dd-mm-yy' format");
            String startDate = read();

            printMessage("Please enter how the number of days the movie will be showing");
            String airingDays = read();

            printMessage("Please enter the (length) of the movie");
            String length = read();

            printMessage("Please enter the (rating) out of 5");
            String rating = read();

            printMessage("Please enter the (price) in 'xx.xx' format");
            String price = read();

            List<String> listOfAvailableTimes =
                    Arrays.stream(availableTimes.trim().split(","))
                            .map(String::trim)
                            .toList();

            try{
                validateFields(movieName, listOfAvailableTimes, startDate, airingDays, length, rating, price);
            } catch (IllegalArgumentException e) {

                printSpace();
                printMessage("Movie added unsuccessfully");
                printMessage(e.getMessage());

                printSpace();
                printMessage("Would you like to add another movie? (y/n)");
                if (!readYesOrNo()) {
                    break;
                }
                continue;
            }

            List<String> generatedDates = dateGenerator.generate(startDate, airingDays);

            Movie movie = new Movie(movieName,
                    listOfAvailableTimes,
                    generatedDates,
                    Integer.parseInt(length),
                    Integer.parseInt(rating),
                    Double.parseDouble(price));

            movies.add(movie);

            printMessage("Would you like to add another movie? (y/n)");
            if (!readYesOrNo()) {
                finishAddingMovie = true;
            }
        }

        try {
            movieService.addMovies(movies);
            printSpace();
            printMessage("Successfully added movies: ");
            for (Movie movie : movies) {
                printMessage(String.valueOf(movie));
            }
        } catch (RuntimeException e) {
            printMessage("Movies added unsuccessfully");
            System.out.println(e.getMessage());
        }
    }

    public void deleteController() {

        boolean finishAddingMovie = false;
        List<String> movies = new ArrayList<>();

        while (!finishAddingMovie) {
            printSpace();
            printMessage("Please enter the (name) of the movie you'd like to delete");
            String movieName = read();

            try{
                DeleteMovieValidator.validate(movieName);
                movies.add(movieName);
                finishAddingMovie = true;
            } catch (IllegalArgumentException e) {
                printSpace();
                printMessage("Movie removed unsuccessfully");
                printMessage(e.getMessage());

                printSpace();
                printMessage("Would you like to remove another movie? (y/n)");
                if (!readYesOrNo()) {
                    finishAddingMovie = true;
                }
            }
        }

        try {
            for(String movie : movies) {
                movieService.eraseMovieByName(movie);
            }
            printSpace();
            printMessage("Successfully deleted movies: ");
            for (String movie : movies) {
                printMessage(movie);
            }
        } catch (NoSuchMovieException e) {
            printMessage("Remove movies unsuccessful.");
            System.out.println(e.getMessage());
        }
    }

    public void editController() {
        boolean finishedEditingMovie = false;

        while (!finishedEditingMovie) {
            printSpace();
            printMessage("Please enter the (name) of the movie you'd like to edit");
            String movieName = read();

            printMessage("Please enter the field you'd like to edit (name|length|rating|price)");
            String field = read();

            printMessage("Please enter the new value of " + "(" + field + ")");
            String value = read();

            try{

                EditMovieValidator.validate(movieName, field, value);
            } catch (IllegalArgumentException e) {
                printSpace();
                printMessage("Movie edited unsuccessfully");
                printMessage(e.getMessage());

                printSpace();
                printMessage("Would you like to edit another Movie? (y/n)");

                if (!readYesOrNo()) {
                    finishedEditingMovie = true;
                }
                continue;
            }

            try {
                printSpace();
                movieService.editMovie(movieName, field, value);
                printSpace();

                printMessage("Movie edited successfully.");
            } catch (NoSuchFieldException e) {
                printMessage("Movie edited unsuccessfully.");
            }

            printSpace();
            printMessage("Would you like to edit another Movie? (y/n)");

            if (!readYesOrNo()) {
                finishedEditingMovie = true;
            }
        }
    }
}
