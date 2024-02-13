package org.klozevitz.itprom_test.dao;

import java.util.List;

public interface IDaoDb<E> {
    List<E> findAll();

    E findById(Integer id);

    E save(E e);

//    E update(E e);

    E delete(Integer id);
}
