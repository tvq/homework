package com.zedge.homework.repositories.impl;

import com.zedge.homework.models.User;
import com.zedge.homework.repositories.AuthRepository;
import com.zedge.homework.repositories.UserRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JdbiUserRepository implements UserRepository, AuthRepository {
    @Autowired
    private Jdbi jdbi;

    @Override
    public List<User> findAll() {
        return jdbi.withHandle(
                handle -> handle.createQuery("SELECT * FROM users")
                        .mapTo(User.class)
        ).list();
    }

    @Override
    public User findOrFail(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE id = :id")
                        .bind("id", id)
                        .mapTo(User.class)
        ).findFirst().orElseThrow();
    }

    @Override
    public User save(User user) {
        jdbi.useHandle(handle -> handle.createUpdate("INSERT INTO users(id, name, token) VALUES (:id, :name, :token)")
            .bind("id", user.getId())
            .bind("name", user.getName())
            .bind("token", user.getToken())
            .execute()
        );

        return user;
    }

    public User findOrFailByToken(String token) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE token = :token")
                        .bind("token", token)
                        .mapTo(User.class)
        ).findFirst().orElseThrow();
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails user = this.findOrFailByToken(token);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }
}
