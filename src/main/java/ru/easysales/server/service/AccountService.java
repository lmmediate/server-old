package ru.easysales.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.easysales.server.entity.Account;
import ru.easysales.server.entity.Item;
import ru.easysales.server.repository.AccountRepository;
import ru.easysales.server.repository.ItemRepository;

import java.util.Collections;
import java.util.Set;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ItemRepository itemRepository;

    public Set<Item> getShoplist() {
        return getCurrentAccount().getItems();
    }

    public void addItemToShopList(int id) {
        Account account = getCurrentAccount();
        Item item = itemRepository.getItemById(id);
        if (item == null) {
            return;
        }
        account.getItems().add(item);
        accountRepository.save(account);
    }

    public void clearShopList() {
        Account account = getCurrentAccount();
        account.getItems().clear();
        accountRepository.save(account);
    }

    public void deleteItemFromShopList(int id) {
        Account account = getCurrentAccount();
        account.getItems().removeIf(item -> item.getId() == id);
        accountRepository.save(account);
    }

    private Account getCurrentAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return accountRepository.findByUsername(user.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account != null) {
            return new User(account.getUsername(),
                    account.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    AuthorityUtils.createAuthorityList("USER"));
        } else {
            throw new UsernameNotFoundException("Could not find the user '"
                    + username + "'");
        }
    }
}
