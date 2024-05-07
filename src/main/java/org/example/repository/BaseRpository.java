package org.example.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.model.BaseModel;
import org.example.model.User;
import org.example.service.BaseService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class BaseRpository<T extends BaseModel> {
    protected String path;
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void write(T  t){
        ArrayList<T> ts = readAll();
        ts.add(t);
        try {
            objectMapper.writeValue(new File(path),ts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<T> readAll(){
        TypeReference<ArrayList<T>> typeReference = new TypeReference<ArrayList<T>>() {};
        try {
            return objectMapper.readValue(new File(path), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
