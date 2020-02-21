package com.zedge.homework.http.controllers;

import com.zedge.homework.models.User;
import com.zedge.homework.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/")
    public List<User> index() {
        return repository.findAll();
    }
}
