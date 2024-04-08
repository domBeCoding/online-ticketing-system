package org.example.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.example.model.Customer;
import org.example.model.Movie;
import org.example.model.TicketAgent;
import org.example.model.VenueManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriter<T> {

    protected List<T> list;

    protected T object;

    protected ObjectMapper mapper = new ObjectMapper();
    protected String filePath;
    protected final CollectionType listType;

    public FileWriter(Class<T> elementType) {
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

    public void append(List<T> appendList) {

        try {
            list = mapper.readValue(new File(filePath), listType);

            List<T> elementsAlreadyInDb = appendList
                    .stream()
                    .filter(x -> list.contains(x))
                    .toList();

            List<T> elementsNotInDb = appendList
                    .stream()
                    .filter(x -> !list.contains(x))
                    .toList();

            if(elementsNotInDb.isEmpty()) throw new RuntimeException("Added elements all already exist in DB: " + elementsAlreadyInDb);

            list.addAll(elementsNotInDb);
            mapper.writeValue(new File(filePath), list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void overwrite(List<T> list) {
        try {
            mapper.writeValue(new File(filePath), list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
