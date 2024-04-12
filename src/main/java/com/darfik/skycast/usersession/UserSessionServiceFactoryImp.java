package com.darfik.skycast.usersession;

public class UserSessionServiceFactoryImp implements UserSessionServiceFactory {

    @Override
    public UserSessionService build() {
        return new UserSessionService();
    }
}
