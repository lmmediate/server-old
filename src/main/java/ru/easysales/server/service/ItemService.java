package ru.easysales.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.easysales.server.dao.ItemDao;
import ru.easysales.server.entity.Item;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    public List<Item> getCurrentItems() {
        return itemDao.getCurrentItems();
    }

    public Item addItem(Item item) {
        return itemDao.save(item);
    }

    public Object getInfo() {
        return new Object() {
            public int getItemCount() {
                return ItemService.this.getCurrentItems().size();
            }
        };
    }
}
