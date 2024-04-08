package org.example.deleter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.example.exception.NoSuchMovieException;
import org.example.model.Customer;
import org.example.model.Movie;
import org.example.model.TicketAgent;
import org.example.model.VenueManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileDeleter<T> {

    List<T> currentList;
    protected ObjectMapper mapper = new ObjectMapper();
    protected String filePath;
    protected CollectionType listType;

    public FileDeleter(Class<T> elementType) {
        setFilePathByType(elementType);
        //Overcome generic type erasure.
        TypeFactory typeFactory = mapper.getTypeFactory();
        listType = typeFactory.constructCollectionType(List.class, elementType);
    }

    private void setFilePathByType(Class<T> elementType) {
        if (elementType.equals(Customer.class)) {
            filePath = "/Users/DPU09/Documents/University/online-ticketing-service/src/main/resources/customer.json";
        } else if (elementType.equals(TicketAgent.class)) {
            filePath = "/Users/DPU09/Documents/University/online-ticketing-service/src/main/resources/ticketAgentManager.json";
        } else if (elementType.equals(VenueManager.class)) {
            filePath = "/Users/DPU09/Documents/University/online-ticketing-service/src/main/resources/venueManager.json";
        } else if (elementType.equals(Movie.class)) {
            filePath = "/Users/DPU09/Documents/University/online-ticketing-service/src/main/resources/movies.json";
        }
    }

    public void remove(List<T> removeList) {

        try {
            currentList = mapper.readValue(new File(filePath), listType);

            List<T> absentElements = removeList
                    .stream()
                    .filter(x -> !currentList.stream().toList().contains(x))
                    .toList();

            if (!absentElements.isEmpty()) {
                throw new NoSuchMovieException("Delete failure: Following elements not present in database:" + absentElements);
            }

            currentList.removeAll(removeList);
            mapper.writeValue(new File(filePath), currentList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
