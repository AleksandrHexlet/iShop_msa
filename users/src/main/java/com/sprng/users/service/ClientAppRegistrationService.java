package com.sprng.users.service;

import com.sprng.library.entity.ClientRegisterData;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.library.service.RandomGeneration;
import com.sprng.users.repository.ClientAppRepository;
import com.sprng.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.oauth2.core.oidc.OidcScopes;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class ClientAppRegistrationService {
    private final RoleRepository roleRepository;
    private ClientAppRepository clientAppRepository;
    private PasswordEncoder passwordEncoder;
    private RandomGeneration randomGeneration;

    @Autowired
    public ClientAppRegistrationService(ClientAppRepository clientAppRepository,
                                        PasswordEncoder passwordEncoder, RoleRepository roleRepository
    ) {
        this.clientAppRepository = clientAppRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.randomGeneration = randomGeneration;
    }

    private Map<String, Object> createTokenSettings() {
        Map<String, Object> tokenSettings = new HashMap<>();
        tokenSettings.put("settings.token.reuse-refresh-tokens", true); // true или false - возможность повторного использования одного refresh токена
        tokenSettings.put("settings.token.authorization-code-time-to-live", List.of(Duration.class.getName(),
                Duration.ofMinutes(10))); // время жизни временного кода авторизации AUTHORIZATION_CODE, например 10 минут
        tokenSettings.put("settings.token.access-token-time-to-live", List.of(Duration.class.getName(),
                Duration.ofDays(7)));  // время жизни access токена, например 1 неделя
        tokenSettings.put("settings.token.refresh-token-time-to-live", List.of(Duration.class.getName(),
                Duration.ofDays(30))); // время жизни refresh токена, например 30 дней
        tokenSettings.put("settings.token.id-token-signature-algorithm", List.of("org.springframework.security" +
                ".oauth2.jose.jws.SignatureAlgorithm", "RS256")); //алгоритм шифрования подписи
        Map<String, String> insert = new HashMap<>();
        insert.put("@class", "org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat");
        insert.put("value", "self-contained");// тип токена: self-contained. Ограничен временем жизни, содержит полезную нагрузку
        // (JWT - один из возможных форматов (реализаций) self-contained токенов)
        tokenSettings.put("settings.token.access-token-format", insert);
        return tokenSettings;
    }

    ;

    private Map<String, Object> createClientSettingsMap() {
        Map<String, Object> clientSettingsMap = new HashMap<>();
        clientSettingsMap.put("settings.client.require-proof-key", true);
        clientSettingsMap.put("settings.client.require-authorization-consent", false);
        return clientSettingsMap;
    }


//    public RegisteredClient clientRegistration(ClientRegisterData clientRegisterData) throws IshopResponseException {

//        Consumer<Set<String>> insertStringsToBuilderSet = (stringSet) -> stringSet
//                .addAll(clientRegisterData.getRedirectURL());
//        if (registeredClientRepositoryCustom.findById(clientRegisterData.getClientName().strip()) != null)
//            throw new IshopResponseException("Client exist.");
//        if (clientAppRepository.findByUserName(clientRegisterData.getClientName().strip()).isPresent())
//            throw new IshopResponseException("Login exist.");
//
//        Consumer<Set<String>> scopeSET = (stringSet) -> {
//            stringSet.addAll(clientRegisterData.getScopes());
//            stringSet.add(OidcScopes.OPENID);
//        };
//
//        String clientSecretClean = String.valueOf(Math.random());
//
//        String clientSecret = passwordEncoder.encode(clientSecretClean);
//        String id = passwordEncoder.encode(String.valueOf(Math.random()));
//        String clientId = passwordEncoder.encode(String.valueOf(Math.random()));
//
//        Instant clientIdIssuedAt = ZonedDateTime.now(ZoneId.systemDefault()).toInstant();
//        Instant clientSecretExpiresAt = ZonedDateTime.now(ZoneId.systemDefault()).plusMonths(6).toInstant();
//
//        RegisteredClient clientRegister = RegisteredClient.withId(clientRegisterData.getClientName().strip())
//                .clientId(clientId)
////                .tokenSettings(clientRegisterData.getPassword().strip())
//                .clientSecret(clientSecret)
//                .clientIdIssuedAt(clientIdIssuedAt)
//                .clientSecretExpiresAt(clientSecretExpiresAt)
////                .scope(clientRegisterData.getScopes().stream().map((scope)-> scope).toString())
//                .scopes(scopeSET)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.client_secret_basic)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////                .redirectUris(insertStringsToBuilderSet)
//                .redirectUri(clientRegisterData.getRedirectURL().strip())
//                .postLogoutRedirectUri(clientRegisterData.getPostLogoutRedirectURL().strip())
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
//                .build();
//        System.out.println("SERVICE clientRegisterData == " + clientRegisterData.getClientName() + " ; "
//                + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
//        System.out.println("Client Exist in DataBase  == " + registeredClientRepository
//                .findById(clientRegisterData.getClientName()));
//
//        String clientPassword = passwordEncoder.encode(clientRegisterData.getPassword());
//        ClientApp clientApp = new ClientApp(clientRegisterData.getClientName(), clientPassword,clientSecretClean);
//
//        registeredClientRepositoryCustom.save(clientRegister);
//
//        // назначаем роль владельцам клиентских приложений
//        Role clientRole = roleRepository.findByRoleType(Role.RoleType.ROLE_CLIENT).orElseGet(()->{
//            Role role = new Role();
//            role.setRoleType(Role.RoleType.ROLE_CLIENT);
//            return roleRepository.save(role);
//        });
//
//        clientApp.setRole(clientRole);
//
//        clientAppRepository.save(clientApp);
//        return clientRegister;
//        return  null;
//    }



    private String getRandomNumber() {
        return UUID.randomUUID().toString();
    }

    public ClientRegisterData registrationAppClient(ClientRegisterData clientRegisterData) throws IshopResponseException {
        if (clientAppRepository.existsByClientName(clientRegisterData.getClientName()))
            throw new IshopResponseException("The client application with this name exist");
        String clientSecretNotSecured = randomGeneration.generate(() -> {
            return String.valueOf(Math.random());
        });

        String clientSecret = randomGeneration.generate(() -> {
            return passwordEncoder.encode(clientSecretNotSecured);
        });

        String id = randomGeneration.generate(() -> {
            String newId;
            do {
                newId = passwordEncoder.encode(UUID.randomUUID().toString());
            } while (clientAppRepository.existsById(newId));
            return newId;
        });

        String clientId = randomGeneration.generate(() -> {
            String newClientId;
            do {
                newClientId = passwordEncoder.encode(UUID.randomUUID().toString());
            } while (clientAppRepository.existsById(newClientId));
            return newClientId;
        });

        ClientRegisterData clientRegistrationData = new ClientRegisterData.Builder(clientRegisterData.getClientName())
                .id(id).clientId(clientId).clientSecret(clientSecret)
                .clientSecretNotSecured(clientSecretNotSecured)
                .authorizationGrantTypes(Set.of("authorization_code", "refresh_token"))
                .clientAuthenticationMethods(Set.of("client_secret_basic"))
                .clientSettings(createClientSettingsMap())
                .clientSecretExpiresAt(ZonedDateTime.now().plusMonths(999))
                .scopes(Set.of("Products.Write", "Products.Read"))
                .redirectUris(Set.of(clientRegisterData.getRedirectUris()))
                .postLogoutRedirectUris(Set.of(clientRegisterData.getPostLogoutRedirectUris()))
                .tokenSettings(createTokenSettings())
                .build();


        return clientRegistrationData;
    }
}

