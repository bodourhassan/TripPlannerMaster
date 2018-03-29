package com.ititeam.tripplannermaster.model;

/**
 * Created by Hanaa on 3/29/2018.
 */

public class User {
    private static String email;
    private static String name;
    private static String password;
    private static String firebasePath;

    public static String getFirebasePath() {
        return firebasePath;
    }

    public static void setFirebasePath(String firebasePath) {
        User.firebasePath = firebasePath;
    }

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
