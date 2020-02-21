package com.zedge.homework.repositories;

import com.zedge.homework.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findOrFail(int id);
    User save(User user);
}
