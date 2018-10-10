package com.edutilos.javaFX.dao;

import java.util.List;

public interface DAO<T,S> {
    void create(T t);
    void update(S s,T t);
  //  void delete(T t);
    void delete(S s);
    T find(S s);

    List<T> findAll();

}
