package ru.easysales.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Routes {
    @RequestMapping({"/", "/discounts/**", "/shoplist"})
    public String index() {
        return "forward:/index.html";
    }
}
