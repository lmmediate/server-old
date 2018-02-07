package ru.easysales.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.easysales.server.entity.Item;

import java.sql.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByDateInLessThanEqualAndDateOutGreaterThanEqual(Date dateIn, Date dateOut);
}
