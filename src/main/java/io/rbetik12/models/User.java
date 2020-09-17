package io.rbetik12.models;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    private long id;
    public String username;
    public String password;

    public User() {

    }

    @Override
    public String toString() {
        return username + " " + password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
