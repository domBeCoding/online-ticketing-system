package org.example.reader;

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

public class FileReader<T> {

    protected List<T> objectList;

    private final ObjectMapper mapper = new ObjectMapper();
    private final CollectionType listType;
    private String filePath;

    public FileReader(Class<T> elementType) {
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

    public void read() {
        try {
            objectList = mapper.readValue(new File(filePath), listType);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    public List<T> getAll() {
        read();
        return objectList;
    }
}
