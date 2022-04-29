package com.LSJ.Blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class CategoryController {

    @GetMapping("/categorys/save")
    public String showSave(){return "usr/category/save";}

    @PostMapping("/categorys/save")
    public String

}
