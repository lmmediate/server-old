package ru.easysales.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ItemResource {

    @Autowired
    ItemRepository repository;

    @GetMapping("/sales")
    public Iterable<Item> getItems() {
        return repository.findAll();
    }
}
