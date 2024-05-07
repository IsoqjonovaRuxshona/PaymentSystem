package org.example.service;

import org.example.model.BaseModel;
import org.example.repository.BaseRpository;

public abstract class BaseService<T extends BaseModel, R extends BaseRpository> {
    protected R repository;
    public BaseService(R repository) {
        this.repository = repository;
    }
}
