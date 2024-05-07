package org.example.service;

import org.example.exception.DataNotFoundException;
import org.example.model.BaseModel;
import org.example.repository.BaseRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class BaseService<T extends BaseModel, R extends BaseRepository<T>> {

    protected final R repository;

    protected BaseService(R repository) {
        this.repository = repository;
    }
    public abstract boolean check(T t);

    public int add(T t) {
        if (check(t)) {
            return -1;
        }
        repository.save(t);
        return 1;
    }

    public void delete(UUID id){
        repository.delete(id);
    }

    public void update(UUID id, T update){
        repository.update(id,update);
    }
    public T findById(UUID id) throws DataNotFoundException {
        Optional<T> optional = repository.findById(id);
        return optional.orElseThrow(new Supplier<DataNotFoundException>() {
            @Override
            public DataNotFoundException get() {
                return new DataNotFoundException("Data not found");
            }
        });
    }
}
