package io.rbetik12.services;

import io.rbetik12.models.User;

import javax.ejb.Stateless;

@Stateless
public class ValidationService {
    public boolean validateUser(User user) {
        return true;
    }
}
