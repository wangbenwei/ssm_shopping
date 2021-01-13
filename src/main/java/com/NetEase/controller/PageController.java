package com.NetEase.controller;

import org.springframework.stereotype.Controller;
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


    @RequestMapping("shoppingCart")
    public String shoppingCart() {
        return "admin/shoppingCart";
    }


}
