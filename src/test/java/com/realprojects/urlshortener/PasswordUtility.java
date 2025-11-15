package com.realprojects.urlshortener;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtility {
    public static void main(String[] args) {
        //Over here we are encoding 2 plain texts, secret and admin
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("secret"));
        System.out.println(encoder.encode("admin"));
    }// This file was solely executed and 2 encoded texts were provided and those got updated in Db in users table through V_3
}
