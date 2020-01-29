package com.nnz.eurekaclient1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nnz.eurekaclient1.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
