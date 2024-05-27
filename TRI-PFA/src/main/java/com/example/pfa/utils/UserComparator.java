package com.example.pfa.utils;
import com.example.pfa.entities.User;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        return Long.compare(user1.getId(), user2.getId());
    }

}


