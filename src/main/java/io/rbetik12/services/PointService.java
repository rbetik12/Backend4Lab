package io.rbetik12.services;

import io.rbetik12.models.Point;
import io.rbetik12.models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class PointService {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAUnit");
    private EntityManager em = factory.createEntityManager();

    public void savePoint(Point point, long userID) {
        point.setHit(check(point));

        User user;
        try {
            user = (User) em.createQuery("from User where id = :id").setParameter("id", userID).getSingleResult();
        }
        catch (NoResultException e) {
            user = null;
        }
        point.setUser(user);

        em.getTransaction().begin();
        em.persist(point);
        em.getTransaction().commit();
    }

    private boolean check(Point _point) {
        Point point = new Point();
        point.setX(_point.getX());
        point.setY(_point.getY());
        point.setR(_point.getR());

        formatPoint(point);
        if (point.getX() >= 0 && point.getY() <= 0 && point.getX() <= (point.getR() / 2) && point.getY() >= -point.getR())
            return true;
        if (point.getX() >= 0 && point.getY() >= 0 && point.getY() <= (point.getR() - point.getX()) / 2)
            return true;
        return point.getX() <= 0 && point.getY() >= 0 && point.getX() * point.getX() + point.getY() * point.getY() <= point.getR() * point.getR();
    }

    private void formatPoint(Point point) {
        point.setX(point.getR() * (point.getX() - 150) / 130);
        point.setY(point.getR() * (150 - point.getY()) / 130);
    }

    public List<Point> getUserPoints(long userID) {
        List<Point> points = new ArrayList<>();

        try {
            points = (List<Point>) em.createQuery("from Point where user.id = :id").setParameter("id", userID).getResultList();
        }
        catch (NoResultException e) {
            System.out.println("Empty!");
        }
        return points;
    }
}
