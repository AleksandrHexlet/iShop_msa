package com.sprng.oauth2.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.sprng.library.entity.Customer;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.oauth2.repository.CustomerRepository;
import com.sprng.oauth2.service.LoginDataDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private LoginDataDetailService loginDataDetailService;
    private CustomerRepository customerRepository;

    @Autowired
    public SecurityConfiguration(LoginDataDetailService loginDataDetailService) {
        this.loginDataDetailService = loginDataDetailService;
        this.customerRepository = customerRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http); // oAuth2 берёт настройки(бины с реализацией для интерфейсов) из Spring Security,чтобы не писать нам самим их
        http.securityMatcher("/oauth2/user/authorize/**")
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class);
        http.exceptionHandling((exceptions) -> exceptions
                .defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/oauth2/user/authorize"),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)));
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    //    applicationContext.getBean("name bean")

    @Bean
    @Order(2)
    public SecurityFilterChain filterChainAuthorizeAllUsers(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/oauth2/user/authorize")
                .authenticationProvider(userAuthenticationProvider())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/oauth2/user/authorize").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLoginConfig -> formLoginConfig
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginPage("/oauth2/user/authorize") // get запрос на получение формы
                        .failureForwardUrl("/oauth2/user/authorize?failed")
                        .loginProcessingUrl("/oauth2/user/authorize") // нажали на кнопку войти и ушел пост запрос
                )
                .build();
    }


    private AuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(loginDataDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate,
                                                           RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsa() throws NoSuchAlgorithmException {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey)
                .keyID(UUID.randomUUID().toString()).build();
    }

    private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }


    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if (context.getTokenType() == OAuth2TokenType.ACCESS_TOKEN) {
                Authentication principal = context.getPrincipal();
                Optional<String> role = principal.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .findFirst();
                if (!role.isEmpty()) {
                        List<String> list = new ArrayList<>(context.getAuthorizedScopes());
                        list.add(role.get());
                        context.getClaims().claims(stringObjectMap -> stringObjectMap.put("scope", list));
                }
//                loginDataDetailService.
                String userName = principal.getName();
               customerRepository.findByUserName(userName)
                       .ifPresent((customer1)->{
                           context.getClaims().claims(addCustomerInfoMap
                                   -> addCustomerInfoMap.put("customerCity", customer1.getCity()));
                       });
            }
        };
    }
}
