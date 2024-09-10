package com.owenc.bank_api.repository;

import com.owenc.bank_api.model.User;
import java.util.List;

// User Repository constructor implemented by InMemoryUserRepository.java
// If a database were used, it would implement UserRepository as well.
public interface UserRepository {
    void saveUser(User user);
    User findUserById(String id);
    List<User> findAllUsers();
}
