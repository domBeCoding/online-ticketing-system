package org.example.deleter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.example.exception.NoSuchMovieException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileDeleter<T> {

    List<T> currentList;
    protected ObjectMapper mapper = new ObjectMapper();
    protected String filePath;
    protected CollectionType listType;

    public FileDeleter(String filePath, Class<T> elementType) {
        //Overcome generic type erasure.
        TypeFactory typeFactory = mapper.getTypeFactory();
        listType = typeFactory.constructCollectionType(List.class, elementType);
        this.filePath = filePath;
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

//    public void remove(List<String> removeList) {
//
//        try {
//            currentList = mapper.readValue(new File(filePath), listType);
//
//            List<T> absentElements = removeList
//                    .stream()
//                    .filter(x -> !currentList.stream().toList().contains(x))
//                    .toList();
//
//            if (!absentElements.isEmpty()) {
//                throw new NoSuchMovieException("Delete failure: Following elements not present in database:" + absentElements);
//            }
//
//            currentList.removeAll(removeList);
//            mapper.writeValue(new File(filePath), currentList);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
