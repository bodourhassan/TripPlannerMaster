package com.ititeam.tripplannermaster.classes;

/**
 * Created by Hanaa on 3/29/2018.
 */

public class User {
    public static String email;
    public static String name;
    public static String password;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }
}
