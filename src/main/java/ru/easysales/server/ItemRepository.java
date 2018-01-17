package ru.easysales.server;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

    @Query("from Item i where current_date >= i.dateIn and current_date <= i.dateOut")
    Iterable<Item> getCurrentItems();
}
