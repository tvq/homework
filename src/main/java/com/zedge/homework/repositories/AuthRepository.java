package com.zedge.homework.repositories;

import org.springframework.security.core.Authentication;

public interface AuthRepository {
    Authentication getAuthentication(String token);
}
