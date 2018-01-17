package ru.easysales.server.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.easysales.server.entity.Item;

import java.util.List;

@Repository
public interface ItemDao extends CrudRepository<Item, Integer> {

    @Query("from Item i where current_date >= i.dateIn and current_date <= i.dateOut")
    List<Item> getCurrentItems();
}
