package com.odnovolov.randomuser.network.model;

import com.odnovolov.randomuser.model.User;
import com.odnovolov.randomuser.model.UserImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mapper {
    public static List<User> userResponseToUsers(RandomUserResponse userResponse) {
        List<RandomUserResponse.Result> results = userResponse.results;
        List<User> users = new ArrayList<>(userResponse.results.size());
        for (RandomUserResponse.Result userResponseResult : results) {
            UserImpl user = extractUser(userResponseResult);
            users.add(user);
        }
        return users;
    }

    private static UserImpl extractUser(RandomUserResponse.Result userResponseResult) {
        return new UserImpl(
                UUID.randomUUID().toString(),
                userResponseResult.name.first,
                userResponseResult.name.last,
                userResponseResult.picture.large,
                userResponseResult.dob.age,
                userResponseResult.location.city,
                userResponseResult.email
        );
    }
}