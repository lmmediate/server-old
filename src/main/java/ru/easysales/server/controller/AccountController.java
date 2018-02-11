package ru.easysales.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.easysales.server.entity.Item;
import ru.easysales.server.service.AccountService;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/shoplist")
    public Set<Item> getShopList() {
       return accountService.getShoplist();
    }
}
