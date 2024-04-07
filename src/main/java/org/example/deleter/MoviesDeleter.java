package org.example.deleter;

import org.example.model.Movie;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MoviesDeleter extends FileDeleter<Movie> {


    public MoviesDeleter() {
        super("/Users/DPU09/Documents/University/online-ticketing-service/src/main/resources/movies.json", Movie.class);
    }

    public void removeByName(String movieName) {
        try {
            currentList = mapper.readValue(new File(filePath), listType);

            currentList.stream()
                    .filter(x -> x.getName().equals(movieName))
                    .findFirst()
                    .ifPresentOrElse(x -> {
                        currentList.remove(x);
                    }, () -> {
                        throw new RuntimeException("Movie not found: " + movieName);
                    });

            mapper.writeValue(new File(filePath), currentList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(List<Movie> removeList) {
        removeList.forEach(x -> removeByName(x.getName()));
    }
}
