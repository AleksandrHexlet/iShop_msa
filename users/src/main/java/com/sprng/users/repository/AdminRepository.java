package com.sprng.users.repository;

import com.sprng.library.entity.AdminIshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminIshop,Integer> {
    AdminIshop findByUserName(String username);
}
