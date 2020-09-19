package io.rbetik12.services;

import io.rbetik12.models.Point;
import io.rbetik12.models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

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

        System.out.println(point);

        em.getTransaction().begin();
        em.persist(point);
        em.getTransaction().commit();
    }

    private boolean check(Point point) {
        if (point.getX() >= 0 && point.getY() <= 0 && point.getX() <= (point.getR() / 2) && point.getY() >= -point.getR())
            return true;
        if (point.getX() >= 0 && point.getY() >= 0 && point.getY() <= (point.getR() - point.getX()) / 2)
            return true;
        return point.getX() <= 0 && point.getY() >= 0 && point.getX() * point.getX() + point.getY() * point.getY() <= point.getR() * point.getR();
    }
}
