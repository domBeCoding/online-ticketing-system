package org.example.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriter<T> {

    protected List<T> list;

    protected T object;

    protected ObjectMapper mapper = new ObjectMapper();
    protected String filePath;
    protected final CollectionType listType;

    public FileWriter(String filePath, Class<T> elementType) {
        //Overcome generic type erasure.
        TypeFactory typeFactory = mapper.getTypeFactory();
        listType = typeFactory.constructCollectionType(List.class, elementType);

        this.filePath = filePath;
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
