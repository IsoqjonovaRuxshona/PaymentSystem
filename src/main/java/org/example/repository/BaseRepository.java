package org.example.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.example.exception.DataNotFoundException;
import org.example.model.BaseModel;
import org.example.model.Commission;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseRepository<T extends BaseModel>{
    protected String path;
    protected Class<T> type;
    public final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public int save(T t) {
        ArrayList<T> data = readFromFile();
        data.add(t);
        writeToFile(data);
        return 1;
    }

    public void delete(UUID id) {
        ArrayList<T> data = readFromFile();
        for (T datum : data) {
            if (datum.getId().equals(id)) {
                datum.setActive(false);
                return;
            }
        }
        writeToFile(data);
    }

    public void update(UUID id, T update) {
        ArrayList<T> data = readFromFile();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(id)) {
                update.setId(id);
                data.set(i, update);
            }
        }
        writeToFile(data);
    }




    public Optional<T> findById(UUID id) throws DataNotFoundException {
        ArrayList<T> data = readFromFile();
        return data.stream().filter(datum -> datum.getId().equals(id)).findFirst();
    }

    public ArrayList<T> getAllByState(boolean state) {
        ArrayList<T> data = readFromFile();
        return data.stream()
                .filter(datum -> datum.isActive() == state)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public void writeToFile(List<T> data) {
        try {
           Path filePath = Paths.get(path);
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            objectMapper.writeValue(filePath.toFile(), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<T> readFromFile() {
        try {
            TypeFactory t = TypeFactory.defaultInstance();
            File file = new File(path);

//            if(!(file.length()>0)) {
//                objectMapper.writeValue(new File(path),"[]");
//            }
            return objectMapper.readValue(file, t.constructCollectionType(ArrayList.class, type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<T> getAll() {
        return readFromFile();
    }
}
