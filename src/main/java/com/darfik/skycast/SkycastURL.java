package com.darfik.skycast;

public enum SkycastURL {
    HOME_URL("/home"),
    LOGIN_URL("/login"),
    REGISTER_URL("/register"),
    SEARCH_URL("/search");


    private final String url;

    SkycastURL(String url) {
        this.url = url;
    }

    public String getValue() {
        return url;
    }
}
