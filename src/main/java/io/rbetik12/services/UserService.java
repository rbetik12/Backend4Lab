package io.rbetik12.services;

import io.rbetik12.models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

@Stateless
public class UserService {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAUnit");
    private EntityManager em = factory.createEntityManager();

    public void createUser(User user) {
        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public boolean checkUsernameExistance(User user){
        try {
            User _user = (User) em.createQuery(" from User where username = :username")
                    .setParameter("username", user.getUsername()).getSingleResult();
            return _user == null;
        } catch (NoResultException e){ return true; }
    }

    public boolean authenticate(User user) {
        try {
            User _user = (User) em.createQuery(" from User where username = :username")
                    .setParameter("username", user.getUsername()).getSingleResult();
            if (_user != null) {
                if (_user.getPassword().equals(String.valueOf(user.getPassword().hashCode()))) {
                    user.setId(_user.getId());
                    return true;
                }
            }
            return false;
        } catch (NoResultException e){ return false; }
    }
}
