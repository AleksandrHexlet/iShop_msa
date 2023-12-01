package com.sprng.oauth2.repository;



import com.sprng.library.entity.LoginData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginDataRepository extends JpaRepository<LoginData, Integer> {
    Optional<LoginData> findByUserName(String userName);

}
