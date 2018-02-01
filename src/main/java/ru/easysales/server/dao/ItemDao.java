package ru.easysales.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.easysales.server.entity.Item;

import java.sql.Date;
import java.util.List;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {
    List<Item> findByDateInLessThanEqualAndDateOutGreaterThanEqual(Date dateIn, Date dateOut);
}
