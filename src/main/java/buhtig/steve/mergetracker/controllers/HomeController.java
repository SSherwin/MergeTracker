package buhtig.steve.mergetracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController {

    @SuppressWarnings("SameReturnValue")
    @RequestMapping("/")
    String index() {
        return "index";
    }
}
