package com.sprng.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class OAuthConfig {


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* .authorizationEndpoint(authorizationEndpoint ->
             authorizationEndpoint.consentPage("ссылка");*/

        http.exceptionHandling((exceptions) -> exceptions.defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/oauth/trader/authorization"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
        ));
//        http.formLogin(Customizer.withDefaults());
//        http.formLogin((form) -> form.usernameParameter("username")
//                .passwordParameter("password")
//                .loginPage("/oauth/trader/authorization"));
//                .failureForwardUrl("/oauth/trader/authorization?failed"));
        return http.build();
    }








    private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

}

// authorizationService отработает @GetMapping("oauth2/authorize") адрес зашит по умолчанию в Spring Security.
// В Controller @GetMapping("oauth2/authorize") нет.Он зашит по умолчанию в Spring Security.
// запрос выглядит так ?client_id=VALUE&redirect_url=VALUE&scope=openid read

//    http://localhost:9090/oauth2/authorize?client_id=$2a$10$XPItnalALTnwWei0WTnlAulpmP2RatO0REzG9m/QjgwgdZFstfmv.&response_type=code&redirect_uri=http://app.ru&scope=openid%20read

