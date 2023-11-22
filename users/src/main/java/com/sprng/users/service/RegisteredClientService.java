package com.sprng.users.service;


import com.sprng.library.entity.ClientApp;
import com.sprng.library.entity.Role;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.ClientRegisterData;
import com.sprng.users.repository.ClientAppRepository;
import com.sprng.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class RegisteredClientService {
    private final RoleRepository roleRepository;
    private final ClientAppRepository clientAppRepository;
    private RegisteredClientRepository registeredClientRepository;
    private  PasswordEncoder passwordEncoder;

    @Autowired
    public RegisteredClientService(RegisteredClientRepository registeredClientRepository,
                                   PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                                   ClientAppRepository clientAppRepository) {
        this.registeredClientRepository = registeredClientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.clientAppRepository = clientAppRepository;
    }

    public RegisteredClient clientRegistration(ClientRegisterData clientRegisterData) throws  IshopResponseException {

//        Consumer<Set<String>> insertStringsToBuilderSet = (stringSet) -> stringSet
//                .addAll(clientRegisterData.getRedirectURL());
        if (registeredClientRepository.findById(clientRegisterData.getClientName().strip()) != null)
            throw new IshopResponseException("Client exist.");
        if (clientAppRepository.findByUserName(clientRegisterData.getClientName().strip()).isPresent())
            throw new IshopResponseException("Login exist.");

        Consumer<Set<String>> scopeSET = (stringSet) -> {
            stringSet.addAll(clientRegisterData.getScopes());
            stringSet.add(OidcScopes.OPENID);
        };

        String clientSecretClean = String.valueOf(Math.random());

        String clientSecret = passwordEncoder.encode(clientSecretClean);
        String id = passwordEncoder.encode(String.valueOf(Math.random()));
        String clientId = passwordEncoder.encode(String.valueOf(Math.random()));

        Instant clientIdIssuedAt = ZonedDateTime.now(ZoneId.systemDefault()).toInstant();
        Instant clientSecretExpiresAt = ZonedDateTime.now(ZoneId.systemDefault()).plusMonths(6).toInstant();

        RegisteredClient clientRegister = RegisteredClient.withId(clientRegisterData.getClientName().strip())
                .clientId(clientId)
//                .tokenSettings(clientRegisterData.getPassword().strip())
                .clientSecret(clientSecret)
                .clientIdIssuedAt(clientIdIssuedAt)
                .clientSecretExpiresAt(clientSecretExpiresAt)
//                .scope(clientRegisterData.getScopes().stream().map((scope)-> scope).toString())
                .scopes(scopeSET)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUris(insertStringsToBuilderSet)
                .redirectUri(clientRegisterData.getRedirectURL().strip())
                .postLogoutRedirectUri(clientRegisterData.getPostLogoutRedirectURL().strip())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .build();
        System.out.println("SERVICE clientRegisterData == " + clientRegisterData.getClientName() + " ; "
                + clientRegisterData.getScopes() + " ; " + clientRegisterData.getRedirectURL());
        System.out.println("Client Exist in DataBase  == " + registeredClientRepository
                .findById(clientRegisterData.getClientName()));

        String clientPassword = passwordEncoder.encode(clientRegisterData.getPassword());
        ClientApp clientApp = new ClientApp(clientRegisterData.getClientName(), clientPassword,clientSecretClean);

        registeredClientRepository.save(clientRegister);

        // назначаем роль владельцам клиентских приложений
        Role clientRole = roleRepository.findByRoleType(Role.RoleType.ROLE_CLIENT).orElseGet(()->{
            Role role = new Role();
            role.setRoleType(Role.RoleType.ROLE_CLIENT);
            return roleRepository.save(role);
        });

        clientApp.setRole(clientRole);

        clientAppRepository.save(clientApp);
        return clientRegister;
    }
}
