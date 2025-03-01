package com.keepitsimples.examples.spring.jpa.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Paul Rhoades on 01/03/2025.
 */
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    MyUser findById(long id);

    List<MyUser> findByLastName(String lastName);
}