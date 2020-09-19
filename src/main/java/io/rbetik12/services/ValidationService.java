package io.rbetik12.services;

import io.rbetik12.models.Point;
import io.rbetik12.models.User;

import javax.ejb.Stateless;

@Stateless
public class ValidationService {
    public boolean validateUser(User user) {
        if (user.getUsername().length() > 255 || user.getPassword().length() > 255) {
            return false;
        }
        return true;
    }

    public boolean validatePoint(Point point) {
        System.out.println(point.getX() >= 5 && point.getX() <= -3);
        System.out.println(point.getY() >= 3 && point.getY() <= -3);
        System.out.println(point.getR() >= 5 && point.getR() <= -3);
        if (point.getX() >= 5 && point.getX() <= -3) return false;
        if (point.getY() >= 3 && point.getY() <= -3) return false;
        return !(point.getR() >= 5) || !(point.getR() <= -3);
    }
}
