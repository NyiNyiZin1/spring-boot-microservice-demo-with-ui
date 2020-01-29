package com.nnz.eurekaclient1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nnz.eurekaclient1.entities.Role;

import java.util.List;

@Repository
public interface RoleRepo extends CrudRepository<Role, Integer> {
    List<Role> findAll();
}
