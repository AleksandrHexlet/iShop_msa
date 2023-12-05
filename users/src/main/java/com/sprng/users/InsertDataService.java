package com.sprng.users;

import com.sprng.library.entity.AdminIshop;
import com.sprng.library.entity.ClientRegisterData;
import com.sprng.library.entity.Role;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.library.service.RandomGeneration;
import com.sprng.users.repository.AdminRepository;
import com.sprng.users.repository.ClientAppRepository;
import com.sprng.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ComponentScan(basePackages = "com.sprng.library.service")

@Service
public class InsertDataService {

    private RoleRepository roleRepository;
    private AdminRepository adminRepository;
    private ClientAppRepository clientAppRepository;
    private PasswordEncoder passwordEncoder;
    private RandomGeneration randomGeneration;

    @Autowired
    public InsertDataService(RoleRepository roleRepository, AdminRepository adminRepository,
                             ClientAppRepository clientAppRepository,
                             PasswordEncoder passwordEncoder, RandomGeneration randomGeneration) {
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.clientAppRepository = clientAppRepository;
        this.passwordEncoder = passwordEncoder;
        this.randomGeneration = randomGeneration;
    }


    @Bean
    public CommandLineRunner insertRole() {
        return (args) -> {
            Role roleAdmin = new Role();
            roleAdmin.setRoleType(Role.RoleType.ROLE_ADMIN);
            roleRepository.save(roleAdmin);

            Role roleTrader = new Role();
            roleTrader.setRoleType(Role.RoleType.ROLE_TRADER);
            roleRepository.save(roleTrader);

            Role roleCustomer = new Role();
            roleCustomer.setRoleType(Role.RoleType.ROLE_CUSTOMER);
            roleRepository.save(roleCustomer);

            Role roleClientOwner = new Role();
            roleClientOwner.setRoleType(Role.RoleType.ROLE_CLIENT_OWNER);
            roleRepository.save(roleClientOwner);

            Role roleClient = new Role();
            roleClient.setRoleType(Role.RoleType.ROLE_CLIENT);
            roleRepository.save(roleClient);


        };
    }


