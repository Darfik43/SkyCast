package com.darfik.skycast.user;

public class UserServiceFactory {


    public static UserService build() {
        return new UserService();
    }
}
