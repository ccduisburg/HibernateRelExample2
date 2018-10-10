package com.personTest.dao;

import java.util.List;

public interface PDAO <T,S>{
    void create(T t);
    void update(S s,T t);
        void delete(S s);
        T find(S s);
        List<T> findAll();


}
