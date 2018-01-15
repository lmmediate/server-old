package ru.easysales.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ItemResource {

    @Autowired
    ItemRepository repository;

    @GetMapping("/sales")
    public Iterable<Item> getItems() {
        return repository.findAll();
    }

    @PostMapping("/sales")
    public Item addItem(@RequestBody Item item){
        return repository.save(item);
    }
}