    @Bean
    public CommandLineRunner insertAdmine() {
        return (args) -> {
//              создаём админов, проверяем что такая роль уже есть  и если есть тогда я
//              через  public Optional<Role> findByRoleType(Role.RoleType roleType); беру роль и устанавливаю админу
//              также вынеси сюда создание клиентских приложений из com.sprng.users.service;ClientAppRegistrationService

            Role roleAdmin = roleRepository.findByRoleType(Role.RoleType.ROLE_ADMIN).orElseGet(() -> {
                Role role = new Role();
                role.setRoleType(Role.RoleType.ROLE_ADMIN);
                return roleRepository.save(role);
            });
            Role roleReadOnlyAdmin = roleRepository.findByRoleType(Role.RoleType.ROLE_READONLY_ADMIN).orElseGet(() -> {
                Role role = new Role();
                role.setRoleType(Role.RoleType.ROLE_READONLY_ADMIN);
                return roleRepository.save(role);
            });

            AdminIshop admin = new AdminIshop("Иван Админ");
            admin.setRole(roleAdmin);
            admin.setUserName("admin");
//            admin.setPassword("$2a$10$7oCTGflP2kNI3WP41FV2IOFyXVh9beW6e9ywgsew3/rmIOxoEq/LW");
            admin.setPassword(passwordEncoder.encode("123456789"));

            AdminIshop readOnlyAdmin = new AdminIshop("Ivan ReadOnly");
            readOnlyAdmin.setRole(roleReadOnlyAdmin);
            readOnlyAdmin.setUserName("readOnlyAdmin");
            readOnlyAdmin.setPassword("$2a$10$fnDj5PUIC0rWC1otWLxHbeRXK8Plfh1oHGriPC6QBI5cP99Tb3wTq");
//
            adminRepository.save(admin);
            adminRepository.save(readOnlyAdmin);

        };
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

    private Map<String, Object> createClientSettingsMap() {
        Map<String, Object> clientSettingsMap = new HashMap<>();
        clientSettingsMap.put("settings.client.require-proof-key", true); // front обязан присылать code-challenge и
        // code-challenge-method и code-verifare (это  code-challenge в не закодированном виде);
        clientSettingsMap.put("settings.client.require-authorization-consent", false);
        return clientSettingsMap;
    }

    @Bean
    public CommandLineRunner clientRegisterData() {
        return (args) -> {
            ClientRegisterData clientRegisterDataFirst;
            ClientRegisterData clientRegisterDataSecond;
            ClientRegisterData clientRegisterDataThird;
            try {
                clientRegisterDataFirst = new ClientRegisterData.Builder("MyFrontAdminMain")
                        .id("1").clientId("123").clientSecret(passwordEncoder.encode("123"))
                        .clientSecretNotSecured("123")
                        .authorizationGrantTypes(Set.of("authorization_code", "client_credentials"))
                        .clientAuthenticationMethods(Set.of("client_secret_basic", "client_secret_post"))
                        .clientSettings(createClientSettingsMap())
                        .clientSecretExpiresAt(ZonedDateTime.now().plusMonths(999))
                        .scopes(Set.of("Products.Write", "Products.Read", "Users.Create"))
                        .redirectUris(Set.of("https://stackoverflow.com", "https://yandex.ru"))
                        .postLogoutRedirectUris(Set.of("https://iShop.com", "https://mail.ru"))
                        .tokenSettings(createTokenSettings())
                        .build();

//                http://127.0.0.1:8080/oauth2/authorize?response_type=code&client_id=123&redirect_uri=https://yandex.ru&scope=Products.Write&code_challenge_method=S256&code_challenge=FeKw08M4keuw8e9gnsQZQgwg4yDOlMZfvIwzEkSOsiU


                clientRegisterDataSecond = new ClientRegisterData.Builder("MyFrontCustom")
                        .id("2").clientId("234").clientSecret(passwordEncoder.encode("234"))
                        .clientSecretNotSecured("234")
                        .authorizationGrantTypes(Set.of("authorization_code", "client_credentials"))
                        .clientAuthenticationMethods(Set.of("client_secret_basic", "client_secret_post"))
                        .clientSettings(createClientSettingsMap())
                        .clientSecretExpiresAt(ZonedDateTime.now().plusMonths(999))
                        .scopes(Set.of("Products.Write", "Products.Read", "Users.Create"))
                        .redirectUris(Set.of("https://stackoverflowSecond.com", "https://yandexSecond.ru"))
                        .postLogoutRedirectUris(Set.of("https://iShopSecond.com", "https://mailSecond.ru"))
                        .tokenSettings(createTokenSettings())
                        .build();

                clientRegisterDataThird = new ClientRegisterData.Builder("MyFrontTrader")
                        .id("3").clientId("345").clientSecret(passwordEncoder.encode("345"))
                        .clientSecretNotSecured("345")
                        .authorizationGrantTypes(Set.of("authorization_code", "client_credentials"))
                        .clientAuthenticationMethods(Set.of("client_secret_basic", "client_secret_post"))
                        .clientSettings(createClientSettingsMap())
                        .clientSecretExpiresAt(ZonedDateTime.now().plusMonths(999))
                        .scopes(Set.of("Products.Write", "Products.Read", "Users.Create"))
                        .redirectUris(Set.of("https://stackoverflowThird.com", "https://yandexThird.ru"))
                        .postLogoutRedirectUris(Set.of("https://iShopThird.com", "https://mailThird.ru"))
                        .tokenSettings(createTokenSettings())
                        .build();
            } catch (IshopResponseException exception) {
                throw new RuntimeException(exception.getMessage());

            }
            clientAppRepository.save(clientRegisterDataFirst);
            clientAppRepository.save(clientRegisterDataSecond);
            clientAppRepository.save(clientRegisterDataThird);
        };
    }

}
