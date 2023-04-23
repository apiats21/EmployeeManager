package com.piatsevich.EmployeeManager.service;

import java.util.List;

public interface GenericService<T, ID> {
    T save(T t);

    List<T> findAll();

    T findById(ID id);

    void deleteById(ID id);

    boolean isExists(ID id);
}
