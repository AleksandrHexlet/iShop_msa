package com.sprng.users.repository;


import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;


public class RegisteredClientRepositoryCustom extends JdbcRegisteredClientRepository {


    /**
     * Constructs a {@code JdbcRegisteredClientRepository} using the provided parameters.
     *
     * @param jdbcOperations the JDBC operations
     */
    public RegisteredClientRepositoryCustom(JdbcOperations jdbcOperations) {
        super(jdbcOperations);
    }

//    @Query(nativeQuery = true, value = "")
//// @Query(nativeQuery = true, value = "SELECT * FROM customer WHERE userName = :usernameOUT")
//    Optional<RegisteredClient>findByClientName(String clientName);
//    Optional<Customer> findByUserName(String userName);
//    public Optional<Customer> getCustomersByUserName(String usernameOUT);

    public RegisteredClient findByClientName(String clientName) {
        RegisteredClient registeredClient = getJdbcOperations()
                .queryForObject("select count(*) from client where clientName= ?", RegisteredClient.class, clientName);
        return  registeredClient;
    }

    public boolean isExist(String userName) {
        Integer countClient = getJdbcOperations()
                .queryForObject("select count(*) from client where clientName= ?", Integer.class, userName);
        return countClient != null;
    }

    ;
}
