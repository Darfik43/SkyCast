package com.darfik.skycast.user;

public class UserServiceFactoryImp implements UserServiceFactory {

    @Override
    public UserService build() {
        return new UserService();
    }
}
