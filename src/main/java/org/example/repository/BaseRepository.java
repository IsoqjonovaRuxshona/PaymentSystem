package org.example.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.example.exception.DataNotFoundException;
import org.example.model.BaseModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseRepository<T extends BaseModel> {
    protected String path;
    protected Class<T> type;
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

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
        for (T datum : data) {
            if (datum.getId().equals(id)) {
                return Optional.of(datum);
            }
        }
        return Optional.empty();
    }

    public ArrayList<T> getAllByState(boolean state) {
        ArrayList<T> data = readFromFile();
        ArrayList<T> newData = new ArrayList<>();
        for (T datum : data) {
            if(datum.isActive() == state) {
                newData.add(datum);
            }
        }
        return newData;
    }
    public void writeToFile(ArrayList<T> data) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            objectMapper.writeValue(file, data);
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
