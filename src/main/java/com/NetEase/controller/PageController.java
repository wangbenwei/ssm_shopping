package com.NetEase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PageController {

    @RequestMapping("loginPage")
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping("publish")
    public String publishPage() {
        return "admin/publish";
    }

    @RequestMapping("notListedPublish")
    public String notListedPublishPage(String msg, Model model) {
        System.out.println(msg);
        model.addAttribute("msg", msg);
        return "admin/notListedPublish";
    }

    @RequestMapping("shoppingCart")
    public String shoppingCart() {
        return "admin/shoppingCart";
    }
    
}
