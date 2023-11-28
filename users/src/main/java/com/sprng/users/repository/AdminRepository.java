package com.sprng.users.repository;

import com.sprng.library.entity.AdminIshop;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends  LoginDataRepository<AdminIshop>  {
}
