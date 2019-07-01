package com.dgg.api;

import com.dgg.entity.User;

public interface IUserService {
    public User queryByusername(String name);

    void addUser(User user);

    User queryByuser(User user);

    int updatePassword(User user);
}
