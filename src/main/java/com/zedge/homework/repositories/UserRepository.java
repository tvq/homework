package com.zedge.homework.repositories;

import com.zedge.homework.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> find(int id);
    User save(User user);
}
