package com.zedge.homework.repositories;

import com.zedge.homework.models.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JdbiUserRepository implements UserRepository {
    @Autowired
    private Jdbi jdbi;

    @Override
    public List<User> findAll() {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM users").mapTo(User.class)).list();
    }

    @Override
    public Optional<User> find(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM user WHERE id = :id").bind("id", id).mapTo(User.class)
        ).findFirst();
    }

    @Override
    public User save(User user) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO users(id, name) VALUES (:id, :name)")
            .bind("id", user.getId())
            .bind("name", user.getName())
            .execute()
        );

        return user;
    }
}
