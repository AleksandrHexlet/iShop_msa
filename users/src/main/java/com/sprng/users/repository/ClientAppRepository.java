package com.sprng.users.repository;


import com.sprng.library.entity.ClientRegisterData;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientAppRepository extends CrudRepository<ClientRegisterData, String> {

    Optional<ClientRegisterData> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
