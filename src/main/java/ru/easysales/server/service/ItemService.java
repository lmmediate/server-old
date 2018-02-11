package ru.easysales.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ru.easysales.server.repository.ItemRepository;
import ru.easysales.server.entity.Item;

import java.security.Principal;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getCurrentItems() {
        Date dateNow = new Date(System.currentTimeMillis());
        // TODO: exclude accounts property
        return itemRepository.findByDateInLessThanEqualAndDateOutGreaterThanEqual(dateNow, dateNow);
    }

    public Set<String> getCurrentCategories() {
        Set<String> set = new HashSet<>();
        List<Item> items = getCurrentItems();
        for (Item item : items) {
            set.add(item.getCategory());
        }
        return set;
    }

    public Item addItem(Item item) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "crawlDate");
        Item found = itemRepository.findOne(Example.of(item, matcher));
        if (found != null) {
            return found;
        }
        return itemRepository.save(item);
    }

    public Object getInfo() {
        return new Object() {
            public static final int ITEMS_PER_PAGE = 30;

            public int getItemCount() {
                return ItemService.this.getCurrentItems().size();
            }

            public int getItemsPerPage() {
                return ITEMS_PER_PAGE;
            }

            public int getNumPages() {
                return (int) Math.ceil(getItemCount() / (double) ITEMS_PER_PAGE);
            }
        };
    }
}
