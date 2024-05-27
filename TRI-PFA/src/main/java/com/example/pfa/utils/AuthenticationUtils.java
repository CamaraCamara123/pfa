package com.example.pfa.utils;

import com.example.pfa.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

    public static User getCurrentlyAuthenticatedUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
