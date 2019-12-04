package com.naung9.eurekaclient1.repositories;

import com.naung9.eurekaclient1.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends CrudRepository<Role, Integer> {
    List<Role> findAll();
}
