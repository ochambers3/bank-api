package com.owenc.bank_api.repository;

import com.owenc.bank_api.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

// InMemoryUserRepository is a simple datastructure implementation of the dataset.
public class InMemoryUserRepository implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User findUserById(String id) {
        return users.get(id);
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }
}

