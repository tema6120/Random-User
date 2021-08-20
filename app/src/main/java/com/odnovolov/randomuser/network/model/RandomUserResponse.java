package com.odnovolov.randomuser.network.model;

import java.util.*;

public class RandomUserResponse {
    Info info;
    List<Result> results;

    public static class Info {
        int page;
        int results;
        String seed;
        String version;
    }

    public static class Result {
        String cell;
        Dob dob;
        String email;
        String gender;
        Id id;
        Location location;
        Login login;
        Name name;
        String nat;
        String phone;
        Picture picture;
        Registered registered;
    }

    public static class Dob {
        int age;
        String date;
    }

    public static class Location {
        String city;
        Coordinates coordinates;
        String country;
        String postcode;
        String state;
        Street street;
        Timezone timezone;
    }

    public static class Login {
        String md5;
        String password;
        String salt;
        String sha1;
        String sha256;
        String username;
        String uuid;
    }

    public static class Name {
        String first;
        String last;
        String title;
    }

    public static class Picture {
        String large;
        String medium;
        String thumbnail;
    }

    public static class Registered {
        int age;
        String date;
    }

    public static class Street {
        String name;
        int number;
    }

    public static class Timezone {
        String description;
        String offset;
    }

    public static class Id {
        String name;
        Object value;
    }

    public static class Coordinates {
        String latitude;
        String longitude;
    }
}