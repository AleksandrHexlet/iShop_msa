package com.sprng.users.repository;

import com.sprng.library.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository <Role, Integer> {

    public Optional<Role> findByRoleType(Role.RoleType roleType);

}
