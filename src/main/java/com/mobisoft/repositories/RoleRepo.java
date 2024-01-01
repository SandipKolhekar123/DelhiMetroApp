package com.mobisoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobisoft.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
