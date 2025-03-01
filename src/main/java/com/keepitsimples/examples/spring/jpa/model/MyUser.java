package com.keepitsimples.examples.spring.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Created by Paul Rhoades on 01/03/2025.
 */
@Entity
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    public MyUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected MyUser() {
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
}
