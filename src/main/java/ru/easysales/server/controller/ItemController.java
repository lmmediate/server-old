package ru.easysales.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.easysales.server.entity.Item;
import ru.easysales.server.service.ItemService;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/sales")
    public Iterable<Item> getCurrentItems() {
        return itemService.getCurrentItems();
    }

    @GetMapping("/sales/categories")
    public Set<String> getCurrentCategories() {
        return itemService.getCurrentCategories();
    }

    @PostMapping("/sales")
    public Item addItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @GetMapping("/sales/info")
    public Object getInfo() {
        return itemService.getInfo();
    }
}

