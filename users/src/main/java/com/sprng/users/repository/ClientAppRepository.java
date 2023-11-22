package com.sprng.users.repository;


import com.sprng.library.entity.ClientApp;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientAppRepository extends CrudRepository <ClientApp, Integer> {

    Optional<ClientApp> findByUserName(String userName);
}
